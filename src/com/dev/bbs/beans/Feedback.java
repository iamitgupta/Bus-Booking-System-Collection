package com.dev.bbs.beans;

public class Feedback {

	private int suggId;
	private int userId;
	private String feedback;

	public int getSuggId() {
		return suggId;
	}

	public void setSuggId(int suggId) {
		this.suggId = suggId;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getFeedback() {
		return feedback;
	}

	public void setFeedback(String feedback) {
		this.feedback = feedback;
	}

	@Override
	public String toString() {
		return "Feedback [suggId=" + suggId + ", userId=" + userId + ", feedback=" + feedback + "]";
	}

}
