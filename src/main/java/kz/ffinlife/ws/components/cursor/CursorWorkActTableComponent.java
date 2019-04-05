package kz.ffinlife.ws.components.cursor;

import ffinlife.ws.CursorWorkActTableRequest;
import ffinlife.ws.CursorWorkActTableResponse;
import ffinlife.ws.Document4;
import kz.ffinlife.ws.components.common.CommonComponent;
import oracle.jdbc.driver.OracleDriver;
import oracle.jdbc.driver.OracleTypes;
import org.springframework.stereotype.Component;

import java.sql.*;


@Component
public class CursorWorkActTableComponent extends CommonComponent {

    public CursorWorkActTableResponse table(CursorWorkActTableRequest request){

        CursorWorkActTableResponse commonResponse = checkSession(request, new CursorWorkActTableResponse());
        if(commonResponse != null){
            return commonResponse;
        }

        CursorWorkActTableResponse response = new CursorWorkActTableResponse();

        Connection conn = null;
        CallableStatement callableStatement = null;

        try{
            DriverManager.registerDriver(new OracleDriver());

            String url = "jdbc:oracle:thin:@10.0.0.10:1526:bsolife";

            conn = DriverManager.getConnection(url, "mlm", "mlm");

            String sql = "{ ? = call cab_util_pck.get_agents_info(?)}";

            callableStatement = conn.prepareCall(sql);

            callableStatement.setString(2, request.getAgentID());

            callableStatement.registerOutParameter (1, OracleTypes.CURSOR);

            callableStatement.execute ();

            ResultSet rs = (ResultSet)callableStatement.getObject (1);
            response.setSuccess(true);
            while (rs.next()) {
                String dep = rs.getString("DEP_NAME");
                String region = rs.getString("REGION_NAME");
                String agentNo = rs.getString("AGENT_NO");
                String FIO = rs.getString("FULLNAME");
                String award = rs.getString("CS");
                Document4 doc = new Document4();
                doc.setDepartment(dep);
                doc.setFIO(FIO);
                doc.setAgentNo(agentNo);
                doc.setRegion(region);
                doc.setAward(award);
                response.getAgentInfo().add(doc);
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
