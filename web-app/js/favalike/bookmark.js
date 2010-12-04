$(document).ready(function() {
    $("#addBookmarkDialog").dialog({
        bgiframe: true,
        autoOpen: false,
        height: 313,
        width: 495,
        modal: true,
        resizable: false,
        buttons: {
            "Add": function() {
                addBookmark();
            },
            "Cancel": function() {
                $(this).dialog("close");
                clearForm(this);
                clearAddBookmarkAutoRetrievedData();
                formatInputFieldHint();
            }
        }
    });

    formatInputFieldHint();
});

function getBookmarksForUser(offset, max) {
    var url = "bookmark/findAllByUser";

    if(isRangeAware(offset, max)) {
        url += "?offset=" + offset + "&max=" + max;
    }

    loadBookmarks(url);
}

function getBookmarksForUserAndFolder(folderId, offset, max) {
    var url = "bookmark/findAllByUserAndFolderId?folderId=" + folderId;

    if(isRangeAware(offset, max)) {
        url += "&offset=" + offset + "&max=" + max;
    }

    loadBookmarks(url);
}

function getBookmarksForUserAndHasTag(offset, max) {
    var url = "bookmark/findAllByUserAndHasTag";

    if(isRangeAware(offset, max)) {
        url += "?offset=" + offset + "&max=" + max;
    }

    loadBookmarks(url);
}

function getBookmarksForUserAndTag(tagId, offset, max) {
    var url = "bookmark/findAllByUserAndTagId?tagId=" + tagId;

    if(isRangeAware(offset, max)) {
        url += "&offset=" + offset + "&max=" + max;
    }

    loadBookmarks(url);
}

function getBookmarksForUserAndFavorite(offset, max) {
    var url = "bookmark/findAllByUserAndFavorite";

    if(isRangeAware(offset, max)) {
        url += "?offset=" + offset + "&max=" + max;
    }

    loadBookmarks(url);
}

function getBookmarksForNodeType(nodeType, id, offset, max) {
    if(!isRangeAware(offset, max)) {
        offset = default_offset;
        max = default_max;
    }

    if(isTagsNodeType(nodeType)) {
        getBookmarksForUserAndHasTag(offset, max);
    }
    else if(isTagNodeType(nodeType)) {
        getBookmarksForUserAndTag(id, offset, max);
    }
    else if(isFoldersNodeType(nodeType)) {
        getBookmarksForUser(offset, max);
    }
    else if(isFolderNodeType(nodeType)) {
        getBookmarksForUserAndFolder(id, offset, max);
    }
    else if(isFavoritesNodeType(nodeType)) {
        getBookmarksForUserAndFavorite(offset, max);
    }
}

function isRangeAware(offset, max) {
    if((offset != undefined && max != undefined) && (isNaN(offset) == false && isNaN(max) == false)) {
        return true;
    }

    return false;
}

var loadBookmarksCallback = {
    success: function(data, textStatus) {
        $("#content").html(data);
    },
    failure: function(XMLHttpRequest, textStatus, errorThrown) {
        showErrorMessageDialog('Error loading bookmarks: ' + XMLHttpRequest.statusText + ' (' + XMLHttpRequest.status + ')');
    }
}

function loadBookmarks(url) {
    var ajaxHttpSender = new AjaxHttpSender();
    ajaxHttpSender.sendGet(url, loadBookmarksCallback);
}

var addBookmarkCallback = {
    success: function(data, textStatus) {
        clearForm("#addBookmarkDialog");
        clearAddBookmarkAutoRetrievedData();
        formatInputFieldHint();
        $("#addBookmarkDialog").dialog("close");

        var tree = $.tree.focused();

        if(tree.selected) {
            var idString = tree.selected.attr("id");
            var treeNodeId = parseId(idString);
            var treeNode = tree.get_type(tree.selected);

            var total = parseInt($("#showBookmarksForm input[id='total']").val());
            var max = parseInt($("#showBookmarksForm input[id='max']").val());

            // Identify offset for last page
            var offset = getLastOffset(total + 1, max);

            // Reload bookmarks
            getBookmarksForNodeType(treeNode, treeNodeId, offset, max);

            refreshTagsTreeNode();
        }
    },
    failure: function(XMLHttpRequest, textStatus, errorThrown) {
        showErrorMessageDialog('Error adding bookmark: ' + XMLHttpRequest.statusText + ' (' + XMLHttpRequest.status + ')');
    }
}

