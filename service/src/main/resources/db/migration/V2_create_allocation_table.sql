CREATE TABLE IF NOT EXISTS `allocation` (
  `id`              BIGINT NOT NULL AUTO_INCREMENT,
  `room_id`         BIGINT NOT NULL,
  `employee_name`   VARCHAR(20) NOT NULL,
  `employee_email`  VARCHAR(30) NOT NULL,
  `subject`         VARCHAR(60) NOT NULL,
  `start_at`        DATETIME(3) NOT NULL,
  `end_at`          DATETIME(3) NOT NULL,
  `created_at`      DATETIME(3) NOT NULL,
  `updated_at`      DATETIME(3) NOT NULL,

  PRIMARY KEY (`id`),
  CONSTRAINT `fk-allocation-room_id-room-id` FOREIGN KEY (`room_id`) REFERENCES `room` (`id`)
);
