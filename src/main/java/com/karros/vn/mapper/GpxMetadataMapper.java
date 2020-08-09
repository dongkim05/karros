package com.karros.vn.mapper;

import org.apache.ibatis.annotations.Param;

import com.karros.vn.model.gpx.GpxMetadata;
import com.karros.vn.model.gpx.LatestTrack;

public interface GpxMetadataMapper {
  Integer countByUserIdAndName(@Param("userId") Integer userId, @Param("name") String name);
  
  Integer insert(@Param("gpxMetadata") GpxMetadata gpxMetadata);
  
  LatestTrack getLatestTrack(@Param("userId") Integer userId);
  
  GpxMetadata selectById(@Param("id") Integer id);
}
