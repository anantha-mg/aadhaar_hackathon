package aadhaar.truescholar.backend

import javax.ws.rs.GET
import javax.ws.rs.Path
import javax.ws.rs.Produces
import javax.ws.rs.QueryParam
import aadhaar.truescholar.util.AadhaarHelper


@Path('/api/authdemo')
class AuthdemoResource {

    @GET
    @Produces('text/plain')
    String getAuthdemoRepresentation(@QueryParam('aadhaarNumber') String aadhaarNumber, @QueryParam('name') String name) {

        return AadhaarHelper.doAadhaarAuthUsingDemographics(aadhaarNumber, name);
    }
}
