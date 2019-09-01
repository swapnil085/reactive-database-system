-- use RDBS;
drop table response;
create table response(

rid        	int(11) not null auto_increment,
eid	int(11) not null,
userid 	int(11) not null,
r_values	VARCHAR(255),
status		enum("WAITING","PROCESSED") default "WAITING",
constraint pk_response_id	primary key(id)
);


alter table response add constraint fk_user_id foreign key response(userid) references login(userid);
