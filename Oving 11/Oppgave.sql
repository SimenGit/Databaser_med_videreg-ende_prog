-- Listene under er tatt fra løsningsforslag på Oving 7.

DROP TABLE IF EXISTS leilighet;
DROP TABLE IF EXISTS andelseier;
DROP TABLE IF EXISTS bygning;
DROP TABLE IF EXISTS borettslag;
DROP TABLE IF EXISTS poststed;

-- Lager tabellene, legger inn NOT NULL- og UNIQUE-krav der det er naturlig
-- V�r forsiktig med � legge inn slike krav, det er vanskelig � forandre
-- dem i ettertid.

CREATE TABLE poststed(
  postnr SMALLINT,
  poststed VARCHAR(20) NOT NULL,
  CONSTRAINT poststed_pk PRIMARY KEY(postnr));

CREATE TABLE borettslag(
  bolag_navn VARCHAR(20),
  bolag_adr VARCHAR(40) NOT NULL UNIQUE,
  etabl_aar SMALLINT NOT NULL,
  postnr SMALLINT NOT NULL,
  CONSTRAINT borettslag_pk PRIMARY KEY(bolag_navn));

CREATE TABLE bygning(
  bygn_id INTEGER NOT NULL AUTO_INCREMENT,
  bygn_adr VARCHAR(40) NOT NULL,
  ant_etasjer INTEGER  DEFAULT 1,
  bolag_navn VARCHAR(20) NOT NULL,
  postnr SMALLINT NOT NULL,
  CONSTRAINT bygning_pk PRIMARY KEY(bygn_id));

CREATE TABLE leilighet(
  leil_nr INTEGER NOT NULL AUTO_INCREMENT,
  ant_rom SMALLINT NOT NULL,
  ant_kvm REAL NOT NULL,
  etasje SMALLINT DEFAULT 1,
  bygn_id INTEGER NOT NULL,
  and_eier_nr INTEGER NOT NULL UNIQUE,
  CONSTRAINT leilighet_pk PRIMARY KEY(leil_nr));

CREATE TABLE andelseier(
  and_eier_nr INTEGER NOT NULL AUTO_INCREMENT,
  fornavn VARCHAR(30) NOT NULL,
  etternavn VARCHAR(30) NOT NULL,
  telefon CHAR(15),
  ansiennitet SMALLINT,
  bolag_navn VARCHAR(20) NOT NULL,
  CONSTRAINT andelseier_pk PRIMARY KEY(and_eier_nr));

-- Fremmedn�kler

ALTER TABLE borettslag
  ADD CONSTRAINT borettslag_fk1 FOREIGN KEY(postnr)
REFERENCES poststed(postnr);

ALTER TABLE bygning
  ADD CONSTRAINT bygning_fk1 FOREIGN KEY(postnr)
REFERENCES poststed(postnr);

ALTER TABLE bygning
  ADD CONSTRAINT bygning_fk2 FOREIGN KEY(bolag_navn)
REFERENCES borettslag(bolag_navn);



ALTER TABLE andelseier
  ADD CONSTRAINT andelseier_fk2 FOREIGN KEY(bolag_navn)
REFERENCES borettslag(bolag_navn);

ALTER TABLE leilighet
  ADD CONSTRAINT leilighet_fk1 FOREIGN KEY(bygn_id)
REFERENCES bygning(bygn_id);

ALTER TABLE leilighet
  ADD CONSTRAINT leilighet_fk2 FOREIGN KEY(and_eier_nr)
REFERENCES andelseier(and_eier_nr);



-- Legger inn gyldige data

INSERT INTO poststed(postnr, poststed) VALUES(2020, 'Skedsmokorset');
INSERT INTO poststed(postnr, poststed) VALUES(6408, 'Aureosen');
INSERT INTO poststed(postnr, poststed) VALUES(7033, 'Trondheim');
INSERT INTO poststed(postnr, poststed) VALUES(7020, 'Trondheim');

INSERT INTO borettslag(bolag_navn, bolag_adr, etabl_aar, postnr) VALUES('Tertitten', 'sveien 100', 1980, 7020);
INSERT INTO borettslag(bolag_navn, bolag_adr, etabl_aar, postnr) VALUES('Sisiken', 'Brurd', 1990, 7033);
INSERT INTO borettslag(bolag_navn, bolag_adr, etabl_aar, postnr) VALUES('Lerken', 'Storgt 5', 2000, 6408);

