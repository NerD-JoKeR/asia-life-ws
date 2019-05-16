package kz.ffinlife.ws.components.cursor;

import ffinlife.ws.*;
import kz.ffinlife.ws.components.common.CommonComponent;
import oracle.jdbc.driver.OracleDriver;
import oracle.jdbc.driver.OracleTypes;
import org.springframework.stereotype.Component;
import java.sql.*;


@Component
public class CabCountryComponent extends CommonComponent {

    public CabCountryResponse country(CabCountryRequest request){

        CabCountryResponse commonResponse = checkSession(request, new CabCountryResponse());
        if(commonResponse != null){
            return commonResponse;
        }

        CabCountryResponse response = new CabCountryResponse();

        Connection conn = null;
        CallableStatement callableStatement = null;

        try{
            DriverManager.registerDriver(new OracleDriver());

            String url = "jdbc:oracle:thin:@10.0.0.10:1526:bsolife";

            conn = DriverManager.getConnection(url, "mlm", "mlm");

            String sql = "{ ? = call webservice.get_country}";

            callableStatement = conn.prepareCall(sql);

            callableStatement.registerOutParameter (1, OracleTypes.CURSOR);

            callableStatement.execute ();

            ResultSet rs = (ResultSet)callableStatement.getObject (1);
            response.setSuccess(true);
            while (rs.next()) {
                String ID = rs.getString("VALUE");
                String TEXT = rs.getString("TEXT");
                Document6 doc = new Document6();
                doc.setCodeOfCountry(ID);
                doc.setNameOfCountry(TEXT);
                response.getCountries().add(doc);
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
