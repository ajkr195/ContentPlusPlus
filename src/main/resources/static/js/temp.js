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


function uploadMultipleUsingSwal() {
	let htmlstring = '<input type="file" id="file-selector" multiple>';

	Swal.fire({
		html: htmlstring,
		title: 'Do you want to get data from Github?',
		text: "You won't be able to revert this!",
		showDenyButton: true,
		denyButtonText: 'Cancel',
		showCloseButton: true,
		showConfirmButton: true,
		confirmButtonText: 'Upload',
		showLoaderOnConfirm: true,
		allowOutsideClick: () => !Swal.isLoading(),
		preConfirm: async function() {
			const fileselectorValid = Swal.getPopup().querySelector('#file-selector').value
			if (fileselectorValid == "") {
				Swal.showValidationMessage(`Please select at least one or more file(s). !`)
			}
			const fileselector = document.getElementById('file-selector');
			
			var files = fileselector.files;
			var formData = new FormData();
			for (var index = 0; index < files.length; index++) {
				formData.append("files", files[index]);
			}
			return fetch('/uploadMultipleFiles', {
				method: 'POST',
				body: formData
			}).then(() => {
				//response.json();
				Swal.fire(
					'Uploaded!',
					'All files uploaded successfully.',
					//'All files uploaded successfully. Response Code: ' + response.status,
					'success'
				).then(() => {
					location.reload();
				});
			}).catch((error) => {
				Swal.fire(
					'Upload Failed!',
					error,
					'error'
				)
			});
		}
	});
}
