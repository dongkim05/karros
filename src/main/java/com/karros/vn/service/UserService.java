package com.karros.vn.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.karros.vn.mapper.GpxMetadataMapper;
import com.karros.vn.mapper.GpxTrkptMapper;
import com.karros.vn.mapper.GpxWptMapper;
import com.karros.vn.mapper.LoginTokenMapper;
import com.karros.vn.mapper.UserMapper;
import com.karros.vn.model.Msg;
import com.karros.vn.model.MsgModel;
import com.karros.vn.model.User;
import com.karros.vn.model.UserSession;
import com.karros.vn.model.exception.InvalidTokenException;
import com.karros.vn.model.exception.MsgResponseException;
import com.karros.vn.model.gpx.GpxInfo;
import com.karros.vn.model.gpx.GpxMetadata;
import com.karros.vn.model.gpx.GpxTrkpt;
import com.karros.vn.model.gpx.GpxWpt;
import com.karros.vn.model.gpx.LatestTrack;
import com.karros.vn.model.token.CreateApiTokenRequest;
import com.karros.vn.model.token.CreateApiTokenResponse;
import com.karros.vn.model.token.LoginToken;
import com.karros.vn.model.user.AccountInfo;
import com.karros.vn.model.user.LoginResponse;
import com.karros.vn.model.user.LogoutRequest;
import com.karros.vn.model.user.RegisterInfoRequest;
import com.karros.vn.model.xml.Gpx;
import com.karros.vn.model.xml.Trkseg;
import com.karros.vn.utils.BCryptEncoder;
import com.karros.vn.utils.Const;
import com.karros.vn.utils.I18N;
import com.karros.vn.utils.JwtUtils;
import com.karros.vn.utils.Utils;
import com.karros.vn.utils.UuidUtils;
import com.karros.vn.utils.XmlUtils;

@Service
public class UserService {

  private BCryptEncoder bcryptEncoder = new BCryptEncoder();

  @Autowired
  private UserMapper userMapper;

  @Autowired
  private JwtUtils jwtUtils;
  
  @Autowired
  private LoginTokenMapper loginTokenMapper;
  
  @Autowired
  private GpxMetadataMapper gpxMetadataMapper;
  
  @Autowired
  private GpxWptMapper gpxWptMapper;
  
  @Autowired
  private GpxTrkptMapper gpxTrkptMapper;
  
  public LoginResponse login(AccountInfo info, HttpServletRequest request) {
    User user = userMapper.getUserByUserName(info.getUserName());
    LoginResponse loginResponse = new LoginResponse();
    MsgModel msgModel = new MsgModel();
    loginResponse.setMsgModel(msgModel);
    if(user == null || bcryptEncoder.matches(info.getPassword(), user.getPassword())) {
      msgModel.addMsg(new Msg("ERROR00001", I18N.msg("error.karros.ERROR00001")));
      throw new MsgResponseException(msgModel);
    }else {
      msgModel.addMsg(new Msg("INFO00001", I18N.msg("info.karros.INFO00001")));
      UserSession userSession = new UserSession();
      userSession.setUser(user);
      loginResponse.setLoginToken(jwtUtils.generateJwtLoginToken(user.getId().toString()));
      loginResponse.setApiToken(jwtUtils.generateJwtApiToken(user.getId().toString()));
      HttpSession session = request.getSession(true);
      session.setAttribute(Const.User.USER_KEY, userSession);
      loginTokenMapper.deleteLogicallyByUuid(info.getUuid(), user.getId());
      LoginToken loginToken = new LoginToken();
      loginToken.setUserId(user.getId());
      loginToken.setUuid(info.getUuid());
      loginToken.setCreatedBy(user.getId());
      loginToken.setUpdatedBy(user.getId());
      loginToken.setToken(loginResponse.getLoginToken());
      loginTokenMapper.insert(loginToken);
    }

    return loginResponse;
  }

  @Transactional
  public MsgModel register(RegisterInfoRequest info) {
    validateRegisterInfo(info);
    if(userMapper.countByUserName(info.getUserName()) > 0) {
      throw new MsgResponseException(new MsgModel(new Msg("ERROR00007", I18N.msg("error.karros.ERROR00007"))));
    }
    User user = new User();
    user.setUserName(info.getUserName());
    user.setPassword(bcryptEncoder.generatePassword(info.getPassword()));
    int cnt = userMapper.insert(user);
    MsgModel msgModel = new MsgModel();
    if(cnt == 0) {
      throw new MsgResponseException(new MsgModel(new Msg("ERROR00006", I18N.msg("error.karros.ERROR00006"))));
    }else {
      msgModel.addMsg(new Msg("INFO00002", I18N.msg("info.karros.INFO00002")));
    }
    return msgModel;
  }

