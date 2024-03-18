let properties = {
    currentPage : 1,
    limitNum : 3
}

let searchOption

function initial () {
    searchOption = JSON.parse(sessionStorage.getItem("search_option"))
    searchOption.sortType = new Set()
    searchOption.sortTag = new Set()
    let background = $("#background")
    setHeader(background)
    addPageBtn(background)
    switchPage(properties.currentPage)
    setBtnFunc()
    changeUI()
}

function switchPage(page) {
    let searchWord = JSON.parse(sessionStorage.getItem("search_option")).searchWord

    let searchOption = JSON.parse(sessionStorage.getItem("search_option"))

    getSearchList(searchWord, page, properties.limitNum, searchOption.sortMode)

    let searchResultList = JSON.parse(sessionStorage.getItem("search_result"))
    let list = $("#search-result-list")
    list.empty()

    if (searchResultList === undefined) {
        $("#page-btn-div").empty()
        return
    }

    for (let i = 0; i <= searchResultList.length - 1; i++) {
        let course = searchResultList[i]
        if (course !== undefined) {
            let item = $("<li class=\"result-item\"></li>>")

            let courseImg = $("<img src=\"../static/images/upload/face-images/default_face_img.png\" alt=\"course_img\" class=\"course-img\">")
            courseImg.attr("src", course.courseImg)
            courseImg.on("click", function () {
                toCoursePage(course.courseId, window)
            })

            let courseTitle = $("<div class=\"course-title\">这是视频标题</div>")
            courseTitle.text(course.courseName)
            courseTitle.on("click", function () {
                toCoursePage(course.courseId, window)
            })

            let typeLabel = $("<div class=\"type-label\">类型:</div>")

            let type = $("<div class=\"type\">类型</div>")
            type.text(course.courseType)

            let tagLabel = $("<div class=\"tag-label\">标签:</div>")

            let tag = $("<div class=\"tag\">标签</div>")
            tag.text(course.courseTag)

            let updateTimeLabel = $("<div class=\"update-time-label\">上传时间:</div>")

            let updateTime = $("<div class=\"update-time\">yyyy-mm-dd</div>")
            updateTime.text(new Date(course.updateTime).toString())

            item.append(courseImg)
            item.append(courseTitle)
            item.append(typeLabel)
            item.append(type)
            item.append(tagLabel)
            item.append(tag)
            item.append(updateTimeLabel)
            item.append(updateTime)

            list.append(item)

            let searchNum = getSearchNum(searchWord)

            setPageBtn(searchNum, properties, switchPage)
        }
    }
}

function changeUI() {
    let stSearchOption = JSON.parse(sessionStorage.getItem("search_option"))

    let sortMode = stSearchOption.sortMode

    let sortBtn = $("#sort-div .sort-btn")
    sortBtn.css("background-color", "#f9f9f9")
    sortBtn.css("color", "black")

    let selectedSortBtn = $("#" + sortMode)
    selectedSortBtn.css("background-color", "grey")
    selectedSortBtn.css("color", "#f9f9f9")

    let typeBtn = $("#type-tag-div .sort-btn")
    typeBtn.css("background-color", "#f9f9f9")
    typeBtn.css("color", "black")

    let sortTypeList = searchOption.sortType
    for (let item of sortTypeList) {
        let selectedTypeSortBtn = $("#" + item)
        selectedTypeSortBtn.css("background-color", "grey")
        selectedTypeSortBtn.css("color", "#f9f9f9")
    }
}

function setBtnFunc() {
    $("#sort-div .sort-btn").on("click", function (e) {
        // e.target 对应方法参数，获取当前事件的元素
        let sortType = $(e.target).attr("id")
        let searchOption = JSON.parse(sessionStorage.getItem("search_option"))
        searchOption.sortMode = sortType
        sessionStorage.setItem("search_option", JSON.stringify(searchOption))
        switchPage(1)
        changeUI()
    })

    $("#type-tag-div .sort-btn").on("click", function (e) {
        let sortWord = $(e.target).attr("id")
        let sortTypeList = searchOption.sortType
        if (!sortTypeList.has(sortWord)) {
            sortTypeList.add(sortWord)
        } else {
            sortTypeList.delete(sortWord)
        }
        searchOption.sortType = sortTypeList
        console.log(searchOption)
        switchPage(1)
        changeUI()
    })
}