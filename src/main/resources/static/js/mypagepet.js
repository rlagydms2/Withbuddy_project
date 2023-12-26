$(document).ready(function (){

    $("#back").click(function (){
        history.back();
    });


    $("#myupdate").click(function (){

        // 사용자가 수정한 정보를 가져옴
        var userId = $("#userId").val();
        var buddyName = $("#petname").val();
        var category = $("#category").val();
        var buddyAge = $("#petage").val();
        var buddySex = $("#sebuddy").val();
        var buddyDetail = $("#petdetail").val();
        const id=loginId;


        // 수정된 정보 서버에 전송
        $.ajax({
            type: "POST",    //
            url: "/user/mypagepet",
            data: {
                id: id,
                buddyName: buddyName,
                category: category,
                buddyAge: buddyAge,
                buddySex: buddySex,
                buddyDetail: buddyDetail,
            },
            success: function (response) {
                console.log("response: ",response);
                alert("수정완료 되었습니다.")
                location.href = '/user/mypage';
            }
        });
        // $("#regionbtn").change(function (){
        //     console.log("값변경 : " + $(this).val());
        //     $("#cahngeInput").val($(this).val());
        // })
        console.log(userId);
        console.log(buddyName);
        console.log(category);
        console.log(buddyAge);
        console.log(buddySex);
        console.log(buddyDetail);

    });


    $("#addbtn").click(function (){
        // `(<input type="file" id="inputfile" multiple>)`

        var inputfile = document.getElementById('inputfile');
        var fileList = document.getElementById('fileList');

        for (var i = 0; i < inputfile.files.length; i++) {
            var imageFile = inputfile.files[i];
            var reader = new FileReader();

            reader.onload = function (e) {
                var img = document.createElement('img');
                img.src = e.target.result;
                img.classList.add('preview-image');
                fileList.appendChild(img);

                $("#delbtn").click(function() {
                    img.addEventListener('click', function () {
                        this.parentNode.removeChild(this);
                    });

                    fileList.appendChild(img);
                });
            };

            reader.readAsDataURL(imageFile);
        }

        inputfile.value = ''; // 파일 입력 필드 초기화
    });


});

