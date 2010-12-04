<%@ page import="com.google.appengine.api.datastore.KeyFactory" contentType="text/html;charset=UTF-8" %>
<html>
    <head>
        <meta name="layout" content="main"/>
        <title>Your Bookmarks</title>
        <script type="text/javascript" src="${createLinkTo(dir:'js/jquery',file:'jquery-1.4.2.min.js')}"></script>
        <script type="text/javascript" src="${createLinkTo(dir:'js/jquery',file:'jquery-ui-1.8.2.custom.min.js')}"></script>
        <script type="text/javascript" src="${createLinkTo(dir:'js/jquery',file:'jquery.tree.min.js')}"></script>
        <script type="text/javascript" src="${createLinkTo(dir:'js/jquery',file:'jquery.tablesorter.min.js')}"></script>
        <script type="text/javascript" src="${createLinkTo(dir:'js/favalike',file:'http.js')}"></script>
        <script type="text/javascript" src="${createLinkTo(dir:'js/favalike',file:'common.js')}"></script>
        <script type="text/javascript" src="${createLinkTo(dir:'js/favalike',file:'pagination.js')}"></script>
        <script type="text/javascript" src="${createLinkTo(dir:'js/favalike',file:'tree.js')}"></script>
        <script type="text/javascript" src="${createLinkTo(dir:'js/favalike',file:'action.js')}"></script>
        <script type="text/javascript" src="${createLinkTo(dir:'js/favalike',file:'folder.js')}"></script>
        <script type="text/javascript" src="${createLinkTo(dir:'js/favalike',file:'bookmark.js')}"></script>
        <script type="text/javascript" src="${createLinkTo(dir:'js/favalike',file:'details.js')}"></script>
        <script type="text/javascript">
            var rootFolderId = "folder_${KeyFactory.keyToString(user.folder.key)}";
            var stat =  [{ attributes : { "id" : "tags", "rel" : "tags" }, data : "Tags", state : "closed"},
                         { attributes : { "id" : "favorites", "rel" : "favorites" }, data : "Favorites", state : "opened"},
                         { attributes : { "id" : "folders", "rel" : "folders" }, data : "${user.folder.name}", state : "closed"}];
         </script>
        <link rel="stylesheet" type="text/css" href="${createLinkTo(dir:'css/ui-favalike',file:'jquery-ui-1.8.2.custom.css')}" />
    </head>
    <body>
        <content tag="loginInfo">
           <div id="login" class="login">
              Welcome ${user.firstname} ${user.lastname}! | <a href="/j_spring_security_logout">Sign out</a>
           </div>
           <br/><br/>
           <div class="alignright"><div id="loading" class="loading"><img src="${createLinkTo(dir:'images',file:'spinner.gif')}" border="0" style="vertical-align:bottom;"> Loading...</div></div>
        </content>
        <content tag="mainContent">
           <div style="height:550px;">
              <div id="main_left" class="main_left">
                 <div style="padding-left: 5px">
                    <table>
                       <tr>
                          <td><g:render template="/action_menu"/></td>
                          <td><g:render template="/backup_menu"/></td>
                          <td><button id="newBookmarkButton">Bookmark</button></td>
                       </tr>
                    </table>
                 </div>
                 <div id="bookmarksTree" style="margin-top: 5px">
                 </div>
              </div>
              <div id="main_right" class="main_right">
                 <div>
                    <div id="content" style="height:350px;">
                    </div>
                    <div id="edit">
                       <g:render template="/folder/edit_folder"/>
                       <g:render template="/bookmark/edit_bookmark"/>
                    </div>
                    <g:render template="/folder/add_folder"/>
                    <g:render template="/bookmark/add_bookmark"/>
                    <g:render template="/folder/delete_folder"/>
                    <g:render template="/bookmark/delete_bookmark"/>
                    <g:render template="/error_message"/>
                 </div>
              </div>
           </div>
        </content>
    </body>
</html>