CREATE TABLE Magasin (
  idMagasin INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
  nomMagasin VARCHAR(50)
);

CREATE TABLE Produit (
  idProduit INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
  nomProduit VARCHAR(50)
);

CREATE TABLE Entrepot (
  idEntrepot INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
  nomEntrepot VARCHAR(50)
);

CREATE TABLE Etat (
  idEtat INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
  descriptionEtat VARCHAR(50)
);

CREATE TABLE Commande (
  idCommande INT PRIMARY KEY NOT NULL,
  idMagasin  INT             NOT NULL,
  idProduit  INT             NOT NULL,
  idEntrepot INT             NOT NULL,
  CONSTRAINT fk_idMagasin_magasin   FOREIGN KEY (idMagasin)  REFERENCES Magasin(idMagasin),
  CONSTRAINT fk_idProduit_produit   FOREIGN KEY (idProduit)  REFERENCES Produit(idProduit),
  CONSTRAINT fk_idEntrepot_entrepot FOREIGN KEY (identrepot) REFERENCES Entrepot(idEntrepot)
);

CREATE TABLE CommandeStatutLog (
  idLog      INT PRIMARY KEY NOT NULL,
  idCommande INT             NOT NULL,
  idEtat     INT             NOT NULL,
  Horodatage TIMESTAMP,
  Emmeteur   VARCHAR(50),
  Action     VARCHAR(50),
  CONSTRAINT fk_idCommande_commande FOREIGN KEY (idCommande) REFERENCES Commande(idCommande),
  CONSTRAINT fk_idEtat_etat         FOREIGN KEY (idEtat)     REFERENCES Etat(idEtat)
);

CREATE TABLE DatabaseVersionning (
  idVersion VARCHAR(20)
);
