# 부동산 실거래가 비교 시스템

## 프로젝트 개요
- 주제 : [프롭테크] 부동산 매매가  비교 서비스
- 서비스 이름 : 부비 (부동산 비교)
- 팀 이름 : 칸쵸 (멘티들 간 연결 고리인 멘토님 Karn 닉네임을 따서 “칸의 조”를 뜻함.)

## 목표 사용자 분석 및 이해
- 대상 : 부동산 실소유자 및 투자자
- 연령층 : 30 ~ 60대
![statistics](https://user-images.githubusercontent.com/62507373/138710082-ae6a9e7e-ecd3-4c1d-bf51-591f0a14924f.png)
            2021.01 ~ 2021.08 아파트매입자 나이 통계

## 기본 기능
- 회원가입
- 로그인
- 인증[세션]
- DB연동
- 공공데이터 API 연계로 데이터 적재[배치성 프로그램]
  - [참고] 아파트 실거래가 공공데이터
    - 국토교통부_아파트매매 실거래 상세 자료(https://www.data.go.kr/data/15057511/openapi.do)
    - 국토교통부_아파트 분양권전매 신고 자료(https://www.data.go.kr/data/15056782/openapi.do)
    - 국토교통부_아파트 전월세 자료(https://www.data.go.kr/data/15058017/openapi.do)

## 비즈니스 핵심 기능

- 특정 비교군 간 아파트 가격 비교 (차트투자)
- 최근 12개월, 6개월, 3개월 가장 많이 오른 아파트 (퀀트투자)

## 대용량 트래픽 처리 등 기술 사용하기 위한 기능
- 청약안내
  - SH 공사, LH 공사 등 진행 중인 청약 정보과 신청 링크를 제공
- 실시간 조회 BEST 부동산 순위 ex) 네이버 실검

**※ 멀티 프로세스, 멀티 스레드 환경에서 단순 조회가 아닌 DB 입력-수정 등의 트랜잭션 처리를 무결하게 다뤄볼 수 있는 기능 생각해보기**

## 프로젝트 방향성
#### 1. 안드로이드 앱으로 출시까지 하는 것을 목표로함
- 단, 멘토링 기간에는 백엔드에서 경험하고 해볼수 있는 것들을 많이 배우기 위해 API 서버 구축을 최우선으로 함
- API 서버 구축이 충분히 완료되었다고 판단된다면 안드로이드 앱으로 프론트 개발 (멘토링이 끝나더라도 멘티들은 계속해서 개발을 해보기로 함)
#### 2. 데이터는 작은 범위로 한정하여 우선 개발함
- 아파트 거래 내역 범위 우선순위
  1) 아파트 매매 실거래(1순위) 
  2) 아파트 분양권 전매 실거래(2순위)
  3) 아파트 전월세 실거래(3순위)

#### 코드 컨벤션
- 네이버 코딩 컨벤션: https://naver.github.io/hackday-conventions-java/

### 브랜치 관리 전략
- Git Flow

## 서비스 설계

#### 1. 주요 기능의 Task Flow 정의
     (참고 자료: https://brunch.co.kr/@tirrilee/7)
![task-flow2](https://user-images.githubusercontent.com/62507373/138710202-2ddbc058-fd69-4819-8aed-60b9136b9ba5.png)


#### 2. 정의한 Task Flow를 바탕으로 주요 API 추출
- 필수 기능
  - 회원가입
  - 로그인
  - 특정 아파트 연도별 실거래가 조회
  - 아파트 비교 그룹 추가/수정/삭제
  - 아파트 비교 그룹 연도별 실거래가 비교 조회
  - 최근 가장 많이 오른 아파트 조회 (순위 대로 N개)
- 선택 기능
  - 청약 조회
  - 실시간 사용자 조회 BEST 부동산 순위 조회 (순위 대로 N개)

## 사용 기술
- ubuntu 18.04 LTS
- MySQL 
- Spring
- Naver Cloud
- Java 11
