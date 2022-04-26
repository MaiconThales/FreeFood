USE freefood;

CREATE TABLE Restaurant (
	idRestaurant int auto_increment primary key,
    name varchar(255) not null,
    address varchar(255) not null
);

CREATE TABLE User(
	idUser int auto_increment primary key,
    name varchar(100) not null,
    username varchar(100) not null,
    password varchar(50) not null
);

CREATE TABLE Menu (
	idMenu int auto_increment primary key,
    idRestaurant int,
    name varchar(255) not null,
    linkImage varchar(255) not null,
    FOREIGN KEY(idRestaurant) REFERENCES Restaurant(idRestaurant)
);

CREATE TABLE Request(
	idRequest int auto_increment primary key,
    idRestaurant int,
    idMenu int,
    idUser int,
    name varchar(255) not null,
    amount int not null,
    deliveryAddress varchar(255) not null,
    observation varchar(255),
    FOREIGN KEY(idRestaurant) REFERENCES Restaurant(idRestaurant),
    FOREIGN KEY(idMenu) REFERENCES Menu(idMenu),
    FOREIGN KEY(idUser) REFERENCES User(idUser)
);