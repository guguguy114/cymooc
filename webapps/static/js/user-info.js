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

    userInfo = getUserInfo(userInfo.uid)

    sessionStorage.setItem("user", JSON.stringify(userInfo))

    userInfo = JSON.parse(sessionStorage.getItem("user"))

    console.log(userInfo)

    faceImg.attr("src", userInfo.faceImage)
    account.text(userInfo.account)
    nickname.text(userInfo.nickname)
    let date = new Date(userInfo.registerTime)
    signupTime.text(date.toString())
    balance.text(userInfo.balance)
}