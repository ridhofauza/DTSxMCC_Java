/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mii.client.app.controller;

import lombok.AllArgsConstructor;
import mii.client.app.model.Region;
import mii.client.app.service.RegionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author MSI-JO
 */
@Controller
@AllArgsConstructor
@RequestMapping("/region")
public class RegionController {
    
    private RegionService regionService;
    
    @GetMapping
    public String index(Model model){
        model.addAttribute("regions", regionService.getAll());
        model.addAttribute("name","johan");
        model.addAttribute("isActive","region");
        return "region/index";
    }
    
    @GetMapping("/create")
    public String create(Region region){
        return "region/create_form";
    }
        
    @PostMapping
    public String created(Region region){
        regionService.create(region);
        return "redirect:/region";
    }
    
    @GetMapping("/update/{id}")
    public String update(@PathVariable Long id, Model model){
        model.addAttribute("region", regionService.getById(id));
        return "region/update_form";
    }
    
    @PutMapping("/{id}")
    public String updated(@PathVariable Long id, Region region){
        regionService.update(id, region);
        return "redirect:/region";
    }
    
    @DeleteMapping("/{id}")
    public String deleted(@PathVariable Long id){
        regionService.delete(id);
        return "redirect:/region";
    }
    
}
