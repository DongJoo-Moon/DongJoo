package com.example.bookstore.ui.mypage;
/* ImageView buy_image;
        TextView buy_title;
        TextView buy_price;
        TextView buy_author;
        TextView buy_publisher;
        TextView buy_the_number;
        TextView buy_snumber;
        TextView buy_date;
        TextView buy_order;*/

public class mypage_item {
    //중고 게시판 양식
    String title;
    String price;
    String author;
    String publisher;

    String the_number;
    String shopping_S_number;
    String buy_date;
    String buy_order;

    String image;
    String buy_name;

    public mypage_item(){}

    public mypage_item(String title, String price,String author,String publisher,String the_number,String shopping_S_number,String buy_date,String buy_order,String image,String buy_name){
        this.title=title;
        this.price=price;
        this.author=author;
        this.publisher=publisher;

        this.the_number=the_number;
        this.shopping_S_number=shopping_S_number;
        this.buy_date=buy_date;
        this.buy_order=buy_order;

        this.image=image;
        this.buy_name=buy_name;


    }
    public String gettitle(){return title;}
    public String getprice(){return price;}
    public String getauthor(){return author;}
    public String getpublisher(){return publisher;}

    public String getthe_number(){ return the_number;}
    public String getshopping_S_number(){ return shopping_S_number;}
    public String getbuy_date(){return buy_date;}
    public String getbuy_order(){return buy_order;}

    public String getimage(){return image;}
    public String getbuy_name(){return buy_name;}

    public void settitle(String title){this.title=title;}
    public void setprice(String price){this.price=price;}
    public void setauthor(String author){this.author=author;}
    public void set_snumber(String publisher){this.publisher=publisher;}

    public void setthe_number(String the_number){this.the_number=the_number;}
    public void setshopping_S_number(String shopping_S_number){this.shopping_S_number=shopping_S_number;}
    public void setbuy_date(String buy_date){this.buy_date=buy_date;}
    public void setbuy_order(String buy_order){this.buy_order=buy_order;}

    public void setimage(String image){this.image=image;}
    public void setbuy_name(String buy_name){this.buy_name=buy_name;}

}
