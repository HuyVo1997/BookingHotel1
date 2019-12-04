$(document).ready(function(){
    var name = $('#txtNameHotel');
    var price = $('#txtPrice');
    var email = $('#txtEmail');
    var phone = $('#txtPhone');
    var city = $('#txtCity');
    var province = $('#txtProvince');
    var address = $('#txtAddress');

    function addHotel() {
        $.ajax({
            type: "POST",
            url: "/add-hotel",
            data: {name: name,
                price: price,
                email: email,
                phone: phone,
                city: city,
                province: province,
                address: address},
            dataType: 'html',
            timeout: 600000,
            success: function (data){
                console.log(data);
            }
        });
    }
});

