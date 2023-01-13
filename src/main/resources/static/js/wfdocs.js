'use strict';



function nextStepDocWorkflow(id, Object) {
	Swal.fire({
		title: 'Are you sure?',
		text: "You want to Foraward this to Next Step?",
		icon: 'warning',
		showDenyButton: true,
		confirmButtonColor: '#006666',
		denyButtonColor: 'gray',
		confirmButtonText: 'Yes, forward it!',
		denyButtonText: `Don't forward`,
	}).then((result) => {
		if (result.isConfirmed) {
			fetch('/nextStepDocWorkflow/' + id, {
				method: 'PUT',
			})
			Swal.fire(
				'Forwarded!',
				'Your file has been forwarded to next step.',
				'success'
			)
			Object.closest('tr').remove();
		} else if (result.isDenied) {
			Swal.fire('No changes made.', 'Your file is at same step as it was before !!  ', 'info')
		}
	})
}

function nextStepDocWorkflowxx(id, Object) {
	fetch('/nextStepDocWorkflow/' + id, {
		method: 'PUT',
	})
		.then((response) => {
			//alert(response.status);
			if (response.status == 204) {
				Object.closest('tr').remove();
			}
			//alert(response.status);
			//alert('Success:', response);
		})
		.catch((error) => {
			alert('Error:', error);
		});
}

function previousStepDocWorkflow(id, Object) {
	Swal.fire({
		title: 'Are you sure?',
		text: "You want to Reject this to Previous Step?",
		icon: 'warning',
		showDenyButton: true,
		confirmButtonColor: '#006666',
		denyButtonColor: 'gray',
		confirmButtonText: 'Yes, reject it!',
		denyButtonText: `Don't reject`,
	}).then((result) => {
		if (result.isConfirmed) {
			fetch('/previousStepDocWorkflow/' + id, {
				method: 'PUT',
			})
			Swal.fire(
				'Rejected !',
				'Your file has been rejected to previous step.',
				'success'
			)
			Object.closest('tr').remove();
		} else if (result.isDenied) {
			Swal.fire('No changes made.', 'Your file is at same step as it was before !!  ', 'info')
		}
	})
}

function previousStepDocWorkflowxx(id, Object) {
	fetch('/previousStepDocWorkflow/' + id, {
		method: 'PUT',
	})
		.then((response) => {
			//alert(response.status);
			if (response.status == 204) {
				Object.closest('tr').remove();
			}
			//alert(response.status);
			//alert('Success:', response);
		})
		.catch((error) => {
			alert('Error:', error);
		});
}

function deleteallWFfiles() {
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
			fetch('/deleteallWFfiles', {
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


function deleteWFfile(id, Object) {
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
			fetch('/deleteWFfile/' + id, {
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

function deleteWFfileVanilla(id, Object) {
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



function showWFFileInfo(myfile) {
	
	let htmlstring = '<div class="row m-0"><div class="text-end col-4 mb-2"><b>Id: </b></div> <div class="text-start col-8">' + myfile.id + '</div></div>' +
		'<div class="row m-0" ><div class="text-end col-4 mb-2"><b>Status: </b></div> <div class="text-start col-8 fs-2 fw-bolder">' + myfile.workflowstatus + '</div></div > ' +
		'<div class="row m-0"><div class="text-end col-4 mb-2"><b>Unique Id: </b></div> <div class="text-start col-8">' + myfile.fileuuid + '</div></div>' +
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
		cancelButtonColor: '#006666',
		//allowOutsideClick: false,
		//width: '40%',
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


function updateWFDocumentContent(id) {
	//alert("Content will be update for Id: " + id);
	let htmlstring = '<input type="file" id="file-selector">';
	Swal.fire({
		title: 'Are you sure to update content?',
		html: htmlstring,
		icon: 'warning',
		showDenyButton: true,
		confirmButtonColor: '#006666',
		denyButtonColor: 'gray',
		confirmButtonText: 'Yes, update content !',
		denyButtonText: `Don't update`,
		preConfirm: () => {
			const fileselector = Swal.getPopup().querySelector('#file-selector').value
			if (!fileselector) {
				Swal.showValidationMessage(`Please select a file for content update. !`)
			}
		}
	}).then((result) => {
		if (result.isConfirmed) {
			const fileselector = document.getElementById('file-selector');
			var files = fileselector.files;
			var formData = new FormData();
			formData.append("file", files[0]);
			fetch('/updateAppWFDocument/' + id, {
				method: 'POST',
				body: formData
			}).then((response) => {
				response.json();
				if (response.status == 200) {
					Swal.fire(
						'Uploaded!',
						'Content updated successfully.',
						//'All files uploaded successfully. Response Code: ' + response.status,
						'success'
					).then(() => {
						location.reload();
					});
				} else {
					Swal.fire(
						'Content update failed!',
						error,
						'error'
					)
				}

			}).catch((error) => {
				Swal.fire(
					'Content update failed!',
					error,
					'error'
				)
			});
		} else if (result.isDenied) {
			Swal.fire('Great. No changes are saved.', 'Try at will to make update  !!  ', 'info')
		}
	})
}

function uploadMultipleInDocFlowUsingSwal() {

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
			fetch('/uploadMultipleWFFiles', {
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
				} else {
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
