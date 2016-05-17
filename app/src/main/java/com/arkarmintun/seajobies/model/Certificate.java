package com.arkarmintun.seajobies.model;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by arkar on 5/16/16.
 */
public class Certificate {

    public String objectId;
    public String certificateName;
    public String certificateNo;
    public String issueDate;
    public String expiryDate;

    public Certificate() {

    }

    public String convertDatetoString(Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        String strDate = dateFormat.format(date);
        return strDate;
    }
}
