<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="com.google.appengine.api.datastore.KeyFactory" %>
<g:if test="${bookmarks.records.size() > 0}">
   <script type="text/javascript" src="${createLinkTo(dir:'js/favalike',file:'table.js')}"></script>
   <form id="showBookmarksForm">
      <table border="1" class="striped" id="bookmarks">
         <thead>
            <tr>
               <th width="250">Name</th>
               <th width="100">Tags</th>
               <th>Location</th>
               <th width="45">&nbsp;</th>
            </tr>
         </thead>
         <tbody>
            <g:each var="bookmark" in="${bookmarks.records}">
            <tr id="bookmark_${KeyFactory.keyToString(bookmark.key)}">
               <td>
                  <g:if test="${bookmark.faviconUrl}">
                     <img src="${bookmark.faviconUrl}" border="0" class="favicon">
                  </g:if>
                  <g:else>
                     <img src="${createLinkTo(dir:'images',file:'page_white.png')}" border="0" style="vertical-align:middle;">
                  </g:else>
                  <span id="bookmarkName_${KeyFactory.keyToString(bookmark.key)}">${bookmark.name}</span>
               </td>
               <td id="bookmarkCommaSeparatedTags_${KeyFactory.keyToString(bookmark.key)}">${bookmark.commaSeparatedTags}</td>
               <td><a href="${bookmark.location}" target="_blank" id="bookmarkLocation_${KeyFactory.keyToString(bookmark.key)}">${bookmark.location}</a></td>
               <td style="text-align: center">
                  <a title="Delete bookmark" href="#" onclick="triggerDeleteBookmarkAction('${KeyFactory.keyToString(bookmark.key)}')"><img src="${createLinkTo(dir:'images',file:'page_delete.png')}" border="0"></a>
                  <g:if test="${bookmark.favorite == true}">
                     <a id="bookmarkFavoriteLink_${KeyFactory.keyToString(bookmark.key)}" title="Remove bookmark from favorites" href="#" onclick="toogleBookmarkFavorite('${KeyFactory.keyToString(bookmark.key)}')"><img id="bookmarkFavoriteImage_${KeyFactory.keyToString(bookmark.key)}" src="${createLinkTo(dir:'images',file:'star.png')}" border="0"></a>
                  </g:if>
                  <g:else>
                     <a id="bookmarkFavoriteLink_${KeyFactory.keyToString(bookmark.key)}" title="Add bookmark to favorites" href="#" onclick="toogleBookmarkFavorite('${KeyFactory.keyToString(bookmark.key)}')"><img id="bookmarkFavoriteImage_${KeyFactory.keyToString(bookmark.key)}" src="${createLinkTo(dir:'images',file:'star_empty.png')}" border="0"></a>
                  </g:else>
                  <input id="bookmarkFavorite_${KeyFactory.keyToString(bookmark.key)}" type="hidden" value="${bookmark.favorite}">
               </td>
            </tr>
            </g:each>
         </tbody>
      </table>
      <input type="hidden" id="total" name="total" value="${bookmarks.total}">
      <input type="hidden" id="offset" name="offset" value="${bookmarks.offset}">
      <input type="hidden" id="max" name="max" value="${bookmarks.max}">
      <input type="hidden" id="lastRecordNumber" name="lastRecordNumber" value="${bookmarks.lastRecordNumber}">
   </form>
   <div class="pagination-container">
      <ezb:simplePagination offset="${bookmarks.offset}" total="${bookmarks.total}" max="${bookmarks.max}" function="${ajaxFunctionName}" parameters="${ajaxFunctionParams}"/>
   </div>
</g:if>
<g:else>
   <span class="notification_text">No bookmarks available for selection.</span>  
</g:else>