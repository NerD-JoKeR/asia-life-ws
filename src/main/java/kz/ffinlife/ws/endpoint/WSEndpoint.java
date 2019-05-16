package kz.ffinlife.ws.endpoint;

import ffinlife.ws.*;
import ffinlife.ws.AuthorizationRequest;
import ffinlife.ws.AuthorizationResponse;
import ffinlife.ws.ChangePasswordRequest;
import ffinlife.ws.ChangePasswordResponse;
import ffinlife.ws.CursorRequest;
import ffinlife.ws.CursorResponse;
import kz.ffinlife.ws.components.authorization.AuthorizationCabAgentComponent;
import kz.ffinlife.ws.components.authorization.AuthorizationComponent;
import kz.ffinlife.ws.components.authorization.AuthorizationWSComponent;
import kz.ffinlife.ws.components.calculator.*;
import kz.ffinlife.ws.components.changePassword.ChangePassCabAgentComponent;
import kz.ffinlife.ws.components.changePassword.ChangePasswordComponent;
import kz.ffinlife.ws.components.changePassword.RecoveryPassCabAgentComponent;
import kz.ffinlife.ws.components.cursor.*;
import kz.ffinlife.ws.components.methods.ProofPaymentComponent;
import kz.ffinlife.ws.components.registration.RegistrationCabAgentComponent;
import kz.ffinlife.ws.components.registration.RegistrationClientComponent;
import kz.ffinlife.ws.components.registration.RegistrationFreedomTravelComponent;
import kz.ffinlife.ws.components.registration.VerifyClientComponent;
import kz.ffinlife.ws.components.workAct.CabDocumentsComponent;
import kz.ffinlife.ws.components.workAct.CabPayRollComponent;
import kz.ffinlife.ws.components.workAct.CabRepWorkActComponent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;




@Endpoint
public class WSEndpoint {
    private static final String NAMESPACE_URI = "http://ffinlife/ws";

    @Autowired
    private AuthorizationWSComponent authorizationWSComponent;


    @Autowired
    private AuthorizationComponent authorizationComponent;


    @Autowired
    private AuthorizationCabAgentComponent authorizationCabAgentComponent;


    @Autowired
    private ProofPaymentComponent proofPaymentComponent;


    @Autowired
    private CabCountryComponent cabCountryComponent;

    @Autowired
    private ChangePasswordComponent changePasswordComponent;


    @Autowired
    private ChangePassCabAgentComponent changePassCabAgentComponent;


    @Autowired
    private RecoveryPassCabAgentComponent recoveryPassCabAgentComponent;


    @Autowired
    private RegistrationFreedomTravelComponent registrationFreedomTravelComponent;


    @Autowired
    private RegistrationCabAgentComponent registrationCabAgentComponent;


    @Autowired
    private RegistrationClientComponent registrationClientComponent;


    @Autowired
    private VerifyClientComponent verifyClientComponent;


    @Autowired
    private CabRepWorkActComponent cabRepWorkActComponent;


    @Autowired
    private CabPayRollComponent cabPayRollComponent;


    @Autowired
    private CabDocumentsComponent cabDocumentsComponent;


    @Autowired
    private CalculatorOsrnsComponent osrnsComponent;


    @Autowired
    private CalculatorFreedomTravelComponent calculatorFreedomTravelComponent ;


    @Autowired
    private CalculatorFreedomFutureComponent calculatorFreedomFutureComponent;


    @Autowired
    private CalculatorFreedomProtectComponent calculatorFreedomProtectComponent;


    @Autowired
    private CalculatorFreedomKidsComponent calculatorFreedomKidsComponent;


    @Autowired
    private CalculatorCabRewardComponent calculatorCabRewardComponent;


    @Autowired
    private CursorComponent cursorComponent;


    @Autowired
    private CursorWorkActTableComponent cursorWorkActTableComponent;


    @Autowired
    private CursorAgentLevelComponent cursorAgentLevelComponent;


    @Autowired
    private CursorAgentConditionComponent cursorAgentConditionComponent;


