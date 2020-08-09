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
import org.springframework.util.CollectionUtils;

import com.karros.vn.config.InterceptorAppConfig;
import com.karros.vn.model.User;
import com.karros.vn.model.UserSession;
import com.karros.vn.model.gpx.GpxInfo;
import com.karros.vn.service.UserService;
import com.karros.vn.utils.Const;

@SpringBootTest(classes =KarrosApplication.class)
@SpringJUnitWebConfig({InterceptorAppConfig.class})
@RunWith(SpringRunner.class)
@ActiveProfiles("test")
public class UserGpxInfoTest extends ApiTest {

  @Autowired
  private UserService userService;

  /**
   * Test gpx info is null
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
    MockMultipartFile mockMultipartFile1 = new MockMultipartFile("file","sample1.gpx",
        "text/plain", file("karros/user/gpxinfo/case2_1.gpx"));
    MockMultipartFile mockMultipartFile2 = new MockMultipartFile("file","sample2.gpx",
        "text/plain", file("karros/user/gpxinfo/case2_2.gpx"));
    userService.uploadFile(mockMultipartFile1, mockedRequest);
    userService.uploadFile(mockMultipartFile2, mockedRequest);
    GpxInfo actualGpxInfo = userService.getGpxInfo(12);
    assertNull("Gpx info metadata is not null",actualGpxInfo.getGpxMetadata());
  } 

  /**
   * Test gpx info is not null
   * 
   * 
   */
  @Test
  public void case2() {
    GpxInfo actualGpxInfo = userService.getGpxInfo(1);
    assertNotNull("Gpx info metadata is null",actualGpxInfo.getGpxMetadata());
    assertEquals(actualGpxInfo.getGpxMetadata().getName(), actualGpxInfo.getGpxMetadata().getName(), "Metadata name is wrong");
    if(!CollectionUtils.isEmpty(actualGpxInfo.getGpxWpts())) {
      assertEquals("Sorteamos por arriba", actualGpxInfo.getGpxWpts().get(0).getName());
    }
    assertEquals(315.86, actualGpxInfo.getGpxTrkpts().get(0).getEle());
  } 
}
