package kz.asialife.ws.components.calculator;


import kz.asialife.ws.KazinaRequest;
import kz.asialife.ws.KazinaResponse;
import oracle.jdbc.driver.OracleDriver;
import org.springframework.stereotype.Component;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Types;


@Component
public class CalculatorKazinaComponent {
    public KazinaResponse kazina(KazinaRequest request) {

        KazinaResponse response = new KazinaResponse();
        try {
            DriverManager.registerDriver(new OracleDriver());
            String url = "jdbc:oracle:thin:@10.0.0.10:1526:bsolife";
            Connection conn = DriverManager.getConnection(url, "mlm", "mlm");
            String sql = "{ ? = call WEBSERVICE.calc_kazina(?,?,?,?,?,?,?,?,?,?,?,?) }";
            CallableStatement callableStatement = conn.prepareCall(sql);
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

            callableStatement.registerOutParameter(1, java.sql.Types.INTEGER);
            callableStatement.registerOutParameter(7, java.sql.Types.INTEGER);
            callableStatement.registerOutParameter(8, java.sql.Types.INTEGER);
            callableStatement.registerOutParameter(9, java.sql.Types.INTEGER);
            callableStatement.registerOutParameter(10, java.sql.Types.INTEGER);
            callableStatement.registerOutParameter(11, java.sql.Types.INTEGER);
            callableStatement.registerOutParameter(12, java.sql.Types.INTEGER);

            callableStatement.execute();
            //this is the main line
            response.setNumber(callableStatement.getInt(1));
            response.setOutADB(callableStatement.getInt(7));
            response.setOutATPD(callableStatement.getInt(8));
            response.setOutTT(callableStatement.getInt(9));
            response.setOutCI(callableStatement.getInt(10));
            response.setOutTD(callableStatement.getInt(11));
            response.setOutHD(callableStatement.getInt(12));


        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return response;
    }
}