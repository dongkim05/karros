package com.karros.vn.model.gpx;

import java.time.LocalDateTime;

public class LatestTrack {
  private Integer gpxMetadataId;
  private String name;
  private String description;
  private LocalDateTime time;
  
  public Integer getGpxMetadataId() {
    return gpxMetadataId;
  }
  public void setGpxMetadataId(Integer gpxMetadataId) {
    this.gpxMetadataId = gpxMetadataId;
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
  public LocalDateTime getTime() {
    return time;
  }
  public void setTime(LocalDateTime time) {
    this.time = time;
  }
}
