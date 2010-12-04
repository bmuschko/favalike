$(document).ready(function() {
    $("#addFolderDialog").dialog({
        bgiframe: true,
        autoOpen: false,
        height: 160,
        width: 290,
        modal: true,
        resizable: false,
        buttons: {
            "Add": function() {
                addFolder();
            },
            "Cancel": function() {
                clearForm(this);
                $(this).dialog("close");
            }
        }
    });
});

function allowAddingFolder() {
    var tree = $.tree.focused();

    if(tree.selected) {
        $('#addFolderDialog').dialog('open');
    }
}

function addFolder() {
    var tree = $.tree.focused();

    if(tree.selected) {
        var idString = tree.selected.attr("id");
        var folderId = idString != "folders" ? parseId(idString) : parseId(rootFolderId);
        var data = "parentFolderId=" + folderId + "&" + $("#addFolderForm").serialize();

        var addFolderCallback = {
            success: function(data, textStatus) {
                clearForm("#addFolderDialog");
                $("#addFolderDialog").dialog("close");
                $.tree.reference($('#' + idString)).refresh($('#' + idString));    
            },
            failure: function(XMLHttpRequest, textStatus, errorThrown) {
                showErrorMessageDialog('Error adding folder: ' + XMLHttpRequest.statusText + ' (' + XMLHttpRequest.status + ')');
            }
        }

        var ajaxHttpSender = new AjaxHttpSender();
        ajaxHttpSender.sendPost('folder/add', data, addFolderCallback);
    }
}

var removeFolderCallback = {
    success: function(data, textStatus) {
        $("#deleteFolderDialog").dialog("destroy");
        $.tree.focused().remove();
        $.tree.focused().refresh();
    },
    failure: function(XMLHttpRequest, textStatus, errorThrown) {
        $("#deleteFolderDialog").dialog("destroy");
        showErrorMessageDialog('Error removing folder: ' + XMLHttpRequest.statusText + ' (' + XMLHttpRequest.status + ')');
    }
}

function removeFolder() {
    var tree = $.tree.focused();
   
    if(tree.selected) {
        var idString = tree.selected.attr("id");
        var folderId = parseId(idString);
        var data = "id=" + folderId;

        var ajaxHttpSender = new AjaxHttpSender();
        ajaxHttpSender.sendPost('folder/delete', data, removeFolderCallback);
    }
}

var getFolderDetailsCallback = {
    success: function(data, textStatus) {
        $("#editFolderForm input[id='id']").val(data.folder.id);
        $("#editFolderForm input[id='name']").val(data.folder.name);
        $("#editFolderForm textarea[id='description']").val(data.folder.description);
    },
    failure: function(XMLHttpRequest, textStatus, errorThrown) {
        showErrorMessageDialog('Error getting folder details: ' + XMLHttpRequest.statusText + ' (' + XMLHttpRequest.status + ')');
    }
}

function getFolderDetails(folderId) {
    var data = "folderId=" + folderId;
    var ajaxHttpSender = new AjaxHttpSender();
    ajaxHttpSender.sendPost('folder/findByFolderId', data, getFolderDetailsCallback);
}

var updateFolderCallback = {
    success: function(data, textStatus) {
        var parentId = data.folder.parentId;

        if(parentId == null) {
            $.tree.focused().rename("#folders", data.folder.name);
        }
        else {
            $.tree.focused().rename("#folder_" + data.folder.id, data.folder.name);   
        }
    },
    failure: function(XMLHttpRequest, textStatus, errorThrown) {
        showErrorMessageDialog('Error updating folder: ' + XMLHttpRequest.statusText + ' (' + XMLHttpRequest.status + ')');
    }
}

function updateFolder() {
    var data = $("#editFolderForm").serialize();
    var ajaxHttpSender = new AjaxHttpSender();
    ajaxHttpSender.sendPost('folder/update', data, updateFolderCallback);
}