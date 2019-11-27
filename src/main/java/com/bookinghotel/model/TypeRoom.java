package com.bookinghotel.model;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name="typeroom")
public class TypeRoom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="typeid")
    private int typeid;

    @Column(name="type")
    private String type;

    @OneToMany(mappedBy = "typeroom",fetch = FetchType.LAZY)
    private Set<Room> rooms;

    public TypeRoom(){

    }

    public TypeRoom(String type) {
        this.type = type;
    }

    public int getTypeid() {
        return typeid;
    }

    public void setTypeid(int typeid) {
        this.typeid = typeid;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
