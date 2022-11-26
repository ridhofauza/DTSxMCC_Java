/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mii.client.app.controller;

import lombok.AllArgsConstructor;
import mii.client.app.model.Country;
import mii.client.app.service.CountryService;
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
@RequestMapping("/country")
public class CountryController {

    private CountryService countryService;
    private RegionService regionService;

    @GetMapping
    public String index(Model model) {
        model.addAttribute("countries", countryService.getAll());
        model.addAttribute("name", "johan");
        model.addAttribute("isActive", "country");
        return "country/index";
    }

    @GetMapping("/create")
    public String create(Country country, Model model) {
        model.addAttribute("regions", regionService.getAll());
        return "country/create_form";
    }

    @PostMapping
    public String created(Country country) {
        countryService.create(country);
        return "redirect:/country";
    }

    @GetMapping("/update/{id}")
    public String update(@PathVariable Long id, Model model) {
        model.addAttribute("country", countryService.getById(id));
        model.addAttribute("regions", regionService.getAll());
        return "country/update_form";
    }

    @PutMapping("/{id}")
    public String updated(@PathVariable Long id, Country country) {
        countryService.update(id, country);
        return "redirect:/country";
    }

    @DeleteMapping("/{id}")
    public String deleted(@PathVariable Long id) {
        countryService.delete(id);
        return "redirect:/country";
    }
}
