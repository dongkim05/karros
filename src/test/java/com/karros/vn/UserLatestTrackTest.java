package com.karros.vn;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
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
import com.karros.vn.model.User;
import com.karros.vn.model.UserSession;
import com.karros.vn.model.gpx.LatestTrack;
import com.karros.vn.service.UserService;
import com.karros.vn.utils.Const;

@SpringBootTest(classes =KarrosApplication.class)
@SpringJUnitWebConfig({InterceptorAppConfig.class})
@RunWith(SpringRunner.class)
@ActiveProfiles("test")
public class UserLatestTrackTest extends ApiTest {

  @Autowired
  private UserService userService;

  /**
   * Test latest track is null
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
    LatestTrack actualLastetTrack = userService.getLatestTrack(mockedRequest);
    assertNull("Latest track is not null",actualLastetTrack);
  } 

  /**
   * Test latest track is not null
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
    MockMultipartFile mockMultipartFile1 = new MockMultipartFile("file","sample1.gpx",
        "text/plain", file("karros/user/latesttrack/case2_1.gpx"));
    MockMultipartFile mockMultipartFile2 = new MockMultipartFile("file","sample2.gpx",
        "text/plain", file("karros/user/latesttrack/case2_2.gpx"));
    userService.uploadFile(mockMultipartFile1, mockedRequest);
    userService.uploadFile(mockMultipartFile2, mockedRequest);
    LatestTrack actualLastetTrack = userService.getLatestTrack(mockedRequest);
    assertNotNull("Latest track is null", actualLastetTrack);
    LatestTrack expected = new LatestTrack();
    expected.setGpxMetadataId(2);
    expected.setName("case 2_2");
    assertEquals(expected.getGpxMetadataId(), actualLastetTrack.getGpxMetadataId(), "Latest track metadata id is wrong");
    assertEquals(expected.getName(), actualLastetTrack.getName(), "Latest track name is wrong");
  } 
}
