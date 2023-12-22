$(document).ready(function (){

    $("#myupdate").click(function (){
        let answer = confirm("수정완료되었습니다.");
        if(answer) {
            $("form[name='frmupdate']").submit();
        }
    });


    $("#addbtn").click(function (){
        // <input type="file" id="inputfile" multiple>)
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

