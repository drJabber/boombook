$(document).ready(function() {
    $('.ui-panel-selectable').hover(
        function () {
            $(this).toggleClass('ui-state-hover');
        }
    );

    $('.ui-panel-selectable p').click(
        function (event) {
            try{
                $(event.target).parent().trigger( "click" );
            }catch(e){
                console.log(e);
            }
        }
    );
});
