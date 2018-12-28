package kz.ffinlife.ws.components.workAct;


import ffinlife.ws.CabMTOMRequest;
import ffinlife.ws.CabMTOMResponse;
import kz.ffinlife.ws.components.common.CommonComponent;
import org.springframework.stereotype.Component;
import oracle.jdbc.driver.OracleDriver;


import java.sql.*;


@Component
public class CabDocumentsComponent extends CommonComponent {

    public CabMTOMResponse mtom(CabMTOMRequest request) {

        CabMTOMResponse commonResponse = checkSession(request, new CabMTOMResponse());
        if(commonResponse != null){
            return commonResponse;
        }

        CabMTOMResponse response = new CabMTOMResponse();

        Connection conn = null;
        CallableStatement callableStatement = null;

        try {
            DriverManager.registerDriver(new OracleDriver()); //oracle driver

            String url = "jdbc:oracle:thin:@10.0.0.10:1526:bsolife"; //connection to DB

            conn = DriverManager.getConnection(url, "mlm", "mlm");

            String sql = "{? = call webservice.get_reports(?)}"; // connected to webserevice and call method from LIC

            callableStatement = conn.prepareCall(sql);

            callableStatement.setInt(2, request.getDocNumber());

            callableStatement.registerOutParameter(1, java.sql.Types.BLOB);

//            Statement statement = conn.createStatement();
//            ResultSet rs = statement.executeQuery("SELECT BLOBDOCUMENT FROM cab_reports where PK_ID = " + request.getDocNumber());
//            byte[] bdata = null;
//            String s = "";
//            if (rs.next()) {
//                Blob blob = rs.getBlob(1);
//                bdata = blob.getBytes(1, (int) blob.length());
//                s = new String(Base64.encode(bdata));
//            }
//            Blob blob = rs.getBlob(5);
//            byte[] bdata = blob.getBytes(1, (int) blob.length());

            System.out.println("Lic part ok");


            callableStatement.execute();

            byte[] byteData = callableStatement.getBytes(1);
            response.setMtom(byteData);
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
