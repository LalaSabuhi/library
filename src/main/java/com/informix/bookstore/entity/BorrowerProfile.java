package com.informix.bookstore.entity;

import jakarta.persistence.*;


@Entity
@Table(name = "borrower_profile")
public class BorrowerProfile {

    @Id
    private Integer userAccountId;

    @OneToOne
    @JoinColumn(name = "user_account_id")
    @MapsId
    private Users userId;

    private String firstName;
    private String lastName;
    private String city;

    private String state;

    private String country;
    private String address;
    private String phone;
    private String idCardPdf;

    @Column(nullable = true, length = 64)
    private String profilePhoto;

    public BorrowerProfile() {
    }

    public BorrowerProfile(int userAccountId, Users userId, String firstName, String lastName, String city, String state, String country, String address, String phone, String idCardPdf, String profilePhoto) {
        this.userAccountId = userAccountId;
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.city = city;
        this.state = state;
        this.country = country;
        this.address = address;
        this.phone = phone;
        this.idCardPdf = idCardPdf;
        this.profilePhoto = profilePhoto;
    }

    public BorrowerProfile(Users savedUser) {
        this.userId = savedUser;
    }

    public int getUserAccountId() {
        return userAccountId;
    }

    public void setUserAccountId(int userAccountId) {
        this.userAccountId = userAccountId;
    }

    public Users getUserId() {
        return userId;
    }

    public void setUserId(Users userId) {
        this.userId = userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getIdCardPdf() {
        return idCardPdf;
    }

    public void setIdCardPdf(String idCardPdf) {
        this.idCardPdf = idCardPdf;
    }

    public String getProfilePhoto() {
        return profilePhoto;
    }

    public void setProfilePhoto(String profilePhoto) {
        this.profilePhoto = profilePhoto;
    }
    @Transient
    public String getPhotosImagePath() {
        if (profilePhoto == null || userAccountId == null) return null;
        return "/photos/borrower/" + userAccountId + "/" + profilePhoto;
    }
    @Transient
    public String getIdCardPath() {
        if (idCardPdf == null || userAccountId == null) return null;
        return "/photos/borrower/" + userAccountId + "/" + idCardPdf;
    }


    @Override
    public String toString() {
        return "BorrowerProfile{" +
                "userAccountId=" + userAccountId +
                ", userId=" + userId +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", city='" + city + '\'' +
                ", state='" + state + '\'' +
                ", country='" + country + '\'' +
                ", address='" + address + '\'' +
                ", phone='" + phone + '\'' +
                ", idCardPdf='" + idCardPdf + '\'' +
                ", profilePhoto='" + profilePhoto + '\'' +
                '}';
    }
}
