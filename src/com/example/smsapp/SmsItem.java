package com.example.smsapp;

public class SmsItem {
    private String smsBody;
    private String smsTime;
    private String smsAddress;
    private String smsPerson;
    private String smsType;
    private String smsContent;

    public SmsItem(String smsBody, String smsTime, String smsAddress,
                   String smsPerson, String smsType) {
        super();
        this.smsBody = smsBody;
        this.smsTime = smsTime;
        this.smsAddress = smsAddress;
        this.smsPerson = smsPerson;
        this.smsType = smsType;
    }

    public String getSmsBody() {
        return smsBody;
    }

    public void setSmsBody(String smsBody) {
        this.smsBody = smsBody;
    }

    public String getSmsTime() {
        return smsTime;
    }

    public void setSmsTime(String smsTime) {
        this.smsTime = smsTime;
    }

    public String getSmsAddress() {
        return smsAddress;
    }

    public void setSmsAddress(String smsAddress) {
        this.smsAddress = smsAddress;
    }

    public String getSmsContent() {

        return smsContent;
    }

    public void setSmsContent() {
        this.smsContent = this.smsPerson + "\n" + this.smsBody;
    }

    public String getSmsPerson() {
        return smsPerson;
    }

    public void setSmsPerson(String smsPerson) {
        this.smsPerson = smsPerson;
    }

    public String getSmsType() {
        return smsType;
    }

    public void setSmsType(String smsType) {
        this.smsType = smsType;
    }

    // id,address,Time,read,status,type,body,count(address) as
    /*
	 * public SmsItem(String smsContent, String smsTime) { super(); SmsContent =
	 * smsContent; this.smsTime = smsTime; }
	 * 
	 * public String getSmsContent() { return SmsContent; } public void
	 * setSmsContent(String smsContent) { SmsContent = smsContent; } public
	 * String getSmsTime() { return smsTime; } public void setSmsTime(String
	 * smsTime) { this.smsTime = smsTime; }
	 */

}
