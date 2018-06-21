package kz.asialife.ws.components.cursor;


import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import oracle.jdbc.driver.OracleCallableStatement;
import oracle.jdbc.driver.OracleDriver;
import oracle.jdbc.driver.OracleTypes;


public class CursorComponent {

    public static void main ( String[] args )
    {

        try
        {
            DriverManager.registerDriver(new OracleDriver());
            String url = "jdbc:oracle:thin:@10.0.0.10:1526:bsolife";
            Connection conn = DriverManager.getConnection(url, "mlm", "mlm");
            String sql = "{ ? = call mlm.WEBSERVICE.kab_kln_pass(?,?,?) }";
            CallableStatement callableStatement = conn.prepareCall(sql);
            OracleCallableStatement ocst;
            ResultSet rs;
            // TODO change cursor calling method
            callableStatement = conn.prepareCall
                    ( "BEGIN generic_ref_cursor.get_ref_cursor ( ?, ? ); END;" );
            callableStatement.setString ( 1, "SELECT sal FROM emp" );
            callableStatement.registerOutParameter ( 2, OracleTypes.CURSOR );

            callableStatement.execute ( );

            ocst = ( OracleCallableStatement ) callableStatement;

            rs = ocst.getCursor ( 2 );

            while ( rs.next
                    ( ) ) { System.out.println ( rs.getInt ( 1 ) ); }

            /* Новый запрос ... */
            callableStatement.setString ( 1, "SELECT dname, loc FROM dept" );
            callableStatement.execute       ( );
            rs = ocst.getCursor ( 2 );
            while ( rs.next ( ) )
            { System.out.println ( rs.getString ( 1 ) + rs.getString ( 2 ) ); }

            /* ... и так далее, запрос за запросом */
            callableStatement.close ( );
        }
        catch ( Exception e ){
            System.out.println ( e );
        }
    }
}

