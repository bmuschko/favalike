var default_offset = 0;
var default_max = 10;

function getLastOffset(total, max) {
    return (getTotalPages(total, max) - 1) * max;
}

function getTotalPages(total, max) {
    var pageCount = parseInt(total / max);

    if(total > max * pageCount) {
        pageCount++;
    }

    return pageCount;
}