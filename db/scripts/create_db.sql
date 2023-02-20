-- mysql -u root --password=super-secret information_schema
-- mysql -u service --password=secret moodtracker

create database moodtracker;

use moodtracker;

CREATE USER 'service'@'%' IDENTIFIED BY 'secret';
GRANT INSERT, UPDATE, DELETE, SELECT on moodtracker.* TO 'service'@'%' WITH GRANT OPTION;

CREATE TABLE `Mood` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `personal_token` varchar(36) NOT NULL DEFAULT '',
  `mood_value` int(1) NOT NULL DEFAULT 1,
  `comment` varchar(350) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;
