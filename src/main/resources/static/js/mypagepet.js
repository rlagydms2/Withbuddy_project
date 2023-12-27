$(document).ready(function (){

    $("#back").click(function (){
        history.back();
    });


    $("#myupdate").click(function (){

        // 사용자가 수정한 정보를 가져옴
        const userId = $("#userId").val();
        const buddyName = $("#buddyName").val();
        const category = $("#category").val();
        const buddyAge = $("#buddyAge").val();
        const buddySex = $("#sebuddy").val();
        const buddyDetail = $("#petdetail").val();
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
                buddyDetail: buddyDetail
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
        console.log(buddyImage);



    });


    // $("#btnAdd").click(function(){
    //     // 기존에 추가된 파일이 있는지 확인
    //     var existingFiles = $("#files").find(".input-group");
    //     if (existingFiles.length === 0) {
    //         // 파일이 없으면 추가
    //         $("#files").append(`
    //             <div class="input-group">
    //                <input style="width: 150px;" class="form-control" type="file" name="buddyFile" accept="image/*"/>
    //                <button type="button" class="btn btn-outline-danger" onclick="$(this).parent().remove()">삭제</button>
    //             </div>
    //         `);
    //     }
    // });

    $("#addbtn").click(function (){


        const inputfile = document.getElementById('inputfile');
        const fileList = document.getElementById('fileList');

        for (var i = 0; i < inputfile.files.length; i++) {
            const imageFile = inputfile.files[i];
            const reader = new FileReader();

            reader.onload = function (e) {
                const img = document.createElement('img');
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

