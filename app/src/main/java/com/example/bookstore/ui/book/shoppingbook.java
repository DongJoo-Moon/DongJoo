package com.example.bookstore.ui.book;

public class shoppingbook {
    //책 정보 양식
    String title;
    String price;
    String image;
    String author;
    String publisher;
    String shopping_S_number;
    String the_number;

    public shoppingbook(){}

    public shoppingbook(String title, String price, String image, String author, String publisher,String shopping_S_number,String the_number){
        this.title=title;
        this.price=price;
        this.image=image;
        this.author=author;
        this.publisher=publisher;
        this.shopping_S_number=shopping_S_number;
        this.the_number=the_number;
    }


    public String gettitle(){return title;}
    public String getimage(){return image;}
    public String getauthor(){return author;}
    public String getpublisher(){return publisher;}
    public String getprice(){return price;}
    public String getshopping_S_number(){return shopping_S_number;}
    public String getthe_number(){return the_number;}


    public void settitle(String title){this.title=title;}
    public void setprice(String price){this.price=price;}
    public void setimage(String image){this.image=image;}
    public void setauthor(String author){this.author=author;}
    public void setshopping_S_number(String shopping_S_number){this.shopping_S_number=shopping_S_number;}
    public void the_number(String the_number){this.the_number=the_number;}

}