$(document).ready(function(){
    function loadDataByAjax(page){
    $.ajax({
        type: 'get',
        url: '/hotelResultPage',
        data: {id : page},
        dataType: 'html',
        success: function(data){
            $(".booking-list").html(data);
        }
    });
}
    loadDataByAjax(0);

    $('.page').on('click',function(){
        loadDataByAjax(this.id);
    });

    $('.pagination li').removeClass('active');
    $('.pagination li').first().addClass('active');

    $('.pagination li').click(function(){
        $('.pagination li.active').removeClass('active');
        $(this).addClass('active');
    });

});

