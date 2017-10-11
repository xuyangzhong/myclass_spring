function formSubmit() {
    var username = $("#username").val();
    var password = $("#password").val();
    console.info("username : " + username + " , password : " + password);
    $.ajax({
        type: "post",
        url: "/userlogin/checklogin.do",
        data: {
            "username": username,
            "password": password
        },
        cache: "false",
        async: "true",
        dataType: "text",
        charset: "utf-8",
        success: function (result) {
            if ("success" == result){
                window.location.href = "/userlogin/index.html";
            }else{
                $("#myAlert").removeClass("hidden");
            }

        }
    });

}

function hide() {
    $("#myAlert").addClass('hidden');
}

function checkusername() {
    $("#username").attr("placeholder", "用户名不能为空");
}

function resetusername() {
    $("#username").attr("placeholder", "");
}

function checkpassword() {
    $("#password").attr("placeholder", "密码不能为空");
}

function resetpassword() {
    $("#password").attr("placeholder", "");
}