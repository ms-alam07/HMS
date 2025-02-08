package com.hms.Payload;

import com.hms.Entity.Property;

import java.time.LocalDate;

public class BookingsDto {

    private String guestName;
    private Long noOfGuest;
    private Long mobile;
    private String email;
    private String roomType;
    private LocalDate fromDate;
    private LocalDate toDate;
    private Property property;

    public Property getProperty() {
        return property;
    }

    public void setProperty(Property property) {
        this.property = property;
    }

    public String getGuestName() {
        return guestName;
    }

    public void setGuestName(String guestName) {
        this.guestName = guestName;
    }

    public Long getNoOfGuest() {
        return noOfGuest;
    }

    public void setNoOfGuest(Long noOfGuest) {
        this.noOfGuest = noOfGuest;
    }

    public Long getMobile() {
        return mobile;
    }

    public void setMobile(Long mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRoomType() {
        return roomType;
    }

    public void setRoomType(String roomType) {
        this.roomType = roomType;
    }

    public LocalDate getFromDate() {
        return fromDate;
    }

    public void setFromDate(LocalDate fromDate) {
        this.fromDate = fromDate;
    }

    public LocalDate getToDate() {
        return toDate;
    }

    public void setToDate(LocalDate toDate) {
        this.toDate = toDate;
    }
}
