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
 * This is the controller that is for the adminpanel screen. The screen where 
 * planned activities are listed, edited, deleted and created
 * @author Arno Dekkers Los
 */
@Controller
public class adminController
{
    private DBEventModifier eventModifier;
    private DBDayModifier dayModifier;

    /**
     * This is the page loader for the standard adminpanel.
     * it returns the createModelAndView methods return value when given the parameter null
     * @return createModelAndView(null)
     */
    @RequestMapping(value = "/adminpanel", method = RequestMethod.GET)
    public ModelAndView initAdminEventScreen()
    {
        return createModelAndView(null);
    }
    
    /**
     * this method is called upon loading the panel and wanting to display only one type of scheduled events
     * upon pageload the method createModelAndView is called which is given the parameter of the selected type event
     * @param scheduleableItemModel
     * @param model
     * @return createModelAndView(type)
     */
    @RequestMapping(value = "/filterList", method = RequestMethod.POST)
    public ModelAndView getFilteredList(@ModelAttribute("SpringWeb") ScheduleableItemModel scheduleableItemModel,
            ModelMap model)
    {
        return createModelAndView(scheduleableItemModel.getType());
    }
    
    /**
     * this method is called when the user tries to delete an event it get's the parameter
     * scheduledItemModel which is the item that has to be deleted. if the id in this model 
     * is null an exception get's thrown. after deleting the event from the firebase it loads 
     * the standard overview of the adminpanel by calling createMoelAndView with parameter null
     * @param scheduledItemModel
     * @param model
     * @return createModelAndView(null)
     */
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
    
    /**
     * This method is called when an admin is done changing the values of an existing event
     * the parameter schedulableITemModel contains the new values and old ID which
     * is then sent to the database methods when done it loads the standard GUI view
     * @param scheduleableItemModel
     * @param model
     * @return createModelAndView(null);
     */
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

    /**
     * This method is called when the admin tries to create an item of the type workshop
     * the parameter scheduleableItemModel contains the values for the new workshop
     * when done the standard view of the adminpanel gets loaded with createModelAndView(null);
     * @param scheduleableItemModel
     * @param model
     * @return createModelAndView(null);
     */
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

    /**
     * This method is called when the admin tries to create an item of the type lecture
     * the parameter scheduleableItemModel contains the values for the new lecture
     * when done the standard view of the adminpanel gets loaded with createModelAndView(null);
     * @param scheduleableItemModel
     * @param model
     * @return createModelAndView(null);
     */
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

    /**
     * This method is called when the admin tries to create an item of the type performance
     * the parameter scheduleableItemModel contains the values for the new performance
     * when done the standard view of the adminpanel gets loaded with createModelAndView(null);
     * @param scheduleableItemModel
     * @param model
     * @return createModelAndView(null);
     */
    @RequestMapping(value = "/createPerformance", method = RequestMethod.POST)
    public ModelAndView createPerformance(@ModelAttribute("SpringWeb") ScheduleableItemModel scheduleableItemModel,
            ModelMap model)
    {
        Event newPerformance = new Performance(scheduleableItemModel.getEventName());
        newPerformance = addEventFieldValues(scheduleableItemModel, newPerformance);
        eventModifier.insertEvent(newPerformance);
        return createModelAndView(null);
    }

    /**
     * This method is called when the admin tries to create an item of the type schoolday
     * the parameter scheduleableItemModel contains the values for the new schoolday
     * when done the standard view of the adminpanel gets loaded with createModelAndView(null);
     * @param scheduleableItemModel
     * @param model
     * @return createModelAndView(null);
     */
    @RequestMapping(value = "/createSchoolday", method = RequestMethod.POST)
    public ModelAndView createSchoolday(@ModelAttribute("SpringWeb") ScheduleableItemModel scheduleableItemModel,
            ModelMap model)
    {
        EventDay newDay = new EventDay(scheduleableItemModel.getEventName());
        newDay = (EventDay) addEventDateValues(scheduleableItemModel, (EventDate) newDay);
        dayModifier.insertDay(newDay);
        return createModelAndView(null);
    }
    
    /**
     * this method is called when an admin selects an item in the list in the GUI
     * the item is sent to the method as the parameter itemToEdit
     * this item is then used to retrieve the whole item and load it into the GUI
     * @param itemToEdit
     * @param model
     * @return thisview
     */
    @RequestMapping(value = "/editItem", method = RequestMethod.POST)
    public ModelAndView editItem(@ModelAttribute("SpringWeb") ScheduledItemModel itemToEdit,
            ModelMap model)
    {
        ModelAndView thisView = createModelAndView(null);
        ScheduleableItemModel selectedItem = new ScheduleableItemModel();
        if(itemToEdit.getString().substring(0, 6).equals("School")){
            dayModifier = new DBDayModifier();
            EventDay selectedDay = dayModifier.getDay(itemToEdit.getId());
            eventDateToScheduleableItemModel(selectedItem, (EventDate) selectedDay);
            selectedItem.setType(EventType.None.toString());
        }else{
            eventModifier = new DBEventModifier();
            Event selectedEvent = eventModifier.getEvent(itemToEdit.getId());
            eventToScheduleableItemModel(selectedItem, selectedEvent);
        }
        selectedItem.setId(itemToEdit.getId());
        thisView.addObject("selectedItem", selectedItem);
        return thisView;
    }

