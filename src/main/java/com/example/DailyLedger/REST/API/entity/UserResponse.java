package com.example.DailyLedger.REST.API.entity;

// UserResponse.java
public class UserResponse {
    private Header header;
    private Body body;

    public UserResponse(Header header, Body body) {
        this.header = header;
        this.body = body;
    }

    public Header getHeader() {
        return header;
    }

    public Body getBody() {
        return body;
    }
}
