CREATE DATABASE javabase DEFAULT CHARACTER SET utf8 COLLATE utf8_unicode_ci;
USE javabase;

CREATE TABLE IF NOT EXISTS highscores(
  pk_highscores INT NOT NULL,
  username TEXT,
  score INTEGER,

  PRIMARY KEY (pk_highscores)
);

INSERT INTO highscores (pk_highscores, username, score)
VALUES (0, 'Bob', 200),
       (1, 'Tom', 500),
       (2, 'Josh', 900),
       (3, 'Frank', 150),
       (4, 'Freddy', 400);


CREATE TABLE IF NOT EXISTS User (
  pk_user INT NOT NULL,
  name TEXT,
  highscore INTEGER,
  eatenGhosts INTEGER,
  creationDate DATE,
  alltimeScore BIGINT,
  gamesPlayed INTEGER,
  finisehLevels INTEGER,

  PRIMARY KEY (pk_user)
);

INSERT INTO User (pk_user, name, highscore, eatenGhosts, creationDate, alltimeScore, gamesPlayed, finisehLevels)
VALUES (0, 'A', 540, 23, '2021-05-23', 5000, 10, 3);


