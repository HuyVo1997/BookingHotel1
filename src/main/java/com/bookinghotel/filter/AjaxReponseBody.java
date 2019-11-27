package com.bookinghotel.filter;

import com.bookinghotel.model.Hotel;
import com.bookinghotel.model.Room;

import java.util.List;

public class AjaxReponseBody {
    String msg;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<Hotel> getResult() {
        return result;
    }

    public void setResult(List<Hotel> result) {
        this.result = result;
    }

    List<Hotel> result;

}
