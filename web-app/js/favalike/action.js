$(function() {
    $("#actionMenu button").button({
        text: false,
        icons: {
            primary: 'ui-icon-gear',
            secondary: 'ui-icon-triangle-1-s'
        }
    }).each(function() {
        $(this).next().menu({
            select: function(event, ui) {
                $(this).hide();
            },
            input: $(this)
        }).hide();
    }).click(function(event) {
        var menu = $(this).next();
        if(menu.is(":visible")) {
            menu.hide();
            return false;
        }
        menu.menu("deactivate").show().css({top:0, left:0}).position({
            my: "left top",
            at: "left bottom",
            of: this
        });
        $("#backupMenu ul").hide();
        $(document).one("click", function() {
            menu.hide();
        });

        return false;
    })

    $("#backupMenu button").button({
        text: false,
        icons: {
            primary: 'ui-icon-suitcase',
            secondary: 'ui-icon-triangle-1-s'
        }
    }).each(function() {
        $(this).next().menu({
            select: function(event, ui) {
                $(this).hide();
            },
            input: $(this)
        }).hide();
    }).click(function(event) {
        var menu = $(this).next();
        if(menu.is(":visible")) {
            menu.hide();
            $("#actionMenu ul").hide();
            return false;
        }
        menu.menu("deactivate").show().css({top:0, left:0}).position({
            my: "left top",
            at: "left bottom",
            of: this
        });
        $("#actionMenu ul").hide();
        $(document).one("click", function() {
            menu.hide();
        });

        return false;
    })

    $("#newBookmarkButton").button({
        text: true,
        icons: {
            primary: 'ui-icon-star'
        }
    }).click(function(event) {
        triggerNewBookmarkAction();
    });

    $('#newBookmarkButton').button('option', 'disabled', false);
});

function disableMenuItem(menuItemId)
{
    var menuItem = $("#" + menuItemId);
    menuItem.removeClass("ui-menu-item");
    menuItem.addClass("ui-menu-item-disabled");
    menuItem.children("a").children("div").unwrap();
}

function enableMenuItem(menuItemId, onClickValue)
{
    var menuItem = $("#" + menuItemId);
    menuItem.children("div").wrap("<a tabindex=\"-1\" class=\"ui-corner-all\" href=\"#\" onclick=\"" + onClickValue + "\"></a>");
    menuItem.removeClass("ui-menu-item-disabled");
    menuItem.addClass("ui-menu-item");
    menuItem.mouseenter(function(event) {
                menuItem.children("a")
                .addClass("ui-state-hover")
                .attr("id", "ui-active-menuitem");
            })
            .mouseleave(function() {
                menuItem.children("a")
                .removeClass("ui-state-hover")
                .removeAttr("id");
           });
}

function triggerNewFolderAction() {
    allowAddingFolder();
}

function triggerNewBookmarkAction() {
    $('#newBookmarkButton').removeClass('ui-state-focus');
    $('#addBookmarkDialog').dialog('open');
    $("#addBookmarkForm input[id='location']").focus();  
}

function triggerDeleteAction() {
    $("#deleteFolderDialog").dialog({
        bgiframe: true,
        autoOpen: false,
        resizable: false,
        height: 175,
        modal: true,
        buttons: {
            "Delete": function() {
                removeFolder();
            },
            "Cancel": function() {
                $(this).dialog("close");
            }
        }
    });

    $("#deleteFolderDialog").dialog("open");
}

function enableAllActions() {
    enableMenuItem('newFolderMenuItem', 'javascript:triggerNewFolderAction()');
    enableMenuItem('newBookmarkMenuItem', 'javascript:triggerNewBookmarkAction()');
    enableMenuItem('deleteMenuItem', 'javascript:triggerDeleteAction()');
    $('#newBookmarkButton').button('option', 'disabled', false);
}

function disableAllActions() {
    disableMenuItem('newFolderMenuItem');
    disableMenuItem('newBookmarkMenuItem');
    disableMenuItem('deleteMenuItem');
    $('#newBookmarkButton').button('option', 'disabled', true);
}

function enableDeleteOnlyAction() {
    disableMenuItem('newFolderMenuItem');
    enableMenuItem('deleteMenuItem', 'javascript:triggerDeleteAction()');
    disableMenuItem('newBookmarkMenuItem');
    $('#newBookmarkButton').button('option', 'disabled', true);
}

function disableDeleteOnlyAction() {
    enableMenuItem('newFolderMenuItem', 'javascript:triggerNewFolderAction()');
    disableMenuItem('deleteMenuItem');
    enableMenuItem('newBookmarkMenuItem', 'javascript:triggerNewBookmarkAction()');
    $('#newBookmarkButton').button('option', 'disabled', false);
}

function triggerExportToHtml() {
    window.location.href = '/export/html';
}

function triggerExportToXml() {
    window.location.href = '/export/xml';
}