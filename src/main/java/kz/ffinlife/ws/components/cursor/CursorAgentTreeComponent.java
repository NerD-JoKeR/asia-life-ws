package kz.ffinlife.ws.components.cursor;

import ffinlife.ws.*;
import kz.ffinlife.ws.components.common.CommonComponent;
import oracle.jdbc.driver.OracleDriver;
import oracle.jdbc.driver.OracleTypes;
import org.springframework.stereotype.Component;

import java.sql.*;


@Component
public class CursorAgentTreeComponent extends CommonComponent {

    public CursorAgentTreeResponse cursorAgentTree(CursorAgentTreeRequest request){

        CursorAgentTreeResponse commonResponse = checkSession(request, new CursorAgentTreeResponse());
        if(commonResponse != null){
            return commonResponse;
        }

        CursorAgentTreeResponse response = new CursorAgentTreeResponse();

        Connection conn = null;
        CallableStatement callableStatement = null;

        try{
            DriverManager.registerDriver(new OracleDriver());

            String url = "jdbc:oracle:thin:@10.0.0.10:1526:bsolife";

            conn = DriverManager.getConnection(url, "mlm", "mlm");

            String sql = "{ ? = call cab_util_pck.get_agent_tree(?)}";

            callableStatement = conn.prepareCall(sql);

            callableStatement.setString(2, request.getAgentID());

            callableStatement.registerOutParameter (1, OracleTypes.CURSOR);

            callableStatement.execute ();

            ResultSet rs = (ResultSet)callableStatement.getObject (1);
            response.setSuccess(true);
            while (rs.next()) {
                String ID = rs.getString("AG_ID");
                String FIO = rs.getString("FULLNAME");
                String managerID = rs.getString("MANAGER_ID");
                String agentNo = rs.getString("AGENT_NO");
                String agetnLevel = rs.getString("LEV");
                Document2 document2 = new Document2();
                document2.setAgentID(ID);
                document2.setManagerID(managerID);
                document2.setFIO(FIO);
                document2.setAgentNo(agentNo);
                document2.setAgentLevel(agetnLevel);
                response.getAgentTree().add(document2);
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
