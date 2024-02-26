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

function judgeLoginState () {
    let userInfo = JSON.parse(sessionStorage.getItem("user"))
    let faceImg = $("#face-img");
    let logoutBtn = $("#logout-div")

    if (userInfo !== null) {
        logoutBtn.on("click", function () {
            sessionStorage.clear()
            window.location.reload(true)
        })
        logoutBtn.css("display", "inline-block")
        faceImg.on("click", function () {
            window.location.href = "../../pages/home.html"
        })

        faceImg.attr("src", userInfo.faceImage)
        faceImg.css("display", "inline-block")
        $("#login-pan-btn").css("display", "none")
    }

    $("#logo").on("click", function () {
        window.location.href = baseUrl
    })
}

function getCourseChapters (courseId) {
    let chapterList;
    $.ajax({
        url: baseUrl + "getCourseChapters",
        method: "post",
        async: false,
        data: {
            id: courseId
        },
        dataType: "json",
        success: function (res) {
            chapterList = res.data
        },
        error: function (res) {
            alert("server error!")
        }
    })
    return chapterList
}

function setLoginPan () {
    const searchBtn = $("#search-button");
    const loginPanBtn = $("#login-pan-btn");
    const cancelBtn = $(".cancel-btn");
    const loginBtn = $("#login-btn");
    const toRegisterPanBtn = $("#to-register-btn");
    const registerBtn = $("#register-btn");
    const returnToLoginPanBtn = $("#return-to-login-pan-btn");

    loginPanBtn.click(function () {
        $("#login-background").css("display", "inline-block")
        updateVerifyCodeImage()
    })

    cancelBtn.click(function () {
        $("#login-background").css("display", "none")
        $("#login-pan").css("display", "inline-block")
        $("#register-pan").css("display", "none")
        $("#account-input").val('')
        $("#password-input").val('')
        $("#verify-code-input").val('')
    })



    toRegisterPanBtn.click(function () {
        $("#login-pan").css("display", "none")
        $("#register-pan").css("display", "inline-block")
        // 清除文本框内容
        $("#account-input-r").val('')
        $("#password-input-r").val('')
        $("#password-confirm-input-r").val('')
    })

    $("#verify-code-image").on("click", function () {
        updateVerifyCodeImage()
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
        let vcPut = $("#verify-code-input").val().toUpperCase();

        vcPut = md5(vcPut);
        console.log("vc : " + vcPut)

        if (vcPut === vc) {
            $.ajax({
                url: baseUrl + "doLogin",
                method: "post",
                data: {acc: acc, pwd: pwd},
                dataType: "json",
                success: function (res) {
                    console.log(res)
                    switch (res.code) {
                        case 0:
                            displayAttention("登录失败", "密码或账号有误")
                            break;
                        case 1:
                            sessionStorage.setItem("user", JSON.stringify(res.data));
                            window.location.href = "../../pages/home.html"
                            break;
                    }
                },
                error: function (res) {
                    alert("server error!");
                }
            });
        }else if (vcPut === ""){
            displayAttention("登录失败", "验证码为空")
        }else {
            displayAttention("登录失败", "验证码错误")

        }
        console.log("click")
    })
}

function updateVerifyCodeImage () {
    $.ajax({
        url: baseUrl + "getVerifyCode",
        method: "get",
        dataType: "json",
        success: function (res) {
            if (res.code === 1) {
                vc = res.msg;
                var verifyCodeImg = $("#verify-code-image")
                verifyCodeImg.attr("src", "../static/images/verifycodes/" + vc + ".jpg");
            }
        },
        error: function (res) {
            alert("server error!")
        }
    })
}

function judgeCoursePurchaseState (uid, courseId) {
    let state;
    $.ajax({
        url: baseUrl + "getPurchaseState",
        method: "post",
        async: false,
        data: {
            uid: uid,
            courseId: courseId
        },
        dataType: "json",
        success: function (res) {
            state = res.data;
        },
        error: function (res) {
            alert("sever error!")
        }
    })
    return state;
}