    /**
     * This method is used to sent parameters to the GUI, these parameters are the
     * different types of events that can be made in our application
     * @return retV
     */
    private List<String> getEventTypes()
    {
        List<String> retV = new ArrayList<String>();
        for (EventType eT : EventType.values())
        {
            retV.add(eT.toString());
        }
        return retV;
    }

    /**
     * this is used to create the ModelAndView standard. which is used upon nearly every page
     * reload. it can be given a parameter in the case that only events of a specific type
     * should be loaded. in case that the given parameter is loaded it is not filtered out
     * it uses the method getEventTypes to pass a list of the different schedulable events to the gui
     * it uses getPossibleFiels to pass a list of the possible fields to the GUI so they can be generated
     * and it uses getScheduledItems to get a list of all items
     * @param eventType
     * @return modelView
     */
    private ModelAndView createModelAndView(String eventType)
    {
        System.out.println("called adminpanel");
        eventModifier = new DBEventModifier();
        dayModifier = new DBDayModifier();
        System.out.println("done with daymodifierconstructor");
        ModelAndView modelView = new ModelAndView("adminpanel", "command", new ScheduleableItemModel());
        modelView.addObject("types", getEventTypes());
        modelView.addObject("fields", getPossibleFields());
        List<ScheduledItemModel> scheduledItems = getScheduledItems(eventType);
        ScheduleableItemModel dummySelectedItem = new ScheduleableItemModel();
        dummySelectedItem.setId("-1");
        modelView.addObject("selectedItem", dummySelectedItem);
        modelView.addObject("scheduledItems", scheduledItems);
        System.out.println("returning modelview");
        return modelView;
    }

    /**
     * the different fields associated with events. this method is called by createModelAndView 
     * the differnt fields are added to a List<String>
     * @return retV
     */
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

    /**
     * this method is used to add the values from an instance scheduleableItemModel to 
     * an instance of the type Event, the method calls addEVentDateValues
     * this is called by the methods to load the create/edit an Event
     * @param item
     * @param targetEvent
     * @return 
     */
    private Event addEventFieldValues(ScheduleableItemModel item, Event targetEvent)
    {
        targetEvent = (Event) addEventDateValues(item, (EventDate) targetEvent);
        targetEvent.setImageURL(item.getImageURL());
        return targetEvent;
    }

    /**
     * this method is used to add all EventDate fields to an instance Eventdate from an
     * instance ScheduleableItemModel this method is called by addEventFieldValues
     * @param item
     * @param targetEventDate
     * @return 
     */
    private EventDate addEventDateValues(ScheduleableItemModel item, EventDate targetEventDate)
    {
        targetEventDate.setDate(item.getDate());
        targetEventDate.setDescription(item.getDescription());
        targetEventDate.setEndTime(item.getEndTime());
        targetEventDate.setStartTime(item.getStartTime());
        targetEventDate.setLocationName(item.getLocationName());
        return targetEventDate;
    }

    /**
     * This method is used to retrieve the existing scheduled items from the firebase
     * in case that the parameter type is null all types are loaded. in case that type is
     * not null only the items from the given type will be loaded in the return List<ScheduledItemModel>
     * @param type
     * @return scheduledItems
     */
    private List<ScheduledItemModel> getScheduledItems(String type)
    {
        System.out.println("in getscheduled items");
        List<ScheduledItemModel> scheduledItems = new ArrayList<ScheduledItemModel>();
        for (EventDay eD : dayModifier.getDays())
        {
            if(type == null || eD.getEventType().toString().equals(type)){
                String text = "School: " + eD.getEventName() + ", Time: " + eD.getEndTime() + "-" + eD.getEndTime();
                scheduledItems.add(new ScheduledItemModel(eD.getId(), text));
            }
        }
        System.out.println("done with getdays");
        for (Event e : eventModifier.getEvents())
        {
            if(type == null || e.getEventType().toString().equals(type)){
                String text = e.getEventType() + ": " + e.getEventName() + ", Time: " + e.getEndTime() + "-" + e.getEndTime();
                scheduledItems.add(new ScheduledItemModel(e.getId(), text));
            }
        }
                System.out.println("done with getscheduled items");
        return scheduledItems;
    }

    /**
     * this method is used place the data of an instance EvenDate in an ScheduleableItemModel instance
     * @param selectedItem
     * @param selectedDay 
     */
    private void eventDateToScheduleableItemModel(ScheduleableItemModel selectedItem, EventDate selectedDay) {
        selectedItem.setEventName(selectedDay.getEventName());
        selectedItem.setDate(selectedDay.getDate());
        selectedItem.setStartTime(selectedDay.getStartTime());
        selectedItem.setEndTime(selectedDay.getEndTime());
        selectedItem.setLocationName(selectedDay.getLocationName());
        selectedItem.setDescription(selectedDay.getDescription());
    }

    /**
     * this method is used to place the item from an instance Event into an instance ScheduleableItemModel
     * @param selectedItem
     * @param selectedEvent 
     */
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
