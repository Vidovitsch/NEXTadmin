/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Controllers;

import Models.QRManager;
import Models.QRViewModel;
import Models.ScheduleableItemModel;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;


/**
 *
 * @author David
 */
@Controller
public class qrController {

    private QRManager qrManager = new QRManager();
    
    @RequestMapping(value = "/qrcodes", method = RequestMethod.GET)
    public ModelAndView initAdminEventScreen() {       
        ModelAndView mav = new ModelAndView("qrcodes");
        QRViewModel vModel = new QRViewModel();
        if (qrManager.checkGenerated()) {
            vModel.setGenerated("true");
            vModel.setCodes(qrManager.getQRCodes());
        } else {
            System.out.println("not generated");
            vModel.setGenerated("false");
        }
        mav.addObject("vModel", vModel);
        
        return mav;
    }
    
    @RequestMapping(value = "/qrcodes", method = RequestMethod.POST)
    public ModelAndView generateQRCodes(@ModelAttribute("SpringWeb") QRViewModel vModel,
            ModelMap model) {
        qrManager.generate();
        vModel.setGenerated("true");
        vModel.setCodes(qrManager.getQRCodes());
        
        ModelAndView mav = new ModelAndView("qrcodes");
        mav.addObject("vModel", vModel);
        
        return mav;
    }
}
