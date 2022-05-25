CREATE TABLE IF NOT EXISTS `client` (
  `api_key`     VARCHAR(20) NOT NULL,
  `description` VARCHAR(20) NOT NULL,
  `active`      TINYINT NOT NULL,

  PRIMARY KEY (`api_key`)
);
