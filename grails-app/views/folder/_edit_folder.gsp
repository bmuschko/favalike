<%@ page contentType="text/html;charset=UTF-8" %>
<form id="editFolderForm" style="display: none">
    <input type="hidden" id="id" name="id" />
    <ul class="formEditList">
       <li>
          <label>Name:</label>
          <input type="text" id="name" name="name"/>
       </li>
       <li>
          <label>Description:</label>
          <textarea id="description" name="description" rows="3" cols="20"></textarea>
       </li>
       <li>
          <label></label>
          <input type="button" style="width: 62px;" class="saveBtn" value="" onclick="javascript:updateFolder()"/>
      </li>
    </ul>
</form>
