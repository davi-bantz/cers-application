package com.example.cersapplication.citizenregistration;

public class ReadWriteUserDetails {
    public String  dob, Phone, Username;


    public ReadWriteUserDetails(){

}
    public ReadWriteUserDetails(String textdob, String textPhone, String textUsername){
        this.dob = textdob;
        this.Username= textUsername;
        this.Phone = textPhone;


     }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

}

