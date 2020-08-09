package com.karros.vn.model.gpx;

import java.time.LocalDateTime;

public class GpxMetadata {
  private Integer id;
  
  private Integer userId;
  
  private String name;
  
  private String description;
  
  private String author;
  
  private String link;
  
  private String linkText;
  
  private LocalDateTime metadataTime;
  
  private Integer updatedBy;
  
  private Integer createdBy;

  public Integer getUserId() {
    return userId;
  }

  public void setUserId(Integer userId) {
    this.userId = userId;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getAuthor() {
    return author;
  }

  public void setAuthor(String author) {
    this.author = author;
  }

  public String getLink() {
    return link;
  }

  public void setLink(String link) {
    this.link = link;
  }

  public String getLinkText() {
    return linkText;
  }

  public void setLinkText(String linkText) {
    this.linkText = linkText;
  }

  public LocalDateTime getMetadataTime() {
    return metadataTime;
  }

  public void setMetadataTime(LocalDateTime metadataTime) {
    this.metadataTime = metadataTime;
  }

  public Integer getUpdatedBy() {
    return updatedBy;
  }

  public void setUpdatedBy(Integer updatedBy) {
    this.updatedBy = updatedBy;
  }

  public Integer getCreatedBy() {
    return createdBy;
  }

  public void setCreatedBy(Integer createdBy) {
    this.createdBy = createdBy;
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }
}
