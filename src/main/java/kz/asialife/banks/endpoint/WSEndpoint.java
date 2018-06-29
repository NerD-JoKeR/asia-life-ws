package kz.asialife.banks.endpoint;

import kz.asialife.banks.AuthorizationWSRequest;
import kz.asialife.banks.AuthorizationWSResponse;
import kz.asialife.banks.component.authorization.AuthorizationWSComponent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

@Endpoint
public class WSEndpoint {
    private static final String NAMESPACE_URI = "http://asialife.kz/banks";



    @Autowired
    private AuthorizationWSComponent authorizationWSComponent;



    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "authorizationWSRequest")
    @ResponsePayload
    public AuthorizationWSResponse authorizationWSRequest(@RequestPayload AuthorizationWSRequest request) {
        return authorizationWSComponent.authorizeWS(request);
    }


}
