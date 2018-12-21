package kz.ffinlife.ws.components.changePassword;

import ffinlife.ws.ChangePassCabAgentRequest;
import ffinlife.ws.ChangePassCabAgentResponse;
import ffinlife.ws.RecoveryPassCabAgentRequest;
import ffinlife.ws.RecoveryPassCabAgentResponse;
import kz.ffinlife.ws.components.common.CommonComponent;
import oracle.jdbc.driver.OracleDriver;
import org.springframework.stereotype.Component;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


@Component
public class RecoveryPassCabAgentComponent extends CommonComponent {

    public RecoveryPassCabAgentResponse recoveryPassCabAgent(RecoveryPassCabAgentRequest request){

        RecoveryPassCabAgentResponse commonResponse = checkSession(request, new RecoveryPassCabAgentResponse());
        if(commonResponse != null){
            return commonResponse;
        }


        RecoveryPassCabAgentResponse response = new RecoveryPassCabAgentResponse();

        Connection conn = null;
        CallableStatement callableStatement = null;

        try {

            DriverManager.registerDriver(new OracleDriver());

            String url = "jdbc:oracle:thin:@10.0.0.10:1526:bsolife";

            conn = DriverManager.getConnection(url, "mlm", "mlm");

            String sql = "{ ? = call mlm.CAB_ADMIN_PCK.recoverpas(?,?,?,?) }";

            callableStatement = conn.prepareCall(sql);

            //TODO change
            callableStatement.setString(2, request.getAgentLogin());
            callableStatement.setString(3, request.getEmail());
            callableStatement.setString(4, request.getAnswer());


            callableStatement.registerOutParameter(1, java.sql.Types.INTEGER);
            callableStatement.registerOutParameter(5, java.sql.Types.VARCHAR);

            callableStatement.execute();

            //this is the main line

            response.setRecoveryPassCabAgentResult(callableStatement.getInt(1));
            response.setRecoveryPassCabAgentMessage(callableStatement.getString(5));
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
