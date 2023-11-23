# application
보안성 강화를 주요 목표로 삼아, 다수의 사용자가 동시에 접속해도 안정적인 성능을 보장하는 커뮤니티를 목표로 합니다.


<br>

## 📒 프로젝트 배포
https://gbapp.fly.dev
<br>
<br>
fly.io 서버가 자동 슬립 모드로 설정되어 있습니다. 링크를 클릭하면 15초 정도 이후에 서비스가 실행됩니다.
<br>
이후에는 원활하게 서비스를 구경하실 수 있습니다.
<br>
<br>
테스트용 계정 아이디 1234, 비밀번호 1234

## ⚙️ 개발 환경 및 기술 세부 스택
- Java 11
- Spring Boot 2.7.11
- MyBatis 3.5.11
- Thymeleaf 3.0
- PostgreSQL 42.3.8
- Redis - lettuce-core: 6.1.10
- Spring Security 5.7.8

  
  
  

## 📄 엔티티 관계도
![postgres - application](https://github.com/fxzz/application/assets/3148006/20ff60d6-25bf-4923-b2e3-eb279e318ed8)




## use-case

![](https://github.com/fxzz/application/raw/main/Usecase%20Diagram.png)


## 시연
**1. 인기 게시글**

```
일주일 단위로 초기화 데이터 없으면 집계, 좋아요 순으로 정렬 1시간씩 순위 변동
```
  
![ezgif com-optimize](https://github.com/fxzz/application/assets/3148006/3d737026-bc48-48d3-b200-4592a3ade9de)

<br>
<br>
<br>
<br>

***

**2. 커뮤니티 페이지**
```
검색과 페이징
```
<div style="display: flex; justify-content: space-between;">
  <img src="https://github.com/fxzz/application/assets/3148006/4c942c02-f750-4c97-bef2-a75ee856c8d3" width="49%">
  <img src="https://github.com/fxzz/application/assets/3148006/6c37fd71-bf74-4cf2-926f-27faed52c322" width="49%">
</div>

<br>
<br>
<br>
<br>

***

**3. 로그인 성공, 로그인 실패**
```
로그인 4회 실패시 reCAPTCHA
```
<div style="display: flex; justify-content: space-between;">
  <img src="https://github.com/fxzz/application/assets/3148006/a13ef6b4-a91c-4940-9aec-6a7c73291a9d" width="49%">
  <img src="https://github.com/fxzz/application/assets/3148006/39719ce7-f85c-4f76-a8dc-1ca3347a399c" width="49%">
</div>

<br>
<br>
<br>
<br>

***

**4. 회원가입 실패**
```
유효성 검사
```

![ezgif com-video-to-gif](https://github.com/fxzz/application/assets/3148006/13b15aba-ac1c-436c-a5ab-4c7959ed7fb2)


<br>
<br>
<br>
<br>

***

**5. 글쓰기 성공, 실패**
```
글쓰기에 태그와 이미지 등록이 포함, 유효성 검사
```

<div style="display: flex; justify-content: space-between;">
  <img src="https://github.com/fxzz/application/assets/3148006/95f2dd0c-279c-408a-945b-7f63ff22cec0" width="49%">
  <img src="https://github.com/fxzz/application/assets/3148006/a203ccb4-d9c8-45d4-b24d-96a3f15e8a5e" width="49%">
</div>

<br>
<br>
<br>
<br>

***

**6. 게시글 읽기**
```
게시글 이동하고 이미지 다운로드, 수정, 삭제, 없는 페이지로 이동시 커뮤니티로 이동
```

![ezgif com-video-to-gif (1)](https://github.com/fxzz/StudyNotes/assets/3148006/d3225be3-eef5-4eb7-a1ca-e2c63e6caebd)

<br>
<br>
<br>
<br>

***

**7. 좋아요, 신고**
```
좋아요, 신고, 중복 검사
```

![ezgif com-video-to-gif](https://github.com/fxzz/StudyNotes/assets/3148006/32963730-ec27-4efd-82c2-d1628891961f)

<br>
<br>
<br>
<br>

***

**8. 댓글과 대댓글**
```
댓글과 대댓글, 삭제, 신고
```

![ezgif com-video-to-gif](https://github.com/fxzz/StudyNotes/assets/3148006/8b2b2e5b-ea36-4c0f-820a-c394eb5bc887)

<br>
<br>
<br>
<br>

***


**9. 알람**
```
내 게시글에 댓글이 달리면 인터셉터에 걸려있는 조건에 맞으면 아이콘 변경
```

![ezgif com-video-to-gif (1)](https://github.com/fxzz/StudyNotes/assets/3148006/4c248d8e-1d5c-4acb-92b0-2a925e5f207a)

<br>
<br>
<br>
<br>

***

**10. 프로필**
```
정보를 수정하는 프로필
```

![ezgif com-video-to-gif (2)](https://github.com/fxzz/StudyNotes/assets/3148006/145c528f-168b-4c3f-8af9-dccfb8e2a63f)

<br>
<br>
<br>
<br>

***

**11. 타임라인**
```
커서 페이징으로 빠르고 간단하게 사용자의 게시글을 조회
```

![ezgif com-video-to-gif (3)](https://github.com/fxzz/StudyNotes/assets/3148006/4119cd5e-ca93-409e-b90d-90eeb54851e5)

<br>
<br>
<br>
<br>

***
**12. 채팅**
```
웹소켓으로 구현한 간단한 채팅
```

![ezgif com-video-to-gif (4)](https://github.com/fxzz/StudyNotes/assets/3148006/a7f44e6d-24fe-41c0-a822-4808fd6e8d54)

<br>
<br>
<br>
<br>

***
**13. 실시간 계정 비활성화**
```
차단을 누르면 그 유저의 세션을 즉시 끊고 계정을 비활성화
```
![ezgif com-video-to-gif (5)](https://github.com/fxzz/StudyNotes/assets/3148006/d3d74734-2bcf-4f44-ac55-acba662aec35)

<br>
<br>
<br>
<br>

***
**14. 간단한 어드민 페이지**
```
통계랑 게시글 댓글 삭제 유저 차단
```

![ezgif com-video-to-gif (6)](https://github.com/fxzz/StudyNotes/assets/3148006/b4d2fdd8-89cd-47ee-8792-d4d6269c4d09)