  private void validateRegisterInfo(RegisterInfoRequest info) {
    MsgModel msgModel = new MsgModel();

    if(info.getUserName() == null) {
      msgModel.addMsg(new Msg("ERROR00002", I18N.msg("error.karros.ERROR00002", 
          I18N.msg("pharse.karros.userName"))));
    }
    if(!Utils.inLength(info.getUserName(), Const.Text.MAX_TEXT_100)) {
      msgModel.addMsg(new Msg("ERROR00004", I18N.msg("error.karros.ERROR00004", 
          I18N.msg("pharse.karros.userName"), Const.Text.MAX_TEXT_100 + "")));
    }

    if(info.getPassword() == null) {
      msgModel.addMsg(new Msg("ERROR00002", I18N.msg("error.karros.ERROR00002", 
          I18N.msg("pharse.karros.password"))));
    }
    if(!Utils.inLength(info.getPassword(),8,20)) {
      msgModel.addMsg(new Msg("ERROR00008", I18N.msg("error.karros.ERROR00008", 
          I18N.msg("pharse.karros.password"), "8", "20")));
    }
    if(msgModel.hasMsg()) {
      throw new MsgResponseException(msgModel);
    }
  }

  public CreateApiTokenResponse createApiToken(HttpServletRequest request, CreateApiTokenRequest info) {
    int valid = jwtUtils.isJwtLoginTokenValid(info.getLoginToken());

    if(valid == Const.Token.TOKEN_EXPIRED) {
      throw new InvalidTokenException(new MsgModel(new Msg("ERROR00010", I18N.msg("error.token.ERROR00010"))));
    }else if(valid == Const.Token.TOKEN_INVALID) {
      throw new InvalidTokenException(new MsgModel(new Msg("ERROR00011", I18N.msg("error.token.ERROR00011"))));
    }

    int count = loginTokenMapper.countByLoginToken(info.getLoginToken());
    if(count == 0) {
      throw new InvalidTokenException(new MsgModel(new Msg("ERROR00011", I18N.msg("error.token.ERROR00011"))));
    }

    CreateApiTokenResponse response = new CreateApiTokenResponse();
    Integer userId = ((UserSession)request.getSession(false).getAttribute(Const.User.USER_KEY)).getUser().getId();
    response.setApiToken(jwtUtils.generateJwtApiToken(userId.toString()));
    return response;
  }
  
  public MsgModel logout(HttpServletRequest request, LogoutRequest logoutRequest) {
    Integer userId = ((UserSession)request.getSession(false).getAttribute(Const.User.USER_KEY)).getUser().getId();
    loginTokenMapper.deleteLogicallyByUuid(logoutRequest.getUuid(), userId);
    request.getSession(false).invalidate();

    return new MsgModel(new Msg("INFO00003", I18N.msg("info.karros.INFO00003")));
  }
  
  @Transactional
  public MsgModel uploadFile(MultipartFile file, HttpServletRequest request) {
    Path fileStorageLocation = null;
    try {
      fileStorageLocation = Files.createTempDirectory("karros-" + UuidUtils.randomUUIDBase32());
      String fileName = StringUtils.cleanPath(file.getOriginalFilename());
      Path targetLocation = fileStorageLocation.resolve(fileName);
      Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
      Gpx gpx = XmlUtils.parseGpxXml(targetLocation.toFile());
      Integer userId = ((UserSession)request.getSession(false).getAttribute(Const.User.USER_KEY)).getUser().getId();
      validateGpx(gpx, userId);

      GpxMetadata gpxMetadata = new GpxMetadata();
      gpxMetadata.setUserId(userId);
      gpxMetadata.setName(gpx.getMetadata().getName());
      gpxMetadata.setDescription(gpx.getMetadata().getDesc());
      gpxMetadata.setAuthor(gpx.getMetadata().getAuthor());
      gpxMetadata.setLink(gpx.getMetadata().getLink().getHref());
      gpxMetadata.setLinkText(gpx.getMetadata().getLink().getText());
      gpxMetadata.setMetadataTime(Utils.parseLocalDateTimeUtc(gpx.getMetadata().getTime()));
      gpxMetadata.setUpdatedBy(userId);
      gpxMetadata.setCreatedBy(userId);
      gpxMetadataMapper.insert(gpxMetadata);
      
      List<GpxWpt> gpxWpts = new ArrayList<>();
      gpx.getWpts().forEach(wpt -> {
        GpxWpt gpxWpt = new GpxWpt();
        gpxWpt.setGpxMetadataId(gpxMetadata.getId());
        gpxWpt.setLat(wpt.getLat());
        gpxWpt.setLon(wpt.getLon());
        gpxWpt.setName(wpt.getName());
        gpxWpt.setSym(wpt.getSym());
        gpxWpt.setUpdatedBy(userId);
        gpxWpt.setCreatedBy(userId);
        gpxWpts.add(gpxWpt);
      });
      if(!CollectionUtils.isEmpty(gpxWpts)) {
        gpxWptMapper.insertMultiple(gpxWpts);
      }
      List<GpxTrkpt> gpxTrkpts = new ArrayList<>();
      for(int i = 0; i < gpx.getTrk().getTrksegs().size(); i++) {
        Trkseg trkseg = gpx.getTrk().getTrksegs().get(i);
        final int trkSeg = i;
        trkseg.getTrkpts().forEach(trkpt -> {
          GpxTrkpt gpxTrkpt = new GpxTrkpt();
          gpxTrkpt.setGpxMetadataId(gpxMetadata.getId());
          gpxTrkpt.setTrkSeg(trkSeg);
          gpxTrkpt.setLat(trkpt.getLat());
          gpxTrkpt.setLon(trkpt.getLon());
          gpxTrkpt.setEle(trkpt.getEle());
          gpxTrkpt.setTrkptTime(Utils.parseLocalDateTimeUtc(trkpt.getTime()));
          gpxTrkpt.setUpdatedBy(userId);
          gpxTrkpt.setCreatedBy(userId);
          gpxTrkpts.add(gpxTrkpt);
        });
      }
      gpxTrkptMapper.insertMultiple(gpxTrkpts);
    } catch (IOException e) {
      e.printStackTrace();
      throw new MsgResponseException(new MsgModel(new Msg("ERROR00009", I18N.msg("error.karros.ERROR00009"))));
    }finally {
      if(fileStorageLocation != null) {
        fileStorageLocation.toFile().delete();
      }
    }
    return new MsgModel(new Msg("INFO00004", I18N.msg("info.karros.INFO00004")));
  }
  
