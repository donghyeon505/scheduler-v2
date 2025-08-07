# API 명세서

## 1. 일정(Schedule) API

### 1-1 일정 생성

| 항목 | 내용 |
|------|-------|
| **URL** | `POST /scheduls` |
| **Request Body** | `title`(String, 필수, 최대 20자)<br>`content`(String, 필수, 최대 200자)<br>`writer`(String, 필수)<br>`password`(String, 필수, 최대 20자) |
| **Response** | `200 OK` |
| **Response Body** | `id`, `title`, `content`, `writer`, `createdAt`, `modifiedAt` |
| **Error** | `400 Bad Request` → 필수값 누락, 글자수 초과 |

### 1-2 일정 조회

| 항목 | 내용 |
|------|-------|
| **URL** | `GET /scheduls` |
| **Query Parameter** | `writer`(String, 선택) -> 작성자명으로 필터링 |
| **Response** | `200 OK` |
| **Response Body** | 일정 목록 배열 (`id`, `title`, `content`, `writer`, `createdAt`, `modifiedAt`) |

### 1-3 단건 일정 조회

| 항목 | 내용 |
|------|-------|
| **URL** | `GET /scheduls/{scheduleId}` |
| **Path Variable** | `scheduleId`(Long, 필수) |
| **Response** | `200 OK` |
| **Response Body** | 일정 정보 (`id`, `title`, `content`, `writer`, `createdAt`, `modifiedAt`) |

### 1-4 일정 수정

| 항목 | 내용 |
|------|-------|
| **URL** | `GET /scheduls/{scheduleId}` |
| **Path Variable** | `scheduleId`(Long, 필수) |
| **Request Body** | `title`(String, 필수, 최대 20자)<br>`content`(String, 필수, 최대 200자)<br>`password`(String, 필수) |
| **Response** | `200 OK` |
| **Response Body** | 수정된 일정 정보 (`id`, `title`, `content`, `writer`, `createdAt`, `modifiedAt`) |
| **Error** | `400 Bad Request` → 필수값 누락, 글자수 초과, 비밀번호 불일치 |
| **비고** | 제목과 내용만 수정 가능 |

### 1-5 일정 삭제

| 항목 | 내용 |
|------|-------|
| **URL** | `GET /scheduls/{scheduleId}` |
| **Path Variable** | `scheduleId`(Long, 필수) |
| **Request Body** | `password`(String, 필수) |
| **Response** | `200 OK` |
| **Error** | `400 Bad Request` → 비밀번호 불일치 |

## 2. API

## 3. API

















# ERD

<img width="578" height="539" alt="{7BC39EC9-564D-42DF-8D4B-723E9928558C}" src="https://github.com/user-attachments/assets/cf03315d-19f8-4769-9af4-c4557dfa98c4" />