    @Autowired
    private CursorAgentTreeComponent cursorAgentTreeComponent;


    @Autowired
    private CursorAgentIDComponent cursorAgentIDComponent;


    @Autowired
    private CursorProductComponent cursorProductComponent;


    @Autowired
    private CursorDepartmentComponent cursorDepartmentComponent;


    @Autowired
    private CursorRegionComponent cursorRegionComponent;


    @Autowired
    private CursorSubDepComponent cursorSubDepComponent;

    @Autowired
    private CursorAutoPayComponent cursorAutoPayComponent;





    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "authorizationWSRequest")
    @ResponsePayload
    public AuthorizationWSResponse authorizationWSRequest(@RequestPayload AuthorizationWSRequest request) {
        return authorizationWSComponent.authorizeWS(request);
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "authorizationRequest")
    @ResponsePayload
    public AuthorizationResponse authorizationRequest(@RequestPayload AuthorizationRequest request) {
        return authorizationComponent.authorize(request);
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "authorizationCabAgentRequest")
    @ResponsePayload
    public AuthorizationCabAgentResponse authorizationCabAgentRequest(@RequestPayload AuthorizationCabAgentRequest request) {
        return authorizationCabAgentComponent.authorizeCabAgent(request);
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "proofPaymentRequest")
    @ResponsePayload
    public ProofPaymentResponse proofPaymentRequest(@RequestPayload ProofPaymentRequest request) {
        return proofPaymentComponent.payment(request);
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "cabCountryRequest")
    @ResponsePayload
    public CabCountryResponse cabCountryRequest(@RequestPayload CabCountryRequest request) {
        return cabCountryComponent.country(request);
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "registrationCabAgentRequest")
    @ResponsePayload
    public RegistrationCabAgentResponse registrationCabRequest(@RequestPayload RegistrationCabAgentRequest request) {
        return registrationCabAgentComponent.registrationCabAgent(request);
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "registrationClientRequest")
    @ResponsePayload
    public RegistrationClientResponse registrationClientRequest(@RequestPayload RegistrationClientRequest request) {
        return registrationClientComponent.regCln(request);
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "clientVerifyRequest")
    @ResponsePayload
    public ClientVerifyResponse clientVerifyRequest(@RequestPayload ClientVerifyRequest request) {
        return verifyClientComponent.verKln(request);
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "cabPayRollRequest")
    @ResponsePayload
    public CabPayRollResponse cabPayRollRequest(@RequestPayload CabPayRollRequest request) {
        return cabPayRollComponent.cabPayRoll(request);
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "cabMTOMRequest")
    @ResponsePayload
    public CabMTOMResponse getMTOMRequest(@RequestPayload CabMTOMRequest request) {
        return cabDocumentsComponent.mtom(request);
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "repWorkActCabRequest")
    @ResponsePayload
    public RepWorkActCabResponse repWorkActCabRequest(@RequestPayload RepWorkActCabRequest request) {
        return cabRepWorkActComponent.repWorkAct(request);
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "changePasswordRequest")
    @ResponsePayload
    public ChangePasswordResponse changePasswordRequest(@RequestPayload ChangePasswordRequest request) {
        return changePasswordComponent.changePassword(request);
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "changePassCabAgentRequest")
    @ResponsePayload
    public ChangePassCabAgentResponse changePassCabResponse(@RequestPayload ChangePassCabAgentRequest request) {
        return changePassCabAgentComponent.changePassCab(request);
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "recoveryPassCabAgentRequest")
    @ResponsePayload
    public RecoveryPassCabAgentResponse recoveryPassCabResponse(@RequestPayload RecoveryPassCabAgentRequest request) {
        return recoveryPassCabAgentComponent.recoveryPassCabAgent(request);
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "calculatorOsrnsRequest")
    @ResponsePayload
    public CalculatorOsrnsResponse osrnsRequest(@RequestPayload CalculatorOsrnsRequest request) {
        return osrnsComponent.osrns(request);
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "calculatorFreedomTravelRequest")
    @ResponsePayload
    public CalculatorFreedomTravelResponse freedomTravelRequest(@RequestPayload CalculatorFreedomTravelRequest request) {
        return calculatorFreedomTravelComponent.freedomTravel(request);
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "calculatorFreedomKidsRequest")
    @ResponsePayload
    public CalculatorFreedomKidsResponse freedomKidsRequest(@RequestPayload CalculatorFreedomKidsRequest request) {
        return calculatorFreedomKidsComponent.freedomKids(request);
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "calculatorFreedomFutureRequest")
    @ResponsePayload
    public CalculatorFreedomFutureResponse freedomFutureRequest(@RequestPayload CalculatorFreedomFutureRequest request) {
        return calculatorFreedomFutureComponent.freedomFuture(request);
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "calculatorFreedomProtectRequest")
    @ResponsePayload
    public CalculatorFreedomProtectResponse freedomProtectRequest(@RequestPayload CalculatorFreedomProtectRequest request) {
        return calculatorFreedomProtectComponent.freedomProtect(request);
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "calculatorCabRewardRequest")
    @ResponsePayload
    public CalculatorCabRewardResponse calculatorCabRewardRequest(@RequestPayload CalculatorCabRewardRequest request) {
        return calculatorCabRewardComponent.cabReward(request);
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "registrationFreedomTravelRequest")
    @ResponsePayload
    public RegistrationFreedomTravelResponse regFreedomTravelRequest(@RequestPayload RegistrationFreedomTravelRequest request) {
        return registrationFreedomTravelComponent.regFreedomTravel(request);
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "cursorRequest")
    @ResponsePayload
    public CursorResponse cursorRequest(@RequestPayload CursorRequest request) {
        return cursorComponent.cursor(request);
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "cursorWorkActTableRequest")
    @ResponsePayload
    public CursorWorkActTableResponse cursorWorkActTableRequest(@RequestPayload CursorWorkActTableRequest request) {
        return cursorWorkActTableComponent.table(request);
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "cursorAgentIDRequest")
    @ResponsePayload
    public CursorAgentIDResponse cursorAgentIDRequest(@RequestPayload CursorAgentIDRequest request) {
        return cursorAgentIDComponent.cursorAgentID(request);
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "cursorAgentTreeRequest")
    @ResponsePayload
    public CursorAgentTreeResponse cursorAgentTreeRequest(@RequestPayload CursorAgentTreeRequest request) {
        return cursorAgentTreeComponent.cursorAgentTree(request);
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "cursorProductsRequest")
    @ResponsePayload
    public CursorProductsResponse cursorProductsRequest(@RequestPayload CursorProductsRequest request) {
        return cursorProductComponent.cursorProduct(request);
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "cursorDepartmentsRequest")
    @ResponsePayload
    public CursorDepartmentsResponse cursorDepartmentsRequest(@RequestPayload CursorDepartmentsRequest request) {
        return cursorDepartmentComponent.cursorDepartment(request);
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "cursorRegionsRequest")
    @ResponsePayload
    public CursorRegionsResponse cursorRegionsRequest(@RequestPayload CursorRegionsRequest request) {
        return cursorRegionComponent.cursorRegions(request);
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "cursorSubDepRequest")
    @ResponsePayload
    public CursorSubDepResponse cursorSubDepRequest(@RequestPayload CursorSubDepRequest request) {
        return cursorSubDepComponent.cursorSubDep(request);
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "cursorAgentLevelRequest")
    @ResponsePayload
    public CursorAgentLevelResponse cursorAgentLevelRequest(@RequestPayload CursorAgentLevelRequest request) {
        return cursorAgentLevelComponent.cursorLevel(request);
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "cursorAgentConditionRequest")
    @ResponsePayload
    public CursorAgentConditionResponse cursorAgentConditionRequest(@RequestPayload CursorAgentConditionRequest request) {
        return cursorAgentConditionComponent.cursorCondition(request);
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "cursorAutoPayRequest")
    @ResponsePayload
    public CursorAutoPayResponse cursorAutoPayRequest(@RequestPayload CursorAutoPayRequest request) {
        return cursorAutoPayComponent.cursorAuto(request);
    }
}
