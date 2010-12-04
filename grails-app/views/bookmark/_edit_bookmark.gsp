<%@ page contentType="text/html;charset=UTF-8" %>
<form id="editBookmarkForm" style="display: none">
    <input type="hidden" id="id" name="id" />
    <ul class="formEditList">
       <li>
          <label>Name:</label>
          <input type="text" id="name" name="name"/>
       </li>
       <li>
          <label>Location:</label>
          <input type="text" id="location" name="location"/>
       </li>
       <li>
          <label>Tags:</label>
          <input type="text" id="commaSeparatedTags" name="commaSeparatedTags" placeholder="Separate tags with commas"/>
       </li>
       <li>
          <label>Description:</label>
          <textarea id="description" name="description" rows="3" cols="47"></textarea>
       </li>
       <li>
          <label></label>
          <input type="button" style="width: 62px;" class="saveBtn" value="" onclick="javascript:updateBookmark()"/>
       </li>
    </ul>
    <ul class="formEditList">
       <li>
          <label>Preview:</label>
          <div id="sitepreview"></div>
       </li>
    </ul>
</form>