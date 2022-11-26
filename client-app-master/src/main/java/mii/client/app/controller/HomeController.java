/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mii.client.app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 *
 * @author MSI-JO
 */
@Controller // HTML dan JSON    Randika, Kurniawan, Alfian, Andi
public class HomeController {

    @GetMapping("/dashboard")
    public String index(Model model){
        model.addAttribute("name", "Hallo Joahan");
        return "index";
    }
    
    
}
