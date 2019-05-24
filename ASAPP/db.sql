CREATE TABLE `pro2019`.`User` (
  `id` int(11) NOT NULL AUTO_INCREMENT PRIMARY KEY,
  `username` varchar(256) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `telegramUsername` varchar(256) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `idTelegram` int(11) NOT NULL,
  `password` varchar(256) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `rules` varchar(10000),
  `langue` enum('FR','EN') CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT 'EN'
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
