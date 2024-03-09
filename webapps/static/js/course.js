let commentNumInOnePage = 2;
let currentCommentPage = 1;

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


    $("#unlock-btn").on("click", function () {
        let user = JSON.parse(sessionStorage.getItem("user"))

        if (user == null) {
            displayAttention("购买失败", "请先登录")
        } else {
            purchaseCourse();
        }
    })

    switchCommentPage(currentCommentPage)
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

function switchCommentPage (page) {
    let courseInfo = JSON.parse(sessionStorage.getItem("current_course"))
    let commentList = getCourseComment(courseInfo.courseId, commentNumInOnePage, page)

    console.log(commentList)
    let list = $("#comment-list")
    list.empty()

    if (commentList === undefined) {
        $("#comment-page-btn-div").empty()
        return
    }

    for (let i = 0; i <= commentList.length - 1; i++) {
        let comment = commentList[i]
        if (comment !== undefined){
            let uid = comment.uid
            let userInfo = getUserInfo(uid)

            let item = $("<li class='comment-item'></li>")

            let commentImg = $("<img class=\"comment-img\" src=\"../static/images/upload/face-images/default_face_img.png\" alt=\"user-img\">")
            let imgSrc = userInfo.faceImage
            commentImg.attr("src", imgSrc)

            let commentBody = $("<div class='comment-body'></div>")
            let courseTitleText = comment.commentBody
            commentBody.text(courseTitleText)


            let commentTime = $("<div class='comment-submit-time'></div>")
            let collectTimeS = new Date(comment.commentTime)
            commentTime.text(collectTimeS.toString())

            let userName = $("<div class='user-name'></div>")
            userName.text(userInfo.nickname)


            item.append(commentImg)
            item.append(commentBody)
            item.append(commentTime)
            item.append(userName)
            list.append(item)
        }
    }

    let totalCommentNum
    let totalPage

    $.ajax({
        url: baseUrl + "getCourseTotalCommentNum",
        method: "post",
        async: false,
        data: {
            courseId: courseInfo.courseId
        },
        dataType: "json",
        success: function (res) {
            console.log(res)
            totalCommentNum = res.data
        },
        error: function (res) {
            alert("server error!")
        }
    })

    totalPage = Math.ceil(totalCommentNum / commentNumInOnePage)


    // 内容生成完成，开始生成下标
    let point1 = $("#point")
    $(".num-btn").remove()
    let nextPageBtn = $("#next-page-btn")
    let lastPageBtn = $("#last-page-btn")
    if (totalPage <= 4){
        point1.remove()
        for (let i = 1; i <= totalPage; i++) {
            addNumBtn(i, nextPageBtn)
            if (currentCommentPage === i) {
                $("#num-btn-" + i).attr("disabled", true)
            } else {
                $("#num-btn-" + i).attr("disabled", false)
            }
        }
    } else {
        point1.css("display", "none")
        let key = 2;
        let count = 0;
        for (let i = 1; i <= totalPage; i++) {
            if (currentCommentPage === 1 || currentCommentPage === 2) {
                if (currentCommentPage === 2) {
                    key = 3
                }
                if (count !== key) {
                    addNumBtn(i, nextPageBtn)
                    count++;
                } else {
                    nextPageBtn.before(point1)
                    point1.css("display", "inline-block")
                    count = 0
                    key = 1
                    i = totalPage - 1
                }
            } else if (currentCommentPage === totalPage || currentCommentPage === totalPage - 1) {
                if (i === 1) {
                    key = 1
                }
                if (count !== key) {
                    addNumBtn(i, nextPageBtn)
                    count++
                } else {
                    nextPageBtn.before(point1)
                    point1.css("display", "inline-block")
                    count = 0
                    if (currentCommentPage === totalPage) {
                        key = 2
                        i = totalPage - 2
                    } else {
                        key = 3
                        i = totalPage - 3
                    }

                }
            } else {
                let stage = 1;
                let point2 = point1.clone()
                if (i === 1) {
                    key = 1
                    count = 0
                } else if (i === totalPage) {
                    key = 1
                    count = 0
                } else {
                    key = 3
                    count = 0
                }
                if (count !== key) {
                    addNumBtn(i, nextPageBtn)
                    count++
                } else {
                    if (stage === 1) {
                        nextPageBtn.before(point1)
                        point1.css("display", "inline-block")
                        i = currentCommentPage - 2
                        stage++;
                    } else if (stage === 2) {
                        nextPageBtn.before(point2)
                        point2.css("display", "inline-block")
                        i = totalPage - 1
                        stage++
                    }
                }
            }



            if (currentCommentPage === i) {
                $("#num-btn-" + i).attr("disabled", true)
            } else {
                $("#num-btn-" + i).attr("disabled", false)
            }
        }
    }

    nextPageBtn.off("click")
    nextPageBtn.on("click", function () {
        if (currentCommentPage !== totalPage){
            currentCommentPage++;
            switchCommentPage(currentCommentPage)
        } else {

        }
    })

    lastPageBtn.off("click")
    lastPageBtn.on("click", function () {
        if (currentCommentPage !== 1) {
            currentCommentPage--
            switchCommentPage(currentCommentPage)
        } else {

        }
    })

    if (currentCommentPage !== totalPage) {
        nextPageBtn.attr("disabled", false)
    } else {
        nextPageBtn.attr("disabled", true)
    }

    if (currentCommentPage !== 1) {
        lastPageBtn.attr("disabled", false)
    } else {
        lastPageBtn.attr("disabled", true)
    }

}

function addNumBtn (num, rightBtn) {
    let numBtn = $("<button id=\"\" class=\"num-btn\"></button>")
    numBtn.attr("id", "num-btn-" + num)
    numBtn.text(num)
    numBtn.on("click", function () {
        currentCommentPage = parseInt(numBtn.text())
        switchCommentPage(currentCommentPage)
    })
    rightBtn.before(numBtn)
}