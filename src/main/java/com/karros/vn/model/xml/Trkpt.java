package com.karros.vn.model.xml;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "trkpt")
@XmlAccessorType (XmlAccessType.FIELD)
public class Trkpt {
  @XmlAttribute
  private Double lat;
  
  @XmlAttribute
  private Double lon;
  
  @XmlElement
  private Double ele;
  
  @XmlElement  
  private String time;

  public Double getLat() {
    return lat;
  }

  public void setLat(Double lat) {
    this.lat = lat;
  }

  public Double getLon() {
    return lon;
  }

  public void setLon(Double lon) {
    this.lon = lon;
  }

  public Double getEle() {
    return ele;
  }

  public void setEle(Double ele) {
    this.ele = ele;
  }

  public String getTime() {
    return time;
  }

  public void setTime(String time) {
    this.time = time;
  }
}
