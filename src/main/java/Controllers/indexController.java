/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import org.springframework.stereotype.Controller;
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
    /**
     * upon the page load of the index page this method is called to return the ModelView
     * @return new ModelAndView("index")
     */
    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public ModelAndView LoginRequest()
    {
        return new ModelAndView("index");
    }
}
