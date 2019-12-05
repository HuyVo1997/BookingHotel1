package com.bookinghotel.model;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "bussiness")
public class Bussiness {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bussinessid")
    private Integer bussinessid;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name="email")
    private String email;

    @ManyToMany
    @JoinTable(
            name = "bussiness_role",
            joinColumns = @JoinColumn(name = "bussinessid"),
            inverseJoinColumns = @JoinColumn(name = "roleid")
    )
    private Set<Role> roles;

    @OneToMany(mappedBy = "bussiness",fetch=FetchType.LAZY)
    private Set<Hotel> hotels;

    public Bussiness() {
    }

    public Bussiness(String username, String password, String email, Set<Role> roles, Set<Hotel> hotels) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.roles = roles;
        this.hotels = hotels;
    }

    public Integer getBussinessid() {
        return bussinessid;
    }

    public void setBussinessid(Integer bussinessid) {
        this.bussinessid = bussinessid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public Set<Hotel> getHotels() {
        return hotels;
    }

    public void setHotels(Set<Hotel> hotels) {
        this.hotels = hotels;
    }
}
