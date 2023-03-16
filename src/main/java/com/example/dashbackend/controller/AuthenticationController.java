package com.example.dashbackend.controller;

import com.example.dashbackend.dto.AuthenticationRequest;
import com.example.dashbackend.dto.AuthenticationResponse;
import com.example.dashbackend.services.AuthenticationService;
import com.example.dashbackend.dto.RegisterRequest;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

  private final AuthenticationService service;

  @PostMapping("/register")
  public ResponseEntity<AuthenticationResponse> register(
      @RequestBody RegisterRequest request
  ) {
    return ResponseEntity.ok(service.register(request));
  }
  @PostMapping("/login")
  public ResponseEntity<Void> authenticate(
          @RequestBody AuthenticationRequest request,
          HttpServletResponse response
  ) {
    String jwtToken = service.authenticate(request);
    Cookie jwtCookie = new Cookie("JWT_TOKEN", jwtToken);
    jwtCookie.setHttpOnly(true);
    jwtCookie.setSecure(true); // Use this only in a production environment with HTTPS
    jwtCookie.setMaxAge(4 * 60 * 60); // Token valid for 4 hours
    jwtCookie.setPath("/");
    response.addCookie(jwtCookie);
    return ResponseEntity.ok().build();
  }

  @GetMapping("/authenticated")
  public ResponseEntity<Boolean> isAuthenticated(HttpServletRequest request) {
    return ResponseEntity.ok(service.isAuthenticated(request));
  }


}
