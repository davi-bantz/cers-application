package com.example.cersapplication.rescuerRegistration;

public class UploadRescuerVerification {
    private String mfilename, mimageUrl;

    public UploadRescuerVerification(String trim){

    }
    public String getMimageUrl() {
        return mimageUrl;
    }

    public void setMimageUrl(String mimageUrl) {
        this.mimageUrl = mimageUrl;
    }

    public void setMfilename (String mfilename){
        this.mfilename = mfilename;

    }
    public  String getMfilename(){
        return mfilename;
    }

    public UploadRescuerVerification (String imageUrl, String filename ){
        mimageUrl = imageUrl;
        mfilename = filename;
    }
}
