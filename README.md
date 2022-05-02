# Android-Game-Programming

## 게임 컨셉
### TITLE : APPLE
##### High Concept
* 플레이어가 사과가 되어서 자신을 공격해오는 벌레들을 제거하며 버티어 내는 게임
* Ladybug(레이디버그)의 모작으로 캐주얼 슈팅게임   
<img src = "https://user-images.githubusercontent.com/70790091/160325040-fb9261f4-fa67-4d35-843e-6e279066153c.png" width="30%" height="30%">

##### 핵심 메카닉
* 캐릭터를 조이스틱으로 움직이며 여러 방향에서 나오는 적들을 제거 및 피해서 오랫동안 살아남는 것
* 캐릭터가 적과 충돌하면 게임 오버
***

## 개발 범위
#### 캐릭터 컨트롤
-조이스틱을 사용한 전방향 이동   
-방향에 따른 캐릭터 회전
#### 적
-상단, 좌측, 우측에서 생성   
-직선으로 이동 (기울기는 랜덤)
#### 아이템
-적을 제거하고 추가 스코어 획득   
-세 종류 : 폭탄, 미사일, 안전지대   
-화면에 최대 3개 존재 (그 이상은 생성 x)
#### 스테이지
-일정시간 지나면 스테이지 증가 (1~3)   
-적 이동속도, 생성 수, 생성 방향 추가
#### 스코어
-시간에 따라 증가   
-적 제거 시 증가
#### 사운드
-BGM   
-아이템 및 적 제거 효과음
#### 애니메이션
-캐릭터 및 적 이동
***
## 예상 게임 실행 흐름
<img src = "https://user-images.githubusercontent.com/70790091/160326705-dfe556fa-5edc-436c-abf1-f8265e91f2cb.png" width="80%" height="80%">

***
## 개발 일정
##### 1주차 - 리소스 수집
게임에 사용할 리소스(캐릭터,적,아이템,배경 등 이미지) 수집   
(95%) 폰트 리소스 수집
##### 2주차 - 캐릭터 오브젝트
캐릭터와 기본배경을 화면에 띄우고 캐릭터 이동 구현   
(100%)
##### 3주차 - 장애물 오브젝트
장애물을 랜덤한 위치에 생성 및 이동 구현   
(100%)
##### 4주차 - 충돌검사 및 충돌처리
캐릭터와 적의 충돌검사를 통해서 게임오버 구현   
(100%)
##### 5주차 - 아이템 오브젝트
아이템 오브젝트 구현, 이동속도 증가 아이템, 폭탄 아이템   
(70%) 폭탄 아이템 구현
##### 6주차 - 스테이지 및 스코어
시간에 따른 스테이지 증가와 그에 따른 적의 변화 구현   
적 제거와 시간에 따른 스코어 증가 구현, 스코어 화면에 띄우기
##### 7주차 - 사운드 및 게임 스테이트
배경음악 및 효과음(아이템 획득, 적 제거) 등 청각적인 효과 구현   
게임 스테이트(타이틀, 메인플레이 등) 구현
##### 8주차 - 아이템 오브젝트
미사일 아이템, 안전지대 아이템 구현
##### 9주차 - 최종점검
최종점검 및 릴리즈

***
## git commit 통계 (5/2기준)
![image](https://user-images.githubusercontent.com/70790091/166214028-d414b2b6-b342-4443-9cd2-bef99fd5e3ba.png)

***
## Game Object
### Apple
![image](https://user-images.githubusercontent.com/70790091/166215079-601253ef-6d09-47de-a729-eb4b3d27e405.png)

-Joystick의 입력에 따라서 이동 및 회전   
-중심점과 반지름을 이용해 거리에 따른 충돌검사   
-Enemy와 충돌 시 게임 종료   
-Item과 충돌 시 해당 item 사용   
플레이어의 입력에 따라 이동하는 캐릭터 역할

### Joystick
![image](https://user-images.githubusercontent.com/70790091/166214814-813e641c-be8a-484e-94e5-2bbd4c427ebe.png)

-바깥 원과 스틱의 중심점 좌표들을 이용   
-입력의 세기, 각도를 설정   
-캐릭터의 이동속도, 방향을 결정   
플레이어가 조작하는 대로 캐릭터를 움직이는 컨트롤러 역할

### Enemy
![image](https://user-images.githubusercontent.com/70790091/166215169-884df890-468f-498f-a3a2-27db1e024c5e.png)

-EnemyGenerator를 통한 스폰   
-직선이동 (랜덤한 기울기, 기울기에 따른 회전)   
-AnimSprite를 상속하여 애니메이션 재생   
-stage에 따라 스폰되는 사이드(상단, 좌측, 우측) 결정   
-화면을 벗어나면 제거   
플레이어를 방해하는 장애물 역할

### Item
![image](https://user-images.githubusercontent.com/70790091/166215281-9d4ee5ac-c6bb-40a5-928a-02e53862f2cc.png)

-ItemGenerator를 통한 스폰   
-직선이동 (상단->하단)   
-Type에 따라서 각각 다른 bitmap, 효과, 지속시간   
-화면을 벗어나면 제거   
플레이어의 오랜 생존, 적을 제거하여 추가 score를 획득하는 데에 도움을 줌

### Manager
#### EnemyGenerator, ItemGenerator
-스폰 간격, 한 화면에 존재할 수 있는 최대 수, stage를 고려하여 오브젝트를 스폰
#### CollisionChecker
-CollisionHelper를 통한 거리에 따른 충돌검사   
-캐릭터와 Enemy, 캐릭터와 Item의 충돌에 대한 처리

### Background
![image](https://user-images.githubusercontent.com/70790091/166215670-314bf650-2670-47d7-8536-2b52dd39d391.png)

-배경과 구름 존재   
-배경은 가장 아래, 구름은 캐릭터와 적보다 위에 그려짐   
-위에서 아래로 각자의 속도로 스크롤

***
## 실행화면 예시 (5/2기준)
![image](https://user-images.githubusercontent.com/70790091/166215796-266021b1-2c7a-4a93-9d04-dbf436fb0cd9.png)

