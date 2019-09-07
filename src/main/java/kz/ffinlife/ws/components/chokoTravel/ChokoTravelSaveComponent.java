package kz.ffinlife.ws.components.chokoTravel;

import ffinlife.ws.ChokoTravelSaveRequest;
import ffinlife.ws.ChokoTravelSaveResponse;
import kz.ffinlife.ws.components.common.CommonComponent;
import oracle.jdbc.driver.OracleDriver;
import org.springframework.stereotype.Component;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class ChokoTravelSaveComponent extends CommonComponent {

    public ChokoTravelSaveResponse chokoSave(ChokoTravelSaveRequest request) {

        ChokoTravelSaveResponse commonResponse = checkSession(request, new ChokoTravelSaveResponse());
        if(commonResponse != null){
            return commonResponse;
        }

        DateFormat sourceFormat = new SimpleDateFormat("dd.MM.yyyy");

        ChokoTravelSaveResponse response = new ChokoTravelSaveResponse();

        Connection conn = null;
        CallableStatement callableStatement = null;

        try {

            Date docDate = sourceFormat.parse(request.getDocDate());
            Date dateIn = sourceFormat.parse(request.getDateIn());
            Date dateOut = sourceFormat.parse(request.getDateOut());
            Date birth = sourceFormat.parse(request.getBirthDate());
            Date InsBDate = sourceFormat.parse(request.getInsurerBDate());
            Date InsDocDate = sourceFormat.parse(request.getInsurerDocDate());

            DriverManager.registerDriver(new OracleDriver()); //oracle driver

            String url = "jdbc:oracle:thin:@10.0.0.10:1526:bsolife"; //connection to DB

            conn = DriverManager.getConnection(url, "mlm", "mlm");

            String sql = "{? = call  webservice.reg_fly(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}"; // connected to webserevice and call method from LIC

            callableStatement = conn.prepareCall(sql);

            callableStatement.setString(2, request.getLastName());
            callableStatement.setString(3, request.getFirstName());
            callableStatement.setString(4, request.getIin());
            callableStatement.setString(5, request.getDocType());
            callableStatement.setString(6, request.getDocNumber());
            callableStatement.setDate(7, new java.sql.Date(docDate.getTime()));
            callableStatement.setDate(8, new java.sql.Date(birth.getTime()));
            callableStatement.setString(9, request.getCityOut());
            callableStatement.setString(10, request.getCityIn());
            callableStatement.setDate(11, new java.sql.Date(dateOut.getTime()));
            callableStatement.setDate(12, new java.sql.Date(dateIn.getTime()));
            callableStatement.setString(13, request.getTypeFly());
            callableStatement.setString(14, request.getTypeFlight());
            callableStatement.setString(15, request.getInsurerName());
            callableStatement.setString(16, request.getInsurerSurname());
            callableStatement.setString(17, request.getInsurerInn());
            callableStatement.setString(18, request.getInsurerDocType());
            callableStatement.setString(19, request.getInsurerDocNumber());
            callableStatement.setDate(20, new java.sql.Date(InsDocDate.getTime()));
            callableStatement.setDate(21, new java.sql.Date(InsBDate.getTime()));
            callableStatement.setString(22, request.getInsurerPhoneNumber());
            callableStatement.setString(23, request.getInsurerEmail());

            callableStatement.registerOutParameter(1, java.sql.Types.VARCHAR);

            callableStatement.execute();

            response.setResponse(callableStatement.getString(1));
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