function addBookmark() {
    var tree = $.tree.focused();

    if(tree.selected) {
        var idString = tree.selected.attr("id");
        var folderId = parseId(idString);

        // Tree id for all bookmarks is "folders"
        folderId = (folderId == 'folders') ? '' : folderId;

        filterInputFieldHintValue($("#addBookmarkForm input[id='commaSeparatedTags']"));
        var data = "folderId=" + folderId + "&" + $("#addBookmarkForm").serialize();

        var ajaxHttpSender = new AjaxHttpSender();
        ajaxHttpSender.sendPost('bookmark/add', data, addBookmarkCallback);
    }
}

var getBookmarkDetailsCallback = {
    success: function(data, textStatus) {
        var commaSeparatedTags = data.bookmark.commaSeparatedTags;

        $("#editBookmarkForm input[id='id']").val(data.bookmark.id);
        $("#editBookmarkForm input[id='name']").val(data.bookmark.name);
        $("#editBookmarkForm input[id='commaSeparatedTags']").val(commaSeparatedTags);
        $("#editBookmarkForm input[id='location']").val(data.bookmark.location);
        $("#editBookmarkForm textarea[id='description']").val(data.bookmark.description);
        $("#editBookmarkForm div[id='sitepreview']").html("<a href=\"" + data.bookmark.location + "\" target=\"_blank\" id=\"sitepreviewLink\"><img src=\"http://open.thumbshots.org/image.aspx?url=" + data.bookmark.location + "\" class=\"preview\" id=\"sitepreviewImage\"></a>");

        if(isBlank(commaSeparatedTags)) {
            formatInputFieldHint();
        }
        else {
            $("#editBookmarkForm input[id='commaSeparatedTags']").removeClass('placeholder');
        }

        showBookmarkDetails();
    },
    failure: function(XMLHttpRequest, textStatus, errorThrown) {
        $("#editBookmarkForm input[id='commaSeparatedTags']").removeClass('placeholder');
        showErrorMessageDialog('Error getting bookmark details: ' + XMLHttpRequest.statusText + ' (' + XMLHttpRequest.status + ')');
    }
}

function getBookmarkDetails(bookmarkId) {
    var data = "bookmarkId=" + bookmarkId;
    var ajaxHttpSender = new AjaxHttpSender();
    ajaxHttpSender.sendPost('bookmark/findByBookmarkId', data, getBookmarkDetailsCallback);
}

var updateBookmarkCallback = {
    success: function(data, textStatus) {
        $("#bookmarkName_" + data.bookmark.id).text(data.bookmark.name);
        $("#bookmarkCommaSeparatedTags_" + data.bookmark.id).text(data.bookmark.commaSeparatedTags);
        $("#bookmarkLocation_" + data.bookmark.id).attr("href", data.bookmark.location);
        $("#bookmarkLocation_" + data.bookmark.id).text(data.bookmark.location);
        $("#sitepreviewLink").attr("href", data.bookmark.location);
        refreshTagsTreeNode();
    },
    failure: function(XMLHttpRequest, textStatus, errorThrown) {
        showErrorMessageDialog('Error updating bookmark: ' + XMLHttpRequest.statusText + ' (' + XMLHttpRequest.status + ')');
    }
}

