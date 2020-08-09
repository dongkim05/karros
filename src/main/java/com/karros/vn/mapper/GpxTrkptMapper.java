package com.karros.vn.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.karros.vn.model.gpx.GpxTrkpt;

public interface GpxTrkptMapper {
  Integer insertMultiple(@Param("gpxTrkpts") List<GpxTrkpt> gpxTrkpts);
  
  List<GpxTrkpt> selectByMetadataId(@Param("gpxMetadataId") Integer gpxMetadataId);
}
