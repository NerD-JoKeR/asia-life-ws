package kz.asialife.banks.component.request;

import kz.asialife.banks.CommonResponse;
import kz.asialife.banks.PaymentRequest;
import kz.asialife.banks.PaymentResponse;
import kz.asialife.banks.component.common.CommonComponent;
import oracle.jdbc.driver.OracleDriver;
import org.springframework.stereotype.Component;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


@Component
public class PaymentComponent extends CommonComponent {
    public PaymentResponse payment(PaymentRequest request) {

        CommonResponse commonResponse = checkSession(request);
        if(commonResponse != null){
            return (PaymentResponse)commonResponse;
        }

        PaymentResponse response = new PaymentResponse();

        Connection conn = null;
        CallableStatement callableStatement = null;

        try {
            DriverManager.registerDriver(new OracleDriver()); //oracle driver

            String url = "jdbc:oracle:thin:@10.0.0.10:1526:bsolife"; //connection to DB

            conn = DriverManager.getConnection(url, "mlm", "mlm");

            String sql = "{ ? = call mlm.WEBSERVICE.reg_pay_doc_bank(?,?,?,?,?) }";  // connected to webserevice and call method from LIC

            callableStatement = conn.prepareCall(sql);

            callableStatement.setString(2, request.getDocNumber());
            callableStatement.setString(3, request.getSum());
            callableStatement.setString(4, request.getStatus());
            callableStatement.setString(5, request.getMessage());

            callableStatement.registerOutParameter(1, java.sql.Types.VARCHAR);

            callableStatement.execute();
            //this is the main line to the return response
            response.setState(callableStatement.getString(1));
            response.getMessage();

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
