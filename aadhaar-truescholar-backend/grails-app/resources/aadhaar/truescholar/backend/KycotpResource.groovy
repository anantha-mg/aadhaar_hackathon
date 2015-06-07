package aadhaar.truescholar.backend

import javax.ws.rs.GET
import javax.ws.rs.Path
import javax.ws.rs.Produces
import javax.ws.rs.QueryParam
import aadhaar.truescholar.util.AadhaarHelper

@Path('/api/kycotp')
class KycotpResource {

    @GET
    @Produces('text/plain')
    HashMap getKycotpRepresentation(@QueryParam('aadhaarNumber') String aadhaarNumber, @QueryParam('otp') String otp) {
        return AadhaarHelper.doAadhaarKYC(aadhaarNumber, otp)
    }
}
