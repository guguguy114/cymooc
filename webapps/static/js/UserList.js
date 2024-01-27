const pageSize = 2;
let total = 0;
let pageCurr = 1;
let pageCount = 0;

function getUsersData(){
    $.ajax({
        url: baseUrl + "getUsers",
        method: "POST",
        dataType: "json",
        data:{
            pageCurr: pageCurr,
            pageSize: pageSize,
        },
        success:function (res){
            total = res.data.count;
            pageCount = Math.ceil(total / pageSize);
            var users = res.data.content;
            for (let i = 0; i < users.length; i++){
                var li = $(`
                    <li>
                        <img src="${users[i].faceImage}" alt="faceImg">
                        <div>${users[i].username}</div>
                    </li>
                `);
                $("#user-list").append(li);
            }
            for (let j = 1; j <= pageCount; j++){
                var num = $(`
                    <button id="num-btn">${j}</button>
        `       );
                num.on("click", function (){
                    pageCurr = j;
                    $("#user-list").empty();
                    $("#page-numbers").empty();
                    // 重新发起ajax请求
                    getUsersData();
                });
                if (pageCurr === j){
                    num.attr("disabled", "true");
                }
                $("#page-numbers").append(num);
            }
            console.log(res);
        },
        error:function (res){
            alert("server error");
        }
    })
}

$(document).ready(function (){
    // 数据初始化
    getUsersData();
    // 设置上一页钮
    $("#next-page").on("click", function (){
        if (pageCurr >= pageCount){
            alert("已经是最后一页了");
            return;
        }
        pageCurr += 1;
        //清空用户列表
        $("#user-list").empty();
        $("#page-numbers").empty();
        // 重新发起ajax请求
        getUsersData();
    })

    // 设置下一页按钮
    $("#last-page").on("click", function (){
        if (pageCurr <= 1){
            alert("已经到顶了~");
            return;
        }
        pageCurr -= 1;
        //清空用户列表
        $("#user-list").empty();
        $("#page-numbers").empty();
        // 重新发起ajax请求
        getUsersData();
    })
})

function initial(){

}


