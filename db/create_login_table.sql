create table login(
userid 				  int not null AUTO_INCREMENT,
username        	  varchar(255) not null,
password 			  varchar(255) not null,
constraint pk_login primary key(userid)
);
