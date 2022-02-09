package com.facebooklive1.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="facebooktimelinelive")
public class TimeLine {
	@ManyToOne
	private FacebookUser fl;

	public long getTimelineid() {
		return timelineid;
	}
	public void setTimelineid(long timelineid) {
		this.timelineid = timelineid;
	}
	public String getSender_id() {
		return sender_id;
	}
	public void setSender_id(String sender_id) {
		this.sender_id = sender_id;
	}
	public String getReceiver_Id() {
		return receiver_Id;
	}
	public void setReceiver_Id(String receiver_Id) {
		this.receiver_Id = receiver_Id;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getMessagelike() {
		return messagelike;
	}
	public void setMessagelike(String messagelike) {
		this.messagelike = messagelike;
	}
	public String getDislike() {
		return dislike;
	}
	public void setDislike(String dislike) {
		this.dislike = dislike;
	}
	public String getRtime() {
		return rtime;
	}
	public void setRtime(String rtime) {
		this.rtime = rtime;
	}
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	
	private long timelineid;
	private String sender_id;
	private String receiver_Id;
	private String message;
	private String messagelike;
	private String dislike;
	private String rtime;

}
