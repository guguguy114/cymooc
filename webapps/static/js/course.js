function initial () {
    judgeLoginState()
    createAttentionPan($("#background"))
    getLikeNum()
    let likeState = getLikeState()
    let likeBtn = $("#like-btn-div")
    if (likeState === 1) {
        likeBtn.css("background-color", "blueviolet")
        likeBtn.css("color", "white")
    } else {
        likeBtn.css("background-color", "white")
        likeBtn.css("color", "black")
    }
    let courseInfo = JSON.parse(sessionStorage.getItem("current_course"))
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

