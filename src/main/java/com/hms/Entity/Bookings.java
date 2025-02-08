package com.hms.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "bookings")
public class Bookings {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "guest_name", nullable = false, length = 100)
    private String guestName;

    @Column(name = "no_of_guest", nullable = false)
    private Long noOfGuest;

    @Column(name = "mobile", nullable = false)
    private Long mobile;

    @Column(name = "email", nullable = false, unique = true, length = 50)
    private String email;



    @ManyToOne
    @JoinColumn(name = "property_id")
    private Property property;

    @Column(name = "room_type", nullable = false, length = 50)
    private String roomType;

    @Column(name = "from_date", nullable = false)
    private LocalDate fromDate;



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

    @Column(name = "to_date", nullable = false)
    private LocalDate toDate;


    public Property getProperty() {
        return property;
    }

    public void setProperty(Property property) {
        this.property = property;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
}