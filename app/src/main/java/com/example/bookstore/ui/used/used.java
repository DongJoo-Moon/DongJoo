package com.example.bookstore.ui.used;

public class used {
    //중고 게시판 양식
    String title;
    String content;
    String id;
    String snum;
    String date;
    String price;
    String profile;
    String used_number;
    String sale;

    public used(){}

    public used(String title, String content, String id, String price, String snum, String date, String profile , String used_number,String sale){
        this.title=title;
        this.content=content;
        this.id=id;
        this.price=price;
        this.snum=snum;
        this.date=date;
        this.profile=profile;
        this.used_number=used_number;
        this.sale=sale;


    }
    public String gettitle(){return title;}
    public String getcontent(){return content;}
    public String getid(){return id;}
    public String getprice(){return price;}
    public String getsnum(){ return snum;}
    public String getdate(){ return date;}
    public String getprofile(){return profile;}
    public String getused_number(){return used_number;}
    public String getsale(){return sale;}

    public void settitle(String title){this.title=title;}
    public void setcontent(String content){this.content=content;}
    public void setid(String id){this.id=id;}
    public void setprice(String price){this.price=price;}
    public void setsnum(String snum){this.snum=snum;}
    public void setdate(String date){this.date=date;}
    public void setprofile(String profile){this.profile=profile;}
    public void setused_number(String used_number){this.used_number=used_number;}
    public void setsale(String sale){this.sale=sale;}

}
