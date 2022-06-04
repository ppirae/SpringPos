package com.example.SpringPos.controller;

import com.example.SpringPos.controller.form.ItemForm;
import com.example.SpringPos.domain.Item;
import com.example.SpringPos.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.sql.Timestamp;
import java.util.List;

@Controller
public class ItemController {

    private final ItemService itemService;

    @Autowired
    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @GetMapping("/items/new")
    public String createForm(Model model) {
        model.addAttribute("form", new ItemForm());
        return "items/createItemForm";
    }

    @PostMapping("items/new")
    public String create(@Valid @ModelAttribute("form") ItemForm form, BindingResult bindingResult) {

        if(bindingResult.hasErrors()) {
            return "items/createItemForm";
        }

        Item item = new Item();
        item.setItemName(form.getItemName());
        item.setItemPrice(form.getItemPrice());
        item.setItemQuantity(form.getItemQuantity());
        item.setItemStockTime(new Timestamp(System.currentTimeMillis()));

        itemService.saveItem(item);
        return "redirect:/";
    }

    @GetMapping("/items")
    public String list(Model model) {
        List<Item> items = itemService.findItems();
        model.addAttribute("items", items);
        return "items/itemList";
    }

    @GetMapping("items/{itemId}/edit")
    public String updateItemForm(@PathVariable("itemId") Long itemId, Model model) {
        Item item = (Item) itemService.findOne(itemId);

        ItemForm form = new ItemForm();
        //form.setId(item.getId());
        form.setItemName(item.getItemName());
        form.setItemPrice(item.getItemPrice());
        form.setItemQuantity(item.getItemQuantity());
        //form.setItemStockTime(new Timestamp(System.currentTimeMillis()));

        model.addAttribute("form", form);
        return "items/updateItemForm";
    }

    @PostMapping("items/{itemId}/edit")
    public String updateItem(@PathVariable Long itemId, @Valid @ModelAttribute("form") ItemForm form, BindingResult bindingResult) {

        if(bindingResult.hasErrors()) {
            return "items/updateItemForm";
        }

        itemService.updateItem(itemId, form.getItemName(), form.getItemPrice(), form.getItemQuantity());

        return "redirect:/items";
    }
}
