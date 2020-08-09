package com.karros.vn.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.karros.vn.annotation.UnsignedAccess;
import com.karros.vn.model.MsgModel;
import com.karros.vn.model.gpx.GpxInfo;
import com.karros.vn.model.gpx.LatestTrack;
import com.karros.vn.model.token.CreateApiTokenRequest;
import com.karros.vn.model.token.CreateApiTokenResponse;
import com.karros.vn.model.user.AccountInfo;
import com.karros.vn.model.user.LoginResponse;
import com.karros.vn.model.user.LogoutRequest;
import com.karros.vn.model.user.RegisterInfoRequest;
import com.karros.vn.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {

  @Autowired
  private UserService userService;
  
  @UnsignedAccess
  @RequestMapping(method = RequestMethod.POST, path = "/login", 
  produces = MediaType.APPLICATION_JSON_VALUE,
  consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<LoginResponse> login(@RequestBody(required = true)AccountInfo info, HttpServletRequest request) {
    return ResponseEntity.ok(userService.login(info,request));
  }

  @UnsignedAccess
  @RequestMapping(method = RequestMethod.POST, path = "/register", 
  produces = MediaType.APPLICATION_JSON_VALUE,
  consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<MsgModel> register(@RequestBody(required = true)RegisterInfoRequest info) {
    return ResponseEntity.ok(userService.register(info));
  }

  @RequestMapping(method = RequestMethod.GET, path = "/logout", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<MsgModel> logout(HttpServletRequest request, @RequestBody(required = true) LogoutRequest logoutRequest) {

    return ResponseEntity.ok(userService.logout(request, logoutRequest));
  }

  @UnsignedAccess
  @RequestMapping(method = RequestMethod.POST, path = "/create-api-token", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<CreateApiTokenResponse> createApiToken(HttpServletRequest request, @RequestBody(required = true)CreateApiTokenRequest info) {
    return ResponseEntity.ok(userService.createApiToken(request, info));
  }
  
  @PostMapping("/upload-file")
  public ResponseEntity<MsgModel> uploadFile(@RequestParam("file") MultipartFile file, HttpServletRequest request) {
    return ResponseEntity.ok(userService.uploadFile(file, request));
  }
  
  @GetMapping("/get-latest-track")
  public ResponseEntity<LatestTrack> getLatestTrack(HttpServletRequest request) {
    return ResponseEntity.ok(userService.getLatestTrack(request));
  }
  
  @GetMapping("/get-gpx-info/{id}")
  public ResponseEntity<GpxInfo> getLatestTrack(@PathVariable("id") Integer id) {
    return ResponseEntity.ok(userService.getGpxInfo(id));
  }
}
