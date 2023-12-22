// 문서가 전부 로딩되고 실행
$(document).ready(function (){
    $("#myupdate").click(function (){
        let answer = confirm("수정완료되었습니다.");
        if(answer) {
            $("form[name='frmupdate']").submit();
        }
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
