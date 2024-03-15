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
            sessionStorage.removeItem("user")
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

    function cancel () {
        $("#login-background").css("display", "none")
        $("#login-pan").css("display", "inline-block")
        $("#register-pan").css("display", "none")
        $("#account-input").val('')
        $("#password-input").val('')
        $("#verify-code-input").val('')
    }

    cancelBtn.click(function () {
        cancel()
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
                            cancel()
                            location.reload(true)
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

function getCourseDetail (courseId) {
    let course
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

function toCoursePage (courseId, targetDocument, chapter = 1) {
    targetDocument.location.href = "../../pages/course.html"
    let currentCourse = getCourseDetail(courseId)
    sessionStorage.setItem("current_course", JSON.stringify(currentCourse))
    let chapterList = getCourseChapters(currentCourse.courseId)
    sessionStorage.setItem("current_chapter", JSON.stringify(chapterList[chapter - 1]))
}

function getCourseComment (courseId, limitNum, page) {
    let commentList
    $.ajax({
        url: baseUrl + "getCourseComments",
        method: "post",
        async: false,
        data: {
            courseId: courseId,
            lim: limitNum,
            page: page
        },
        dataType: "json",
        success: function (res) {
            commentList = res.data
        },
        error: function (res) {
            alert("server error!")
        }
    })
    return commentList
}

function getWatchHistoryList(uid, limitNum, page) {
    let watchHistoryList
    $.ajax({
        url: baseUrl + "getUserWatchHistory",
        method: "post",
        async: false,
        data: {
            uid: uid,
            lim: limitNum,
            page: page
        },
        dataType: "json",
        success: function (res) {
            watchHistoryList = res.data
        },
        error: function (res) {
            alert("server error!")
        }
    })
    return watchHistoryList
}

function getCourseChapter(chapterId) {
    let chapter
    $.ajax({
        url: baseUrl + "getCourseChapter",
        method: "post",
        async: false,
        data: {
            chapterId: chapterId
        },
        dataType: "json",
        success: function (res) {
            chapter = res.data
        },
        error: function (res) {
            alert("server error!")
        }
    })
    return chapter
}

function getUserInfo(uid) {
    let userInfo;
    $.ajax({
        url: baseUrl + "getUserInfo",
        method: "post",
        async: false,
        data: {uid: uid},
        dataType: "json",
        success: function (res) {
            switch (res.code) {
                case 0:
                    alert(res.msg)
                    break;
                case 1:
                    userInfo = res.data
                    break;
            }
        },
        error: function (res) {
            alert("server error!")
        }
    })
    return userInfo;
}

function setSearchFunc() {
    $("#search-button").on("click", function () {
        let searchWord = $("#search-field").val()
        console.log("val:" + searchWord)
        if (searchWord === "") {
            console.log("no values")
            displayAttention("搜索失败", "请输入搜索词")
        } else {
            console.log("search")
            window.location.href = "../pages/search-page.html"
        }
    })
}

function createHeader(background) {
    let backgroundElem = background;

    let header = $("<div id='header'></div>")

    let logo = $("<img src=\"../static/images/logo.jpg\" alt=\"logo\" srcset=\"\" id=\"logo\">")
    let searchLabel = $("<label for=\"search-field\" id=\"search-field-label\">搜索:</label>")
    let searchInput = $("<input type=\"text\" placeholder=\"请输入课程\" id=\"search-field\">")
    let searchBtn = $("<input type=\"button\" id=\"search-button\">")
    let loginBtn = $("<button id=\"login-pan-btn\" >登录</button>")
    let faceImg = $("<img src=\"../static/images/upload/face-images/default_face_img.png\" alt=\"face-img\" id=\"face-img\">")
    let logoutBtn = $("<div id=\"logout-div\">登出</div>")

    header.append(logo)
    header.append(searchLabel)
    header.append(searchInput)
    header.append(searchBtn)
    header.append(loginBtn)
    header.append(faceImg)
    header.append(logoutBtn)

    backgroundElem.prepend(header)
    console.log("create complete")
}

function setHeader(background) {
    createHeader(background)
    judgeLoginState()
    createAttentionPan(background)
    setLoginPan()
    setSearchFunc()
    console.log("setting")
}









/**
 * Description: 负责添加页面的切换按钮部分的方法
 * @return void
 * @author Guguguy
 * @since 2024/3/15 15:05
 * @param itemTotalNum 所有的元素数量，包含当前展示的和未展示的元素总数量
 * @param properties 页面某元素所需的参数对象，它应该包含两个分别命名为currentPage和limitNum的属性
 * @param switchPageFunc 切换页面所需的方法
 */
function setPageBtn (itemTotalNum, properties, switchPageFunc) {

    let totalPage = Math.ceil(itemTotalNum / properties.limitNum)


    // 内容生成完成，开始生成下标
    let point1 = $("#point-1")
    let point2 = $("#point-2")
    $(".num-btn").remove()
    let nextPageBtn = $("#next-page-btn")
    let lastPageBtn = $("#last-page-btn")
    if (totalPage <= 4){
        point1.remove()
        point2.remove()
        for (let i = 1; i <= totalPage; i++) {
            addNumBtn(i, nextPageBtn, properties, switchPageFunc)
            if (properties.currentPage === i) {
                $("#num-btn-" + i).attr("disabled", true)
            } else {
                $("#num-btn-" + i).attr("disabled", false)
            }
        }
    } else {
        point1.css("display", "none")
        point2.css("display", "none")
        let key = 2;
        let count = 0;
        let stage = 0;
        let countState = 0;
        for (let i = 1; i <= totalPage; i++) {
            if (properties.currentPage === 1 || properties.currentPage === 2 || properties.currentPage === 3) {
                if (properties.currentPage === 2) {
                    key = 3
                }
                if (properties.currentPage === 3) {{
                    key = 4
                }}
                if (count !== key) {
                    addNumBtn(i, nextPageBtn, properties, switchPageFunc)
                    count++;
                } else {
                    nextPageBtn.before(point1)
                    point1.css("display", "inline-block")
                    count = 0
                    key = 1
                    i = totalPage - 1
                }
            } else if (properties.currentPage === totalPage || properties.currentPage === totalPage - 1 || properties.currentPage === totalPage - 2) {
                if (i === 1) {
                    key = 1
                }
                if (count !== key) {
                    addNumBtn(i, nextPageBtn, properties, switchPageFunc)
                    count++
                } else {
                    nextPageBtn.before(point1)
                    point1.css("display", "inline-block")
                    count = 0
                    if (properties.currentPage === totalPage) {
                        key = 2
                        i = totalPage - 2
                    } else if (properties.currentPage === totalPage - 1){
                        key = 3
                        i = totalPage - 3
                    } else {
                        key = 4
                        i = totalPage - 4
                    }

                }
            } else {
                if (stage === 0) {
                    stage = 1
                }
                if (i === 1) {
                    key = 1
                    count = 0
                } else if (i === totalPage) {
                    key = 1
                    count = 0
                } else {
                    key = 3
                    if (countState === 0){
                        count = 0
                        countState = 1
                    }
                }
                if (count !== key) {
                    addNumBtn(i, nextPageBtn, properties, switchPageFunc)
                    count++
                    console.log("key : " + key + " count : " + count + " stage : " + stage)
                    if (count === key){
                        if (stage === 1) {
                            nextPageBtn.before(point1)
                            point1.css("display", "inline-block")
                            i = properties.currentPage - 2
                            stage++;
                        } else if (stage === 2) {
                            nextPageBtn.before(point2)
                            point2.css("display", "inline-block")
                            i = totalPage - 1
                            stage++
                        }
                    }
                }
            }


            // 处于当前页面时按钮置灰
            if (properties.currentPage === i) {
                $("#num-btn-" + i).attr("disabled", true)
            } else {
                $("#num-btn-" + i).attr("disabled", false)
            }
        }
    }

    nextPageBtn.off("click")
    nextPageBtn.on("click", function () {
        if (properties.currentPage !== totalPage){
            properties.currentPage++;
            switchPageFunc(properties.currentPage)
        } else {

        }
    })

    lastPageBtn.off("click")
    lastPageBtn.on("click", function () {
        if (properties.currentPage !== 1) {
            properties.currentPage--
            switchPageFunc(properties.currentPage)
        } else {

        }
    })

    if (properties.currentPage !== totalPage) {
        nextPageBtn.attr("disabled", false)
    } else {
        nextPageBtn.attr("disabled", true)
    }

    if (properties.currentPage !== 1) {
        lastPageBtn.attr("disabled", false)
    } else {
        lastPageBtn.attr("disabled", true)
    }
}


/**
 * Description: 一个负责添加页面按钮的方法
 * @return void
 * @author Guguguy
 * @since 2024/3/15 16:34
 * @param num 页面按钮上的数字
 * @param rightBtn 最右端的页面按钮，因为需要将其他的数字按钮从它前面插入
 * @param properties 需要进行分页的页面参数对象，它应该包含命名为currentPage的参数
 * @param switchPage 所依赖的切换页面方法
 */
function addNumBtn (num, rightBtn, properties, switchPage) {
    let numBtn = $("<button id=\"\" class=\"num-btn\"></button>")
    numBtn.attr("id", "num-btn-" + num)
    numBtn.text(num)
    numBtn.on("click", function () {
        properties.currentPage = parseInt(numBtn.text())
        switchPage(properties.currentPage)
    })
    rightBtn.before(numBtn)
}

function addPageBtn (fatherElem) {
    console.log("add")
    console.log(fatherElem)
    let item = $("<div id=\"page-btn-div\">" +
                    "<button id=\"last-page-btn\" class=\"page-btn\">上一页</button>" +
                    "<button id=\"1\" class=\"num-btn\">1</button>" +
                    "<button id=\"2\" class=\"num-btn\">2</button>" +
                    "<div id=\"point-1\">...</div>" +
                    "<div id=\"point-2\">...</div>" +
                    "<button id=\"3\" class=\"num-btn\">3</button>" +
                    "<button id=\"4\" class=\"num-btn\">4</button>" +
                    "<button id=\"next-page-btn\" class=\"page-btn\">下一页</button>" +
                "</div>")
    console.log(item)
    fatherElem.append(item)
}
