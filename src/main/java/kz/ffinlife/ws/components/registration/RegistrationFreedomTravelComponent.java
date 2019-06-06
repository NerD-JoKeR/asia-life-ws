package kz.ffinlife.ws.components.registration;



import ffinlife.ws.RegistrationFreedomTravelRequest;
import ffinlife.ws.RegistrationFreedomTravelResponse;
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
public class RegistrationFreedomTravelComponent extends CommonComponent {

    public RegistrationFreedomTravelResponse regFreedomTravel(RegistrationFreedomTravelRequest request){

        RegistrationFreedomTravelResponse commonResponse = checkSession(request, new RegistrationFreedomTravelResponse());
        if(commonResponse != null){
            return commonResponse;
        }

        DateFormat sourceFormat = new SimpleDateFormat("dd.MM.yyyy");

        RegistrationFreedomTravelResponse response = new RegistrationFreedomTravelResponse();

        Connection conn = null;
        CallableStatement callableStatement = null;

        try {
            Date begin = sourceFormat.parse(request.getDateStart());
            Date end = sourceFormat.parse(request.getDateEnd());
            Date birth = sourceFormat.parse(request.getDateBirth());
            Date birth2 = sourceFormat.parse(request.getDateBirth2());
            Date passportDate = sourceFormat.parse(request.getPassportDate());
            Date passportDate2 = sourceFormat.parse(request.getPassportDate2());
            Date passportEnd = sourceFormat.parse(request.getPassportDateEnd());
            Date passportEnd2 = sourceFormat.parse(request.getPassportDateEnd2());

            DriverManager.registerDriver(new OracleDriver());

            String url = "jdbc:oracle:thin:@10.0.0.10:1526:bsolife";

            conn = DriverManager.getConnection(url, "mlm", "mlm");

            String sql = "{ ? = call WEBSERVICE.reg_mst(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) }";

            callableStatement = conn.prepareCall(sql);

            callableStatement.setInt(2, request.getCountry1()); //Country1 in number
            callableStatement.setInt(3, request.getCountry2()); //Country2 in number
            callableStatement.setInt(4, request.getCountry3()); //Country3 in number
            callableStatement.setInt(5, request.getRprogramm()); //rprogramm in number
            callableStatement.setInt(6, request.getRprogSrok()); //rprog_srok in number
            callableStatement.setInt(7, request.getProgMaxDays()); //prog_max_days in number
            callableStatement.setDate(8, new java.sql.Date(begin.getTime())); //Date_Start in date
            callableStatement.setDate(9, new java.sql.Date(end.getTime())); //Date_End in date
            callableStatement.setInt(10, request.getCurRate()); //CurRate in number
            callableStatement.setString(11, request.getFioKir()); //FIO_kir in varchar2
            callableStatement.setString(12, request.getFioLat()); //FIO_lat in varchar2
            callableStatement.setString(13, request.getInn()); //IIN in varchar2
            callableStatement.setString(14, request.getResId()); //RESID in varchar2
            callableStatement.setString(15, request.getAddress()); //Adress in varchar2
            callableStatement.setString(16, request.getSex()); //sex in varchar2
            callableStatement.setDate(17, new java.sql.Date(birth.getTime())); //date_birth in date
            callableStatement.setInt(18, request.getRegion()); //Region in number
            callableStatement.setString(19, request.getFioKir2()); //FIO_kir2 in varchar2
            callableStatement.setString(20, request.getFioLat2()); //FIO_lat2 in varchar2
            callableStatement.setString(21, request.getIin2()); //IIN2 in varchar2
            callableStatement.setString(22, request.getResId2()); //RESID2 in varchar2
            callableStatement.setString(23, request.getAddress2()); //Address2 in varchar2
            callableStatement.setString(24, request.getSex2()); //sex2 in varchar2
            callableStatement.setDate(25, new java.sql.Date(birth2.getTime())); //date_birth2 in date
            callableStatement.setInt(26, request.getRegion2()); //Region2 in number
            callableStatement.setString(27, request.getPassportNum()); //Passport_num in varchar2
            callableStatement.setString(28, request.getPassportGive()); //Passport_give in varchar
            callableStatement.setDate(29, new java.sql.Date(passportDate.getTime())); //Passport_date in date
            callableStatement.setDate(30, new java.sql.Date(passportEnd.getTime())); //Passport_date_end in date
            callableStatement.setInt(31, request.getDocType()); //doc_type in number
            callableStatement.setString(32, request.getPassportNum2()); //Passport_num2 in varchar2
            callableStatement.setString(33, request.getPassportGive2()); //Passport_give2 in varchar
            callableStatement.setDate(34, new java.sql.Date(passportDate2.getTime())); //Passport_date2 in date
            callableStatement.setDate(35, new java.sql.Date(passportEnd2.getTime())); //Passport_date_end2 in date
            callableStatement.setString(36, request.getMobilePhone()); //mobil_phone in varchar2 default null
            callableStatement.setString(37, request.getPhone()); //phone in varchar2 default null
            callableStatement.setString(38, request.getEmail()); //email in varchar2 default null
            callableStatement.setString(39, request.getMobilePhone2()); //mobil_phone2 in varchar2 default null
            callableStatement.setString(40, request.getPhone2()); //phone2 in varchar2 default null
            callableStatement.setString(41, request.getEmail2()); //email2 in varchar2 default null

            callableStatement.registerOutParameter(1, java.sql.Types.VARCHAR);

            callableStatement.execute();

            response.setMessage(callableStatement.getString(1));
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
