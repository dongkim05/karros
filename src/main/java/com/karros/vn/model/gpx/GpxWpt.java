package com.karros.vn.model.gpx;

public class GpxWpt {
  private Integer gpxMetadataId;
  
  private Double lat;
  
  private Double lon;
  
  private String name;
  
  private String sym;
  
  private Integer updatedBy;
  
  private Integer createdBy;

  public Integer getGpxMetadataId() {
    return gpxMetadataId;
  }

  public void setGpxMetadataId(Integer gpxMetadataId) {
    this.gpxMetadataId = gpxMetadataId;
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
