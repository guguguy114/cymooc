var vc;
function initial() {
    const searchBtn = $("#search-button");
    const loginPanBtn = $("#login-pan-btn");
    const cancelBtn = $(".cancel-btn");
    const attentionCancelBtn = $(".attention-cancel-btn");
    const loginBtn = $("#login-btn");
    const toRegisterPanBtn = $("#to-register-btn");
    const registerBtn = $("#register-btn");
    const returnToLoginPanBtn = $("#return-to-login-pan-btn");

    loginPanBtn.click(function () {
        $("#login-background").css("display", "inline-block")
        $.ajax({
            url: baseUrl + "getVerifyCode",
            method: "get",
            dataType: "json",
            success: function (res) {
                if (res.code === 1) {
                    vc = res.msg;
                    console.log(vc)
                    var verifyCodeImg = $("#verify-code-image")
                    verifyCodeImg.attr("src", "../static/images/verifycodes/" + vc + ".jpg");
                }
            },
            error: function (res) {
                alert("server error!")
            }
        })
    })

    cancelBtn.click(function () {
        $("#login-background").css("display", "none")
        $("#login-pan").css("display", "inline-block")
        $("#register-pan").css("display", "none")
        $("#account-input").val('')
        $("#password-input").val('')
        $("#verify-code-input").val('')
    })

    attentionCancelBtn.click(function () {
        $("#attention-background").css("display", "none")
    })

    toRegisterPanBtn.click(function () {
        $("#login-pan").css("display", "none")
        $("#register-pan").css("display", "inline-block")
        // 清除文本框内容
        $("#account-input-r").val('')
        $("#password-input-r").val('')
        $("#password-confirm-input-r").val('')
    })

    registerBtn.click(function () {
        let acc = $("#account-input-r").val()
        let pwd = $("#password-input-r").val()
        let pwdConf = $("#password-confirm-input-r").val()
        if (pwd !== pwdConf) {
            displayAttention("注册失败", "两次密码输入不一致")
        } else {
            $.ajax({
                url: baseUrl + "doRegister",
                method: "post",
                data: {
                    acc: acc,
                    pwd: pwd,
                },
                dataType: "json",
                success: function (res) {
                    console.log(res)
                    switch (res.code) {
                        case 0:
                            displayAttention("注册失败", "发生未知错误");
                            break;
                        case 2:
                            displayAttention("注册失败", "该账号以存在，请重新输入");
                            break;
                        case 1:
                            displayAttention("注册成功", "请返回登录页面登录");
                            $("#login-pan").css("display", "inline-block")
                            $("#register-pan").css("display", "none")
                            // 清除文本框内容
                            $("#account-input").val('')
                            $("#password-input").val('')
                            $("#verify-code-input").val('')
                            break;
                    }
                },
                error: function (res) {
                    alert("server error!")
                }
            })
        }
    })

    returnToLoginPanBtn.click(function () {
        $("#login-pan").css("display", "inline-block")
        $("#register-pan").css("display", "none")
        // 清除文本框内容
        $("#account-input").val('')
        $("#password-input").val('')
        $("#verify-code-input").val('')
        // console.log('clear')
    })

    loginBtn.click(function () {
        let acc = $("#account-input").val();
        let pwd = $("#password-input").val();
        console.log("click")
        $.ajax({
            url: baseUrl + "doLogin",
            method: "post",
            data: {acc: acc, pwd: pwd},
            dataType: "json",
            success: function (res) {
                console.log(res)
                switch (res.code) {
                    case 0:
                        displayAttention("登录失败", "发生未知错误")
                        break;
                    case 1:
                        displayAttention("登录成功", "")
                        break;
                }
            },
            error: function (res) {
                alert("server error!");
            }
        });
    })
}

function displayAttention (title, text) {
    $("#attention-background").css("display", "inline-block")
    $("#attention-title").html(title)
    $("#attention-context").html(text)
}