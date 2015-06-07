<%--
  Created by IntelliJ IDEA.
  User: anantha
  Date: 7/6/15
  Time: 3:03 PM
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Online Registration for Exams - powered by Aadhaar</title>
    <script src="http://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
</head>

<body bgcolor="#FFFFFF" leftmargin="0" topmargin="0">

<header id="header" class="clearfix" role="banner">
    <div id="logo"><h1><a href="http://mhrd.gov.in/" title="Home"><img src="http://mhrd.gov.in/sites/upload_files/mhrd/files/logo_0.png" alt="Home"></a></h1></div>

</header>

<style>
ul {
    width: 70%;
    margin: auto;
}
form {
    width: 350px;
    margin: 0 auto;
}

#notice, #heading {
    width: 350px;
    margin: 0 auto;
    margin-top: 50px;
}


</style>


<div style="margin-top: 10px;">
    <ul>
        <li> Authentication of the student is done by doing student's demographic details match against Aadhaar system</li>
        <li> This is a completely online process - location [exam centre] details will be put up on website 2 months before exam </li>
        <li> We respect students' privacy - results of exams will be sent to registered mobile of student as found in Aadhaar system  </li>



    </ul>
</div>

<h2 id="heading"> REGISTER FOR KARNATAKA CET 2016 EXAM ONLINE</h2>

<p>
    <i>Disclaimer: This is a demo site built for Aadhaar hackathon and does not belong to Govt of India/Karnataka. This portal will work until Khosla labs APIs are kept open for hackathon </i>
</p>

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
        <tr>
            <td>Full Name :</td> <td><input type="text" id="name"></td><br>
        </tr>
        <tr>
            <td><input id="auth" type="button" value="Register for Exam"></td>
        </tr>


    </table>
</form>




<script>


    $("#auth").click(function(){
        $.get("/aadhaar-truescholar-backend/registration/auth",{ aadhaarNumber:$("#aadhaar_number").val(), name : $("#name").val()},
                function(data, status){
                    //alert("Data: " + data + "\nStatus: " + status);
                    $("#message").text(data);
                    $("#message").show();
                });
    });
</script>



</body>
</html>