<%@ page contentType="text/html;charset=UTF-8" %>
<div id="addBookmarkDialog" title="Add Bookmark">
    <form id="addBookmarkForm">
        <ul class="formAddList">
           <li>
              <label>Location:</label>
              <input type="text" id="location" name="location" value="http://"/>
           </li>
           <li>
              <label>Name:</label>
              <input type="text" id="name" name="name"/>
           </li>
           <li>
              <label>Tags:</label>
              <input type="text" id="commaSeparatedTags" name="commaSeparatedTags" placeholder="Separate tags with commas"/>
           </li>
           <li>
              <label>Description:</label>
              <textarea id="description" name="description" rows="6"></textarea>
           </li>
        </ul>
        <ul class="formAddListRight">
           <li>
              <div id="autoRetriever"><img src="${createLinkTo(dir:'images',file:'wand.png')}" border="0" style="vertical-align:middle;"> <a href="#" onclick="javascript:retrieveBookmarkMetaData()">Auto-Retrieve Data</a></div>
           </li>
           <li><div id="message" style="color: red;margin-top:15px;"></div></li>
           <div id="favicon" style="margin-top:15px;"></div>
           <div id="sitepreview" style="margin-top:15px;"></div>
        </ul>
        <input type="hidden" id="faviconUrl" name="faviconUrl"/>
    </form>
</div>