let faceImg
let account
let nickname
let signupTime
let balance

function initial () {
    faceImg = $("#face-img")
    account = $("#account")
    nickname = $("#nickname")
    signupTime = $("#signup-date")
    balance = $("#balance")

    let chargeBtn = $("#charge-btn")

    updateUserInfo()

    chargeBtn.on("click", function () {
        console.log("click")
        window.parent.displayChargeInputPage()
    })
}

function updateUserInfo () {
    let userInfo = JSON.parse(sessionStorage.getItem("user"))

    $.ajax({
        url: baseUrl + "getUserInfo",
        method: "post",
        data: {uid: userInfo.uid},
        dataType: "json",
        success: function (res) {
            switch (res.code) {
                case 0:
                    alert(res.msg)
                    break;
                case 1:
                    sessionStorage.setItem("user", JSON.stringify(res.data))
                    break;
            }
        },
        error: function (res) {
            alert("server error!")
        }
    })

    userInfo = JSON.parse(sessionStorage.getItem("user"))

    console.log(userInfo)

    faceImg.attr("src", userInfo.faceImage)
    account.text(userInfo.account)
    nickname.text(userInfo.nickname)
    var date = new Date(userInfo.registerTime)
    signupTime.text(date.toString())
    balance.text(userInfo.balance)
}