let currentWatchHistoryPage = 1;
let watchHistoryNumInOnePage = 2;

function initial() {
    switchWatchHistoryPage(currentWatchHistoryPage)
}

function switchWatchHistoryPage (page) {
    let userInfo = JSON.parse(sessionStorage.getItem("user"))
    let watchHistoryList = getWatchHistoryList(userInfo.uid, watchHistoryNumInOnePage, page)

    let list = $("#history-list")
    list.empty()

    if (watchHistoryList === undefined) {
        $("#comment-page-btn-div").empty()
        return
    }

    for (let i = 0; i <= watchHistoryList.length - 1; i++) {
        let watchHistory = watchHistoryList[i]
        if (watchHistory !== undefined){
            let courseInfo = getCourseDetail(watchHistory.courseId)
            let courseId = courseInfo.courseId
            let chapterInfo = getCourseChapter(watchHistory.chapterId)
            let chapterOrder = chapterInfo.chapterOrder

            let item = $("<li class='history-item'></li>")

            let historyImg = $("<img src=\"../static/images/upload/face-images/default_face_img.png\" alt=\"course_img\" class=\"course-img\">")
            let imgSrc = courseInfo.courseImg
            historyImg.attr("src", imgSrc)
            historyImg.on("click", function () {
                toCoursePage(courseId, parent, chapterOrder)
            })

            let courseTitle = $("<div class=\"course-title\">这是视频标题</div>")
            let courseTitleText = courseInfo.courseName
            courseTitle.text(courseTitleText)
            courseTitle.on("click", function () {
                toCoursePage(courseId, parent, chapterOrder)
            })

            let chapterLabel = $("<div class=\"chapter-label\">观看至章节:</div>")

            let chapterName = $("<div class=\"chapter-name\">这是章节名称</div>")
            let chapterNameText = chapterInfo.chapterName
            chapterName.text(chapterNameText)

            let watchTimeLabel = $("<div class=\"watch-time-label\">观看时间:</div>")

            let watchTime = $("<div class=\"watch-time\">yyyy-mm-dd</div>")
            let collectTimeS = new Date(watchHistory.watchTime)
            watchTime.text(collectTimeS.toString())



            item.append(historyImg)
            item.append(courseTitle)
            item.append(watchTime)
            item.append(chapterLabel)
            item.append(chapterName)
            item.append(watchTimeLabel)
            list.append(item)
        }
    }

    let totalWatchHistoryNum
    let totalPage

    $.ajax({
        url: baseUrl + "getWatchHistoryNum",
        method: "post",
        async: false,
        data: {
            uid: userInfo.uid
        },
        dataType: "json",
        success: function (res) {
            totalWatchHistoryNum = res.data
        },
        error: function (res) {
            alert("server error!")
        }
    })

    totalPage = Math.ceil(totalWatchHistoryNum / watchHistoryNumInOnePage)


    // 内容生成完成，开始生成下标
    let point1 = $("#point")
    $(".num-btn").remove()
    let nextPageBtn = $("#next-page-btn")
    let lastPageBtn = $("#last-page-btn")
    if (totalPage <= 4){
        point1.remove()
        for (let i = 1; i <= totalPage; i++) {
            addNumBtn(i, nextPageBtn)
            if (currentWatchHistoryPage === i) {
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
            if (currentWatchHistoryPage === 1 || currentWatchHistoryPage === 2) {
                if (currentWatchHistoryPage === 2) {
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
            } else if (currentWatchHistoryPage === totalPage || currentWatchHistoryPage === totalPage - 1) {
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
                    if (currentWatchHistoryPage === totalPage) {
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
                        i = currentWatchHistoryPage - 2
                        stage++;
                    } else if (stage === 2) {
                        nextPageBtn.before(point2)
                        point2.css("display", "inline-block")
                        i = totalPage - 1
                        stage++
                    }
                }
            }



            if (currentWatchHistoryPage === i) {
                $("#num-btn-" + i).attr("disabled", true)
            } else {
                $("#num-btn-" + i).attr("disabled", false)
            }
        }
    }

    nextPageBtn.off("click")
    nextPageBtn.on("click", function () {
        if (currentWatchHistoryPage !== totalPage){
            currentWatchHistoryPage++;
            switchWatchHistoryPage(currentWatchHistoryPage)
        } else {

        }
    })

    lastPageBtn.off("click")
    lastPageBtn.on("click", function () {
        if (currentWatchHistoryPage !== 1) {
            currentWatchHistoryPage--
            switchWatchHistoryPage(currentWatchHistoryPage)
        } else {

        }
    })

        if (currentWatchHistoryPage !== totalPage) {
        nextPageBtn.attr("disabled", false)
    } else {
        nextPageBtn.attr("disabled", true)
    }

    if (currentWatchHistoryPage !== 1) {
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
        currentWatchHistoryPage = parseInt(numBtn.text())
        switchWatchHistoryPage(currentWatchHistoryPage)
    })
    rightBtn.before(numBtn)
}

