package aadhaar.truescholar.backend

import aadhaar.truescholar.util.AadhaarHelper
import aadhaar.truescholar.util.Age
import aadhaar.truescholar.util.AgeCalculator
import com.aadhaarconnect.bridge.gateway.model.GenderType
import java.text.SimpleDateFormat

class ScholarshipController {
    private static String digitsRegex = "[0-9]+";

    def index() {
        render(view: 'index', model: null)
    }

    private static String validateAadhaarNumber(String aadhaarNumber) {
        if(aadhaarNumber == null || aadhaarNumber.isEmpty()) { return "Pls fill Aadhaar number" ;}
        if(aadhaarNumber.length() != 12) { return "Aadhaar number must be 12 digits. Pls check and try again"; }
        if(!aadhaarNumber.matches(digitsRegex)) { return "Aadhaar must contain only numbers. Pls remove any other characters and try again"; }
        return null;
    }

    private static String validateOTP(String otp) {
        if(otp == null || otp.isEmpty()) { return "Pls fill OTP" ;}
        if(otp.length() != 6) { return "OTP number must be 6 digits. Pls check and try again"; }
        if(!otp.matches(digitsRegex)) { return "OTP must contain only numbers. Pls remove any other characters and try again"; }
        return null;
    }


    def otp() {
        //render "Aadhaar Number: " + params.aadhaarNumber
        String aadhaarNumber = params.aadhaarNumber
        String error = validateAadhaarNumber(aadhaarNumber)
        if(error!=null) {render error; return;}

        HashMap responseMap = AadhaarHelper.doAadhaarOTP(aadhaarNumber)
        if(responseMap.get("APIStatus") == true){
            render "OTP would be sent to your mobile soon via Aadhaar system"
            return
        } else {
            render "There was a server error in sending OTP - Pls try again"
            return
        }

    }

    def kyc() {
        //render "Aadhaar Number: " + params.aadhaarNumber + " OTP : " + params.otp
        String aadhaarNumber = params.aadhaarNumber
        String otp = params.otp

        String error = validateAadhaarNumber(aadhaarNumber)
        if(error!=null) {render error; return;}
        error = validateOTP(otp)
        if(error!=null) {render error; return;}

        HashMap responseMap = AadhaarHelper.doAadhaarKYC(aadhaarNumber, otp)
        if(responseMap.get("APIStatus") == true){
            String name = responseMap.get("KYCName")
            GenderType gender =  responseMap.get("KYCGender")
            String dobString =  responseMap.get("KYCDob")

            if(gender != GenderType.F){
                render "Dear " + name + ", Only females are eligible to apply for this scholarship. Based on KYC check via Aadhaar, you are ineligible for this scholarship"
                return
            }

            if(dobString != null && !dobString.isEmpty()){
                SimpleDateFormat sdf = new SimpleDateFormat("dd-mm-YYYY");
                Date dob = sdf.parse(dobString);

                Age age = AgeCalculator.calculateAge(dob);
                if(age.getYears() < 12 || age.getYears() > 30){
                    render "Dear " + name + ", Only females between age 12 to 30 are eligible to apply for this scholarship. Based on KYC check via Aadhaar, you are ineligible for this scholarship " +
                            " as your Date of Birth is " + dobString + " (Age : " + age.getYears() + " )"
                    return
                }
            }

            // Mock system to differentiate students with high scores [eligible for scholarship] and low scores [not eligible for scholarship]
            if(aadhaarNumber[11].toInteger() % 2 == 0){
                render "Dear " + name + ", Based on KYC check via Aadhaar, you have passed the minimum criteria of gender and age. " +
                        " But as per TrueScholar database, your score is below the criteria of scholarship. Hence you are ineligible"
                return
            }

            render "Dear " + name + ", Congrats. Based on KYC check, you are eligibile for scholarship. Scholarship amount will be credited to your bank account linked to Aadhaar from coming month"
            return
        } else {
            if(responseMap.get("APIStatusCode").equals("K-100")){
                render "KYC Authentication failed - Pls ensure you are entering OTP correctly and have not used this OTP earlier"
                return
            }

            render "There was a server error in doing KYC - Pls try again"
            return
        }

    }




}
