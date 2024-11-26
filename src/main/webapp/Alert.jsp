<script src="https://cdn.jsdelivr.net/npm/sweetalert2@11" defer="defer"></script>
<script type="text/javascript" defer="defer">
	function showAlert(title) {
		Swal.fire({
			title: title || 'Oops' ,
			text : title,
			icon : 'success',
		});
	}
</script>