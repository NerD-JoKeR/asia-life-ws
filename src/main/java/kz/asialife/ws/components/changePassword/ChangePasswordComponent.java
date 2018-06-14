package kz.asialife.ws.components.changePassword;

import kz.asialife.ws.ChangePasswordRequest;
import kz.asialife.ws.ChangePasswordResponse;
import org.springframework.stereotype.Component;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import oracle.jdbc.driver.OracleDriver;

@Component
public class ChangePasswordComponent {
    public ChangePasswordResponse changePassword(ChangePasswordRequest request){

        ChangePasswordResponse response = new ChangePasswordResponse();
        try {
            DriverManager.registerDriver(new OracleDriver());
            String url = "jdbc:oracle:thin:@10.0.0.10:1526:bsolife";
            Connection conn = DriverManager.getConnection(url, "mlm", "mlm");
            String sql = "{ ? = call mlm.WEBSERVICE.kab_kln_pass(?,?,?) }";
            CallableStatement callableStatement = conn.prepareCall(sql);
            callableStatement.setString(2, request.getIin());
            callableStatement.setString(3, request.getPassword());
            callableStatement.setString(4, request.getOldPass());

            callableStatement.registerOutParameter(1, java.sql.Types.VARCHAR);

            callableStatement.execute();
            //this is the main line
            response.setResult(callableStatement.getString(1));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return response;
    }
}
