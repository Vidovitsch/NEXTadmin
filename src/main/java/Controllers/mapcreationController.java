/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * This class is used upon requests for loading the map creation page
 * @author David
 */
@Controller
public class mapcreationController {
    /**
     * This method is calling upon loading the map creation page
     * @return new ModelAndView("mapcreation")
     */
    @RequestMapping("/mapcreation")
    public ModelAndView initMapScreen() {
        return new ModelAndView("mapcreation");
    }
}
