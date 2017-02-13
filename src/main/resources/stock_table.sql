DROP TABLE IF EXISTS news;
CREATE TABLE news
  (idNews INT PRIMARY KEY AUTO_INCREMENT NOT NULL,
   category CHAR(25) NOT NULL,
   title CHAR(100) NOT NULL,
   date DATE NOT NULL);
INSERT INTO news (category, title, date)
  VALUES ('FILM', 'Iron Man', '2017-07-20');
INSERT INTO news (category, title, date)
  VALUES ('FILM', 'Star Wars: Episode IV', '2017-07-20');
INSERT INTO news (category, title, date)
  VALUES ('FILM', '1+1', '2017-07-20');
INSERT INTO news (category, title, date)
  VALUES ('FILM', 'Watchman', '2017-07-20');
INSERT INTO news (category, title, date)
  VALUES ('BOOK', 'The Great Atlas of the Stars', '2017-07-20');
INSERT INTO news (category, title, date)
  VALUES ('BOOK', '100 Greatest People', '2017-08-20');
INSERT INTO news (category, title, date)
  VALUES ('BOOK', 'Around the World in Eighty Days', '2017-07-19');
INSERT INTO news (category, title, date)
  VALUES ('BOOK', 'Learning Java', '2018-07-20');
INSERT INTO news (category, title, date)
  VALUES ('DISK', '100 Greatest Songs of All Time', '2017-07-20');
INSERT INTO news (category, title, date)
  VALUES ('DISK', 'Rock Music Compilation', '2017-07-20');
INSERT INTO news (category, title, date)
  VALUES ('DISK', 'Learn English in 100 Days', '2017-07-20');