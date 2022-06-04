package com.example.SpringPos.controller;

import com.example.SpringPos.controller.form.SalesForm;
import com.example.SpringPos.domain.Item;
import com.example.SpringPos.domain.Payment;
import com.example.SpringPos.domain.Sales;
import com.example.SpringPos.service.ItemService;
import com.example.SpringPos.service.SalesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
public class SalesController {

    private SalesService salesService;
    private ItemService itemService;

    @Autowired
    public SalesController(SalesService salesService, ItemService itemService) {
        this.salesService = salesService;
        this.itemService = itemService;
    }

    @ModelAttribute("payments")
    public List<Payment> payments() {
        List<Payment> payments = new ArrayList<>();
        payments.add(new Payment("CARD", "카드"));
        payments.add(new Payment("CASH", "현금"));
        return payments;
    }

    @GetMapping("/sales")
    public String createForm(Model model) {

        List<Item> items = itemService.findItems();

        model.addAttribute("items", items);

        return "sales/salesForm";
    }

    @PostMapping("/sales")
    public String sales(@RequestParam(value = "itemId") Long itemId,
                        @RequestParam(value = "count") int count) {

        salesService.saveSales(itemId, count);

        return "redirect:/sales";
    }


    @GetMapping("/salesList")
    public String salesList(Model model) {
        List<Sales> sales = salesService.findSales();
        model.addAttribute("sales", sales);

        return "sales/salesList";
    }
}
