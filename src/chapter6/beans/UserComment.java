package chapter6.beans;

import java.io.Serializable;
import java.util.Date;

public class UserComment implements Serializable {

	private int commentId;
	private String commentText;
	private int commentUserId;
	private int commentMessageId;
	private Date commentCreatedDate;
	private String userAccount;
	private String userName;

	private static final long serialVersionUID = 1L;
	public int getCommentId() {
		return commentId;
	}
	public void setCommentId(int commentId) {
		this.commentId = commentId;
	}
	public String getCommentText() {
		return commentText;
	}
	public void setCommentText(String commentText) {
		this.commentText = commentText;
	}
	public int getCommentUserId() {
		return commentUserId;
	}
	public void setCommentUserId(int commentUserId) {
		this.commentUserId = commentUserId;
	}
	public int getCommentMessageId() {
		return commentMessageId;
	}
	public void setCommentMessageId(int commentMessageId) {
		this.commentMessageId = commentMessageId;
	}
	public Date getCommentCreatedDate() {
		return commentCreatedDate;
	}
	public void setCommentCreatedDate(Date commentCreatedDate) {
		this.commentCreatedDate = commentCreatedDate;
	}
	public String getUserAccount() {
		return userAccount;
	}
	public void setUserAccount(String userAccount) {
		this.userAccount = userAccount;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
}
