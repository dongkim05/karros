package com.karros.vn.model.xml;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "gpx")
@XmlAccessorType(XmlAccessType.FIELD)
public class Gpx {
  
  @XmlElement(name="metadata")
  private Metadata metadata;
  
  @XmlElement(name = "wpt")
  private List<Wpt> wpts;
  
  @XmlElement
  private Trk trk;
  
  public Metadata getMetadata() {
    return metadata;
  }

  public void setMetadata(Metadata metadata) {
    this.metadata = metadata;
  }

  public Trk getTrk() {
    return trk;
  }


  public void setTrk(Trk trk) {
    this.trk = trk;
  }

  public List<Wpt> getWpts() {
    return wpts;
  }

  public void setWpts(List<Wpt> wpts) {
    this.wpts = wpts;
  }
}
