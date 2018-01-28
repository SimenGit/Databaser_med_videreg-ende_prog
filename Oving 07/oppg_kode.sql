DROP TABLE IF EXISTS CERTIFICATE;
DROP TABLE IF EXISTS WORKHISTORY;
DROP TABLE IF EXISTS ASSIGNMENT;
DROP TABLE IF EXISTS CANDIDATEQUALIFICATION;
DROP TABLE IF EXISTS QUALIFICATIONS;
DROP TABLE IF EXISTS COMPANY;
DROP TABLE IF EXISTS CANDIDATE;


CREATE TABLE CANDIDATE(
  candidate_id INTEGER NOT NULL AUTO_INCREMENT PRIMARY KEY,
  candidate_surname VARCHAR(30) NOT NULL,
  candidate_lastname VARCHAR(30) NOT NULL,
  candidate_phone INTEGER NOT NULL
);

CREATE TABLE COMPANY(
  company_id INTEGER NOT NULL AUTO_INCREMENT PRIMARY KEY,
  company_name VARCHAR(30) NOT NULL,
  company_phone INTEGER NOT NULL,
  company_email VARCHAR(30) NOT NULL
);

CREATE TABLE QUALIFICATIONS(
  qualification_id INTEGER NOT NULL AUTO_INCREMENT PRIMARY KEY,
  qualification_name VARCHAR(30) NOT NULL
);

CREATE TABLE CANDIDATEQUALIFICATION(
  candidate_id INTEGER NOT NULL,
  qualification_id INTEGER NOT NULL,
  FOREIGN KEY(candidate_id) REFERENCES CANDIDATE(candidate_id),
  FOREIGN KEY(qualification_id) REFERENCES QUALIFICATIONS(qualification_id),
  CONSTRAINT candidatequalification_pk PRIMARY KEY (candidate_id, qualification_id)
);

CREATE TABLE ASSIGNMENT(
  assignment_id INTEGER AUTO_INCREMENT NOT NULL PRIMARY KEY,
  qualification_id INTEGER,
  company_id INTEGER NOT NULL,
  candidate_id INTEGER,
  start_date DATE NOT NULL,
  end_date DATE NOT NULL,
  total_hours INTEGER,
  FOREIGN KEY(company_id) REFERENCES COMPANY(company_id),
  FOREIGN KEY(candidate_id) REFERENCES CANDIDATE(candidate_id),
  FOREIGN KEY(qualification_id) REFERENCES QUALIFICATIONS(qualification_id)
);

CREATE TABLE WORKHISTORY(
  workhistory_id INTEGER NOT NULL AUTO_INCREMENT PRIMARY KEY,
  candidate_id INTEGER NOT NULL,
  assignment_id INTEGER NOT NULL,
  total_hours INTEGER,
  FOREIGN KEY(assignment_id)REFERENCES ASSIGNMENT(assignment_id),
  FOREIGN KEY(candidate_id)REFERENCES CANDIDATE(candidate_id)
);

CREATE TABLE CERTIFICATE(
  certificate_id INTEGER NOT NULL AUTO_INCREMENT PRIMARY KEY,
  workhistory_id INTEGER NOT NULL,
  candidate_id INTEGER NOT NULL,
  FOREIGN KEY(candidate_id)REFERENCES CANDIDATE(CANDIDATE_ID),
  FOREIGN KEY(workhistory_id) REFERENCES WORKHISTORY(workhistory_id)
);


INSERT INTO CANDIDATE VALUES(DEFAULT, 'Erlend', 'Sundoy', '90909090');
INSERT INTO CANDIDATE VALUES(DEFAULT, 'Snorre', 'Strand', '80808080');
INSERT INTO CANDIDATE VALUES(DEFAULT, 'Oscar', 'Skaug', '70707070');

INSERT INTO COMPANY VALUES(DEFAULT, 'SpekkMekk', '80000000', 'spekk@mekk.no');
INSERT INTO COMPANY VALUES(DEFAULT, 'Kanyla', '70000000', 'kanyla@firma.no');
INSERT INTO COMPANY VALUES(DEFAULT, 'Bambyla', '60000000', 'bambyla@jobb.no');

INSERT INTO QUALIFICATIONS VALUES(DEFAULT, 'Spise vaffler');
INSERT INTO QUALIFICATIONS VALUES(DEFAULT, 'Ta bilder');
INSERT INTO QUALIFICATIONS VALUES(DEFAULT, 'Kaste stein');

INSERT INTO CANDIDATEQUALIFICATION VALUES('1', '1'); -- Erlend kan spise vaffler
INSERT INTO CANDIDATEQUALIFICATION VALUES('1', '2'); -- Erlend kan ta bilder
INSERT INTO CANDIDATEQUALIFICATION VALUES('2', '3'); -- Snorre kan kaste stein :)
                                                     -- Oscar kan NULL!

