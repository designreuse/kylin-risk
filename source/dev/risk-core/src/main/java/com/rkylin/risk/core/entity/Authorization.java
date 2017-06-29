package com.rkylin.risk.core.entity;

import lombok.Getter;
import lombok.Setter;
import org.joda.time.LocalDate;


import java.util.List;

@Getter
@Setter
@SuppressWarnings("serial")
public class Authorization implements java.io.Serializable {

  private Short userId;

  private String username;

  private String realname;

  private String sessionId;

  private String operatorType;

  private String ipAddress;

  private String loginTime;

  private List<Role> roles;

  private List<String> resources;

  private Menu menu;

  private String[] products;

  private Integer logid;

  private LocalDate passwdexpdate;

  public Authorization() {
  }

  public Authorization(String username, String realname,
                       String operatorType, String ipAddress, String loginTime, List roles) {
    this.username = username;
    this.realname = realname;
    this.operatorType = operatorType;
    this.ipAddress = ipAddress;
    this.loginTime = loginTime;
    this.roles = roles;
  }
}
