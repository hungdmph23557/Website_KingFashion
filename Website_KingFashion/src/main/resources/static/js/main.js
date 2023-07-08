(function($) {

	"use strict";

	var fullHeight = function() {

		$('.js-fullheight').css('height', $(window).height());
		$(window).resize(function(){
			$('.js-fullheight').css('height', $(window).height());
		});

	};
	fullHeight();

	$('#sidebarCollapse').on('click', function () {
      $('#sidebar').toggleClass('active');
  });

})(jQuery);
$(document).ready(function () {
	//jquery for toggle sub menus
	$('.sub-btn').click(function () {
		$(this).next('.sub-menu').slideToggle();
		$(this).find('.dropdown').toggleClass('rotate');
	});
	// // Xử lý sự kiện nhấp chuột vào liên kết "View Update"
	// $('a[data-toggle="modal"]').on('click', function() {
	// 	var id = $(this).data('id');
	// 	var modal = $('#updateEmployeeModal');
	//
	// 	// Gửi yêu cầu AJAX để lấy thông tin cần thiết
	// 	$.ajax({
	// 		url: '/mau-sac/view-update/' + id,
	// 		type: 'GET',
	// 		success: function(response) {
	// 			// Xử lý phản hồi thành công và cập nhật dữ liệu vào modal
	// 			modal.find('.modal-content').html(response);
	// 			modal.modal('show');
	// 		},
	// 		error: function(xhr, status, error) {
	// 			// Xử lý lỗi
	// 			console.log(error);
	// 		}
	// 	});
	// });
});