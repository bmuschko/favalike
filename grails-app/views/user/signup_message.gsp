<%@ page contentType="text/html;charset=UTF-8" %>
<html>
   <head>
      <meta name="layout" content="main"/>
      <title>Registration</title>
   </head>
   <body>
      <content tag="mainContent">
         <div>
            <h3>Please check your email.</h3>
            <h4>We have sent you an email to confirm your registration.</h4>
            <p>If you did not receive the email confirmation at ${username}, please add ${email} to your address book and <a href="${createLink(action:'resendmail', params:['username': username])}">click here to resend the email</a>.</p>
         </div>
      </content>
   </body>
</html>