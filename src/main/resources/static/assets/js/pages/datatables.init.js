$(document).ready(function () {
    $("#datatable-buttons").DataTable({
        lengthChange: !1,
        buttons: ["copy", "excel", "pdf", "colvis"]
    }).buttons().container().appendTo("#datatable-buttons_wrapper .col-md-6:eq(0)"), $(".dataTables_length select").addClass("form-select form-select-sm")
});

$(document).ready(function () {
    $("#datatable-buttons-1").DataTable({
        lengthChange: !1,
        buttons: ["copy", "excel", "pdf", "colvis"]
    }).buttons().container().appendTo("#datatable-buttons-1_wrapper .col-md-6:eq(0)"), $(".dataTables_length select").addClass("form-select form-select-sm")
});

$(document).ready(function () {
    $("#datatable-buttons-2").DataTable({
        scrollX: false,
        lengthChange: !1,
        buttons: ["copy", "excel", "pdf", "colvis"]
    }).buttons().container().appendTo("#datatable-buttons-2_wrapper .col-md-6:eq(0)"), $(".dataTables_length select").addClass("form-select form-select-sm")
});


$(document).ready(function () {
    $("#datatable-buttons-3").DataTable({
        lengthChange: !1,
        buttons: ["copy", "excel", "pdf"]
    }).buttons().container().appendTo("#datatable-buttons-3_wrapper .col-md-6:eq(0)"), $(".dataTables_length select").addClass("form-select form-select-sm")
});


$(document).ready(function () {
    $("#datatable-buttons-4").DataTable({
        lengthChange: !1,
        buttons: ["copy", "excel", "pdf"]
    }).buttons().container().appendTo("#datatable-buttons-3_wrapper .col-md-6:eq(0)"), $(".dataTables_length select").addClass("form-select form-select-sm")
});

