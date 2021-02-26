CREATE TABLE writer(
	writer_id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
	name VARCHAR(30) NOT NULL,
	surname VARCHAR(30) NOT NULL,
	email VARCHAR(100) NOT NULL UNIQUE
);

CREATE TABLE genre(
	genre VARCHAR(50) NOT NULL PRIMARY KEY
);

CREATE TABLE person(
	person_id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
	email VARCHAR(100) NOT NULL UNIQUE,
	phone VARCHAR(15) NOT NULL
);

CREATE TABLE physical_person(
	person_id INTEGER NOT NULL PRIMARY KEY,
	name VARCHAR(30) NOT NULL,
	surname VARCHAR(30) NOT NULL,
	cpf VARCHAR(11) NOT NULL UNIQUE,
	FOREIGN KEY (person_id)
		REFERENCES person (person_id)
);

CREATE TABLE juridical_person(
	person_id INTEGER NOT NULL PRIMARY KEY,
	name VARCHAR(50) NOT NULL UNIQUE,
	cnpj VARCHAR(14) NOT NULL UNIQUE,
	FOREIGN KEY (person_id)
		REFERENCES person (person_id)
);

CREATE TABLE users(
	user_id INTEGER NOT NULL PRIMARY KEY,
	login VARCHAR(25) NOT NULL UNIQUE,
	access_level CHARACTER NOT NULL,
	user_password VARCHAR(25) NOT NULL,
	FOREIGN KEY (user_id)
		REFERENCES physical_person (person_id)
); 

CREATE TABLE publisher(
	publisher_id INTEGER NOT NULL PRIMARY KEY,
	FOREIGN KEY (publisher_id)
		REFERENCES juridical_person (person_id)
);

CREATE TABLE book(
	book_id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
	title VARCHAR(100) NOT NULL,
	pages INTEGER,
	publisher_id INTEGER NOT NULL,
	FOREIGN KEY (publisher_id)
		REFERENCES publisher (publisher_id)
);

CREATE TABLE book_writer(
	writer_id INTEGER NOT NULL,
	book_id INTEGER NOT NULL,
	PRIMARY KEY(writer_id, book_id),
	FOREIGN KEY (writer_id)
		REFERENCES writer (writer_id),
	FOREIGN KEY (book_id)
		REFERENCES book (book_id)
);

CREATE TABLE book_genre(
	genre VARCHAR(50) NOT NULL,
	book_id INTEGER NOT NULL,
	PRIMARY KEY(genre, book_id),
	FOREIGN KEY (genre)
		REFERENCES genre(genre),
	FOREIGN KEY (book_id)
		REFERENCES book(book_id)
);

CREATE TABLE book_user(
	user_id INTEGER NOT NULL,
	book_id INTEGER NOT NULL,
	PRIMARY KEY(user_id, book_id),
	FOREIGN KEY (user_id)
		REFERENCES users(user_id),
	FOREIGN KEY (book_id)
		REFERENCES book(book_id)
);

CREATE VIEW publisher_vw AS
	SELECT pu.publisher_id, name, cnpj, email, phone
	FROM publisher AS pu
	INNER JOIN juridical_person AS jp ON pu.publisher_id = jp.person_id
	INNER JOIN person AS pe ON jp.person_id = pe.person_id; 
	
CREATE VIEW user_vw AS 
	SELECT us.user_id, name, surname, cpf, phone, email, login, user_password, access_level
	FROM users AS us
	INNER JOIN physical_person AS pp ON us.user_id = pp.person_id
	INNER JOIN person AS p ON pp.person_id = p.person_id;
	
CREATE VIEW writers_book_vw AS
	SELECT *
	FROM writer
	INNER JOIN book_writer ON writer.writer_id = book_writer.writer_id;	
	
CREATE VIEW user_books_vw AS
	SELECT *
	FROM book AS b
	INNER JOIN book_user AS bu ON bu.book_id = b.book_id;
	
	
	
	
	
	