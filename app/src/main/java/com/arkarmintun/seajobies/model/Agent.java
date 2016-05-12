package com.arkarmintun.seajobies.model;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by arkar on 5/3/16.
 */
public class Agent {

    public String objectId;
    public String name;
    public String managingDirector;
    public String address;
    public JSONArray phoneNumbers;

    public Agent() {

    }

    public JSONArray convertStringToJsonPhoneNumbers(String phs) {
        String removeWhiteSpaces = phs.replace(" ", "").replace("-", "");
        String[] phNumbers = removeWhiteSpaces.split(",");
        JSONArray phoneNumbers = new JSONArray(Arrays.asList(phNumbers));
        return phoneNumbers;
    }
}
