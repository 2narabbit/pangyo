<#macro defaultCSS>
    <style>
        .pollArea {
            color: black;
            float: right;
        }
        .pollArea.polled {
            color: red;
        }
    </style>
</#macro>

<#macro defaultScript contentType>
<script type="text/javascript">
    function dontPoll(contentId, $pollArea) {
        $.ajax({
            url : '/api/poll/${contentType!}/' + contentId,
            type : 'DELETE',
            contentType : "application/json",
            success: function() {
                var $pollCount = $pollArea.find('.pollCount');
                $pollCount.text($pollCount.text()*1-1);
                $pollArea.removeClass('polled');
            },
            error: function(res) {
                console.log(res);
                alert('투표 취소에 실패했습니다.');
            }
        });
    }

    function doPoll(contentId, $pollArea) {
        $.ajax({
            url : '/api/poll/${contentType!}/' + contentId,
            type : 'POST',
            contentType : "application/json",
            success: function() {
                var $pollCount = $pollArea.find('.pollCount');
                $pollCount.text($pollCount.text()*1+1);
                $pollArea.addClass('polled');
            },
            error: function(res) {
                console.log(res);
                res = res.responseJSON;
                if (res.message) {
                    alert(res.message);
                } else {
                    alert('투표에 실패했습니다.');
                }
            }
        });
    }

    function poll(contentId, _this) {
        if ($(_this).hasClass('polled')) {
            dontPoll(contentId, $(_this));
        } else {
            doPoll(contentId, $(_this));
        }
    }
</script>
</#macro>
