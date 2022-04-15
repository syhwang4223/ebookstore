--------------------------------------------------- 회원 --------------------------------------------
insert into member (authority, login_id) values ('ROLE_USER', 'chicken_love');
insert into member (authority, login_id) values ('ROLE_USER', 'dadada');
insert into member (authority, login_id) values ('ROLE_USER', 'asdf1234');


--------------------------------------------------- 회원 --------------------------------------------
insert into category (name) values ('romance');
insert into category (name) values ('romance-fantasy');
insert into category (name) values ('fantasy');
insert into category (name) values ('bl');

--------------------------------------------------- 책 --------------------------------------------
-- 로맨스
insert into book (title, author, price, isbn, publication_date, publisher, img_url, category_id, likes)
values ('로미오와 로미오', '주석', 12000, '9561483123624', '2018-06-19', '우와', 'https://user-images.githubusercontent.com/90035354/163523092-5a3c7f3b-96bd-4616-aeab-b5aacafc4700.png', 1, 0);
insert into book (title, author, price, isbn, publication_date, publisher, img_url, category_id, likes)
    values ('미끼는 미끼야', '수산물녀', 14000, '9881783106321', '2010-10-12', '블레스', 'https://user-images.githubusercontent.com/90035354/163523094-af6be75e-ee66-4d94-a7a5-6cdeaf754f49.png', 1, 0);
insert into book (title, author, price, isbn, publication_date, publisher, img_url, category_id, likes)
    values ('돌아와', '하하이', 5000, '9915264626321', '2011-04-12', '우와', 'https://user-images.githubusercontent.com/90035354/163523088-e3fca3d9-bba5-43ae-aa45-4f9b224412bd.png', 1, 0);
insert into book (title, author, price, isbn, publication_date, publisher, img_url, category_id, likes)
    values ('돌아오지마', '이하하', 8500, '9282783656209', '2022-11-09', '아름', 'https://user-images.githubusercontent.com/90035354/163523085-8f7dbdf7-967d-42c7-b348-462fd7f69bee.png', 1, 0);
insert into book (title, author, price, isbn, publication_date, publisher, img_url, category_id, likes)
    values ('X급의 퀘스트', '리리링', 9000, '9685733178661', '2021-05-01', '아름', 'https://user-images.githubusercontent.com/90035354/163523077-f8de8e39-6b54-4097-8bbe-e5ae1abc63c1.png', 1, 0);
-- 로맨스 판타지
insert into book (title, author, price, isbn, publication_date, publisher, img_url, category_id, likes)
    values ('은행나무 아래에', '수지김', 19000, '9345623786325', '2015-01-24', '맥시', 'https://user-images.githubusercontent.com/90035354/163523075-072ff8b4-38ea-47c6-aa2c-87b9f923028e.png', 2, 0);
insert into book (title, author, price, isbn, publication_date, publisher, img_url, category_id, likes)
    values ('하류 생활', '홍홍이', 17000, '1451756246248', '2014-03-07', '뿌리', 'https://user-images.githubusercontent.com/90035354/163523069-1a305c1b-0675-44e0-ab57-a2c19d643d53.png', 2, 0);
insert into book (title, author, price, isbn, publication_date, publisher, img_url, category_id, likes)
    values ('엄마가 된 S급 헌터', 'SY', 13400, '2883283604341', '2015-01-24', '맥시', 'https://user-images.githubusercontent.com/90035354/163523064-bdd412b7-c137-4022-933d-e5a851fec7a2.png', 2, 0);
insert into book (title, author, price, isbn, publication_date, publisher, img_url, category_id, likes)
    values ('병약한 공녀의 요양일기', '진혜', 24500, '5681284116521', '2020-01-12', '광팬', 'https://user-images.githubusercontent.com/90035354/163523082-9ae90b69-899d-466e-a593-6cc4a7b49e6a.png', 2, 0);
insert into book (title, author, price, isbn, publication_date, publisher, img_url, category_id, likes)
    values ('이제 그만하고 싶어!', '일만수', 18200, '5421713506356', '2015-05-06', '광팬', 'https://user-images.githubusercontent.com/90035354/163523052-1808e39a-3bd5-4fb3-863f-5c39899ae0dd.jpg', 2, 0);
