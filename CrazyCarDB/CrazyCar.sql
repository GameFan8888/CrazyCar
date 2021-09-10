create database crazy_car;
use crazy_car;

create table if not exists `all_user`(
   `user_id` int unsigned auto_increment,
   `user_name` varchar(100) not null,
   `user_password` VARCHAR(40) not null,
   `login_time` int not null,
    `aid` INT(4)  not null,
   primary key ( `user_id` )
   )engine = innodb default charset = utf8;
insert into all_user ( user_id, user_name, user_password, login_time, aid )
                       values
					   (1, "Tast", "111111", 1629544628, 0);
insert into all_user ( user_id, user_name, user_password, login_time, aid )
                       values
					   (2, "asd", "111111", 1629544634, 1);
insert into all_user ( user_id, user_name, user_password, login_time, aid )
                       values
					   (3, "qwe", "111111", 1629544655, 2);        
insert into all_user ( user_id, user_name, user_password, login_time, aid )
                       values
					   (4, "Lory", "111111", 1629544666, 3);                       
select* from all_user;

select user_password 
from all_user 
where user_name = "Tast";
/*修改原有表all_user*/
/*
ALTER TABLE all_user ADD aid INT(4)  not null;
alter table all_user drop column aid;
*/
/*avatar_index*/
create table if not exists `avatar_index`(
   `id` int unsigned auto_increment,
   `aid` int not null,
   `user_id` int not null,
   primary key ( `id` )
   )engine = innodb default charset = utf8;
insert into avatar_index ( aid, user_id )
				   values
				   (0, 1);
insert into avatar_index ( aid, user_id )
				   values
				   (2, 1);
insert into avatar_index ( aid, user_id )
				   values
				   (4, 1);
insert into avatar_index ( aid, user_id )
				   values
				   (8, 1);
insert into avatar_index ( aid, user_id )
				   values
				   (16, 1);
insert into avatar_index ( aid, user_id )
				   values
				   (17, 1);
insert into avatar_index ( aid, user_id )
				   values
				   (18, 1);
insert into avatar_index ( aid, user_id )
				   values
				   (19, 1);
select aid from
 avatar_index 
 where aid = 0 and user_id = 1;
select* from avatar_index;

/*avatar_name*/
create table if not exists `avatar_name`(
   `id` int unsigned auto_increment,
   `aid` int not null,
   `avatar_name` VARCHAR(40) not null,
   primary key ( `id` )
   )engine = innodb default charset = utf8;
insert into avatar_name ( aid, avatar_name )
				   values
				   (0, "Tast 0");
insert into avatar_name ( aid, avatar_name )
				   values
				   (1, "Black 1");
insert into avatar_name ( aid, avatar_name )
				   values
				   (2, "Write 2");
insert into avatar_name ( aid, avatar_name )
				   values
				   (3, "Write 3");
insert into avatar_name ( aid, avatar_name )
				   values
				   (4, "Write 4");
insert into avatar_name ( aid, avatar_name )
				   values
				   (5, "Write 5");
insert into avatar_name ( aid, avatar_name )
				   values
				   (6, "Avatar6");
insert into avatar_name ( aid, avatar_name )
				   values
				   (7, "Avatar7");
insert into avatar_name ( aid, avatar_name )
				   values
				   (8, "Avatar8");
insert into avatar_name ( aid, avatar_name )
				   values
				   (9, "Avatar9");
insert into avatar_name ( aid, avatar_name )
				   values
				   (10, "Avatar10");
insert into avatar_name ( aid, avatar_name )
				   values
				   (11, "Avatar11");
insert into avatar_name ( aid, avatar_name )
				   values
				   (12, "Avatar12");
insert into avatar_name ( aid, avatar_name )
				   values
				   (13, "Avatar13");
insert into avatar_name ( aid, avatar_name )
				   values
				   (14, "Avatar14");
insert into avatar_name ( aid, avatar_name )
				   values
				   (15, "Avatar15");
insert into avatar_name ( aid, avatar_name )
				   values
				   (16, "Avatar160");
insert into avatar_name ( aid, avatar_name )
				   values
				   (17, "Avatar17");
insert into avatar_name ( aid, avatar_name )
				   values
				   (18, "Avatar18");
insert into avatar_name ( aid, avatar_name )
				   values
				   (19, "Avatar19");
select* from avatar_name;

select* from all_user;
update all_user 
set aid = 2 
where user_id = 1;

