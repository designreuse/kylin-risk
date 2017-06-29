package com.rkylin.risk.service.bean;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author qiuxian
 * @create 2016-11-04 11:35
 **/
@Setter
@Getter
@ToString
public class MitouRiskDataBean {
  private CardId cardId;
  private Contact contact;

  @JsonProperty("work_info")
  private WorkInfo workInfo;

  @JsonProperty("bank_info")
  private BankInfo bankInfo;

  @JsonProperty("education_info")
  private EducationInfo educationInfo;




  public class CardId{
    private String cardId;
    private String name;
    private String nation;
    private String address;
    private String startDate;
    private String endDate;
    private String frontImage;
    private String reverseImage;
    private String selfDeclaration;

    public String getName() {
      return name;
    }

    public void setName(String name) {
      this.name = name;
    }

    public String getCardId() {
      return cardId;
    }

    public void setCardId(String cardId) {
      this.cardId = cardId;
    }

    public String getNation() {
      return nation;
    }

    public void setNation(String nation) {
      this.nation = nation;
    }

    public String getAddress() {
      return address;
    }

    public void setAddress(String address) {
      this.address = address;
    }

    public String getStartDate() {
      return startDate;
    }

    public void setStartDate(String startDate) {
      this.startDate = startDate;
    }

    public String getEndDate() {
      return endDate;
    }

    public void setEndDate(String endDate) {
      this.endDate = endDate;
    }

    public String getFrontImage() {
      return frontImage;
    }

    public void setFrontImage(String frontImage) {
      this.frontImage = frontImage;
    }

    public String getReverseImage() {
      return reverseImage;
    }

    public void setReverseImage(String reverseImage) {
      this.reverseImage = reverseImage;
    }

    public String getSelfDeclaration() {
      return selfDeclaration;
    }

    public void setSelfDeclaration(String selfDeclaration) {
      this.selfDeclaration = selfDeclaration;
    }
  }


  public class Contact{
    private String qq;
    private String firstContactName ;
    private String secondContactName ;
    private String firstContactMobile;
    private String secondContactMobile ;
    private String firstContactRelationship;
    private String secondContactRelationship;

    public String getQq() {
      return qq;
    }

    public void setQq(String qq) {
      this.qq = qq;
    }

    public String getFirstContactName() {
      return firstContactName;
    }

    public void setFirstContactName(String firstContactName) {
      this.firstContactName = firstContactName;
    }

    public String getSecondContactName() {
      return secondContactName;
    }

    public void setSecondContactName(String secondContactName) {
      this.secondContactName = secondContactName;
    }

    public String getFirstContactMobile() {
      return firstContactMobile;
    }

    public void setFirstContactMobile(String firstContactMobile) {
      this.firstContactMobile = firstContactMobile;
    }

    public String getSecondContactMobile() {
      return secondContactMobile;
    }

    public void setSecondContactMobile(String secondContactMobile) {
      this.secondContactMobile = secondContactMobile;
    }

    public String getFirstContactRelationship() {
      return firstContactRelationship;
    }

    public void setFirstContactRelationship(String firstContactRelationship) {
      this.firstContactRelationship = firstContactRelationship;
    }

    public String getSecondContactRelationship() {
      return secondContactRelationship;
    }

    public void setSecondContactRelationship(String secondContactRelationship) {
      this.secondContactRelationship = secondContactRelationship;
    }
  }

  public class WorkInfo{
    private String workCompanyName ;

    public String getWorkCompanyName() {
      return workCompanyName;
    }

    public void setWorkCompanyName(String workCompanyName) {
      this.workCompanyName = workCompanyName;
    }

  }

  public class BankInfo{
    private String cardNo;
    private String bankDeposit  ;
    private String bankProvince  ;
    private String bankCity;
    private String bankBranch ;
    private String bankReservePhone;

    public String getCardNo() {
      return cardNo;
    }

    public void setCardNo(String cardNo) {
      this.cardNo = cardNo;
    }

    public String getBankReservePhone() {
      return bankReservePhone;
    }

    public void setBankReservePhone(String bankReservePhone) {
      this.bankReservePhone = bankReservePhone;
    }

    public String getBankBranch() {
      return bankBranch;
    }

    public void setBankBranch(String bankBranch) {
      this.bankBranch = bankBranch;
    }

    public String getBankCity() {
      return bankCity;
    }

    public void setBankCity(String bankCity) {
      this.bankCity = bankCity;
    }

    public String getBankProvince() {
      return bankProvince;
    }

    public void setBankProvince(String bankProvince) {
      this.bankProvince = bankProvince;
    }

    public String getBankDeposit() {
      return bankDeposit;
    }

    public void setBankDeposit(String bankDeposit) {
      this.bankDeposit = bankDeposit;
    }
  }

  public class EducationInfo{
    private String schoolArea;
    private String universityName  ;
    private String education;
    private String enrolDate ;

    public String getSchoolArea() {
      return schoolArea;
    }

    public void setSchoolArea(String schoolArea) {
      this.schoolArea = schoolArea;
    }

    public String getUniversityName() {
      return universityName;
    }

    public void setUniversityName(String universityName) {
      this.universityName = universityName;
    }

    public String getEducation() {
      return education;
    }

    public void setEducation(String education) {
      this.education = education;
    }

    public String getEnrolDate() {
      return enrolDate;
    }

    public void setEnrolDate(String enrolDate) {
      this.enrolDate = enrolDate;
    }
  }



}
