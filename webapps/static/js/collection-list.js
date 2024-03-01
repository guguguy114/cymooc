let itemNumInOnePage = 3;
let currentPage = 1;

let uid;



function initial() {
    let user = JSON.parse(sessionStorage.getItem("user"));
    if (user !== null) {
        uid = user.uid
        let currentPageCollections = getUserCollections(uid)
        switchPages(currentPage, currentPageCollections)
    }
}

function getUserCollections (uid) {
    let collections
    $.ajax({
        url: baseUrl + "getUserCollections",
        method: "post",
        async: false,
        data: {
            uid: uid,
        },
        dataType: "json",
        success: function (res) {
            collections = res.data
        },
        error: function (res) {
            alert("server error")
        }
    })
    return collections
}

function switchPages (page, courseList) {
    let list = $("#collection-list")
    list.empty()

    if (courseList === undefined) {
        $("#page-btn-div").empty()
        return
    }

    let start = (page - 1) * itemNumInOnePage
    let end = (start + itemNumInOnePage) - 1
    for (let i = start; i <= end; i++) {
        let course = courseList[i]
        if (course !== undefined){
            let courseId = course.courseId

            let item = $("<li class='collection-item'></li>")

            let itemImg = $("<img src='' alt='course-img' class='course-img'>")
            let imgSrc = course.courseImg
            itemImg.attr("src", imgSrc)
            itemImg.on("click", function () {
                parent.location.href = "../../pages/course.html"
                let currentCourse = getCourseDetail(courseId)
                sessionStorage.setItem("current_course", JSON.stringify(currentCourse))
                let chapterList = getCourseChapters(currentCourse.courseId)
                sessionStorage.setItem("current_chapter", JSON.stringify(chapterList[0]))
            })

            let courseTitle = $("<div class='course-title'></div>")
            let courseTitleText = course.courseName
            courseTitle.text(courseTitleText)

            courseTitle.on("click", function () {
                parent.location.href = "../../pages/course.html"
                let currentCourse = getCourseDetail(courseId)
                sessionStorage.setItem("current_course", JSON.stringify(currentCourse))
                let chapterList = getCourseChapters(currentCourse.courseId)
                sessionStorage.setItem("current_chapter", JSON.stringify(chapterList[0]))
            })

            let collectTimeLabel = $("<div class='collect-time-label'>收藏时间:</div>")

            let collectTime = $("<div class=\"collect-time\"></div>")
            let collectTimeS = new Date(course.updateTime)
            collectTime.text(collectTimeS.toString())

            let unCollectBtn = $("<div class=\"cancel-collection-btn\">取消收藏</div>")
            unCollectBtn.on("click", function () {
                window.parent.displayConfirmPan("取消收藏", "确认取消收藏？", function () {
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
                            window.parent.closeAttentionPan()
                            if (res.code === 1) {
                                window.parent.displayAttention("取消收藏", "取消收藏成功")
                                document.location.reload(true)
                            } else {
                                window.parent.displayAttention("取消收藏", "取消收藏失败")
                            }
                        },
                        error: function (res) {
                            alert("server error!")
                        }
                    })
                })




            })

            item.append(itemImg)
            item.append(courseTitle)
            item.append(collectTimeLabel)
            item.append(collectTime)
            item.append(unCollectBtn)
            list.append(item)
        }
    }

    let point1 = $("#point")

    // 内容生成完成，开始生成下标
    $(".num-btn").remove()
    let nextPageBtn = $("#next-page-btn")
    let lastPageBtn = $("#last-page-btn")
    let totalPage = Math.ceil(courseList.length / itemNumInOnePage)
    if (totalPage <= 4){
        point1.remove()
        for (let i = 1; i <= totalPage; i++) {
            addNumBtn(i, nextPageBtn)
            if (currentPage === i) {
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
            if (currentPage === 1 || currentPage === 2) {
                if (currentPage === 2) {
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
            } else if (currentPage === totalPage || currentPage === totalPage - 1) {
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
                    if (currentPage === totalPage) {
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
                        i = currentPage - 2
                        stage++;
                    } else if (stage === 2) {
                        nextPageBtn.before(point2)
                        point2.css("display", "inline-block")
                        i = totalPage - 1
                        stage++
                    }
                }
            }



            if (currentPage === i) {
                $("#num-btn-" + i).attr("disabled", true)
            } else {
                $("#num-btn-" + i).attr("disabled", false)
            }
        }
    }

    nextPageBtn.off("click")
    nextPageBtn.on("click", function () {
        if (currentPage !== totalPage){
            currentPage++;
            switchPages(currentPage, getUserCollections(uid))
        } else {

        }
    })

    lastPageBtn.off("click")
    lastPageBtn.on("click", function () {
        if (currentPage !== 1) {
            currentPage--
            switchPages(currentPage, getUserCollections(uid))
        } else {

        }
    })

    if (currentPage !== totalPage) {
        nextPageBtn.attr("disabled", false)
    } else {
        nextPageBtn.attr("disabled", true)
    }

    if (currentPage !== 1) {
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
        currentPage = parseInt(numBtn.text())
        switchPages(currentPage, getUserCollections(uid))
    })
    rightBtn.before(numBtn)
}