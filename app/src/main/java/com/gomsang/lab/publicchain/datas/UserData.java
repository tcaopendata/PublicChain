package com.gomsang.lab.publicchain.datas;

import java.util.UUID;

/**
 * Created by Gyeongrok Kim on 2017-08-08.
 */

public class UserData {

    private String uid;
    private String name;
    private String email;
    private String address;
    private String phone;

    public UserData() {
    }

    public UserData(String uid, String name, String email, String address, String phone) {
        this.uid = uid;
        this.name = name;
        this.email = email;
        this.address = address;
        this.phone = phone;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
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
