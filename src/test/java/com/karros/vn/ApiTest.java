package com.karros.vn;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.junit.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultHandler;

import com.karros.vn.utils.I18N;

public class ApiTest {
  @Autowired
  private ResourceBundleMessageSource messageSource;

  private static final Logger logger = LoggerFactory.getLogger(ApiTest.class);
  @Before
  public void setup() {
    I18N.messageSource = messageSource;
  }

  public byte[] file(String path) {
    ClassLoader classLoader = getClass().getClassLoader();
    File file = new File(classLoader.getResource(path).getFile());
    String filePath = file.getAbsolutePath().replace("%20", " ");
    File file1 = new File(filePath);
    byte[] bytesArray = new byte[(int) file1.length()]; 

    FileInputStream fis;
    try {
      fis = new FileInputStream(file1);
      fis.read(bytesArray); //read file into bytes[]
      fis.close();
    } catch (FileNotFoundException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }			
    return bytesArray;
  }

  public ResultHandler printResponse() {
    return new ResultHandler() {	
      @Override
      public void handle(MvcResult result) throws Exception {
        logger.debug("API({}): status ({}):{}"
            ,result.getRequest().getRequestURI()
            ,result.getResponse().getStatus()
            ,result.getResponse().getContentAsString());	
      }
    };
  }
}
