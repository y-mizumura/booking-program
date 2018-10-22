
DROP TABLE IF EXISTS entry CASCADE;
DROP TABLE IF EXISTS event CASCADE;
DROP TABLE IF EXISTS user CASCADE;
DROP TABLE IF EXISTS member CASCADE;
DROP TABLE IF EXISTS charge CASCADE;
DROP TABLE IF EXISTS location CASCADE;

CREATE TABLE latte.location(
	location_id INT AUTO_INCREMENT NOT NULL COMMENT '場所ID',
	name varchar(15) NOT NULL COMMENT '場所名称',
	kubun varchar(1) NOT NULL COMMENT '場所区分',
	access varchar(20) COMMENT 'アクセス',
	sys_create_func varchar(5) COMMENT '作成機能',
	sys_create_user varchar(10) COMMENT '作成者',
	sys_create_datetime DATETIME COMMENT '作成日時',
	sys_update_func varchar(5) COMMENT '更新機能',
	sys_update_user varchar(10) COMMENT '更新者',
	sys_update_datetime DATETIME COMMENT '更新日時',
	PRIMARY KEY (location_id)
) COMMENT '場所';

CREATE TABLE latte.charge(
	charge_cd varchar(3) NOT NULL COMMENT '参加費コード',
	name varchar(10) NOT NULL COMMENT '参加費名称',
	charge int NOT NULL COMMENT '金額',
	sys_create_func varchar(5) COMMENT '作成機能',
	sys_create_user varchar(10) COMMENT '作成者',
	sys_create_datetime DATETIME COMMENT '作成日時',
	sys_update_func varchar(5) COMMENT '更新機能',
	sys_update_user varchar(10) COMMENT '更新者',
	sys_update_datetime DATETIME COMMENT '更新日時',
	PRIMARY KEY (charge_cd)
) COMMENT '参加費';

CREATE TABLE latte.member(
	member_id int NOT NULL COMMENT 'メンバーID',
	name varchar(10) NOT NULL COMMENT '名前',
	state varchar(1) NOT NULL COMMENT '状態',
	sex varchar(1) NOT NULL COMMENT '性別',
	position varchar(1) NOT NULL COMMENT 'ポジション',
	charge_cd varchar(3) NOT NULL COMMENT '料金コード',
	icon_name varchar(15) NOT NULL COMMENT 'アイコン名',
	sys_create_func varchar(5) COMMENT '作成機能',
	sys_create_user varchar(10) COMMENT '作成者',
	sys_create_datetime DATETIME COMMENT '作成日時',
	sys_update_func varchar(5) COMMENT '更新機能',
	sys_update_user varchar(10) COMMENT '更新者',
	sys_update_datetime DATETIME COMMENT '更新日時',
	PRIMARY KEY (member_id),
	foreign key(charge_cd) references latte.charge(charge_cd)
) COMMENT 'メンバー';

CREATE TABLE latte.user(
	user_id varchar(10) NOT NULL COMMENT 'ユーザID',
	password varchar(60) NOT NULL COMMENT 'パスワード',
	role varchar(10) NOT NULL COMMENT '役割',
	state varchar(1) NOT NULL COMMENT '状態',
	member_id int NOT NULL COMMENT 'メンバーID',
	sys_create_func varchar(5) COMMENT '作成機能',
	sys_create_user varchar(10) COMMENT '作成者',
	sys_create_datetime DATETIME COMMENT '作成日時',
	sys_update_func varchar(5) COMMENT '更新機能',
	sys_update_user varchar(10) COMMENT '更新者',
	sys_update_datetime DATETIME COMMENT '更新日時',
	PRIMARY KEY (user_id),
	foreign key(member_id) references latte.member(member_id)
) COMMENT 'ユーザ';

CREATE TABLE latte.event (
	event_id INT AUTO_INCREMENT NOT NULL COMMENT 'イベントID',
	title varchar(20) COMMENT 'タイトル',
	location_id int NOT NULL COMMENT '場所ID',
	event_date date NOT NULL COMMENT 'イベント実施日',
	start_time time NOT NULL COMMENT '開始時刻',
	end_time time NOT NULL COMMENT '終了時刻',
	uke_state varchar(1) NOT NULL COMMENT '受付状態',
	memo varchar(100) COMMENT 'メモ',
	sys_create_func varchar(5) COMMENT '作成機能',
	sys_create_user varchar(10) COMMENT '作成者',
	sys_create_datetime DATETIME COMMENT '作成日時',
	sys_update_func varchar(5) COMMENT '更新機能',
	sys_update_user varchar(10) COMMENT '更新者',
	sys_update_datetime DATETIME COMMENT '更新日時',
	PRIMARY KEY (event_id),
	foreign key(location_id) references latte.location(location_id)
) COMMENT 'イベント';

CREATE TABLE latte.entry (
	entry_id INT AUTO_INCREMENT NOT NULL COMMENT 'イベント参加ID',
	event_id int NOT NULL COMMENT 'イベントID',
	member_id int NOT NULL COMMENT 'メンバーID',
	entry_kubun varchar(1) NOT NULL COMMENT '参加・不参加区分',
	entry_start_time time COMMENT '参加時間（開始）',
	entry_end_time time COMMENT '参加時間（終了）',
	entry_fee int COMMENT '参加費',
	seisanzumi_flg int COMMENT '清算済みフラグ',
	sys_create_func varchar(5) COMMENT '作成機能',
	sys_create_user varchar(10) COMMENT '作成者',
	sys_create_datetime DATETIME COMMENT '作成日時',
	sys_update_func varchar(5) COMMENT '更新機能',
	sys_update_user varchar(10) COMMENT '更新者',
	sys_update_datetime DATETIME COMMENT '更新日時',
	PRIMARY KEY (entry_id),
	foreign key(event_id) references latte.event(event_id),
	foreign key(member_id) references latte.member(member_id)
) COMMENT 'イベント参加';