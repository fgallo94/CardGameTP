CREATE SCHEMA matchs;

use matchs;

CREATE TABLE Logs (
    id_log int Not null primary key auto_increment,
    player varchar(50) not null,
    hand varchar(500) not null
);


