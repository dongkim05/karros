package com.karros.vn.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.karros.vn.model.gpx.GpxWpt;

public interface GpxWptMapper {
  Integer insertMultiple(@Param("gpxWpts") List<GpxWpt> gpxWpts);
  
  List<GpxWpt> selectByMetadataId(@Param("gpxMetadataId") Integer gpxMetadataId);
}
