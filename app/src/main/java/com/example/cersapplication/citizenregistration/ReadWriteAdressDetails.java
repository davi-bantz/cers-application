package com.example.cersapplication.citizenregistration;

public class ReadWriteAdressDetails {

    public String  Purok, Street, City, Barangay, Province;

    public ReadWriteAdressDetails(){

    }


    public ReadWriteAdressDetails ( String textPurok, String textStreet, String textCity, String textBarangay, String textProvince) {

        this.Purok = textPurok;
        this.Street = textStreet;
        this.City = textCity;
        this.Barangay = textBarangay;
        this.Province = textProvince;
    }

    public String getPurok() {
        return Purok;
    }

    public void setPurok(String purok) {
        Purok = purok;
    }

    public String getStreet() {
        return Street;
    }

    public void setStreet(String street) {
        Street = street;
    }

    public String getCity() {
        return City;
    }

    public void setCity(String city) {
        City = city;
    }

    public String getBarangay() {
        return Barangay;
    }

    public void setBarangay(String barangay) {
        Barangay = barangay;
    }

    public String getProvince() {
        return Province;
    }

    public void setProvince(String province) {
        Province = province;
    }
}

