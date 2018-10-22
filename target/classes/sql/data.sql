--テーブル内のデータ削除
DELETE FROM event;
DELETE FROM user;
DELETE FROM member;
DELETE FROM charge;
DELETE FROM location;

--場所
INSERT INTO location (name, access, sys_create_func, sys_create_user, sys_create_datetime, sys_update_func, sys_update_user, sys_update_datetime) 
	VALUES ('舎人公園', '舎人公園駅から徒歩1分', 'X0000', 'taro', '2017-08-01 12:00:00', 'X0000', 'taro', '2017-08-01 12:00:00');
INSERT INTO location (name, access, sys_create_func, sys_create_user, sys_create_datetime, sys_update_func, sys_update_user, sys_update_datetime) 
	VALUES ('谷河内テニスコート', '篠崎駅から徒歩15分', 'X0000', 'taro', '2017-08-01 12:00:00', 'X0000', 'taro', '2017-08-01 12:00:00');
INSERT INTO location (name, access, sys_create_func, sys_create_user, sys_create_datetime, sys_update_func, sys_update_user, sys_update_datetime) 
	VALUES ('松戸テニス倶楽部', '千葉県', 'X0000', 'taro', '2017-08-01 12:00:00', 'X0000', 'taro', '2017-08-01 12:00:00');

--料金
INSERT INTO charge (charge_cd, name, charge, sys_create_func, sys_create_user, sys_create_datetime, sys_update_func, sys_update_user, sys_update_datetime) 
	VALUES ('S01', '社会人（男性）', 700, 'X0000', 'taro', '2017-08-01 12:00:00', 'X0000', 'taro', '2017-08-01 12:00:00');
INSERT INTO charge (charge_cd, name, charge, sys_create_func, sys_create_user, sys_create_datetime, sys_update_func, sys_update_user, sys_update_datetime) 
	VALUES ('S02', '社会人（女性）', 600, 'X0000', 'taro', '2017-08-01 12:00:00', 'X0000', 'taro', '2017-08-01 12:00:00');
INSERT INTO charge (charge_cd, name, charge, sys_create_func, sys_create_user, sys_create_datetime, sys_update_func, sys_update_user, sys_update_datetime) 
	VALUES ('G01', '学生', 500, 'X0000', 'taro', '2017-08-01 12:00:00', 'X0000', 'taro', '2017-08-01 12:00:00');

--メンバー
INSERT INTO member (member_id, name, state, sex, position, charge_cd, sys_create_func, sys_create_user, sys_create_datetime, sys_update_func, sys_update_user, sys_update_datetime) 
	VALUES (17080101,'田中　太郎','Z','M','Z','S01', 'X0000', 'taro', '2017-08-01 12:00:00', 'X0000', 'taro', '2017-08-01 12:00:00');
INSERT INTO member (member_id, name, state, sex, position, charge_cd, sys_create_func, sys_create_user, sys_create_datetime, sys_update_func, sys_update_user, sys_update_datetime) 
	VALUES (17080102,'山田　花子','Z','F','Z','S01', 'X0000', 'taro', '2017-08-01 12:00:00', 'X0000', 'taro', '2017-08-01 12:00:00');
INSERT INTO member (member_id, name, state, sex, position, charge_cd, sys_create_func, sys_create_user, sys_create_datetime, sys_update_func, sys_update_user, sys_update_datetime) 
	VALUES (17100101,'鈴木　涼','Z','M','K','G01', 'X0000', 'taro', '2017-08-01 12:00:00', 'X0000', 'taro', '2017-08-01 12:00:00');
INSERT INTO member (member_id, name, state, sex, position, charge_cd, sys_create_func, sys_create_user, sys_create_datetime, sys_update_func, sys_update_user, sys_update_datetime) 
	VALUES (17120101,'佐藤　武','Z','M','K','G01', 'X0000', 'taro', '2017-08-01 12:00:00', 'X0000', 'taro', '2017-08-01 12:00:00');
INSERT INTO member (member_id, name, state, sex, position, charge_cd, sys_create_func, sys_create_user, sys_create_datetime, sys_update_func, sys_update_user, sys_update_datetime) 
	VALUES (17120102,'石川　大地','Z','M','K','G01', 'X0000', 'taro', '2017-08-01 12:00:00', 'X0000', 'taro', '2017-08-01 12:00:00');
INSERT INTO member (member_id, name, state, sex, position, charge_cd, sys_create_func, sys_create_user, sys_create_datetime, sys_update_func, sys_update_user, sys_update_datetime) 
	VALUES (17120103,'福田　海','Z','F','K','G01', 'X0000', 'taro', '2017-08-01 12:00:00', 'X0000', 'taro', '2017-08-01 12:00:00');
INSERT INTO member (member_id, name, state, sex, position, charge_cd, sys_create_func, sys_create_user, sys_create_datetime, sys_update_func, sys_update_user, sys_update_datetime) 
	VALUES (17120104,'代々木　まほ','Z','F','K','G01', 'X0000', 'taro', '2017-08-01 12:00:00', 'X0000', 'taro', '2017-08-01 12:00:00');
INSERT INTO member (member_id, name, state, sex, position, charge_cd, sys_create_func, sys_create_user, sys_create_datetime, sys_update_func, sys_update_user, sys_update_datetime) 
	VALUES (17120105,'江戸川　コナン','Z','M','K','G01', 'X0000', 'taro', '2017-08-01 12:00:00', 'X0000', 'taro', '2017-08-01 12:00:00');
