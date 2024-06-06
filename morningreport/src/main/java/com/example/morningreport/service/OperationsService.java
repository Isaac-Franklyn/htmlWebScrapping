package com.example.morningreport.service;

import com.example.morningreport.model.WellInfo;
import com.example.morningreport.model.Operations;
import com.example.morningreport.repository.OperationsRepository;
import com.example.morningreport.repository.WellInfoRepository;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


@Service
public class OperationsService {
	
	@Autowired
    private OperationsRepository operationRepository;
	 @Autowired
	 private WellInfoRepository wellInfoRepository;
	 
	 public List<Operations> parseAndSaveOperations(File htmlFile) throws IOException {
	        List<Operations> operations = new ArrayList<>();
	        Document doc = Jsoup.parse(htmlFile, "UTF-8");
	        Elements rows = doc.select("table#AutoNumber2[datafldx] tbody tr");
	        

        for (Element row : rows) {
            Elements cells = row.select("td");
            if (cells.size() >= 18) {
                Operations operation = new Operations();
                operation.setHours(cells.get(0).text());
                operation.setFromTime(cells.get(1).text());
                operation.setToTime(cells.get(3).text());
                operation.setLateral(cells.get(4).text());
                operation.setHoleSize(cells.get(5).text());
                operation.setCategory(cells.get(6).text());
                operation.setMajorOperation(cells.get(7).text());
                operation.setAction(cells.get(8).text());
                operation.setObject(cells.get(9).text());
                operation.setRespCo(cells.get(10).text());
                operation.setHoleDepthStart(cells.get(11).text());
                operation.setHoleDepthEnd(cells.get(12).text());
                operation.setEventDepthStart(cells.get(13).text());
                operation.setEventDepthEnd(cells.get(14).text());
                operation.setLtType(cells.get(15).text());
                operation.setLtId(cells.get(16).text());
                operation.setSummary(cells.get(17).text());

                operations.add(operation);
            }
        }

        return operationRepository.saveAll(operations);
    }
	 
	 public List<WellInfo> parseAndSaveWellInfo(File htmlFile) throws IOException {
	        List<WellInfo> wellInfoList = new ArrayList<>();
	        Document doc = Jsoup.parse(htmlFile, "UTF-8");
	        
	        WellInfo wellInfo = new WellInfo();
	        
	        //initializing the first row
	        Elements row1 = doc.select("thead.report-header tr td[colspanx='25'] tbody tr");
	        
	        //reading the date
	        Elements dateCell = row1.select("table[datasrc][datafld] tbody tr td.l[nowrap]");
	        wellInfo.setDate(dateCell.text());
	        
	        //reading well no.
	        Elements wellCell = row1.select("td.s[width='0%'][nowrap][colspan='3']:not([datafld],[datasrc]) tbody tr td.l[colspan='3']");
	        wellInfo.setWellNo(wellCell.text());
	        
	        //reading charge
	        Elements chargeCell = row1.select("td.s[width='0%'][nowrap][colspan='3']:not([datafld],[datasrc]) tbody tr td.l span");
	        wellInfo.setCharge(chargeCell.text());
	        
	        //reading rig 
	        Elements rigCell = row1.select("td.s[width='0%'][nowrap][colspan='2']:not([datafld],[datasrc]) td.l");
	        Element firstRigCell = rigCell.first();
	        wellInfo.setRig(firstRigCell.text());
	        
	        //reading foreman
	        Elements foremanCell = row1.select("td.s[width='0%'][nowrap][colspan='2']:not([datafld],[datasrc]) td.l a#W_FMAN_NAME_ARM[style='']");
	        wellInfo.setForeman(foremanCell.text());
	      
	        
	        //reading engineer
	        Elements engineerCell = row1.select("td.s[width='0%'][nowrap][colspan='2']:not([datafld],[datasrc]) td.l a#W_DRLG_ENG_NAME[style='']");
	        wellInfo.setEngineer(engineerCell.text());
	        
	        //reading superintendent
	        Elements superintendentCell = row1.select("td.s[width='0%'][nowrap][colspan='2']:not([datafld],[datasrc]) td.l a:not([style=''],[id])");
	        wellInfo.setSuperintendent(superintendentCell.text());
	        
	        //initializing the second row
	        Elements row2 = doc.select("thead.report-header tr td[colspanx='30'] tbody tr td.s[align='left'][width='0%'][valign='top']");
	        
	        //reading depth
	        Elements depthCells = row2.select("td.l");
	        Element depthCell = depthCells.get(0);
	        wellInfo.setDepth(depthCell.text());
	        
	        //reading last 24 hours   
	        Elements last24Cells = row2.select("td.l");
	        Element last24Cell = last24Cells.get(1);
	        wellInfo.setLast24HrOperations(last24Cell.text());
	        
	        //reading next 24 hours
	        Elements next24Cells = row2.select("td.l");
	        Element next24Cell = next24Cells.get(2);
	        wellInfo.setNext24HrPlan(next24Cell.text());
	        
	        //reading location
	        Elements locationCells = row2.select("td.l");
	        Element locationCell = locationCells.get(3);
	        wellInfo.setLocation(locationCell.text());
	        
	        //reading days since spud/comm
	        Element daysSinceCell = doc.select("thead.report-header tr td[colspanx='30'] tbody tr td[align='middle'][width='0%'][valign='top'] tbody td.s span.l").first();
	        wellInfo.setDaysSinceSpud(daysSinceCell.text());
	        
	        //reading formation tops
	        Elements formationCell = doc.select("thead.report-header tr td[colspanx='30'] tbody tr td[colspan='3'][width='25%'][valign='top'] tbody td.j");
	        wellInfo.setFormationTops(formationCell.text());
	        
	        //reading circ percent
	        Element circCell = doc.select("thead.report-header tr td[colspanx='30'] tbody tr td[align='middle'][width='0%'][valign='top'] tbody td.s span.l").get(1);
	        wellInfo.setCircPercent(circCell.text());
	        
	        //reading prev depth
	        Element prevDepthCell = doc.selectFirst("thead.report-header tr td[colspanx='30'] tbody tr td[align='left'][nowrap][valign='top'] td.l[align='middle']");
	        wellInfo.setPrevDepth(prevDepthCell.text());
	        
	        wellInfoList.add(wellInfo);
	        return wellInfoRepository.saveAll(wellInfoList);
	        
 }
	 public void deleteDataOfOperations() {
		 operationRepository.deleteAll();
	 }
	 
	 public void deleteDataOfWellInfo() {
		 wellInfoRepository.deleteAll();
	 }
	 
	 public List<Operations> getDataOfOperations() {
		 return operationRepository.findAll();
	 }
	 
	 public List<WellInfo> getDataOfWellInfo() {
		 return wellInfoRepository.findAll();
	 }
}
