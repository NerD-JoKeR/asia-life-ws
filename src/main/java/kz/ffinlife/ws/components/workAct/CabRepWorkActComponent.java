package kz.ffinlife.ws.components.workAct;


import ffinlife.ws.RepWorkActCabRequest;
import ffinlife.ws.RepWorkActCabResponse;
import kz.ffinlife.ws.components.common.CommonComponent;
import oracle.jdbc.driver.OracleDriver;
import org.springframework.stereotype.Component;

import java.sql.*;


@Component
public class CabRepWorkActComponent extends CommonComponent {

    public RepWorkActCabResponse repWorkAct(RepWorkActCabRequest request) {

        RepWorkActCabResponse commonResponse = checkSession(request, new RepWorkActCabResponse());
        if(commonResponse != null){
            return commonResponse;
        }

        RepWorkActCabResponse response = new RepWorkActCabResponse();

        Connection conn = null;
        CallableStatement callableStatement = null;

        try {
            DriverManager.registerDriver(new OracleDriver()); //oracle driver

            String url = "jdbc:oracle:thin:@10.0.0.10:1526:bsolife"; //connection to DB

            conn = DriverManager.getConnection(url, "mlm", "mlm");

            String sql = "{? = call cab_rep_workact.runReport(?,?,?,?,?,?,?,?,?)}"; // connected to webserevice and call method from LIC

            callableStatement = conn.prepareCall(sql);

            callableStatement.setString(2, request.getAgentLogin());
            callableStatement.setString(3, request.getRepType());
            callableStatement.setString(4, request.getAgentId());
            callableStatement.setString(5, request.getDepartmentId());
            callableStatement.setString(6, request.getRegionId());
            callableStatement.setString(7, request.getSubDepartmentId());
            callableStatement.setString(8, request.getDateFrom());
            callableStatement.setString(9, request.getDateTo());

            callableStatement.registerOutParameter(1, java.sql.Types.INTEGER);
            callableStatement.registerOutParameter(10, java.sql.Types.VARCHAR);

            callableStatement.execute();

            response.setResultCabWorkAct(callableStatement.getInt(1));
            response.setMessageCabWorkAct(callableStatement.getString(10));
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