INSERT INTO member (member_id, name, state, sex, position, charge_cd, sys_create_func, sys_create_user, sys_create_datetime, sys_update_func, sys_update_user, sys_update_datetime) 
	VALUES (17120106,'青木　正平','Z','M','K','G01', 'X0000', 'taro', '2017-08-01 12:00:00', 'X0000', 'taro', '2017-08-01 12:00:00');
INSERT INTO member (member_id, name, state, sex, position, charge_cd, sys_create_func, sys_create_user, sys_create_datetime, sys_update_func, sys_update_user, sys_update_datetime) 
	VALUES (17120107,'武田　哲夫','Z','M','K','G01', 'X0000', 'taro', '2017-08-01 12:00:00', 'X0000', 'taro', '2017-08-01 12:00:00');
INSERT INTO member (member_id, name, state, sex, position, charge_cd, sys_create_func, sys_create_user, sys_create_datetime, sys_update_func, sys_update_user, sys_update_datetime) 
	VALUES (17120108,'マーガリン･サッチン','Z','F','K','G01', 'X0000', 'taro', '2017-08-01 12:00:00', 'X0000', 'taro', '2017-08-01 12:00:00');
INSERT INTO member (member_id, name, state, sex, position, charge_cd, sys_create_func, sys_create_user, sys_create_datetime, sys_update_func, sys_update_user, sys_update_datetime) 
	VALUES (17120109,'大杉　加奈子','Z','F','K','G01', 'X0000', 'taro', '2017-08-01 12:00:00', 'X0000', 'taro', '2017-08-01 12:00:00');
INSERT INTO member (member_id, name, state, sex, position, charge_cd, sys_create_func, sys_create_user, sys_create_datetime, sys_update_func, sys_update_user, sys_update_datetime) 
	VALUES (17120110,'深海　勤','Z','M','K','G01', 'X0000', 'taro', '2017-08-01 12:00:00', 'X0000', 'taro', '2017-08-01 12:00:00');
INSERT INTO member (member_id, name, state, sex, position, charge_cd, sys_create_func, sys_create_user, sys_create_datetime, sys_update_func, sys_update_user, sys_update_datetime) 
	VALUES (17120111,'中島　萌','Z','F','K','G01', 'X0000', 'taro', '2017-08-01 12:00:00', 'X0000', 'taro', '2017-08-01 12:00:00');
	
--ユーザ（ログイン） PW:demo
INSERT INTO user (user_id, password, role, state, member_id, sys_create_func, sys_create_user, sys_create_datetime, sys_update_func, sys_update_user, sys_update_datetime) 
	VALUES ('taro','$2a$10$H1QlWRjignQ5gWHWiElOX.7XawuZvWioJr3sh.M147YTp/jgH/Afy', 'ADMIN', 'E', 17080101, 'X0000', 'taro', '2017-08-01 12:00:00', 'X0000', 'taro', '2017-08-01 12:00:00');
INSERT INTO user (user_id, password, role, state, member_id, sys_create_func, sys_create_user, sys_create_datetime, sys_update_func, sys_update_user, sys_update_datetime) 
	VALUES ('hanako','$2a$10$H1QlWRjignQ5gWHWiElOX.7XawuZvWioJr3sh.M147YTp/jgH/Afy', 'E_ADMIN', 'E', 17080102, 'X0000', 'taro', '2017-08-01 12:00:00', 'X0000', 'taro', '2017-08-01 12:00:00');
INSERT INTO user (user_id, password, role, state, member_id, sys_create_func, sys_create_user, sys_create_datetime, sys_update_func, sys_update_user, sys_update_datetime) 
	VALUES ('ryo','$2a$10$H1QlWRjignQ5gWHWiElOX.7XawuZvWioJr3sh.M147YTp/jgH/Afy', 'USER', 'E', 17100101, 'X0000', 'taro', '2017-08-01 12:00:00', 'X0000', 'taro', '2017-08-01 12:00:00');

--イベント
INSERT INTO event (title, location_id, event_date, start_time, end_time, uke_state, memo, sys_create_func, sys_create_user, sys_create_datetime, sys_update_func, sys_update_user, sys_update_datetime)
	VALUES ('通常練習', 1, '2017-12-01', '11:00', '15:00', 'U', 'memo', 'X0000', 'taro', '2017-08-01 12:00:00', 'X0000', 'taro', '2017-08-01 12:00:00');
INSERT INTO event (title, location_id, event_date, start_time, end_time, uke_state, memo, sys_create_func, sys_create_user, sys_create_datetime, sys_update_func, sys_update_user, sys_update_datetime)
	VALUES ('通常練習', 2, '2017-12-02', '11:00', '17:00', 'U', 'memo', 'X0000', 'taro', '2017-08-01 12:00:00', 'X0000', 'taro', '2017-08-01 12:00:00');
INSERT INTO event (title, location_id, event_date, start_time, end_time, uke_state, memo, sys_create_func, sys_create_user, sys_create_datetime, sys_update_func, sys_update_user, sys_update_datetime)
	VALUES ('通常練習', 3, '2017-12-03', '15:00', '19:00', 'U', 'memo', 'X0000', 'taro', '2017-08-01 12:00:00', 'X0000', 'taro', '2017-08-01 12:00:00');
	