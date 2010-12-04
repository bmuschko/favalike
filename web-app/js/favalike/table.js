$(document).ready(function() {
    $(".striped").tablesorter({widgets: ['zebra']}); 
    
    $(".striped tbody tr").mouseover(function() {
        $(this).removeAttr("class");
        $(this).addClass("over");
    });

    $(".striped tbody tr").mouseout(function() {
        $(this).removeClass("over");
        $(".striped tbody tr:odd").addClass("odd");
    });

    $(".striped tbody tr").click(function() {
        var bookmarkId = parseId($(this).attr('id'));
        getBookmarkDetails(bookmarkId);
    });
});
