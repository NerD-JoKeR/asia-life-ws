package kz.ffinlife.ws.components.workAct;

import ffinlife.ws.CabPayRollRequest;
import ffinlife.ws.CabPayRollResponse;
import kz.ffinlife.ws.components.common.CommonComponent;
import oracle.jdbc.driver.OracleDriver;
import org.springframework.stereotype.Component;

import java.sql.*;


@Component
public class CabPayRollComponent extends CommonComponent {

    public CabPayRollResponse cabPayRoll(CabPayRollRequest request) {

        CabPayRollResponse commonResponse = checkSession(request, new CabPayRollResponse());
        if(commonResponse != null){
            return commonResponse;
        }

        CabPayRollResponse response = new CabPayRollResponse();

        Connection conn = null;
        CallableStatement callableStatement = null;

        try {
            DriverManager.registerDriver(new OracleDriver()); //oracle driver

            String url = "jdbc:oracle:thin:@10.0.0.10:1526:bsolife"; //connection to DB

            conn = DriverManager.getConnection(url, "mlm", "mlm");

            String sql = "{? = call cab_rep_payroll_2.runReport(?,?,?,?,?,?,?,?,?,?)}"; // connected to webserevice and call method from LIC

            callableStatement = conn.prepareCall(sql);

            callableStatement.setString(2, request.getAgentLogin());
            callableStatement.setString(3, request.getRepType());
            callableStatement.setString(4, request.getAgentId());
            callableStatement.setString(5, request.getDepartmentId());
            callableStatement.setString(6, request.getRegionId());
            callableStatement.setString(7, request.getSubDepartmentId());
            callableStatement.setString(8, request.getAgentFIO());
            callableStatement.setString(9, request.getAgentNumber());
            callableStatement.setString(10, request.getDateFrom());
            callableStatement.setString(11, request.getDateTo());

            callableStatement.registerOutParameter(1, java.sql.Types.INTEGER);
            //callableStatement.registerOutParameter(12, java.sql.Types.VARCHAR);

            callableStatement.execute();

            response.setCabPAyRollResult(callableStatement.getInt(1));
            //response.setCabPAyRollMessage(callableStatement.getString(12));
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
