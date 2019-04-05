package kz.ffinlife.ws.components.cursor;

import ffinlife.ws.*;
import kz.ffinlife.ws.components.common.CommonComponent;
import oracle.jdbc.driver.OracleDriver;
import oracle.jdbc.driver.OracleTypes;
import org.springframework.stereotype.Component;

import java.sql.*;


@Component
public class CursorAgentConditionComponent extends CommonComponent {

    public CursorAgentConditionResponse cursorCondition(CursorAgentConditionRequest request){

        CursorAgentConditionResponse commonResponse = checkSession(request, new CursorAgentConditionResponse());
        if(commonResponse != null){
            return commonResponse;
        }

        CursorAgentConditionResponse response = new CursorAgentConditionResponse();

        Connection conn = null;
        CallableStatement callableStatement = null;

        try{
            DriverManager.registerDriver(new OracleDriver());

            String url = "jdbc:oracle:thin:@10.0.0.10:1526:bsolife";

            conn = DriverManager.getConnection(url, "mlm", "mlm");

            String sql = "{ ? = call cab_util_pck.get_conditions}";

            callableStatement = conn.prepareCall(sql);

            callableStatement.registerOutParameter (1, OracleTypes.CURSOR);

            callableStatement.execute ();

            ResultSet rs = (ResultSet)callableStatement.getObject (1);
            response.setSuccess(true);
            while (rs.next()) {
                String ID = rs.getString("NUMBER_ID");
                String TEXT = rs.getString("CONDITIONS");
                String AWARD = rs.getString("AWARD");
                Document3 doc3 = new Document3();
                doc3.setNo(ID);
                doc3.setText(TEXT);
                doc3.setAward(AWARD);
                response.getAgentConditions().add(doc3);
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
