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

  private Order order;

  private Photo photo;

  private Contact contact;

  private Integer identityType;

  private MateCardId mateCardId;

  private String contactsList;

  @JsonProperty("work_info")
  private WorkInfo workInfo;

  @JsonProperty("bank_info")
  private BankInfo bankInfo;

  @JsonProperty("education_info")
  private EducationInfo educationInfo;


  @ToString
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
    private String cardIdCompare;

    public String getCardIdCompare() {
      return cardIdCompare;
    }

    public void setCardIdCompare(String cardIdCompare) {
      this.cardIdCompare = cardIdCompare;
    }

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

  @ToString
  public class Contact{
    private String qq;
    private String firstContactName ;
    private String secondContactName ;
    private String firstContactMobile;
    private String secondContactMobile ;
    private String firstContactRelationship;
    private String secondContactRelationship;
    private String email;

    public String getEmail() {
      return email;
    }

    public void setEmail(String email) {
      this.email = email;
    }

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

  @ToString
  public class WorkInfo{
    private String unitName ;
    private String jobTitle;
    private String salary;
    private String unitAddress;
    private String unitFullAddress;
    private String currentAddress;
    private String currentFullAddress;

    public String getUnitName() {
      return unitName;
    }

    public void setUnitName(String unitName) {
      this.unitName = unitName;
    }

    public String getCurrentFullAddress() {
      return currentFullAddress;
    }

    public void setCurrentFullAddress(String currentFullAddress) {
      this.currentFullAddress = currentFullAddress;
    }

    public String getCurrentAddress() {
      return currentAddress;
    }

    public void setCurrentAddress(String currentAddress) {
      this.currentAddress = currentAddress;
    }

    public String getUnitFullAddress() {
      return unitFullAddress;
    }

    public void setUnitFullAddress(String unitFullAddress) {
      this.unitFullAddress = unitFullAddress;
    }

    public String getUnitAddress() {
      return unitAddress;
    }

    public void setUnitAddress(String unitAddress) {
      this.unitAddress = unitAddress;
    }

    public String getSalary() {
      return salary;
    }

    public void setSalary(String salary) {
      this.salary = salary;
    }

    public String getJobTitle() {
      return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
      this.jobTitle = jobTitle;
    }
  }

  @ToString
  public class BankInfo{
    private String cardNo;
    private String bankDeposit  ;
    private String bankProvince  ;
    private String bankCity;
    private String bankBranch ;
    private String bankReservePhone;
    private String checkMethod;

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

    public String getCheckMethod() {
      return checkMethod;
    }

    public void setCheckMethod(String checkMethod) {
      this.checkMethod = checkMethod;
    }
  }

  @ToString
  public class EducationInfo{
    private String province;
    private String city;
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

    public String getProvince() {
      return province;
    }

    public void setProvince(String province) {
      this.province = province;
    }

    public String getCity() {
      return city;
    }

    public void setCity(String city) {
      this.city = city;
    }
  }

  @ToString
  public class Photo{
    private String logoGroupPhoto;
    private String contractPhoto;

    public String getLogoGroupPhoto() {
      return logoGroupPhoto;
    }

    public void setLogoGroupPhoto(String logoGroupPhoto) {
      this.logoGroupPhoto = logoGroupPhoto;
    }

    public String getContractPhoto() {
      return contractPhoto;
    }

    public void setContractPhoto(String contractPhoto) {
      this.contractPhoto = contractPhoto;
    }
  }

  @ToString
  public class MateCardId{
    private String cardId;
    private String name;
    private String sex;
    private String nation;
    private String address;
    private String startDate;
    private String endDate;
    private String frontImage;
    private String reverseImage;
    private String matesMobile;
    private String photographyType;
    private String childName;
    private String childCardId;

    public String getCardId() {
      return cardId;
    }

    public void setCardId(String cardId) {
      this.cardId = cardId;
    }

    public String getName() {
      return name;
    }

    public void setName(String name) {
      this.name = name;
    }

    public String getSex() {
      return sex;
    }

    public void setSex(String sex) {
      this.sex = sex;
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

    public String getReverseImage() {
      return reverseImage;
    }

    public void setReverseImage(String reverseImage) {
      this.reverseImage = reverseImage;
    }

    public String getFrontImage() {
      return frontImage;
    }

    public void setFrontImage(String frontImage) {
      this.frontImage = frontImage;
    }

    public String getMatesMobile() {
      return matesMobile;
    }

    public void setMatesMobile(String matesMobile) {
      this.matesMobile = matesMobile;
    }

    public String getPhotographyType() {
      return photographyType;
    }

    public void setPhotographyType(String photographyType) {
      this.photographyType = photographyType;
    }

    public String getChildName() {
      return childName;
    }

    public void setChildName(String childName) {
      this.childName = childName;
    }

    public String getChildCardId() {
      return childCardId;
    }

    public void setChildCardId(String childCardId) {
      this.childCardId = childCardId;
    }
  }

  @ToString
  public class Order {
    private String orderNo;
    private String orderName;
    private String applyDate;
    private String submitCheckDate;
    private String orderScene;
    private String orderPrice;
    private String loanAmount;
    private String loanTerm;
    private String fundProvider;
    private String merchantName;
    private String merchantId;
    private String faceImage;
    private String faceCompare;
    private String longitude;
    private String latitude;

    public String getOrderNo() {
      return orderNo;
    }

    public void setOrderNo(String orderNo) {
      this.orderNo = orderNo;
    }

    public String getOrderName() {
      return orderName;
    }

    public void setOrderName(String orderName) {
      this.orderName = orderName;
    }

    public String getApplyDate() {
      return applyDate;
    }

    public void setApplyDate(String applyDate) {
      this.applyDate = applyDate;
    }

    public String getOrderScene() {
      return orderScene;
    }

    public void setOrderScene(String orderScene) {
      this.orderScene = orderScene;
    }

    public String getOrderPrice() {
      return orderPrice;
    }

    public void setOrderPrice(String orderPrice) {
      this.orderPrice = orderPrice;
    }

    public String getLoanTerm() {
      return loanTerm;
    }

    public void setLoanTerm(String loanTerm) {
      this.loanTerm = loanTerm;
    }

    public String getMerchantName() {
      return merchantName;
    }

    public void setMerchantName(String merchantName) {
      this.merchantName = merchantName;
    }

    public String getFaceImage() {
      return faceImage;
    }

    public void setFaceImage(String faceImage) {
      this.faceImage = faceImage;
    }

    public String getLatitude() {
      return latitude;
    }

    public void setLatitude(String latitude) {
      this.latitude = latitude;
    }

    public String getLongitude() {
      return longitude;
    }

    public void setLongitude(String longitude) {
      this.longitude = longitude;
    }

    public String getFaceCompare() {
      return faceCompare;
    }

    public void setFaceCompare(String faceCompare) {
      this.faceCompare = faceCompare;
    }

    public String getMerchantId() {
      return merchantId;
    }

    public void setMerchantId(String merchantId) {
      this.merchantId = merchantId;
    }

    public String getFundProvider() {
      return fundProvider;
    }

    public void setFundProvider(String fundProvider) {
      this.fundProvider = fundProvider;
    }

    public String getLoanAmount() {
      return loanAmount;
    }

    public void setLoanAmount(String loanAmount) {
      this.loanAmount = loanAmount;
    }

    public String getSubmitCheckDate() {
      return submitCheckDate;
    }

    public void setSubmitCheckDate(String submitCheckDate) {
      this.submitCheckDate = submitCheckDate;
    }
  }

}
