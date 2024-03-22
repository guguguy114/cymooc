let properties = {
    currentPage : 1,
    limitNum : 3
}

function initial() {
    addPageBtn($("#background"))
    switchWatchHistoryPage(properties.currentPage)
}

function switchWatchHistoryPage (page) {
    let userInfo = JSON.parse(sessionStorage.getItem("user"))
    let watchHistoryList = getWatchHistoryList(userInfo.uid, properties.limitNum, page)

    let list = $("#history-list")
    list.empty()

    if (watchHistoryList === undefined) {
        $("#page-btn-div").empty()
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
            setTitle(courseTitle, courseInfo.courseName)
            courseTitle.on("click", function () {
                toCoursePage(courseId, parent, chapterOrder)
            })

            let chapterLabel = $("<div class=\"purchase-price-label\">观看至章节:</div>")

            let chapterName = $("<div class=\"purchase-price\">这是章节名称</div>")
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

    // 生成数字页标
    setPageBtn(totalWatchHistoryNum, properties, switchWatchHistoryPage)
}

