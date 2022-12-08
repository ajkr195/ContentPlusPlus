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
			//var content = "<p>All Files Uploaded Successfully</p>";
			document.getElementById("multipleFileUploadInput").value = null;
			//for (var i = 0; i < response.length; i++) {
			//content += "<p>DownloadUrl : <a href='" + response[i].fileDownloadUri + "' target='_blank'>" + response[i].fileDownloadUri + "</a></p>";
			//}
			//multipleFileUploadSuccess.innerHTML = content;
			//multipleFileUploadSuccess.style.display = "block";
			Swal.fire(
				'Uploaded!',
				'All files uploaded successfully.',
				'success'
			).then(() => {
				location.reload();
			});
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


function deletealldbfiles() {
	Swal.fire({
		title: 'Are you sure?',
		text: "You want to Delete em all from Content++ ? This operation is irreversible and all your files may not be recovered later.",
		icon: 'warning',
		showDenyButton: true,
		confirmButtonColor: '#d33',
		denyButtonColor: 'gray',
		confirmButtonText: 'Yes, delete em all !',
		denyButtonText: `Don't delete`,
	}).then((result) => {
		if (result.isConfirmed) {
			fetch('/deletealldbfiles', {
				method: 'DELETE',
			})
			Swal.fire(
				'Deleted!',
				'Your files have been deleted.',
				'success'
			).then(() => {
				location.reload();
			});

		} else if (result.isDenied) {
			Swal.fire('Great. Changes are not saved', 'Your important files are safe in Content++ :)', 'info')
		}
		//location.reload();
	})
}


function deletedbfile(id, Object) {
	Swal.fire({
		title: 'Are you sure?',
		text: "You want to Delete this file from Content++ ? This operation is irreversible and this file may not be recovered later.",
		icon: 'warning',
		showDenyButton: true,
		confirmButtonColor: '#d33',
		denyButtonColor: 'gray',
		confirmButtonText: 'Yes, delete it!',
		denyButtonText: `Don't delete`,
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
		} else if (result.isDenied) {
			Swal.fire('Great. Changes are not saved', 'Your important file is safe in Content++ :)', 'info')
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
