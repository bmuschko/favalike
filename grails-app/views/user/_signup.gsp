<%@ page contentType="text/html;charset=UTF-8" %>
<div class="title_text_signup">
   Sign up and go:
</div>
<g:form controller="user" action="signup" method="POST">
   <ul class="formList">
      <li>
         <label>First Name*:</label>
         <input type="text" name="firstname" value="${user?.firstname}" maxlength="50" class="textField ${hasErrors(bean: user, field: 'firstname', 'errors')}" />
      </li>
      <g:hasErrors bean="${user}" field="firstname">
         <label class="login-error-label">
            <g:eachError bean="${user}" field="firstname">
               <g:message error="${it}"/>
            </g:eachError>
         </label>
      </g:hasErrors>
      <li>
         <label>Last Name*:</label>
         <input type="text" name="lastname" value="${user?.lastname}" maxlength="50" class="textField ${hasErrors(bean: user, field: 'lastname', 'errors')}" />
      </li>
      <g:hasErrors bean="${user}" field="lastname">
         <label class="login-error-label">
            <g:eachError bean="${user}" field="lastname">
               <g:message error="${it}"/>
            </g:eachError>
         </label>
      </g:hasErrors>
      <li>
         <label>Email*:</label>
         <input type="text" name="username" value="${user?.username}" maxlength="50" class="textField ${hasErrors(bean: user, field: 'username', 'errors')}" />
      </li>
      <g:hasErrors bean="${user}" field="username">
         <label class="login-error-label">
            <g:eachError bean="${user}" field="username">
               <g:message error="${it}"/>
            </g:eachError>
         </label>
      </g:hasErrors>
      <li>
         <label>Password*:</label>
         <input type="password" name="password" value="${user?.password}" maxlength="50" class="textField ${hasErrors(bean: user, field: 'password', 'errors')}" />
      </li>
      <g:hasErrors bean="${user}" field="password">
         <label class="login-error-label">
            <g:eachError bean="${user}" field="password">
               <g:message error="${it}"/>
            </g:eachError>
         </label>
      </g:hasErrors>
      <li>
         <label></label>
         <input type="Submit" style="width: 70px;" class="signupBtn" tabindex="10" value="" name="submit"/>
      </li>
      <li>
         <label></label>
         <div style="font-size: 7.5pt; line-height: 1.5; width: 200px;">
            By clicking Sign Up, you are creating a new account
         </div>
      </li>
   </ul>
</g:form>