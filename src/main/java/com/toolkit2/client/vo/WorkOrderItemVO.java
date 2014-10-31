package com.toolkit2.client.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

public class WorkOrderItemVO extends VO
  implements Serializable
{
  private WorkOrderItemPK pk;
  private boolean masterWorkOrder = false;
  private String relatedDoc = "";

  private String setId = "";

  private BigDecimal woSub = BigDecimal.ZERO;

  private BigDecimal woDisc = BigDecimal.ZERO;

  private BigDecimal extendPrice = BigDecimal.ZERO;

  private BigDecimal woMade = BigDecimal.ZERO;

  private BigDecimal woOpen = BigDecimal.ZERO;

  private BigDecimal woTax = BigDecimal.ZERO;

  private BigDecimal woTotal = BigDecimal.ZERO;

  private String referenceDoc1 = "";

  private String referenceDoc2 = "";

  private String changeOrder = "00";

  private String qr = "";

  private String status = "NOT APPROVED";

  private String mfgStatus = "NOT SETUP";

  private Date startDate = new Date();

  private Date dateRequired = new Date();

  private Date desired = new Date();

  private int delayDays = 0;

  private String priority = "";

  private Date original = new Date();

  private Date actual = null;

  private String location = "";

  private String shipVia = "";

  private BigDecimal freight = BigDecimal.ZERO;

  private String partNumber = "";

  private String partType = "P/A";

  private String partFrom = "INVENTORY";

  private String partUnit = "EA";

  private String modelNumber = "";

  private String revision = "";

  private String dwg = "";

  private String wiFlag = "";

  private BigDecimal quantity = BigDecimal.ZERO;

  private BigDecimal quantityAsm = BigDecimal.ZERO;

  private BigDecimal hqa = BigDecimal.ZERO;

  private BigDecimal costPerUnit = BigDecimal.ZERO;
  private BigDecimal stdCostPerUnit = BigDecimal.ZERO;

  private String unit = "EA";

  private BigDecimal list = BigDecimal.ZERO;

  private BigDecimal discount = BigDecimal.ZERO;

  private BigDecimal taxRate = BigDecimal.ZERO;

  private String description = "";

  private String mfg = "";

  private String mpn = "";
  private String mpnRev;
  private String workOrderItemComments = "";

  private String workOrderItemPrivateNotes = "";

  private boolean costAlarm = false;
  private Date reviewedDate;
  private String reviewedBy = "";
  private String reviewDescription = "";
  private String hold = "";
  private BigDecimal originalCost = BigDecimal.ZERO;
  private BigDecimal overPercent = BigDecimal.ZERO;
  private Set refDocOnes = new HashSet();
  private Set refDocTwoes = new HashSet();
  private String wowiCurrentInstrucationSeq = "";
  private String wowiCurrentInstrucationSeqWC = "";
  private String wowiCurrentInstrucationSeqWCDesc = "";

  private BigDecimal leadTime = null;
  private boolean icrRequiredWhenReceiverNa = true;

  public WorkOrderItemPK getPk()
  {
    return this.pk;
  }

  public void setPk(WorkOrderItemPK pk) {
    this.pk = pk;
  }

  public String getSetId() {
    return this.setId;
  }

  public void setSetId(String setId) {
    this.setId = setId;
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

  public BigDecimal getExtendPrice() {
    return this.extendPrice;
  }

  public void setExtendPrice(BigDecimal extendPrice) {
    this.extendPrice = extendPrice;
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

  public BigDecimal getWoTax() {
    return this.woTax;
  }

  public void setWoTax(BigDecimal woTax) {
    this.woTax = woTax;
  }

  public BigDecimal getWoTotal() {
    return this.woTotal;
  }

  public void setWoTotal(BigDecimal woTotal) {
    this.woTotal = woTotal;
  }

  public String getReferenceDoc1() {
    return this.referenceDoc1;
  }

  public void setReferenceDoc1(String referenceDoc1) {
    this.referenceDoc1 = referenceDoc1;
  }

  public String getReferenceDoc2() {
    return this.referenceDoc2;
  }

  public void setReferenceDoc2(String referenceDoc2) {
    this.referenceDoc2 = referenceDoc2;
  }

  public String getChangeOrder() {
    return this.changeOrder;
  }

  public void setChangeOrder(String changeOrder) {
    this.changeOrder = changeOrder;
  }

  public String getQr() {
    return this.qr;
  }

  public void setQr(String qr) {
    this.qr = qr;
  }

  public String getStatus() {
    return this.status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public String getMfgStatus() {
    return this.mfgStatus;
  }

  public void setMfgStatus(String mfgStatus) {
    this.mfgStatus = mfgStatus;
  }

  public Date getStartDate() {
    return this.startDate;
  }

  public void setStartDate(Date startDate) {
    this.startDate = startDate;
  }

  public Date getDateRequired() {
    return this.dateRequired;
  }

  public void setDateRequired(Date dateRequired) {
    this.dateRequired = dateRequired;
  }

  public Date getDesired() {
    return this.desired;
  }

  public void setDesired(Date desired) {
    this.desired = desired;
  }

  public int getDelayDays() {
    return this.delayDays;
  }

  public void setDelayDays(int delayDays) {
    this.delayDays = delayDays;
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

  public String getLocation() {
    return this.location;
  }

  public void setLocation(String location) {
    this.location = location;
  }

  public String getShipVia() {
    return this.shipVia;
  }

  public void setShipVia(String shipVia) {
    this.shipVia = shipVia;
  }

  public BigDecimal getFreight() {
    return this.freight;
  }

  public void setFreight(BigDecimal freight) {
    this.freight = freight;
  }

  public String getPartNumber() {
    return this.partNumber;
  }

  public void setPartNumber(String partNumber) {
    this.partNumber = partNumber;
  }

  public String getPartType() {
    return this.partType;
  }

  public void setPartType(String partType) {
    this.partType = partType;
  }

  public String getPartFrom() {
    return this.partFrom;
  }

  public void setPartFrom(String partFrom) {
    this.partFrom = partFrom;
  }

  public String getModelNumber() {
    return this.modelNumber;
  }

  public void setModelNumber(String modelNumber) {
    this.modelNumber = modelNumber;
  }

  public String getRevision() {
    return this.revision;
  }

  public void setRevision(String revision) {
    this.revision = revision;
  }

  public String getDwg() {
    return this.dwg;
  }

  public void setDwg(String dwg) {
    this.dwg = dwg;
  }

  public BigDecimal getQuantity() {
    return this.quantity;
  }

  public void setQuantity(BigDecimal quantity) {
    this.quantity = quantity;
  }

  public BigDecimal getQuantityAsm() {
    return this.quantityAsm;
  }

  public void setQuantityAsm(BigDecimal quantityAsm) {
    this.quantityAsm = quantityAsm;
  }

  public BigDecimal getHqa() {
    return this.hqa;
  }

  public void setHqa(BigDecimal hqa) {
    this.hqa = hqa;
  }

  public BigDecimal getCostPerUnit() {
    return this.costPerUnit;
  }

  public void setCostPerUnit(BigDecimal costPerUnit) {
    this.costPerUnit = costPerUnit;
  }

  public String getUnit() {
    return this.unit;
  }

  public void setUnit(String unit) {
    this.unit = unit;
  }

  public BigDecimal getList() {
    return this.list;
  }

  public void setList(BigDecimal list) {
    this.list = list;
  }

  public BigDecimal getDiscount() {
    return this.discount;
  }

  public void setDiscount(BigDecimal discount) {
    this.discount = discount;
  }

  public BigDecimal getTaxRate() {
    return this.taxRate;
  }

  public void setTaxRate(BigDecimal taxRate) {
    this.taxRate = taxRate;
  }

  public String getDescription() {
    return this.description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getMfg() {
    return this.mfg;
  }

  public void setMfg(String mfg) {
    this.mfg = mfg;
  }

  public String getMpn() {
    return this.mpn;
  }

  public void setMpn(String mpn) {
    this.mpn = mpn;
  }

  public String getMpnRev() {
    return this.mpnRev;
  }

  public void setMpnRev(String mpnRev) {
    this.mpnRev = mpnRev;
  }

  public String getWorkOrderItemComments() {
    return this.workOrderItemComments;
  }

  public void setWorkOrderItemComments(String workOrderItemComments) {
    this.workOrderItemComments = workOrderItemComments;
  }

  public String getWorkOrderItemPrivateNotes() {
    return this.workOrderItemPrivateNotes;
  }

  public Set getRefDocOnes() {
    return this.refDocOnes;
  }

  public void setWorkOrderItemPrivateNotes(String workOrderItemPrivateNotes) {
    this.workOrderItemPrivateNotes = workOrderItemPrivateNotes;
  }

  public void setRefDocOnes(Set refDocOnes) {
    this.refDocOnes = refDocOnes;
  }

  public Set getRefDocTwoes() {
    return this.refDocTwoes;
  }

  public void setRefDocTwoes(Set refDocTwoes) {
    this.refDocTwoes = refDocTwoes;
  }

  public boolean isCostAlarm() {
    return this.costAlarm;
  }

  public void setCostAlarm(boolean costAlarm) {
    this.costAlarm = costAlarm;
  }

  public String getReviewedBy() {
    return this.reviewedBy;
  }

  public void setReviewedBy(String reviewedBy) {
    this.reviewedBy = reviewedBy;
  }

  public Date getReviewedDate() {
    return this.reviewedDate;
  }

  public void setReviewedDate(Date reviewedDate) {
    this.reviewedDate = reviewedDate;
  }

  public String getReviewDescription() {
    return this.reviewDescription;
  }

  public void setReviewDescription(String reviewDescription) {
    this.reviewDescription = reviewDescription;
  }

  public BigDecimal getOriginalCost() {
    return this.originalCost;
  }

  public void setOriginalCost(BigDecimal originalCost) {
    this.originalCost = originalCost;
  }

  public String toString() {
    return new ToStringBuilder(this).append("pk", getPk()).toString();
  }

  public boolean equals(Object other) {
    if (!(other instanceof WorkOrderItemVO)) {
      return false;
    }
    WorkOrderItemVO castOther = (WorkOrderItemVO)other;
    return new EqualsBuilder().append(getPk(), castOther.getPk()).isEquals();
  }

  public int hashCode() {
    return new HashCodeBuilder().append(getPk()).toHashCode();
  }

  public String uniqueStr() {
    return getClass().getName() + "#" + getPk();
  }

  public String getWiFlag() {
    return this.wiFlag;
  }

  public void setWiFlag(String wiFlag) {
    this.wiFlag = wiFlag;
  }

  public boolean isMasterWorkOrder() {
    return this.masterWorkOrder;
  }

  public void setMasterWorkOrder(boolean masterWorkOrder) {
    this.masterWorkOrder = masterWorkOrder;
  }

  public String getRelatedDoc() {
    return this.relatedDoc;
  }

  public void setRelatedDoc(String relatedDoc) {
    this.relatedDoc = relatedDoc;
  }

  public Date getActual() {
    return this.actual;
  }

  public void setActual(Date actual) {
    this.actual = actual;
  }

  public BigDecimal getOverPercent() {
    return this.overPercent;
  }

  public void setOverPercent(BigDecimal overPercent) {
    this.overPercent = overPercent;
  }

  public BigDecimal getStdCostPerUnit() {
    return this.stdCostPerUnit;
  }

  public void setStdCostPerUnit(BigDecimal stdCostPerUnit) {
    this.stdCostPerUnit = stdCostPerUnit;
  }

  public String getWowiCurrentInstrucationSeq() {
    return this.wowiCurrentInstrucationSeq;
  }

  public void setWowiCurrentInstrucationSeq(String wowiCurrentInstrucationSeq) {
    this.wowiCurrentInstrucationSeq = wowiCurrentInstrucationSeq;
  }

  public String getWowiCurrentInstrucationSeqWC() {
    return this.wowiCurrentInstrucationSeqWC;
  }

  public void setWowiCurrentInstrucationSeqWC(String wowiCurrentInstrucationSeqWC) {
    this.wowiCurrentInstrucationSeqWC = wowiCurrentInstrucationSeqWC;
  }

  public String getWowiCurrentInstrucationSeqWCDesc() {
    return this.wowiCurrentInstrucationSeqWCDesc;
  }

  public void setWowiCurrentInstrucationSeqWCDesc(String wowiCurrentInstrucationSeqWCDesc) {
    this.wowiCurrentInstrucationSeqWCDesc = wowiCurrentInstrucationSeqWCDesc;
  }

  public String getHold() {
    return this.hold;
  }

  public void setHold(String hold) {
    this.hold = hold;
  }

  public String getPartUnit() {
    return this.partUnit;
  }

  public void setPartUnit(String partUnit) {
    this.partUnit = partUnit;
  }

  public BigDecimal getLeadTime() {
    return this.leadTime;
  }

  public void setLeadTime(BigDecimal leadTime) {
    this.leadTime = leadTime;
  }

  public boolean isIcrRequiredWhenReceiverNa() {
    return this.icrRequiredWhenReceiverNa;
  }

  public void setIcrRequiredWhenReceiverNa(boolean icrRequiredWhenReceiverNa) {
    this.icrRequiredWhenReceiverNa = icrRequiredWhenReceiverNa;
  }
}