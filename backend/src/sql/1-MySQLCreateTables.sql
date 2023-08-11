# ALTER TABLE Purchase
#     drop foreign key PurchaseUserIdF;
DROP TABLE IF EXISTS Purchase;
DROP TABLE IF EXISTS Session;
DROP TABLE IF EXISTS Movie;
DROP TABLE IF EXISTS MovieTheater;
DROP TABLE  IF EXISTS User;


CREATE TABLE Movie (
                       id BIGINT NOT NULL AUTO_INCREMENT,
                       Title VARCHAR(60) COLLATE latin1_bin NOT NULL,
                       Summary VARCHAR(1000) COLLATE latin1_bin NOT NULL,
                       Duration BIGINT NOT NULL,
                       CONSTRAINT MoviePK PRIMARY KEY (id)
) ENGINE = InnoDB;

CREATE TABLE MovieTheater (
                              id BIGINT NOT NULL AUTO_INCREMENT,
                              name VARCHAR(60) COLLATE latin1_bin NOT NULL,
                              capacity BIGINT NOT NULL,
                              CONSTRAINT MovieTheaterPK PRIMARY KEY (id)
)ENGINE = InnoDB;
CREATE TABLE Session (
                        id BIGINT NOT NULL AUTO_INCREMENT,
                        movieId BIGINT NOT NULL,
                        movieTheaterId BIGINT NOT NULL,
                        date DATETIME NOT NULL,
                        price DECIMAL(11, 2) NOT NULL,
                        seatsAvailable BIGINT NOT NULL,
                        version BIGINT NOT NULL,
                        CONSTRAINT SesionPK PRIMARY KEY (id),
                        CONSTRAINT SesionMovieTheaterIdFK FOREIGN KEY(movieTheaterId)
                            REFERENCES MovieTheater (id),
                        CONSTRAINT SesionMovieIdFK FOREIGN KEY(movieId)
                            REFERENCES Movie (id)
)ENGINE = InnoDB;




CREATE TABLE User (
                      id BIGINT NOT NULL AUTO_INCREMENT,
                      userName VARCHAR(60) COLLATE latin1_bin NOT NULL,
                      password VARCHAR(60) NOT NULL,
                      firstName VARCHAR(60) NOT NULL,
                      lastName VARCHAR(60) NOT NULL,
                      email VARCHAR(60) NOT NULL,
                      role TINYINT NOT NULL,
                      CONSTRAINT UserPK PRIMARY KEY (id),
                      CONSTRAINT UserNameUniqueKey UNIQUE (userName)


) ENGINE = InnoDB;

CREATE TABLE Purchase(
                         id BIGINT NOT NULL AUTO_INCREMENT,
                         reservedSeats BIGINT NOT NULL,

                         sessionId BIGINT NOT NULL ,
                         userId BIGINT NOT NULL,
                         cardNumber VARCHAR(20) NOT NULL ,
                         purchaseDate DATETIME NOT NULL ,
                         used BIT NOT NULL ,
                         CONSTRAINT PurchasePK PRIMARY KEY (id),
                         CONSTRAINT PurchaseSessionIdF FOREIGN KEY (sessionId)
                             REFERENCES Session (id),
                         CONSTRAINT PurchaseUserIdF FOREIGN KEY (userId)
                             REFERENCES User (id)


) ENGINE = InnoDB;

CREATE INDEX UserIndexByUserName ON User (userName);

