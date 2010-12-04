<%@ page contentType="text/html;charset=UTF-8" %>
<div id="login" class="login">
   <form action="/j_spring_security_check" method="POST">
      <table>
	     <tr>
		    <td>
			   Email:<br/>
			   <input name="j_username" id="j_username" type="text" value="<g:if test='${params.login_error}'>${session.SPRING_SECURITY_LAST_USERNAME}</g:if>" style="width: 145px;" tabindex="1" maxlength="70"/><br/>
			   <input name="_spring_security_remember_me" type="checkbox" style="float:left; margin: 0px 2px 0pt 0pt; padding: 0pt; position: relative; top: 1px; left: 0pt;"/>
			   <div class="small_text">Remember me</div>
			</td>
			<td>
			   Password:<br/>
			   <input name="j_password" id="j_password" type="password" onfocus="this.select();" style="width: 145px;" tabindex="1" maxlength="70"/><br />
			   <div class="small_text">Forgot your password?</div>
			</td>
			<td>
		   	   <input class="loginBtn" type="Submit" tabindex="3" value="" name="submit"/>
			</td>
		 </tr>
	  </table>
   </form>
   <g:if test="${params.login_error && session.SPRING_SECURITY_LAST_EXCEPTION}">
      <div style="color: red; font-size:8pt;float:left; margin: 2px 2px 0pt 0pt; padding: 0pt; position: relative; top: 1px; left: 2pt;">
         ${session.SPRING_SECURITY_LAST_EXCEPTION.message}.
      </div>
   </g:if>
</div>