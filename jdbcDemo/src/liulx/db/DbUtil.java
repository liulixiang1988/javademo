package liulx.db;

import java.sql.*;

/**
 * Created by liulixiang on 2015/4/21.
 * Description:
 */
public class DbUtil {

    public static final String URL = "jdbc:mysql://localhost:3306/imooc";
    public static final String USER = "liulx";
    public static final String PASSWORD = "123456";
    private static Connection conn = null;
    static{
        //1.������������
        try {
            Class.forName("com.mysql.jdbc.Driver");
            //2. ������ݿ�����
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection(){
        return conn;
    }

    public static void main(String[] args) throws Exception {
        //1.������������
        Class.forName("com.mysql.jdbc.Driver");
        //2. ������ݿ�����
        Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
        //3.�������ݿ⣬ʵ����ɾ�Ĳ�
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT user_name, age FROM imooc_goddess");

        while(rs.next()){
            System.out.println(rs.getString("user_name")+" ���䣺"+rs.getInt("age"));
        }
    }
}
