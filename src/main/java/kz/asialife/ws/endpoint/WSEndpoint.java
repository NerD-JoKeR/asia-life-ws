package kz.asialife.ws.endpoint;

import kz.asialife.ws.*;
import kz.asialife.ws.AuthorizationRequest;
import kz.asialife.ws.AuthorizationResponse;
import kz.asialife.ws.ChangePasswordRequest;
import kz.asialife.ws.ChangePasswordResponse;
import kz.asialife.ws.OsrnsRequest;
import kz.asialife.ws.OsrnsResponse;
import kz.asialife.ws.components.authorization.AuthorizationComponent;
import kz.asialife.ws.components.calculator.CalculatorOSRNSComponent;
import kz.asialife.ws.components.changePassword.ChangePasswordComponent;
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

    @Autowired
    private CalculatorOSRNSComponent osrnsComponent;

    @Autowired
    private ChangePasswordComponent changePasswordComponent;


    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "authorizationRequest")
    @ResponsePayload
    public AuthorizationResponse authorizationRequest(@RequestPayload AuthorizationRequest request) {
        return authorizationComponent.authorize(request);
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "osrnsRequest")
    @ResponsePayload
    public OsrnsResponse osrnsRequest(@RequestPayload OsrnsRequest request) {
        return osrnsComponent.osrns(request);
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "changePasswordRequest")
    @ResponsePayload
    public ChangePasswordResponse changePasswordRequest(@RequestPayload ChangePasswordRequest request) {
        return changePasswordComponent.changePassword(request);
    }
}
