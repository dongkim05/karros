package com.karros.vn.model.gpx;

import java.time.LocalDateTime;

public class GpxTrkpt {
  
  private Integer gpxMetadataId;
  
  private Integer trkSeg;
  
  private Double lat;
  
  private Double lon;
  
  private Double ele;
  
  private LocalDateTime trkptTime;
  
  private Integer updatedBy;
  
  private Integer createdBy;

  public Integer getGpxMetadataId() {
    return gpxMetadataId;
  }

  public void setGpxMetadataId(Integer gpxMetadataId) {
    this.gpxMetadataId = gpxMetadataId;
  }

  public Integer getTrkSeg() {
    return trkSeg;
  }

  public void setTrkSeg(Integer trkSeg) {
    this.trkSeg = trkSeg;
  }

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

  public LocalDateTime getTrkptTime() {
    return trkptTime;
  }

  public void setTrkptTime(LocalDateTime trkptTime) {
    this.trkptTime = trkptTime;
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
}
