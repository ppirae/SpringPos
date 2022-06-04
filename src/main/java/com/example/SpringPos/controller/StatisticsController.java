package com.example.SpringPos.controller;

import com.example.SpringPos.domain.Sales;
import com.example.SpringPos.service.SalesService;
import com.example.SpringPos.service.StatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class StatisticsController {

    private final SalesService salesService;
    private final StatisticsService statisticsService;

    @Autowired
    public StatisticsController(SalesService salesService, StatisticsService statisticsService) {
        this.salesService = salesService;
        this.statisticsService = statisticsService;
    }

    @GetMapping("statistics/period")
    public String statisticsPeriod(Model model) {
        List<Sales> sales = salesService.findSales();
        model.addAttribute("statisticsService", statisticsService);
        model.addAttribute("sales", sales);

        return "statistics/period";
    }

    @GetMapping("statistics/item")
    public String statisticsItem(Model model) {
        List<Sales> sales = salesService.findSales();
        model.addAttribute("statisticsService", statisticsService);
        model.addAttribute("sales", sales);

        return "statistics/item";
    }
}
