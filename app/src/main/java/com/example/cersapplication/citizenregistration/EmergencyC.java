package com.example.cersapplication.citizenregistration;

public class EmergencyC {

    public String ECName, ECPhone, ECPurok, ECBarangay, ECStreet, ECCity, ECProvince;

    public EmergencyC(){

    }

    public EmergencyC(String textEmerName, String textEmerNumber, String textEmerPurok, String textEmerStreet, String textEmerBarangay, String textEmerCity, String textEmerProvince){
        this.ECName = textEmerName;
        this.ECPhone = textEmerNumber;
        this.ECPurok = textEmerPurok;
        this.ECStreet = textEmerStreet;
        this.ECBarangay = textEmerBarangay;
        this.ECCity = textEmerCity;
        this.ECProvince = textEmerProvince;

    }

    public String getECName() {
        return ECName;
    }

    public void setECName(String ECName) {
        this.ECName = ECName;
    }

    public String getECPhone() {
        return ECPhone;
    }

    public void setECPhone(String ECPhone) {
        this.ECPhone = ECPhone;
    }

    public String getECPurok() {
        return ECPurok;
    }

    public void setECPurok(String ECPurok) {
        this.ECPurok = ECPurok;
    }

    public String getECBarangay() {
        return ECBarangay;
    }

    public void setECBarangay(String ECBarangay) {
        this.ECBarangay = ECBarangay;
    }

    public String getECStreet() {
        return ECStreet;
    }

    public void setECStreet(String ECStreet) {
        this.ECStreet = ECStreet;
    }

    public String getECCity() {
        return ECCity;
    }

    public void setECCity(String ECCity) {
        this.ECCity = ECCity;
    }

    public String getECProvince() {
        return ECProvince;
    }

    public void setECProvince(String ECProvince) {
        this.ECProvince = ECProvince;
    }
}
