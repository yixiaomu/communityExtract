package cn.passionshark.project.community.core.dto;

import cn.passionshark.project.community.common.validate.Signable;

public class RegisteredResponseDto extends BaseCommunityResponseDto{
    
    private static final long serialVersionUID = -1122907355511061391L;

    @Signable
    private String orderNo;
    @Signable
    private String orderDate;
    @Signable
    private String payAmount;
    @Signable
    private String bankNo;
    @Signable
    private String finishTime;
    @Signable
    private String uniTradeNo;

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public String getPayAmount() {
        return payAmount;
    }

    public void setPayAmount(String payAmount) {
        this.payAmount = payAmount;
    }

    public String getBankNo() {
        return bankNo;
    }

    public void setBankNo(String bankNo) {
        this.bankNo = bankNo;
    }

    public String getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(String finishTime) {
        this.finishTime = finishTime;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }
    public String getUniTradeNo() {
        return uniTradeNo;
    }

    public void setUniTradeNo(String uniTradeNo) {
        this.uniTradeNo = uniTradeNo;
    }

    
    

    @Override
    public String toString() {
        return "{\"orderNo\":\"" + orderNo + "\",\"orderDate\":\"" + orderDate + "\",\"payAmount\":\"" + payAmount
                + "\",\"bankNo\":\"" + bankNo + "\",\"finishTime\":\"" + finishTime + "\",\"toString()\":\""
                + super.toString() + "\"} ";
    }

}
