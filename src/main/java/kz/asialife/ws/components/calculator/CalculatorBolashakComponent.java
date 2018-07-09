package kz.asialife.ws.components.calculator;

import kz.asialife.ws.BolashakRequest;
import kz.asialife.ws.BolashakResponse;
import kz.asialife.ws.CommonResponse;
import kz.asialife.ws.components.common.CommonComponent;
import oracle.jdbc.driver.OracleDriver;
import org.springframework.stereotype.Component;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


@Component
public class CalculatorBolashakComponent extends CommonComponent {

    public BolashakResponse bolashak(BolashakRequest request){

        CommonResponse commonResponse = checkSession(request);
        if(commonResponse != null){
            return (BolashakResponse)commonResponse;
        }

        BolashakResponse response = new BolashakResponse();

        Connection conn = null;
        CallableStatement callableStatement = null;

        try {
            DriverManager.registerDriver(new OracleDriver());

            String url = "jdbc:oracle:thin:@10.0.0.10:1526:bsolife";

            conn = DriverManager.getConnection(url, "mlm", "mlm");

            String sql = "{ ? = call WEBSERVICE.calc_bolashak(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) }";

            callableStatement = conn.prepareCall(sql);

            callableStatement.setString(2, request.getPeriodichnost());
            callableStatement.setString(3, request.getSex());
            callableStatement.setInt(4, request.getClnYears());
            callableStatement.setInt(5, request.getSrokYears());
            callableStatement.setInt(6, request.getStrSum());
            callableStatement.setInt(7, request.getDB());
            callableStatement.setInt(8, request.getADB());
            callableStatement.setInt(9, request.getATPD());
            callableStatement.setInt(10, request.getTT());
            callableStatement.setInt(11, request.getCI());
            callableStatement.setInt(12, request.getTD());
            callableStatement.setInt(13, request.getHD());
            callableStatement.setInt(14, request.getTTV());


            callableStatement.registerOutParameter(1, java.sql.Types.VARCHAR);
            callableStatement.registerOutParameter(15, java.sql.Types.VARCHAR);
            callableStatement.registerOutParameter(16, java.sql.Types.VARCHAR);
            callableStatement.registerOutParameter(17, java.sql.Types.VARCHAR);
            callableStatement.registerOutParameter(18, java.sql.Types.VARCHAR);
            callableStatement.registerOutParameter(19, java.sql.Types.VARCHAR);
            callableStatement.registerOutParameter(20, java.sql.Types.VARCHAR);
            callableStatement.registerOutParameter(21, java.sql.Types.VARCHAR);
            callableStatement.registerOutParameter(22, java.sql.Types.VARCHAR);
            callableStatement.registerOutParameter(23, java.sql.Types.VARCHAR);

            callableStatement.execute();
            //this is the main line
            response.setNumber(callableStatement.getString(1));
            response.setOutDB(callableStatement.getString(15));
            response.setOutADB(callableStatement.getString(16));
            response.setOutATPD(callableStatement.getString(17));
            response.setOutTT(callableStatement.getString(18));
            response.setOutCI(callableStatement.getString(19));
            response.setOutTD(callableStatement.getString(20));
            response.setOutHD(callableStatement.getString(21));
            response.setOutTPD(callableStatement.getString(22));
            response.setOutTTV(callableStatement.getString(23));
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
