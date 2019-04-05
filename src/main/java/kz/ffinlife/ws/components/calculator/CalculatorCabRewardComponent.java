package kz.ffinlife.ws.components.calculator;

import ffinlife.ws.CalculatorCabRewardRequest;
import ffinlife.ws.CalculatorCabRewardResponse;
import kz.ffinlife.ws.components.common.CommonComponent;
import oracle.jdbc.driver.OracleDriver;
import org.springframework.stereotype.Component;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


@Component
public class CalculatorCabRewardComponent extends CommonComponent {

    public CalculatorCabRewardResponse cabReward(CalculatorCabRewardRequest request) {

        CalculatorCabRewardResponse commonResponse = checkSession(request, new CalculatorCabRewardResponse());
        if(commonResponse != null){
            return commonResponse;
        }

        CalculatorCabRewardResponse response = new CalculatorCabRewardResponse();

        Connection conn = null;
        CallableStatement callableStatement = null;

        try {
            DriverManager.registerDriver(new OracleDriver()); //oracle driver

            String url = "jdbc:oracle:thin:@10.0.0.10:1526:bsolife"; //connection to DB

            conn = DriverManager.getConnection(url, "mlm", "mlm");

            String sql = "{? = call cab_util_pck.runCalcReward(?,?,?,?,?,?,?,?,?)}"; // connected to webserevice and call method from LIC

            callableStatement = conn.prepareCall(sql);

            callableStatement.setString(2, request.getAgentID());
            callableStatement.setString(3, request.getProductId());
            callableStatement.setString(4, request.getBonusAmount());
            callableStatement.setString(5, request.getTermInsurance());
            callableStatement.setString(6, request.getYearPayment());
            callableStatement.setString(7, request.getSubordinate());


            callableStatement.registerOutParameter(1, java.sql.Types.INTEGER);
            callableStatement.registerOutParameter(8, java.sql.Types.VARCHAR);
            callableStatement.registerOutParameter(9, java.sql.Types.VARCHAR);
            callableStatement.registerOutParameter(10, java.sql.Types.VARCHAR);


            callableStatement.execute();

            response.setCabRewardResult(callableStatement.getInt(1));
            response.setUnitsAmount(callableStatement.getString(8));
            response.setCommissionAmount(callableStatement.getString(9));
            response.setCabRewardMessage(callableStatement.getString(10));
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
