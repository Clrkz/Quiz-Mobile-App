package com.example.quizapp;
public class Services {

    private String id;
    private String name;
    private String status;
    private String items;

    public Services(
                    String id,
                    String name,
                    String status,
                    String items) {
        this.id = id;
        this.name = name;
        this.status = status;
        this.items = items;
    }


    public String getid() {
        return id;
    }
    public void setid(String id) { this.id = id;  }

    public String getname() {
        return name;
    }
    public void setname(String name) { this.name = name;  }


    public String getstatus() {
        return status;
    }
    public void setstatus(String status) { this.status = status;  }


    public String getitems() {
        return items;
    }
    public void setitems(String items) { this.items = items;  }



}