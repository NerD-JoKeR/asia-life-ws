package kz.asialife.ws.components.authorization;

import kz.asialife.ws.AuthorizationRequest;
import kz.asialife.ws.AuthorizationResponse;
import org.springframework.stereotype.Component;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import oracle.jdbc.driver.OracleDriver;

@Component
public class AuthorizationComponent {
    public AuthorizationResponse authorize(AuthorizationRequest request){

        AuthorizationResponse response = new AuthorizationResponse();
        //TODO finally close conn & callableStatement
        try {

            DriverManager.registerDriver(new OracleDriver());
            String url = "jdbc:oracle:thin:@10.0.0.10:1526:bsolife";
            Connection conn = DriverManager.getConnection(url, "mlm", "mlm");
            String sql = "{ ? = call mlm.WEBSERVICE.kab_kln_authoriz(?,?,?,?,?) }";
            CallableStatement callableStatement = conn.prepareCall(sql);
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

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return response;
    }
}
