package kz.ffinlife.ws.components.cursor;


import ffinlife.ws.CursorAgentIDRequest;
import ffinlife.ws.CursorAgentIDResponse;
import ffinlife.ws.Document2;
import kz.ffinlife.ws.components.common.CommonComponent;
import oracle.jdbc.driver.OracleDriver;
import oracle.jdbc.driver.OracleTypes;
import org.springframework.stereotype.Component;

import java.sql.*;


@Component
public class CursorAgentIDComponent extends CommonComponent {

    public CursorAgentIDResponse cursorAgentID(CursorAgentIDRequest request){

        CursorAgentIDResponse commonResponse = checkSession(request, new CursorAgentIDResponse());
        if(commonResponse != null){
            return commonResponse;
        }

        CursorAgentIDResponse response = new CursorAgentIDResponse();

        Connection conn = null;
        CallableStatement callableStatement = null;

        try{
            DriverManager.registerDriver(new OracleDriver());

            String url = "jdbc:oracle:thin:@10.0.0.10:1526:bsolife";

            conn = DriverManager.getConnection(url, "mlm", "mlm");

            String sql = "{ ? = call cab_util_pck.get_agent_id(?,?,?,?,?)}";

            callableStatement = conn.prepareCall(sql);

            callableStatement.setString(2, request.getDepartmentID());
            callableStatement.setString(3, request.getRegionID());
            callableStatement.setString(4, request.getSubDepID());
            callableStatement.setString(5, request.getFIO());
            callableStatement.setString(6, request.getAgentNumber());

            callableStatement.registerOutParameter (1, OracleTypes.CURSOR);

            callableStatement.execute ();

            ResultSet rs = (ResultSet)callableStatement.getObject (1);
            response.setSuccess(true);
            while (rs.next()) {
                String ID = rs.getString("AG_ID");
                String FIO = rs.getString("FULLNAME");
                String login = rs.getString("AGENT_NO");
                String regionId = rs.getString("REGION_ID");
                String depId = rs.getString("DEP_ID");
                String subDepId = rs.getString("SUBDEP_ID");
                Document2 document2 = new Document2();
                document2.setAgentID(ID);
                document2.setFIO(FIO);
                document2.setLogin(login);
                document2.setRegionID(regionId);
                document2.setDepID(depId);
                document2.setSubDepID(subDepId);
                response.getAgentsID().add(document2);
            }
            rs.getArray(0);

            rs.close();
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
