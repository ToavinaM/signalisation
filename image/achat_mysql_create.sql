CREATE TABLE `departement` (
	`id` int NOT NULL AUTO_INCREMENT,
	`nom` varchar(50) NOT NULL,
	`login` varchar(50) NOT NULL,
	`codeValidation` varchar(100) NOT NULL,
	PRIMARY KEY (`id`)
);

CREATE TABLE `produit` (
	`id` int NOT NULL AUTO_INCREMENT,
	`nom` varchar(50) NOT NULL,
	PRIMARY KEY (`id`)
);

CREATE TABLE `fournisseur` (
	`id` int NOT NULL AUTO_INCREMENT,
	`nom` varchar(50) NOT NULL,
	PRIMARY KEY (`id`)
);

CREATE TABLE `besoin` (
	`id` int NOT NULL AUTO_INCREMENT,
	`idDepartement` int NOT NULL,
	`idProduit` int NOT NULL,
	`quantite` int NOT NULL,
	`dateDemande` DATETIME NOT NULL,
	PRIMARY KEY (`id`)
);

CREATE TABLE `devis` (
	`id` int NOT NULL AUTO_INCREMENT,
	`idFournisseur` int NOT NULL,
	`quantite` int NOT NULL,
	`dateEnvoye` DATE NOT NULL,
	`dateLimite` DATE NOT NULL,
	PRIMARY KEY (`id`)
);

CREATE TABLE `proForma` (
	`id` int NOT NULL AUTO_INCREMENT,
	`idDevis` int NOT NULL,
	`dateArrive` DATE NOT NULL,
	`dateLimite` DATE NOT NULL,
	`prixUnitaire` double NOT NULL,
	PRIMARY KEY (`id`)
);

CREATE TABLE `note` (
	`id` int NOT NULL AUTO_INCREMENT,
	`idFournisseur` int NOT NULL,
	`idProduit` int NOT NULL,
	`note` int NOT NULL,
	PRIMARY KEY (`id`)
);

CREATE TABLE `commande` (
	`id` int NOT NULL AUTO_INCREMENT,
	`idFournisseur` int NOT NULL,
	`dateCommande` DATE NOT NULL,
	`dateLimite` DATE NOT NULL,
	`etat` CHAR(1) NOT NULL,
	PRIMARY KEY (`id`)
);

CREATE TABLE `commandeDetail` (
	`id` int NOT NULL AUTO_INCREMENT,
	`idCommande` int NOT NULL,
	`idProduit` int NOT NULL,
	`quantite` int NOT NULL,
	`prixUnitaire` double NOT NULL,
	PRIMARY KEY (`id`)
);

CREATE TABLE `livraison` (
	`id` int NOT NULL AUTO_INCREMENT,
	`idCommande` int NOT NULL,
	`dateArrive` DATE NOT NULL,
	PRIMARY KEY (`id`)
);

CREATE TABLE `livraisonDetail` (
	`id` int NOT NULL AUTO_INCREMENT,
	`idLivraison` int NOT NULL,
	`idProduit` int NOT NULL,
	`quantite` int NOT NULL,
	PRIMARY KEY (`id`)
);

CREATE TABLE `reception` (
	`id` int NOT NULL AUTO_INCREMENT,
	`idLivraisonDetail` int NOT NULL,
	`quantite` int NOT NULL,
	PRIMARY KEY (`id`)
);

ALTER TABLE `besoin` ADD CONSTRAINT `besoin_fk0` FOREIGN KEY (`idDepartement`) REFERENCES `departement`(`id`);

ALTER TABLE `besoin` ADD CONSTRAINT `besoin_fk1` FOREIGN KEY (`idProduit`) REFERENCES `produit`(`id`);

ALTER TABLE `devis` ADD CONSTRAINT `devis_fk0` FOREIGN KEY (`idFournisseur`) REFERENCES `fournisseur`(`id`);

ALTER TABLE `proForma` ADD CONSTRAINT `proForma_fk0` FOREIGN KEY (`idDevis`) REFERENCES `devis`(`id`);

ALTER TABLE `note` ADD CONSTRAINT `note_fk0` FOREIGN KEY (`idFournisseur`) REFERENCES `fournisseur`(`id`);

ALTER TABLE `note` ADD CONSTRAINT `note_fk1` FOREIGN KEY (`idProduit`) REFERENCES `produit`(`id`);

ALTER TABLE `commande` ADD CONSTRAINT `commande_fk0` FOREIGN KEY (`idFournisseur`) REFERENCES `fournisseur`(`id`);

ALTER TABLE `commandeDetail` ADD CONSTRAINT `commandeDetail_fk0` FOREIGN KEY (`idCommande`) REFERENCES `commande`(`id`);

ALTER TABLE `commandeDetail` ADD CONSTRAINT `commandeDetail_fk1` FOREIGN KEY (`idProduit`) REFERENCES `produit`(`id`);

ALTER TABLE `livraison` ADD CONSTRAINT `livraison_fk0` FOREIGN KEY (`idCommande`) REFERENCES `commande`(`id`);

ALTER TABLE `livraisonDetail` ADD CONSTRAINT `livraisonDetail_fk0` FOREIGN KEY (`idLivraison`) REFERENCES `livraison`(`id`);

ALTER TABLE `livraisonDetail` ADD CONSTRAINT `livraisonDetail_fk1` FOREIGN KEY (`idProduit`) REFERENCES `produit`(`id`);

ALTER TABLE `reception` ADD CONSTRAINT `reception_fk0` FOREIGN KEY (`idLivraisonDetail`) REFERENCES `livraisonDetail`(`id`);













