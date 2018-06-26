package kz.asialife.ws.endpoint;

import kz.asialife.ws.*;
import kz.asialife.ws.AuthorizationRequest;
import kz.asialife.ws.AuthorizationResponse;
import kz.asialife.ws.ChangePasswordRequest;
import kz.asialife.ws.ChangePasswordResponse;
import kz.asialife.ws.CursorRequest;
import kz.asialife.ws.CursorResponse;
import kz.asialife.ws.KazinaRequest;
import kz.asialife.ws.KazinaResponse;
import kz.asialife.ws.MstRequest;
import kz.asialife.ws.MstResponse;
import kz.asialife.ws.OsrnsRequest;
import kz.asialife.ws.OsrnsResponse;
import kz.asialife.ws.RegMstRequest;
import kz.asialife.ws.RegMstResponse;
import kz.asialife.ws.components.authorization.AuthorizationComponent;
import kz.asialife.ws.components.calculator.CalculatorKazinaComponent;
import kz.asialife.ws.components.calculator.CalculatorMstComponent;
import kz.asialife.ws.components.calculator.CalculatorOSRNSComponent;
import kz.asialife.ws.components.changePassword.ChangePasswordComponent;
import kz.asialife.ws.components.cursor.CursorComponent;
import kz.asialife.ws.components.registration.MstRegistrationComponent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import org.springframework.ws.soap.SoapHeaderElement;

@Endpoint
public class WSEndpoint {
    private static final String NAMESPACE_URI = "http://asialife.kz/ws";

    @Autowired
    private AuthorizationComponent authorizationComponent;


    @Autowired
    private ChangePasswordComponent changePasswordComponent;

    @Autowired
    private CalculatorOSRNSComponent osrnsComponent;

    @Autowired
    private CalculatorMstComponent mstComponent;

    @Autowired
    private MstRegistrationComponent mstRegistrationComponent;

    @Autowired
    private CalculatorKazinaComponent calculatorKazinaComponent;

    @Autowired
    private CursorComponent cursorComponent;

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "authorizationRequest")
    @ResponsePayload
    public AuthorizationResponse authorizationRequest(@RequestPayload AuthorizationRequest request) {
        return authorizationComponent.authorize(request);
    }


    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "changePasswordRequest")
    @ResponsePayload
    public ChangePasswordResponse changePasswordRequest(@RequestPayload ChangePasswordRequest request) {
        return changePasswordComponent.changePassword(request);
    }


    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "osrnsRequest")
    @ResponsePayload
    public OsrnsResponse osrnsRequest(@RequestPayload OsrnsRequest request) {
        return osrnsComponent.osrns(request);
    }


    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "mstRequest")
    @ResponsePayload
    public MstResponse mstRequest(@RequestPayload MstRequest request) {
        return mstComponent.mst(request);
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "kazinaRequest")
    @ResponsePayload
    public KazinaResponse kazinaRequest(@RequestPayload KazinaRequest request) {
        return calculatorKazinaComponent.kazina(request);
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "regMstRequest")
    @ResponsePayload
    public RegMstResponse regMstRequest(@RequestPayload RegMstRequest request) {
        return mstRegistrationComponent.mst(request);
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "cursorRequest")
    @ResponsePayload
    public CursorResponse cursorRequest(@RequestPayload CursorRequest request) {
        return cursorComponent.cursor(request);
    }

}
