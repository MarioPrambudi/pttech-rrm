insert into configuration ( config_key , config_value , ordering , version ) values ('CEL.ip', '192.168.1.1', 0, 0);
insert into configuration ( config_key , config_value , ordering , version ) values ('CEL.port', '3333', 1, 0);
insert into configuration ( config_key , config_value , ordering , version ) values ('CEL.location', '/home/user/celcom', 1, 0);
insert into configuration ( config_key , config_value , ordering , version ) values ('CEL.username', 'celcomuser', 2, 0);
insert into configuration ( config_key , config_value , ordering , version ) values ('CEL.password', 'password', 3, 0);
insert into configuration ( config_key , config_value , ordering , version ) values ('CEL.uploadschedule', '* * 2 * * ?', 4, 0);
insert into configuration ( config_key , config_value , ordering , version ) values ('TNG.reloadendpoint', 'http://localhost:8080/services/reloadrequest/request?wsdl', 0, 0);
insert into configuration ( config_key , config_value , ordering , version ) values ('TNG.batchendpoint', 'http://localhost:8080/services/reloadrequest/request?wsdl', 1, 0);
insert into configuration ( config_key , config_value , ordering , version ) values ('TNG.uploadschedule', '* * 2 * * ?', 2, 0);
insert into configuration ( config_key , config_value , ordering , version ) values ('RRM.retries', '3', 0, 0);
insert into configuration ( config_key , config_value , ordering , version ) values ('RRM.timeout', '120', 1, 0);
insert into configuration ( config_key , config_value , ordering , version ) values ('RRM.terminalHeartBeat', '120', 1, 0);
insert into configuration ( config_key , config_value , ordering , version ) values ('REPORT.TNG.TG0001', 'Daily Detailed Request Reload Report', 0, 0);
insert into configuration ( config_key , config_value , ordering , version ) values ('REPORT.TNG.TG0002', 'Summary Request Reload Report', 1, 0);
insert into configuration ( config_key , config_value , ordering , version ) values ('REPORT.TNG.TG0003', 'Daily Detailed Reload Report', 2, 0);
insert into configuration ( config_key , config_value , ordering , version ) values ('REPORT.TNG.TG0004', 'Summary Reload Report', 3, 0);
insert into configuration ( config_key , config_value , ordering , version ) values ('REPORT.TNG.TG0005', 'Daily Detailed Cancellation Reload Request Report', 4, 0);
insert into configuration ( config_key , config_value , ordering , version ) values ('REPORT.TNG.TG0006', 'Summary Cancellation Reload Report', 5, 0);
insert into configuration ( config_key , config_value , ordering , version ) values ('REPORT.TNG.TG0007', 'Settlement Reload Report', 6, 0);
insert into configuration ( config_key , config_value , ordering , version ) values ('REPORT.TNG.TG0008', 'Commission Reload Report', 7, 0);
insert into configuration ( config_key , config_value , ordering , version ) values ('REPORT.CEL.CE0001', 'Daily Transaction Detailed Report', 1, 0);
insert into configuration ( config_key , config_value , ordering , version ) values ('REPORT.CEL.CE0002', 'Daily Transaction Detailed By Range Of Dates Report', 2, 0);
insert into configuration ( config_key , config_value , ordering , version ) values ('REPORT.CEL.CE0003', 'Daily Transaction Fee  Detailed Report', 3, 0);
insert into configuration ( config_key , config_value , ordering , version ) values ('REPORT.CEL.CE0004', 'Daily Transaction Fee  Detailed By Range Of Dates Report', 4, 0);
insert into configuration ( config_key , config_value , ordering , version ) values ('REPORT.CEL.CE0005', 'Summary of Daily Transactions Report', 5, 0);
insert into configuration ( config_key , config_value , ordering , version ) values ('REPORT.CEL.CE0006', 'Summary of Daily Transactions By Range of Dates Report', 6, 0);
insert into configuration ( config_key , config_value , ordering , version ) values ('REPORT.CEL.CE0007', 'Summary of Daily Fee Report', 7, 0);
insert into configuration ( config_key , config_value , ordering , version ) values ('REPORT.CEL.CE0008', 'Summary of Daily Fee By Range of Dates Report', 8, 0);


insert into province ( id, name ) values ( 1, 'Johor' );
insert into province ( id, name ) values ( 2, 'Melaka' );
insert into province ( id, name ) values ( 3, 'Penang' );
insert into province ( id, name ) values ( 4, 'Selangor' );
insert into province ( id, name ) values ( 5, 'W. P. Kuala Lumpur' );

insert into city ( city_name, acquirer_state ) values ('Johor Bahru', 1);
insert into city ( city_name, acquirer_state ) values ('Kluang', 1);
insert into city ( city_name, acquirer_state ) values ('Alur Gajah', 2);
insert into city ( city_name, acquirer_state ) values ('Kampung Bahru', 2);
insert into city ( city_name, acquirer_state ) values ('George Town', 3);
insert into city ( city_name, acquirer_state ) values ('Petaling Jaya', 4);
insert into city ( city_name, acquirer_state ) values ('Shah Alam', 4);
insert into city ( city_name, acquirer_state ) values ('Klang', 4);
insert into city ( city_name, acquirer_state ) values ('Kuala Lumpur', 5);

insert into reload_request  ( mfg_number , reload_amount , service_provider_id , tng_key , trans_code , trans_id , version  ) values ('2211', 50000, 1, 1, 1, 1, 0);
insert into reload_request  ( mfg_number , reload_amount , service_provider_id , tng_key , trans_code , trans_id , version  ) values ('2222', 94000, 1, 1, 1, 2, 0);


insert into conf_validity_period( config_key , config_value , start_date , end_date, version ) values ('REPORT.FEES', '1.00', '2011-05-01 00:00:00', null, 0);
insert into conf_validity_period( config_key , config_value , start_date , end_date, version ) values ('REPORT.SOF', '0.25', '2011-05-01 00:00:00', null, 0);
insert into conf_validity_period( config_key , config_value , start_date , end_date, version ) values ('REPORT.TNG', '0.20', '2011-05-01 00:00:00', null, 0);
insert into conf_validity_period( config_key , config_value , start_date , end_date, version ) values ('REPORT.RS', '0.30', '2011-05-01 00:00:00', null, 0);
insert into conf_validity_period( config_key , config_value , start_date , end_date, version ) values ('REPORT.AT', '0.25', '2011-05-01 00:00:00', null, 0);