-- 판타지
insert into book (title, author, price, isbn, publication_date, publisher, img_url, category_id, likes)
    values ('전지적 작가 시점', '샹숑', 91000, '6421653206321', '2017-06-22', '판타지수첩', 'https://user-images.githubusercontent.com/85507868/163430545-acfde68a-cd42-4332-b030-0c1604ebb70f.png', 3, 0);
insert into book (title, author, price, isbn, publication_date, publisher, img_url, category_id, likes)
    values ('환생 폭군은 죽고 싶어', '팔팍', 18200, '1151756246248', '2015-08-11', '러시브', 'https://user-images.githubusercontent.com/85507868/163430570-1fd921bf-fd63-43e7-9d07-74ea07a367f8.png', 3, 0);
insert into book (title, author, price, isbn, publication_date, publisher, img_url, category_id, likes)
    values ('택배 실종 사건', '마치', 82100, '4581243106411', '2008-01-24', '판타지수첩', 'https://user-images.githubusercontent.com/85507868/163430547-9b988c43-d933-415c-b53c-327fa0a028b6.png', 3, 0);
insert into book (title, author, price, isbn, publication_date, publisher, img_url, category_id, likes)
    values ('이세계에서 힐러로 살아남기', '두리루', 17200, '1825786106321', '2013-02-15', '도어락', 'https://user-images.githubusercontent.com/85507868/163430552-f8f0406c-9208-448b-9b55-2372b89294e3.png', 3, 0);
insert into book (title, author, price, isbn, publication_date, publisher, img_url, category_id, likes)
    values ('힘을 숨긴  XX', '립스피', 34100, '1581584102325', '2012-05-11', '도어락', 'https://user-images.githubusercontent.com/85507868/163430553-631b78df-271b-46bf-9636-e73a327af2e0.png', 3, 0);
-- bl
insert into book (title, author, price, isbn, publication_date, publisher, img_url, category_id, likes)
    values ('저 퇴근하고 싶어요', '미커', 17200, '4281583126321', '2022-04-22', '블레스', 'https://user-images.githubusercontent.com/85507868/163430556-5f27665a-d4e8-4215-96b6-3d3cc64ef520.png', 4, 0);
insert into book (title, author, price, isbn, publication_date, publisher, img_url, category_id, likes)
    values ('5월의 신랑', '별다랑', 90400, '2385763706221', '2012-08-09', '블루투스', 'https://user-images.githubusercontent.com/85507868/163430558-bda87548-4863-4092-9b1d-e4515aa17368.png', 4, 0);
insert into book (title, author, price, isbn, publication_date, publisher, img_url, category_id, likes)
    values ('체크 메이트', '들쥐', 12700, '4851283105672', '2005-01-22', '립스틱', 'https://user-images.githubusercontent.com/85507868/163430561-36344d78-65e0-4f0f-ae4b-3448bc6da11d.png', 4, 0);
insert into book (title, author, price, isbn, publication_date, publisher, img_url, category_id, likes)
    values ('체크 메이트', '다다다', 21300, '5431283105111', '2004-07-15', '반짝', 'https://user-images.githubusercontent.com/85507868/163430566-e7d81753-ab88-48e8-a446-79ffb5a17c9e.png', 4, 0);
insert into book (title, author, price, isbn, publication_date, publisher, img_url, category_id, likes)
    values ('코마상태', '구육', 78500, '5234282106321', '2002-01-19', '반짝', 'https://user-images.githubusercontent.com/85507868/163430568-f8bf1e8b-8e49-45ae-9bf4-66c7b0780a09.png', 4, 0);


--------------------------------------------------- 댓글 --------------------------------------------
insert into comment (content, likes, star, book_id, member_id, write_date_time, parent_id)
    values ('결말이 좀 아쉬워요. 너무 허무하게 끝납니다.', 0, 5, 1, 1, '2018-06-19', null);
insert into comment (content, likes, star, book_id, member_id, write_date_time, parent_id)
    values ('재밌읍니다 김밥 한 줄 놓고 가용 ^^ @)))))', 0, 4, 1, 2, '2018-06-20', null);
insert into comment (content, likes, star, book_id, member_id, write_date_time, parent_id)
    values ('엥 로맨스 카테인데 로미오와 로미오면 남장물인가?', 0, 5, 1, 3, '2021-11-05', null);


insert into comment (content, likes, book_id, member_id, write_date_time, parent_id)
    values ('네 남장물 맞아여ㅋㅋㅋ', 0, 1, 1, '2021-11-05', 3);


