package com.karros.vn.model.xml;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "trkseg")
@XmlAccessorType (XmlAccessType.FIELD)
public class Trkseg {

  @XmlElement(name = "trkpt")
  private List<Trkpt> trkpts;

  public List<Trkpt> getTrkpts() {
    return trkpts;
  }

  public void setTrkpts(List<Trkpt> trkpts) {
    this.trkpts = trkpts;
  }
}
