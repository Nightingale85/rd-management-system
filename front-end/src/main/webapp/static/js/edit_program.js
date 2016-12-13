var addModuleFormID = "#addModuleForm";
var addNewModuleButtonID = "#addNewModule";

$(document).ready(function () {
    $(addModuleFormID).hide();

    gotoViewMode();

    $("#button_edit").on('click', function () {
        gotoEditMode();
    });

    $("#button_cancel").on('click', function () {
        location.reload();
    });

    $(addNewModuleButtonID).on('click', function () {
        $(addNewModuleButtonID).hide();
        $(addModuleFormID).show();
    });
});

var editButtonsGroupID = "#editButtonsGroup";
var editProgramFieldsClass =  ".editProgramField";
var viewButtonsGroupID = "#viewButtonsGroup";

function gotoEditMode() {
    $(editButtonsGroupID).hide();
    $(addNewModuleButtonID).show();
    $(viewButtonsGroupID).show();
    $(editProgramFieldsClass).prop('disabled', false);
}

function gotoViewMode() {
    $(editButtonsGroupID).show();
    $(viewButtonsGroupID).hide();
    $(addModuleFormID).hide();
    $(editProgramFieldsClass).prop('disabled', true);
}