INSERT INTO andelseier(and_eier_nr, fornavn, etternavn, telefon, ansiennitet, bolag_navn)
VALUES(DEFAULT, 'Even', 'Trulsbo', '56667743', 3, 'Tertitten');
INSERT INTO andelseier(and_eier_nr, fornavn, etternavn, telefon, ansiennitet, bolag_navn)
VALUES(DEFAULT, 'Anna', 'Olsen', '45674588', 10, 'Tertitten');
INSERT INTO andelseier(and_eier_nr, fornavn, etternavn, telefon, ansiennitet, bolag_navn)
VALUES(DEFAULT, 'Ingrid', 'Olsen', '45785388', 8, 'Tertitten');
INSERT INTO andelseier(and_eier_nr, fornavn, etternavn, telefon, ansiennitet, bolag_navn)
VALUES(DEFAULT, 'Arne', 'Torp', '78565388', 7, 'Tertitten');
INSERT INTO andelseier(and_eier_nr, fornavn, etternavn, telefon, ansiennitet, bolag_navn)
VALUES(DEFAULT, 'Arne', 'Martinsen', '78555388', 4, 'Sisiken');

INSERT INTO bygning(bygn_id, bygn_adr, ant_etasjer, bolag_navn, postnr) VALUES(DEFAULT, 'sveien 100a', 3, 'Tertitten', 7020);
INSERT INTO bygning(bygn_id, bygn_adr, ant_etasjer, bolag_navn, postnr) VALUES(DEFAULT, 'sveien 100b', 3, 'Tertitten', 7020);
INSERT INTO bygning(bygn_id, bygn_adr, ant_etasjer, bolag_navn, postnr) VALUES(DEFAULT, 'sveien 100c', 6, 'Tertitten', 7020);
INSERT INTO bygning(bygn_id, bygn_adr, ant_etasjer, bolag_navn, postnr) VALUES(DEFAULT, 'Storgt 10', 2, 'Sisiken', 7020);

-- bruker defaultverdien for antall etasjer
INSERT INTO bygning(bygn_id, bygn_adr, bolag_navn, postnr) VALUES(DEFAULT, 'sveien 100', 'Tertitten', 7020);

INSERT INTO leilighet(leil_nr, ant_rom, ant_kvm, etasje, bygn_id, and_eier_nr) VALUES(DEFAULT, 5, 110, 3, 1, 1);
INSERT INTO leilighet(leil_nr, ant_rom, ant_kvm, etasje, bygn_id, and_eier_nr) VALUES(DEFAULT, 5, 110, 3, 1, 2);
INSERT INTO leilighet(leil_nr, ant_rom, ant_kvm, etasje, bygn_id, and_eier_nr) VALUES(DEFAULT, 2, 50, 1, 3, 3);

-- bruker defaultverdien for etasje
INSERT INTO leilighet(leil_nr, ant_rom, ant_kvm, bygn_id, and_eier_nr) VALUES(DEFAULT, 5, 110, 1, 4);



-- Oppgaver:

-- OPPGAVE 01:
-- Finn alle borettslag etablert i årene 1975-1985.
SELECT borettslag.bolag_navn FROM borettslag
WHERE etabl_aar >= 1975 && etabl_aar <= 1985;

-- OPPGAVE 02:
/* Skriv ut en liste over andelseiere. Listen skal ha linjer som ser slik ut (tekster i kursiv er data fra databasen):
"fornavn etternavn, ansiennitet: ansiennitet år".
Listen skal være sortert på ansiennitet, de med lengst ansiennitet øverst. */
SELECT andelseier.fornavn, andelseier.etternavn, andelseier.ansiennitet FROM andelseier
ORDER BY andelseier.ansiennitet DESC;

-- OPPGAVE 03:
-- I hvilket år ble det eldste borettslaget etablert?
SELECT MIN(borettslag.etabl_aar)
FROM borettslag;

-- OPPGAVE 04:
-- Finn adressene til alle bygninger som inneholder leiligheter med minst tre rom.
SELECT bygning.bygn_adr
FROM bygning
INNER JOIN leilighet
ON bygning.bygn_id = leilighet.bygn_id
WHERE leilighet.ant_rom >= 3;