  private void validateGpx(Gpx gpx, Integer userId) {
    MsgModel msgModel = new MsgModel();
    if(StringUtils.isEmpty(gpx.getMetadata().getName())) {
      msgModel.addMsg(new Msg("ERROR00002", I18N.msg("error.karros.ERROR00002", 
          I18N.msg("pharse.karros.xml.gpx.metadata.name"))));
    }else if(!Utils.inLength(gpx.getMetadata().getName(), Const.Text.MAX_TEXT_1000)) {
      msgModel.addMsg(new Msg("ERROR00004", I18N.msg("error.karros.ERROR00004", 
          I18N.msg("pharse.karros.xml.gpx.metadata.name"), Const.Text.MAX_TEXT_1000 + "")));
    }else if(gpxMetadataMapper.countByUserIdAndName(userId, gpx.getMetadata().getName()) > 0) {
      msgModel.addMsg(new Msg("ERROR00010", I18N.msg("error.karros.ERROR00010")));
    }
    
    if(!StringUtils.isEmpty(gpx.getMetadata().getDesc()) && 
        !Utils.inLength(gpx.getMetadata().getDesc(), Const.Text.MAX_TEXT_1000)) {
      msgModel.addMsg(new Msg("ERROR00004", I18N.msg("error.karros.ERROR00004", 
          I18N.msg("pharse.karros.xml.gpx.metadata.desc"), Const.Text.MAX_TEXT_1000 + "")));
    }

    if(!StringUtils.isEmpty(gpx.getMetadata().getAuthor()) && 
        !Utils.inLength(gpx.getMetadata().getAuthor(), Const.Text.MAX_TEXT_100)) {
      msgModel.addMsg(new Msg("ERROR00004", I18N.msg("error.karros.ERROR00004", 
          I18N.msg("pharse.karros.xml.gpx.metadata.author"), Const.Text.MAX_TEXT_100 + "")));
    }

    if(!StringUtils.isEmpty(gpx.getMetadata().getLink().getHref()) && 
        !Utils.inLength(gpx.getMetadata().getLink().getHref(), Const.Text.MAX_TEXT_1000)) {
      msgModel.addMsg(new Msg("ERROR00004", I18N.msg("error.karros.ERROR00004", 
          I18N.msg("pharse.karros.xml.gpx.metadata.link"), Const.Text.MAX_TEXT_1000 + "")));
    }
    
    if(!StringUtils.isEmpty(gpx.getMetadata().getLink().getText()) && 
        !Utils.inLength(gpx.getMetadata().getLink().getText(), Const.Text.MAX_TEXT_1000)) {
      msgModel.addMsg(new Msg("ERROR00004", I18N.msg("error.karros.ERROR00004", 
          I18N.msg("pharse.karros.xml.gpx.metadata.link_text"), Const.Text.MAX_TEXT_1000 + "")));
    }
    
    if(StringUtils.isEmpty(gpx.getMetadata().getTime())) {
      msgModel.addMsg(new Msg("ERROR00002", I18N.msg("error.karros.ERROR00002", 
          I18N.msg("pharse.karros.xml.gpx.metadata.time"))));
    }
    
    if(!CollectionUtils.isEmpty(gpx.getWpts())) {
      gpx.getWpts().forEach(wpt -> {
        if(wpt.getLat() == null) {
          msgModel.addMsg(new Msg("ERROR00002", I18N.msg("error.karros.ERROR00002", 
              I18N.msg("pharse.karros.xml.gpx.wpt.lat"))));
        }
        if(wpt.getLon() == null) {
          msgModel.addMsg(new Msg("ERROR00002", I18N.msg("error.karros.ERROR00002", 
              I18N.msg("pharse.karros.xml.gpx.wpt.lat"))));
        }
        
        if(StringUtils.isEmpty(wpt.getName())) {
          msgModel.addMsg(new Msg("ERROR00002", I18N.msg("error.karros.ERROR00002", 
              I18N.msg("pharse.karros.xml.gpx.wpt.name"))));
        }else if(!Utils.inLength(wpt.getName(), Const.Text.MAX_TEXT_1000)) {
          msgModel.addMsg(new Msg("ERROR00004", I18N.msg("error.karros.ERROR00004", 
              I18N.msg("pharse.karros.xml.gpx.wpt.name"), Const.Text.MAX_TEXT_1000 + "")));
        }
        
        if(!StringUtils.isEmpty(wpt.getSym()) && !Utils.inLength(wpt.getSym(), Const.Text.MAX_TEXT_1000)) {
          msgModel.addMsg(new Msg("ERROR00004", I18N.msg("error.karros.ERROR00004", 
              I18N.msg("pharse.karros.xml.gpx.wpt.sym"), Const.Text.MAX_TEXT_1000 + "")));
        }
      });
    }
    
    if(gpx.getTrk() == null) {
      msgModel.addMsg(new Msg("ERROR00002", I18N.msg("error.karros.ERROR00002", 
          I18N.msg("pharse.karros.xml.gpx.trk"))));
    }else if(CollectionUtils.isEmpty(gpx.getTrk().getTrksegs())) {
      msgModel.addMsg(new Msg("ERROR00002", I18N.msg("error.karros.ERROR00002", 
          I18N.msg("pharse.karros.xml.gpx.trk.trkSeg"))));
    }else {
      gpx.getTrk().getTrksegs().forEach(trkSeg -> {
        if(CollectionUtils.isEmpty(trkSeg.getTrkpts())) {
          msgModel.addMsg(new Msg("ERROR00002", I18N.msg("error.karros.ERROR00002", 
              I18N.msg("pharse.karros.xml.gpx.trk.trkSeg.trkpt"))));
        }else {
          trkSeg.getTrkpts().forEach(trkpt -> {
            if(trkpt.getLat() == null) {
              msgModel.addMsg(new Msg("ERROR00002", I18N.msg("error.karros.ERROR00002", 
                  I18N.msg("pharse.karros.xml.gpx.trk.trkSeg.trkpt.lat"))));
            }
            if(trkpt.getLon() == null) {
              msgModel.addMsg(new Msg("ERROR00002", I18N.msg("error.karros.ERROR00002", 
                  I18N.msg("pharse.karros.xml.gpx.trk.trkSeg.trkpt.lon"))));
            }
            if(trkpt.getEle() == null) {
              msgModel.addMsg(new Msg("ERROR00002", I18N.msg("error.karros.ERROR00002", 
                  I18N.msg("pharse.karros.xml.gpx.trk.trkSeg.trkpt.ele"))));
            }
            if(StringUtils.isEmpty(trkpt.getTime())) {
              msgModel.addMsg(new Msg("ERROR00002", I18N.msg("error.karros.ERROR00002", 
                  I18N.msg("pharse.karros.xml.gpx.trk.trkSeg.trkpt.time"))));
            }
          });
        }
      });
    }
    if(msgModel.hasMsg()) {
      throw new MsgResponseException(msgModel);
    }
  }
  
  public LatestTrack getLatestTrack(HttpServletRequest request) {
    Integer userId = ((UserSession)request.getSession(false).getAttribute(Const.User.USER_KEY)).getUser().getId();
    return gpxMetadataMapper.getLatestTrack(userId);
  }
  
  public GpxInfo getGpxInfo(Integer gpxMetadataId) {
    GpxInfo gpxInfo = new GpxInfo();
    gpxInfo.setGpxMetadata(gpxMetadataMapper.selectById(gpxMetadataId));
    gpxInfo.setGpxWpts(gpxWptMapper.selectByMetadataId(gpxMetadataId));
    gpxInfo.setGpxTrkpts(gpxTrkptMapper.selectByMetadataId(gpxMetadataId));
    return gpxInfo;
  }
}
