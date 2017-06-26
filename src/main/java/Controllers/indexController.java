/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import Database.DBGroupModifier;
import Database.DBUserModifier;
import Models.IndexModel;
import Models.ScheduleableItemModel;
import Models.ScheduledItemModel;
import Models.User;
import Models.UserAllocation;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
/**
 * This class is used to load the GUI for the index Screen
 * @author Michiel van Eijkeren
 */
@Controller
public class indexController
{
    DBGroupModifier groupModifier;
    DBUserModifier userModifier;
    /**
     * upon the page load of the index page this method is called to return the ModelView
     * @return new ModelAndView("index")
     */
    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public ModelAndView initIndexScreen()
    {
        
        return new ModelAndView("index", "command", new IndexModel());
    }
    
    /**
     * This method is used to reset/remove all the user groups
     * @return "index"
     */
    @RequestMapping(value = "/resetGroups", method = RequestMethod.GET)
    public String resetGroups()
    {
        groupModifier = new DBGroupModifier();
        groupModifier.resetGroups();
        return "index";
    }
    
    /**
     * This method is used to create all the user groups at the beginning
     * of the evenement
     * It retrieves the Users from the firbase with "@student.fontys.nl" and
     * that are not alocated in a group yet. It then creates the new groups and
     * puts them over these
     * @return "index"
     */
    @RequestMapping(value = "/createGroups", method = RequestMethod.POST)
    public String createGroups(@ModelAttribute("SpringWeb") IndexModel indexModel)
    {
        groupModifier = new DBGroupModifier();
        userModifier = new DBUserModifier();
        ArrayList<User> eligibleUsers = userModifier.getUnassignedUsers();
        new UserAllocation(eligibleUsers, indexModel.getStudentsPerGroup(), indexModel.getMMixGroups());
        return "index";
    }
}
