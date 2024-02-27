function initial () {
    $("#reset-pwd-btn").on("click", function () {
        window.parent.displayFormPan("修改密码", "旧密码:", "新密码:", "确认新密码:", "确认修改")
        $(window.parent.document).contents().find("#form-input-1").attr("type", "password")
        $(window.parent.document).contents().find("#form-input-2").attr("type", "password")
        $(window.parent.document).contents().find("#form-input-3").attr("type", "password")

        $(window.parent.document).contents().find("#option-btn-1").on("click", function () {

            let userInfo = JSON.parse(sessionStorage.getItem("user"))
            let pwdO = userInfo.password
            let pwdN = $(window.parent.document).contents().find("#form-input-2").val()
            let pwdNR = $(window.parent.document).contents().find("#form-input-3").val()
            let pwdOInput = $(window.parent.document).contents().find("#form-input-1").val()
            let acc = userInfo.account

            pwdOInput = md5(pwdOInput)
            pwdN = md5(pwdN)
            pwdNR = md5(pwdNR)


            $(window.parent.document).contents().find("#charge-num-input").css("display", "none")
            $(window.parent.document).contents().find("#charge-confirm-btn").css("display", "none")

            if (pwdO !== pwdOInput) {
                window.parent.displayAttention("修改失败", "输入的旧密码不一致")
            } else if (pwdN === pwdO) {
                window.parent.displayAttention("修改失败", "新密码不能与旧密码相同")
            } else if (pwdN !== pwdNR) {
                window.parent.displayAttention("修改失败", "新密码两次输入不一致")
            } else {
                if (pwdN === pwdNR) {
                    $.ajax({
                        url: baseUrl + "changeInfo",
                        method: "post",
                        data: {
                            type: "password",
                            info: pwdN,
                            acc: acc
                        },
                        dataType: "json",
                        success: function (res) {
                            if (res.code === 1) {
                                window.parent.displayAttention("修改成功", "密码已重置")

                                $.ajax({
                                    url: baseUrl + "getUserInfo",
                                    method: "post",
                                    data: {uid: userInfo.uid},
                                    dataType: "json",
                                    success: function (res) {
                                        switch (res.code) {
                                            case 0:
                                                alert(res.msg)
                                                break;
                                            case 1:
                                                sessionStorage.setItem("user", JSON.stringify(res.data))
                                                break;
                                        }
                                    },
                                    error: function (res) {
                                        alert("server error!")
                                    }
                                })
                                window.parent.closeFormPan()

                            }
                        },
                        error: function (res) {
                            alert("server error!")
                        }
                    })
                }
            }
        })
    })
    $("#reset-nickname-btn").on("click", function () {
        window.parent.displayFormPan("修改昵称", "新昵称", "", "", "确认修改")
        $(window.parent.document).contents().find("#form-input-1").attr("type", "text")

        $(window.parent.document).contents().find("#option-btn-1").on("click", function () {
            let userInfo = JSON.parse(sessionStorage.getItem("user"))
            let nameN = $(window.parent.document).contents().find("#form-input-1").val()
            console.log(nameN)
            let acc = userInfo.account


            $(window.parent.document).contents().find("#charge-num-input").css("display", "none")
            $(window.parent.document).contents().find("#charge-confirm-btn").css("display", "none")

            if (userInfo.nickname === nameN) {
                window.parent.displayAttention("修改失败", "新昵称与原来一致")
            } else {
                $.ajax({
                    url: baseUrl + "changeInfo",
                    method: "post",
                    data: {
                        type: "nickname",
                        info: nameN,
                        acc: acc
                    },
                    dataType: "json",
                    success: function (res) {
                        if (res.code === 1) {
                            window.parent.displayAttention("修改成功", "昵称已重置")
                            $.ajax({
                                url: baseUrl + "getUserInfo",
                                method: "post",
                                data: {acc: userInfo.account},
                                dataType: "json",
                                success: function (res) {
                                    switch (res.code) {
                                        case 0:
                                            alert(res.msg)
                                            break;
                                        case 1:
                                            sessionStorage.setItem("user", JSON.stringify(res.data))
                                            break;
                                    }
                                },
                                error: function (res) {
                                    alert("server error!")
                                }
                            })
                            window.parent.closeFormPan()
                        }
                    },
                    error: function (res) {
                        alert("server error!")
                    }
                })
            }
        })

    })
}