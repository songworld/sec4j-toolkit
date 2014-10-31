package com.toolkit2.client.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

public class RefdocVO extends VO
  implements Serializable
{
  /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
private Long ii;
  private String forderNumber = "";

  private String fitemNumber = "";

  private String torderNumber = "";

  private String titemNumber = "";

  private BigDecimal qty = BigDecimal.ZERO;

  private String lastChangeUser = "";

  private Date lastChangeDate = new Date();

  private Integer lastChangeTime = Integer.valueOf(0);

  private short linkOrRef = 1;

  public RefdocVO(String forderNumber, String fitemNumber, String torderNumber, String titemNumber, BigDecimal qty, String lastChangeUser, Date lastChangeDate, Integer lastChangeTime, short linkRef)
  {
    this.forderNumber = forderNumber;
    this.fitemNumber = fitemNumber;
    this.torderNumber = torderNumber;
    this.titemNumber = titemNumber;
    this.qty = qty;
    this.lastChangeUser = lastChangeUser;
    this.lastChangeDate = lastChangeDate;
    this.lastChangeTime = lastChangeTime;
    this.linkOrRef = linkRef;
  }

  public RefdocVO()
  {
  }

  public Long getIi() {
    return this.ii;
  }

  public void setIi(Long ii) {
    this.ii = ii;
  }

  public String getForderNumber() {
    return this.forderNumber;
  }

  public void setForderNumber(String forderNumber) {
    this.forderNumber = forderNumber;
  }

  public String getFitemNumber() {
    return this.fitemNumber;
  }

  public void setFitemNumber(String fitemNumber) {
    this.fitemNumber = fitemNumber;
  }

  public String getTorderNumber() {
    return this.torderNumber;
  }

  public void setTorderNumber(String torderNumber) {
    this.torderNumber = torderNumber;
  }

  public String getTitemNumber() {
    return this.titemNumber;
  }

  public void setTitemNumber(String titemNumber) {
    this.titemNumber = titemNumber;
  }

  public BigDecimal getQty() {
    return this.qty;
  }

  public void setQty(BigDecimal qty) {
    this.qty = qty;
  }

  public String getLastChangeUser() {
    return this.lastChangeUser;
  }

  public void setLastChangeUser(String lastChangeUser) {
    this.lastChangeUser = lastChangeUser;
  }

  public Date getLastChangeDate() {
    return this.lastChangeDate;
  }

  public void setLastChangeDate(Date lastChangeDate) {
    this.lastChangeDate = lastChangeDate;
  }

  public Integer getLastChangeTime() {
    return this.lastChangeTime;
  }

  public void setLastChangeTime(Integer lastChangeTime) {
    this.lastChangeTime = lastChangeTime;
  }

  public short getLinkOrRef() {
    return this.linkOrRef;
  }

  public void setLinkOrRef(short linkOrRef) {
    this.linkOrRef = linkOrRef;
  }

  public String toString() {
    return new ToStringBuilder(this).append("ii", getIi()).append("forderNumber", getForderNumber()).append("fitemNumber", getFitemNumber()).append("torderNumber", getTorderNumber()).append("titemNumber", getTitemNumber()).toString();
  }

  public boolean equals(Object other)
  {
    if (this == other) {
      return true;
    }
    if (!(other instanceof RefdocVO)) {
      return false;
    }
    RefdocVO castOther = (RefdocVO)other;
    return new EqualsBuilder().append(getForderNumber(), castOther.getForderNumber()).append(getFitemNumber(), castOther.getFitemNumber()).append(getTorderNumber(), castOther.getTorderNumber()).append(getTitemNumber(), castOther.getTitemNumber()).isEquals();
  }

  public int hashCode()
  {
    return new HashCodeBuilder().append(getForderNumber()).append(getFitemNumber()).append(getTorderNumber()).append(getTitemNumber()).toHashCode();
  }
}
