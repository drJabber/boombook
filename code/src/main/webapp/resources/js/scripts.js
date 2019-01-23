$(document).ready(function() {
    $('.ui-panel-selectable').hover(
        function () {
            $(this).toggleClass('ui-state-hover');
        }
    );
});