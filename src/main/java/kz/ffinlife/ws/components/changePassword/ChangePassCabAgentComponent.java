package kz.ffinlife.ws.components.changePassword;


import ffinlife.ws.ChangePassCabAgentRequest;
import ffinlife.ws.ChangePassCabAgentResponse;
import kz.ffinlife.ws.components.common.CommonComponent;
import oracle.jdbc.driver.OracleDriver;
import org.springframework.stereotype.Component;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


@Component
public class ChangePassCabAgentComponent extends CommonComponent {

    public ChangePassCabAgentResponse changePassCab(ChangePassCabAgentRequest request){

        ChangePassCabAgentResponse commonResponse = checkSession(request, new ChangePassCabAgentResponse());
        if(commonResponse != null){
            return commonResponse;
        }


        ChangePassCabAgentResponse response = new ChangePassCabAgentResponse();

        Connection conn = null;
        CallableStatement callableStatement = null;

        try {

            DriverManager.registerDriver(new OracleDriver());

            String url = "jdbc:oracle:thin:@10.0.0.10:1526:bsolife";

            conn = DriverManager.getConnection(url, "mlm", "mlm");

            String sql = "{ ? = call mlm.CAB_ADMIN_PCK.change_pas(?,?,?,?,?) }";

            callableStatement = conn.prepareCall(sql);

            callableStatement.setString(2, request.getAgentLogin());
            callableStatement.setString(3, request.getOldPass());
            callableStatement.setString(4, request.getNewPass1());
            callableStatement.setString(5, request.getNewPass2());

            callableStatement.registerOutParameter(1, java.sql.Types.INTEGER);
            callableStatement.registerOutParameter(6, java.sql.Types.VARCHAR);

            callableStatement.execute();

            //this is the main line

            response.setChangePassResult(callableStatement.getInt(1));
            response.setChangePassMessage(callableStatement.getString(6));
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
