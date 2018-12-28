package kz.ffinlife.ws.components.changePassword;

import ffinlife.ws.ChangePasswordRequest;
import ffinlife.ws.ChangePasswordResponse;
import kz.ffinlife.ws.components.common.CommonComponent;
import org.springframework.stereotype.Component;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import oracle.jdbc.driver.OracleDriver;

@Component
public class ChangePasswordComponent extends CommonComponent {

    public ChangePasswordResponse changePassword(ChangePasswordRequest request){
        ChangePasswordResponse commonResponse = checkSession(request, new ChangePasswordResponse());
        if(commonResponse != null){
            return commonResponse;
        }

        ChangePasswordResponse response = new ChangePasswordResponse();

        Connection conn = null;
        CallableStatement callableStatement = null;

        try {
            DriverManager.registerDriver(new OracleDriver());
            String url = "jdbc:oracle:thin:@10.0.0.10:1526:bsolife";
            conn = DriverManager.getConnection(url, "mlm", "mlm");

            String sql = "{ ? = call mlm.WEBSERVICE.kab_kln_pass(?,?,?) }";
            callableStatement = conn.prepareCall(sql);
            callableStatement.setString(2, request.getIin());
            callableStatement.setString(3, request.getPassword());
            callableStatement.setString(4, request.getOldPass());

            callableStatement.registerOutParameter(1, java.sql.Types.VARCHAR);

            callableStatement.execute();

            response.setResult(callableStatement.getString(1));
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
