function initial(){
    var userData = JSON.parse(sessionStorage.getItem("user")).data;
    var user = JSON.parse(sessionStorage.getItem("user"));
    console.log(userData);
    $("#user-name").html(userData.userName);
    $("#user-acc").html(userData.acc);
    $("#face-image").attr("src", userData.faceImage);
    $("#money").html(userData.money);
    $("#btn").on("click", function (){
        /* 充值操作 */
        var money = prompt("请输入金额");
        $.ajax({
            url: baseUrl + "charge",
            method: "POST",
            data: {
                id: userData.id,
                money: money
            },
            dataType: "json",
            success: function (res){
                alert("success");
                var money = parseInt($("money").text()) + res.data;
                $("#money").html(money)
                sessionStorage.setItem("user", user);
            },
            error: function (){
                alert("error charge");
            }
        })
    })
}