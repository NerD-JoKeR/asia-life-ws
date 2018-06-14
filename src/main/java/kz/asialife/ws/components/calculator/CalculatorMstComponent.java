package kz.asialife.ws.components.calculator;

import kz.asialife.ws.MstRequest;
import kz.asialife.ws.MstResponse;
import org.springframework.stereotype.Component;


import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import oracle.jdbc.driver.OracleDriver;

@Component
public class CalculatorMstComponent {
    public MstResponse mst(MstRequest request){
        MstResponse response = new MstResponse();
        try {
            DriverManager.registerDriver(new OracleDriver());
            String url = "jdbc:oracle:thin:@10.0.0.10:1526:bsolife";
            Connection conn = DriverManager.getConnection(url, "mlm", "mlm");
            String sql = "{ ? = call WEBSERVICE.calc_mst(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) }";
            CallableStatement callableStatement = conn.prepareCall(sql);
            callableStatement.setInt(2, request.getInsuranceProgramm());
            //TODO dont forget correct date
            callableStatement.setDate(3, new java.sql.Date(request.getBeginDate().getTimezone()));
            callableStatement.setDate(4, new java.sql.Date(request.getEndDate().getTimezone()));
            callableStatement.setDate(5, new java.sql.Date(request.getBirthDate().getTimezone()));
            callableStatement.setInt(6, 0);//sum_strah
            callableStatement.setInt(7, request.getCountry1());
            callableStatement.setInt(8, request.getCountry2());
            callableStatement.setInt(9, request.getCountry3());
            callableStatement.setInt(10, request.getNumberOfDays());
            callableStatement.setInt(11, 30);//rprog_max_days
            callableStatement.setString(12, request.getContactNumber());
            callableStatement.setString(13, request.getEmail());

            callableStatement.registerOutParameter(1, java.sql.Types.VARCHAR);
            callableStatement.registerOutParameter(14, java.sql.Types.VARCHAR);
            callableStatement.registerOutParameter(15, java.sql.Types.VARCHAR);
            callableStatement.registerOutParameter(16, java.sql.Types.VARCHAR);
            callableStatement.registerOutParameter(17, java.sql.Types.VARCHAR);

            callableStatement.execute();
            //this is the main line
            response.setResult(callableStatement.getString(1));
            response.setKurs(callableStatement.getString(14));
            response.setSumStrahKz(callableStatement.getString(15));
            response.setPremEur(callableStatement.getString(16));
            response.setErr(callableStatement.getString(17));

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return response;
    }
}
