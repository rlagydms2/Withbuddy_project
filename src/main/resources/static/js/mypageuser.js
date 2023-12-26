// 문서가 전부 로딩되고 실행

$(document).ready(function (){

    $('#back').click(function (){
        history.back();
    });

    $("#myupdate").click(function (){


        // 사용자가 수정한 정보를 가져옴
        var userId = $("#userId").val();
        var phone = $("#phone").val();
        var email = $("#email").val();
        var addressId = $("#regionbtn").val();


        // 수정된 정보 서버에 전송
        $.ajax({
            type: "POST",    //
            url: "/user/mypageuser",
            data: {
                userId: userId,
                phone: phone,
                email: email,
                addressId: addressId
            },
            success: function (response) {
                alert("정보가 성공적으로 수정되었습니다.");
                window.location.href = "/user/mypage";
            },
            error: function (error) {
                alert("정보수정실패, 입력양식을 확인해주세요.")
            }
        });
        $("#regionbtn").change(function (){
            console.log("값변경 : " + $(this).val());
            $(selectBoxChange).val($(this).val());
        })
        console.log(userId);
        console.log(phone);
        console.log(email);
        console.log(addressId);

    });
});

// 바로실행
(function () {
    const email1 = document.querySelector('#email1');
    const emaillist = document.querySelector('#emailbtn');

    // select 옵션 변경 시
    emaillist.addEventListener('change', function (event) {
        // option 선택 시
        if (event.target.value !== 'type') {
            // 선택한 옵션 input에 입력하고 disabled
            email1.value = event.target.value;
            email1.disabled = true;
        } else { // 직접 입력 시
            // input 내용 초기화 & 입력 가능하도록 변경
            email1.value = '';
            email1.disabled = false;
        }
    });

    // 도메인 직접 입력 or domain option 선택
    const region = document.querySelector('#region');
    const regionlist = document.querySelector('#regionbtn');

    // select 옵션 변경 시
    regionlist.addEventListener('change', function (event) {
        // option 선택 시
        region.value = event.target.value;
        region.disabled = true;
    });
})();
