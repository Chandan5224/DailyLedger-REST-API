package com.example.DailyLedger.REST.API.service;

import com.example.DailyLedger.REST.API.entity.EmailVerificationToken;
import com.example.DailyLedger.REST.API.entity.User;
import com.example.DailyLedger.REST.API.repository.EmailTokenRepository;
import com.example.DailyLedger.REST.API.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.management.RuntimeErrorException;
import java.time.LocalDateTime;
import java.util.Random;
@Service
public class UserService {
    @Autowired
    public UserService(UserRepository userRepo, EmailTokenRepository tokenRepo, EmailService emailService, PasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.tokenRepo = tokenRepo;
        this.emailService = emailService;
        this.passwordEncoder = passwordEncoder;
    }

    private final UserRepository userRepo;
    private final EmailTokenRepository tokenRepo;
    private final EmailService emailService;
    private final PasswordEncoder passwordEncoder;

    public void registerUser(String name, String email,String password) {
        if (userRepo.existsByEmail(email)) {
            throw new RuntimeException("Email already exists");
        }
        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));
        user.setVerified(false);
        userRepo.save(user);

        String code = String.valueOf(new Random().nextInt(89999) + 10000);

        EmailVerificationToken token = new EmailVerificationToken();
        token.setEmail(email);
        token.setCode(code);
        token.setExpiresAt(LocalDateTime.now().plusMinutes(5));
        tokenRepo.save(token);

        emailService.sendOtp(email, code);
    }

    public void verifyEmail(String email, String code) {
        EmailVerificationToken token = tokenRepo.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Verification code not found"));

        if (!token.getCode().equals(code)) {
            throw new RuntimeException("Invalid verification code");
        }

        if (token.getExpiresAt().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("Verification code expired");
        }

        User user = userRepo.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setVerified(true);
        userRepo.save(user);

        tokenRepo.delete(token); // optional: remove used token
    }

    public void login(String email,String password){
        User user = userRepo.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
        if(!user.isVerified()){
            throw new RuntimeException("Email is not verified");
        }
        if(!passwordEncoder.matches(password, user.getPassword())){
            throw new RuntimeException("Incorrect Password");
        }
    }


    public void resendOTP(String email) {
        EmailVerificationToken token = tokenRepo.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        String code = String.valueOf(new Random().nextInt(89999) + 10000);
        token.setCode(code);
        token.setExpiresAt(LocalDateTime.now().plusMinutes(5)); // Optional: Reset expiration
        tokenRepo.save(token);
        emailService.sendOtp(email, code);
    }


}
