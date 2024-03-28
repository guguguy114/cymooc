let properties = {
    currentPage : 1,
    limitNum : 3
}

let searchOption
let tags

function initial () {
    let searchWord = JSON.parse(sessionStorage.getItem("search_option")).searchWord
    searchOption = JSON.parse(sessionStorage.getItem("search_option"))
    searchOption.sortType = new Set()
    searchOption.sortType.add("front-side-type-btn")
    searchOption.sortType.add("back-side-type-btn")
    searchOption.sortTag = getSearchResultTags(searchWord)

    let background = $("#background")
    tags = getSearchResultTags(searchWord)
    setTagBtn(tags)

    setHeader(background)
    addPageBtn(background)
    switchPage(properties.currentPage)
    setBtnFunc()
    changeUI()
}

function switchPage(page) {

    let searchWord = JSON.parse(sessionStorage.getItem("search_option")).searchWord

    let storageSearchOption = JSON.parse(sessionStorage.getItem("search_option"))

    let typeList = ""

    let tagList = ""

    for (let item of searchOption.sortType) {
        typeList += $("#" + item).text() + ","
    }

    for (let item of searchOption.sortTag) {
        tagList += $(document.getElementById(item)).text() + ","
    }

    console.log(tagList)

    getSearchList(searchWord, page, properties.limitNum, storageSearchOption.sortMode, typeList, tagList)

    let searchResultList

    let list = $("#search-result-list")
    list.empty()

    if (sessionStorage.getItem("search_result") === "undefined") {
        $("#page-btn-div").empty()

        let item = $("<li id=\"error-item\" class=\"result-item\">")
        let errorDiv = $("<div id=\"error-title\">没有搜索结果</div>")

        item.append(errorDiv)
        list.append(item)

        return

    } else {
        searchResultList = JSON.parse(sessionStorage.getItem("search_result"))
    }

    console.log("up")
    for (let i = 0; i <= searchResultList.length - 1; i++) {
        let course = searchResultList[i]

        if (course !== undefined) {

            let item = $("<li class=\"result-item\"></li>")

            let courseImg = $("<img src=\"../static/images/upload/face-images/default_face_img.png\" alt=\"course_img\" class=\"course-img\">")
            courseImg.attr("src", course.courseImg)
            courseImg.on("click", function () {
                toCoursePage(course.courseId, window)
            })

            let courseTitle = $("<div class=\"course-title\">这是视频标题</div>")
            setTitle(courseTitle, course.courseName)
            courseTitle.on("click", function () {
                toCoursePage(course.courseId, window)
            })

            let descriptionLabel = $("<div class='description-label'>课程简介：</div>")

            let description = $("<div class='course-description'></div>")
            description.text(course.courseDescription)

            let typeLabel = $("<div class=\"type-label\">类型:</div>")

            let type = $("<div class=\"type\">类型</div>")
            type.text(course.courseType)

            let tagLabel = $("<div class=\"tag-label\">标签:</div>")

            let tag = $("<div class=\"tag\">标签</div>")
            tag.text(course.courseTag)

            let playNumLabel = $("<div class='play-num-label'>播放数：</div>")

            let playNum = $("<div class='play-num'>num</div>")
            playNum.text(getCourseData("playNum", course))

            let collectNumLabel = $("<div class='collect-num-label'>收藏数：</div>")

            let collectNum = $("<div class='collect-num'>num</div>")
            collectNum.text(getCourseData("collectNum", course))

            let commentNumLabel = $("<div class='comment-num-label'>评论数：</div>")

            let commentNum = $("<div class='comment-num'>num</div>")
            commentNum.text(getCourseData("commentNum", course))

            let updateTimeLabel = $("<div class=\"update-time-label\">上传时间:</div>")

            let updateTime = $("<div class=\"update-time\">yyyy-mm-dd</div>")
            updateTime.text(new Date(course.updateTime).toString())

            item.append(courseImg)
            item.append(courseTitle)
            item.append(descriptionLabel)
            item.append(description)
            item.append(typeLabel)
            item.append(type)
            item.append(tagLabel)
            item.append(tag)
            item.append(playNumLabel)
            item.append(playNum)
            item.append(collectNumLabel)
            item.append(collectNum)
            item.append(commentNumLabel)
            item.append(commentNum)
            item.append(updateTimeLabel)
            item.append(updateTime)

            list.append(item)

            let searchNum = getSearchNum(searchWord, typeList, tagList)

            $("#total-search-num").text("本次共有 " + searchNum + " 条搜索结果")
            $("#current-course-num").text("当前页面共有 " + searchResultList.length + " 条搜索结果")

            setPageBtn(searchNum, properties, switchPage)
        }
    }
}

function changeUI() {
    let stSearchOption = JSON.parse(sessionStorage.getItem("search_option"))

    // 排序模式设置
    let sortMode = stSearchOption.sortMode

    let sortBtn = $("#sort-div .sort-btn")
    sortBtn.css("background-color", "#f9f9f9")
    sortBtn.css("color", "black")

    let selectedSortBtn = $("#" + sortMode)
    selectedSortBtn.css("background-color", "grey")
    selectedSortBtn.css("color", "#f9f9f9")


    // 前后端按钮设置
    let typeBtn = $("#type-tag-div .sort-btn")
    typeBtn.css("background-color", "#f9f9f9")
    typeBtn.css("color", "black")

    let sortTypeList = searchOption.sortType
    for (let item of sortTypeList) {
        let selectedTypeSortBtn = $("#" + item)
        selectedTypeSortBtn.css("background-color", "grey")
        selectedTypeSortBtn.css("color", "#f9f9f9")
    }

    // 设置tag按钮
    let tagBtn = $("#tag-div .sort-btn")
    tagBtn.css("background-color", "#f9f9f9")
    tagBtn.css("color", "black")

    let sortTagList = searchOption.sortTag
    for (let item of sortTagList) {
        let selectedTagSortBtn = $(document.getElementById(item))
        selectedTagSortBtn.css("background-color", "grey")
        selectedTagSortBtn.css("color", "#f9f9f9")
    }
}

function setBtnFunc() {
    $("#sort-div .sort-btn").on("click", function (e) {
        // e.target 对应方法参数，获取当前事件的元素
        let sortType = $(e.target).attr("id")
        console.log(sortType)
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
        switchPage(1)
        changeUI()
    })
}

function setTagBtn(tagList) {
    let list = $("#tag-div")
    let tagBtn = $("#tag-div .sort-btn")
    tagBtn.remove()
    for (let item of tagList) {
        let tagBtn = $("<button class=\"sort-btn\">播放数</button>");
        tagBtn.attr("id", item)
        tagBtn.on("click", function () {
            let tagWord = item
            let sortTagList = searchOption.sortTag
            if (!sortTagList.has(tagWord)) {
                sortTagList.add(tagWord)
            } else {
                sortTagList.delete(tagWord)
            }
            searchOption.sortTag = sortTagList
            switchPage(1)
            changeUI()
        })
        tagBtn.text(item)
        list.append(tagBtn)
    }
}

function getCourseData(dataType, course) {
    let num
    switch (dataType) {
        case "playNum":
            num = getCoursePlayNum(course)
            break
        case "collectNum":
            num = getCollectNum(course)
            break
        case "commentNum":
            num = getCommentNum(course)
            break
    }
    return num
}