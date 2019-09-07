package kz.ffinlife.ws.components.chokoTravel;

import ffinlife.ws.ChokoTravelCheckRequest;
import ffinlife.ws.ChokoTravelCheckResponse;
import kz.ffinlife.ws.components.common.CommonComponent;
import oracle.jdbc.driver.OracleDriver;
import org.springframework.stereotype.Component;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Component
public class ChokoTravelCheckComponent extends CommonComponent {

    public ChokoTravelCheckResponse chokoCheck(ChokoTravelCheckRequest request) {

        ChokoTravelCheckResponse commonResponse = checkSession(request, new ChokoTravelCheckResponse());
        if(commonResponse != null){
            return commonResponse;
        }

        ChokoTravelCheckResponse response = new ChokoTravelCheckResponse();

        Connection conn = null;
        CallableStatement callableStatement = null;

        try {

            DriverManager.registerDriver(new OracleDriver()); //oracle driver

            String url = "jdbc:oracle:thin:@10.0.0.10:1526:bsolife"; //connection to DB

            conn = DriverManager.getConnection(url, "mlm", "mlm");

            String sql = "{? = call  webservice.check_fly(?)}"; // connected to webserevice and call method from LIC

            callableStatement = conn.prepareCall(sql);

            callableStatement.setString(2, request.getText());

            callableStatement.registerOutParameter(1, java.sql.Types.VARCHAR);

            callableStatement.execute();

            response.setResponse(callableStatement.getString(1));
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
