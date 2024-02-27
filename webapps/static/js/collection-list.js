let itemNumInOnePage = 1;
let currentPage = 1;





function initial() {
    let user = JSON.parse(sessionStorage.getItem("user"));
    let uid;
    if (user !== null) {
        uid = user.uid
        getUserCollections(uid, itemNumInOnePage, currentPage)
    }
}

function getUserCollections (uid, num, currentPage) {
    $.ajax({
        url: baseUrl + "getUserCollections",
        method: "post",
        data: {
            uid: uid,
            num: num,
            currentPage: currentPage
        },
        dataType: "json",
        success: function (res) {

        },
        error: function (res) {
            alert("server error")
        }
    })
}