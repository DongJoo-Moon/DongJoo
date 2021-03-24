# DongJoo
bookstore (대학교 졸업작품 프로젝트)

프로젝트를 계획한 이유
> 저희 대학교는 학기초나 2학기가 시작되면 많은 사람들이 학교 내에 위치한 구내서점으로 전공서적을 사러 갑니다.
> 구내 서점의 위치가 가까운 단과대도 존재하고, 몇 십분을 걸어야하는 단과대도 존재합니다. 특히, 여름이 되면
> 땀을 흘리면서 사러 가야한다는 불편한 점과 막상 구내서점에 도착해도 학생들이 많아서 자기가 원하는 책을 사려면
> 오래 기다려야 했습니다. 이런 불편한 점을 완하시키기 위해 학교 구내서점 어플을 만들어 배달을 이용하거나 
> 어플을 이용해 미리 원하는 책을 픽업해둬서 구내서점을 찾아가게 되면 기다리지 않고 바로 찾아갈 수 있게 해서
> 불편한 점을 완하시키려고 만들었습니다.

> 이 어플을 다양한 기능을 가지고 있습니다. 크게는 메인화면, 중고서비스, 고객센터로 나누어지게 됩니다. 
> 메인화면에서는 시중에 나와있는 도서 어플처럼 누구나 손쉽게 이용할 수 있습니다. 특히, 도서 어플에서 자주
> 사용하는 장바구니, 책 검색 ..등이 주된 기능입니다.
> 중고서비스에서는 어플 사용자가 중고 책 거래할 때 사용하는 부분입니다. 중고 책을 살 수도 있고, 팔 수도 있는
> 중고 책에 관한 다양한 기능을 할 수 있는 부분입니다. 여기서 사고자하는 사용자와 팔고자하는 사용자는 따로 연락
> 할 필요없이 어플안에 있는 채팅 기능을 통해 쉽게 연락하여 이용할 수 있습니다.
> 고객센터부분에서는 어플의 문제점이나 자주 묻는 질문에 대해서 볼 수 있습니다.

## 내가 구현한 bookstore 기능 및 기술 설명
<hr/>

+ 로그인 기능

<img src="https://user-images.githubusercontent.com/80870181/112103867-bc718b80-8bed-11eb-8268-4cb90e29a440.png" width="400" height="400">
</img> 

> 학번과 비밀번호를 입력할 수 있는 인터페이스를 통해 어플 사용자의 학번과 비밀번호를 입력하게 되면 화면 전환(intent) 되도록 설정되어 있습니다.
> 여기에서 학번 값과 비밀번호 값은 int형이 포함되어 있기 때문에 파이어베이스에 있는 정보와 비교하기 위해서는 string형 변환이 필요해 아래의 코드와
> 같이 변환시켜서 비교하게 됩니다. 
> 만약 어플 사용자가 틀린 학번이나 비밀번호를 입력하게 되면 Toast 기능을 통해 어플 하단에 경고 메시지가 나타나게 됩니다.

 <pre>
 <code>
 loginButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                final String id = EditText_id.getText().toString(); // 내가 입력한 데이터 값
                final String pw = EditText_password.getText().toString();
                databaseReference.child(id).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Iterator<DataSnapshot> child = dataSnapshot.getChildren().iterator(); //users의 모든 자식들의 key값과 value 값들을 iterator로 참조합니다.
                        if(dataSnapshot.getChildrenCount() > 0 ){
                            String fbPw = dataSnapshot.child("password").getValue().toString(); //파이어베이스에 있는 데이터 값
                            String fbname = dataSnapshot.child("name").getValue().toString(); //firebase에서 name의 Value값을 get하고 string 시킴.

                            if(fbPw.equals(pw)){ // 값 비교하는 부분
                                Intent intent = new Intent(loginActivity.this, MainActivity.class);

                                id_name = fbname;
                                id_Snum = id;
                                intent.putExtra("name", id_name);
                                finish();

                                Toast.makeText(loginActivity.this,fbname+"님 어서오세요.",Toast.LENGTH_LONG).show(); // 로그인 성공 시
                                startActivity(intent); //메인화면으로 전환
                            }else{
                                Toast.makeText(loginActivity.this,"비밀번호를 틀리셧습니다.",Toast.LENGTH_LONG).show();
                            }
                        }else{
                            Toast.makeText(loginActivity.this,"학번과 비밀번호를 확인하십쇼",Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        });
  </code>
  </pre>
  
<img src="https://user-images.githubusercontent.com/80870181/112268394-425b0880-8cba-11eb-87b8-a562d6f6d049.PNG" width="400" height="400">
</img>

