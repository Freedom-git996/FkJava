package base.jdbc;

import javax.sql.rowset.CachedRowSet;
import javax.sql.rowset.RowSetFactory;
import javax.sql.rowset.RowSetProvider;
import java.sql.*;

public class ResultTest {

    public static void main(String[] args) throws ClassNotFoundException {
        int page = 1;
        int pageSize = 5;

        Class.forName("com.mysql.jdbc.Driver");
        String sql = "select * from employee where employee_id > ?";
        try (
                Connection conn = DriverManager.getConnection("jdbc:mysql://192.168.112.20:3306/ibatis", "root", "admin");
                PreparedStatement ps = conn.prepareStatement(sql);

        ) {
            DatabaseMetaData dm = conn.getMetaData();
            try (ResultSet tables = dm.getTables(null, null, "%", new String[]{"TABLE"})) {
//                for (int i = 0; i < tables.getMetaData().getColumnCount(); i++) {
//                    System.out.print(tables.getMetaData().getColumnName(i + 1) + "\t");
//                }
//                System.out.println();
//                while (tables.next()) {
//                    for (int i = 0; i < tables.getMetaData().getColumnCount(); i++) {
//                        System.out.print(tables.getString(i + 1) + "\t");
//                    }
//                    System.out.println();
//                }

                while (tables.next()) {
                    System.out.println("当前表名为：" + tables.getString("TABLE_NAME") + "\n");
                    try (ResultSet columns = dm.getColumns(null, null, tables.getString("TABLE_NAME"), "%")) {
                        while(columns.next()) {
                            System.out.println("列名：" + columns.getString("COLUMN_NAME") + "\t类型为：" + columns.getString("TYPE_NAME") + "\n");
                        }
                    }
                    System.out.println();
                }
            }

            System.out.println("\n开始执行查询操作");
            ps.setInt(1, 103);

            try(ResultSet rs = ps.executeQuery()) {
//                RowSetFactory factory = RowSetProvider.newFactory();
//                CachedRowSet cachedRowSet = factory.createCachedRowSet();
//                //分页设置
//                cachedRowSet.setPageSize(pageSize);
//                cachedRowSet.populate(rs, (page - 1) * pageSize + 1);


                ResultSetMetaData rsd = rs.getMetaData();
                System.out.println(rsd.getColumnName(1) + "\t" + rsd.getColumnName(2) + "\t" + rsd.getColumnName(3));
                while (rs.next()) {
                    System.out.println(rs.getInt(1) + "\t" + rs.getString(2) + "\t" + rs.getString(3));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
