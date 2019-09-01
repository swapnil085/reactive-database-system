use RDBS;
create table response(

id        	int(11) not null auto_increment,
event_id	int(11),
p_id 	int(11) not null,
r_values	VARCHAR(255),
status		enum("WAITING","PROCESSED"),
constraint pk_response_id	primary key(id)
);

alter table response add constraint fk_event_id foreign key response(event_id) references event_table(id); 
alter table response add constraint fk_user_id foreign key response(p_id) references passenger_details(id); 