function updateBookmark() {
    // Exception for using serialize() because one field has to be included conditionally
    var id = $("#editBookmarkForm input[id='id']").val();
    var name = $("#editBookmarkForm input[id='name']").val();
    var commaSeparatedTags = $("#editBookmarkForm input[id='commaSeparatedTags']").val();
    var location = $("#editBookmarkForm input[id='location']").val();
    var description = $("#editBookmarkForm textarea[id='description']").val();

    var data = "id=" + id + "&name=" + name + "&location=" + location + "&description=" + description;

    data += "&commaSeparatedTags=";

    // Only send value for tags if not input field hint
    if(isInputFieldHintNoValue($("#editBookmarkForm input[id='commaSeparatedTags']")) == false) {
        data += commaSeparatedTags;
    }
    
    var ajaxHttpSender = new AjaxHttpSender();
    ajaxHttpSender.sendPost('bookmark/update', data, updateBookmarkCallback);
}

function triggerDeleteBookmarkAction(bookmarkId) {
    $("#deleteBookmarkDialog").dialog({
        bgiframe: true,        
        autoOpen: false,
        resizable: false,
        height: 160,
        modal: true,
        buttons: {
            "Delete": function() {
                removeBookmark(bookmarkId);
            },
            "Cancel": function() {
                $(this).dialog("close");
            }
        }
    });

    $("#deleteBookmarkDialog").dialog("open");
}

function removeBookmark(bookmarkId) {
    var data = "id=" + bookmarkId;
    var ajaxHttpSender = new AjaxHttpSender();
    ajaxHttpSender.sendPost('bookmark/delete', data, removeBookmarkCallback);
}

var removeBookmarkCallback = {
    success: function(data, textStatus) {
        $("#deleteBookmarkDialog").dialog("destroy");

        // Reload selected folder details
        var tree = $.tree.focused();

        if(tree.selected) {
            var idString = tree.selected.attr("id");
            var treeNodeId = parseId(idString);
            var treeNode = tree.get_type(tree.selected);

            var offset = parseInt($("#showBookmarksForm input[id='offset']").val());
            var max = parseInt($("#showBookmarksForm input[id='max']").val());
            var lastRecordNumber = parseInt($("#showBookmarksForm input[id='lastRecordNumber']").val());

            // Identify offset for current page
            offset = (lastRecordNumber - 1) == offset ? offset - max : offset;

            // In case we delete the last record on page one
            if(offset < 0) {
                offset = 0;
            }

            // Reload bookmarks
            getBookmarksForNodeType(treeNode, treeNodeId, offset, max);

            refreshTagsTreeNode();
            hideAllDetails();
        }
    },
    failure: function(XMLHttpRequest, textStatus, errorThrown) {
        $("#deleteBookmarkDialog").dialog("destroy");
        showErrorMessageDialog('Error removing bookmark: ' + XMLHttpRequest.statusText + ' (' + XMLHttpRequest.status + ')');
    }
}

function toogleBookmarkFavorite(bookmarkId) {
    var favorite = $("#bookmarkFavorite_" + bookmarkId).val();

    if(favorite === 'true') {
        removeBookmarkFromFavorites(bookmarkId);
    }
    else {
        addBookmarkToFavorites(bookmarkId);
    }
}

function removeBookmarkFromFavorites(bookmarkId) {
    var removeBookmarkFromFavoritesCallback = {
        success: function(data, textStatus) {
            $("#bookmarkFavorite_" + bookmarkId).val('false');
            $("#bookmarkFavoriteLink_" + bookmarkId).attr("title", "Add bookmark to favorites");
            $("#bookmarkFavoriteImage_" + bookmarkId).attr("src", "images/star_empty.png");
        },
        failure: function(XMLHttpRequest, textStatus, errorThrown) {
            showErrorMessageDialog('Error removing bookmark from favorites: ' + XMLHttpRequest.statusText + ' (' + XMLHttpRequest.status + ')');
        }
    }

    var data = "id=" + bookmarkId + "&favorite=false";
    var ajaxHttpSender = new AjaxHttpSender();
    ajaxHttpSender.sendPost('bookmark/favorite', data, removeBookmarkFromFavoritesCallback);
}

