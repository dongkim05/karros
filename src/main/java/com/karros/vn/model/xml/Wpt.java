package com.karros.vn.model.xml;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "wpt")
@XmlAccessorType (XmlAccessType.FIELD)
public class Wpt {
  
  @XmlAttribute
  private Double lat;
  
  @XmlAttribute
  private Double lon;
  
  @XmlElement
  private String name;
  
  @XmlElement
  private String sym;

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

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getSym() {
    return sym;
  }

  public void setSym(String sym) {
    this.sym = sym;
  }
}
