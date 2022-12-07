'use strict';

var singleUploadForm = document.querySelector('#singleUploadForm');
var singleFileUploadInput = document.querySelector('#singleFileUploadInput');
var singleFileUploadError = document.querySelector('#singleFileUploadError');
var singleFileUploadSuccess = document.querySelector('#singleFileUploadSuccess');

var multipleUploadForm = document.querySelector('#multipleUploadForm');
var multipleFileUploadInput = document.querySelector('#multipleFileUploadInput');
var multipleFileUploadError = document.querySelector('#multipleFileUploadError');
var multipleFileUploadSuccess = document.querySelector('#multipleFileUploadSuccess');


function uploadMultipleFiles(files) {
	var formData = new FormData();
	for (var index = 0; index < files.length; index++) {
		formData.append("files", files[index]);
	}
	const fileField = document.querySelector('input[type="file"]');

	//formData.append('filecusomName', 'abc123');
	//formData.append('files', fileField.files[0]);

	fetch('/uploadMultipleFiles', {
		method: 'POST',
		body: formData
	})
		.then((response) => response.json())
		.then((response) => {
			console.log('Success:', response);
			multipleFileUploadError.style.display = "none";
			var content = "<p>All Files Uploaded Successfully</p>";
			document.getElementById("multipleFileUploadInput").value = null;
			for (var i = 0; i < response.length; i++) {
				content += "<p>DownloadUrl : <a href='" + response[i].fileDownloadUri + "' target='_blank'>" + response[i].fileDownloadUri + "</a></p>";
			}
			multipleFileUploadSuccess.innerHTML = content;
			multipleFileUploadSuccess.style.display = "block";
		})
		.catch((error) => {
			multipleFileUploadSuccess.style.display = "none";
			multipleFileUploadError.innerHTML = (response && response.message) || "Some Error Occurred";
			console.error('Error:', error);
		});
}







multipleUploadForm.addEventListener('submit', function(event) {
	var files = multipleFileUploadInput.files;
	if (files.length === 0) {
		multipleFileUploadError.innerHTML = "Please select at least one file";
		multipleFileUploadError.style.display = "block";
	}
	uploadMultipleFiles(files);
	event.preventDefault();
}, true);



function logoutConfirmation() {

	Swal.fire({
		title: '<strong>Delete File ?</strong>',
		icon: 'question',
		html:
			'Are you sure, you want to Delete this file from Content++ ?',
		showCloseButton: true,
		showCancelButton: true,
		focusConfirm: false,
		confirmButtonText:
			'<a class="text-white" href="/logout" th:href="@{/logout}" >Yes!</a> ',
		confirmButtonColor: '#dc3545',
		cancelButtonText:
			'No',
	}).then((result) => {
		if (result.isConfirmed) {
			Swal.fire(
				'Deleted!',
				'Your file has been deleted.',
				'success'
			)
		}
	})


}

function deletedbfile(id, Object) {
	Swal.fire({
		title: 'Are you sure?',
		text: "You want to Delete this file from Content++ ?",
		icon: 'warning',
		showCancelButton: true,
		confirmButtonColor: '#d33',
		cancelButtonColor: '#3085d6',
		confirmButtonText: 'Yes, delete it!'
	}).then((result) => {
		if (result.isConfirmed) {
			fetch('/deletedbfile/' + id, {
				method: 'DELETE',
			})
			Swal.fire(
				'Deleted!',
				'Your file has been deleted.',
				'success'
			)
			Object.closest('tr').remove();
		}
	})
}

function deletedbfileVanilla(id, Object) {
	fetch('/deletedbfile/' + id, {
		method: 'DELETE',
	})
		.then((response) => {
			//alert(response.status);
			if (response.status == 204) {
				Object.closest('tr').remove();
			}
			console.log('Success:', response);
		})
		.catch((error) => {
			console.error('Error:', error);
		});
}
