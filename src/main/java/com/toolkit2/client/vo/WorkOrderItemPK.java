package com.toolkit2.client.vo;
import java.io.Serializable;
import org.apache.commons.lang.builder.CompareToBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

public class WorkOrderItemPK
  implements Serializable, Comparable<WorkOrderItemPK>
{
  private String workOrderNumber;
  private String workOrderItemNumber;

  public WorkOrderItemPK()
  {
  }

  public WorkOrderItemPK(String workOrderNumber, String workOrderItemNumber)
  {
    this.workOrderNumber = workOrderNumber;
    this.workOrderItemNumber = workOrderItemNumber;
  }

  public void setWorkOrderNumber(String workOrderNumber) {
    this.workOrderNumber = workOrderNumber;
  }

  public String getWorkOrderNumber() {
    return this.workOrderNumber;
  }

  public void setWorkOrderItemNumber(String workOrderItemNumber) {
    this.workOrderItemNumber = workOrderItemNumber;
  }

  public String getWorkOrderItemNumber() {
    return this.workOrderItemNumber;
  }

  public String toString() {
    return this.workOrderNumber + "." + this.workOrderItemNumber;
  }

  public boolean equals(Object other) {
    if (!(other instanceof WorkOrderItemPK)) {
      return false;
    }
    WorkOrderItemPK castOther = (WorkOrderItemPK)other;
    return new EqualsBuilder().append(getWorkOrderNumber(), castOther.getWorkOrderNumber()).append(getWorkOrderItemNumber(), castOther.getWorkOrderItemNumber()).isEquals();
  }

  public int hashCode() {
    return new HashCodeBuilder().append(getWorkOrderItemNumber()).append(getWorkOrderNumber()).toHashCode();
  }

  public int compareTo(WorkOrderItemPK other) {
    if (this == other) {
      return 0;
    }
    if (other == null) {
      return 1;
    }
    return new CompareToBuilder().append(getWorkOrderNumber(), other.getWorkOrderNumber()).append(getWorkOrderItemNumber(), other.getWorkOrderItemNumber()).toComparison();
  }
}