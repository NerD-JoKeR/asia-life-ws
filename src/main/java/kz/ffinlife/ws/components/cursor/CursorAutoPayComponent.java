package kz.ffinlife.ws.components.cursor;

import ffinlife.ws.*;
import kz.ffinlife.ws.components.common.CommonComponent;
import oracle.jdbc.driver.OracleDriver;
import oracle.jdbc.driver.OracleTypes;
import org.springframework.stereotype.Component;

import java.sql.*;


@Component
public class CursorAutoPayComponent extends CommonComponent {

    public CursorAutoPayResponse cursorAuto(CursorAutoPayRequest request){

        CursorAutoPayResponse commonResponse = checkSession(request, new CursorAutoPayResponse());
        if(commonResponse != null){
            return commonResponse;
        }

        CursorAutoPayResponse response = new CursorAutoPayResponse();

        Connection conn = null;
        CallableStatement callableStatement = null;

        try{
            DriverManager.registerDriver(new OracleDriver());

            String url = "jdbc:oracle:thin:@10.0.0.10:1526:bsolife";

            conn = DriverManager.getConnection(url, "mlm", "mlm");

            String sql = "{ ? = call WEBSERVICE.graphic_pay(?) }";

            callableStatement = conn.prepareCall(sql);

            callableStatement.setInt(2, request.getId());

            callableStatement.registerOutParameter (1, OracleTypes.CURSOR);

            callableStatement.execute ();

            ResultSet rs = (ResultSet)callableStatement.getObject (1);
            response.setSuccess(true);
            while (rs.next()) {
                String col1 = rs.getString("C_PLAN_DATE");
                String col2 = rs.getString("BASE_PAYMENT");
                Document5 doc = new Document5();
                doc.setData(col1);
                doc.setSum(col2);
                response.getInfo().add(doc);
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
