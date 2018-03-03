 CREATE TABLE station (
 id BIGINT NOT NULL AUTO_INCREMENT,
 name VARCHAR (64),
  height DOUBLE PRECISION (4,3),
  PRIMARY KEY (id)
    );
--DROP TABLE station;

CREATE TABLE raw_point (
 id BIGINT NOT NULL AUTO_INCREMENT,
 name MEDIUMINT,
 distance DOUBLE PRECISION(7,3),
 horizontal_angel DOUBLE PRECISION (7,4),
 vertical_angel DOUBLE PRECISION (7,4),
 height DOUBLE PRECISION(4,3),
 station_id BIGINT,
 PRIMARY KEY (id),
 FOREIGN KEY (station_id) REFERENCES station(id)
 );
 --DROP TABLE raw_point;

CREATE TABLE point (
id BIGINT NOT NULL AUTO_INCREMENT,
name VARCHAR(20),
x DOUBLE PRECISION(10,3),
y DOUBLE PRECISION(10,3),
z DOUBLE PRECISION(6,3),
station_id BIGINT,
PRIMARY KEY (id),
FOREIGN KEY (station_id) REFERENCES station(id)
);
 --DROP TABLE point;

-- coordinate_id BIGINT FOREIGN KEY REFERENCES point(id),