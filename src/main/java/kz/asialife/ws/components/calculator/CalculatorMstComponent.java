package kz.asialife.ws.components.calculator;

import kz.asialife.ws.CommonResponse;
import kz.asialife.ws.MstRequest;
import kz.asialife.ws.MstResponse;
import kz.asialife.ws.components.common.CommonComponent;
import org.springframework.stereotype.Component;


import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import oracle.jdbc.driver.OracleDriver;

@Component
public class CalculatorMstComponent extends CommonComponent {
    public MstResponse mst(MstRequest request){

        MstResponse commonResponse = checkSession(request, new MstResponse());
        if(commonResponse != null){
            return commonResponse;
        }

        DateFormat sourceFormat = new SimpleDateFormat("dd.MM.yyyy");

        MstResponse response = new MstResponse();

        Connection conn = null;
        CallableStatement callableStatement = null;

        try {

            Date begin = sourceFormat.parse(request.getBeginDate());
            Date end = sourceFormat.parse(request.getEndDate());
            Date birth = sourceFormat.parse(request.getBirthDate());

            DriverManager.registerDriver(new OracleDriver());

            String url = "TODO paste correct con url";

            conn = DriverManager.getConnection(url, "log", "pass");

            String sql = "{ ? = call WEBSERVICE.calc_mst(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) }";

            callableStatement = conn.prepareCall(sql);

            callableStatement.setInt(2, request.getInsuranceProgramm());
            callableStatement.setDate(3, new java.sql.Date(begin.getTime()));
            callableStatement.setDate(4, new java.sql.Date(end.getTime()));
            callableStatement.setDate(5, new java.sql.Date(birth.getTime()));
            callableStatement.setInt(6, request.getSumStrah());//sum_strah
            callableStatement.setInt(7, request.getCountry1());
            callableStatement.setInt(8, request.getCountry2());
            callableStatement.setInt(9, request.getCountry3());
            callableStatement.setInt(10, request.getRprogSrok());
            callableStatement.setInt(11, request.getRprogMaxDays());
            callableStatement.setInt(12, request.getRprogMaxDays());
            callableStatement.setString(13, request.getEmail());

            callableStatement.registerOutParameter(1, java.sql.Types.VARCHAR);
            callableStatement.registerOutParameter(14, java.sql.Types.VARCHAR);
            callableStatement.registerOutParameter(15, java.sql.Types.VARCHAR);
            callableStatement.registerOutParameter(16, java.sql.Types.VARCHAR);
            callableStatement.registerOutParameter(17, java.sql.Types.VARCHAR);

            callableStatement.execute();
            //this is the main line
            response.setPremKz(callableStatement.getString(1));
            response.setKurs(callableStatement.getString(14));
            response.setSumStrahKz(callableStatement.getString(15));
            response.setPremEur(callableStatement.getString(16));
            response.setErr(callableStatement.getString(17));
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