INSERT INTO ASSIGNMENT VALUES(DEFAULT, NULL, '1', '3', '20170110', '20170124', '40'); -- ingen kvalikk, Oscar, SpekkMekk, fra 10 - 24, totalt 40 timer.
INSERT INTO ASSIGNMENT VALUES(DEFAULT, '1', '2', '1', '20170112', '20170114', '10'); -- må kunne spise vaffler, Kanyler, Erlend, fra 12 - 14, totalt 10 timer.
INSERT INTO ASSIGNMENT VALUES(DEFAULT, '3', '3', '2', '20170110', '20170110', '1'); -- må kunne kaste stein, Bambyler, Snorre, Den 10.01 - totalt 1 time.

INSERT INTO WORKHISTORY VALUES(DEFAULT, '1', '2', '10'); -- increment(1), Erlend(1), Assignment(2), 10 timer.
INSERT INTO WORKHISTORY VALUES(DEFAULT, '2', '3', '1'); -- increment(2), Snorre(2), Assignment(3), 1 time.
INSERT INTO WORKHISTORY VALUES(DEFAULT, '3', '1', '40'); -- increment(3), Oscar(3), Assignment(1), 40 timer.

INSERT INTO CERTIFICATE VALUES(DEFAULT, '1', '1'); -- Erlends sertifikat
INSERT INTO CERTIFICATE VALUES(DEFAULT, '2', '2'); -- Snorres sertitikat
INSERT INTO CERTIFICATE VALUES(DEFAULT, '3', '3'); -- Oscars sertifikat


-- OPPGAVE B)
-- 1 ) Enkelte fremmednøkler kan være NULL. For eksempel kan kvalifikasjoner være null.

-- 2 ) Jeg kunne for eksmpel Laget en primærnøkkel i CANDIDATEQUALIFICATIONS, inkrementert den og brukt den som nøkkel på assignment.
-- for å få sjekket at kvalifikasjonene blir møtt. slik det er nå må man manuelt lese av tabellene for å sjekke kravene møtes.



-- OPPGAVE D)

-- 1) Lag en liste over alle bedriftene. Navn, telefon og epost til bedriften skal skrives ut.
SELECT company_name, company_phone, company_email FROM COMPANY;

-- 2) Lag en liste over alle oppdragene.
-- Om hvert oppdrag skal du skrive ut oppdragets nummer samt navn og telefonnummer til bedriften som tilbyr oppdraget.
SELECT assignment_id, company_name, company_phone FROM ASSIGNMENT NATURAL JOIN COMPANY;

-- 3) Lag en liste over kandidater og kvalifikasjoner.
-- Kandidatnavn og kvalifikasjonsbeskrivelse skal med i utskriften i tillegg til løpenumrene som identifiserer kandidat og kvalifikasjon.
SELECT candidate_id, candidate_surname, candidate_lastname, qualification_id, qualification_name
FROM CANDIDATE NATURAL JOIN QUALIFICATIONS NATURAL JOIN CANDIDATEQUALIFICATION;
-- Har ikke løpenummer i CANDIDATEQUALIFICATIONS.

-- 4) Som oppgave 3), men få med de kandidatene som ikke er registrert med kvalifikasjoner.
SELECT CANDIDATE.candidate_id, candidate_surname, candidate_lastname, qualification_name
FROM CANDIDATE LEFT JOIN CANDIDATEQUALIFICATION ON(CANDIDATE.candidate_id = CANDIDATEQUALIFICATION.candidate_id)
LEFT JOIN QUALIFICATIONS ON(CANDIDATEQUALIFICATION.qualification_id = QUALIFICATIONS.qualification_id);

-- 5) Skriv ut jobbhistorikken til en bestemt vikar, gitt kandidatnr.
-- Vikarnavn, sluttdato, oppdragsnr og bedriftsnavn skal med.
SELECT CANDIDATE.candidate_id, CANDIDATE.candidate_surname, ASSIGNMENT.end_date, ASSIGNMENT.assignment_id, COMPANY.company_name
  FROM CANDIDATE NATURAL JOIN ASSIGNMENT NATURAL JOIN COMPANY;



-- RELASJONSMODELLEN:

-- CANDIDATE(candidate_id, candidate_surname, candidate_lastname, candidate_phone)

-- COMPANY(company_id, company_name, company_phone, company_email)

-- QUALIFICATION(qualification_id, qualification_name)

-- CANDIDATEQUALIFICATION(candidatequalification_pk, candidate_id *, qualification_id *)

-- ASSIGNMENT(assignment_id, qualification_id *, company_id *, candidate_id *, start_date, end_date, total_hours)

-- WORKHISTORY(workhistory_id, candidate_id *, assignment_id *, total_hours)

-- CERTIFICATE(certificate_id, workhistory_id *, candidate_id *)
