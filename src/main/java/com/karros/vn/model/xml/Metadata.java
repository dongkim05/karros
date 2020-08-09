package com.karros.vn.model.xml;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class Metadata {
  @XmlElement
  private String name;
  
  @XmlElement
  private String desc;
  
  @XmlElement
  private String author;
  
  @XmlElement
  private Link link;
  
  @XmlElement
  private String time;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getDesc() {
    return desc;
  }

  public void setDesc(String desc) {
    this.desc = desc;
  }

  public String getAuthor() {
    return author;
  }

  public void setAuthor(String author) {
    this.author = author;
  }

  public Link getLink() {
    return link;
  }

  public void setLink(Link link) {
    this.link = link;
  }

  public String getTime() {
    return time;
  }

  public void setTime(String time) {
    this.time = time;
  }
}
