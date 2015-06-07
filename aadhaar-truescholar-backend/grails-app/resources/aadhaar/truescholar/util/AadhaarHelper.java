package aadhaar.truescholar.util;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.HashMap;

import javax.net.ssl.HttpsURLConnection;

import com.aadhaarconnect.bridge.gateway.model.AuthResponse;
import com.aadhaarconnect.bridge.gateway.model.KycResponse;
import com.aadhaarconnect.bridge.gateway.model.OtpResponse;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class AadhaarHelper {

    private static final Gson gson = new GsonBuilder().disableHtmlEscaping().create();

    /*public static void main(String[] args) throws Exception {
        String aadhaarNumber = "27776506xxxx";
        String name = "Anantha Padmanabha";
        String otp = "948647";
        doAadhaarAuthUsingDemographics(aadhaarNumber, name);
        //doAadhaarOTP(aadhaarNumber);
        //doAadhaarAuthUsingOTP(aadhaarNumber, otp);
        //doAadhaarKYC(aadhaarNumber, otp);
    }*/

    public static boolean doAadhaarAuthUsingDemographics(String aadhaarNumber, String name) throws Exception{
        String url = "https://ac.khoslalabs.com/hackgate/hackathon/auth/raw";

        String jsonString = "{\n" +
                "\"aadhaar-id\": \"" + aadhaarNumber + "\",\n" +
                "\"location\": {\n" +
                "\"type\": \"pincode\",\n" +
                "\"pincode\": \"560067\"\n" +
                "},\n" +
                "\"modality\": \"demo\",\n" +
                "\"certificate-type\": \"preprod\",\n" +
                "\"demographics\": {\n" +
                "\"name\": {\n" +
                "\"matching-strategy\": \"exact\",\n" +
                "\"name-value\": \" " + name + "\"\n" +
                "}\n" +
                "}\n" +
                "}";


        String jsonResponse = sendPost(url,jsonString);
        AuthResponse authResponse = GsonSerializerUtil.unmarshal(jsonResponse, AuthResponse.class);
        System.out.println(authResponse.isSuccess());
        return authResponse.isSuccess();
    }

    public static HashMap doAadhaarOTP(String aadhaarNumber) throws Exception{
        HashMap responseMap = new HashMap();
        String url = "https://ac.khoslalabs.com/hackgate/hackathon/otp";

        String jsonString = "{\"aadhaar-id\":\"" + aadhaarNumber + " \",\n" +
                "\"location\":{\n" +
                "\"type\":\"pincode\",\n" +
                "\"pincode\":\"123456\"\n" +
                "},\n" +
                "\"channel\":\"SMS\"\n" +
                "}";


        String jsonResponse = sendPost(url,jsonString);
        OtpResponse otpResponse = GsonSerializerUtil.unmarshal(jsonResponse, OtpResponse.class);
        responseMap.put("APIStatus", otpResponse.isSuccess());

        return responseMap;
    }

    public static boolean doAadhaarAuthUsingOTP(String aadhaarNumber, String otp) throws Exception{
        String url = "https://ac.khoslalabs.com/hackgate/hackathon/auth/raw";

        String jsonString = "{\n" +
                "\"aadhaar-id\": \"" + aadhaarNumber + "\",\n" +
                "\"location\": {\n" +
                "\"type\": \"pincode\",\n" +
                "\"pincode\": \"560067\"\n" +
                "},\n" +
                "\"modality\": \"otp\",\n" +
                "\"certificate-type\": \"preprod\",\n" +
                "\"otp\": \"" + otp + "\"\n" +
                "}\n";


        String jsonResponse = sendPost(url,jsonString);
        AuthResponse authResponse = GsonSerializerUtil.unmarshal(jsonResponse, AuthResponse.class);
        System.out.println(authResponse.isSuccess());
        return authResponse.isSuccess();
    }

    public static HashMap doAadhaarKYC(String aadhaarNumber, String otp) throws Exception{
        HashMap responseMap = new HashMap();
        String url = "https://ac.khoslalabs.com/hackgate/hackathon/kyc/raw";

        String jsonString = "{\n" +
                "\"consent\": \"Y\",\n" +
                "\"auth-capture-request\": {\n" +
                "\"aadhaar-id\": \"" + aadhaarNumber + "\",\n" +
                "\"location\": {\n" +
                "\"type\": \"pincode\",\n" +
                "\"pincode\": \"560067\"\n" +
                "},\n" +
                "\"modality\": \"otp\",\n" +
                "\"certificate-type\": \"preprod\",\n" +
                "\"otp\": \"" + otp  + "\"\n" +
                "}\n" +
                "}";


        String jsonResponse = sendPost(url,jsonString);

        KycResponse kycResponse = GsonSerializerUtil.unmarshal(jsonResponse, KycResponse.class);

        responseMap.put("APIStatus", kycResponse.isSuccess());
        responseMap.put("APIStatusCode", kycResponse.getStatusCode());
        if(kycResponse.isSuccess()){
            responseMap.put("KYCPhoto", kycResponse.getKyc().getPhoto());
            responseMap.put("KYCName", kycResponse.getKyc().getPoi().getName());
            responseMap.put("KYCDob", kycResponse.getKyc().getPoi().getDob());
            responseMap.put("KYCGender", kycResponse.getKyc().getPoi().getGender());
            responseMap.put("KYCEmail", kycResponse.getKyc().getPoi().getEmail());
            responseMap.put("KYCPhone", kycResponse.getKyc().getPoi().getPhone());

            responseMap.put("KYCDistrict", kycResponse.getKyc().getPoa().getDist());
            responseMap.put("KYCState", kycResponse.getKyc().getPoa().getState());
            responseMap.put("KYCPincode", kycResponse.getKyc().getPoa().getPc());
        }

        return responseMap;
    }


    // HTTP POST request
    public static String sendPost(String url, String paramString) throws Exception {

        URL obj = new URL(url);
        HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();

        //add request header
        con.setRequestMethod("POST");
        con.setRequestProperty("Content-Type", "application/json");
        String urlParameters = paramString;

        // Send post request
        con.setDoOutput(true);
        DataOutputStream wr = new DataOutputStream(con.getOutputStream());
        wr.writeBytes(urlParameters);
        wr.flush();
        wr.close();

        int responseCode = con.getResponseCode();
        System.out.println("\nSending 'POST' request to URL : " + url);
        System.out.println("Post parameters : " + urlParameters);
        System.out.println("Response Code : " + responseCode);

        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        //print result
        System.out.println(response.toString());
        return response.toString();

    }

}
