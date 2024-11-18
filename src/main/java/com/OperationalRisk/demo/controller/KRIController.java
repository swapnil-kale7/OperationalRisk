package com.OperationalRisk.demo.controller;

import com.OperationalRisk.demo.Entity.KRI;
import com.OperationalRisk.demo.Service.KriService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/kris")
public class KRIController {

    @Autowired
    private KriService kriService;

    @GetMapping
    public String viewKRIs(Model model) {
        List<KRI> kris = kriService.getAllKris();
        model.addAttribute("kris", kris);
        return "kri_list";
    }

    @PostMapping
    public String addKRI(@ModelAttribute KRI kri) {
        kri.setDateRecorded(new Date());
        kriService.saveKRI(kri);
        return "redirect:/kris";
    }

    @GetMapping("/new")
    public String showAddKRIForm(Model model) {
        model.addAttribute("kri", new KRI());
        return "add_kri";
    }

    @GetMapping("/report")
    public String viewKRIReport(@RequestParam String riskType,
                                @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
                                @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate,
                                Model model) {
        List<KRI> kris = kriService.getKRIsByRiskTypeAndDateRange(riskType, startDate, endDate);
        model.addAttribute("kris", kris);
        double aggregateScore = kriService.calculateAggregateKriScore(kris);
        model.addAttribute("riskType", riskType);
        model.addAttribute("startDate", startDate);
        model.addAttribute("endDate", endDate);
        model.addAttribute("kris", kris);
        model.addAttribute("aggregateScore", aggregateScore);
        return "kri_report";
    }

    @GetMapping("/dashboard")
    public String viewKRIDashboard(Model model) {
        List<KRI> kris = kriService.getAllKris();
        SimpleDateFormat isoFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        // Collect data for chart
        List<String> dateLabels = new ArrayList<>();
        List<Double> failedTradesData = new ArrayList<>();
        List<Double> turnoverRateData = new ArrayList<>();
        List<Double> errorFrequencyData = new ArrayList<>();
        List<Double> errorSeverityData = new ArrayList<>();

        for (KRI kri : kris) {
            if (kri.getDateRecorded() != null) {
                dateLabels.add(isoFormat.format(kri.getDateRecorded()));
            }else { dateLabels.add(""); }// Handle null dates


                if (kri.getName().equals("Failed Trades")) {
                    failedTradesData.add(kri.getValue());
                } else if (kri.getName().equals("Staff Turnover Rate")) {
                    turnoverRateData.add(kri.getValue());
                } else if (kri.getName().equals("Error Frequency")) {
                    errorFrequencyData.add(kri.getValue());
                } else if (kri.getName().equals("Error Severity")) {
                    errorSeverityData.add(kri.getValue());
                }




        }
        System.out.println("failedTradesData" +failedTradesData);
        System.out.println("dateLabels"+dateLabels);
        System.out.println(turnoverRateData);



        model.addAttribute("dateLabels", dateLabels);
        model.addAttribute("failedTradesData", failedTradesData);
        model.addAttribute("turnoverRateData", turnoverRateData);
        model.addAttribute("errorFrequencyData", errorFrequencyData);
        model.addAttribute("errorSeverityData", errorSeverityData);

        return "kri_dashboard";
    }

}



