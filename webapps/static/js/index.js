let vc; // 验证码
let currentBannerPage = 1; // 默认轮播图位置
let tik = 1; // 初始tik
let maxBannerNum = 5; // 最大轮播图数量
let maxShowVideoNum = 10;// 最大展示推荐视频数量
function initial() {
    setHeader($("#background"))

    // 设置横幅播放
    setInterval(function () {
        if (tik !== 5) { // 5秒轮换一次
            tik++;
        }else {
            if (currentBannerPage === maxBannerNum) {
                currentBannerPage = 0;
            }
            currentBannerPage ++;
            setBannerImg(currentBannerPage)
        }
    }, 1000)

    // 设置横幅下面的点
    for (let i = 1; i <= maxBannerNum; i++) {
        let list = $("#banner-page-points-list");
        let pointNum = i;
        let listItem = $("<li class='banner-page-point-item'></li>");
        let point = $("<div class='banner-page-point'></div>")
        point.attr("id", "banner-page-point" + i);
        point.on("click", function () {
            setBannerImg(i);
        })
        listItem.append(point);
        list.append(listItem)
    }

    // 设置推荐视频
    getRecommendVideo("like_list")

    // 设置轮播图的按钮
    $("#banner-page-point1").css("background-color", "white")
    $("#banner-right-btn").on("click", function () {
        if (currentBannerPage === 5) {
            setBannerImg(1);
        } else {
            setBannerImg(currentBannerPage + 1);
        }
    })
    $("#banner-left-btn").on("click", function () {
        if (currentBannerPage === 1) {
            setBannerImg(5);
        } else {
            setBannerImg(currentBannerPage - 1);
        }
    })

    $("#logo").on("click", function () {
        window.location.href = baseUrl
    })


    let sortBtn = $(".current-sort-way")
    let sortContentPan = $("#sort-way-content")


    $("#sort-way").on("mouseenter", function () {
        sortContentPan.css("display", "block")
    })




    $("#others-comment-div").on("mouseenter", function () {
        sortContentPan.css("display", "block")
    })


    $("#like").on("click", function () {
        $(".current-sort-way").html($("#like").html())
        $("#sort-way-content").css("display", "none")
        $(".video-list-item").remove()
        getRecommendVideo("like_list")
    })

    $("#comment").on("click", function () {
        $(".current-sort-way").html($("#comment").html())
        $("#sort-way-content").css("display", "none")
        $(".video-list-item").remove()
        getRecommendVideo("comment")
    })

    $("#collection").on("click", function () {
        $(".current-sort-way").html($("#collection").html())
        $("#sort-way-content").css("display", "none")
        $(".video-list-item").remove()
        getRecommendVideo("collection")
    })
}

function setBannerImg (page) {
    let frontImg = $("#banner-img");
    frontImg.fadeToggle("slow", function () {
        frontImg.attr("src", "../static/images/banners/banner" + page + ".jpg")
        for (let i = 1; i <= maxBannerNum; i++) {
            let id = ("#banner-page-point" + i);
            if (currentBannerPage === i) {
                $(id).css("background-color", "white");
            }else {
                $(id).css("background-color", "grey");
            }
        }
        frontImg.fadeToggle("slow");
        frontImg.css("top", 0)
    });
    currentBannerPage = page;
    tik = 0;
}



function getRecommendVideo (func) {
    let recommendVideoNum = 10;
    let data;
    $.ajax({
        url: baseUrl + "getRecommendCourse",
        method: "post",
        data: {
            num: recommendVideoNum,
            func: func
        },
        dataType: "json",
        success: function (res) {
            data = res.data
            for (let i = 1; i <= maxShowVideoNum; i++) {
                let videoList = $("#video-suggest-list")
                let videoLi = $("<li class='video-list-item'></li>")
                let videoDiv = $("<div class='video-div'></div>")
                let imgId = "video-img" + i
                let videoImgDiv = $("<div class='video-img-div'></div>")
                let videoImg = $("<img src='../static/images/upload/course-images/default_video_img.jpg' alt='video-img' class='video-img'> <hr>")
                let titleId = "title" + i
                let videoName = $("<div>this is title</div>")

                videoName.attr("id", titleId);
                videoImg.attr("id", imgId);

                if (i <= data.length) {
                    let course = getCourseDetail(data[i - 1]);
                    videoName.text(course.courseName)
                    videoImg.attr("src", course.courseImg)
                }

                videoDiv.on("click", function () {
                    toCoursePage(data[i - 1], window)
                })

                videoImgDiv.append(videoImg)
                videoDiv.append(videoImgDiv)
                videoDiv.append(videoName)
                videoLi.append(videoDiv)
                videoList.append(videoLi)
            }
        },
        error: function (res) {
            alert("server error!")
        }
    })

    // 设置推荐视频

}

