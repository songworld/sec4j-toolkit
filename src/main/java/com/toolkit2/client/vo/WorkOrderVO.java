package com.toolkit2.client.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

public class WorkOrderVO extends VO
  implements Serializable
{
  private String workOrderNumber = "";

  private String lastChangeUser = "";

  private Date lastChangeDate = new Date();
  private Date adate;
  private String origin = "";

  private BigDecimal woSub = BigDecimal.ZERO;

  private BigDecimal woDisc = BigDecimal.ZERO;

  private BigDecimal subTotal = BigDecimal.ZERO;

  private BigDecimal woMade = BigDecimal.ZERO;

  private BigDecimal woOpen = BigDecimal.ZERO;

  private BigDecimal totalTax = BigDecimal.ZERO;

  private BigDecimal totalFreight = BigDecimal.ZERO;

  private BigDecimal grantTotal = BigDecimal.ZERO;

  private String changeOrder = "00";

  private String vendorId = "";

  private String billToId = "";

  private String po = "";

  private String contact = "";

  private String status = "NOT APPROVED";
  private Date approved;
  private String approvedBy = "";

  private String originator = "";

  private Date delivery = new Date(new Date().getTime() + 2592000000L);

  private Date desired = new Date(new Date().getTime() + 2592000000L);

  private int delay = 0;

  private String priority = "";

  private Date original = new Date();

  private String actual = "";

  private String shipToId = "";

  private String shipVia = "";

  private String shippingAccount = "";

  private String location = "";

  private String language = "";

  private String currency = "";

  private String terms = "";

  private Date entered = new Date();

  private String salesman = "";

  private String requestor = "";

  private String project = "";

  private String relatedDoc = "";

  private String debitAccount = "";

  private String bp = "";

  private String workOrderComments = "";

  private String workOrderPrivateNotes = "";

  private String companyUnitId = "0";

  private boolean useWip = false;

  private int lastChangeTimes = 0;
  private Set workOrderItem = new HashSet();
  private Set boilerplates = new HashSet();
  private String coComments;
  private boolean hold = false;

  public String getWorkOrderNumber()
  {
    return this.workOrderNumber;
  }

  public void setWorkOrderNumber(String workOrderNumber) {
    this.workOrderNumber = workOrderNumber;
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

  public Date getAdate() {
    return this.adate;
  }

  public void setAdate(Date adate) {
    this.adate = adate;
  }

  public String getOrigin() {
    return this.origin;
  }

  public void setOrigin(String origin) {
    this.origin = origin;
  }

  public BigDecimal getWoSub() {
    return this.woSub;
  }

  public void setWoSub(BigDecimal woSub) {
    this.woSub = woSub;
  }

  public BigDecimal getWoDisc() {
    return this.woDisc;
  }

  public void setWoDisc(BigDecimal woDisc) {
    this.woDisc = woDisc;
  }

  public BigDecimal getSubTotal() {
    return this.subTotal;
  }

  public void setSubTotal(BigDecimal subTotal) {
    this.subTotal = subTotal;
  }

  public BigDecimal getWoMade() {
    return this.woMade;
  }

  public void setWoMade(BigDecimal woMade) {
    this.woMade = woMade;
  }

  public BigDecimal getWoOpen() {
    return this.woOpen;
  }

  public void setWoOpen(BigDecimal woOpen) {
    this.woOpen = woOpen;
  }

  public BigDecimal getTotalTax() {
    return this.totalTax;
  }

  public void setTotalTax(BigDecimal totalTax) {
    this.totalTax = totalTax;
  }

  public BigDecimal getTotalFreight() {
    return this.totalFreight;
  }

  public void setTotalFreight(BigDecimal totalFreight) {
    this.totalFreight = totalFreight;
  }

  public BigDecimal getGrantTotal() {
    return this.grantTotal;
  }

  public void setGrantTotal(BigDecimal grantTotal) {
    this.grantTotal = grantTotal;
  }

  public String getChangeOrder() {
    return this.changeOrder;
  }

  public void setChangeOrder(String changeOrder) {
    this.changeOrder = changeOrder;
  }

  public String getVendorId() {
    return this.vendorId;
  }

  public void setVendorId(String vendorId) {
    this.vendorId = vendorId;
  }

  public String getBillToId() {
    return this.billToId;
  }

  public void setBillToId(String billToId) {
    this.billToId = billToId;
  }

  public String getPo() {
    return this.po;
  }

  public void setPo(String po) {
    this.po = po;
  }

  public String getContact() {
    return this.contact;
  }

  public void setContact(String contact) {
    this.contact = contact;
  }

  public String getStatus() {
    return this.status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public Date getApproved() {
    return this.approved;
  }

  public void setApproved(Date approved) {
    this.approved = approved;
  }

  public String getApprovedBy() {
    return this.approvedBy;
  }

  public void setApprovedBy(String approvedBy) {
    this.approvedBy = approvedBy;
  }

  public String getOriginator() {
    return this.originator;
  }

  public void setOriginator(String originator) {
    this.originator = originator;
  }

  public Date getDelivery() {
    return this.delivery;
  }

  public void setDelivery(Date delivery) {
    this.delivery = delivery;
  }

  public Date getDesired() {
    return this.desired;
  }

  public void setDesired(Date desired) {
    this.desired = desired;
  }

  public int getDelay() {
    return this.delay;
  }

  public void setDelay(int delay) {
    this.delay = delay;
  }

  public String getPriority() {
    return this.priority;
  }

  public void setPriority(String priority) {
    this.priority = priority;
  }

  public Date getOriginal() {
    return this.original;
  }

  public void setOriginal(Date original) {
    this.original = original;
  }

  public String getActual() {
    return this.actual;
  }

  public void setActual(String actual) {
    this.actual = actual;
  }

  public String getShipToId() {
    return this.shipToId;
  }

  public void setShipToId(String shipToId) {
    this.shipToId = shipToId;
  }

  public String getShipVia() {
    return this.shipVia;
  }

  public void setShipVia(String shipVia) {
    this.shipVia = shipVia;
  }

  public String getShippingAccount() {
    return this.shippingAccount;
  }

  public void setShippingAccount(String shippingAccount) {
    this.shippingAccount = shippingAccount;
  }

  public String getLocation() {
    return this.location;
  }

  public void setLocation(String location) {
    this.location = location;
  }

  public String getLanguage() {
    return this.language;
  }

  public void setLanguage(String language) {
    this.language = language;
  }

  public String getCurrency() {
    return this.currency;
  }

  public void setCurrency(String currency) {
    this.currency = currency;
  }

  public String getTerms() {
    return this.terms;
  }

  public void setTerms(String terms) {
    this.terms = terms;
  }

  public Date getEntered() {
    return this.entered;
  }

  public void setEntered(Date entered) {
    this.entered = entered;
  }

  public String getSalesman() {
    return this.salesman;
  }

  public void setSalesman(String salesman) {
    this.salesman = salesman;
  }

  public String getRequestor() {
    return this.requestor;
  }

  public void setRequestor(String requestor) {
    this.requestor = requestor;
  }

  public String getProject() {
    return this.project;
  }

  public void setProject(String project) {
    this.project = project;
  }

  public String getRelatedDoc() {
    return this.relatedDoc;
  }

  public void setRelatedDoc(String relatedDoc) {
    this.relatedDoc = relatedDoc;
  }

  public String getDebitAccount() {
    return this.debitAccount;
  }

  public void setDebitAccount(String debitAccount) {
    this.debitAccount = debitAccount;
  }

  public String getBp() {
    return this.bp;
  }

  public void setBp(String bp) {
    this.bp = bp;
  }

  public String getWorkOrderComments() {
    return this.workOrderComments;
  }

  public void setWorkOrderComments(String workOrderComments) {
    this.workOrderComments = workOrderComments;
  }

  public String getWorkOrderPrivateNotes() {
    return this.workOrderPrivateNotes;
  }

  public void setWorkOrderPrivateNotes(String workOrderPrivateNotes) {
    this.workOrderPrivateNotes = workOrderPrivateNotes;
  }

  public int getLastChangeTimes() {
    return this.lastChangeTimes;
  }

  public void setLastChangeTimes(int lastChangeTimes) {
    this.lastChangeTimes = lastChangeTimes;
  }

  public Set getWorkOrderItem() {
    return this.workOrderItem;
  }

  public Set getBoilerplates() {
    return this.boilerplates;
  }

  public String getCoComments() {
    return this.coComments;
  }

  public void setWorkOrderItem(Set workOrderItem) {
    this.workOrderItem = workOrderItem;
  }

  public void setBoilerplates(Set boilerplates) {
    this.boilerplates = boilerplates;
  }

  public void setCoComments(String coComments) {
    this.coComments = coComments;
  }

  public String toString() {
    return new ToStringBuilder(this).append("workOrderNumber", getWorkOrderNumber()).toString();
  }

  public boolean equals(Object other) {
    if (!(other instanceof WorkOrderVO)) {
      return false;
    }
    WorkOrderVO castOther = (WorkOrderVO)other;
    return new EqualsBuilder().append(getWorkOrderNumber(), castOther.getWorkOrderNumber()).isEquals();
  }

  public int hashCode() {
    return new HashCodeBuilder().append(getWorkOrderNumber()).toHashCode();
  }

  public String uniqueStr() {
    return getClass().getName() + "#" + getWorkOrderNumber();
  }

  public String getCompanyUnitId() {
    return this.companyUnitId;
  }

  public void setCompanyUnitId(String companyUnitId) {
    this.companyUnitId = companyUnitId;
  }

  public boolean isUseWip() {
    return this.useWip;
  }

  public void setUseWip(boolean useWip) {
    this.useWip = useWip;
  }

  public boolean isHold() {
    return this.hold;
  }

  public void setHold(boolean hold) {
    this.hold = hold;
  }
}