package kz.ffinlife.ws.components.registration;


import ffinlife.ws.ClientVerifyRequest;
import ffinlife.ws.ClientVerifyResponse;
import kz.ffinlife.ws.components.common.CommonComponent;
import oracle.jdbc.driver.OracleDriver;
import org.springframework.stereotype.Component;

import java.sql.*;


@Component
public class VerifyClientComponent extends CommonComponent {

    public ClientVerifyResponse verKln(ClientVerifyRequest request) {

        ClientVerifyResponse commonResponse = checkSession(request, new ClientVerifyResponse());
        if(commonResponse != null){
            return commonResponse;
        }

        ClientVerifyResponse response = new ClientVerifyResponse();

        Connection conn = null;
        CallableStatement callableStatement = null;

        try {
            DriverManager.registerDriver(new OracleDriver()); //oracle driver

            String url = "jdbc:oracle:thin:@10.0.0.10:1526:bsolife"; //connection to DB

            conn = DriverManager.getConnection(url, "mlm", "mlm");

            String sql = "{? = call webservice.kab_kln_wer(?)}"; // connected to webserevice and call method from LIC

            callableStatement = conn.prepareCall(sql);

            callableStatement.setString(2, request.getInn());

            callableStatement.registerOutParameter(1, java.sql.Types.VARCHAR);

            callableStatement.execute();

            response.setResult(callableStatement.getString(1));
            response.setSuccess(true);

            callableStatement.close();
            conn.close();

        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            try {
                callableStatement.close();
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return response;
    }
}
