DELIMITER $$

Create Trigger insert_bus AFTER INSERT ON passenger_details FOR EACH ROW
 BEGIN  
  IF  0 = (select available_seats from bus_details where New.bus_id = bus_details.id) THEN
 update bus_details set available_seats = 2 where id = New.bus_id;
 ELSE 
 update bus_details set available_seats = available_seats - 1 where id = New.bus_id;
  
 END IF;
 END $$

 DELIMITER ;