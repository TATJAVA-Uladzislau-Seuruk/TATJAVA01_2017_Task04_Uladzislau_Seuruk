DROP TABLE IF EXISTS news;
CREATE TABLE news
  (idNews INT PRIMARY KEY AUTO_INCREMENT NOT NULL,
   category CHAR(25) NOT NULL,
   title CHAR(100) NOT NULL,
   date CHAR(10) NOT NULL);
INSERT INTO news (category, title, date)
  VALUES ('FILM', 'Iron Man', '20.07.2017');
INSERT INTO news (category, title, date)
  VALUES ('FILM', 'Star Wars: Episode IV', '20.07.2017');
INSERT INTO news (category, title, date)
  VALUES ('FILM', '1+1', '20.07.2017');
INSERT INTO news (category, title, date)
  VALUES ('FILM', 'Watchman', '20.01.2017');
INSERT INTO news (category, title, date)
  VALUES ('BOOK', 'The Great Atlas of the Stars', '20.07.2017');
INSERT INTO news (category, title, date)
  VALUES ('BOOK', '100 Greatest People', '20.08.2017');
INSERT INTO news (category, title, date)
  VALUES ('BOOK', 'Around the World in Eighty Days', '19.07.2017');
INSERT INTO news (category, title, date)
  VALUES ('BOOK', 'Learning Java', '20.07.2018');
INSERT INTO news (category, title, date)
  VALUES ('DISK', '100 Greatest Songs of All Time', '20.07.2017');
INSERT INTO news (category, title, date)
  VALUES ('DISK', 'Rock Music Compilation', '20.07.2017');
INSERT INTO news (category, title, date)
  VALUES ('DISK', 'Learn English in 100 Days', '20.07.2017');