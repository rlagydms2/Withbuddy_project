$(function(){
    // TODO
    // 검증코드등...

    // [추가] 버튼 누르면 첨부파일 추가
    $("#btnAdd").click(function(){
        // 기존에 추가된 파일이 있는지 확인
        var existingFiles = $("#files").find(".input-group");
        if (existingFiles.length === 0) {
            // 파일이 없으면 추가
            $("#files").append(`
                <div class="input-group">
                   <input style="width: 150px;" class="form-control" type="file" name="buddyFile" accept="image/*"/>
                   <button type="button" class="btn btn-outline-danger" onclick="$(this).parent().remove()">삭제</button>
                </div>
            `);
        }
    });

    // Summernote 추가
    $("#content").summernote({
        height: 300,
    });
});

// //이건 여러개
// $(function(){
//     // TODO
//     // 검증코드등...
//
//     // [추가] 버튼 누르면 첨부파일 추가
//     var i = 0;
//     $("#btnAdd").click(function(){
//         $("#files").append(`
//             <div class="input-group">
//                <input style="width: 200px;" class="form-control" type="file" name="upfile${i}"/>
//                <button type="button" class="btn btn-outline-danger" onclick="$(this).parent().remove()">삭제</button>
//             </div>
//         `);
//         i++;
//     });
//
//     // Summernote 추가
//     $("#content").summernote({
//         height: 300,
//     });
// });


