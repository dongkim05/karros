package com.karros.vn;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.context.junit4.SpringRunner;

import com.karros.vn.config.InterceptorAppConfig;
import com.karros.vn.model.Msg;
import com.karros.vn.model.MsgModel;
import com.karros.vn.model.User;
import com.karros.vn.model.UserSession;
import com.karros.vn.model.exception.MsgResponseException;
import com.karros.vn.service.UserService;
import com.karros.vn.utils.Const;
import com.karros.vn.utils.I18N;

@SpringBootTest(classes =KarrosApplication.class)
@SpringJUnitWebConfig({InterceptorAppConfig.class})
@RunWith(SpringRunner.class)
@ActiveProfiles("test")
public class UserUploadTest extends ApiTest {

  @Autowired
  private UserService userService;

  /**
   * Test upload successfully
   * 
   * 
   */
    @Test
  public void case1() {
    HttpServletRequest  mockedRequest = Mockito.mock(HttpServletRequest.class); 
    HttpSession mockedSession = Mockito.mock(HttpSession.class);
    UserSession userSession = new UserSession();
    User user = new User();
    user.setId(1);
    userSession.setUser(user);
    when(mockedSession.getAttribute(Const.User.USER_KEY)).thenReturn(userSession);
    when(mockedRequest.getSession(false)).thenReturn(mockedSession);
    MockMultipartFile mockMultipartFile = new MockMultipartFile("file","sample.gpx",
        "text/plain", file("karros/user/upload/case1.gpx"));
    MsgModel msgModel = userService.uploadFile(mockMultipartFile, mockedRequest);
    MsgModel expected = new MsgModel(new Msg("INFO00004", I18N.msg("info.karros.INFO00004")));
    assertTrue("Message size is wrong",msgModel.getMessages() != null && msgModel.getMessages().size() == 1);
    assertEquals(expected.getMessages().get(0).getCode(), msgModel.getMessages().get(0).getCode(), "Message code does not match");
    assertEquals(expected.getMessages().get(0).getMsg(), msgModel.getMessages().get(0).getMsg(), "Message does not match");
  }

  /**
   * Test upload empty gpx name
   * 
   * 
   */
  @Test
  public void case2() {
    HttpServletRequest  mockedRequest = Mockito.mock(HttpServletRequest.class); 
    HttpSession mockedSession = Mockito.mock(HttpSession.class);
    UserSession userSession = new UserSession();
    User user = new User();
    user.setId(1);
    userSession.setUser(user);
    when(mockedSession.getAttribute(Const.User.USER_KEY)).thenReturn(userSession);
    when(mockedRequest.getSession(false)).thenReturn(mockedSession);
    MockMultipartFile mockMultipartFile = new MockMultipartFile("file","sample.gpx",
        "text/plain", file("karros/user/upload/case2.gpx"));
    
    MsgModel msgModel = null;
    MsgResponseException thrown = assertThrows(
        MsgResponseException.class,
        () -> userService.uploadFile(mockMultipartFile, mockedRequest),
        "Expected uploadFile() to throw, but it didn't"
        );
    msgModel = thrown.getMsgModel();
    MsgModel expected = new MsgModel(new Msg("ERROR00002", I18N.msg("error.karros.ERROR00002", 
        I18N.msg("pharse.karros.xml.gpx.metadata.name"))));
    assertTrue("Message size is wrong",msgModel.getMessages() != null && msgModel.getMessages().size() == 1);
    assertEquals(expected.getMessages().get(0).getCode(), msgModel.getMessages().get(0).getCode(), "Message code does not match");
    assertEquals(expected.getMessages().get(0).getMsg(), msgModel.getMessages().get(0).getMsg(), "Message does not match");
  } 
  
  /**
   * Test upload gpx name is greater than 1000
   * 
   * 
   */
  @Test
  public void case3() {
    HttpServletRequest  mockedRequest = Mockito.mock(HttpServletRequest.class); 
    HttpSession mockedSession = Mockito.mock(HttpSession.class);
    UserSession userSession = new UserSession();
    User user = new User();
    user.setId(1);
    userSession.setUser(user);
    when(mockedSession.getAttribute(Const.User.USER_KEY)).thenReturn(userSession);
    when(mockedRequest.getSession(false)).thenReturn(mockedSession);
    MockMultipartFile mockMultipartFile = new MockMultipartFile("file","sample.gpx",
        "text/plain", file("karros/user/upload/case3.gpx"));
    
    MsgModel msgModel = null;
    MsgResponseException thrown = assertThrows(
        MsgResponseException.class,
        () -> userService.uploadFile(mockMultipartFile, mockedRequest),
        "Expected uploadFile() to throw, but it didn't"
        );
    msgModel = thrown.getMsgModel();
    MsgModel expected = new MsgModel(new Msg("ERROR00004", I18N.msg("error.karros.ERROR00004", 
        I18N.msg("pharse.karros.xml.gpx.metadata.name"), Const.Text.MAX_TEXT_1000 + "")));
    assertTrue("Message size is wrong",msgModel.getMessages() != null && msgModel.getMessages().size() == 1);
    assertEquals(expected.getMessages().get(0).getCode(), msgModel.getMessages().get(0).getCode(), "Message code does not match");
    assertEquals(expected.getMessages().get(0).getMsg(), msgModel.getMessages().get(0).getMsg(), "Message does not match");
  } 
  
