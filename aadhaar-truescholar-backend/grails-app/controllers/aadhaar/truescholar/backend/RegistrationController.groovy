package aadhaar.truescholar.backend

import aadhaar.truescholar.util.AadhaarHelper

class RegistrationController {
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

    private static String validateName(String name) {
        if(name == null || name.isEmpty()) { return "Pls fill your name" ;}
        if(name.matches(".*\\d+.*")) { return "Name must not contain any numbers. Pls remove such characters and try again"; }
        return null;
    }

    def auth() {
        //render "Aadhaar Number: " + params.aadhaarNumber
        String aadhaarNumber = params.aadhaarNumber
        String name = params.name
        String error = validateAadhaarNumber(aadhaarNumber)
        if(error!=null) {render error; return;}

        error = validateName(name)
        if(error!=null) {render error; return;}

       boolean  status= AadhaarHelper.doAadhaarAuthUsingDemographics(aadhaarNumber, name)
        if(status){
            render "Congrats. Details matched with Aadhaar system. You have successfully registered for exam online. Your Aadhaar number is your roll number"
            return
        } else {
            /*render "There was a server error in doing Auth - Pls try again"
            return*/

            render "Details entered did not match with Aadhaar system - Pls double check the details entered and ensure you enter same details thats present on your Aadhaar card"
            return

        }

    }
}
