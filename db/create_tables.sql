drop database RDBS;
create database RDBS;
use RDBS;

create table login(
userid 				  int not null AUTO_INCREMENT,
username        	  varchar(255) not null,
password 			  varchar(255) not null,
constraint pk_login primary key(userid)
);

create table event(

eid				int(11) not null AUTO_INCREMENT,
e_values		varchar(255),
status			enum("WAITING","PROCESSED") default "WAITING",
time_stamp  	timestamp default current_timestamp,

constraint pk_event_table primary key(eid)
);

create table bus_details(
bid					int not null AUTO_INCREMENT,
source				varchar(255) not null,
destination 		varchar(255) not null,
available_seats		int(10) not null,
doj					DATETIME not null,
base_fare				int not null,
seat_threshold           int not null,
date_threshold     DATETIME not null,
dynamic_fare		int not null,

constraint pk_bus_details primary key(bid)
);
alter table bus_details add column status enum("AVAILABLE","NOT_AVAILABLE") default "AVAILABLE";
create table tickets(
tid 					int not null AUTO_INCREMENT,
userid         int not null,
bid         int not null,
fare				int not null,
pnr  				varchar(255) not null,
doj  				varchar(255) not null,
constraint pk_tickets primary key(tid)
);

alter table tickets add constraint fk_tickets_pass foreign key(userid) references login(userid);
alter table tickets add constraint fk_tickets_bus foreign key(bid) references bus_details(bid);


create table response(

rid        	int(11) not null auto_increment,
eid	int(11) not null,
userid 	int(11) not null,
r_values	VARCHAR(255),
status		enum("WAITING","PROCESSED") default "WAITING",
constraint pk_response_id	primary key(rid)
);


alter table response add constraint fk_user_id foreign key response(userid) references login(userid);

create table wish_list(

wid					int(11) not null auto_increment,
userid	int(11) not null,
source				varchar(255),
destination			varchar(255),

constraint pk_wish_list primary key(wid)
);

alter table wish_list add constraint fk_wish_pass foreign key(userid) references login(userid);
alter table wish_list add column status enum("BOOKED","WAITING") DEFAULT "WAITING" ;
