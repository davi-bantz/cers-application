package com.example.cersapplication.rescuerRegistration;

public class ReadWriteServiceDetails {

    public String department, role, addresOS, firstAid, CPR, SNR, KnotLash, CasHandle;

    public ReadWriteServiceDetails (){

    }
    public ReadWriteServiceDetails(String textDepartment, String textRole, String textAddressOS) {
        this.department = textDepartment;
        this.role = textRole;
        this.addresOS = textAddressOS;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getAddresOS() {
        return addresOS;
    }

    public void setAddresOS(String addresOS) {
        this.addresOS = addresOS;
    }

    public String getFirstAid() {
        return firstAid;
    }

    public void setFirstAid(String firstAid) {
        this.firstAid = firstAid;
    }

    public String getCPR() {
        return CPR;
    }

    public void setCPR(String CPR) {
        this.CPR = CPR;
    }

    public String getSNR() {
        return SNR;
    }

    public void setSNR(String SNR) {
        this.SNR = SNR;
    }

    public String getKnotLash() {
        return KnotLash;
    }

    public void setKnotLash(String knotLash) {
        KnotLash = knotLash;
    }

    public String getCasHandle() {
        return CasHandle;
    }

    public void setCasHandle(String casHandle) {
        CasHandle = casHandle;
    }
}
