package kz.asialife.ws.components.common;

import kz.asialife.ws.CommonRequest;
import kz.asialife.ws.CommonResponse;
import oracle.jdbc.driver.OracleDriver;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class CommonComponent {

    public CommonResponse checkSession(CommonRequest request){
        CommonResponse response = new CommonResponse();
        String sessionId = request.getSessionId();
        if(sessionId == null){
            response.setSuccess(false);
            response.setMessage("Ne predostavlen session id");
            return response;
        }

        Connection conn = null;
        CallableStatement callableStatement = null;

        try {
            DriverManager.registerDriver(new OracleDriver());
            String url = "jdbc:oracle:thin:@10.0.0.10:1526:bsolife";
            conn = DriverManager.getConnection(url, "mlm", "mlm");

            String sql = "{ ? = call mlm.WEBSERVICE.kab_kln_pass(?,?,?) }";
            callableStatement = conn.prepareCall(sql);
            callableStatement.setString(2, request.getSessionId());

            callableStatement.registerOutParameter(1, java.sql.Types.VARCHAR);

            callableStatement.execute();

            String sessionStatus = callableStatement.getString(1);
            if(sessionStatus.equals("BLOCKED")){
                response.setSuccess(false);
                response.setMessage("Sessia zablokirovana");
            } else if(sessionStatus.equals("EXPIRED")){
                response.setSuccess(false);
                response.setMessage("Sessia istekla");
            }

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


        return null;
    }

}
