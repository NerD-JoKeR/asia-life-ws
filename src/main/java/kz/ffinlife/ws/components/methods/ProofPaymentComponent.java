package kz.ffinlife.ws.components.methods;


import ffinlife.ws.ProofPaymentRequest;
import ffinlife.ws.ProofPaymentResponse;
import kz.ffinlife.ws.components.common.CommonComponent;
import oracle.jdbc.driver.OracleDriver;
import org.springframework.stereotype.Component;

import java.sql.*;


@Component
public class ProofPaymentComponent extends CommonComponent {

    public ProofPaymentResponse payment(ProofPaymentRequest request){

        ProofPaymentResponse commonResponse = checkSession(request, new ProofPaymentResponse());
        if(commonResponse != null){
            return commonResponse;
        }

        ProofPaymentResponse response = new ProofPaymentResponse();

        Connection conn = null;
        CallableStatement callableStatement = null;

        try{
            DriverManager.registerDriver(new OracleDriver());

            String url = "jdbc:oracle:thin:@10.0.0.10:1526:bsolife";

            conn = DriverManager.getConnection(url, "mlm", "mlm");

            String sql = "{ ? = call WEBSERVICE.kab_kln_putpay(?) }";

            callableStatement = conn.prepareCall(sql);

            callableStatement.setString(2,request.getId());

            callableStatement.registerOutParameter(1, java.sql.Types.VARCHAR);

            callableStatement.execute ();

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
