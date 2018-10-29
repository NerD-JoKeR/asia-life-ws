package kz.asialife.ws.components.calculator;

import kz.asialife.ws.CommonResponse;
import kz.asialife.ws.OsrnsRequest;
import kz.asialife.ws.OsrnsResponse;
import kz.asialife.ws.components.common.CommonComponent;
import org.springframework.stereotype.Component;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import oracle.jdbc.driver.OracleDriver;


@Component
public class CalculatorOSRNSComponent extends CommonComponent {

    public OsrnsResponse osrns(OsrnsRequest request) {

        OsrnsResponse commonResponse = checkSession(request, new OsrnsResponse());
        if(commonResponse != null){
            return commonResponse;
        }

        OsrnsResponse response = new OsrnsResponse();

        Connection conn = null;
        CallableStatement callableStatement = null;

        try {

            DriverManager.registerDriver(new OracleDriver()); //oracle driver

            String url = "TODO paste correct con url"; //connection to DB

            conn = DriverManager.getConnection(url, "log", "pass");

            String sql = "{ ? = call mlm.WEBSERVICE.calc_osrns(?,?,?,?,?) }"; // connected to webserevice and call method from LIC

            callableStatement = conn.prepareCall(sql);

            callableStatement.setInt(2, request.getOked());
            callableStatement.setDouble(3, request.getYearFond());
            callableStatement.setInt(4, request.getColSotr());


            callableStatement.registerOutParameter(1, java.sql.Types.DOUBLE);
            callableStatement.registerOutParameter(5, java.sql.Types.DOUBLE);
            callableStatement.registerOutParameter(6, java.sql.Types.VARCHAR);

            callableStatement.execute();
            //this is the main line to the return response
            response.setPremKz(callableStatement.getString(1));
            response.setSumStrahKz(String.valueOf(callableStatement.getDouble(5)));
            response.setErr(callableStatement.getString(6));
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
