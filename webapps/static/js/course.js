function initial () {
    judgeLoginState()
    let courseInfo = JSON.parse(sessionStorage.getItem("current_course"))
    $("#course-title").text(courseInfo.courseName)
    $("#course-description").text(courseInfo.courseDescription)
}