-- alle 3 rommene i sveien 100a har 5 rom. Så de dekker kravene.
-- Sjekker med spørringen under:
SELECT *
  FROM bygning
NATURAL JOIN leilighet;

-- OPPGAVE 05:
-- Finn antall bygninger i borettslaget "Tertitten".
SELECT bygning.bygn_id FROM bygning
LEFT OUTER JOIN borettslag
ON bygning.bolag_navn = borettslag.bolag_navn
WHERE borettslag.bolag_navn = 'Tertitten';

SELECT * FROM bygning
NATURAL JOIN borettslag;
-- 4 bygninger i tertitten.

-- OPPGAVE 06:
/* Lag en liste som viser antall bygninger i hvert enkelt borettslag. Listen skal være sortert på borettslagsnavn.
Husk at det kan finnes borettslag uten bygninger - de skal også med.
 */

SELECT borettslag.bolag_navn, count(bygn_id) AS ant_bygninger FROM borettslag
LEFT JOIN bygning
  ON(borettslag.bolag_navn = bygning.bolag_navn)
GROUP BY borettslag.bolag_navn
ORDER BY borettslag.bolag_navn DESC;


-- OPPGAVE 07: XXX
-- Finn antall leiligheter i borettslaget "Tertitten".

SELECT leilighet.leil_nr
FROM leilighet
LEFT OUTER JOIN bygning
  ON leilighet.bygn_id = bygning.bygn_id
LEFT OUTER JOIN borettslag
  ON bygning.bolag_navn = borettslag.bolag_navn
WHERE borettslag.bolag_navn = 'Tertitten';


-- OPPGAVE 08
-- Hvor høyt kan du bo i borettslaget "Tertitten"?
SELECT MAX(bygning.ant_etasjer)
FROM bygning
LEFT JOIN borettslag
  ON bygning.bolag_navn = borettslag.bolag_navn
WHERE borettslag.bolag_navn = 'Tertitten';


-- OPPGAVE 09
-- Finn navn og nummer til andelseiere som ikke har leilighet.

SELECT andelseier.fornavn, andelseier.etternavn, andelseier.telefon
FROM andelseier
LEFT JOIN leilighet
  ON (andelseier.and_eier_nr = leilighet.and_eier_nr)
WHERE leilighet.leil_nr IS NULL;

-- OPPGAVE 10
-- Finn antall andelseiere pr borettslag, sortert etter antallet. Husk at det kan finnes borettslag uten andelseiere - de skal også med.

SELECT andelseier.bolag_navn, COUNT(and_eier_nr) AS eier_antall FROM borettslag
LEFT JOIN andelseier
  ON(andelseier.bolag_navn = borettslag.bolag_navn)
GROUP BY andelseier.bolag_navn
ORDER BY eier_antall DESC;


-- OPPGAVE 11
-- Skriv ut en liste over alle andelseiere. For de som har leilighet, skal leilighetsnummeret skrives ut.

SELECT andelseier.fornavn, andelseier.etternavn, leilighet.leil_nr
FROM andelseier
LEFT JOIN leilighet
  ON andelseier.and_eier_nr = leilighet.and_eier_nr;

-- OPPGAVE 12
-- Hvilke borettslag har leiligheter med eksakt 4 rom?

SELECT borettslag.bolag_navn, leilighet.ant_rom FROM borettslag
  LEFT JOIN bygning
  ON borettslag.bolag_navn = bygning.bolag_navn
  LEFT JOIN leilighet
  ON bygning.bygn_id = leilighet.bygn_id
WHERE leilighet.ant_rom = '4';
--  Ingen har eksakt 4 rom.


-- OPPGAVE 13
-- Skriv ut en liste over antall andelseiere pr postnr og poststed, begrenset til de som bor i leiligheter tilknyttet et borettslag. Husk at postnummeret til disse er postnummeret til bygningen de bor i, og ikke postnummeret til borettslaget. Du trenger ikke ta med poststeder med 0 andelseiere. (Ekstraoppgave: Hva hvis vi vil ha med poststeder med 0 andelseiere?)

SELECT DISTINCT COUNT(andelseier.and_eier_nr) AS ant_eiere, poststed.poststed, bygning.postnr
FROM poststed NATURAL JOIN borettslag NATURAL JOIN bygning NATURAL JOIN andelseier
GROUP BY (and_eier_nr);

