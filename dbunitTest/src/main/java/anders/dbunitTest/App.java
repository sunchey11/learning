package anders.dbunitTest;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.dbunit.DatabaseUnitException;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.database.QueryDataSet;
import org.dbunit.dataset.DataSetException;
import org.dbunit.dataset.xml.FlatXmlDataSet;

/**
 * 导出数据
 *
 */
public class App 
{
    public static void main( String[] args ) throws IOException, ClassNotFoundException, SQLException, DatabaseUnitException
    {
    	
    	String driver = "com.mysql.jdbc.Driver";//数据库驱动
		String url = "jdbc:mysql://127.0.0.1:13306/pwp_demo";//**指的是数据库名称
		String username = "root";//数据库用户名
		String password = "root";//数据库密码
		Class.forName(driver);//加载数据库驱动
		Connection  conn = DriverManager.getConnection(url,username,password);//连接Connection对象
    	IDatabaseConnection connection = new DatabaseConnection(conn);
		QueryDataSet backupDataSet = new QueryDataSet(connection);
		
    	backupDataSet.addTable("pwp_account");
    	backupDataSet.addTable("pwp_org");
    	File file = new File("aa.xml");// 备份文件
    	FlatXmlDataSet.write(backupDataSet,new FileOutputStream(file));
    	FlatXmlDataSet.write(backupDataSet, new FileWriter("bbb.xml"), "gbk");
    }
}
