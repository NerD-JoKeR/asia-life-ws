package kz.asialife.ws.components.cursor;


import kz.asialife.ws.CursorRequest;
import kz.asialife.ws.CursorResponse;
import java.sql.DriverManager;

import kz.asialife.ws.Document;
import oracle.jdbc.driver.OracleDriver;
import oracle.jdbc.driver.OracleTypes;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.CallableStatement;
import java.sql.ResultSet;


@Component
public class CursorComponent {
    public CursorResponse cursor(CursorRequest request){

        CursorResponse response = new CursorResponse();

        try{
            // Load the driver
            DriverManager.registerDriver(new OracleDriver());

            // Connect to the database
            String url = "jdbc:oracle:thin:@10.0.0.10:1526:bsolife";
            Connection conn = DriverManager.getConnection(url, "mlm", "mlm");
            // Prepare a PL/SQL call
            String sql = "{ ? = call WEBSERVICE.kab_kln_docs(?) }";
            CallableStatement callableStatement = conn.prepareCall(sql);

            callableStatement.setString(2, request.getInn());

            callableStatement.registerOutParameter (1, OracleTypes.CURSOR);

            callableStatement.execute ();

            ResultSet rs = (ResultSet)callableStatement.getObject (1);
            while (rs.next()) {
                String col1 = rs.getString("COL1");
                String col2 = rs.getString("COL2");
                String col3 = rs.getString("COL3");
                String col4 = rs.getString("COL4");
                String col5 = rs.getString("COL5");
                String col6 = rs.getString("COL6");
                String col7 = rs.getString("COL7");
                String col8 = rs.getString("COL8");
                Document document = new Document();
                document.setCol1(col1);
                document.setCol2(col2);
                document.setCol3(col3);
                document.setCol4(col4);
                document.setCol5(col5);
                document.setCol6(col6);
                document.setCol7(col7);
                document.setCol8(col8);
                response.getCursor().add(document);
            }
            rs.getArray(0);

            rs.close();
            callableStatement.close();
            conn.close();

        } catch (Exception ex) {
        ex.printStackTrace();
        }

        return response;
    }
}

