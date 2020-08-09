CREATE TABLE users (
  id INTEGER PRIMARY KEY auto_increment,
  user_name VARCHAR(64) NOT NULL,
  password VARCHAR(1000) NOT NULL,
  updated_at datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_by INTEGER,
  created_at datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  created_by INTEGER,
  delete_flag TINYINT(1) DEFAULT 0
  );
  
CREATE TABLE login_token (
  id INTEGER PRIMARY KEY auto_increment,
  user_id INTEGER NOT NULL,
  token VARCHAR(1000) NOT NULL,
  uuid  VARCHAR(100) NOT NULL,
  updated_at datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_by INTEGER,
  created_at datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  created_by INTEGER,
  delete_flag TINYINT(1) DEFAULT 0
  );
  
CREATE TABLE gpx_metadata (
  id INTEGER PRIMARY KEY auto_increment,
  user_id INTEGER NOT NULL,
  name VARCHAR(1000) NOT NULL,
  description VARCHAR(1000),
  author  VARCHAR(100),
  link  VARCHAR(1000),
  link_text  VARCHAR(1000),
  metadata_time datetime NOT NULL,
  updated_at datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_by INTEGER,
  created_at datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  created_by INTEGER,
  delete_flag TINYINT(1) DEFAULT 0
  );
  
CREATE TABLE gpx_wpt (
  id INTEGER PRIMARY KEY auto_increment,
  gpx_metadata_id INTEGER NOT NULL,
  lat DECIMAL(11,7) NOT NULL,
  lon DECIMAL(11,7) NOT NULL,
  name VARCHAR(1000) NOT NULL,
  sym VARCHAR(1000),
  updated_at datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_by INTEGER,
  created_at datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  created_by INTEGER,
  delete_flag TINYINT(1) DEFAULT 0
  );
  
CREATE TABLE gpx_trkpt (
  id INTEGER PRIMARY KEY auto_increment,
  gpx_metadata_id INTEGER NOT NULL,
  trk_seg INTEGER NOT NULL,
  lat DECIMAL(11,7) NOT NULL,
  lon DECIMAL(11,7) NOT NULL,
  ele DOUBLE NOT NULL,
  trkpt_time datetime NOT NULL,
  updated_at datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_by INTEGER,
  created_at datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  created_by INTEGER,
  delete_flag TINYINT(1) DEFAULT 0
  );