> 위는 파이어베이스에 담겨져 있는 학번과 비밀번호를 저장하는 곳에 있는 key 값과 value 값이다. 
> 로그인을 하기 위해 값을 비교할 때는 아이디의 value 값과 password의 value 값을 어플을 사용하는 사용자가 입력한 값과 비교하여 맞으면 
> 로그인이 되는 구조이다.

<hr/>

+ 단과대 선택 및 도서 구매,장바구니

> 먼저 단과대 선택은 어플 사용자들의 다양한 과들을 고려하여 각 단과대를 클릭하면 단과대에 있는 과가 팝업(popup menu)창 형식으로 뜨게 됩니다. 
> 거기서 원하는 과를 선택하여 들어가게 되면 그 학과의 전공책을 볼 수 있고, 구매하거나 장바구니에 담을 수 있습니다.
> 팝업 창과 어플 메인화면은 다음과 같습니다.

<img src="https://user-images.githubusercontent.com/80870181/112269028-2f950380-8cbb-11eb-9000-5e7dafc33d9b.png" width="400" height="400">
</img>

> 자신이 원하는 단과대(item)를 선택하면 발생하는 'popup.setOnMenuItemClickListener'를 이용하여 원하는 과를 눌럿을 때 intent 되도록 하였습니다.

<img src="https://user-images.githubusercontent.com/80870181/112122842-06656c00-8c04-11eb-8dab-30f4ed4ae2d6.png" width="400" height="400">
</img> 

> 책 정보 같은 경우에는 firebase에 있는 정보를 가져와서 자바의 배열을 쓰기보다는 가변성이 좋은 arrayList를 사용해 공간이 늘어날 때마다 능동적으로 대처할 수 있도록
> 했습니다. 책 이름, 책 이미지, 책 저자, 출판사 .. 등 정보를 나타내기 위해서는 recyclerview를 사용해 0번부터 있는 정보를 나타내도록 하였습니다. 
> 그 부분에 대한 코드가 아래와 같습니다.

<pre>
<code>
DatabaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    //파이어베이스 데이터베이스의 데이터를 받아오는 곳
                    arrayList.clear();//기존 배열리스트 초기화
                    for(DataSnapshot snapshot : dataSnapshot.getChildren()){ //반복문으로 데이터 list 추출
                        book Book = snapshot.getValue(book.class); //만들어둿던 book객체에 데이터를 담는다.
                        arrayList.add(Book);  //담은 데이터들을 배열리스트에 넣고 리사이클러뷰로 보낼준비
                    }
                    adapter.notifyDataSetChanged();//리스트 저장 및 새로고침
                }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                //db가져오던중 에러 발생
                Log.e("computer_book", String.valueOf(databaseError.toException())); //에러문 출력
            }
        });
        adapter = new MyBook(arrayList,this);
        recyclerView.setAdapter(adapter);//리사이클러뷰에 어댑터 연결
 </code>
 </pre>

> 책에 대한 정보 ArrayList에 대한 코드는 다음과 같습니다.

<pre>
<code>
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
</code>
</pre>

> 아래는 ArrayList에 대한 정보와 일치한 파이어베이스 데이터입니다.

<img src="https://user-images.githubusercontent.com/80870181/112129895-36643d80-8c0b-11eb-8833-4b290699aeea.PNG" width="400" height="400">
</img>

<hr/>

+ 검색(search) 기능

> 어플 사용자가 원하는 도서를 쉽게 찾을 수 있도록 하는 기능입니다. 실행하는 방법은 어플 상단 오른쪽의 돋보기 모양을 클릭하면 됩니다.
> 나타내는 방법은 아까 위의 학과 별 도서를 나타내는 방법과 매우 유사합니다.(recyclerview 와 ArrayList를 활용)
> 다른 점은 사용자가 검색할려는 대상이 파이어베이스에 존재하는 도서인지를 확인하는 if 문이 달라졋을 뿐입니다.  
> 다음 코드가 검색 기능 부분입니다.

<pre>
<code>
DatabaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //파이어베이스 데이터베이스의 데이터를 받아오는 곳
                arrayList.clear();//기존 배열리스트 초기화
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {//반복문으로 데이터 list 추출
                    if(snapshot.child("title").getValue().toString().equals(book_name)){
                        book Book = snapshot.getValue(book.class);//만들어둿던 book객체에 데이터를 담는다.
                        arrayList.add(Book);//담은 데이터들을 배열리스트에 넣고 리사이클러뷰로 보낼준비
                    }
                }
                adapter.notifyDataSetChanged();//리스트 저장 및 새로고침
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                //db가져오던중 에러 발생
                Log.e("search_book", String.valueOf(databaseError.toException())); //에러문 출력
            }
        });
        adapter = new MyBook(arrayList, this);

        recyclerView.setAdapter(adapter);
