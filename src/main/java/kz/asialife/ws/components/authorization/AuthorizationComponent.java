package kz.asialife.ws.components.authorization;

import kz.asialife.ws.AuthorizationRequest;
import kz.asialife.ws.AuthorizationResponse;
import kz.asialife.ws.CommonResponse;
import kz.asialife.ws.components.common.CommonComponent;
import org.springframework.stereotype.Component;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import oracle.jdbc.driver.OracleDriver;

@Component
public class AuthorizationComponent  extends CommonComponent {

    public AuthorizationResponse authorize(AuthorizationRequest request){

        CommonResponse commonResponse = checkSession(request);
        if(commonResponse != null){
            return (AuthorizationResponse) commonResponse;
        }


        AuthorizationResponse response = new AuthorizationResponse();

        Connection conn = null;
        CallableStatement callableStatement = null;

        try {

            DriverManager.registerDriver(new OracleDriver());

            String url = "jdbc:oracle:thin:@10.0.0.10:1526:bsolife";

            conn = DriverManager.getConnection(url, "mlm", "mlm");

            String sql = "{ ? = call mlm.WEBSERVICE.kab_kln_authoriz(?,?,?,?,?) }";

            callableStatement = conn.prepareCall(sql);

            callableStatement.setString(2, request.getIin());
            callableStatement.setString(3, request.getPassword());

            callableStatement.registerOutParameter(1, java.sql.Types.VARCHAR);
            callableStatement.registerOutParameter(4, java.sql.Types.VARCHAR);
            callableStatement.registerOutParameter(5, java.sql.Types.VARCHAR);
            callableStatement.registerOutParameter(6, java.sql.Types.VARCHAR);

            callableStatement.execute();
            //this is the main line
            response.setResult(callableStatement.getString(1));
            response.setFio(callableStatement.getString(4));
            response.setPhone(callableStatement.getString(5));
            response.setEmail(callableStatement.getString(6));
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
