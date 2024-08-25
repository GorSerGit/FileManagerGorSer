package com.example.mymicroservice.model;

import java.util.HashMap;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;

public class MyResponse {
    private List<String> responseMessage;

    public MyResponse(List<String> responseMessage) {
        this.responseMessage = responseMessage;
    }

    public List<String> getResponseMessage() {
        return responseMessage;
    }

    public void setResponseMessage(List<String> responseMessage) {
        this.responseMessage = responseMessage;
    }
}