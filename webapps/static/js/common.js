// 共有方法和公共变量
const baseUrl = "http://127.0.0.1:9999/";

function createAttentionPan (background) {
    let attentionPan = $("<div id=\"attention-background\"><div id=\"attention-page\"><div id=\"attention-pan\"><div id=\"attention-head\"><div id=\"attention-title\">这是标题</div><img src=\"../static/images/icons/cancel.png\" alt=\"cancel\" class='attention-cancel-btn' id='attention-cancel-btn'></div><hr><div id=\"attention-context\">这是正文</div></div></div></div>")
    background.append(attentionPan);
    $(".attention-cancel-btn").on("click", function () {
        console.log("click")
        $("#attention-background").css("display", "none")
    })
}

function displayAttention (title, text) {
    $("#attention-background").css("display", "inline-block")
    $("#attention-title").html(title)
    $("#attention-context").html(text)
}