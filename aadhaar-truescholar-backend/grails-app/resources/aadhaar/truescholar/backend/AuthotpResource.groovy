package aadhaar.truescholar.backend

import javax.ws.rs.GET
import javax.ws.rs.Path
import javax.ws.rs.Produces
import javax.ws.rs.QueryParam
import aadhaar.truescholar.util.AadhaarHelper

@Path('/api/authotp')
class AuthotpResource {

    @GET
    @Produces('text/plain')
    String getAuthotpRepresentation(@QueryParam('aadhaarNumber') String aadhaarNumber, @QueryParam('otp') String otp) {
        return AadhaarHelper.doAadhaarAuthUsingOTP(aadhaarNumber, otp)
    }
}
