use RDBS;

create table event_mapping(
event_id    int not null,
event_name  varchar(255),
constraint pk_event_mapping primary key(event_id)
);
