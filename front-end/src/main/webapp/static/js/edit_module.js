var addTopicForm = "#addTopicForm";
var addNewTopicButton = "#addNewTopic";
var saveNewTopicButton = "#saveNewTopic";

$(document).ready(function () {
    $(addTopicForm).hide();
    $(saveNewTopicButton).hide();

    $(addNewTopicButton).on('click', function () {
        $(addNewTopicButton).hide();
        $(saveNewTopicButton).show();
        $(addTopicForm).show();
    });
});