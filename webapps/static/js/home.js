let userInfo

function initial() {
    createAttentionPan($("#background"))
    closeAttentionPan()

    let attentionPan = $("#attention-pan")
    let chargeNumInput = $("<input type='number' id='charge-num-input'>")
    let chargeConfirmBtn = $("<button id='charge-confirm-btn'>确定</button>")

    attentionPan.append(chargeNumInput)
    attentionPan.append(chargeConfirmBtn)



    $(".cancel-btn").on("click", function () {
        $("#form-background").css("display", "none")
        $("#form-input-1").val('')
        $("#form-input-2").val('')
        $("#form-input-3").val('')
    })

    $("#attention-cancel-btn").on("click", function () {
        closeAttentionPan()
    })


    $("#logo").on("click", function () {
        window.location.href = baseUrl
    })

    userInfo = JSON.parse(sessionStorage.getItem("user"))








}

function displayChargeInputPage () {
    displayAttention("充值", "请输入充值数额")
    let chargeConfirmBtn = $("#charge-confirm-btn")
    chargeConfirmBtn.off("click")
    chargeConfirmBtn.on("click", function () {
        let num = $("#charge-num-input").val()
        let acc = userInfo.account
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
                iframe[0].contentWindow.updateUserInfo()
                alert("charge successfully, charge num : " + res.data)
                closeAttentionPan()
                balance.html(parseInt(balance.text()) + res.data)
            },
            error: function (res) {
                alert("server error!")
            }
        })
    })
    let chargeNumInput = $("#charge-num-input")
    chargeConfirmBtn.css("display", "inline-block")
    chargeNumInput.css("display", "inline-block")
}

function closeAttentionPan () {
    let chargeNumInput = $("#charge-num-input")
    let chargeConfirmBtn = $("#charge-confirm-btn")
    chargeNumInput.val('')
    chargeConfirmBtn.css("display", "none")
    chargeNumInput.css("display", "none")
    $("#attention-background").css("display", "none")
}

function displayFormPan (title = "", label1 = "", label2 = "", label3 = "", btn1 = "", btn2 = "") {
    let optBtn1 = $("#option-btn-1")
    let optBtn2 = $("#option-btn-2")


    $("#form-title").text(title)
    if (label1 === "") {
        $("#form-input-div-1").css("display", "none")
    }else {
        $("#form-input-div-1").css("display", "inline-block")
    }
    if (label2 === "") {
        $("#form-input-div-2").css("display", "none")
    }else {
        $("#form-input-div-2").css("display", "inline-block")
    }
    if (label3 === "") {
        $("#form-input-div-3").css("display", "none")
    }else {
        $("#form-input-div-3").css("display", "inline-block")
    }
    if (btn1 === "") {
        optBtn1.css("display", "none")
    }else {
        optBtn1.css("display", "inline-block")
    }
    if (btn2 === "") {
        optBtn2.css("display", "none")
    }else {
        optBtn2.css("display", "inline-block")
    }

    $("#form-input-label-1").text(label1)
    $("#form-input-label-2").text(label2)
    $("#form-input-label-3").text(label3)

    optBtn1.val(btn1)
    optBtn2.val(btn2)

    $("#form-background").css("display", "inline-block")
    $("#form-pan").css("display", "inline-block")
}

function closeFormPan () {
    $("#form-input-1").val()
    $("#form-input-2").val()
    $("#form-input-3").val()
}

function displayConfirmPan (title, text, func) {
    closeAttentionPan()
    let confirmBtn = $("#charge-confirm-btn")
    confirmBtn.off("click")
    confirmBtn.on("click", function () {
        func()
        closeAttentionPan()
    })
    confirmBtn.css("display", "inline-block")
    displayAttention(title, text)
}
