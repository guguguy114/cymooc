let properties = {
    currentPage : 1,
    limitNum : 2
}

let uid

function initial () {
    addPageBtn($("#background"))
    let user = JSON.parse(sessionStorage.getItem("user"));
    if (user !== null) {
        uid = user.uid
        switchPages(properties.currentPage)
    }
}

function switchPages (page) {
    let purchaseHistoryList = getUserPurchaseHistory(uid, properties.limitNum, page)

    let list = $("#history-list")
    list.empty()

    if (purchaseHistoryList === undefined) {
        $("#page-btn-div").empty()
        return
    }

    for (let i = 0; i <= purchaseHistoryList.length - 1; i++) {
        let purchaseHistory = purchaseHistoryList[i]
        if (purchaseHistory !== undefined) {

            let courseInfo = getCourseDetail(purchaseHistory.courseId)
            let courseImgS = courseInfo.courseImg


            let item = $("<li class='history-item'></li>")


            let courseImg = $("<img src=\"../static/images/upload/face-images/default_face_img.png\" alt=\"course_img\" class=\"course-img\">")
            courseImg.attr("src", courseImgS)
            courseImg.on("click", function () {
                toCoursePage(courseInfo.courseId, parent)
            })

            let courseTitle = $("<div class=\"course-title\">这是视频标题</div>")
            courseTitle.text(courseInfo.courseName)
            courseTitle.on("click", function () {
                toCoursePage(courseInfo.courseId, parent)
            })

            let purchaseLabel = $("<div class=\"purchase-price-label\">购买金额:</div>")

            let purchasePrice = $("<div class=\"purchase-price\">金额</div>")
            purchasePrice.text(purchaseHistory.cost)

            let purchaseTimeLabel = $("<div class=\"purchase-time-label\">购买时间:</div>")

            let purchaseTime = $("<div class=\"purchase-time\">yyyy-mm-dd</div>")
            purchaseTime.text(new Date(purchaseHistory.purchaseTime).toString())

            item.append(courseImg)
            item.append(courseTitle)
            item.append(purchaseLabel)
            item.append(purchasePrice)
            item.append(purchaseTimeLabel)
            item.append(purchaseTime)
            list.append(item)
        }
    }

    let totalPurchaseNum

    $.ajax({
        url: baseUrl + "getPurchaseHistoryNum",
        method: "post",
        dataType: "json",
        async: false,
        data: {
            uid: uid
        },
        success: function (res) {
            totalPurchaseNum = res.data
        },
        error: function (res) {
            alert("server error!")
        }
    })

    setPageBtn(totalPurchaseNum, properties, switchPages)
}

function getUserPurchaseHistory (uid, limitNum, page) {
    let historyList
    $.ajax({
        url: baseUrl + "getUserCoursePurchaseHistory",
        method: "post",
        dataType: "json",
        async: false,
        data: {
            uid: uid,
            lim: limitNum,
            page: page
        },
        success: function (res) {
            historyList = res.data
        },
        error: function (res) {
            alert("server error!")
        }
    })
    return historyList
}