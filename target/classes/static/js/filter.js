$(document).ready(function () {
    var location = "Đà Nẵng";
    var price = $('#price').val();
    var star = $('#star').val();
    var hservice = $('#hservice').val();
    var remove = 0;
    var option = 0;
    var reload = 0;
    loadData();

    $('input[type="checkbox"], input[type="radio"]').on('ifChanged', function (e) {
        $(this).trigger("onclick", e);
        if(this.checked){
            remove = 0;
            if(this.id <= 5){
                option = 1;
                star = this.id;
            }
            if(this.id >= 6 && this.id <= 15){
                option = 2;
                hservice = $('#'+this.id).val();
            }
            reload = 0;
            loadData();
        }
        else{
            remove = 1;
            if(this.id <= 5){
                option = 3;
                star = this.id;
            }
            if((this.id >= 6 && this.id <= 15)){
                option = 4;
                hservice = $('#'+this.id).val();
            }
            reload = 0;
            loadData();
        }
    });

    function loadData() {
        $.ajax({
            type: "POST",
            url: "/hotelResult",
            data: {location: location,
                price: price,
                star: star,
                hservice: hservice,
                remove: remove,
                option: option,
                reload: reload},
            dataType: 'html',
            timeout: 600000,
            success: function (data){
                $('.booking-list').html(data);
            }
        });
    }

    $(window).on('load', function(){
        reload = 1;
        loadData();
    });
});


