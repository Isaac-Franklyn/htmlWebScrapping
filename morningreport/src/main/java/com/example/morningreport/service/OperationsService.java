package com.example.morningreport.service;

import com.example.morningreport.model.WellInfo;
import com.example.morningreport.model.MudData;
import com.example.morningreport.model.Operations;
import com.example.morningreport.repository.MudDataRepository;
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
	@Autowired
	private MudDataRepository mudDataRepository;

	public List<Operations> parseAndSaveOperations(File htmlFile) throws IOException {
		List<Operations> operations = new ArrayList<>();
		Document doc = Jsoup.parse(htmlFile, "UTF-8");
		Elements tbodys = doc
				.select("table.report-container table[datasrcx='#data'][datafldx='DRLG_OP_SMRY'] tbody tr");

		for (Element tbody : tbodys) {

			Operations operation = new Operations();
			
			if (!tbody.select("td.cell1x[align='right']").isEmpty()) {
				Element firstCell1x = tbody.select("td.cell1x[align='right']").first();
				operation.setHours(firstCell1x.text());
				
				String fromTime = tbody.select("td.cell2x[align='right']").text();
				String hyphen = " -";
				String toTime = tbody.select("td.cell1x[align='right']").get(1).text();
			
				String time = fromTime.concat(hyphen).concat(toTime);
						operation.setFromToTime(time);
			}
			if(!(tbody.select("td.cell2x[align='left']").isEmpty())) {
				operation.setLateral(tbody.select("td.cell2x[align='left']").get(0).text());
				operation.setHoleSize(tbody.select("td.cell2x[align='left']").get(1).text());
				operation.setCategory(tbody.select("td.cell2x[align='left']").get(2).text());
				operation.setMajorOperation(tbody.select("td.cell2x[align='left']").get(3).text());
				operation.setAction(tbody.select("td.cell2x[align='left']").get(4).text());
				operation.setObject(tbody.select("td.cell2x[align='left']").get(5).text());
				operation.setRespCo(tbody.select("td.cell2x[align='left']").get(6).text());
				operation.setHoleDepthStart(tbody.select("td.cell2x[align='left']").get(7).text());
				operation.setHoleDepthEnd(tbody.select("td.cell2x[align='left']").get(8).text());
				
				// setting event depth start
				if (!(tbody.select("td.cell2x[align='left']").get(9).text() == null)) {
					operation.setEventDepthStart(tbody.select("td.cell2x[align='left']").get(9).text());
				} else
					operation.setEventDepthStart("");

				// setting event depth end
				if (!(tbody.select("td.cell2x[align='left']").get(10).text() == null)) {
					operation.setEventDepthEnd(tbody.select("td.cell2x[align='left']").get(10).text());
				} else
					operation.setEventDepthEnd("");

				// setting lt type
				if (!(tbody.select("td.cell2x[align='left']").get(11).text() == null)) {
					operation.setLtType(tbody.select("td.cell2x[align='left']").get(11).text());
				} else
					operation.setLtType("");

				// setting lt id
				if (!(tbody.select("td.cell2x[align='left']").get(12).text() == null)) {
					operation.setLtId(tbody.select("td.cell2x[align='left']").get(12).text());
				} else
					operation.setLtId("");

				operation.setSummary(tbody.select("td.cell2x[align='left']").get(13).text());

				operations.add(operation);

			}	
		}

		return operationRepository.saveAll(operations);
	}

	public List<WellInfo> parseAndSaveWellInfo(File htmlFile) throws IOException {
		List<WellInfo> wellInfoList = new ArrayList<>();
		Document doc = Jsoup.parse(htmlFile, "UTF-8");

		WellInfo wellInfo = new WellInfo();

		// initializing the first row
		Elements row1 = doc.select("thead.report-header tr td[colspanx='25'] tbody tr");

		// reading the date
		Elements dateCell = row1.select("table[datasrc][datafld] tbody tr td.l[nowrap]");
		wellInfo.setDate(dateCell.text());

		// reading well no.
		Elements wellCell = row1
				.select("td.s[width='0%'][nowrap][colspan='3']:not([datafld],[datasrc]) tbody tr td.l[colspan='3']");
		wellInfo.setWellNo(wellCell.text());

		// reading charge
		Elements chargeCell = row1
				.select("td.s[width='0%'][nowrap][colspan='3']:not([datafld],[datasrc]) tbody tr td.l span");
		wellInfo.setCharge(chargeCell.text());

		// reading rig
		Elements rigCell = row1.select("td.s[width='0%'][nowrap][colspan='2']:not([datafld],[datasrc]) td.l");
		Element firstRigCell = rigCell.first();
		wellInfo.setRig(firstRigCell.text());

		// reading foreman
		Elements foremanCell = row1.select(
				"td.s[width='0%'][nowrap][colspan='2']:not([datafld],[datasrc]) td.l a#W_FMAN_NAME_ARM[style='']");
		wellInfo.setForeman(foremanCell.text());

		// reading engineer
		Elements engineerCell = row1.select(
				"td.s[width='0%'][nowrap][colspan='2']:not([datafld],[datasrc]) td.l a#W_DRLG_ENG_NAME[style='']");
		wellInfo.setEngineer(engineerCell.text());

		// reading superintendent
		Elements superintendentCell = row1
				.select("td.s[width='0%'][nowrap][colspan='2']:not([datafld],[datasrc]) td.l a:not([style=''],[id])");
		wellInfo.setSuperintendent(superintendentCell.text());

		// initializing the second row
		Elements row2 = doc.select(
				"thead.report-header tr td[colspanx='30'] tbody tr td.s[align='left'][width='0%'][valign='top']");

		// reading depth
		Elements depthCells = row2.select("td.l");
		Element depthCell = depthCells.get(0);
		wellInfo.setDepth(depthCell.text());

		// reading last 24 hours
		Elements last24Cells = row2.select("td.l");
		Element last24Cell = last24Cells.get(1);
		wellInfo.setLast24HrOperations(last24Cell.text());

		// reading next 24 hours
		Elements next24Cells = row2.select("td.l");
		Element next24Cell = next24Cells.get(2);
		wellInfo.setNext24HrPlan(next24Cell.text());

		// reading location
		Elements locationCells = row2.select("td.l");
		Element locationCell = locationCells.get(3);
		wellInfo.setLocation(locationCell.text());

		// reading days since spud/comm
		Element daysSinceCell = doc.select(
				"thead.report-header tr td[colspanx='30'] tbody tr td[align='middle'][width='0%'][valign='top'] tbody td.s span.l")
				.first();
		wellInfo.setDaysSinceSpud(daysSinceCell.text());

		// reading formation tops
		Elements formationCell = doc.select(
				"thead.report-header tr td[colspanx='30'] tbody tr td[colspan='3'][width='25%'][valign='top'] tbody td.j");
		wellInfo.setFormationTops(formationCell.text());

		// reading circle percent
		Element circCell = doc.select(
				"thead.report-header tr td[colspanx='30'] tbody tr td[align='middle'][width='0%'][valign='top'] tbody td.s span.l")
				.get(1);
		wellInfo.setCircPercent(circCell.text());

		// reading prev depth
		Element prevDepthCell = doc.selectFirst(
				"thead.report-header tr td[colspanx='30'] tbody tr td[align='left'][nowrap][valign='top'] td.l[align='middle']");
		wellInfo.setPrevDepth(prevDepthCell.text());

		wellInfoList.add(wellInfo);
		return wellInfoRepository.saveAll(wellInfoList);

	}
	
	public List<MudData> parseAndSaveMudData(File htmlFile) throws IOException {
		
		List<MudData> muds = new ArrayList<>();
		Document doc = Jsoup.parse(htmlFile, "UTF-8");
		
		Elements trs = doc.select("table.report-container tbody tr:nth-child(2) table#AutoNumber2[datasrc][datafld='MUD_DETL'] > tbody > tr");
			
			for ( int i = 0; i < 2; i ++) {
				int index = i*2;
				
				Element firstRow = trs.get(index);
				Element secondRow = trs.get(index +1);
				
				MudData mud = new MudData();
				
				//reading weight
				Elements weightCell1 = firstRow.select("td.cell1x tbody td.l");
				Elements weightCell2 = firstRow.select("td.cell1x tbody td.s");
				mud.setWeight(weightCell1.text().concat(" ").concat(weightCell2.text()));
				
				//reading fl temp
				Elements tempCell1 = secondRow.select("td.cell1x tbody td.l");
				Elements tempCell2 = secondRow.select("td.cell1x tbody td.s");
				mud.setFlTemp(tempCell1.text().concat(" ").concat(tempCell2.text()));
				
				
				
				Elements td = firstRow.select("td.cell2x");
				Elements td2 = secondRow.select("td.cell2x");
				
				if(!td.isEmpty()) {
					//reading Funnel Vision
					Elements funnelCell1 = td.get(0).select("td.l");
					Elements funnelCell2 = td.get(0).select("td.s");
					mud.setFunnelVis(funnelCell1.text().concat(" ").concat(funnelCell2.text()));
					
					//reading filtrateWL
					Elements filtrateCell1 = td.get(1).select("td.l");
					Elements filterateCell2 = td.get(1).select("td.s");
					mud.setFiltrateWL(filtrateCell1.text().concat(" ").concat(filterateCell2.text()));
					
					//reading pv
					Elements pvCell1 = td.get(3).select("td.l");
					Elements pvCell2 = td.get(3).select("td.s");
					mud.setPv(pvCell1.text().concat(" ").concat(pvCell2.text()));;
					
					//reading yp
					Elements ypCell1 = td.get(4).select("td.l");
					Elements ypCell2 = td.get(4).select("td.s");
					mud.setYp(ypCell1.text().concat(" ").concat(ypCell2.text()));
					
					//reading electricalstabilty
					Elements elecCell1 = td.get(5).select("td.l");
					Elements elecCell2 = td.get(5).select("td.s");
					mud.setElectricalStability(elecCell1.text().concat(" ").concat(elecCell2.text()));
					
					//reading rpm
					Elements rpmCell1 = td.get(6).select("td.l");
					Elements rpmCell2 = td.get(6).select("td.s");
					mud.setRpm_3_6(rpmCell1.text().concat(" ").concat(rpmCell2.text()));
					
					//reading gels
					Elements gelCell1 = td.get(7).select("td.l[align='right']");
					Elements gelCell2 = td.get(7).select("td.l[classx='s']");
					mud.setGelsSec_Min(gelCell1.text().concat("/").concat(gelCell2.text()));
					
					//reading ph
					Elements phCell1 = td.get(8).select("td.l");
					Elements phCell2 = td.get(8).select("td.s");
					mud.setPh(phCell1.text().concat(" ").concat(phCell2.text()));
					
					//reading CAPPM
					Elements caCell1 = td.get(9).select("td.l");
					Elements caCell2 = td.get(9).select("td.s");
					mud.setCaPPM(caCell1.text().concat(" ").concat(caCell2.text()));
					
					//reading CLPPM
					Elements clCell1 = td.get(10).select("td.l");
					Elements clCell2 = td.get(10).select("td.s");
					mud.setClPPM(clCell1.text().concat(" ").concat(clCell2.text()));
					
					//reading 
					
					
				}
				
				if(!td2.isEmpty()) {
					//reading cake hthp
					Elements cakeCell1 = td2.get(0).select("td.l");
					Elements cakeCell2 = td2.get(0).select("td.s");
					mud.setCakeHTHP(cakeCell1.text().concat(" ").concat(cakeCell2.text()));
					
					//reading cake API
					Elements apiCell1 = td2.get(1).select("td.l");
					Elements apiCell2 = td2.get(1).select("td.s");
					mud.setCakeAPI(apiCell1.text().concat(" ").concat(apiCell2.text()));
					
					//reading water volume
					Elements waterCell1 = td2.get(2).select("td.l");
					Elements waterCell2 = td2.get(2).select("td.s");
					mud.setWaterVolPercent(waterCell1.text().concat(" ").concat(waterCell2.text()));
					
					//reading oil
					Elements oilCell1 = td2.get(3).select("td.l");
					Elements oilCell2 = td2.get(3).select("td.s");
					mud.setOilVolPercent(oilCell1.text().concat(" ").concat(oilCell2.text()));
					
					//reading solids
					Elements solidCell1 = td2.get(4).select("td.l");
					Elements solidCell2 = td2.get(4).select("td.s");
					mud.setSolidPercent(solidCell1.text().concat(" ").concat(solidCell2.text()));
					
					//reading sand volume percent
					Elements sandCell1 = td2.get(5).select("td.l");
					Elements sandCell2 = td2.get(5).select("td.s");
					mud.setSandVolPercent(sandCell1.text().concat(" ").concat(sandCell2.text()));
					
					//reading lgs
					Elements lgsCell1 = td2.get(6).select("td.l");
					Elements lgsCell2 = td2.get(6).select("td.s");
					mud.setPercentLGS(lgsCell1.text().concat(" ").concat(lgsCell2.text()));
					
					//reading mbt
					Elements mbtCell1 = td2.get(7).select("td.l");
					Elements mbtCell2 = td2.get(7).select("td.s");
					mud.setmBT(mbtCell1.text().concat(" ").concat(mbtCell2.text()));
					
					//reading mud type
					Elements mudCell1 = td2.get(8).select("td.l");
					Elements mudCell2 = td2.get(8).select("td.s");
					mud.setMudType(mudCell1.text().concat(" ").concat(mudCell2.text()));
					
					//reading water well
					Elements wellCell1 = td2.get(9).select("td.l");
					Elements wellCell2 = td2.get(9).select("td.s");
					mud.setWaterWell(wellCell1.text().concat(" ").concat(wellCell2.text()));
					
					
					
				}
				muds.add(mud);
			
		}
		
			
			
		

		return mudDataRepository.saveAll(muds);
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
