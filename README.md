# 일정관리-v2

Spring 심화 과제

# API 명세서

## 속성
(최대 20자)
### 유저
| 필드명     | 타입        | 필수 | 설명                         | 
|-----------|------------|-----|------------------------------|
| id        | Long       | X   | 유저 ID (PK, 자동생성)         |
| username  | String     | O   | 유저 닉네임 (최소 2, 최대 10)   |
| email     | String     | O   | 이메일 (로그인 시 ID, 최대 320) |
| password  | String     | O   | 비밀번호 (최소 4, 최대 20)      |
| createdAt | datetime   | X	| 등록일 (자동 생성)              | 
| modifiedAt| datetime	  | X	| 수정일 (자동 생성)              |

### 일정
| 필드명     | 타입        | 필수  | 설명                 |
|-----------|------------|------|----------------------|
| id        | Long       | X    | 일정 ID (PK, 자동생성) |
| title     | String     | O    | 일정 제목 (최대 20자)  |
| content   | String     | O    | 일정 내용 (최대 200자) |
| user_id   | Long       | X    | 유저 ID (FK, 자동 생성)|
| createdAt | datetime   | X	 | 등록일 (자동 생성)      |
| modifiedAt| datetime	  | X	 | 수정일 (자동 생성)      |

### 댓글
| 필드명       | 타입        | 필수  | 설명                  |
|-------------|------------|------|-----------------------|
| id          | Long       | X    | 댓글 ID (자동생성)      |
| comment     | String     | O    | 댓글 (최대 100자)      |
| user_id     | Long       | X    | 유저 ID (FK, 자동 생성) |
| schedule_id | Long       | X    | 일정 ID (FK, 자동 생성) |
| createdAt   | datetime   | X	   | 등록일 (자동 생성)       |
| modifiedAt  | datetime   | X	   | 수정일 (자동 생성)       |

## 기능 

### 유저(User) API 
| 기능       | 메서드    | 엔드포인트   | 로그인 여부 | 설명                                            |
|-----------|--------|---------------|---------|--------------------------------------------------|
| 회원가입   | POST   | /users/signup | X       | 회원가입을 합니다                                   |
| 로그인     | POST   | /users/login  | X       | 로그인을 합니다 (세션 생성)                          |
| 로그아웃   | POST   | /users/logout | O       | 로그아웃을 합니다 (세션 제거)                         |
| 정보 조회  | GET    | /users/me     | O       | 자신의 정보를 조회합니다                             |
| 닉네임 변경| PUT    | /users/me     | O       | 자신의 닉네임을 변경합니다                            |
| 회원 탈퇴  | DELETE | /users/me     | O       | 회원 탈퇴를 합니다 (자신의 일정과 댓글 제거 및 세션 제거) |

### 일정(Schedule) API
| 기능          | 메서드    | 엔드포인트                 | 로그인 여부 | 설명                                                   |
|--------------|----------|--------------------------|-----------|--------------------------------------------------------|
| 일정 생성      | POST     | /schedules               | O         | 일정을 생성합니다                                         |
| 일정 조회      | GET      | /schedules               | X         | 일정을 전체 조회합니다 (페이지 default page = 0, size = 10) |
| 단건 일정 조회  | GET      | /schedules/{scheduleId}  | X         | 해당 일정을 조회합니다 (댓글포함)                           |
| 일정 수정      | PUT      | /schedules/{scheduleId}  | O         | 자신의 일정을 수정합니다                                   |
| 일정 삭제      | DELETE   | /schedules/{scheduleId}  | O         | 자신의 일정을 삭제합니다 (해당 일정의 댓글도 삭제)             |

### 댓글(Comment) API 
| 기능          | 메서드    | 엔드포인트                          | 로그인 여부 | 설명                       |
|--------------|----------|-----------------------------------|-----------|----------------------------|
| 댓글 생성      | POST     | /schedules/{scheduleId}/comments  | O         | 해당 일정에 댓글을 생성합니다  |
| 댓글 수정      | PUT      | /comments/{commentId}             | O         | 자신의 댓글을 수정합니다      |
| 댓글 삭제      | DELETE   | /comments/{commentId}             | O         | 자신의 댓글을 삭제합니다      |

### API 성공 / 실패 코드

#### 성공코드
| 코드          | 내용                     |
|--------------|--------------------------|
| 200(OK)      | 해당 API 성공             |
| 201(CREATED) | 생성 성공                 |

#### 실패코드
| 코드               | 내용                     |
|-------------------|-------------------------|
| 400(BAD_REQUEST)  | 잘못된 요청               |
| 401(UNAUTHORIZED) | 권한 없음                 |
| 404(NOT_FOUND)    | 해당 리소스를 찾을 수 없음   |


# 기능 예시

## 1. 유저(User) API

### 1-1 회원가입

#### ===== Request =====
```json
{
  "username": "닉네임1",
  "email": "이메일1@gmail.com",
  "password": "12345678"
}
```

#### ===== Response ===== (201 CREATED)
```json
{
  "id": "1",
  "username": "닉네임1",
  "email": "이메일1@gmail.com",
  "createdAt": "2025-08-13T00:00:00.00000",
  "modifiedAt": "2025-08-13T00:00:00.00000" 
}
```

