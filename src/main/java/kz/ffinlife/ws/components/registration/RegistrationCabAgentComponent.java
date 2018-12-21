package kz.ffinlife.ws.components.registration;

import ffinlife.ws.RegistrationCabAgentRequest;
import ffinlife.ws.RegistrationCabAgentResponse;
import kz.ffinlife.ws.components.common.CommonComponent;
import oracle.jdbc.driver.OracleDriver;
import org.springframework.stereotype.Component;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


@Component
public class RegistrationCabAgentComponent extends CommonComponent {

    public RegistrationCabAgentResponse registrationCabAgent(RegistrationCabAgentRequest request){

        RegistrationCabAgentResponse commonResponse = checkSession(request, new RegistrationCabAgentResponse());
        if(commonResponse != null){
            return commonResponse;
        }

        RegistrationCabAgentResponse response = new RegistrationCabAgentResponse();

        Connection conn = null;
        CallableStatement callableStatement = null;

        try {

            DriverManager.registerDriver(new OracleDriver());

            String url = "jdbc:oracle:thin:@10.0.0.10:1526:bsolife";

            conn = DriverManager.getConnection(url, "mlm", "mlm");

            String sql = "{ ? = call mlm.CAB_ADMIN_PCK.register_agent(?,?,?,?,?,?) }";

            callableStatement = conn.prepareCall(sql);

            callableStatement.setString(2, request.getCompNumber());
            callableStatement.setString(3, request.getIin());
            callableStatement.setString(4, request.getEmail());
            callableStatement.setString(5, request.getQuestion());
            callableStatement.setString(6, request.getAnswer());

            callableStatement.registerOutParameter(1, java.sql.Types.INTEGER);
            callableStatement.registerOutParameter(7, java.sql.Types.VARCHAR);

            callableStatement.execute();

            //this is the main line

            response.setCabResult(callableStatement.getInt(1));
            response.setCabMessage(callableStatement.getString(7));
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

