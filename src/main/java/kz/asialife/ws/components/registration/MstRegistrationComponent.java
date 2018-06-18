package kz.asialife.ws.components.registration;


import kz.asialife.ws.RegMstRequest;
import kz.asialife.ws.RegMstResponse;
import oracle.jdbc.driver.OracleDriver;
import org.springframework.stereotype.Component;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class MstRegistrationComponent {

    public RegMstResponse mst(RegMstRequest request){
        DateFormat sourceFormat = new SimpleDateFormat("dd.MM.yyyy");

        RegMstResponse response = new RegMstResponse();
        try {

            Date begin = sourceFormat.parse(request.getDateStart());
            Date end = sourceFormat.parse(request.getDateEnd());
            Date birth = sourceFormat.parse(request.getDateBirth());
            Date birth2 = sourceFormat.parse(request.getDateBirth2());
            Date passportDate = sourceFormat.parse(request.getPassportDate());
            Date passportDate2 = sourceFormat.parse(request.getPassportDate2());
            Date passportEnd = sourceFormat.parse(request.getPassportDateEnd());
            Date passportEnd2 = sourceFormat.parse(request.getPassportDateEnd2());

            // TODO  dont forget check this code !! and after that check connection with oracle.
            // TODO  check the response mst on LIC in oralce
            // TODO  ask about Id Session in head , how and which response i need to check and give back
            DriverManager.registerDriver(new OracleDriver());
            String url = "jdbc:oracle:thin:@10.0.0.10:1526:bsolife";
            Connection conn = DriverManager.getConnection(url, "mlm", "mlm");
            String sql = "{ ? = call WEBSERVICE.reg_mst(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) }";
            CallableStatement callableStatement = conn.prepareCall(sql);
            callableStatement.setInt(2, request.getCountry1()); //Country1 in number
            callableStatement.setInt(3, request.getRprogramm()); //rprogramm in number
            callableStatement.setInt(4, request.getRprogSrok()); //rprog_srok in number
            callableStatement.setInt(5, request.getProgMaxDays()); //prog_max_days in number
            callableStatement.setDate(6, new java.sql.Date(begin.getTime())); //Date_Start in date
            callableStatement.setDate(7, new java.sql.Date(end.getTime())); //Date_End in date
            callableStatement.setInt(8, request.getCurRate()); //CurRate in number
            callableStatement.setString(9, request.getFioKir()); //FIO_kir in varchar2
            callableStatement.setString(10, request.getFioLat()); //FIO_lat in varchar2
            callableStatement.setString(11, request.getInn()); //IIN in varchar2
            callableStatement.setString(12, request.getResId()); //RESID in varchar2
            callableStatement.setString(13, request.getAddress()); //Adress in varchar2
            callableStatement.setString(14, request.getSex()); //sex in varchar2
            callableStatement.setDate(15, new java.sql.Date(birth.getTime())); //date_birth in date
            callableStatement.setInt(16, request.getRegion()); //Region in number
            callableStatement.setString(17, request.getFioKir2()); //FIO_kir2 in varchar2
            callableStatement.setString(18, request.getFioLat2()); //FIO_lat2 in varchar2
            callableStatement.setString(19, request.getIin2()); //IIN2 in varchar2
            callableStatement.setString(20, request.getResId2()); //RESID2 in varchar2
            callableStatement.setString(21, request.getAddress2()); //Address2 in varchar2
            callableStatement.setString(22, request.getSex2()); //sex2 in varchar2
            callableStatement.setDate(23, new java.sql.Date(birth2.getTime())); //date_birth2 in date
            callableStatement.setInt(24, request.getRegion2()); //Region2 in number
            callableStatement.setString(25, request.getPassportNum()); //Passport_num in varchar2
            callableStatement.setString(26, request.getPassportGive()); //Passport_give in varchar
            callableStatement.setDate(27, new java.sql.Date(passportDate.getTime())); //Passport_date in date
            callableStatement.setDate(28, new java.sql.Date(passportEnd.getTime())); //Passport_date_end in date
            callableStatement.setInt(29, request.getDocType()); //doc_type in number
            callableStatement.setString(30, request.getPassportNum2()); //Passport_num2 in varchar2
            callableStatement.setString(31, request.getPassportGive2()); //Passport_give2 in varchar
            callableStatement.setDate(32, new java.sql.Date(passportDate2.getTime())); //Passport_date2 in date
            callableStatement.setDate(33, new java.sql.Date(passportEnd2.getTime())); //Passport_date_end2 in date
            callableStatement.setString(34, request.getMobilePhone()); //mobil_phone in varchar2 default null
            callableStatement.setString(35, request.getPhone()); //phone in varchar2 default null
            callableStatement.setString(36, request.getEmail()); //email in varchar2 default null
            callableStatement.setString(37, request.getMobilePhone2()); //mobil_phone2 in varchar2 default null
            callableStatement.setString(38, request.getPhone2()); //phone2 in varchar2 default null
            callableStatement.setString(39, request.getEmail2()); //email2 in varchar2 default null

            callableStatement.registerOutParameter(1, java.sql.Types.VARCHAR);

            callableStatement.execute();
            //this is the main line
            response.setMessage(callableStatement.getString(1));

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return response;
    }
}
