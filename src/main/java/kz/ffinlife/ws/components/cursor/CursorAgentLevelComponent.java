package kz.ffinlife.ws.components.cursor;

import ffinlife.ws.*;
import kz.ffinlife.ws.components.common.CommonComponent;
import oracle.jdbc.driver.OracleDriver;
import oracle.jdbc.driver.OracleTypes;
import org.springframework.stereotype.Component;


import java.sql.*;


@Component
public class CursorAgentLevelComponent  extends CommonComponent {

    public CursorAgentLevelResponse cursorLevel(CursorAgentLevelRequest request){

        CursorAgentLevelResponse commonResponse = checkSession(request, new CursorAgentLevelResponse());
        if(commonResponse != null){
            return commonResponse;
        }

        CursorAgentLevelResponse response = new CursorAgentLevelResponse();

        Connection conn = null;
        CallableStatement callableStatement = null;

        try{
            DriverManager.registerDriver(new OracleDriver());

            String url = "jdbc:oracle:thin:@10.0.0.10:1526:bsolife";

            conn = DriverManager.getConnection(url, "mlm", "mlm");

            String sql = "{ ? = call cab_util_pck.get_levels}";

            callableStatement = conn.prepareCall(sql);

            callableStatement.registerOutParameter (1, OracleTypes.CURSOR);

            callableStatement.execute ();

            ResultSet rs = (ResultSet)callableStatement.getObject (1);
            response.setSuccess(true);
            while (rs.next()) {
                String ID = rs.getString("NUMBER_ID");
                String Text = rs.getString("TEXT");
                String Tarif = rs.getString("TARIF");
                Document3 doc3 = new Document3();
                doc3.setNo(ID);
                doc3.setText(Text);
                doc3.setTarif(Tarif);
                response.getAgentLevels().add(doc3);
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
