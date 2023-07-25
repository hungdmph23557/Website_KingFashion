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
$(".menu > ul > li").click(function (e) {
	// remove active from already active
	$(this).siblings().removeClass("active");
	// add active to clicked
	$(this).toggleClass("active");
	// if has sub menu open it
	$(this).find("ul").slideToggle();
	// close other sub menu if any open
	$(this).siblings().find("ul").slideUp();
	// remove active class of sub menu items
	$(this).siblings().find("ul").find("li").removeClass("active");
});

$(".menu-btn").click(function () {
	$(".sidebar").toggleClass("active");
});
jQuery(document).ready(function(){
	// This button will increment the value
	$('[data-quantity="plus"]').click(function(e){
		// Stop acting like a button
		e.preventDefault();
		// Get the field name
		fieldName = $(this).attr('data-field');
		// Get its current value
		var currentVal = parseInt($('input[name='+fieldName+']').val());
		// If is not undefined
		if (!isNaN(currentVal)) {
			// Increment
			$('input[name='+fieldName+']').val(currentVal + 1);
		} else {
			// Otherwise put a 0 there
			$('input[name='+fieldName+']').val(0);
		}
	});
	// This button will decrement the value till 0
	$('[data-quantity="minus"]').click(function(e) {
		// Stop acting like a button
		e.preventDefault();
		// Get the field name
		fieldName = $(this).attr('data-field');
		// Get its current value
		var currentVal = parseInt($('input[name='+fieldName+']').val());
		// If it isn't undefined or its greater than 0
		if (!isNaN(currentVal) && currentVal > 0) {
			// Decrement one
			$('input[name='+fieldName+']').val(currentVal - 1);
		} else {
			// Otherwise put a 0 there
			$('input[name='+fieldName+']').val(0);
		}
	});
});
function openPanel() {
	document.getElementById("newPanel").style.display = "block";
}
function toggleSelect(button) {
	if (button.classList.contains('selected')) {
		button.classList.remove('selected');
		button.classList.add('deselected');
	} else {
		button.classList.remove('deselected');
		button.classList.add('selected');
	}
}
function increaseQuantity(button) {
	var input = button.previousElementSibling;
	input.value = parseInt(input.value) + 1;
}

function decreaseQuantity(button) {
	var input = button.nextElementSibling;
	if (parseInt(input.value) > 1) {
		input.value = parseInt(input.value) - 1;
	}
}

