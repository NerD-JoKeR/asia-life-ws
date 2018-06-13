package kz.asialife.ws.endpoint;

import kz.asialife.ws.AuthorizationRequest;
import kz.asialife.ws.AuthorizationResponse;
import kz.asialife.ws.components.authorization.AuthorizationComponent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

@Endpoint
public class WSEndpoint {
    private static final String NAMESPACE_URI = "http://asialife.kz/ws";

    @Autowired
    private AuthorizationComponent authorizationComponent;


    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "authorizationRequest")
    @ResponsePayload
    public AuthorizationResponse authorizationRequest(@RequestPayload AuthorizationRequest request) {
        return authorizationComponent.authorize(request);
    }
}