</code>
</pre>

> 검색 기능 부분도 똑같이 구매하거나 장바구니에 담을 수 있는 기능을 수행할 수 있습니다.
> 검색하면 아래 사진과 같이 나오게 됩니다.
 
<img src="https://user-images.githubusercontent.com/80870181/112151237-58b68500-8c24-11eb-84ff-e5e030aa8b43.png" width="400" height="400">
</img>

<hr/>

+ 장바구니 기능
> 도서 어플의 대표적인 기능이라고 볼 수 있는 장바구니 기능입니다. 이용하는 방법은 간단합니다. 책에 대해 검색을 하거나 책에 대한 정보를 알아 볼때 구매하기 옆에 항상 장바구니로 
> 넣을 수 있도록 버튼 형식으로 구현시켜놨습니다. 장바구니 버튼을 누르면 안드로이드의 alertDialog 가 떠서 몇 개를 주문 할지를 정할 수 있는 경고창이 뜹니다. 여기서 수량을 정하고
> 확인을 누르면 어플 사용자의 장바구니에 원하는 품목의 책과 수량이 담기게 됩니다.
> 장바구니 버튼을 클릭했을 때 alertDialog는 다음과 같다. 재고량보다 초과하여 장바구니에 담을 시에도 alertDialog가 뜨게 되어있다.

<img src="https://user-images.githubusercontent.com/80870181/112178911-80fead80-8c3d-11eb-9074-e6ca9c3d464f.png" width="400" height="400">
</img>

> 다음은 자신의 장바구니에 원하는 품목이 담겻는 지 확인하는 사진이다. 잘못 담긴 부분은 삭제도 가능하며, 장바구니에 있는 책들의 총 가격도 포함되어있습니다.

<img src="https://user-images.githubusercontent.com/80870181/112178917-822fda80-8c3d-11eb-97ed-b2c412b11ad0.png" width="400" height="400">
</img>

> 먼저 장바구니 버튼을 클릭하면 발생하는 'setOnClickListener'를 이용하여 화면의 이동없이 AlertDialog 형식으로 바로 개수를 정할 수 있도록 하였습니다.
> 해당하는 코드는 아래와 같습니다.
<pre>
<code>
holder.book_shoppingbtn.setOnClickListener(new View.OnClickListener() {//장바구니 클릭시
            @Override
            public void onClick(View v) { //장바구니 버튼을 클릭했을 시
                position1 = position;
                title = holder.book_title.getText().toString();
                price = holder.book_price.getText().toString();
                author = holder.book_author.getText().toString();
                publisher = holder.book_publisher.getText().toString();
                image = arrayList.get(position1).getimage();

                final EditText edittext = new EditText(context);
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("장바구니 개수 입력");
                builder.setMessage("개수를 입력해주세요.");
                builder.setView(edittext);
                builder.setPositiveButton("입력",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                the_number = edittext.getText().toString();
                                Map<String, Object> mHashmap = new HashMap<>(); //HashMap 은 해시함수를 통해 키와 값을 매핑하는 방식으로 데이터를 다루는 자료구조 알고리즘을 구현한 클래스 입니다.

                                mHashmap.put("title", title);
                                mHashmap.put("price", price);
                                mHashmap.put("author", author);
                                mHashmap.put("publisher", publisher);
                                mHashmap.put("image",  image);
                                mHashmap.put("shopping_S_number",snum);
                                mHashmap.put("the_number",the_number);

                                DatabaseReference1.child(snum).child(title).updateChildren(mHashmap);

                                Toast.makeText(context, title + "이 " + "장바구니에 담겼습니다.", Toast.LENGTH_SHORT).show();
                            }
                        });
                builder.setNegativeButton("취소",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });
                builder.show();
            }
        });
</code>
</pre>

> 위의 코드에서 ArrayList를 쓰지않고 HashMap을 쓴 이유는 ArrayList밖에 몰랐던 점도 있지만 HashMap이 Key-Value 쌍으로 나타내기 좋은 점과 Key값으로 Value값을 탐색할 수 있다는
> 점에서 HashMap을 사용하여 장바구니에 있는 책에 대한 데이터를 나타내었습니다. 위의 코드와 같이 장바구니에 담는 걸 선택하면 파이어베이스는 다음과 같이 업데이트가 됩니다.

<img src="https://user-images.githubusercontent.com/80870181/112314627-92eb5980-8cec-11eb-86c4-f9547b270fb2.PNG" width="400" height="400">
</img>
<hr/>
