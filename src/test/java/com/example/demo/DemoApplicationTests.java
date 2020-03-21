package com.example.demo;

import com.alibaba.druid.sql.ast.SQLStatement;
import com.alibaba.druid.sql.ast.statement.SQLColumnDefinition;
import com.alibaba.druid.sql.ast.statement.SQLCommentStatement;
import com.alibaba.druid.sql.ast.statement.SQLCreateTableStatement;
import com.alibaba.druid.sql.ast.statement.SQLTableElement;
import com.alibaba.druid.sql.dialect.db2.ast.stmt.DB2CreateTableStatement;
import com.alibaba.druid.sql.dialect.db2.parser.DB2CreateTableParser;
import com.alibaba.druid.sql.dialect.db2.parser.DB2StatementParser;
import com.alibaba.druid.sql.dialect.db2.visitor.DB2SchemaStatVisitor;
import com.alibaba.druid.sql.dialect.mysql.ast.statement.MySqlCreateTableStatement;
import com.alibaba.druid.sql.dialect.mysql.parser.MySqlCreateTableParser;
import com.alibaba.druid.sql.dialect.mysql.parser.MySqlStatementParser;
import com.alibaba.druid.sql.dialect.mysql.visitor.MySqlSchemaStatVisitor;
import com.alibaba.druid.sql.parser.SQLCreateTableParser;
import com.alibaba.druid.sql.parser.SQLExprParser;
import com.alibaba.druid.sql.parser.SQLParser;
import com.alibaba.druid.sql.parser.SQLStatementParser;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class DemoApplicationTests {

	@Test
	void contextLoads() {
	}
	@Test
	void testDDL() {
		String sql = "CREATE TABLE `ACT_Shop_Classification` (\n" +
				"\t\t\t\t`id` int(11) NOT NULL AUTO_INCREMENT COMMENT '表主键',\n" +
				"\t\t\t\t`shop_id` int(11) NOT NULL COMMENT '商户id',\n" +
				"\t\t\t\t`act_name` varchar(100) COLLATE utf8_unicode_ci NOT NULL DEFAULT '' COMMENT '活动名称',\n" +
				"\t\t\t\t`act_image_path` varchar(500) COLLATE utf8_unicode_ci NOT NULL DEFAULT '' COMMENT '活动图片地址',\n" +
				"\t\t\t\t`act_url` varchar(255) COLLATE utf8_unicode_ci NOT NULL DEFAULT '' COMMENT '活动图片地址',\n" +
				"\t\t\t\t`shop_type` tinyint(2) NOT NULL DEFAULT '1' COMMENT '商户类别，1：展示商户，2：活动商户',\n" +
				"\t\t\t\t`add_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '添加时间',\n" +
				"\t\t\t\t`updateTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP\n" +
				"\t\tON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',\n" +
				"\t\t\t\t`delete_flag` tinyint(2) NOT NULL DEFAULT '0' COMMENT '标记删除，0：正常，1：删除',\n" +
				"\t\t\t\tPRIMARY KEY (`id`),\n" +
				"\t\t\t\tKEY `updateTimeIndex` (`updateTime`)\n" +
				") ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='精彩案例'";

		//测试1
//		SQLExprParser sp = new SQLExprParser(sql,"mysql");
//		MySqlCreateTableParser paser = new MySqlCreateTableParser(sql);
////		SQLStatementParser sp = new SQLStatementParser(sql,"mysql");
//		SQLCreateTableStatement table = paser.parseCreateTable();
//		List<SQLTableElement> list = table.getTableElementList();
		MySqlStatementParser parser =new MySqlStatementParser (sql);
		List<SQLStatement>  list = parser.parseStatementList();
		for(SQLStatement statement:list){
			if(statement instanceof MySqlCreateTableStatement){
				List<SQLTableElement> elms = ((MySqlCreateTableStatement) statement).getTableElementList();
				for(SQLTableElement elm :elms){
					if(elm instanceof  SQLColumnDefinition){
						System.out.println("字段名:"+((SQLColumnDefinition) elm).getName());
						System.out.println("字段类型:"+((SQLColumnDefinition) elm).getDataType());
						System.out.println("中文名:"+((SQLColumnDefinition) elm).getComment());
					}

				}
			}else if(statement instanceof SQLCommentStatement){
				System.out.println("comment字段:"+((SQLCommentStatement) statement).getOn());
				System.out.println("中文名:"+((SQLCommentStatement) statement).getComment());
			}

		}
		//测试2"
		String sql2 ="CREATE\n" +
				"　　　　TABLE table_name\n" +
				"　　　　(\n" +
				"　　　　　　company CHARACTER(1) NOT NULL DEFAULT 'N',\n" +
				"　　　　　　online varcha(1) NOT NULL DEFAULT 'N',\n" +
				"　　　　　　create_time TIMESTAMP DEFAULT CURRENT TIMESTAMP NOT NULL,\n" +
				"　　　　　　PRIMARY KEY(company)\n" +
				");\n" +
				"COMMENT ON TABLE table_name IS '某某表';\n" +
				"COMMENT ON COLUMN table_name.company IS '分公司码';";

		DB2StatementParser parser3 =new DB2StatementParser(sql2);
		List<SQLStatement>  list3 = parser3.parseStatementList();
//		list = parser3.getTableElementList();
		for(SQLStatement statement:list3){
			if(statement instanceof DB2CreateTableStatement){
				List<SQLTableElement> elms = ((DB2CreateTableStatement) statement).getTableElementList();
				for(SQLTableElement elm :elms){
					if(elm instanceof  SQLColumnDefinition){
						System.out.println("字段名:"+((SQLColumnDefinition) elm).getName());
						System.out.println("字段类型:"+((SQLColumnDefinition) elm).getDataType());
						System.out.println("中文名:"+((SQLColumnDefinition) elm).getComment());
					}

				}
			}else if(statement instanceof SQLCommentStatement){
				System.out.println("comment字段:"+((SQLCommentStatement) statement).getOn());
				System.out.println("中文名:"+((SQLCommentStatement) statement).getComment());
			}

		}
	}
}
