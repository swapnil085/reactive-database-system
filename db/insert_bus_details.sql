
insert into bus_details(source,destination,available_seats,seat_threshold,doj,date_threshold,base_fare,dynamic_fare) values("bangalore","chennai",2,2,ADDTIME(current_timestamp, "5 2:10:5.000003"),ADDTIME(current_timestamp, "3 2:10:5.000003"),200,200);
  insert into bus_details(source,destination,available_seats,seat_threshold,doj,date_threshold,base_fare,dynamic_fare) values("bangalore","chennai",1,2,ADDTIME(current_timestamp, "5 2:10:5.000003"),ADDTIME(current_timestamp, "3 2:10:5.000003"),200,200);
    insert into bus_details(source,destination,available_seats,seat_threshold,doj,date_threshold,base_fare,dynamic_fare) values("bangalore","chennai",0,2,ADDTIME(current_timestamp, "5 2:10:5.000003"),ADDTIME(current_timestamp, "3 2:10:5.000003"),200,200);

-- alter table bus_details add column date_threshold DATETIME not null;