function addBookmarkToFavorites(bookmarkId) {
    var addBookmarkFromFavoritesCallback = {
        success: function(data, textStatus) {
            $("#bookmarkFavorite_" + bookmarkId).val('true');
            $("#bookmarkFavoriteLink_" + bookmarkId).attr("title", "Remove bookmark from favorites");
            $("#bookmarkFavoriteImage_" + bookmarkId).attr("src", "images/star.png");
        },
        failure: function(XMLHttpRequest, textStatus, errorThrown) {
            showErrorMessageDialog('Error adding bookmark to favorites: ' + XMLHttpRequest.statusText + ' (' + XMLHttpRequest.status + ')');
        }
    }

    var data = "id=" + bookmarkId + "&favorite=true";
    var ajaxHttpSender = new AjaxHttpSender();
    ajaxHttpSender.sendPost('bookmark/favorite', data, addBookmarkFromFavoritesCallback);
}

function refreshTagsTreeNode() {
    if(tagsNodeOpened == true) {
        $.tree.reference("tags").refresh($('#tags'));    
    }
}

function retrieveBookmarkMetaData() {
    var url = $("#addBookmarkForm input[id='location']").val();
    var data = "url=" + url;
    var ajaxHttpSender = new AjaxHttpSender();
    ajaxHttpSender.sendPost('bookmark/metaData', data, retrieveBookmarkMetaDataCallback);
    $("#addBookmarkForm div[id='autoRetriever']").html("<img src=\"images/spinner.gif\" border=\"0\" style=\"vertical-align:middle;\"> Loading...");
}

var retrieveBookmarkMetaDataCallback = {
    success: function(data, textStatus) {
        resetAutoRetriever();
        var url = $("#addBookmarkForm input[id='location']").val();
        var title = data.bookmark.title;
        var description = data.bookmark.description;
        var faviconUrl = data.bookmark.faviconUrl;

        if(title != null) {
            $("#addBookmarkForm input[id='name']").val(title);
        }
        else {
            $("#addBookmarkForm input[id='name']").val("");
        }

        if(description != null) {
            $("#addBookmarkForm textarea[id='description']").val(description);
        }
        else {
            $("#addBookmarkForm textarea[id='description']").val("");
        }

        if(faviconUrl != null) {
            $("#addBookmarkForm div[id='favicon']").html("<li><label>Favicon:</label><img src=\"" + faviconUrl + "\" class=\"favicon\"></li>");
            $("#addBookmarkForm input[id='faviconUrl']").val(faviconUrl);
        }
        else {
            $("#addBookmarkForm div[id='favicon']").html("<li><label>Favicon:</label><img src=\"images/page_white.png\" class=\"favicon\"></li>");
            $("#addBookmarkForm input[id='faviconUrl']").val("");
        }

        $("#addBookmarkForm div[id='message']").html("");
        $("#addBookmarkForm div[id='sitepreview']").html("<li><label style=\"width:75px\">Preview:</label></li> <li><img src=\"http://open.thumbshots.org/image.aspx?url=" + url + "\" class=\"preview\"></li>");
    },
    failure: function(XMLHttpRequest, textStatus, errorThrown) {
        resetAutoRetriever();
        $("#addBookmarkForm div[id='favicon']").html("");
        $("#addBookmarkForm input[id='faviconUrl']").val("");
        $("#addBookmarkForm div[id='sitepreview']").html("");
        $("#addBookmarkForm div[id='message']").html("<li>Unable to retrieve site data</li>");
    }
}

function clearAddBookmarkAutoRetrievedData() {
    $("#addBookmarkForm input[id='location']").val("http://");
    $("#addBookmarkForm div[id='favicon']").html("");
    $("#addBookmarkForm input[id='faviconUrl']").val("");
    $("#addBookmarkForm div[id='sitepreview']").html("");
    $("#addBookmarkForm div[id='message']").html("");
}

function resetAutoRetriever() {
    $("#addBookmarkForm div[id='autoRetriever']").html("<img src=\"images/wand.png\" border=\"0\" style=\"vertical-align:middle;\"> <a href=\"#\" onclick=\"javascript:retrieveBookmarkMetaData()\">Auto-Retrieve Data</a>");
}