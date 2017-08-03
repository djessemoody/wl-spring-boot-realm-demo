package com.moodycode.MockObject;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.Date;
import java.util.Map;

/***
 *  MOODYCODE.COM DEMO
 *  EXAMPLE JSON RETURN TYPE
 */
public class Returner {



  private int version;

  @JsonFormat(pattern="yyyy-MM-dd'T'HH:mm:ss.SSSZ")
  private Date updateTime = new Date();

  private Map<String,Object> response;

  public int getVersion() {
    return version;
  }

  public void setVersion(int version) {
    this.version = version;
  }

  public Date getUpdateTime() {
    return updateTime;
  }

  public void setUpdateTime(Date updateTime) {
    this.updateTime = updateTime;
  }

  public Map<String,Object> getResponse() {
    return response;
  }

  public void setResponse(Map<String,Object> response) {
    this.response = response;
  }
}

