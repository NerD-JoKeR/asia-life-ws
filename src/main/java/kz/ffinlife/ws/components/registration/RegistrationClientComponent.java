package kz.ffinlife.ws.components.registration;

import ffinlife.ws.RegistrationClientRequest;
import ffinlife.ws.RegistrationClientResponse;
import kz.ffinlife.ws.components.common.CommonComponent;
import oracle.jdbc.driver.OracleDriver;
import org.springframework.stereotype.Component;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


@Component
public class RegistrationClientComponent extends CommonComponent {

    public RegistrationClientResponse regCln(RegistrationClientRequest request) {

        RegistrationClientResponse commonResponse = checkSession(request, new RegistrationClientResponse());
        if(commonResponse != null){
            return commonResponse;
        }

        RegistrationClientResponse response = new RegistrationClientResponse();

        Connection conn = null;
        CallableStatement callableStatement = null;

        try {
            DriverManager.registerDriver(new OracleDriver()); //oracle driver

            String url = "jdbc:oracle:thin:@10.0.0.10:1526:bsolife"; //connection to DB

            conn = DriverManager.getConnection(url, "mlm", "mlm");

            String sql = "{? = call webservice.kab_kln_reg(?,?,?)}"; // connected to webserevice and call method from LIC

            callableStatement = conn.prepareCall(sql);

            callableStatement.setString(2, request.getDocNumber());
            callableStatement.setString(3, request.getInn());
            callableStatement.setString(4, request.getPhone());

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
