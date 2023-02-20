package ch.ike.moodtracker.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="mood")
public class MoodModel {

	@Id
	@Column(name="id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	@Column(name = "create_time")
	@Temporal(TemporalType.TIMESTAMP)
	private java.util.Date createTime;
	
	@Column(name = "personal_token")
	private String personalToken;
	
	@Column(name = "mood_value")
	private int moodValue;
	
	private String comment;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getPersonalToken() {
		return personalToken;
	}

	public void setPersonalToken(String personalToken) {
		this.personalToken = personalToken;
	}

	public int getMoodValue() {
		return moodValue;
	}

	public void setMoodValue(int moodValue) {
		this.moodValue = moodValue;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	@Override
	public String toString(){
		return "id="+id+", date="+createTime.toString()+", personalToken="+personalToken+", moodValue="+moodValue+", comment="+comment;
	}
}