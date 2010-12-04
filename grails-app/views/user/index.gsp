<%@ page contentType="text/html;charset=UTF-8" %>
<html>
   <head>
      <meta name="layout" content="main"/>
      <title>Welcome</title>
   </head>
   <body>
      <content tag="loginInfo">
         <g:render template="login"/>
      </content>
      <content tag="mainContent">
         <table>
            <tr>
               <td valign="top">
                  <div id="index_left" class="index_left">
                  <div class="title_text">
                     Manage your favorites - easily!
	              </div>
                  <br/>
                  <div id="feature_container" class="feature_container">
                     <ul>
                        <li><img src="${createLinkTo(dir:'images',file:'rainbow.png')}" style="vertical-align:top;">&nbsp;&nbsp;Free service</li>
                        <li><img src="${createLinkTo(dir:'images',file:'world.png')}" style="vertical-align:top;">&nbsp;&nbsp;Access your bookmarks from everywhere</li>
                        <li><img src="${createLinkTo(dir:'images',file:'wand.png')}" style="vertical-align:top;">&nbsp;&nbsp;Automatically retrieve bookmark information</li>
                        <li><img src="${createLinkTo(dir:'images',file:'star.png')}" style="vertical-align:top;">&nbsp;&nbsp;Mark your favorite bookmarks</li>
                        <li><img src="${createLinkTo(dir:'images',file:'tag_blue.png')}" style="vertical-align:top;">&nbsp;&nbsp;Tag your bookmarks</li>
                        <li><img src="${createLinkTo(dir:'images',file:'briefcase.png')}" style="vertical-align:top;">&nbsp;&nbsp;Export your bookmarks</li>
                     </ul>
                  </div>
                  </div>
               </td>
               <td><div class="separator"></div></td>
               <td>
                  <div id="index_right" class="index_right">
                     <g:render template="signup"/>
                  </div>
               </td>
            </tr>
         </table>
      </content>
   </body>
</html>

