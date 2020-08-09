package com.karros.vn.utils;

import java.io.File;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import com.karros.vn.model.xml.Gpx;

public class XmlUtils {
  public static Gpx parseGpxXml(File file) {
    try {    
      JAXBContext jaxbContext = JAXBContext.newInstance(Gpx.class);    

      Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();    

      return (Gpx) jaxbUnmarshaller.unmarshal(file);
    } catch (JAXBException e) {
      e.printStackTrace(); 
    } 
    
    return null;
  }
}
