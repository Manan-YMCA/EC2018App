package com.manan.dev.ec2018app.Models;

/**
 * Created by nisha on 2/27/2018.
 */

public class QRTicketModel {
    private int paymentStatus,arrivalStatus;
    private String QRcode,eventID;
    private Long timeStamp;

    public QRTicketModel() {
    }

    public QRTicketModel(int paymentStatus, int arrivalStatus, String QRcode, String eventID, Long timeStamp) {
        this.paymentStatus = paymentStatus;
        this.arrivalStatus = arrivalStatus;
        this.QRcode = QRcode;
        this.eventID = eventID;
        this.timeStamp = timeStamp;
    }
    public QRTicketModel(int paymentStatus, int arrivalStatus, String QRcode, String eventID) {
        this.paymentStatus = paymentStatus;
        this.arrivalStatus = arrivalStatus;
        this.QRcode = QRcode;
        this.eventID = eventID;
    }

    public int getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(int paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public int getArrivalStatus() {
        return arrivalStatus;
    }

    public void setArrivalStatus(int arrivalStatus) {
        this.arrivalStatus = arrivalStatus;
    }

    public String getQRcode() {
        return QRcode;
    }

    public void setQRcode(String QRcode) {
        this.QRcode = QRcode;
    }

    public String getEventID() {
        return eventID;
    }

    public void setEventID(String eventID) {
        this.eventID = eventID;
    }

    public Long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(Long timeStamp) {
        this.timeStamp = timeStamp;
    }
}
