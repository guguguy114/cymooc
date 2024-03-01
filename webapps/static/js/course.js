

function initial () {

    judgeLoginState()
    createAttentionPan($("#background"))
    setLoginPan()
    getLikeNum()
    getCollectNum()

    let lockBackground = $("#lock-state-screen")
    let user = JSON.parse(sessionStorage.getItem("user"));
    let courseInfo = JSON.parse(sessionStorage.getItem("current_course"))
    console.log(courseInfo)
    if (user !== null){
        let purchaseState = judgeCoursePurchaseState(user.uid, courseInfo.courseId);
        if (purchaseState) {
            lockBackground.css("display", "none")
        } else {
            lockBackground.css("display", "block")
        }
    } else {
        lockBackground.css("display", "block")
    }
    let collectBtn = $("#collect-btn-div")
    let likeState = getLikeState()
    let likeBtn = $("#like-btn-div")
    if (likeState === 1) {
        likeBtn.css("background-color", "blueviolet")
        likeBtn.css("color", "white")
    } else {
        likeBtn.css("background-color", "white")
        likeBtn.css("color", "black")
    }

    let chapterList = getCourseChapters(courseInfo.courseId)
    let currentChapter = JSON.parse(sessionStorage.getItem("current_chapter"))
    $("#video-source").attr("src", currentChapter.chapterVideo)
    $("#course-title").text(courseInfo.courseName)
    $("#course-description").text(courseInfo.courseDescription)
    for (i = 0; i <= chapterList.length - 1; i++) {
        let chapter = chapterList[i]
        console.log(chapter)
        let chapterDivList = $("#chapter-list")
        let chapterLi = $("<li class='chapter-item'></li>")
        let chapterDiv = $("<div class='chapter-div'></div>")
        chapterDiv.text(chapter.chapterName)
        chapterDiv.on("click", function () {
            sessionStorage.setItem("current_chapter", JSON.stringify(chapter))
            let currentChapter = JSON.parse(sessionStorage.getItem("current_chapter"))
            $("#video-source").attr("src", currentChapter.chapterVideo)
            window.location.reload(true)
        })
        chapterLi.append(chapterDiv)
        chapterDivList.append(chapterLi)
    }


    likeBtn.on("mouseover", function () {
        likeBtn.css("background-color", "rgba(106, 20, 155, 0.5)")
        likeBtn.css("color", "white")
    })

    likeBtn.on("mouseout", function () {
        if (likeState === 1) {
            likeBtn.css("background-color", "blueviolet")
            likeBtn.css("color", "white")
        } else {
            likeBtn.css("background-color", "white")
            likeBtn.css("color", "black")
        }
    })

    collectBtn.on("mouseover", function () {
        collectBtn.css("background-color", "rgba(106, 20, 155, 0.5)")
        collectBtn.css("color", "white")
    })

    collectBtn.on("mouseout", function () {
        if (collectState === 1) {
            collectBtn.css("background-color", "blueviolet")
            collectBtn.css("color", "white")
        } else {
            collectBtn.css("background-color", "white")
            collectBtn.css("color", "black")
        }
    })

    likeBtn.on("click", function () {

        let user = JSON.parse(sessionStorage.getItem("user"))
        let uid
        let course = JSON.parse(sessionStorage.getItem("current_course"))
        let courseId = course.courseId;
        if (user === null) {
            console.log("no login")
            displayAttention("点赞失败", "请先登录")
        } else {
            uid = user.uid
            $.ajax({
                url: baseUrl + "like",
                method: "post",
                async: false,
                data: {
                    id: uid,
                    courseId: courseId
                },
                dataType: "json",
                success: function (res) {
                    likeState = getLikeState()
                    getLikeNum()
                    if (likeState === 1) {
                        likeBtn.css("background-color", "blueviolet")
                        likeBtn.css("color", "white")
                    } else {
                        likeBtn.css("background-color", "white")
                        likeBtn.css("color", "black")
                    }
                },
                error: function (res) {
                    alert("server error!")
                }
            })
        }
    })

    let collectState = getCollectState();

    if (collectState === 1) {
        collectBtn.css("background-color", "blueviolet")
        collectBtn.css("color", "white")
    } else {
        collectBtn.css("background-color", "white")
        collectBtn.css("color", "black")
    }

    collectBtn.on("click", function () {
        let user = JSON.parse(sessionStorage.getItem("user"))
        let uid
        let course = JSON.parse(sessionStorage.getItem("current_course"))
        let courseId = course.courseId;
        if (user === null) {
            console.log("no login")
            displayAttention("收藏失败", "请先登录")
        } else {
            uid = user.uid
            $.ajax({
                url: baseUrl + "collection",
                method: "post",
                async: false,
                data: {
                    uid: uid,
                    courseId: courseId
                },
                dataType: "json",
                success: function (res) {
                    collectState = getCollectState()
                    getCollectNum()
                    if (collectState === 1) {
                        collectBtn.css("background-color", "blueviolet")
                        collectBtn.css("color", "white")
                    } else {
                        collectBtn.css("background-color", "white")
                        collectBtn.css("color", "black")
                    }
                },
                error: function (res) {
                    alert("server error!")
                }
            })
        }
    })


    let unlockBtn = $("#unlock-btn").on("click", function () {
        let user = JSON.parse(sessionStorage.getItem("user"))

        if (user == null) {
            displayAttention("购买失败", "请先登录")
        } else {
            purchaseCourse();
        }
    })


}

