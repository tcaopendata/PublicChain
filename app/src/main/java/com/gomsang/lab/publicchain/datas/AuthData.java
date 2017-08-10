package com.gomsang.lab.publicchain.datas;

import java.util.UUID;

/**
 * Created by Gyeongrok Kim on 2017-08-08.
 */

public class AuthData {

    private String identifyToken;
    // private token for recover
    private String privateToken;
    private String name;
    private String email;
    private String address;
    private String phone;

    public AuthData() {
    }

    public AuthData(String identifyToken, String name, String email, String address, String phone) {
        this.identifyToken = identifyToken;
        this.privateToken = UUID.randomUUID().toString();
        this.name = name;
        this.email = email;
        this.address = address;
        this.phone = phone;
    }

    public String getIdentifyToken() {
        return identifyToken;
    }

    public String getPrivateToken() {
        return privateToken;
    }

    public void setPrivateToken(String privateToken) {
        this.privateToken = privateToken;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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
}
