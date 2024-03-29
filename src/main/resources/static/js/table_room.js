$(document).ready(function(){
    $('#example4').DataTable();
    $("#myselect").change(function() {
        var selectedVal = $("#myselect option:selected").val();
        $.ajax({
            type: "POST",
            url: "/all_rooms",
            data: {id: selectedVal},
            dataType: 'json',
            success: function (data){
                console.log(data);
                $('#example4').dataTable({
                    destroy :true,
                    data: data.result,
                    columns:[
                        {data : 'typeroom.type',className: "center"},
                        {data : 'title',className: "center"},
                        {data : 'numofadults',className: "center"},
                        {data : 'numofchild',className: "center"},
                        {data : 'numofbed',className: "center"},
                        {data : 'price',className: "center"},
                        {
                            data: null,
                            className: "center",
                            render: function (data, type, row) {
                                return '<a href=\'/bussiness/update-room/'+ data["roomid"] +'\' class=\"btn btn-tbl-edit btn-xs\"><i class=\"fa fa-pencil\"></i></a>'
                            }
                        },

                        {
                            data: null,
                            className: "center",
                            render: function (data, type, row) {
                                return '<a href=\'/delete-room/'+ data["roomid"] +'\' class=\"btn btn-tbl-delete btn-xs\"><i class=\"fa fa-trash-o \"></i></a>'
                            }
                        }
                    ]
                })
            },
        });
    });

})

