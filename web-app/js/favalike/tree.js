$(function () {
    tagsNodeOpened = new Boolean();

    $("#bookmarksTree").tree({
        selected: "folders",
        rules: {
            // only nodes of type root can be top level nodes
            valid_children: ["tags", "favorites", "folders"]
        },
        types : {
            "tags": {
                creatable: false,
                deletable: false,
                renameable: false,
                draggable: false,
                valid_children: ["tag"],
                icon : {
                    image: "images/tag_blue.png"
                }
            },
            "tag": {
                creatable: false,
                draggable: false,
                valid_children: "none",
                icon: {
                    image: "images/tag_blue.png"
                }
            },
            "favorites": {
                creatable: false,
                deletable: false,
                renameable: false,
                draggable: false,
                valid_children: "none",
                icon : {
                    image: "images/star.png"
                }
            },
            "folders": {
                creatable: true,
                deletable: false,
                renameable: true,
                draggable: false,
                valid_children: ["folder"],
                icon: {
                    image: "images/folder_star.png"
                }
            },
            "folder": {
                creatable: true,
                deletable: true,
                renameable: true,
                draggable: false,
                valid_children: ["folder"],
                icon: {
                    image: "images/folder.png"
                }
            }
        },
        data: {
            async: true,
            type: "json",
            opts: {
                method: "GET"
            }
        },
        callback: {
            beforedata: function (node, tree) {
                if(node == false) {
                    tree.settings.data.opts.static = stat;
                }
                else {
                    var nodeType = getNodeType(tree, node);

                    if(isFoldersNodeType(nodeType)) {
                        tree.settings.data.opts.url = "/folder/findAllByParentFolder";
                    }
                    else if(isFolderNodeType(nodeType)) {
                        var idString = node.attr("id");
                        var folderId = parseId(idString);
                        tree.settings.data.opts.url = "/folder/findAllByFolderId?folderId=" + folderId;
                    }
                    else if(isTagsNodeType(nodeType)) {
                        tree.settings.data.opts.url = "/tag/findAllByUser";
                    }

                    tree.settings.data.opts.static = false;
                }
            },
            onselect: function(node, tree) {
                var nodeType = getNodeType(tree, node);

                if(isFoldersNodeType(nodeType)) {
                    onFoldersNodeSelect(node, tree);
                }
                else if(isFolderNodeType(nodeType)) {
                    onFolderNodeSelect(node, tree);
                }
                else if(isTagsNodeType(nodeType)) {
                    onTagsNodeSelect(node, tree);
                }
                else if(isTagNodeType(nodeType)) {
                    onTagNodeSelect(node, tree);
                }
                else if(isFavoritesNodeType(nodeType)) {
                    onFavoritesNodeSelect(node, tree);
                }
            },
            onopen: function(node, tree) {
                var nodeType = getNodeType(tree, node);
                
                if(isTagsNodeType(nodeType)) {
                    tagsNodeOpened = new Boolean(true);
                }
            },
            onopen_all: function(tree) {
                tagsNodeOpened = new Boolean(true);
            }
        }
    });
});

function getNodeType(tree, node) {
    return tree.get_type(tree.get_node(node)[0]);
}

function onFoldersNodeSelect(node, tree) {
    disableDeleteOnlyAction();
    var folderId = parseId(rootFolderId);
    getBookmarksForUser(default_offset, default_max);
    getFolderDetails(folderId);
    showFolderDetails();
}

function onFolderNodeSelect(node, tree) {
    enableAllActions();
    var folderId = getNodeId(node);
    getBookmarksForUserAndFolder(folderId, default_offset, default_max);
    getFolderDetails(folderId);
    showFolderDetails();
}

function onTagsNodeSelect(node, tree) {
    disableAllActions();
    getBookmarksForUserAndHasTag(default_offset, default_max);
    hideAllDetails();
}

function onTagNodeSelect(node, tree) {
    disableAllActions();
    var tagId = getNodeId(node);
    getBookmarksForUserAndTag(tagId, default_offset, default_max);
    hideAllDetails();
}

function onFavoritesNodeSelect(node, tree) {
    disableAllActions();
    getBookmarksForUserAndFavorite(default_offset, default_max);
    hideAllDetails();
}

function isTagsNodeType(nodeType) {
    return equalsString(nodeType, "tags");
}

function isTagNodeType(nodeType) {
    return equalsString(nodeType, "tag");
}

function isFavoritesNodeType(nodeType) {
    return equalsString(nodeType, "favorites");
}

function isFoldersNodeType(nodeType) {
    return equalsString(nodeType, "folders");
}

function isFolderNodeType(nodeType) {
    return equalsString(nodeType, "folder");
}

function getNodeIdAttribute(node) {
    var idString = node.attr("id");
    return parseId(idString);
}

function getNodeId(node) {
    var idString = node.id;
    return parseId(idString);
}