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
        return createModelAndView(null);
    }
    
    @RequestMapping(value = "/filterList", method = RequestMethod.POST)
    public ModelAndView getFilteredList(@ModelAttribute("SpringWeb") ScheduleableItemModel scheduleableItemModel,
            ModelMap model)
    {
        return createModelAndView(scheduleableItemModel.getType());
    }
    
    @RequestMapping(value = "/deleteEvent", method = RequestMethod.POST)
    public ModelAndView deleteItem(@ModelAttribute("SpringWeb") ScheduledItemModel scheduledItemModel,
            ModelMap model)
    {
        if(scheduledItemModel.getId() == null || "".equals(scheduledItemModel.getId())){
            throw new IllegalArgumentException("tried to delete database entry with id null");
        }
        if(scheduledItemModel.getString().equals(EventType.None.toString())){
            dayModifier = new DBDayModifier();
            EventDay day = new EventDay("day to delete");
            day.setId(scheduledItemModel.getId());
            dayModifier.removeDay(day);
        }else{
            eventModifier = new DBEventModifier();
            Workshop event = new Workshop("event to delete");
            event.setId(scheduledItemModel.getId());
            eventModifier.removeEvent(event);
        }
        return createModelAndView(null);
    }
    
    @RequestMapping(value = "/editEvent", method = RequestMethod.POST)
    public ModelAndView editEvent(@ModelAttribute("SpringWeb") ScheduleableItemModel scheduleableItemModel,
            ModelMap model)
    {
        EventDate itemToEdit = null;
        if(scheduleableItemModel.getType().equals(EventType.None.toString())){
            itemToEdit = new EventDay(scheduleableItemModel.getEventName());
            addEventDateValues(scheduleableItemModel, (EventDate) itemToEdit);
            ((EventDay)itemToEdit).setId(scheduleableItemModel.getId());
            dayModifier = new DBDayModifier();
            dayModifier.updateDay((EventDay)itemToEdit);
        }else{
            eventModifier = new DBEventModifier();
            if(scheduleableItemModel.getType().equals(EventType.Workshop.toString())){
                itemToEdit = new Workshop(scheduleableItemModel.getEventName());
                ((Workshop) itemToEdit).setPresenter(scheduleableItemModel.getPresenter());
                ((Workshop) itemToEdit).setMaxUsers(Integer.parseInt(scheduleableItemModel.getMaxUsers()));
            }else if(scheduleableItemModel.getType().equals(EventType.Lecture.toString())){
                itemToEdit = new Lecture(scheduleableItemModel.getEventName());
                ((Lecture) itemToEdit).setPresenter(scheduleableItemModel.getPresenter());
            }else if(scheduleableItemModel.getType().equals(EventType.Performance.toString())){
                itemToEdit = new Performance(scheduleableItemModel.getEventName());
            }
            addEventFieldValues(scheduleableItemModel, (Event)itemToEdit);
            ((Event)itemToEdit).setId(scheduleableItemModel.getId());
            eventModifier.updateEvent((Event)itemToEdit);
        }
        return createModelAndView(null);
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
        ModelAndView thisView = createModelAndView(null);
        ScheduleableItemModel selectedItem = new ScheduleableItemModel();
        if(itemToEdit.getString().substring(0, 6).equals("School")){
            dayModifier = new DBDayModifier();
            EventDay selectedDay = dayModifier.getDay(itemToEdit.getId());
            System.out.println(selectedDay.getEventName());
            eventDateToScheduleableItemModel(selectedItem, (EventDate) selectedDay);
            selectedItem.setType(EventType.None.toString());
        }else{
            eventModifier = new DBEventModifier();
            Event selectedEvent = eventModifier.getEvent(itemToEdit.getId());
            eventToScheduleableItemModel(selectedItem, selectedEvent);
            System.out.println(selectedEvent.getEventName());
        }
        selectedItem.setId(itemToEdit.getId());
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
        ScheduleableItemModel dummySelectedItem = new ScheduleableItemModel();
        dummySelectedItem.setId("-1");
        modelView.addObject("selectedItem", dummySelectedItem);
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

    private void eventDateToScheduleableItemModel(ScheduleableItemModel selectedItem, EventDate selectedDay) {
        selectedItem.setEventName(selectedDay.getEventName());
        selectedItem.setDate(selectedDay.getDate());
        selectedItem.setStartTime(selectedDay.getStartTime());
        selectedItem.setEndTime(selectedDay.getEndTime());
        selectedItem.setLocationName(selectedDay.getLocationName());
        selectedItem.setDescription(selectedDay.getDescription());
    }

    private void eventToScheduleableItemModel(ScheduleableItemModel selectedItem, Event selectedEvent) {
        eventDateToScheduleableItemModel(selectedItem, (EventDate)selectedEvent);
        selectedItem.setImageURL(selectedEvent.getImageURL());
        selectedItem.setType(selectedEvent.getEventType().toString());
        switch(selectedEvent.getEventType()){
            case Workshop:
                selectedItem.setMaxUsers(Integer.toString(((Workshop)selectedEvent).getMaxUsers()));
                selectedItem.setPresenter(((Workshop)selectedEvent).getPresenter());
                break;
            case Lecture:
                selectedItem.setPresenter(((Lecture)selectedEvent).getPresenter());
                break;
        }
    }
}