function getLikeState() {
    let user = JSON.parse(sessionStorage.getItem("user"))
    let course = JSON.parse(sessionStorage.getItem("current_course"))
    let uid;
    let courseId = course.courseId;
    let state
    if (user !== null) {
        uid = user.uid
        $.ajax({
            url: baseUrl + "getCurrentCourseLikeState",
            method: "post",
            async: false,
            data: {
                uid: uid,
                courseId: courseId
            },
            dataType: "json",
            success: function (res) {
                state = res.data
            },
            error: function () {
                alert("server error!")
            }
        })
    } else {
        console.log("no login")
    }
    return state
}

function getCollectState () {
    let user = JSON.parse(sessionStorage.getItem("user"))
    let course = JSON.parse(sessionStorage.getItem("current_course"))
    let uid;
    let courseId = course.courseId
    let state
    if (user !== null){
        uid = user.uid
        $.ajax({
            url: baseUrl + "getCurrentCollectState",
            method: "post",
            async: false,
            data: {
                uid: uid,
                courseId: courseId
            },
            dataType: "json",
            success: function (res) {
                state = res.data
            },
            error: function (res) {
                alert("server error!")
            }
        })
    }
    return state;
}

function getLikeNum () {
    let course = JSON.parse(sessionStorage.getItem("current_course"))
    let courseId = course.courseId
    $.ajax({
        url: baseUrl + "getLikeNum",
        method: "post",
        data: {
            courseId: courseId
        },
        dataType: "json",
        success: function (res) {
            $("#like-num").text(res.data)
        },
        error: function (res) {
            alert("server error!")
        }
    })
}

function getCollectNum () {
    let course = JSON.parse(sessionStorage.getItem("current_course"))
    let courseId = course.courseId
    $.ajax({
        url: baseUrl + "getCollectNum",
        method: "post",
        data: {
            courseId: courseId
        },
        dataType: "json",
        success: function (res) {
            $("#collect-num").text(res.data)
        },
        error: function (res) {
            alert("server error!")
        }
    })
}

function purchaseCourse () {
    let course = JSON.parse(sessionStorage.getItem("current_course"))
    let courseId = course.courseId
    let user = JSON.parse(sessionStorage.getItem("user"))
    let uid = user.uid
    let lockBackground = $("#lock-state-screen")

    $.ajax({
        url: baseUrl + "purchaseCourse",
        method: "post",
        data: {
            uid: uid,
            courseId: courseId
        },
        dataType: "json",
        success: function (res) {
            if (res.code === 1) {
                lockBackground.css("display", "none")
                displayAttention("购买成功", "")
            } else if (res.code === 2) {
                displayAttention("购买失败", "余额不足")
            } else if (res.code === 3) {
                displayAttention("购买失败", "你已购买此课程，请勿重复购买")
            }
        },
        error: function (res) {
            alert("server error!")
        }
    })
}