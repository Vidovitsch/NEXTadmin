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
 *
 * @author Michiel van Eijkeren
 */
@Controller
public class studentManagementController
{
    @RequestMapping(value = "/studentmanagement", method = RequestMethod.GET)
    public ModelAndView studentmanagementrequest()
    {
        return new ModelAndView("studentmanagement");
    }
}
