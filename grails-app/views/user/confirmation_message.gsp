<%@ page contentType="text/html;charset=UTF-8" %>
<html>
   <head>
      <meta name="layout" content="main"/>
      <title>Account Confirmation</title>
   </head>
   <body>
      <content tag="mainContent">
         <div>
            <g:if test="${result.success}">
               <h3>Successfully confirmed account.</h3>
            </g:if>
            <g:else>
               <h3>Failed to confirm account.</h3>              
            </g:else>
            <g:if test="${result.success}">
               <p>${result.message}</p>
            </g:if>
            <g:else>
               <div style="color: red;"><p>${result.message}.</p></div>
            </g:else>
         </div>
      </content>
   </body>
</html>