create table if not exists `ab_resource`(
   `r_id` int unsigned auto_increment,
   `r_name` varchar(100) not null,
   `r_hash` VARCHAR(40) not null,
   `r_crc` VARCHAR(40) not null,
    `r_url` VARCHAR(40) not null,
    `r_size` VARCHAR(40) not null,
   primary key ( `r_id` )
   )engine = innodb default charset = utf8;
insert into ab_resource ( r_name, r_hash, r_crc, r_url, r_size)
				   values
				   ("avatar", "9370cfe1c8e8884648f086b820bca347", "1242346442", "avatar", "0.1289");
select* from ab_resource;
select r_hash 
from
 ab_resource
 where r_name = "avatar";

/*time trail class*/
create table if not exists `time_trial_class`(
   `cid` int not null,
   `difficulty` int not null,
   `map_id` int not null,
   `limit_time` int not null,
   `class_name` VARCHAR(40) not null,
   primary key ( `cid` )
   )engine = innodb default charset = utf8;
insert into time_trial_class ( cid, difficulty, map_id, limit_time, class_name )
				   values
				   (0, 2, 0, 60, "Test0");
insert into time_trial_class ( cid, difficulty, map_id, limit_time, class_name )
				   values
				   (1, 1, 0, 70, "Test1");
insert into time_trial_class ( cid, difficulty, map_id, limit_time, class_name )
				   values
				   (2, 3, 0, 80, "Test2");
insert into time_trial_class ( cid, difficulty, map_id, limit_time, class_name )
				   values
				   (3, 2, 0, 90, "Test3");
select* from time_trial_class;
select cid from time_trial_class;


/*time_trial_record*/
create table if not exists `time_trial_record`(
    `id` int unsigned auto_increment,
	`uid` int not null,
    `cid` int not null,
    `complete_time` int not null,
    `record_time` int not null,
   primary key ( `id` )
   )engine = innodb default charset = utf8;
insert into time_trial_record ( uid, cid, complete_time, record_time)
				   values
				   (1, 0, 22, 1629544628);
insert into time_trial_record ( uid, cid, complete_time, record_time)
				   values
				   (1, 0, 14, 1629544644);
insert into time_trial_record ( uid, cid, complete_time, record_time)
				   values
				   (1, 0, -1, 1629544628);
insert into time_trial_record ( uid, cid, complete_time, record_time)
				   values
				   (1, 0, 14, 1629544644);
insert into time_trial_record ( uid, cid, complete_time, record_time)
				   values
				   (2, 0, 15, 1629544644);
insert into time_trial_record ( uid, cid, complete_time, record_time)
				   values
				   (2, 0, 14, 1629544644);
insert into time_trial_record ( uid, cid, complete_time, record_time)
				   values
				   (3, 0, 8, 1629544644);
insert into time_trial_record ( uid, cid, complete_time, record_time)
				   values
				   (4, 0, 16, 1629544644);

/*查询自己的成绩排名*/
select
	ta.*, @rownum  := @rownum  + 1 AS rownum
from
	(
		select uid, complete_time
		from time_trial_record

		where uid = 1 and cid = 0
		order by complete_time asc
	) AS ta,
	(select @rownum:= 0) r;

/*查询自己的最好成绩*/
select complete_time 
from
 (
	select
		record.*, @rownum  := @rownum + 1 as rownum
	from
		(
			select uid, complete_time
			from time_trial_record
				where uid = 1 and cid = 0
				order by complete_time asc
		) as record,
		(select @rownum:= 0) r
) as history_rank where rownum = 1 and complete_time != -1;

/*获取记录中的所有人的排名*/
select
	user_rank.*, @rank_num  := @rank_num  + 1 as rank_num
from
	(
		select *
		from
		(select uid, min(complete_time) as complete_time
		from
			 time_trial_record
			 where cid = 0 and complete_time != -1 
			 group by uid) as min_time
		order by complete_time asc
	) as user_rank,
	(select @rank_num:= 0) r;


drop table if exists time_trial_rank_0;
create table  time_trial_rank_0 as
select * from  (select user_rank.*, @rank_num  := @rank_num  + 1 as rank_num
				from
					(
						select *
						from
						(select uid, min(complete_time) as complete_time
						from
							 time_trial_record
							 where cid = 0 and complete_time != -1 
							 group by uid) as min_time
						order by complete_time asc
					) as user_rank,
					(select @rank_num:= 0) r)  as all_user_rank;
select rank_num from time_trial_rank_0 where uid = 1;      
select count(rank_num) as rank_count from  time_trial_rank_0;
select * from time_trial_rank_0;
              

select uid from time_trial_rank_0 where rank_num = 4;






