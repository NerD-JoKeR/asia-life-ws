package kz.asialife.ws.components.cursor;


import kz.asialife.ws.CursorRequest;
import kz.asialife.ws.CursorResponse;
import java.sql.DriverManager;
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
            // You can put a database name after the @ sign in the connection URL.
            String url = "jdbc:oracle:thin:@10.0.0.10:1526:bsolife";
            Connection conn = DriverManager.getConnection(url, "mlm", "mlm");
            // Prepare a PL/SQL call
            String sql = "{ ? = call WEBSERVICE.kab_kln_docs(?) }";
            CallableStatement callableStatement = conn.prepareCall(sql);

            // Find out all the SALES person
            callableStatement.setString(2, request.getInn());

            callableStatement.registerOutParameter (1, OracleTypes.CURSOR);

            callableStatement.execute ();

            ResultSet rset = (ResultSet)callableStatement.getObject (1);

            // Dump the cursor
//            while (rset.next ())
//                System.out.println (rset.getString ("COL1"));
//                System.out.println (rset.getString ("COL2"));
//                System.out.println (rset.getString ("COL3"));
//                System.out.println (rset.getString ("COL4"));
//                System.out.println (rset.getString ("COL5"));
//                System.out.println (rset.getString ("COL6"));
//                System.out.println (rset.getString ("COL7"));
//                System.out.println (rset.getString ("COL8"));

            // Close all the resources
            rset.close();
            callableStatement.close();
            conn.close();

        } catch (Exception ex) {
        ex.printStackTrace();
        }

        return response;
    }
}

