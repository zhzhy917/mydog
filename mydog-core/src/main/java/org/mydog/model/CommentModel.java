package org.mydog.model;

import java.io.Serializable;

public class CommentModel implements Serializable{
	
	private static final long serialVersionUID = 5570200331930019695L;

	private boolean suppressDate ; 
	
	private boolean suppressComment ;

	public boolean isSuppressDate() {
		return suppressDate;
	}

	public void setSuppressDate(boolean suppressDate) {
		this.suppressDate = suppressDate;
	}

	public boolean isSuppressComment() {
		return suppressComment;
	}

	public void setSuppressComment(boolean suppressComment) {
		this.suppressComment = suppressComment;
	} 
}
