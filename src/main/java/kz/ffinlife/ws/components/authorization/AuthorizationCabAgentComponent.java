package kz.ffinlife.ws.components.authorization;

import ffinlife.ws.AuthorizationCabAgentRequest;
import ffinlife.ws.AuthorizationCabAgentResponse;
import kz.ffinlife.ws.components.common.CommonComponent;
import oracle.jdbc.driver.OracleDriver;
import org.springframework.stereotype.Component;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Component
public class AuthorizationCabAgentComponent extends CommonComponent {
    public AuthorizationCabAgentResponse authorizeCabAgent(AuthorizationCabAgentRequest request){

        AuthorizationCabAgentResponse commonResponse = checkSession(request, new AuthorizationCabAgentResponse());
        if(commonResponse != null){
            return commonResponse;
        }

        AuthorizationCabAgentResponse response = new AuthorizationCabAgentResponse();

        Connection conn = null;
        CallableStatement callableStatement = null;

        try {
            DriverManager.registerDriver(new OracleDriver());

            String url = "jdbc:oracle:thin:@10.0.0.10:1526:bsolife";

            conn = DriverManager.getConnection(url, "mlm", "mlm");

            String sql = "{ ? = call cab_admin_pck.auth_user(?,?,?) }";

            callableStatement = conn.prepareCall(sql);

            callableStatement.setString(2, request.getLogin());
            callableStatement.setString(3, request.getPassword());

            callableStatement.registerOutParameter(1, java.sql.Types.VARCHAR);
            callableStatement.registerOutParameter(4, java.sql.Types.VARCHAR);

            callableStatement.execute();

            response.setResult(callableStatement.getString(1));
            response.setName(callableStatement.getString(4));
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
