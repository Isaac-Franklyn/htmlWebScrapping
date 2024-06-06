package com.example.morningreport.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "morningreport")
public class Operations {
	
	@Id
    private String id;
    private String hours;
    private String fromTime;
    private String toTime;
    private String lateral;
    private String holeSize;
    private String category;
    private String majorOperation;
    private String action;
    private String object;
    private String respCo;
    private String holeDepthStart;
    private String holeDepthEnd;
    private String eventDepthStart;
    private String eventDepthEnd;
    private String ltType;
    private String ltId;
    private String summary;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getHours() {
		return hours;
	}
	public void setHours(String hours) {
		this.hours = hours;
	}
	public String getFromTime() {
		return fromTime;
	}
	public void setFromTime(String fromTime) {
		this.fromTime = fromTime;
	}
	public String getToTime() {
		return toTime;
	}
	public void setToTime(String toTime) {
		this.toTime = toTime;
	}
	public String getLateral() {
		return lateral;
	}
	public void setLateral(String lateral) {
		this.lateral = lateral;
	}
	public String getHoleSize() {
		return holeSize;
	}
	public void setHoleSize(String holeSize) {
		this.holeSize = holeSize;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getMajorOperation() {
		return majorOperation;
	}
	public void setMajorOperation(String majorOperation) {
		this.majorOperation = majorOperation;
	}
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	public String getObject() {
		return object;
	}
	public void setObject(String object) {
		this.object = object;
	}
	public String getRespCo() {
		return respCo;
	}
	public void setRespCo(String respCo) {
		this.respCo = respCo;
	}
	public String getHoleDepthStart() {
		return holeDepthStart;
	}
	public void setHoleDepthStart(String holeDepthStart) {
		this.holeDepthStart = holeDepthStart;
	}
	public String getHoleDepthEnd() {
		return holeDepthEnd;
	}
	public void setHoleDepthEnd(String holeDepthEnd) {
		this.holeDepthEnd = holeDepthEnd;
	}
	public String getEventDepthStart() {
		return eventDepthStart;
	}
	public void setEventDepthStart(String eventDepthStart) {
		this.eventDepthStart = eventDepthStart;
	}
	public String getEventDepthEnd() {
		return eventDepthEnd;
	}
	public void setEventDepthEnd(String eventDepthEnd) {
		this.eventDepthEnd = eventDepthEnd;
	}
	public String getLtType() {
		return ltType;
	}
	public void setLtType(String ltType) {
		this.ltType = ltType;
	}
	public String getLtId() {
		return ltId;
	}
	public void setLtId(String ltId) {
		this.ltId = ltId;
	}
	public String getSummary() {
		return summary;
	}
	public void setSummary(String summary) {
		this.summary = summary;
	}
    
    


}
