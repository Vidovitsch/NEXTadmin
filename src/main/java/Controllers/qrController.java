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
 * This class is used for the page loads of the QRManager screen
 * @author David
 */
@Controller
public class qrController {

    private QRManager qrManager = new QRManager();
    
    /**
     * This method is called upon loading the QRMAnager screen it retreives all 
     * QR codes from the firebase and adds these to the ModelAndview mav
     * @return mav
     */
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
    
    /**
     * This method is called upon a post request in the QRCodemanager
     * it calls the generate method of the QRManagerfield after that it returns 
     * a ModelAndView containing the qrCodes retrieved by the getQRCodes from QRManager
     * @param vModel
     * @param model
     * @return 
     */
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
