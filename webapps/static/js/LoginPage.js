function initial(){// 设置点击事件
    $("#login-btn").click(function () {
        // 获取输入框中的数值
        loadUser();
        console.log("logging!");// 这句会先于ajax内的success语段先执行，体现ajax的异步请求特点，ajax执行后会直接执行ajax后的同级语句，而不会使页面卡在等待ajax的情况，可以类比为线程
        // 异步操作可以避免用户等待时间过长，节省用户时间
    });
    $("#login-confirm").on('click', function (){
        $("#login-feedback").css("display", "none");
        // console.log("do click");
    })
    console.log("initial complete!")
}

function loadUser(){
    var acc = $("#acc").val();
    var pwd = $("#pwd").val();
    $.ajax({ // 发起ajax请求
        // 向对应模块发起请求并提交数据
        url: baseUrl + "login",
        // 客户端向服务器请求的类型
        method: "POST",
        data: {
            // 设置需要传递给服务器后端的数据
            acc: acc,
            pwd: pwd,
        },
        dataType: "json", // 指定传输回来的数据类型
        success: function (res) { // 这里的res是服务器返回的资源
            if(res.code === 0){
                console.log("failed");
                $("#login-feedback").css("display", "block");
            }else if (res.code === 1){
                console.log("success");
                sessionStorage.setItem("user", JSON.stringify(res));
                window.location.href = "../../pages/home.html";
                $("#login-feedback").css("display", "block");
            }
            console.log(res)
        },
        // 服务器没有响应时会调用error方法
        error: function (res){
            console.log(res);
            alert("404")
        }
    });
}