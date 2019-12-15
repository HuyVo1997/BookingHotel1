package com.bookinghotel.service;

import com.restfb.Facebook;

public class CustomUser {


    @Facebook("name")
    private String fullName;

    @Facebook
    private String email;


    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}