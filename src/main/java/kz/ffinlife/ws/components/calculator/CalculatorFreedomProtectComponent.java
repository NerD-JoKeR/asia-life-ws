package kz.ffinlife.ws.components.calculator;


import ffinlife.ws.CalculatorFreedomProtectRequest;
import ffinlife.ws.CalculatorFreedomProtectResponse;
import kz.ffinlife.ws.components.common.CommonComponent;
import oracle.jdbc.driver.OracleDriver;
import org.springframework.stereotype.Component;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Component
public class CalculatorFreedomProtectComponent extends CommonComponent{

    public CalculatorFreedomProtectResponse freedomProtect(CalculatorFreedomProtectRequest request) {

        CalculatorFreedomProtectResponse commonResponse = checkSession(request, new CalculatorFreedomProtectResponse());
        if(commonResponse != null){
            return commonResponse;
        }

        CalculatorFreedomProtectResponse response = new CalculatorFreedomProtectResponse();

        Connection conn = null;
        CallableStatement callableStatement = null;


        try {
            DriverManager.registerDriver(new OracleDriver());

            String url = "jdbc:oracle:thin:@10.0.0.10:1526:bsolife";

            conn = DriverManager.getConnection(url, "mlm", "mlm");

            String sql = "{ ? = call WEBSERVICE.CALC_KORGAU(?,?,?,?,?,?,?,?,?,?,?) }";

            callableStatement = conn.prepareCall(sql);

            callableStatement.setString(2, request.getPeriodichnost());
            callableStatement.setInt(3, request.getClnYears());
            callableStatement.setInt(4, request.getSrokYears());
            callableStatement.setInt(5, request.getStrSumRip());
            callableStatement.setInt(6, request.getStrSumDop());
            callableStatement.setInt(7, request.getStrSumNs());
            callableStatement.setInt(8, request.getStrSumBody());


            callableStatement.registerOutParameter(1, java.sql.Types.VARCHAR);
            callableStatement.registerOutParameter(9, java.sql.Types.VARCHAR);
            callableStatement.registerOutParameter(10, java.sql.Types.VARCHAR);
            callableStatement.registerOutParameter(11, java.sql.Types.VARCHAR);
            callableStatement.registerOutParameter(12, java.sql.Types.VARCHAR);

            callableStatement.execute();
            //this is the main line
            response.setNumber(callableStatement.getString(1));
            response.setOutStrSumDop(callableStatement.getString(9));
            response.setOutStrSumNs(callableStatement.getString(10));
            response.setOutStrSumBody(callableStatement.getString(11));
            response.setOutALL(callableStatement.getString(12));
            response.setSuccess(true);

            callableStatement.close();
            conn.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }finally {
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
