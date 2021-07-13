delimiter //
CREATE procedure  runsql()
BEGIN
		DECLARE dbsize int DEFAULT 0;
		DECLARE dbname VARCHAR(60);


		# 定义游标，将结果集赋值到游标中
		DECLARE list  CURSOR FOR SELECT SCHEMA_NAME from INFORMATION_SCHEMA.SCHEMATA WHERE SCHEMA_NAME like  'kp_tk_%'  and SCHEMA_NAME !='kp_tk_base';
SELECT count(*) into @dbsize from INFORMATION_SCHEMA.SCHEMATA WHERE SCHEMA_NAME like  'kp_tk_%'  and SCHEMA_NAME !='kp_tk_base';

# 遍历数据
		OPEN list ;
fetch  list into dbname;
while dbsize< 0 do
				set dbsize =dbsize -1;
ALTER TABLE CONCAT(str1,str2,...)tk_question_options ADD COLUMN `contentLength` int(11) NULL DEFAULT 0 COMMENT '试题长度' AFTER `update_date`;
select dbname;
end while;
CLOSE list;
END
//
delimiter ;
# 运行存储过程
CALL runsql();
DROP procedure  runsql;
