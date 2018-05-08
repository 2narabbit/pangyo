create table ACTION_HISTORY
(
	id bigint auto_increment
		primary key,
	action_type enum('LIKE', 'POLL', 'SUPPORT') not null,
	content_type enum('POST', 'CANDIDATE', 'CAMPAIGN') not null,
	content_id bigint not null,
	user_id bigint not null,
	reg_dttm datetime default CURRENT_TIMESTAMP not null,
	constraint ACTION_HISTORY_action_type_content_type_content_id_user_id_pk
		unique (action_type, content_type, content_id, user_id)
)
comment '사용자 액션 히스토리'
;


create table CAMPAIGN
(
	id bigint auto_increment
		primary key,
	campaign_candidate_id bigint not null,
	view_count bigint default '0' not null,
	support_count bigint default '0' not null,
	is_register_ad tinyint(1) default '0' not null,
	has_report tinyint(1) default '0' not null,
	ranking int default '0' null,
	goal_exposure_count bigint default '0' not null,
	display tinyint(1) default '1' null,
	status enum('SERVICE', 'DELETED') default 'SERVICE' not null,
	reg_dttm datetime default CURRENT_TIMESTAMP not null,
	up_dttm datetime default CURRENT_TIMESTAMP not null,
	constraint CAMPAIGN_campaign_candidate_id_uindex
		unique (campaign_candidate_id)
)
comment '캠페인' engine=InnoDB
;

create table CAMPAIGN_CANDIDATE
(
	id bigint auto_increment
		primary key,
	execute_rule_id bigint not null,
	star_id bigint not null,
	user_id bigint not null,
	title varchar(1024) not null,
	body varchar(4096) not null,
	randing_url varchar(4096) null,
	banner_img varchar(4096) null,
	poll_count bigint default '0' not null,
	display tinyint(1) default '1' not null,
	status enum('SERVICE', 'DELETED', 'SELECTED') default 'SERVICE' not null,
	reg_dttm datetime default CURRENT_TIMESTAMP not null,
	up_dttm datetime default CURRENT_TIMESTAMP not null,
	constraint CAMPAIGN_CANDIDATE_execute_rule_id_user_id_star_id_pk
		unique (execute_rule_id, user_id, star_id)
)
comment '캠페인 후보' engine=InnoDB
;

create table CAMPAIGN_OP
(
	campaign_id bigint not null
		primary key,
	title varchar(1024) null,
	body varchar(4096) null,
	randing_url varchar(4096) null,
	banner_img varchar(4096) null
)
comment '운영자가 캠페인 내용을 수정한 내역' engine=InnoDB
;

CREATE TABLE `CAMPAIGN_RANK` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `campaign_id` bigint(20) NOT NULL COMMENT '캠페인 id',
  `time` varchar(19) COLLATE utf8_bin NOT NULL COMMENT '순위 결정 시각',
  `ranking` int(11) NOT NULL COMMENT '순위',
  `reg_dttm` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
)
comment '캠페인 순위 스냅샷' engine=InnoDB
;

CREATE TABLE `STAR_RANK` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `star_id` bigint(20) NOT NULL COMMENT '스타 id',
  `time` varchar(19) COLLATE utf8_bin NOT NULL COMMENT '순위 결정 시각',
  `ranking` int(11) NOT NULL COMMENT '순위',
  `reg_dttm` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
)
comment '스타 순위 스냅샷' engine=InnoDB
;

create table COMMENT
(
	id bigint auto_increment
		primary key,
	content_type enum('POST', 'CANDIDATE', 'CAMPAIGN') not null,
	content_id bigint not null,
	user_id bigint not null,
	body varchar(4096) not null,
	status enum('SERVICE', 'DELETED') default 'SERVICE' not null,
	reg_dttm datetime default CURRENT_TIMESTAMP not null,
	up_dttm datetime default CURRENT_TIMESTAMP null
)
comment '댓글'
;

