$(document).ready(function (){
    $("#addbtn").click(function (){

        // 업로드 로직을 여기에 추가
        const formData = new FormData(document.getElementById('uploadForm'));

        // 추가적인 업로드 로직 작성
        // ...

        // 예: Ajax로 서버에 업로드 요청 보내기
        const xhr = new XMLHttpRequest();
        xhr.open('POST', '/upload', true);
        xhr.onreadystatechange = function() {
            if (xhr.readyState === 4) {
                if (xhr.status === 200) {
                    alert('파일 업로드 성공: ' + xhr.responseText);
                } else {
                    alert('파일 업로드 실패: ' + xhr.responseText);
                }
            }
        };
        xhr.send(formData);

        // const fileInputs = document.getElementById('fileInputs');
        //
        // // 새로운 파일 선택 버튼 추가
        // const newInput = document.createElement('input');
        // newInput.type = 'file';
        // newInput.name = 'files[]';
        // fileInputs.appendChild(newInput);
    });


    $("#delbtn").click(function (){
        const fileInputs = document.getElementById('fileInputs');

        // 마지막 파일 선택 버튼 삭제
        const lastInput = fileInputs.lastElementChild;
        if (lastInput) {
            fileInputs.removeChild(lastInput);
        }
    });
});


