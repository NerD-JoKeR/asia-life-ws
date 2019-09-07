package kz.ffinlife.ws.components.workAct;

import ffinlife.ws.*;
import kz.ffinlife.ws.components.common.CommonComponent;
import oracle.jdbc.driver.OracleDriver;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.sql.*;


@Component
public class Word2PdfComponent extends CommonComponent {

    private static final String PATH_TO_EXE = "C:\\work\\app\\asia-life-ws\\jar\\OfficeToPDF.exe";

    public Word2PdfResponse word2pdf(Word2PdfRequest request) {

        Word2PdfResponse commonResponse = checkSession(request, new Word2PdfResponse());
        if(commonResponse != null){
            return commonResponse;
        }

        Word2PdfResponse response = new Word2PdfResponse();

        Connection conn = null;
        CallableStatement callableStatement = null;
        PreparedStatement pstmt = null;

        try {
            DriverManager.registerDriver(new OracleDriver()); //oracle driver

            String url = "jdbc:oracle:thin:@10.0.0.9:1521:lic"; //connection to DB

            conn = DriverManager.getConnection(url, "lic", "life###1");

            String sql = "{? = call Z$PX_UIX_DEVELOPER.get_doc(?)}"; // connected to webserevice and call method from LIC

            callableStatement = conn.prepareCall(sql);

            callableStatement.setString(2, request.getDocNumber());

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
            System.out.println("byte part ok");
            FileUtils.writeByteArrayToFile(new File("C:\\work\\app\\asia-life-ws\\pdfAIS\\" + request.getDocNumber() + ".doc"), byteData);
            System.out.println("to file part ok");

            final String PATH_TO_TEMPLATE = "C:\\work\\app\\asia-life-ws\\pdfAIS\\" + request.getDocNumber() + ".doc";
            final String PATH_TO_OUTPUT = "C:\\work\\app\\asia-life-ws\\pdfAIS\\" + request.getDocNumber() + ".pdf";

            Process process;
            process = new ProcessBuilder(PATH_TO_EXE, PATH_TO_TEMPLATE, PATH_TO_OUTPUT).start();
            process.waitFor();

            System.out.println("pdf ready part ok");

            System.out.println("Result of Office processing: " + process.exitValue());
            File file = new File(PATH_TO_OUTPUT);
            byte[] fileContent = Files.readAllBytes(file.toPath());
            System.out.println(fileContent.length);

            pstmt = conn.prepareStatement("update aaaaa  set fl= ? where docnum = ?");
            File blob = new File("C:\\work\\app\\asia-life-ws\\pdfAIS\\" + request.getDocNumber() + ".pdf");
            FileInputStream in = new FileInputStream(blob);

            // the cast to int is necessary because with JDBC 4 there is
            // also a version of this method with a (int, long)
            // but that is not implemented by Oracle

            pstmt.setBinaryStream(1, in, (int)blob.length());

            pstmt.setString(2, request.getDocNumber());  // set the PK value
            pstmt.executeUpdate();
            conn.commit();
            pstmt.close();

            response.setDoc("Номер документа PDF : " + request.getDocNumber());
            response.setSuccess(true);

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
