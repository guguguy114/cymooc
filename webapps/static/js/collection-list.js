let properties = {
    currentPage : 1,
    limitNum : 3
}

let uid;

function initial() {
    addPageBtn($("#background"))
    let user = JSON.parse(sessionStorage.getItem("user"));
    if (user !== null) {
        uid = user.uid
        switchPages(properties.currentPage)
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

function switchPages (page) {
    let courseList = getUserCollections(uid)
    let list = $("#collection-list")
    list.empty()

    if (courseList === undefined) {
        $("#page-btn-div").empty()
        return
    }

    let start = (page - 1) * properties.limitNum
    let end = (start + properties.limitNum) - 1
    for (let i = start; i <= end; i++) {
        let course = courseList[i]
        if (course !== undefined){
            let courseId = course.courseId

            let item = $("<li class='collection-item'></li>")

            let itemImg = $("<img src='' alt='course-img' class='course-img'>")
            let imgSrc = course.courseImg
            itemImg.attr("src", imgSrc)
            itemImg.on("click", function () {
                toCoursePage(courseId, parent)
            })

            let courseTitle = $("<div class='course-title'></div>")
            setTitle(courseTitle, course.courseName)

            courseTitle.on("click", function () {
                toCoursePage(courseId, parent)
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

    setPageBtn(courseList.length, properties, switchPages)

}

