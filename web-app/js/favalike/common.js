$(document).ready(function() {
    $("#errorMessageDialog").dialog({
        bgiframe: true,
        autoOpen: false,
        modal: true,
        resizable: false,
        buttons: {
            "Ok": function() {
                $(this).dialog("close");
            }
        }
    });
});

function showErrorMessageDialog(message) {
    $("#errorMessage").html(message);

    $("#errorDialog").dialog({
        bgiframe: true,
        autoOpen: false,
        modal: true,
        resizable: false,
        buttons: {
            "Ok": function() {
                $(this).dialog("close");
            }
        }
    });

    $("#errorDialog").dialog("open");
}

function parseId(idString) {
    return idString.substring(idString.lastIndexOf("_") + 1, idString.length);
}

function equalsString(actual, expected) {
    return (actual == expected) ? true : false;
}

function isBlank(value) {
    return (value == null || value == '') ? true : false;
}

function enableElement(element) {
    element.removeAttr("disabled");
}

function disableElement(element) {
    element.attr("disabled", "disabled");
}

function showElement(element) {
    element.css({display: "block"});
}

function hideElement(element) {
    element.css({display: "none"});
}

function clearForm(target) {
    $(':input', target)
        .not(':button, :submit, :reset, :hidden')
        .val('')
        .removeAttr('checked')
        .removeAttr('selected');
}

function formatInputFieldHint() {
    $('[placeholder]').focus(function() {
        var input = $(this);
        if (input.val() == input.attr('placeholder')) {
            input.val('');
            input.removeClass('placeholder');
        }
    }).blur(function() {
        var input = $(this);
        if (input.val() == '') {
            input.addClass('placeholder');
            input.val(input.attr('placeholder'));
        }
    }).blur();
}

function filterInputFieldHintValue(inputField) {
    if(isInputFieldHintNoValue(inputField) == true) {
        inputField.val('');
    }
}

function isInputFieldHintNoValue(inputField) {
    if(inputField.val() == inputField.attr('placeholder')) {
        return new Boolean(true);
    }

    return new Boolean(false);
}