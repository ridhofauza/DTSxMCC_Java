$(document).ready(function () {
    $('#table-region').DataTable({
        ajax: {
            url: 'api/region',
            dataSrc: ''
        },
        columns: [{
                data: null,
                render: (data, type, row, meta) => {
                    return meta.row + 1
                }
            },
            {
                data: 'name'
            },
            {
                data: null,
                render: (data, type, row, meta) => {
                    return `
                        <button type="button" class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#detailRegion" onclick="findById(${data.id})">
                        Detail
                        </button>

                        <button type="button" class="btn btn-warning" data-bs-toggle="modal" data-bs-target="#updateRegion" onclick="beforeUpdate(${data.id})">
                        Update
                        </button>

                         <button type="button" class="btn btn-danger" onclick="deleteRegion(${data.id})">
                        Delete
                        </button>
                    `
                }
            }
        ]
    });
});

$('#create-region').click((e) => {
    e.preventDefault()

    let name = $("#regionName").val()
    console.log(name)
    if (name === "" || name === null) {
        Swal.fire({
            position: 'center',
            icon: 'error',
            title: 'Please fill all field',
            showConfirmButton: false,
            timer: 1000
        })
    } else {
        $.ajax({
            method: "POST",
            url: "api/region",
            dataType: "JSON",
            contentType: "application/json",
            beforeSend: addCsrfToken(),
            data: JSON.stringify({
                name: name
            }),
            success: (result) => {
                $('#createRegion').modal('hide')
                $('#table-region').DataTable().ajax.reload()
                $('#regionName').val()
                Swal.fire({
                    position: 'center',
                    icon: 'success',
                    title: 'Region has been created',
                    showConfirmButton: false,
                    timer: 2000
                })
            },
            error: (result) => {
                Swal.fire({
                    position: 'center',
                    icon: 'error',
                    title: 'Region name already exists',
                    showConfirmButton: false,
                    timer: 1000
                })
            }
        })
    }
})

function findById(id) {
    $.ajax({
        method: "GET",
        url: "api/region/" + id,
        dataType: "json",
        success: (result) => {
            $("#region-id").text(`${result.id}`)
            $("#region-name").text(`${result.name}`)
        }
    })
}

function beforeUpdate(id) {
    $.ajax({
        method: "GET",
        url: "api/region/" + id,
        dataType: "json",
        success: (result) => {
            $("#update-region-id").val(`${result.id}`)
            $("#update-region-name").val(`${result.name}`)
        }
    })
}

$('#update-region').click((e) => {
    e.preventDefault()

    let id = $("#update-region-id").val()
    let name = $("#update-region-name").val()

    if (name === "" || name === null) {
        Swal.fire({
            position: 'center',
            icon: 'error',
            title: 'Please fill all field',
            showConfirmButton: false,
            timer: 1000
        })
    } else {
        $.ajax({
            method: "PUT",
            url: "api/region/" + id,
            dataType: "json",
            beforeSend: addCsrfToken(),
            data: JSON.stringify({
                name: name
            }),
            contentType: "application/json",
            success: (result) => {
                $('#updateRegion').modal('hide')
                $('#table-region').DataTable().ajax.reload()
                $('#update-region-name').val("")
                $('#update-region-id').val("")
                Swal.fire({
                    position: 'center',
                    icon: 'success',
                    title: 'Region has been updated',
                    showConfirmButton: false,
                    timer: 2000
                })
            }
        })
    }
})

function deleteRegion(id) {
    Swal.fire({
        title: 'Are you sure?',
        text: "You won't be able to delete this region!",
        icon: 'warning',
        showCancelButton: true,
        confirmButtonColor: '#3085d6',
        cancelButtonColor: '#d33',
        confirmButtonText: 'Yes, delete it!'
    }).then((result) => {
        if (result.isConfirmed) {
            $.ajax({
                method: "DELETE",
                url: "api/region/" + id,
                dataType: "json",
                beforeSend: addCsrfToken(),
                success: (result) => {
                    $('#table-region').DataTable().ajax.reload()
                    Swal.fire({
                        icon: "success",
                        title: 'Region has been deleted',
                        width: 600,
                        padding: '3em',
                        color: 'black',
                        background: '#fff',
                        showConfirmButton: false,
                        timer: 2000,
                        backdrop: `
                        rgba(0,0,123,0.4)
                        url("https://sweetalert2.github.io/images/nyan-cat.gif")
                        left top
                        no-repeat
                    `
                    })
                }
            })
        }
    })
}