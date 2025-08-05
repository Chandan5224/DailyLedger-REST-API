package com.example.DailyLedger.REST.API.utility;

import com.example.DailyLedger.REST.API.entity.Body;
import com.example.DailyLedger.REST.API.entity.Header;
import com.example.DailyLedger.REST.API.entity.UserResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.JsonNode;

public class ResponseBuilder {

    private static final ObjectMapper mapper = new ObjectMapper();

    public static UserResponse build(Object data, Object error, int code) {
        JsonNode jsonNode = mapper.valueToTree(data);
        return new UserResponse(
                new Header(code),
                new Body(error, jsonNode)
        );
    }
}
