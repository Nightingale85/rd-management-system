var editModeClass = ".onEdit";
var showFormButtonID = "#showFormButton";
var addNewItemFormID = "#addNewItemForm";

$(document).ready(function () {
    $(editModeClass).hide();

    gotoViewMode();

    $("#button_edit").on('click', function () {
        gotoEditMode();
    });

    $("#button_close").on('click', function () {
        document.location.href = "/admin/dashboard";
    });

    $("#editProgramButton").on('click', function () {
        document.location.href = "/admin/program";
    });

    $("#button_cancel").on('click', function () {

    });

    $("#button_save").on('click', function () {
        gotoViewMode();
    });

    $(showFormButtonID).on('click', function () {
        $(showFormButtonID).hide();
        $(editModeClass).show();
        $(addNewItemFormID).show();
    });
});

var saveModeClass = ".onSave";

function gotoEditMode() {
    $(editModeClass).show();
    $(saveModeClass).hide();
}

function gotoViewMode() {
    $(editModeClass).hide();
    $(addNewItemFormID).hide();
    $(saveModeClass).show();
}