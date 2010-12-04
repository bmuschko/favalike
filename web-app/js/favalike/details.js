function showFolderDetails() {
    showElement($("#editFolderForm"));
    hideElement($("#editTagForm"));
    hideElement($("#editBookmarkForm"));
}

function showBookmarkDetails() {
    hideElement($("#editFolderForm"));
    hideElement($("#editTagForm"));
    showElement($("#editBookmarkForm"));
}

function hideAllDetails() {
    hideElement($("#editFolderForm"));
    hideElement($("#editTagForm"));
    hideElement($("#editBookmarkForm"));
}