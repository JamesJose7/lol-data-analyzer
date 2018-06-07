package com.jeep.lolesports.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
public class Administrador {
    @Id
    private int id;
    @NotNull
    @Size(min = 4, max = 10, message = "Esta mal")
    private String user;
    @NotNull
    @Size(min = 4, max = 10, message = "Esta mal")
    private String password;

    public Administrador(int id, String user, String password) {
        this.id = id;
        this.user = user;
        this.password = password;
    }

    public Administrador() {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
