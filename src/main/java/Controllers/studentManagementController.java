/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import Database.DBEventModifier;
import Database.DBGroupModifier;
import Database.IModEvent;
import Database.IModGroup;
import Models.Event;
import Models.Group;
import Models.QRViewModel;
import Models.StudentManagerViewModel;
import Models.Workshop;
import java.util.ArrayList;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
/**
 * This class is used to load the GUI from the StudentManager gui
 * @author Michiel van Eijkeren
 */
@Controller
public class studentManagementController {
    
    /**
     * this method creates a new ModelAndView and returns this upon a get request
     * @return mav
     */
    @RequestMapping(value = "/studentmanagement", method = RequestMethod.GET)
    public ModelAndView studentmanagementrequest() {
        ModelAndView mav = new ModelAndView("studentmanagement");
        
        return mav;
    }
    
    /**
     * this method creates a new ModelAndView and returns this upon a post request
     * @return mav
     */
    @RequestMapping(value = "/studentmanagement", method = RequestMethod.POST)
    public ModelAndView handleAttend(@ModelAttribute("SpringWeb") StudentManagerViewModel vModel,
            ModelMap model) {
        ModelAndView mav = new ModelAndView("studentmanagement");
        
        return mav;
    }
   
}
