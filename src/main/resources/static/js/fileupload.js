'use strict';


function deleteallDBfiles() {
	Swal.fire({
		title: 'Are you sure?',
		text: "You want to Delete all files ? This operation is irreversible and all your files may not be recovered later.",
		icon: 'warning',
		showDenyButton: true,
		confirmButtonColor: '#dc3545',
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
			Swal.fire('Great. Changes are not saved.', 'Your important files are safe  !!', 'info')
		}
		//location.reload();
	})
}

function deleteallFSfiles() {
	Swal.fire({
		title: 'Are you sure?',
		text: "You want to Delete all files ? This operation is irreversible and all your files may not be recovered later.",
		icon: 'warning',
		showDenyButton: true,
		confirmButtonColor: '#dc3545',
		denyButtonColor: 'gray',
		confirmButtonText: 'Yes, delete em all !',
		denyButtonText: `Don't delete`,
	}).then((result) => {
		if (result.isConfirmed) {
			fetch('/deleteAllFSfiles', {
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
			Swal.fire('Great. Changes are not saved.', 'Your important files are safe  !!', 'info')
		}
		//location.reload();
	})
}


function deletedbfile(id, Object) {
	Swal.fire({
		title: 'Are you sure?',
		text: "You want to Delete this file? This operation is irreversible and this file may not be recovered later.",
		icon: 'warning',
		showDenyButton: true,
		confirmButtonColor: '#006666',
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
			Swal.fire('Great. Changes are not saved.', 'Your important file is safe  !!  ', 'info')
		}
	})
}

function deletefileVanilla(id, Object) {
	fetch('/deletefile/' + id, {
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

function deleteFSfile(id, Object) {
	Swal.fire({
		title: 'Are you sure?',
		text: "You want to Delete this file? This operation is irreversible and this file may not be recovered later.",
		icon: 'warning',
		showDenyButton: true,
		confirmButtonColor: '#dc3545',
		denyButtonColor: 'gray',
		confirmButtonText: 'Yes, delete it!',
		denyButtonText: `Don't delete`,
	}).then((result) => {
		if (result.isConfirmed) {
			fetch('/fsdelfile/' + id, {
				method: 'DELETE',
			})
			Swal.fire(
				'Deleted!',
				'Your file has been deleted.',
				'success'
			)
			Object.closest('tr').remove();
		} else if (result.isDenied) {
			Swal.fire('Great. Changes are not saved.', 'Your important file is safe  !!  ', 'info')
		}
	})
}



function showFSFileInfo(myfile) {

	let htmlstring = '<div class="row m-0"><div class="text-end col-4 mb-2"><b>Id: </b></div> <div class="text-start col-8">' + myfile.id + '</div></div>' +
		'<div class="row m-0"><div class="text-end col-4 mb-2"><b>File Size: </b></div> <div class="text-start col-8">' + myfile.filesize + '</div></div>' +
		'<div class="row m-0"><div class="text-end col-4 mb-2"><b>Creator: </b></div> <div class="text-start col-8">' + myfile.createdBy + '</div></div>' +
		'<div class="row m-0"><div class="text-end col-4 mb-2"><b>created Date: </b></div> <div class="text-start col-8">' + myfile.createdDate + '</div></div>' +
		'<div class="row m-0"><div class="text-end col-4 mb-2"><b>Modifier: </b></div> <div class="text-start col-8">' + myfile.lastModifiedBy + '</div></div>' +
		'<div class="row m-0"><div class="text-end col-4 mb-2"><b>Modified Date: </b></div> <div class="text-start col-8">' + myfile.lastModifiedDate + '</div></div>' +
		'<div class="row m-0"><div class="text-end col-4 mb-2"><b>MimeType: </b></div> <div class="text-start col-8">' + myfile.filetype + '</div></div>' +
		'</br>Auto closing in <b><timer></timer></b> milliseconds.';

	let timerInterval;

	Swal.fire({
		title: myfile.fileName,
		icon: 'info',
		html: htmlstring,
		showCloseButton: true,
		showConfirmButton: false,
		showCancelButton: true,
		cancelButtonText: 'Close',
		cancelButtonColor: '#28a745',
		//allowOutsideClick: false,
		timer: 10000,
		timerProgressBar: true,

		didOpen: () => {
			//Swal.showLoading()
			const b = Swal.getHtmlContainer().querySelector('timer')
			timerInterval = setInterval(() => {
				b.textContent = Swal.getTimerLeft()
			}, 100)
		},
		willClose: () => {
			clearInterval(timerInterval)
		}
	})
}




function showDBFileInfo(myfile) {

	let htmlstring = '<div class="row m-0"><div class="text-end col-4 mb-2"><b>Id: </b></div> <div class="text-start col-8">' + myfile.id + '</div></div>' +
		'<div class="row m-0"><div class="text-end col-4 mb-2"><b>File Size: </b></div> <div class="text-start col-8">' + myfile.fileSize + '</div></div>' +
		'<div class="row m-0"><div class="text-end col-4 mb-2"><b>Creator: </b></div> <div class="text-start col-8">' + myfile.createdBy + '</div></div>' +
		'<div class="row m-0"><div class="text-end col-4 mb-2"><b>created Date: </b></div> <div class="text-start col-8">' + myfile.createdDate + '</div></div>' +
		'<div class="row m-0"><div class="text-end col-4 mb-2"><b>Modifier: </b></div> <div class="text-start col-8">' + myfile.lastModifiedBy + '</div></div>' +
		'<div class="row m-0"><div class="text-end col-4 mb-2"><b>Modified Date: </b></div> <div class="text-start col-8">' + myfile.lastModifiedDate + '</div></div>' +
		'<div class="row m-0"><div class="text-end col-4 mb-2"><b>MimeType: </b></div> <div class="text-start col-8">' + myfile.fileType + '</div></div>' +
		'</br>Auto closing in <b><timer></timer></b> milliseconds.';

	let timerInterval;

	Swal.fire({
		title: myfile.filename,
		icon: 'info',
		html: htmlstring,
		showCloseButton: true,
		showConfirmButton: false,
		showCancelButton: true,
		cancelButtonText: 'Close',
		cancelButtonColor: '#28a745',
		//allowOutsideClick: false,
		timer: 10000,
		timerProgressBar: true,

		didOpen: () => {
			//Swal.showLoading()
			const b = Swal.getHtmlContainer().querySelector('timer')
			timerInterval = setInterval(() => {
				b.textContent = Swal.getTimerLeft()
			}, 100)
		},
		willClose: () => {
			clearInterval(timerInterval)
		}
	})
}


function uploadMultipleInDbUsingSwal() {

	let htmlstring = '<input type="file" id="file-selector" multiple>';

	Swal.fire({
		title: 'Upload File(s)',
		icon: 'info',
		html: htmlstring,
		showDenyButton: true,
		denyButtonText: 'Cancel',
		showCloseButton: true,
		showConfirmButton: true,
		confirmButtonText: 'Upload',
		allowOutsideClick: false,
		showLoaderOnConfirm: true,
		preConfirm: () => {
			const fileselector = Swal.getPopup().querySelector('#file-selector').value
			if (!fileselector) {
				Swal.showValidationMessage(`Please select at least one or more file(s). !`)
			}
		}
	}).then((result) => {

		if (result.isConfirmed) {
			const fileselector = document.getElementById('file-selector');
			var files = fileselector.files;
			var formData = new FormData();
			for (var index = 0; index < files.length; index++) {
				formData.append("files", files[index]);
			}
			fetch('/uploadMultipleFiles', {
				method: 'POST',
				body: formData
			}).then((response) => {
				response.json();
				if (response.status == 200) {
					Swal.fire(
						'Uploaded!',
						'All files uploaded successfully.',
						//'All files uploaded successfully. Response Code: ' + response.status,
						'success'
					).then(() => {
						location.reload();
					});
				} else if (response.status == 405) {
					Swal.fire(
						'Upload failed!',
						'File(s) already exist(s). ',
						'error'
					).then(() => {
						location.reload();
					});
				}else {
					Swal.fire(
						'Upload Failed!',
						error,
						'error'
					)
				}

			}).catch((error) => {
				Swal.fire(
					'Upload Failed!',
					error,
					'error'
				)
			});

		} else if (result.isDenied) {
			Swal.fire('No file uploaded', 'Try again to upload files  !!  ', 'info')
		}
	})

}

function uploadMultipleInFsUsingSwal() {

	let htmlstring = '<input type="file" id="file-selector" multiple>';

	Swal.fire({
		title: 'Upload File(s)',
		icon: 'info',
		html: htmlstring,
		showDenyButton: true,
		denyButtonText: 'Cancel',
		showCloseButton: true,
		showConfirmButton: true,
		confirmButtonText: 'Upload',
		allowOutsideClick: false,
		showLoaderOnConfirm: true,
		preConfirm: () => {
			const fileselector = Swal.getPopup().querySelector('#file-selector').value
			if (!fileselector) {
				Swal.showValidationMessage(`Please select at least one or more file(s). !`)
			}
		}
	}).then((result) => {

		if (result.isConfirmed) {
			const fileselector = document.getElementById('file-selector');
			var files = fileselector.files;
			var formData = new FormData();
			for (var index = 0; index < files.length; index++) {
				formData.append("files", files[index]);
			}
			fetch('/fsupload', {
				method: 'POST',
				body: formData
			}).then((response) => {
				response.json();
				if (response.status == 200) {
					Swal.fire(
						'Uploaded!',
						'All files uploaded successfully.',
						//'All files uploaded successfully. Response Code: ' + response.status,
						'success'
					).then(() => {
						location.reload();
					});
				} else if (response.status == 405) {
					Swal.fire(
						'Upload failed!',
						'File(s) already exist(s). ',
						'error'
					).then(() => {
						location.reload();
					});
				}else {
					Swal.fire(
						'Upload Failed!',
						error,
						'error'
					)
				}

			}).catch((error) => {
				Swal.fire(
					'Upload Failed!',
					error,
					'error'
				)
			});

		} else if (result.isDenied) {
			Swal.fire('No file uploaded', 'Try again to upload files  !!  ', 'info')
		}
	})

}