  /**
   * Test upload gpx name is duplicated
   * 
   * 
   */
  @Test
  public void case4() {
    HttpServletRequest  mockedRequest = Mockito.mock(HttpServletRequest.class); 
    HttpSession mockedSession = Mockito.mock(HttpSession.class);
    UserSession userSession = new UserSession();
    User user = new User();
    user.setId(1);
    userSession.setUser(user);
    when(mockedSession.getAttribute(Const.User.USER_KEY)).thenReturn(userSession);
    when(mockedRequest.getSession(false)).thenReturn(mockedSession);
    MockMultipartFile mockMultipartFile = new MockMultipartFile("file","sample.gpx",
        "text/plain", file("karros/user/upload/case4.gpx"));
    userService.uploadFile(mockMultipartFile, mockedRequest);
    MsgModel msgModel = null;
    MsgResponseException thrown = assertThrows(
        MsgResponseException.class,
        () -> userService.uploadFile(mockMultipartFile, mockedRequest),
        "Expected uploadFile() to throw, but it didn't"
        );
    msgModel = thrown.getMsgModel();
    MsgModel expected = new MsgModel(new Msg("ERROR00010", I18N.msg("error.karros.ERROR00010")));
    assertTrue("Message size is wrong",msgModel.getMessages() != null && msgModel.getMessages().size() == 1);
    assertEquals(expected.getMessages().get(0).getCode(), msgModel.getMessages().get(0).getCode(), "Message code does not match");
    assertEquals(expected.getMessages().get(0).getMsg(), msgModel.getMessages().get(0).getMsg(), "Message does not match");
  } 
  
  /**
   * Test upload gpx 
   *  - Description is greater than 1000
   *  - Author is greater than 100
   *  - Link is greater than 1000
   *  - Link text is greater than 1000
   *  - Time is empty
   *  - Wpt lat and lon is null
   *  - Wpt name is null and name,sym is greater than 1000
   *  - Trkseg is empty
   *  - Trkpt lat,lon,ele and time is null
   * 
   * 
   */
  @Test
  public void case5() {
    HttpServletRequest  mockedRequest = Mockito.mock(HttpServletRequest.class); 
    HttpSession mockedSession = Mockito.mock(HttpSession.class);
    UserSession userSession = new UserSession();
    User user = new User();
    user.setId(1);
    userSession.setUser(user);
    when(mockedSession.getAttribute(Const.User.USER_KEY)).thenReturn(userSession);
    when(mockedRequest.getSession(false)).thenReturn(mockedSession);
    MockMultipartFile mockMultipartFile = new MockMultipartFile("file","sample.gpx",
        "text/plain", file("karros/user/upload/case5.gpx"));
    MsgModel msgModel = null;
    MsgResponseException thrown = assertThrows(
        MsgResponseException.class,
        () -> userService.uploadFile(mockMultipartFile, mockedRequest),
        "Expected uploadFile() to throw, but it didn't"
        );
    msgModel = thrown.getMsgModel();
    MsgModel expected = new MsgModel(new Msg("ERROR00004", I18N.msg("error.karros.ERROR00004", 
        I18N.msg("pharse.karros.xml.gpx.metadata.desc"), Const.Text.MAX_TEXT_1000 + "")));
    expected.addMsg(new Msg("ERROR00004", I18N.msg("error.karros.ERROR00004", 
        I18N.msg("pharse.karros.xml.gpx.metadata.author"), Const.Text.MAX_TEXT_100 + "")));
    expected.addMsg(new Msg("ERROR00004", I18N.msg("error.karros.ERROR00004", 
        I18N.msg("pharse.karros.xml.gpx.metadata.link"), Const.Text.MAX_TEXT_1000 + "")));
    expected.addMsg(new Msg("ERROR00004", I18N.msg("error.karros.ERROR00004", 
        I18N.msg("pharse.karros.xml.gpx.metadata.link_text"), Const.Text.MAX_TEXT_1000 + "")));
    expected.addMsg(new Msg("ERROR00002", I18N.msg("error.karros.ERROR00002", 
        I18N.msg("pharse.karros.xml.gpx.metadata.time"))));
    expected.addMsg(new Msg("ERROR00002", I18N.msg("error.karros.ERROR00002", 
        I18N.msg("pharse.karros.xml.gpx.wpt.lat"))));
    expected.addMsg(new Msg("ERROR00002", I18N.msg("error.karros.ERROR00002", 
        I18N.msg("pharse.karros.xml.gpx.wpt.lat"))));
    expected.addMsg(new Msg("ERROR00002", I18N.msg("error.karros.ERROR00002", 
        I18N.msg("pharse.karros.xml.gpx.wpt.name"))));
    expected.addMsg(new Msg("ERROR00004", I18N.msg("error.karros.ERROR00004", 
        I18N.msg("pharse.karros.xml.gpx.wpt.name"), Const.Text.MAX_TEXT_1000 + "")));
    expected.addMsg(new Msg("ERROR00004", I18N.msg("error.karros.ERROR00004", 
        I18N.msg("pharse.karros.xml.gpx.wpt.sym"), Const.Text.MAX_TEXT_1000 + "")));
    expected.addMsg(new Msg("ERROR00002", I18N.msg("error.karros.ERROR00002", 
        I18N.msg("pharse.karros.xml.gpx.trk.trkSeg.trkpt"))));
    expected.addMsg(new Msg("ERROR00002", I18N.msg("error.karros.ERROR00002", 
        I18N.msg("pharse.karros.xml.gpx.trk.trkSeg.trkpt.lat"))));
    expected.addMsg(new Msg("ERROR00002", I18N.msg("error.karros.ERROR00002", 
        I18N.msg("pharse.karros.xml.gpx.trk.trkSeg.trkpt.lon"))));
    expected.addMsg(new Msg("ERROR00002", I18N.msg("error.karros.ERROR00002", 
        I18N.msg("pharse.karros.xml.gpx.trk.trkSeg.trkpt.ele"))));
    expected.addMsg(new Msg("ERROR00002", I18N.msg("error.karros.ERROR00002", 
        I18N.msg("pharse.karros.xml.gpx.trk.trkSeg.trkpt.time"))));
    assertTrue("Message size is wrong",msgModel.getMessages() != null && msgModel.getMessages().size() == 15);
    for(int i = 0; i < 15; i++) {
      Msg exMsg = expected.getMessages().get(i);
      Msg msg = msgModel.getMessages().get(i);
      assertEquals(exMsg.getCode(), msg.getCode(), "Message code does not match");
      assertEquals(exMsg.getMsg(), msg.getMsg(), "Message does not match");
    }
  }
  
