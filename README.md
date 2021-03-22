# DongJoo
bookstore (대학교 졸업작품 프로젝트)

프로젝트를 계획한 이유
> 저희 대학교는 학기초나 2학기가 시작되면 많은 사람들이 학교 내에 위치한 구내서점으로 전공서적을 사러 갑니다.
> 구내 서점의 위치가 가까운 단과대도 존재하고, 몇 십분을 걸어야하는 단과대도 존재합니다. 특히, 여름이 되면
> 땀을 흘리면서 사러 가야한다는 불편한 점과 막상 구내서점에 도착해도 학생들이 많아서 자기가 원하는 책을 사려면
> 오래 기다려야 했습니다. 이런 불편한 점을 완하시키기 위해 학교 구내서점 어플을 만들어 배달을 이용하거나 
> 어플을 이용해 미리 원하는 책을 픽업해둬서 구내서점을 찾아가게 되면 기다리지 않고 바로 찾아갈 수 있게 해서
> 불편한 점을 완하시키려고 만들었습니다.
> 

> 이 어플을 다양한 기능을 가지고 있습니다. 크게는 메인화면, 중고서비스, 고객센터로 나누어지게 됩니다. 
> 메인화면에서는 시중에 나와있는 도서 어플처럼 누구나 손쉽게 이용할 수 있습니다. 특히, 도서 어플에서 자주
> 사용하는 장바구니, 책 검색 ..등이 주된 기능입니다.
> 중고서비스에서는 어플 사용자가 중고 책 거래할 때 사용하는 부분입니다. 중고 책을 살 수도 있고, 팔 수도 있는
> 중고 책에 관한 다양한 기능을 할 수 있는 부분입니다. 여기서 사고자하는 사용자와 팔고자하는 사용자는 따로 연락
> 할 필요없이 어플안에 있는 채팅 기능을 통해 쉽게 연락하여 이용할 수 있습니다.
> 고객센터부분에서는 어플의 문제점이나 자주 묻는 질문에 대해서 볼 수 있습니다.

## bookstore 기능 및 기술 설명
<hr/>

+ 로그인 기능
> 학번과 비밀번호를 입력할 수 있는 UI를 통해 입력한 뒤 로그인 버튼을 누르면 학번 값과 비밀번호 값이 문자열 값으로 변환되어
> 파이어베이스에 보관하고 있는 값들을 비교하여 맞게되면 화면을 전환하는 기능 Intent를 사용해 로그인 화면에서 어플 메인화면으로
> 이동하게 됩니다. 학번이나 비밀번호를 틀렸을 경우에는 Toast 기능을 통해 어플 하단에 경고 메시지를 나타내게 합니다.
> '''
 loginButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                final String id = EditText_id.getText().toString();
                final String pw = EditText_password.getText().toString();
                databaseReference.child(id).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Iterator<DataSnapshot> child = dataSnapshot.getChildren().iterator();
                        if(dataSnapshot.getChildrenCount() > 0 ){
                            String fbPw = dataSnapshot.child("password").getValue().toString();
                            String fbname = dataSnapshot.child("name").getValue().toString(); //firebase에서 name의 Value값을 get하고 string 시킴.

                            if(fbPw.equals(pw)){
                                Intent intent = new Intent(loginActivity.this, MainActivity.class);

                                id_name = fbname;
                                id_Snum = id;
                                intent.putExtra("name", id_name);
                                finish();

                                Toast.makeText(loginActivity.this,fbname+"님 어서오세요.",Toast.LENGTH_LONG).show();
                                startActivity(intent);
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
'''
단과대 선택 및 도서 나열
