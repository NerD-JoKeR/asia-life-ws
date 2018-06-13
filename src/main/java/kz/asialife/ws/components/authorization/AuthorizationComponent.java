package kz.asialife.ws.components.authorization;

import kz.asialife.ws.AuthorizationRequest;
import kz.asialife.ws.AuthorizationResponse;
import org.springframework.stereotype.Component;

@Component
public class AuthorizationComponent {
    public AuthorizationResponse authorize(AuthorizationRequest request){

        //TODO implement your logic


        //these are fake data
        AuthorizationResponse response = new AuthorizationResponse();
        response.setIin(request.getIin());
        response.setPassword(request.getPassword());
        response.setFio("FIO");
        response.setPhone("+77777657655");
        response.setEmail("mail@mail.ru");
        response.setResult("CUSTOM RESULT");

        return response;
    }
}
