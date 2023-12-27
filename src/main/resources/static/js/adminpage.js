$(document).ready(function(){
    $('[name="detail_tap"]').click(function (){
        checkbtn($(this));
        });
    checkbtn($('#joinlist_btn'));


});

function checkbtn(btn){
    if(btn.is(':checked')){
        btn.parent().css('background-color','white','color','black');
        btn.parent().siblings().css('background-color','grey');
        switch (btn.attr('id')){
            case 'joinlist_btn' :
                $('joindata').css('display', 'flex');
                $('joindata').siblings().css('display', 'none');
                break;
            case 'userlist_btn' :
                $('userlist').css('display', 'flex');
                $('userlist').siblings().css('display', 'none');
                break;
            case 'petdata_btn' :
                $('petdata').css('display', 'flex');
                $('petdata').siblings().css('display', 'none');
                break;
        }
    }
}