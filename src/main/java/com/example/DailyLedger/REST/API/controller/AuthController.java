
package com.example.DailyLedger.REST.API.controller;

import com.example.DailyLedger.REST.API.entity.UserResponse;
import com.example.DailyLedger.REST.API.service.UserService;
import com.example.DailyLedger.REST.API.utility.ResponseBuilder;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    //    public ResponseEntity<UserResponse> signup(@RequestBody JsonNode request) {
    @PostMapping("/signup")
    public ResponseEntity<UserResponse> signup(
            @RequestParam String name,
            @RequestParam String email,
            @RequestParam String password) {

//        String name = request.get("name").asText();
//        String email = request.get("email").asText();
//        String password = request.get("password").asText();
        try {
            userService.registerUser(name, email, password);
            return ResponseEntity.ok(
                    ResponseBuilder.build("Registered Successfully", null, 200)
            );
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(ResponseBuilder.build(null, e.getMessage(), 400));
        }
    }

    @PostMapping("/login")
    public ResponseEntity<UserResponse> login(@RequestParam String email,
                                              @RequestParam String password) {

        try {
            userService.login(email, password);
            return ResponseEntity.ok(
                    ResponseBuilder.build("Login Successfully", null, 200)
            );
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(ResponseBuilder.build(null, e.getMessage(), 400));
        }

    }

    @PostMapping("/verify-email")
    public ResponseEntity<UserResponse> verifyEmail(@RequestParam String email,
                                                    @RequestParam String code) {
//        String email = request.get("email").asText();
//        String code = request.get("code").asText();

        try {
            userService.verifyEmail(email, code);
            return ResponseEntity.ok(
                    ResponseBuilder.build("Verified Successfully", null, 200)
            );
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(ResponseBuilder.build(null, e.getMessage(), 400));
        }
    }

    @PostMapping("/resend-otp")
    public ResponseEntity<UserResponse> resendOTP(@RequestParam String email) {
//        String email = request.get("email").asText();
        try {
            userService.resendOTP(email);
            return ResponseEntity.ok(
                    ResponseBuilder.build("OTP sent Successfully", null, 200)
            );
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(ResponseBuilder.build(null, e.getMessage(), 400));
        }
    }

}
