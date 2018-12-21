package kz.ffinlife.ws.components.workAct;




import com.sun.xml.internal.messaging.saaj.util.Base64;
import ffinlife.ws.DocumentMTOM;
import ffinlife.ws.GetMTOMRequest;
import ffinlife.ws.GetMTOMResponse;
import ffinlife.ws.Mtom;
import kz.ffinlife.ws.components.common.CommonComponent;
import oracle.jdbc.driver.OracleDriver;
import oracle.sql.BLOB;
import org.springframework.stereotype.Component;


import javax.annotation.PostConstruct;
import java.io.File;
import java.io.FileInputStream;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;


@Component
public class CabDocumentsComponent extends CommonComponent {

    private static final String PROFILE_PICTURE_PATH = "E:/soap_mtom/";
    private static final Map<Integer, Mtom> USER_MAP = new HashMap<>();

    @PostConstruct
    public void init() {
        Mtom mtom = new Mtom();
        DocumentMTOM doc = new DocumentMTOM();
        try {
            doc.setName(mtom.getDocumentMTOM().getName());
            doc.setContent(BLOB.read(new File(PROFILE_PICTURE_PATH + user.getId() + ".jpeg")));
            mtom.setDocumentMTOM(doc);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public GetMTOMResponse mtom(GetMTOMRequest request) {

        GetMTOMResponse commonResponse = checkSession(request, new GetMTOMResponse());
        if(commonResponse != null){
            return commonResponse;
        }

        GetMTOMResponse response = new GetMTOMResponse();

        Connection conn = null;
        CallableStatement callableStatement = null;

        try {
            File blobFile = new File();
            FileInputStream in = new FileInputStream(blobFile);

            DriverManager.registerDriver(new OracleDriver()); //oracle driver

            String url = "jdbc:oracle:thin:@10.0.0.10:1526:bsolife"; //connection to DB

            conn = DriverManager.getConnection(url, "mlm", "mlm");
            //TODO change call sql request
            String sql = "{? = call webservice.get_reports(?)}"; // connected to webserevice and call method from LIC

            callableStatement = conn.prepareCall(sql);

            callableStatement.setInt(2, request.getDocNumber());

            callableStatement.setBinaryStream(1, in, (int)blobFile.length());
            callableStatement.executeUpdate();
            System.out.println("Lic part ok");
            Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery("SELECT USER_ID, NAME, BLOBDOCUMENT,  FROM cab_reports where ID = '" + request.getDocNumber() + "'");
            if (rs.next()) {
                String filename = rs.getString(1);
                Blob blob = rs.getBlob(2);
                byte[] bdata = blob.getBytes(1, (int) blob.length());
                String s = new String(Base64.encode(bdata));
            }

            callableStatement.execute();
            //this is the main line to the return response
            response.setMtom(callableStatement.getBlob(1));
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
