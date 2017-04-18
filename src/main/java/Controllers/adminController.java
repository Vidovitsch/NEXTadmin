/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import Database.DBDayModifier;
import Database.DBEventModifier;
import Enums.EventType;
import Models.ScheduleableItemModel;
import Models.Workshop;
import Models.Event;
import Models.EventDate;
import Models.EventDay;
import Models.Lecture;
import Models.Performance;
import Models.ScheduledItemModel;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author Arno Dekkers Los
 */
@Controller
public class adminController {
    private DBEventModifier eventModifier;
    private DBDayModifier dayModifier;
    
    @RequestMapping(value = {"/adminpanel", "/createWorkshop"} , method = RequestMethod.GET)
    public ModelAndView initAdminEventScreen() {
        //insertDummySchedule();
        return createModelAndView(null);
    }
    
    @RequestMapping(value = "/createWorkshop", method = RequestMethod.POST)
    public ModelAndView createWorkshop(@ModelAttribute("SpringWeb") ScheduleableItemModel scheduleableItemModel,
            ModelMap model)
    {
        System.out.println("got in the method");
        Event newWorkshop = new Workshop(scheduleableItemModel.getEventName());
        newWorkshop = addEventFieldValues(scheduleableItemModel, newWorkshop);
        ((Workshop)newWorkshop).setPresenter(scheduleableItemModel.getPresenter());
        ((Workshop)newWorkshop).setMaxUsers(Integer.parseInt(scheduleableItemModel.getMaxUsers()));
        eventModifier.insertEvent(newWorkshop);
        return createModelAndView(null);
    }
    
    @RequestMapping(value = "/createLecture", method = RequestMethod.POST)
    public ModelAndView createLecture(@ModelAttribute("SpringWeb") ScheduleableItemModel scheduleableItemModel,
            ModelMap model)
    {
        Event newLecture = new Lecture(scheduleableItemModel.getEventName());
        newLecture = addEventFieldValues(scheduleableItemModel, newLecture);
        ((Lecture)newLecture).setPresenter(scheduleableItemModel.getPresenter());
        eventModifier.insertEvent(newLecture);
        return createModelAndView(null);
    }
    
    @RequestMapping(value = "/createPerformance", method = RequestMethod.POST)
    public ModelAndView createPerformance(@ModelAttribute("SpringWeb") ScheduleableItemModel scheduleableItemModel,
            ModelMap model)
    {
        Event newPerformance = new Performance(scheduleableItemModel.getEventName());
        newPerformance = addEventFieldValues(scheduleableItemModel, newPerformance);
        eventModifier.insertEvent(newPerformance);
        return createModelAndView(null);
    }
    
    @RequestMapping(value = "/createSchoolday", method = RequestMethod.POST)
    public ModelAndView createSchoolday(@ModelAttribute("SpringWeb") ScheduleableItemModel scheduleableItemModel,
            ModelMap model)
    {
        EventDay newDay = new EventDay(scheduleableItemModel.getEventName());
        newDay = (EventDay)addEventDateValues(scheduleableItemModel, (EventDate)newDay);
        dayModifier.insertDay(newDay);
        return createModelAndView(null);
    }
    
    private List<String> getEventTypes(){
        List<String> retV = new ArrayList<String>();
        for(EventType eT : EventType.values()){
            retV.add(eT.toString());
        }
        return retV;
    }
    
    private ModelAndView createModelAndView(String day){
        eventModifier = new DBEventModifier();
        dayModifier = new DBDayModifier();
        ModelAndView modelView = new ModelAndView("adminpanel", "command", new ScheduleableItemModel());
        modelView.addObject("types", getEventTypes());
        modelView.addObject("fields", getPossibleFields());
        List<ScheduledItemModel> scheduledItems = new ArrayList<ScheduledItemModel>();
        if("".equals(day) || day == null){
            scheduledItems.add(new ScheduledItemModel("1", "eerste test item"));
            scheduledItems.add(new ScheduledItemModel("2", "tweede test item"));
            scheduledItems.add(new ScheduledItemModel("3", "derde test item"));
        }else{
            System.out.println("wrong statement");
        }
        modelView.addObject("scheduledItems", scheduledItems);
        return modelView;
    }
    
    private List<String> getPossibleFields(){
        List<String> retV = new ArrayList<String>();
        retV.add("id");
        retV.add("eventName");
        retV.add("startTime");
        retV.add("endTime");
        retV.add("date");
        retV.add("locationName");
        retV.add("imageURL");
        retV.add("presenter");
        retV.add("maxUsers");
        return retV;
    }
    
    private Event addEventFieldValues(ScheduleableItemModel item, Event targetEvent){
        targetEvent = (Event)addEventDateValues(item, (EventDate)targetEvent);
        targetEvent.setImageURL(item.getImageURL());
        return targetEvent;
    }
    
    private EventDate addEventDateValues(ScheduleableItemModel item, EventDate targetEventDate){
        targetEventDate.setDate(item.getDate());
        targetEventDate.setDescription(item.getDescription());
        targetEventDate.setEndTime(item.getEndTime());
        targetEventDate.setStartTime(item.getStartTime());
        targetEventDate.setLocationName(item.getLocationName());
        return targetEventDate;
    }
}
