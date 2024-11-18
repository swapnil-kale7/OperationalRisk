package com.OperationalRisk.demo.controller;

import com.OperationalRisk.demo.Entity.Risk;
import com.OperationalRisk.demo.Entity.userr;
import com.OperationalRisk.demo.Repository.RiskRepo;
import com.OperationalRisk.demo.Repository.UserRepo;
import jakarta.servlet.http.HttpServletResponse;


import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/risks")
public class RiskController {

    @Autowired
    private RiskRepo riskRepository;

    @Autowired
    UserRepo userRepo;

    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("risk", new Risk());
        return "create-risk";
    }

    @PostMapping("/create")
    public String createRisk(@ModelAttribute Risk risk,Principal principal) {
        userr userr1=userRepo.findByUsername(principal.getName());
//        principal.auth
         risk.setCreatedBY(userr1);
        riskRepository.save(risk);
        return "redirect:/risks";
    }

    @GetMapping
    public String listRisks(Model model) {
        model.addAttribute("risks", riskRepository.findAll());
        return "list-risks";
    }

    @GetMapping("/{id}")
    public String viewRiskDetails(@PathVariable("id") Long id, Model model,Principal principal) {
        Risk risk = riskRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid risk Id:" + id));
        userr userr1=userRepo.findByUsername(principal.getName());
        model.addAttribute("principal", userr1);
        model.addAttribute("risk", risk);
        return "view-risk";
    }

    @GetMapping("/edit/{id}")
    public String showUpdateForm(@PathVariable("id") Long id, Model model, Principal principal) {
        Risk risk = riskRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid risk Id:" + id));
        userr userr= userRepo.findByUsername(principal.getName());

        if (!risk.getCreatedBY().equals(userr) && !userr.getRole().equals("ADMIN")){
            return "redirect:/risks";
        }
        model.addAttribute("principal", userr);
        model.addAttribute("risk", risk);
        return "update-risk";
    }


    @PostMapping("/update/{id}")
    public String updateRisk(@PathVariable("id") Long id, @ModelAttribute Risk risk, Principal principal) {
        userr userr=userRepo.findByUsername(principal.getName());
        Risk currRisk=riskRepository.findById(id).orElseThrow(()->new IllegalArgumentException("Invalid riks ID"+ id));

        if (!currRisk.getCreatedBY().equals(userr) && !userr.getRole().equals("ADMIN")){
            return "redirect:/risks";
        }
        risk.setCreatedBY(currRisk.getCreatedBY());
        currRisk.setName(risk.getName());
        currRisk.setDescription(risk.getDescription());
        currRisk.setSeverity(risk.getSeverity());
        currRisk.setStatus(risk.getStatus());
        currRisk.setLocation(risk.getLocation());
        currRisk.setMitigation(risk.getMitigation());

        riskRepository.save(risk);
        return "redirect:/risks";
    }



    @GetMapping("/delete/{id}")
    public String deleteRisk(@PathVariable("id") Long id, Principal principal) {
        userr userr=userRepo.findByUsername(principal.getName());
        Risk risk = riskRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid risk Id:" + id));

        if (!risk.getCreatedBY().equals(userr) && !userr.getRole().equals("ADMIN")){
            return "redirect:/risks";
        }
        riskRepository.delete(risk);
        return "redirect:/risks";
    }


    @GetMapping("/filter")
    public String filterRisks(@RequestParam(required = false) String severity,
                              @RequestParam(required = false) String location,
                              @RequestParam(required = false) String status,
                              Model model) {
        List<Risk> risks = riskRepository.findAll();

        if (severity != null && !severity.isEmpty()) {
            risks=riskRepository.findBySeverity(severity);
        }

        if (location != null && !location.isEmpty()) {
            risks = riskRepository.findByLocation(location);
        }
        if (status != null && !status.isEmpty()) {
            risks = riskRepository.findByStatus(status);
        }

        model.addAttribute("risks", risks);
        return "list-risks";
    }

    @GetMapping("/exportpage")
    public String exportpage(){

        return "ExportToExcel";
    }

    @GetMapping("/export")
    public void exportToExcel(HttpServletResponse response,
            @RequestParam(required = false) String severity,
                              @RequestParam(required = false) String location,
                              @RequestParam(required = false) String status) throws IOException {

            response.setContentType("application/octet-stream");
            String headerKey="Content-Disposition";
            String headerValue="attachment; filename=risk.xlsx";
            response.setHeader(headerKey,headerValue);

            List<Risk> risks = riskRepository.findAll();

            if (severity!=null && !severity.isEmpty() ){
                risks=riskRepository.findBySeverity(severity);
            }

            if (location!=null && !location.isEmpty() ){
                risks=riskRepository.findByLocation(location);
            }

            if (status!=null && !status.isEmpty() ){
            risks=riskRepository.findByStatus(status);
            }

            Workbook workbook=new XSSFWorkbook();
            Sheet sheet=workbook.createSheet();

        Row headerRow = sheet.createRow(0);
        String[] headers = {"ID", "Name", "Description", "Severity", "Status", "Location", "Mitigation"};
        for (int i = 0; i < headers.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(headers[i]);
        }
        int rowindex=1;
        for (Risk risk : risks){
            Row row =sheet.createRow(rowindex++);
            row.createCell(0).setCellValue(risk.getId());
            row.createCell(1).setCellValue(risk.getName());
            row.createCell(2).setCellValue(risk.getDescription());
            row.createCell(3).setCellValue(risk.getSeverity());
            row.createCell(4).setCellValue(risk.getStatus());
            row.createCell(5).setCellValue(risk.getLocation());
            row.createCell(5).setCellValue(risk.getMitigation());

        }
        workbook.write(response.getOutputStream());
        workbook.close();
    }

    @GetMapping("/uploadpage")
    public String uploadpage(){

        return "ExcelUpload";
    }


    @PostMapping("upload")
    public String uploadFile(@RequestParam("file") MultipartFile multipartFile,
                             Principal principal) throws IOException {
        Workbook workbook=new XSSFWorkbook(multipartFile.getInputStream());
        Sheet sheet=workbook.getSheetAt(0);

        for (Row row : sheet){
            if (row.getRowNum()==0){
                continue; // skip Header
            }

            Risk risk =new Risk();
            risk.setName(row.getCell(1).toString());
            risk.setDescription(row.getCell(2).getStringCellValue());
            risk.setSeverity(row.getCell(3).getStringCellValue());
            risk.setStatus(row.getCell(4).getStringCellValue());
            risk.setLocation(row.getCell(5).getStringCellValue());
            risk.setMitigation(row.getCell(6).getStringCellValue());

            userr userr=userRepo.findByUsername(principal.getName());
            risk.setCreatedBY(userr);
            riskRepository.save(risk);
        }
        workbook.close();
        return "redirect:/risks";
    }


}
