package com.example.morningreport.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "mud_data")
public class MudData {
	
	@Id private String id;
	private String weight;
	private String funnelVis;
	private String filtrateWL;
	private String pv;
	private String yp;
	private String electricalStability;
	private String rpm_3_6;
	private String gelsSec_Min;
	private String ph;
	private String caPPM;
	private String clPPM;
	private String flTemp;
	private String cakeHTHP;
	private String cakeAPI;
	private String waterVolPercent;
	private String oilVolPercent;
	private String solidPercent;
	private String sandVolPercent;
	private String percentLGS;
	private String mBT;
	private String mudType;
	private String waterWell;
	
	public String getSolidPercent() {
		return solidPercent;
	}
	public void setSolidPercent(String solidPercent) {
		this.solidPercent = solidPercent;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getWeight() {
		return weight;
	}
	public void setWeight(String weight) {
		this.weight = weight;
	}
	public String getFunnelVis() {
		return funnelVis;
	}
	public void setFunnelVis(String funnelVis) {
		this.funnelVis = funnelVis;
	}
	public String getFiltrateWL() {
		return filtrateWL;
	}
	public void setFiltrateWL(String filtrateWL) {
		this.filtrateWL = filtrateWL;
	}
	public String getPv() {
		return pv;
	}
	public void setPv(String pv) {
		this.pv = pv;
	}
	public String getYp() {
		return yp;
	}
	public void setYp(String yp) {
		this.yp = yp;
	}
	public String getElectricalStability() {
		return electricalStability;
	}
	public void setElectricalStability(String electricalStability) {
		this.electricalStability = electricalStability;
	}
	public String getRpm_3_6() {
		return rpm_3_6;
	}
	public void setRpm_3_6(String rpm_3_6) {
		this.rpm_3_6 = rpm_3_6;
	}
	public String getGelsSec_Min() {
		return gelsSec_Min;
	}
	public void setGelsSec_Min(String gelsSec_Min) {
		this.gelsSec_Min = gelsSec_Min;
	}
	public String getPh() {
		return ph;
	}
	public void setPh(String ph) {
		this.ph = ph;
	}
	public String getCaPPM() {
		return caPPM;
	}
	public void setCaPPM(String caPPM) {
		this.caPPM = caPPM;
	}
	public String getClPPM() {
		return clPPM;
	}
	public void setClPPM(String clPPM) {
		this.clPPM = clPPM;
	}
	public String getFlTemp() {
		return flTemp;
	}
	public void setFlTemp(String flTemp) {
		this.flTemp = flTemp;
	}
	public String getCakeHTHP() {
		return cakeHTHP;
	}
	public void setCakeHTHP(String cakeHTHP) {
		this.cakeHTHP = cakeHTHP;
	}
	public String getCakeAPI() {
		return cakeAPI;
	}
	public void setCakeAPI(String cakeAPI) {
		this.cakeAPI = cakeAPI;
	}
	public String getWaterVolPercent() {
		return waterVolPercent;
	}
	public void setWaterVolPercent(String waterVolPercent) {
		this.waterVolPercent = waterVolPercent;
	}
	public String getOilVolPercent() {
		return oilVolPercent;
	}
	public void setOilVolPercent(String oilVolPercent) {
		this.oilVolPercent = oilVolPercent;
	}
	public String getSandVolPercent() {
		return sandVolPercent;
	}
	public void setSandVolPercent(String sandVolPercent) {
		this.sandVolPercent = sandVolPercent;
	}
	public String getPercentLGS() {
		return percentLGS;
	}
	public void setPercentLGS(String percentLGS) {
		this.percentLGS = percentLGS;
	}
	public String getmBT() {
		return mBT;
	}
	public void setmBT(String mBT) {
		this.mBT = mBT;
	}
	public String getMudType() {
		return mudType;
	}
	public void setMudType(String mudType) {
		this.mudType = mudType;
	}
	public String getWaterWell() {
		return waterWell;
	}
	public void setWaterWell(String waterWell) {
		this.waterWell = waterWell;
	}
	
	

}
