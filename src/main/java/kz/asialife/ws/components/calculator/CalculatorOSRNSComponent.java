package kz.asialife.ws.components.calculator;

import kz.asialife.ws.OsrnsRequest;
import kz.asialife.ws.OsrnsResponse;
import org.springframework.stereotype.Component;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import oracle.jdbc.driver.OracleDriver;


@Component
public class CalculatorOSRNSComponent {
    public OsrnsResponse osrns(OsrnsRequest request) {

        OsrnsResponse response = new OsrnsResponse();
        try {

            DriverManager.registerDriver(new OracleDriver()); //oracle driver
            String url = "jdbc:oracle:thin:@10.0.0.10:1526:bsolife"; //connection to DB
            Connection conn = DriverManager.getConnection(url, "mlm", "mlm");
            //Connection conn = DriverManager.getConnection(PropertyValues.getPropertyValue("url_db"), PropertyValues.getPropertyValue("mlm"/*db_username*/), PropertyValues.getPropertyValue("mlm"/*db_password*/));
            String sql = "{ ? = call mlm.WEBSERVICE.calc_osrns(?,?,?,?,?) }"; // connected to webserevice and call method from LIC
            CallableStatement callableStatement = conn.prepareCall(sql);
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

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return response;
    }
}
