$(document).ready(function () {
    $("#btnDelete").on("click", function (e) {
        e.preventDefault();
        var fileId = $(this).data("file-id");
        var confirmation = confirm("Are you sure you want to delete this file?");
        if (confirmation) {
            $.ajax({
                type: "DELETE",
                url: "/api/files/delete/" + fileId,
                success: function (response) {
                    $("#success-message").show(); // Show the success message div
                },
                error: function (error) {
                    alert("Error deleting file: " + error.responseText);
                }
            });
        }
    });
});