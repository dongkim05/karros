package com.karros.vn.model.xml;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "trk")
@XmlAccessorType (XmlAccessType.FIELD)
public class Trk {
  
  @XmlElement(name = "trkseg")
  private List<Trkseg> trksegs;

  public List<Trkseg> getTrksegs() {
    return trksegs;
  }

  public void setTrksegs(List<Trkseg> trksegs) {
    this.trksegs = trksegs;
  }
}
