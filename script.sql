CREATE TABLE `cliente` (
  `ntelefono` int NOT NULL,
  `nome_cliente` varchar(50) DEFAULT NULL,
  `cognome_cliente` varchar(50) DEFAULT NULL,
  `citta` varchar(50) DEFAULT NULL,
  `indirizzo` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`ntelefono`),
  UNIQUE KEY `ntelefono_UNIQUE` (`ntelefono`)
)

CREATE TABLE `animale` (
  `matricola` int NOT NULL,
  `ntelefono` int DEFAULT NULL,
  `nome` varchar(50) DEFAULT NULL,
  `tipo_animale` varchar(50) DEFAULT NULL,
  `prezzo` double DEFAULT NULL,
  `data_acquisto` date DEFAULT NULL,
  PRIMARY KEY (`matricola`),
  KEY `ntelefono_idx` (`ntelefono`),
  CONSTRAINT `ntelefono` FOREIGN KEY (`ntelefono`) REFERENCES `cliente` (`ntelefono`)
)