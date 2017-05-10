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
public class adminController
{

    private DBEventModifier eventModifier;
    private DBDayModifier dayModifier;

    @RequestMapping(value = "/adminpanel", method = RequestMethod.GET)
    public ModelAndView initAdminEventScreen()
    {
        System.out.println("admin");
        return createModelAndView(null);
    }
    
    @RequestMapping(value = "/filterList", method = RequestMethod.POST)
    public ModelAndView getFilteredList(@ModelAttribute("SpringWeb") ScheduleableItemModel scheduleableItemModel,
            ModelMap model)
    {
        return createModelAndView(scheduleableItemModel.getType());
    }

    @RequestMapping(value = "/createWorkshop", method = RequestMethod.POST)
    public ModelAndView createWorkshop(@ModelAttribute("SpringWeb") ScheduleableItemModel scheduleableItemModel,
            ModelMap model)
    {
        Event newWorkshop = new Workshop(scheduleableItemModel.getEventName());
        newWorkshop = addEventFieldValues(scheduleableItemModel, newWorkshop);
        ((Workshop) newWorkshop).setPresenter(scheduleableItemModel.getPresenter());
        ((Workshop) newWorkshop).setMaxUsers(Integer.parseInt(scheduleableItemModel.getMaxUsers()));
        eventModifier.insertEvent(newWorkshop);
        return createModelAndView(null);
    }

    @RequestMapping(value = "/createLecture", method = RequestMethod.POST)
    public ModelAndView createLecture(@ModelAttribute("SpringWeb") ScheduleableItemModel scheduleableItemModel,
            ModelMap model)
    {
        Event newLecture = new Lecture(scheduleableItemModel.getEventName());
        newLecture = addEventFieldValues(scheduleableItemModel, newLecture);
        ((Lecture) newLecture).setPresenter(scheduleableItemModel.getPresenter());
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
        newDay = (EventDay) addEventDateValues(scheduleableItemModel, (EventDate) newDay);
        dayModifier.insertDay(newDay);
        return createModelAndView(null);
    }
    
    @RequestMapping(value = "/editItem", method = RequestMethod.POST)
    public ModelAndView editItem(@ModelAttribute("SpringWeb") ScheduledItemModel itemToEdit,
            ModelMap model)
    {
        System.out.println(itemToEdit.getId() + " " + itemToEdit.getString());
        ModelAndView thisView = createModelAndView(null);
        ScheduleableItemModel selectedItem = null;
        if(itemToEdit.getString().substring(0, 6).equals("School")){
            dayModifier = new DBDayModifier();
            EventDay selectedDay = dayModifier.getDay(itemToEdit.getId());
            System.out.println(selectedDay.toString());
        }else{
            eventModifier = new DBEventModifier();
            eventModifier.getEvent(itemToEdit.getId());
            System.out.println("Non school item");
        }
        thisView.addObject("selectedItem", selectedItem);
        return thisView;
    }

    private List<String> getEventTypes()
    {
        List<String> retV = new ArrayList<String>();
        for (EventType eT : EventType.values())
        {
            retV.add(eT.toString());
        }
        return retV;
    }

    private ModelAndView createModelAndView(String day)
    {
        eventModifier = new DBEventModifier();
        dayModifier = new DBDayModifier();
        ModelAndView modelView = new ModelAndView("adminpanel", "command", new ScheduleableItemModel());
        modelView.addObject("types", getEventTypes());
        modelView.addObject("fields", getPossibleFields());
        List<ScheduledItemModel> scheduledItems = getScheduledItems(day);
        modelView.addObject("scheduledItems", scheduledItems);
        return modelView;
    }

    private List<String> getPossibleFields()
    {
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

    private Event addEventFieldValues(ScheduleableItemModel item, Event targetEvent)
    {
        targetEvent = (Event) addEventDateValues(item, (EventDate) targetEvent);
        targetEvent.setImageURL(item.getImageURL());
        return targetEvent;
    }

    private EventDate addEventDateValues(ScheduleableItemModel item, EventDate targetEventDate)
    {
        targetEventDate.setDate(item.getDate());
        targetEventDate.setDescription(item.getDescription());
        targetEventDate.setEndTime(item.getEndTime());
        targetEventDate.setStartTime(item.getStartTime());
        targetEventDate.setLocationName(item.getLocationName());
        return targetEventDate;
    }

    private List<ScheduledItemModel> getScheduledItems(String type)
    {
        List<ScheduledItemModel> scheduledItems = new ArrayList<ScheduledItemModel>();
        for (EventDay eD : dayModifier.getDays())
        {
            if(type == null || eD.getEventType().toString().equals(type)){
                String text = "School: " + eD.getEventName() + ", Time: " + eD.getEndTime() + "-" + eD.getEndTime();
                scheduledItems.add(new ScheduledItemModel(eD.getId(), text));
            }
        }
        for (Event e : eventModifier.getEvents())
        {
            if(type == null || e.getEventType().toString().equals(type)){
                String text = e.getEventType() + ": " + e.getEventName() + ", Time: " + e.getEndTime() + "-" + e.getEndTime();
                scheduledItems.add(new ScheduledItemModel(e.getId(), text));
            }
        }
        return scheduledItems;
    }
}
