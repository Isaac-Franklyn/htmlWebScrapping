package com.example.morningreport.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "well_info")
	public class WellInfo {
	    @Id
	    private String id;
	    private String date;
	    private String wellNo;
	    private String rig;
	    private String charge;
	    private String foreman;
	    private String engineer;
	    private String superintendent;
	    private String depth;
	    private String prevDepth;
	    private String last24HrOperations;
	    private String next24HrPlan;
	    private String location;
	    private String daysSinceSpud;
	    private String formationTops;
	    private String circPercent;
	    
	    public String getCharge() {
			return charge;
		}
		public void setCharge(String charge) {
			this.charge = charge;
		}
		public String getEngineer() {
			return engineer;
		}
		public void setEngineer(String engineer) {
			this.engineer = engineer;
		}
		public String getSuperintendent() {
			return superintendent;
		}
		public void setSuperintendent(String superintendent) {
			this.superintendent = superintendent;
		}
		
		public String getId() {
			return id;
		}
		public void setId(String id) {
			this.id = id;
		}
		public String getDate() {
			return date;
		}
		public void setDate(String date) {
			this.date = date;
		}
		public String getWellNo() {
			return wellNo;
		}
		public void setWellNo(String wellNo) {
			this.wellNo = wellNo;
		}
		public String getRig() {
			return rig;
		}
		public void setRig(String rig) {
			this.rig = rig;
		}
		public String getForeman() {
			return foreman;
		}
		public void setForeman(String foreman) {
			this.foreman = foreman;
		}
		public String getDepth() {
			return depth;
		}
		public void setDepth(String depth) {
			this.depth = depth;
		}
		public String getLast24HrOperations() {
			return last24HrOperations;
		}
		public void setLast24HrOperations(String last24HrOperations) {
			this.last24HrOperations = last24HrOperations;
		}
		public String getNext24HrPlan() {
			return next24HrPlan;
		}
		public void setNext24HrPlan(String next24HrPlan) {
			this.next24HrPlan = next24HrPlan;
		}
		public String getLocation() {
			return location;
		}
		public void setLocation(String location) {
			this.location = location;
		}
		public String getDaysSinceSpud() {
			return daysSinceSpud;
		}
		public void setDaysSinceSpud(String daysSinceSpud) {
			this.daysSinceSpud = daysSinceSpud;
		}
		public String getFormationTops() {
			return formationTops;
		}
		public void setFormationTops(String formationTops) {
			this.formationTops = formationTops;
		}
		public String getPrevDepth() {
			return prevDepth;
		}
		public void setPrevDepth(String prevDepth) {
			this.prevDepth = prevDepth;
		}
		
		public String getCircPercent() {
			return circPercent;
		}
		public void setCircPercent(String circPercent) {
			this.circPercent = circPercent;
		}
	    
}
