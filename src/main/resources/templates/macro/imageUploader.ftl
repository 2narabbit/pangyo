<#macro defaultUI img>
    <div>
        <div>
            <input id="uploadImage" type="file">
            <a id="removeImage" href="#">삭제</a>
        </div>

        <div id="imageView">
            <#if img??>
                <img src="${img}" style="max-width:400px">
            </#if>
        </div>
    </div>

    <script type="text/javascript">
        function getImageUrl() {
            var img = $('#imageView > img').attr('src');
            return img == null ? '' : img;
        }

        $("#uploadImage").change(function(){
            var file = this.files[0];

            $.ajax({
                url : '/api/image/' + file.name,
                type : 'POST',
                data : file,
                cache: false,
                processData: false,
                contentType: false,
                success: function(res) {
                    var html = '<img src="/api/image/' + file.name + '" style="max-width:400px">';
                    $('#imageView').html(html);
                },
                error: function(res) {
                    console.log(res);
                    alert('이미지 업로드에 실패했습니다.');
                }
            });
        });

        $('#removeImage').click(function () {
            if(confirm("이미지를 정말 삭제하시겠습니까?")) {
                $('#imageView').html('');
            }
        });
    </script>
</#macro>