### 1-2 로그인

#### ===== Request =====
```json
{
  "email": "이메일1@gmail.com",
  "password": "12345678"
}
```

#### ===== Response ===== (200 OK)
닉네임1님 반갑습니다.

### 1-3 로그아웃

#### ===== Response ===== (200 OK)
로그아웃 되었습니다.

### 1-4 정보 조회

#### ===== Response ===== (200 OK)
```json
{
  "id": "1",
  "username": "닉네임1",
  "email": "이메일1@gmail.com",
  "createdAt": "2025-08-13T00:00:00.00000",
  "modifiedAt": "2025-08-13T00:00:00.00000" 
}
```

### 1-5 닉네임 변경

#### ===== Request =====
```json
{
  "username": "수정된 닉네임1",
  "password": "12345678"
}
```

#### ===== Response ===== (200 OK)
```json
{
  "id": "1",
  "username": "수정된 닉네임1",
  "email": "이메일1@gmail.com",
  "createdAt": "2025-08-13T00:00:00.00000",
  "modifiedAt": "2025-08-13T00:00:00.00000" 
}
```

## 2. 일정(Schedule) API

### 2-1 일정 생성

#### ===== Request =====
```json
{
  "title": "세션 제목1",
  "content": "세션 내용1"
}
```

#### ===== Response ===== (201 CREATED)
```json
{
  "id": 1,
  "title": "세션 제목1",
  "content": "세션 내용1",
  "username": "닉네임1",
  "createdAt": "2025-08-13T00:00:00.00000",
  "modifiedAt": "2025-08-13T00:00:00.00000"
}
```

### 2-2 일정 조회
| 파라미터 | 타입   | 설명              | default  |
|---------|-------|------------------|----------|
| page    | int   | 페이지 번호        | 0        |
| size    | int   | 페이지 당 일정 갯수 | 10       |

#### ===== Response ===== (200 OK)
```json
{
  "content": [
    {
      "id": 1,
      "title": "세션 제목1",
      "content": "세션 내용1",
      "username": "닉네임1",
      "createdAt": "2025-08-13T00:00:00.00000",
      "modifiedAt": "2025-08-13T00:00:00.00000"
    }
  ],
  "pageable": {
    "pageNumber": 0,
    "pageSize": 10,
    "sort": {
      "empty": true,
      "sorted": false,
      "unsorted": true
    },
    "offset": 0,
    "paged": true,
    "unpaged": false
  },
  "last": true,
  "totalPages": 1,
  "totalElements": 4,
  "size": 10,
  "number": 0,
  "sort": {
    "empty": true,
    "sorted": false,
    "unsorted": true
  },
  "first": true,
  "numberOfElements": 4,
  "empty": false
}
```

### 2-3 단건 일정 조회

#### ===== Request =====

- path parameter: scheduleId (예시: 1)

#### ===== Response ===== (200 OK)
```json
{
  "schedule": {
    "id": 1,
    "title": "세션 제목1",
    "content": "세션 내용1",
    "username": "닉네임1",
    "createdAt": "2025-08-13T00:00:00.00000",
    "modifiedAt": "2025-08-13T00:00:00.00000"
  },
  "comments": []
}
```

### 2-4 일정 수정

#### ===== Request =====

- path parameter: scheduleId (예시: 1)
```json
{
  "title": "수정된 세션 제목1",
  "content": "수정된 세션 내용1"
}
```

#### ===== Response ===== (200 OK)
```json
{
  "id": 1,
  "title": "수정된 세션 제목1",
  "content": "수정된 세션 내용1",
  "username": "닉네임1",
  "createdAt": "2025-08-13T00:00:00.00000",
  "modifiedAt": "2025-08-13T00:00:00.00000"
}
```

### 2-5 일정 삭제

#### ===== Request =====

- path parameter: scheduleId (예시: 1)

#### ===== Response ===== (200 OK)

## 3. 댓글(Comment) API

### 3-1 댓글 생성

#### ===== Request =====

- path parameter: scheduleId (예시: 1)
```json
{
  "comment": "댓글1"
}
```

#### ===== Response ===== (301 CREATED)
```json
{
  "id": 1,
  "comment": "댓글1",
  "username": "닉네임1",
  "createdAt": "2025-08-13T00:00:00.00000",
  "modifiedAt": "2025-08-13T00:00:00.00000"
}
```

### 3-2 댓글 수정

#### ===== Request =====

- path parameter: commentId (예시: 1)
```json
{
  "comment": "수정된 댓글1"
}
```

#### ===== Response ===== (200 OK)
```json
{
  "id": 1,
  "comment": "수정된 댓글1",
  "username": "닉네임1",
  "createdAt": "2025-08-13T00:00:00.00000",
  "modifiedAt": "2025-08-13T00:00:00.00000"
}
```
### 3-3 댓글 삭제

#### ===== Request =====

- path parameter: commentId (예시: 1)

#### ===== Response ===== (200 OK)












# ERD

<img width="578" height="539" alt="{7BC39EC9-564D-42DF-8D4B-723E9928558C}" src="https://github.com/user-attachments/assets/cf03315d-19f8-4769-9af4-c4557dfa98c4" />
