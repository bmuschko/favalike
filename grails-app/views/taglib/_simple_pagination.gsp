<%@ page contentType="text/html;charset=UTF-8" %>
<div class="pagination-left-controls">${previousLink}</div>
<div class="pagination-right-controls">${nextLink}</div>
<div>
<g:if test="${lastRecordNumber - firstRecordNumber == 0}">
   Results ${lastRecordNumber} out of ${total}
</g:if>
<g:else>
   Results ${firstRecordNumber}-${lastRecordNumber} out of ${total}
</g:else>
</div>