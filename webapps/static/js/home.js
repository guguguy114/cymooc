
function initial() {
    updateUserInfo()

    $("#logo").on("click", function () {
        window.location.href = baseUrl
    })
}

function updateUserInfo () {
    let userInfo = JSON.parse(sessionStorage.getItem("user")).data

}