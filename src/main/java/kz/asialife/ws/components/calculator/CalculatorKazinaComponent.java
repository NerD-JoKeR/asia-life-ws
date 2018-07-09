package kz.asialife.ws.components.calculator;


import kz.asialife.ws.CommonResponse;
import kz.asialife.ws.KazinaRequest;
import kz.asialife.ws.KazinaResponse;
import kz.asialife.ws.components.common.CommonComponent;
import oracle.jdbc.driver.OracleDriver;
import org.springframework.stereotype.Component;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


@Component
public class CalculatorKazinaComponent extends CommonComponent {

    public KazinaResponse kazina(KazinaRequest request) {

        CommonResponse commonResponse = checkSession(request);
        if(commonResponse != null){
            return (KazinaResponse)commonResponse;
        }

        KazinaResponse response = new KazinaResponse();

        Connection conn = null;
        CallableStatement callableStatement = null;


        try {
            DriverManager.registerDriver(new OracleDriver());

            String url = "jdbc:oracle:thin:@10.0.0.10:1526:bsolife";

            conn = DriverManager.getConnection(url, "mlm", "mlm");

            String sql = "{ ? = call WEBSERVICE.calc_kazina(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) }";

            callableStatement = conn.prepareCall(sql);

            callableStatement.setString(2, request.getPeriodichnost());
            callableStatement.setString(3, request.getSex());
            callableStatement.setInt(4, request.getClnYears());
            callableStatement.setInt(5, request.getSrokYears());
            callableStatement.setInt(6, request.getStrSum());
            callableStatement.setInt(7, request.getADB());
            callableStatement.setInt(8, request.getATPD());
            callableStatement.setInt(9, request.getTT());
            callableStatement.setInt(10, request.getCI());
            callableStatement.setInt(11, request.getTD());
            callableStatement.setInt(12, request.getHD());



            callableStatement.registerOutParameter(1, java.sql.Types.VARCHAR);
            callableStatement.registerOutParameter(13, java.sql.Types.VARCHAR);
            callableStatement.registerOutParameter(14, java.sql.Types.VARCHAR);
            callableStatement.registerOutParameter(15, java.sql.Types.VARCHAR);
            callableStatement.registerOutParameter(16, java.sql.Types.VARCHAR);
            callableStatement.registerOutParameter(17, java.sql.Types.VARCHAR);
            callableStatement.registerOutParameter(18, java.sql.Types.VARCHAR);
            callableStatement.registerOutParameter(19, java.sql.Types.VARCHAR);

            callableStatement.execute();
            //this is the main line
            response.setNumber(callableStatement.getString(1));
            response.setOutADB(callableStatement.getString(13));
            response.setOutATPD(callableStatement.getString(14));
            response.setOutTT(callableStatement.getString(15));
            response.setOutCI(callableStatement.getString(16));
            response.setOutTD(callableStatement.getString(17));
            response.setOutHD(callableStatement.getString(18));
            response.setOutTPD(callableStatement.getString(19));
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