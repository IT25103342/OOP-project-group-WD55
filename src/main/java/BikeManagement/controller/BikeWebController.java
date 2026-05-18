package BikeManagement.controller;

import BikeManagement.entity.Bike;
import BikeManagement.service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class BikeWebController {

    @Autowired
    private InventoryService inventoryService;

    @GetMapping("/inventory")
    public String showWebPage(Model model) {
        List<Bike> bikeList = inventoryService.getBikes();
        model.addAttribute("bikes", bikeList);
        return "index";
    }

    @PostMapping("/add")
    public String webAddBike(@RequestParam("id") String id,
                             @RequestParam("type") String type,
                             @RequestParam("status") String status) {

        inventoryService.addBike(id, type, status);
        return "redirect:/inventory";
    }

    @GetMapping("/delete/{id}")
    public String deleteBike(@PathVariable("id") String id) {
        inventoryService.removeBike(id);
        return "redirect:/inventory";
    }
}