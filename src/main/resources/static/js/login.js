const signUpButton = document.getElementById('signUp');
const signInButton = document.getElementById('signIn');
const container = document.getElementById('container');

// 박스 이동
signUpButton.addEventListener('click', () => {
    container.classList.add("right-panel-active");
});

signInButton.addEventListener('click', () => {
    container.classList.remove("right-panel-active");
});


// ---------------------이메일 인증 토글-----------------------------------------------
let verificationPopup;

function openVerificationPopup() {
    sendMail();

    try {
        if (!verificationPopup || verificationPopup.closed) {
            // 팝업이 없거나 닫혀있으면 새로운 팝업 열기
            verificationPopup = window.open("", "VerificationPopup", "width=400,height=200");

            // Add HTML content to the popup
            verificationPopup.document.write(`
                <html>
                    <body>
                        <label for="verificationCode">인증 코드:</label>
                        <input type="text" id="verificationCode" name="verificationCode" required/>
                        <button id="confirmBtn">확인</button>
                    </body>
                </html>
            `);

            // 확인 버튼에 대한 클릭 이벤트 핸들러 등록
            verificationPopup.document.getElementById("confirmBtn").addEventListener("click", function () {
                try {
                    const enteredCode = verificationPopup.document.getElementById("verificationCode").value;
                    const storedCode = sessionStorage.getItem('certCode');

                    if (enteredCode === storedCode) {
                        alert("인증이 성공했습니다.");
                    } else {
                        alert("인증이 실패했습니다.");
                    }

                    verificationPopup.close();
                } catch (error) {
                    console.error(error);
                    alert("오류가 발생했습니다.");
                }
            });
        } else {
            // 이미 열려 있는 팝업을 활성화
            verificationPopup.focus();
        }
    } catch (error) {
        alert(error.message);
    }
}


function sendMail(){
    $.ajax({
        url: `/user/rest/login/mailConfirm`, //url
        type: "post",
        data: {
            email: $("#email").val()
        },
        //ajax에 연결이 되면
        success: function (result) {
            console.log(result)

            // sessionStorage.push('certCode', result);
            sessionStorage.setItem('certCode', result);
            // sessionStorage.getItem('certCode');
        },
        error: function (a, b, c) {
            console.error(c);
        },
    });

}
//-----------------------------------------------------------------------------------------


//아이디 중복 검사
$('#userId').on('change',function () {
    $.ajax({
        url: `/user/rest/checkId`, //url
        type: "post",
        data: {
            userId: $("#userId").val()},
        //ajax에 연결이 되면
        success: function (result) {
            if (result){
                console.log('사용가능')
                $('.id-fail-msg').text('');
            }else {
                console.log('사용ㄴㄴ')
                $('.id-fail-msg').text('중복된 아이디 입니다');

            }
        },
        error: function (a, b, c) {
            console.error(c);
        },
    });
});

//이메일 중복 확인
$('#email').on('change',function () {
    $.ajax({
        url: `/user/rest/email`, //url
        type: "post",
        data: {
            email: $("#email").val()},
        //ajax에 연결이 되면
        success: function (result) {
            if (result){
                console.log('사용가능')
                $('.email-fail-msg').text('');
            }else {
                console.log('사용ㄴㄴ')
                $('.email-fail-msg').text('중복된 이메일 입니다');
            }
        },
        error: function (a, b, c) {
            console.error(c);
        },
    });
});

//비밀번호 재입력검사
$('#re_password').on('change',function () {
    if ($('#password').val() != $('#re_password').val()){
        $('.password-fail-msg').text('비밀번호 불일치');
    }else {
        $('.password-fail-msg').text('');
    }
})

//-----------------------양식 안맞으면 가입막기-----------------------------------------------
$("#signUpBtn").on("click", function (e) {
    if ($(".password-fail-msg").text() !== "") {
        e.preventDefault(); // 제출 막기
        alert("양식을 다 써주세요!");
    }
});


// document.getElementById("signUpBtn").addEventListener("click", function (e) {
//     // 유효성 검사
//     const userId = document.getElementById("userId");
//     const password = document.getElementById("password");
//     const rePassword = document.getElementById("re_password");
//     const email = document.getElementById("email");
//     const phone = document.getElementById("phone");
//
//     if (
//         userId.validity.valueMissing ||
//         userId.validity.patternMismatch ||
//         password.validity.valueMissing ||
//         password.validity.patternMismatch ||
//         rePassword.validity.valueMissing ||
//         rePassword.validity.patternMismatch ||
//         email.validity.valueMissing ||
//         email.validity.patternMismatch ||
//         phone.validity.valueMissing ||
//         phone.validity.patternMismatch
//     ) {
//         // 유효성 검사에 실패한 경우
//         alert("양식을 맞춰주세요");
//         e.preventDefault(); // 제출 막기
//     }
// });