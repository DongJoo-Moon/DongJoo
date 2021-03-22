package com.example.bookstore.ui.book;

public class book {
    //책 정보 양식
    String title;
    String price;
    String image;
    String author;
    String publisher;
    String stock;

    public book(){}

    public book(String title,String price,String image,String author, String publisher, String stock){
        this.title=title;
        this.price=price;
        this.image=image;
        this.author=author;
        this.publisher=publisher;
        this.stock=stock;
    }


    public String gettitle(){return title;}
    public String getimage(){return image;}
    public String getauthor(){return author;}
    public String getpublisher(){return publisher;}
    public String getprice(){return price;}
    public String getstock(){return stock;}


    public void settitle(String title){this.title=title;}
    public void setprice(String price){this.price=price;}
    public void setimage(String image){this.image=image;}
    public void setauthor(String author){this.author=author;}
    public void setpublisher(String publisher){this.publisher=publisher;}
    public void setstock(String stock){this.stock=stock;}

}