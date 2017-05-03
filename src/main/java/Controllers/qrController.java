/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Controllers;

import Models.QRGenerator;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;


/**
 *
 * @author David
 */
@Controller
public class qrController {

    @RequestMapping(value = "/qrcodes", method = RequestMethod.GET)
    public ModelAndView initAdminEventScreen() {
        new QRGenerator().generate();
        return new ModelAndView("qrcodes");
    }
}
