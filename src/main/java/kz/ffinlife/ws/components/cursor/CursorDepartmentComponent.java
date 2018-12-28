package kz.ffinlife.ws.components.cursor;

import ffinlife.ws.*;
import kz.ffinlife.ws.components.common.CommonComponent;
import oracle.jdbc.driver.OracleDriver;
import oracle.jdbc.driver.OracleTypes;
import org.springframework.stereotype.Component;

import java.sql.*;


@Component
public class CursorDepartmentComponent extends CommonComponent {

    public CursorDepartmentsResponse cursorDepartment(CursorDepartmentsRequest request){

        CursorDepartmentsResponse commonResponse = checkSession(request, new CursorDepartmentsResponse());
        if(commonResponse != null){
            return commonResponse;
        }

        CursorDepartmentsResponse response = new CursorDepartmentsResponse();

        Connection conn = null;
        CallableStatement callableStatement = null;

        try{
            // Load the driver
            DriverManager.registerDriver(new OracleDriver());

            // Connect to the database
            String url = "jdbc:oracle:thin:@10.0.0.10:1526:bsolife";

            conn = DriverManager.getConnection(url, "mlm", "mlm");

            // Prepare a PL/SQL call
            String sql = "{ ? = call cab_util_pck.get_departments}";

            callableStatement = conn.prepareCall(sql);

            callableStatement.registerOutParameter (1, OracleTypes.CURSOR);

            callableStatement.execute ();

            ResultSet rs = (ResultSet)callableStatement.getObject (1);
            response.setSuccess(true);
            while (rs.next()) {
                String ID = rs.getString("DEP_ID");
                String Name = rs.getString("DEP_NAME");
                Document1 document1 = new Document1();
                document1.setId(ID);
                document1.setName(Name);
                response.getDepartments().add(document1);
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
