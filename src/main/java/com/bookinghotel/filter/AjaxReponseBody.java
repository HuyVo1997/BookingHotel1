package com.bookinghotel.filter;

import com.bookinghotel.model.Hotel;
import com.bookinghotel.model.Location;
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

    public List<Room> getResult() {
        return result;
    }

    public void setResult(List<Room> result) {
        this.result = result;
    }

    public List<Location> getLocationResult() {
        return locationResult;
}

    public void setLocationResult(List<Location> locationResult) {
        this.locationResult = locationResult;
    }

    List<Room> result;

    List<Location> locationResult;


}
