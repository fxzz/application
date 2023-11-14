# application
사용자의 안전을 생각해 튼튼하고 견고한 커뮤니티 사이트를 목표로 구축하고 있습니다.


<br>

## 📒 프로젝트 배포
https://gbapp.fly.dev
<br>
<br>
fly.io 서버가 자동 슬립 모드로 설정되어 있습니다. 링크를 클릭하면 15초 정도 이후에 서비스가 실행됩니다.
<br>
이후에는 원활하게 서비스를 구경하실 수 있습니다.

## ⚙️ 개발 환경 및 기술 세부 스택
- Java 11
- Spring Boot 2.7.11
- MyBatis
- Thymeleaf
- PostgreSQL
- Redis
- Spring Security

  
  
  

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
글쓰기에 태그 등록 이미지 등록이 포함, 유효성 검사
```

<div style="display: flex; justify-content: space-between;">
  <img src="https://github.com/fxzz/application/assets/3148006/95f2dd0c-279c-408a-945b-7f63ff22cec0" width="49%">
  <img src="https://github.com/fxzz/application/assets/3148006/a203ccb4-d9c8-45d4-b24d-96a3f15e8a5e" width="49%">
</div>


