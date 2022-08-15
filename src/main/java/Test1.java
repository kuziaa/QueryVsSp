import dbConnection.SQLDatabaseConnection;
import queryReader.QueryReader;

import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.*;

public class Test1 {

    private final String queryFile = "";
    private final String spFile = "";
    private final String paramFile = "";

    public static void main(String[] args) throws SQLException, URISyntaxException, IOException {


        Connection con = SQLDatabaseConnection.getConnection("992150");
//        Statement statement = con.createStatement();
//        PreparedStatement pstm;
//        CallableStatement cstm;
//
//        String sql = "select " +
//                "    MA.Occupancy_DT, SUM(MA.Rooms_Sold) Occupancy  " +
//                "from  " +
//                "    MKT_Accom_Activity MA " +
//                "    join Accom_Type AT " +
//                "    on MA.Accom_Type_ID = AT.Accom_Type_ID " +
//                "    join Accom_Class AC " +
//                "    on AC.Accom_Class_ID = AT.Accom_Class_ID " +
//                "    join Mkt_Seg_Forecast_Group MSFG " +
//                "    on MSFG.Mkt_Seg_ID = MA.Mkt_Seg_ID " +
//                "where " +
//                "    MA.Occupancy_DT between '2022-07-11 00:00:00.000' and '2023-10-03 00:00:00.000' " +
//                "    and MSFG.Status_ID = 1 " +
//                "group by MA.Occupancy_DT     " +
//                "order by MA.Occupancy_DT  ";
//
//        long start1 = System.currentTimeMillis();
//        ResultSet resultSet = statement.executeQuery("select * from Accom_Class");
//        long end1 = System.currentTimeMillis();
//        System.out.println("Elapsed Time in milisec: "+ (end1-start1));
//
//        ResultSetMetaData rsmd = resultSet.getMetaData();
//        System.out.println(rsmd.getColumnCount());
//        System.out.println(rsmd.getColumnName(1));
//        System.out.println(rsmd.getColumnName(2));

//        QueryReader queryReader = new QueryReader("old_query_declare.sql");
//        PreparedStatement pstm = con.prepareStatement(queryReader.getQuery());
//        pstm.setString(1, "696");
//        pstm.setString(2, "696.31");
//        ResultSet rs = pstm.executeQuery();
//        while (rs.next()) {
//            System.out.println("" + rs.getRow() + "   " + rs.getInt("Accom_Activity_ID") + "       ");
//        }

        QueryReader queryReader = new QueryReader("new_SP.sql");
        CallableStatement cstm = con.prepareCall(queryReader.getQuery());
        cstm.setString(1, "2023-01-14");
        cstm.setString(2, "2022-12-24");
        cstm.setString(3, "2022-12-17");
        cstm.setString(4, "2022-12-10");
        cstm.setString(5, "2022-12-03");
        cstm.setString(6, "195");
        cstm.setString(7, "1");

        ResultSet rs = cstm.executeQuery();
        while (rs.next()) {
            System.out.println("" + rs.getRow() + "   " + rs.getString("avg_on_books") + "       ");
        }
    }
}
