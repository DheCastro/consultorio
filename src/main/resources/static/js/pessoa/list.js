$(document).ready(function () {
    $('#dataTable').DataTable({
		processing: true,
		serverSide: true,
		responsive: true,
		ajax: {
			url: "/consultorio/pessoas/datatables/server",
			data: "data"
		},
		lengthMenu: [ 10, 15, 20, 25 ],
		columns: [
			{data: 'nome'},
			{data: 'cpf'}
		],
		dom: 'Bfrtip'
	});
});