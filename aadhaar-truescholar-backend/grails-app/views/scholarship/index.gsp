<!doctype html>
<%--
  Created by IntelliJ IDEA.
  User: anantha
  Date: 6/6/15
  Time: 8:06 PM
--%>

<html><head>


    <meta http-equiv="Content-Language" content="en-us">
    <title>'BETI PADHAO' Special Scholarship</title>

    <script src="http://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>

</head>
<body bgcolor="#FFFFFF" leftmargin="0" topmargin="0">
<table cellspacing="0" cellpadding="0" width="101%" border="0">
    <tbody>
    <tr>
        <td valign="top" align="left" width="100%" colspan="2">
            <div align="right">
                <table cellspacing="0" cellpadding="0" width="100%" border="0" style="border-collapse: collapse" bordercolor="#111111" height="111">
                    <tbody>
                    <tr>
                        <td width="100%" height="97" background="http://wcd.nic.in/NewHeader/header-bg.jpg" align="center">
                            <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                <tbody><tr>
                                    <td width="10%" align="center" valign="top" style="margin-top:5px">
                                        <img src="http://wcd.nic.in/NewHeader/wcd-logo.png" alt="" width="103" height="68"></td>
                                    <td width="38%" align="left" valign="top"><h1 class="logo">
                                        <font size="4">Ministry of Women &amp; Child Development</font><font size="3"><br>
                                        </font>
                                        <span><font size="3">Government of India</font></span></h1></td>
                                    <td width="7%" align="left" valign="top">
                                        <img src="http://wcd.nic.in/NewHeader/emblem.png" alt="" width="47" height="81"></td>
                                    <td width="32%" align="right" class="valignBtm">
                                        <!--<img src="NewHeader/header-pic1.png" alt="" width="336" height="91">--></td>
                                    <td width="13%" align="center" class="valignMid">
                                        <p align="left">
                                            <img border="0" src="http://wcd.nic.in/bbbp.png" width="88" height="91"></p></td>
                                </tr>




                                </tbody></table>









                        </td>
                    </tr>

                    </tbody></table>
            </div>
        </td></tr>

    <tr bgcolor="orange"><td>
        <div style="width: 100%; height: 45px ;margin-top: 5px; margin-left: 0px; text-align: center">

                        <b> PRADHAN MANTRI "BETI PADHAO" SPECIAL SCHOLARSHIP </b> <br>
            Disclaimer : This is a demo site for Aadhaar Hackathon and does not belong to Govt. of India
        </div>
    </td></tr>

    </tbody></table>


<style>
ul {
    width: 70%;
    margin: auto;
}
form {
    width: 350px;
    margin: 0 auto;
}

#notice {
    width: 350px;
    margin: 0 auto;
    margin-top: 50px;
}


</style>


<div style="margin-top: 10px;">
<ul>
    <li> KYC of the student is done using Aadhaar using OTP sent to registered mobile in Aadhaar system </li>
    <li> This scheme is available to only female students - this will be verified using Aadhaar </li>
    <li> Scholarship is available to students between age 12 to 30 only - this will be verified through Aadhaar </li>
    <li> Finally after minimum criteria of age and gender is met, TrueScholar central system is used to determine if the student has high score [eligible for scholarship] or low
    score [not eligible for scholarship]. TrueScholar central exam results system is mock/dummy for now: it will return low score if last Aadhaar digit is even, and high score if last Aadhaar digit is odd</li>
</ul>
</div>

<div id="notice">
    <b>
        <div id="message" style="display: none"></div>
    </b>
</div>




<form >
    <table>
        <tr>
            <td>Aadhaar Number :</td> <td><input type="text" id="aadhaar_number"></td><br>
        </tr>
        <tr style="float:right">
            <td><input id="send_otp" type="button" value="Send OTP to Mobile"></td>
        </tr>
        <tr>
            <td>OTP :</td> <td><input type="text" id="otp"></td><br>
        </tr>
        <tr>
            <td><input id="kyc" type="button" value="APPLY FOR SCHOLARSHIP"></td>
        </tr>


    </table>
</form>




<script>

    $("#send_otp").click(function(){
        $.get("/aadhaar-truescholar-backend/scholarship/otp",{ aadhaarNumber:$("#aadhaar_number").val()}, function(data, status){
            //alert("Data: " + data + "\nStatus: " + status);
            $("#message").text(data);
            $("#message").show();

        });
    });

    $("#kyc").click(function(){
        $.get("/aadhaar-truescholar-backend/scholarship/kyc",{ aadhaarNumber:$("#aadhaar_number").val(), otp : $("#otp").val()},
                function(data, status){
            //alert("Data: " + data + "\nStatus: " + status);
            $("#message").text(data);
            $("#message").show();
        });
    });
</script>



</body></html>