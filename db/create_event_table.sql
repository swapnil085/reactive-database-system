
drop table event;

create table event(

eid				int(11) not null AUTO_INCREMENT,
e_values		varchar(255),
status			enum("WAITING","PROCESSED") default "WAITING",
time_stamp  	timestamp default current_timestamp,

constraint pk_event_table primary key(eid)
);
