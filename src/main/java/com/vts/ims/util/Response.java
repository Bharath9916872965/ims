package com.vts.ims.util;

public class Response {
  private String message;
  private String status;

  public Response(String message) {
    this.message = message;
  }

  public String getMessage() {
    return message;
  }
  
  public String getStatus() {
		return status;
	
  }

  public void setStatus(String status) {
		this.status = status;
  }

  public void setMessage(String message) {
    this.message = message;
  }

public Response(String message, String status) {
	super();
	this.message = message;
	this.status = status;
}

}
