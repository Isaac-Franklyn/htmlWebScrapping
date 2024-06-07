package com.example.morningreport.controller;

import java.io.File;


import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.morningreport.model.MudData;
import com.example.morningreport.model.Operations;
import com.example.morningreport.model.WellInfo;
import com.example.morningreport.service.OperationsService;

@RestController
@RequestMapping("/api/readhtml")
public class OperationsController {
	
	@Autowired
    private OperationsService operationService;

    @PostMapping("/upload/operations")
    public List<Operations> uploadFile(@RequestParam("file") MultipartFile file) throws IOException {
        File convFile = new File(System.getProperty("java.io.tmpdir") + "/" + file.getOriginalFilename());
        file.transferTo(convFile);
        return operationService.parseAndSaveOperations(convFile);
    }
    
    @PostMapping("/upload/wellinfo")
    public List<WellInfo> uploadFileWell(@RequestParam("file") MultipartFile file) throws IOException {
        File convFile = new File(System.getProperty("java.io.tmpdir") + "/" + file.getOriginalFilename());
        file.transferTo(convFile);
        return operationService.parseAndSaveWellInfo(convFile);
    }
    
    @PostMapping("/upload/muddata")
    public List<MudData> uploadMudData(@RequestParam("file") MultipartFile file) throws IOException {
        File convFile = new File(System.getProperty("java.io.tmpdir") + "/" + file.getOriginalFilename());
        file.transferTo(convFile);
        return operationService.parseAndSaveMudData(convFile);
    }
    
    
    @DeleteMapping("/operations")
    public void deleteDataOP() {
    	operationService.deleteDataOfOperations();
    }
    
    @DeleteMapping("/wellinfo")
    public void deleteDataWI() {
    	operationService.deleteDataOfWellInfo();
    }
    
    @GetMapping("/operations")
    public List<Operations> getDataOfOperations() {
    	return operationService.getDataOfOperations();
    }
    
    @GetMapping("/wellinfo")
    public List<WellInfo> getDataOfWellInfo() {
    	return operationService.getDataOfWellInfo();
    }
}
