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

//
// function commandButtonClick(element){
//     console.log("button clicked");
// }
//
// function commandButtonComplete(element){
//     console.log("button complete");
// }
//
// function commandButtonSubmit(element){
//     console.log("button submit");
// }