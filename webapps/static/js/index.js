let vc; // 验证码
let currentBannerPage = 1; // 默认轮播图位置
let tik = 1; // 初始tik
let maxBannerNum = 5; // 最大轮播图数量
let maxShowVideoNum = 10;// 最大展示推荐视频数量
function initial() {
    const searchBtn = $("#search-button");
    const loginPanBtn = $("#login-pan-btn");
    const cancelBtn = $(".cancel-btn");
    const loginBtn = $("#login-btn");
    const toRegisterPanBtn = $("#to-register-btn");
    const registerBtn = $("#register-btn");
    const returnToLoginPanBtn = $("#return-to-login-pan-btn");

    judgeLoginState()

    createAttentionPan($("#background"))

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

    // 设置横幅播放
    setInterval(function () {
        if (tik !== 5) { // 5秒轮换一次
            tik++;
        }else {
            if (currentBannerPage === maxBannerNum) {
                currentBannerPage = 0;
            }
            currentBannerPage ++;
            setBannerImg(currentBannerPage)
        }
    }, 1000)

    // 设置横幅下面的点
    for (let i = 1; i <= maxBannerNum; i++) {
        let list = $("#banner-page-points-list");
        let pointNum = i;
        let listItem = $("<li class='banner-page-point-item'></li>");
        let point = $("<div class='banner-page-point'></div>")
        point.attr("id", "banner-page-point" + i);
        point.on("click", function () {
            setBannerImg(i);
        })
        listItem.append(point);
        list.append(listItem)
    }

    getRecommendVideo("like_list")

    $("#banner-page-point1").css("background-color", "white")
    $("#banner-right-btn").on("click", function () {
        if (currentBannerPage === 5) {
            setBannerImg(1);
        } else {
            setBannerImg(currentBannerPage + 1);
        }
    })
    $("#banner-left-btn").on("click", function () {
        if (currentBannerPage === 1) {
            setBannerImg(5);
        } else {
            setBannerImg(currentBannerPage - 1);
        }
    })

    $("#logo").on("click", function () {
        window.location.href = baseUrl
    })

    $("#verify-code-image").on("click", function () {
        updateVerifyCodeImage()
    })

    $(".current-sort-way").on("mouseenter", function () {
        $("#sort-way-content").css("display", "block")
    })

    $("#like").on("click", function () {
        $(".current-sort-way").html($("#like").html())
        $("#sort-way-content").css("display", "none")
        $(".video-list-item").remove()
        getRecommendVideo("like_list")
    })

    $("#comment").on("click", function () {
        $(".current-sort-way").html($("#comment").html())
        $("#sort-way-content").css("display", "none")
        $(".video-list-item").remove()
        getRecommendVideo("comment")
    })

    $("#collection").on("click", function () {
        $(".current-sort-way").html($("#collection").html())
        $("#sort-way-content").css("display", "none")
        $(".video-list-item").remove()
        getRecommendVideo("collection")
    })
}

function setBannerImg (page) {
    let frontImg = $("#banner-img");
    frontImg.fadeToggle("slow", function () {
        frontImg.attr("src", "../static/images/banners/banner" + page + ".jpg")
        for (let i = 1; i <= maxBannerNum; i++) {
            let id = ("#banner-page-point" + i);
            if (currentBannerPage === i) {
                $(id).css("background-color", "white");
            }else {
                $(id).css("background-color", "grey");
            }
        }
        frontImg.fadeToggle("slow");
        frontImg.css("top", 0)
    });
    currentBannerPage = page;
    tik = 0;
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

function getRecommendVideo (func) {
    let recommendVideoNum = 10;
    let data;
    $.ajax({
        url: baseUrl + "getRecommendCourse",
        method: "post",
        data: {
            num: recommendVideoNum,
            func: func
        },
        dataType: "json",
        success: function (res) {
            data = res.data
            for (let i = 1; i <= maxShowVideoNum; i++) {
                let videoList = $("#video-suggest-list")
                let videoLi = $("<li class='video-list-item'></li>")
                let videoDiv = $("<div class='video-div'></div>")
                let imgId = "video-img" + i
                let videoImgDiv = $("<div class='video-img-div'></div>")
                let videoImg = $("<img src='../static/images/upload/course-images/default_video_img.jpg' alt='video-img' class='video-img'> <hr>")
                let titleId = "title" + i
                let videoName = $("<div>this is title</div>")

                videoName.attr("id", titleId);
                videoImg.attr("id", imgId);

                if (i <= data.length) {
                    let course = getCourseDetail(data[i - 1]);
                    videoName.text(course.courseName)
                    videoImg.attr("src", course.courseImg)
                }

                videoDiv.on("click", function () {
                    window.location.href = "../../pages/course.html"
                    let currentCourse = getCourseDetail(data[i - 1])
                    sessionStorage.setItem("current_course", JSON.stringify(currentCourse))
                    let chapterList = getCourseChapters(currentCourse.courseId)
                    sessionStorage.setItem("current_chapter", JSON.stringify(chapterList[0]))
                })

                videoImgDiv.append(videoImg)
                videoDiv.append(videoImgDiv)
                videoDiv.append(videoName)
                videoLi.append(videoDiv)
                videoList.append(videoLi)
            }
        },
        error: function (res) {
            alert("server error!")
        }
    })

    // 设置推荐视频

}

function getCourseDetail (courseId) {
    let course = null;
    $.ajax({
        url: baseUrl + "getCourse",
        method: "post",
        async: false,
        data: {
            id: courseId
        },
        dataType: "json",
        success: function (res) {
            course = res.data
        },
        error: function (res) {
            alert("server error!")
        }
    })
    return course
}