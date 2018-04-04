
"use strict";
//# sourceURL=main.js

// DOM 加载完再执行
$(".blog-menu .list-group-item").click(function() {

	var url = $(this).attr("url");

	//先移除点击样式，再添加当前的点击样式
	$(".blog-menu .list-group-item").removeClass("active");
	$(this).addClass("active");


	$.ajax({
        url:url,
        success:function (data) {
            $("#rightContainer").html(data);

        },
        error : function () {
            alert("error")

        }
    });

	$(".blog-menu .list-group-item:first").trigger("click");



});