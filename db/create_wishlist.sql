
-- use RDBS;
drop table wish_list;
create table wish_list(

wid					int(11) not null auto_increment,
userid	int(11) not null,
source				varchar(255),
destination			varchar(255),

constraint pk_wish_list primary key(wid)
);

alter table wish_list add constraint fk_wish_pass foreign key(userid) references login(userid);
alter table wish_list add column status enum("BOOKED","WAITING") DEFAULT "WAITING" ;
