package aadhaar.truescholar.backend

import javax.ws.rs.GET
import javax.ws.rs.Path
import javax.ws.rs.Produces
import javax.ws.rs.QueryParam
import aadhaar.truescholar.util.AadhaarHelper


@Path('/api/otp')
class OtpResource {

    @GET
    @Produces('text/plain')
    HashMap getOtpRepresentation(@QueryParam('aadhaarNumber') String aadhaarNumber) {
        return AadhaarHelper.doAadhaarOTP(aadhaarNumber);
    }
}