  /**
   * Test upload trk seg is empty
   * 
   * 
   */
  @Test
  public void case6() {
    HttpServletRequest  mockedRequest = Mockito.mock(HttpServletRequest.class); 
    HttpSession mockedSession = Mockito.mock(HttpSession.class);
    UserSession userSession = new UserSession();
    User user = new User();
    user.setId(1);
    userSession.setUser(user);
    when(mockedSession.getAttribute(Const.User.USER_KEY)).thenReturn(userSession);
    when(mockedRequest.getSession(false)).thenReturn(mockedSession);
    MockMultipartFile mockMultipartFile = new MockMultipartFile("file","sample.gpx",
        "text/plain", file("karros/user/upload/case6.gpx"));
    MsgModel msgModel = null;
    MsgResponseException thrown = assertThrows(
        MsgResponseException.class,
        () -> userService.uploadFile(mockMultipartFile, mockedRequest),
        "Expected uploadFile() to throw, but it didn't"
        );
    msgModel = thrown.getMsgModel();
    MsgModel expected = new MsgModel(new Msg("ERROR00002", I18N.msg("error.karros.ERROR00002", 
        I18N.msg("pharse.karros.xml.gpx.trk.trkSeg"))));
    assertTrue("Message size is wrong",msgModel.getMessages() != null && msgModel.getMessages().size() == 1);
    assertEquals(expected.getMessages().get(0).getCode(), msgModel.getMessages().get(0).getCode(), "Message code does not match");
    assertEquals(expected.getMessages().get(0).getMsg(), msgModel.getMessages().get(0).getMsg(), "Message does not match");
  }  
  
  /**
   * Test upload trk is null
   * 
   * 
   */
  @Test
  public void case7() {
    HttpServletRequest  mockedRequest = Mockito.mock(HttpServletRequest.class); 
    HttpSession mockedSession = Mockito.mock(HttpSession.class);
    UserSession userSession = new UserSession();
    User user = new User();
    user.setId(1);
    userSession.setUser(user);
    when(mockedSession.getAttribute(Const.User.USER_KEY)).thenReturn(userSession);
    when(mockedRequest.getSession(false)).thenReturn(mockedSession);
    MockMultipartFile mockMultipartFile = new MockMultipartFile("file","sample.gpx",
        "text/plain", file("karros/user/upload/case7.gpx"));
    MsgModel msgModel = null;
    MsgResponseException thrown = assertThrows(
        MsgResponseException.class,
        () -> userService.uploadFile(mockMultipartFile, mockedRequest),
        "Expected uploadFile() to throw, but it didn't"
        );
    msgModel = thrown.getMsgModel();
    MsgModel expected = new MsgModel(new Msg("ERROR00002", I18N.msg("error.karros.ERROR00002", 
        I18N.msg("pharse.karros.xml.gpx.trk"))));
    assertTrue("Message size is wrong",msgModel.getMessages() != null && msgModel.getMessages().size() == 1);
    assertEquals(expected.getMessages().get(0).getCode(), msgModel.getMessages().get(0).getCode(), "Message code does not match");
    assertEquals(expected.getMessages().get(0).getMsg(), msgModel.getMessages().get(0).getMsg(), "Message does not match");
  }   
}