create index COMMENT_content_type_content_id_index
	on COMMENT (content_type, content_id)
;

create table EXECUTION_RULE
(
	id bigint auto_increment
		primary key,
	turn_num bigint not null,
	type enum('CAMPAIGN', 'CANDIDATE', 'AD') not null,
	start_dttm datetime not null,
	end_dttm datetime not null,
	status enum('READY', 'RUNNING', 'DONE', 'END') not null comment 'ENUM(''BEFORE'', ''RUNNING'', ''STOP'', ''END'')
	—> ENUM(‘READY’, ''RUNNING'', ‘DONE’, ''END'')',
	reg_dttm datetime default CURRENT_TIMESTAMP not null,
	up_dttm datetime default CURRENT_TIMESTAMP not null,
	constraint EXECUTION_RULE_turn_num_uindex
		unique (turn_num)
)
comment '집행룰' engine=InnoDB
;

create table LOV
(
	user_id bigint not null
		primary key,
	`key` varchar(256) default '0' not null,
	value bigint default '0' not null,
	reg_dttm datetime default CURRENT_TIMESTAMP not null,
	up_dttm datetime default CURRENT_TIMESTAMP not null
)
comment '럽 사용/누적 총합' engine=InnoDB
;

create table LOV_HISTORY
(
	id bigint auto_increment
		primary key,
	content_type enum('POST', 'CANDIDATE', 'CAMPAIGN') not null,
	action_type enum('LIKE', 'POLL', 'SUPPORT') not null,
	content_id bigint not null,
	user_id bigint not null,
	lov_count int default '0' not null,
	reg_dttm datetime default CURRENT_TIMESTAMP not null
)
comment '럽 히스토리' engine=InnoDB
;

create table POLICY
(
	id int not null
		primary key,
	`key` varchar(256) not null,
	value varchar(256) not null,
	reg_dttm datetime default CURRENT_TIMESTAMP not null,
	end_dttm datetime null
)
comment '정책' engine=InnoDB
;

create table POST
(
	id bigint auto_increment
		primary key,
	star_id bigint not null,
	user_id bigint not null,
	body varchar(4096) null,
	img varchar(4096) null,
	view_count bigint default '0' not null,
	like_count bigint default '0' not null,
	comment_count bigint default '0' null,
	status enum('SERVICE', 'DELETED') default 'SERVICE' not null,
	reg_dttm datetime default CURRENT_TIMESTAMP not null,
	up_dttm datetime default CURRENT_TIMESTAMP not null
)
comment '게시글'
;

create table STAR
(
	id bigint auto_increment
		primary key,
	name varchar(128) not null,
	job varchar(64) null,
	naver_os varchar(128) not null,
	profile_img varchar(256) null,
	main_img varchar(256) null,
	display tinyint(1) default '1' not null,
	fan_count bigint default '0' not null,
	message varchar(256) null,
	reg_dttm datetime default CURRENT_TIMESTAMP not null,
	up_dttm datetime default CURRENT_TIMESTAMP not null,
	constraint STAR_naver_os_uindex
		unique (naver_os)
)
comment '스타' engine=InnoDB
;

create table USER
(
	id bigint auto_increment
		primary key,
	service varchar(32) not null,
	service_user_id varchar(128) not null,
	name varchar(128) not null,
	profile_img varchar(256) null,
	recommand_code bigint null,
	status enum('MEMBER', 'DELETED') default 'MEMBER' not null,
	reg_dttm datetime default CURRENT_TIMESTAMP not null,
	up_dttm datetime default CURRENT_TIMESTAMP not null
)
comment '유저' engine=InnoDB
;

create table USER_STAR_MAP
(
	user_id bigint not null,
	star_id bigint not null,
	reg_dttm datetime default CURRENT_TIMESTAMP not null,
	primary key (user_id, star_id)
)
comment '유저/스타 매핑' engine=InnoDB
;
