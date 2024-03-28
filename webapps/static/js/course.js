let properties = {
    currentPage : 1,
    limitNum : 2,
    lockState: false,
    chapterList: null
}

function initial () {
    addPageBtn($("#comment-part-div"))
    setHeader($("#background"))

    let attentionPan = $("#attention-pan")
    let chargeConfirmBtn = $("<button id='charge-confirm-btn'>确定</button>")

    attentionPan.append(chargeConfirmBtn)


    let lockBackground = $("#lock-state-screen")
    let user = JSON.parse(sessionStorage.getItem("user"));
    let courseInfo = JSON.parse(sessionStorage.getItem("current_course"))
    $("#like-num").text(getLikeNum(courseInfo))
    $("#collect-num").text(getCollectNum(courseInfo))
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

    properties.chapterList = getCourseChapters(courseInfo.courseId)
    let currentChapter = JSON.parse(sessionStorage.getItem("current_chapter"))

    let purchaseState;
    if (user !== null){
        purchaseState = judgeCoursePurchaseState(user.uid, courseInfo.courseId);
        properties.lockState = purchaseState
        if (purchaseState === 0 && currentChapter.chapterOrder !== 1) {
            lockBackground.css("display", "block")
        } else if (purchaseState === 1){
            lockBackground.css("display", "none")
            $("#lock-info-div").css("display", "none")
            console.log("buy")
        } else {
            lockBackground.css("display", "none")
        }
    } else if (currentChapter.chapterOrder === 1){
        lockBackground.css("display", "none")
    } else {
        lockBackground.css("display", "block")
    }


    $("#chapter-video").on("ended", function () {
        if (properties.chapterList[currentChapter.chapterOrder] === undefined) {
        } else {
            sessionStorage.setItem("current_chapter", JSON.stringify(properties.chapterList[currentChapter.chapterOrder]))
            let nextChapter = JSON.parse(sessionStorage.getItem("current_chapter"))
            $("#video-source").attr("src", nextChapter.chapterVideo)
            window.location.reload(true)
        }
    })
    $("#video-source").attr("src", currentChapter.chapterVideo)
    $("#course-title").text(courseInfo.courseName)
    $("#course-description").text(courseInfo.courseDescription)
    $("#price").text(courseInfo.coursePrice)
    for (let i = 0; i <= properties.chapterList.length - 1; i++) {
        let chapter = properties.chapterList[i]
        let chapterDivList = $("#chapter-list")
        let chapterLi = $("<li class='chapter-item'></li>")

        let chapterDiv = $("<div class='chapter-div'></div>")
        if (currentChapter.chapterId === chapter.chapterId) {
            chapterLi.css("background-color", "rgba(196, 97, 61, 0.75)")
            chapterDiv.css("color", "white")
        }
        if (!purchaseState && i !== 0){
            let lockImg = $("<img class='lock-icon' alt='lock-icon' src='../static/images/icons/lock.png'>")
            chapterLi.append(lockImg)
        }
        chapterDiv.text(chapter.chapterName)
        chapterDiv.attr("title", chapter.chapterName)
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
                    $("#like-num").text(getLikeNum(course))
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
                    $("#collect-num").text(getCollectNum(course))
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
        purchaseCourse()
    })

    switchCommentPage(properties.currentPage)

    $("#submit-comment-btn").on("click", function () {
        let commentString = $("#comment-input").val()

        if (user === null) {
            displayAttention("错误", "请先登录")
        } else {
            if (commentString === "") {
                displayAttention("评论失败", "不能发送空评论")
            } else {
                $.ajax({
                    url: baseUrl + "submitComment",
                    method: "post",
                    dataType: "json",
                    data: {
                        comment: commentString,
                        uid: user.uid,
                        courseId: courseInfo.courseId
                    },
                    success: function (res) {
                        $("#comment-input").val("")
                        properties.currentPage = 1
                        $("#page-btn-div").remove()
                        addPageBtn($("#comment-part-div"))
                        switchCommentPage(properties.currentPage)
                        displayAttention("发表评论", "发表评论成功")
                    },
                    error: function () {
                        alert("server error!")
                    }
                })
            }
        }
    })


    user = JSON.parse(sessionStorage.getItem("user"))
    courseInfo = JSON.parse(sessionStorage.getItem("current_course"))
    let chapterInfo = JSON.parse(sessionStorage.getItem("current_chapter"))
    if (user !== null && courseInfo !== null && chapterInfo !== null) {
        let uid = user.uid
        let courseId = courseInfo.courseId
        let chapterId = chapterInfo.chapterId
        if (judgeCoursePurchaseState(uid, courseId) === 1){
            $.ajax({
                url: baseUrl + "addWatchHistory",
                method: "post",
                data: {
                    uid: uid,
                    courseId: courseId,
                    chapterId: chapterId
                },
                dataType: "json",
                success: function (res) {
                },
                error: function (res) {
                    alert("server error!")
                }
            })
        }
    }

    $("#course-lock-img").on("click", function () {
        purchaseCourse()
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



function purchaseCourse () {
    let user = JSON.parse(sessionStorage.getItem("user"))
    let course = JSON.parse(sessionStorage.getItem("current_course"))
    if (user == null) {
        displayAttention("购买失败", "请先登录")
    } else {
        displayConfirmPan("购买课程", "是否确定购买,价格为：" + course.coursePrice, purchase)
    }
}

function switchCommentPage (page) {
    let courseInfo = JSON.parse(sessionStorage.getItem("current_course"))
    let commentList = getCourseComment(courseInfo.courseId, properties.limitNum, page)

    let list = $("#comment-list")
    list.empty()

    if (commentList === undefined) {
        $("#page-btn-div").empty()
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
            setTitle(userName, userInfo.nickname, 8)


            let currentUser = JSON.parse(sessionStorage.getItem("user"))
            if (currentUser !== null){
                if (uid === currentUser.uid) {
                    let deleteBtn = $("<div class='delete-comment-btn'></div>")
                    let deleteIcon = $("<img alt='delete-icon' class='delete-icon' src='../static/images/icons/cancel.png'>")
                    deleteBtn.on("click", function () {
                        $.ajax({
                            url: baseUrl + "deleteComment",
                            method: "post",
                            dataType: "json",
                            data: {
                                courseId: courseInfo.courseId,
                                uid: currentUser.uid,
                                commentId: comment.commentId
                            },
                            success: function (res) {
                                if (res.code === 1){
                                    displayAttention("删除评论", "删除成功")
                                    switchCommentPage(page)
                                } else {
                                    displayAttention("删除评论", "删除失败")
                                }
                            },
                            error: function () {
                                alert("server error!")
                            }
                        })
                    })
                    deleteBtn.append(deleteIcon)
                    item.append(deleteBtn)
                }
            }


            item.append(commentImg)
            item.append(commentBody)
            item.append(commentTime)
            item.append(userName)
            list.append(item)
        }
    }

    let totalCommentNum

    $.ajax({
        url: baseUrl + "getCourseTotalCommentNum",
        method: "post",
        async: false,
        data: {
            courseId: courseInfo.courseId
        },
        dataType: "json",
        success: function (res) {
            totalCommentNum = res.data
        },
        error: function (res) {
            alert("server error!")
        }
    })

    setPageBtn(totalCommentNum, properties, switchCommentPage)
}

function purchase() {
    let user = JSON.parse(sessionStorage.getItem("user"))
    let course = JSON.parse(sessionStorage.getItem("current_course"))
    let courseId = course.courseId
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
                displayAttention("购买成功", "购买成功2秒后刷新界面")
                setTimeout(function () {
                    location.reload(true)
                }, 2000)
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
