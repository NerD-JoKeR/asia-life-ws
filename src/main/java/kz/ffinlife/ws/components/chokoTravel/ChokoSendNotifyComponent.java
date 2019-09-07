package kz.ffinlife.ws.components.chokoTravel;

import ffinlife.ws.ChokoTravelSendNotificationRequest;
import ffinlife.ws.ChokoTravelSendNotificationResponse;
import kz.ffinlife.ws.components.common.CommonComponent;
import oracle.jdbc.driver.OracleDriver;
import org.springframework.stereotype.Component;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


@Component
public class ChokoSendNotifyComponent extends CommonComponent {
    public ChokoTravelSendNotificationResponse chokoSendNotify(ChokoTravelSendNotificationRequest request) {

        ChokoTravelSendNotificationResponse commonResponse = checkSession(request, new ChokoTravelSendNotificationResponse());
        if(commonResponse != null){
            return commonResponse;
        }

        ChokoTravelSendNotificationResponse response = new ChokoTravelSendNotificationResponse();

        Connection conn = null;
        CallableStatement callableStatement = null;

        try {
            DriverManager.registerDriver(new OracleDriver()); //oracle driver

            String url = "jdbc:oracle:thin:@10.0.0.10:1526:bsolife"; //connection to DB

            conn = DriverManager.getConnection(url, "mlm", "mlm");

            String sql = "{? = call  webservice.sendNotify_fly(?)}"; // connected to webserevice and call method from LIC

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
