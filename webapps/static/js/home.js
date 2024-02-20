
function initial() {
    createAttentionPan($("#background"))

    let attentionPan = $("#attention-pan")
    let chargeNumInput = $("<input type='number' id='charge-num-input'>")
    let chargeConfirmBtn = $("<button id='charge-confirm-btn'>确定</button>")

    attentionPan.append(chargeNumInput)
    attentionPan.append(chargeConfirmBtn)

    chargeConfirmBtn = $("#charge-confirm-btn")

    $("#attention-cancel-btn").on("click", function () {
        closeAttentionPan()
    })


    $("#logo").on("click", function () {
        window.location.href = baseUrl
    })

    let userInfo = JSON.parse(sessionStorage.getItem("user"))


    let acc = userInfo.account

    chargeConfirmBtn.on("click", function () {
        let num = $("#charge-num-input").val()
        console.log("acc : " + acc + " " + "num : " + num)
        if (num === "") {
            alert("输入为空请重新输入!")
            return
        }
        $.ajax({
            url: baseUrl + "charge",
            method: "post",
            data: {
                acc: acc,
                num: num
            },
            dataType: "json",
            success: function (res) {
                let iframe = $("#right-frame")
                let balance = iframe.contents().find("#balance")
                console.log(res)
                iframe[0].contentWindow.updateUserInfo()
                alert("charge successfully, charge num : " + res.data)
                closeAttentionPan()
                balance.html(parseInt(balance.text()) + res.data)

            },
            error: function (res) {
                alert("server error!")
            }
        })
        console.log("click")

    })


}

function displayChargeInputPage () {
    displayAttention("充值", "请输入充值数额")
    let chargeNumInput = $("#charge-num-input")
    let chargeConfirmBtn = $("#charge-confirm-btn")
    chargeConfirmBtn.css("display", "inline-block")
    chargeNumInput.css("display", "inline-block")
}

function closeAttentionPan () {
    let chargeNumInput = $("#charge-num-input")
    let chargeConfirmBtn = $("#charge-confirm-btn")
    console.log("click")
    chargeNumInput.val('')
    chargeConfirmBtn.css("display", "none")
    chargeNumInput.css("display", "none")
    $("#attention-background").css("display", "none")

}
