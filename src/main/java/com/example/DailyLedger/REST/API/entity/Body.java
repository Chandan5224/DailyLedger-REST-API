package com.example.DailyLedger.REST.API.entity;// Body.java
import com.fasterxml.jackson.databind.JsonNode;

public class Body {
    private Object error;
    private JsonNode value;

    public Body(Object error, JsonNode value) {
        this.error = error;
        this.value = value;
    }

    public Object getError() {
        return error;
    }

    public JsonNode getValue() {
        return value;
    }
}
