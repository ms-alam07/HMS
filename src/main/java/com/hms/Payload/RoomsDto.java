package com.hms.Payload;

import java.time.LocalDate;

public class RoomsDto {
    private String room_type;
    private Long total_rooms;
    private Long nightly_price;
    private LocalDate date;
    private Long propertyId;


    public String getRoom_type() {
        return room_type;
    }

    public void setRoom_type(String room_type) {
        this.room_type = room_type;
    }

    public Long getTotal_rooms() {
        return total_rooms;
    }

    public void setTotal_rooms(Long total_rooms) {
        this.total_rooms = total_rooms;
    }

    public Long getNightly_price() {
        return nightly_price;
    }

    public void setNightly_price(Long nightly_price) {
        this.nightly_price = nightly_price;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Long getPropertyId() {
        return propertyId;
    }

    public void setPropertyId(Long propertyId) {
        this.propertyId = propertyId;
    }
}