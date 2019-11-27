$(document).ready(function(){
    var dateStart = new Date($('#dateStart').val());
    var dateEnd = new Date ($('#dateEnd').val());
    var price = $('#price').val();
    var quantity = $('#quantity').val();

    $('#monthDateStart').html(GetMonthName(dateStart.getMonth()) + ", " + dateStart.getDate());
    $('#DayStart').html(GetDay(dateStart.getDay()));
    $('#monthDateEnd').html(GetMonthName(dateEnd.getMonth()) + ", " + dateEnd.getDate());
    $('#DayEnd').html(GetDay(dateEnd.getDay()));

    $('#monthDateStart1').html(GetMonthName(dateStart.getMonth()) + ", " + dateStart.getDate());
    $('#DayStart1').html(GetDay(dateStart.getDay()));
    $('#monthDateEnd1').html(GetMonthName(dateEnd.getMonth()) + ", " + dateEnd.getDate());
    $('#DayEnd1').html(GetDay(dateEnd.getDay()));

    $('#numDate').html(CaculatorDate(dateStart,dateEnd));
    $('#numDate1').html(CaculatorDate(dateStart,dateEnd));
    $('#numDate2').html(CaculatorDate(dateStart,dateEnd));
    $('#numDate3').html(CaculatorDate(dateStart,dateEnd));


});

function GetMonthName(monthNumber) {
    var months = ['January', 'February', 'March', 'April', 'May', 'June', 'July', 'August', 'September', 'October', 'November', 'December'];
    return months[monthNumber];
}

function GetDay(date){
    var weekday = new Array(7);
    weekday[0] = "Sunday";
    weekday[1] = "Monday";
    weekday[2] = "Tuesday";
    weekday[3] = "Wednesday";
    weekday[4] = "Thursday";
    weekday[5] = "Friday";
    weekday[6] = "Saturday";
    return weekday[date];
}

function CaculatorDate(start,end){
    var diff  = new Date(end - start)
    var days  = diff/1000/60/60/24;
    return days;
}