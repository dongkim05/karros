package com.karros.vn.model.gpx;

import java.util.List;

public class GpxInfo {
  private GpxMetadata gpxMetadata;
  
  private List<GpxWpt> gpxWpts;
  
  private List<GpxTrkpt> gpxTrkpts;

  public GpxMetadata getGpxMetadata() {
    return gpxMetadata;
  }

  public void setGpxMetadata(GpxMetadata gpxMetadata) {
    this.gpxMetadata = gpxMetadata;
  }

  public List<GpxWpt> getGpxWpts() {
    return gpxWpts;
  }

  public void setGpxWpts(List<GpxWpt> gpxWpts) {
    this.gpxWpts = gpxWpts;
  }

  public List<GpxTrkpt> getGpxTrkpts() {
    return gpxTrkpts;
  }

  public void setGpxTrkpts(List<GpxTrkpt> gpxTrkpts) {
    this.gpxTrkpts = gpxTrkpts;
  }
  
}
