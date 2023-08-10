DROP TABLE IF EXISTS "vote_select";
DROP TABLE IF EXISTS "vote_question";
DROP TABLE IF EXISTS "two_way_answer";
DROP TABLE IF EXISTS "two_way_question";
DROP TABLE IF EXISTS "best_member";
DROP TABLE IF EXISTS "todo";
DROP TABLE IF EXISTS "symbol_grant";
DROP TABLE IF EXISTS "record";
DROP TABLE IF EXISTS "alarm";
DROP TABLE IF EXISTS "symbol";
DROP TABLE IF EXISTS "report";
DROP TABLE IF EXISTS "likes";
DROP TABLE IF EXISTS "feed";
DROP TABLE IF EXISTS "tagging";
DROP TABLE IF EXISTS "bubble_list";
DROP TABLE IF EXISTS "bubble";
DROP TABLE IF EXISTS "category";
DROP TABLE IF EXISTS "hashtag";
DROP TABLE IF EXISTS "follow";
DROP TABLE IF EXISTS "member";

CREATE TABLE "member" (
	"member_number"	BIGSERIAL		NOT NULL,
	"member_id"	VARCHAR(50)		NOT NULL,
	"member_email"	VARCHAR(50)		NOT NULL,
	"member_password"	VARCHAR(64)		NOT NULL,
	"member_nickname"	VARCHAR(20)		NOT NULL,
	"member_name"	VARCHAR(5)		NOT NULL,
	"member_intro"	VARCHAR(250)		NULL,
	"member_type"	CHAR(2)		NOT NULL,
	"member_state"	SMALLINT	DEFAULT 0	NOT NULL,
	"is_email_authentication"	BOOLEAN	DEFAULT FALSE	NOT NULL,
	"is_email_receive"	BOOLEAN	DEFAULT FALSE	NOT NULL,
	"create_dttm"	TIMESTAMP	DEFAULT now()	NOT NULL,
	"delete_dttm"	TIMESTAMP		NULL
);

COMMENT ON COLUMN "member"."member_number" IS '회원번호 인덱스 : 자동증가';

COMMENT ON COLUMN "member"."member_id" IS '일반 사용자 : 인증 이메일 / 소셜 사용자(네이버, 구글) 식별 아이디 [유니크] : 1~50자';

COMMENT ON COLUMN "member"."member_email" IS '연락용 이메일 [유니크 X] : 1~50자';

COMMENT ON COLUMN "member"."member_password" IS '8~16자 + 영문 + 숫자 + 특수문자 ( SHA256 암호화 = 32byte =  64개의 16진수 문자 )';

COMMENT ON COLUMN "member"."member_nickname" IS '2~20자 (한글 or 영문 or 숫자) [유니크]';

COMMENT ON COLUMN "member"."member_name" IS '2~5글자(한글만)';

COMMENT ON COLUMN "member"."member_intro" IS '자기소개 글자수 제한 : 250자';

COMMENT ON COLUMN "member"."member_type" IS 'CO : 일반 회원 / NA : 네이버 회원 / GO : 구글 회원';

COMMENT ON COLUMN "member"."member_state" IS '0 : 승인 대기 / 1 : 활동중 / 2 : 탈퇴';

COMMENT ON COLUMN "member"."is_email_authentication" IS 'TRUE : 이메일 인증 된 상태 / FALSE : 이메일 인증 안된 상태';

COMMENT ON COLUMN "member"."is_email_receive" IS 'TRUE : 이메일 받음 / FALSE : 이메일 안받음';

COMMENT ON COLUMN "member"."create_dttm" IS '회원 가입 요청 일시 (위 데이터들이 처음 만들어진 시간)';

COMMENT ON COLUMN "member"."delete_dttm" IS '회원 탈퇴 일시 (탈퇴 처리가 완료 된 시점)';


CREATE TABLE "feed" (
	"feed_number"	BIGSERIAL		NOT NULL,
	"feed_creator_number"	BIGSERIAL		NOT NULL,
	"feed_title"	VARCHAR(20)		NOT NULL,
	"feed_content"	VARCHAR(500)		NOT NULL,
	"feed_like_cnt"	INTEGER	DEFAULT 0	NOT NULL,
	"feed_view_cnt"	INTEGER	DEFAULT 0	NOT NULL,
	"feed_active"	BOOLEAN	DEFAULT TRUE	NOT NULL,
	"feed_img_origin"	VARCHAR(100)		NULL,
	"feed_img_trans"	VARCHAR(26)		NULL,
	"create_dttm"	TIMESTAMP	DEFAULT now()	NOT NULL,
	"delete_dttm"	TIMESTAMP		NULL
);

COMMENT ON COLUMN "feed"."feed_number" IS '글 번호 인덱스 : 자동증가';

COMMENT ON COLUMN "feed"."feed_creator_number" IS '피드를 작성한 회원의 회원 번호';

COMMENT ON COLUMN "feed"."feed_title" IS '최대 20자';

COMMENT ON COLUMN "feed"."feed_content" IS '최대 500자';

COMMENT ON COLUMN "feed"."feed_like_cnt" IS '좋아요 받은 수';

COMMENT ON COLUMN "feed"."feed_view_cnt" IS '조회수';

COMMENT ON COLUMN "feed"."feed_active" IS 'TRUE : 활성 피드 / FALSE : 삭제된 피드';

COMMENT ON COLUMN "feed"."feed_img_origin" IS '피드 이미지 원본 이름 + 확장자';

COMMENT ON COLUMN "feed"."feed_img_trans" IS '변환 이름 : 년월일시분초_랜덤6숫자.확장자 (20230707155030_123456.jpg)';

COMMENT ON COLUMN "feed"."create_dttm" IS '작성 시간';

COMMENT ON COLUMN "feed"."delete_dttm" IS '피드 삭제 시간';


CREATE TABLE "bubble" (
	"bubble_number"	BIGSERIAL		NOT NULL,
	"member_number"	BIGSERIAL		NOT NULL,
	"category_number"	INTEGER		NOT NULL,
	"is_bubble_talk"	BOOLEAN	DEFAULT FALSE NOT NULL,
	"bubble_title"	VARCHAR(20)		NOT NULL,
	"bubble_content"	VARCHAR(500)		NOT NULL,
	"bubble_thumbnail"	TEXT		NULL,
	"bubble_state"	BOOLEAN		NOT NULL,
	"plan_open_dttm"	TIMESTAMP		NULL,
	"plan_close_dttm"	TIMESTAMP		NULL,
	"create_dttm"	TIMESTAMP	DEFAULT now()	NOT NULL,
	"done_dttm"	TIMESTAMP		NULL
);

COMMENT ON COLUMN "bubble"."bubble_number" IS '버블 인덱스';

COMMENT ON COLUMN "bubble"."member_number" IS '방장회원번호 인덱스';

COMMENT ON COLUMN "bubble"."category_number" IS '대분류 인덱스';

COMMENT ON COLUMN "bubble"."is_bubble_talk" IS 'FALSE : 버블링 / TRUE : 버블톡';

COMMENT ON COLUMN "bubble"."bubble_title" IS '버블 제목 1~20자';

COMMENT ON COLUMN "bubble"."bubble_content" IS '버블 소개 글 : 1~500자';

COMMENT ON COLUMN "bubble"."bubble_thumbnail" IS '이미지 url';

COMMENT ON COLUMN "bubble"."bubble_state" IS 'TRUE: 대기 및 활성 / FALSE: 종료';

COMMENT ON COLUMN "bubble"."plan_open_dttm" IS '버블 시작 예정 일시';

COMMENT ON COLUMN "bubble"."plan_close_dttm" IS '버블 종료 예정 일시';

COMMENT ON COLUMN "bubble"."create_dttm" IS '버블이 생성된 일시';

COMMENT ON COLUMN "bubble"."done_dttm" IS '버블 상태가 FALSE가 된 일시';


CREATE TABLE "category" (
	"category_number"	INTEGER		NOT NULL,
	"category_name"	varchar(30)		NOT NULL,
	"create_dt"	DATE	DEFAULT now()	NOT NULL
);

COMMENT ON COLUMN "category"."category_number" IS '분류 인덱스';

COMMENT ON COLUMN "category"."category_name" IS '분류 이름';

COMMENT ON COLUMN "category"."create_dt" IS '분류 생성 일';


CREATE TABLE "bubble_list" (
	"bubble_number"	BIGSERIAL		NOT NULL,
	"member_number"	BIGSERIAL		NOT NULL,
	"is_mic_on"	BOOLEAN	DEFAULT FALSE	NOT NULL,
	"is_cam_on"	BOOLEAN	DEFAULT FALSE	NOT NULL,
	"is_mic_lock"	BOOLEAN	DEFAULT FALSE	NOT NULL,
	"is_cam_lock"	BOOLEAN	DEFAULT FALSE	NOT NULL,
	"is_chat_lock"	BOOLEAN	DEFAULT FALSE	NOT NULL,
	"join_state"	SMALLINT	DEFAULT 0	NOT NULL,
	"craete_dttm"	TIMESTAMP	DEFAULT now()	NOT NULL
);

COMMENT ON COLUMN "bubble_list"."bubble_number" IS '버블 인덱스';

COMMENT ON COLUMN "bubble_list"."member_number" IS '회원번호 인덱스';

COMMENT ON COLUMN "bubble_list"."is_mic_on" IS 'TRUE : 마이크 on / FALSE : 마이크 off';

COMMENT ON COLUMN "bubble_list"."is_cam_on" IS 'TRUE : 카메라 on / FALSE : 카메라 off';

COMMENT ON COLUMN "bubble_list"."is_mic_lock" IS 'TRUE : 마이크 강제 off / FALSE : 마이크 잠금 해제';

COMMENT ON COLUMN "bubble_list"."is_cam_lock" IS 'TRUE : 카메라 강제 off / FALSE : 카메라 잠금 해제';

COMMENT ON COLUMN "bubble_list"."is_chat_lock" IS 'TRUE : 채팅 강제 off / FALSE : 채팅 잠금 해제';

COMMENT ON COLUMN "bubble_list"."join_state" IS '0: 신청 / 1: 참여 / 2: 강퇴';

COMMENT ON COLUMN "bubble_list"."craete_dttm" IS '버블 신청 일시(취소시 삭제)';


CREATE TABLE "hashtag" (
	"hashtag_number"	BIGSERIAL		NOT NULL,
	"hashtag_name"	VARCHAR(10)		NOT NULL,
	"create_dt"	DATE	DEFAULT now()	NOT NULL
);

COMMENT ON COLUMN "hashtag"."hashtag_number" IS '해시태그 인덱스';

COMMENT ON COLUMN "hashtag"."hashtag_name" IS '해시태그 이름';

COMMENT ON COLUMN "hashtag"."create_dt" IS '태그 파일 생성 날짜';

CREATE TABLE "likes" (
	"like_feed_number"	BIGSERIAL		NOT NULL,
	"like_member_number"	BIGSERIAL		NOT NULL,
	"create_dttm"	TIMESTAMP	DEFAULT now()	NOT NULL
);

COMMENT ON COLUMN "likes"."like_feed_number" IS '글 번호 인덱스 : 자동증가';

COMMENT ON COLUMN "likes"."like_member_number" IS '회원번호 인덱스 : 자동증가';

COMMENT ON COLUMN "likes"."create_dttm" IS '좋아요 누른 시간';

CREATE TABLE "tagging" (
	"bubble_number"	BIGSERIAL		NOT NULL,
	"hashtag_number"	BIGSERIAL		NOT NULL,
	"create_dttm"	TIMESTAMP	DEFAULT now()	NOT NULL
);

COMMENT ON COLUMN "tagging"."bubble_number" IS '버블 인덱스';

COMMENT ON COLUMN "tagging"."hashtag_number" IS '해시태그 인덱스';

COMMENT ON COLUMN "tagging"."create_dttm" IS '태그 부여 일시';


CREATE TABLE "follow" (
	"follow_number"		BIGSERIAL	NOT NULL,
	"follower_number"	BIGSERIAL		NOT NULL,
	"following_number"	BIGSERIAL		NOT NULL,
	"create_dttm"	TIMESTAMP	DEFAULT now()	NOT NULL
);

COMMENT ON COLUMN "follow"."follow_number" IS '팔로우 인덱스';

COMMENT ON COLUMN "follow"."follower_number" IS '팔로우를 누른 회원의 회원 번호';

COMMENT ON COLUMN "follow"."following_number" IS '팔로우 받은 회원의 회원 번호';

COMMENT ON COLUMN "follow"."create_dttm" IS '팔로우 한 시간';

CREATE TABLE "symbol" (
	"symbol_number"	BIGSERIAL		NOT NULL,
	"symbol_name"	VARCHAR(30)		NOT NULL,
	"symbol_img_name"	VARCHAR(30)		NOT NULL,
	"symbol_code"	VARCHAR(16)		NOT NULL,
	"symbol_condition_cnt"	INTEGER		NOT NULL,
	"create_dt"	DATE	DEFAULT now()	NOT NULL
);

COMMENT ON COLUMN "symbol"."symbol_number" IS '심볼 인덱스';

COMMENT ON COLUMN "symbol"."symbol_name" IS '심볼 이름';

COMMENT ON COLUMN "symbol"."symbol_img_name" IS '심볼 이미지 이름';

COMMENT ON COLUMN "symbol"."symbol_code" IS '조건 구분 코드 : 메모 참고';

COMMENT ON COLUMN "symbol"."symbol_condition_cnt" IS '심볼 부여 조건 수';

COMMENT ON COLUMN "symbol"."create_dt" IS '심볼 데이터 추가 날짜';

CREATE TABLE "report" (
	"report_number"	BIGSERIAL		NOT NULL,
	"reporter"	INTEGER		NOT NULL,
	"suspect"	INTEGER		NOT NULL,
	"reason"	TEXT		NOT NULL,
	"result_state"	SMALLINT	DEFAULT 0	NOT NULL,
	"result_content"	TEXT		NULL,
	"report_result_dttm"	TIMESTAMP		NULL,
	"create_dttm"	TIMESTAMP	DEFAULT now()	NOT NULL
);

COMMENT ON COLUMN "report"."report_number" IS '신고 번호';

COMMENT ON COLUMN "report"."reporter" IS '신고하는 회원';

COMMENT ON COLUMN "report"."suspect" IS '신고당한 회원';

COMMENT ON COLUMN "report"."reason" IS '신고 사유';

COMMENT ON COLUMN "report"."result_state" IS '0 : 신청 / 1 : 처리중 / 2 : 처리 완료';

COMMENT ON COLUMN "report"."result_content" IS '신고 처리 결과';

COMMENT ON COLUMN "report"."report_result_dttm" IS '신고를 처리 완료 한 일시';

COMMENT ON COLUMN "report"."create_dttm" IS '신고 일시';

CREATE TABLE "alarm" (
	"alarm_number"	BIGSERIAL		NOT NULL,
	"member_number"	INTEGER		NOT NULL,
	"alarm_type"	VARCHAR(10)		NOT NULL,
	"alarm_reason"	VARCHAR(30)		NULL,
	"alarm_state"	SMALLINT	DEFAULT 0	NOT NULL,
	"read_dttm"	TIMESTAMP		NULL,
	"create_dttm"	TIMESTAMP	DEFAULT now()	NOT NULL
);

COMMENT ON COLUMN "alarm"."alarm_number" IS '알람 인덱스';

COMMENT ON COLUMN "alarm"."member_number" IS '회원번호 인덱스';

COMMENT ON COLUMN "alarm"."alarm_type" IS 'follow / likes / symbol / exp / regist / bubling';

COMMENT ON COLUMN "alarm"."alarm_reason" IS '회원 닉네임 / 칭호 이름 / 버블명';

COMMENT ON COLUMN "alarm"."alarm_state" IS '0 : 안읽음 / 1 : 읽음 / 2 : 삭제됨';

COMMENT ON COLUMN "alarm"."read_dttm" IS '회원이 알람을 읽은 시간(알람을 클릭 혹은 삭제)';

COMMENT ON COLUMN "alarm"."create_dttm" IS '알람 발생 일시';

CREATE TABLE "symbol_grant" (
	"granted_member_number"	BIGSERIAL	NOT NULL,
	"symbol_number"			BIGSERIAL	NOT NULL,
	"is_symbol_active"		BOOLEAN		DEFAULT FALSE	NOT NULL,
	"create_dttm"			TIMESTAMP	DEFAULT now()	NOT NULL
);

COMMENT ON COLUMN "symbol_grant"."granted_member_number" IS '심볼을 부여 받은 회원의 회원 번호';

COMMENT ON COLUMN "symbol_grant"."symbol_number" IS '심볼 인덱스';

COMMENT ON COLUMN "symbol_grant"."is_symbol_active" IS 'FALSE : 비활성화 / TRUE : 활성화(대표 심볼로 선택)';

COMMENT ON COLUMN "symbol_grant"."create_dttm" IS '심볼을 받은 일시';

CREATE TABLE "record" (
	"member_number"	BIGSERIAL		NOT NULL,
	"exp_cnt"	INTEGER	DEFAULT 0	NOT NULL,
	"feed_cnt"	INTEGER	DEFAULT 0	NOT NULL,
	"bubble_join_cnt"	INTEGER	DEFAULT 0	NOT NULL,
	"like_give_cnt"	INTEGER	DEFAULT 0	NOT NULL,
	"like_receive_cnt"	INTEGER	DEFAULT 0	NOT NULL,
	"best_cnt"	INTEGER	DEFAULT 0	NOT NULL,
	"following_cnt"	INTEGER	DEFAULT 0	NOT NULL,
	"follower_cnt"	INTEGER	DEFAULT 0	NOT NULL,
	"record_update_dttm"	TIMESTAMP	DEFAULT now()	NOT NULL
);

COMMENT ON COLUMN "record"."member_number" IS '회원번호 인덱스 : 자동증가';

COMMENT ON COLUMN "record"."exp_cnt" IS '사이트 이용 경험치';

COMMENT ON COLUMN "record"."feed_cnt" IS '작성한 피드 개수';

COMMENT ON COLUMN "record"."bubble_join_cnt" IS '버블 참여 횟수';

COMMENT ON COLUMN "record"."like_give_cnt" IS '피드에 좋아요 누른 수';

COMMENT ON COLUMN "record"."like_receive_cnt" IS '회원이 작성한 피드에 좋아요 받은 수 총합';

COMMENT ON COLUMN "record"."best_cnt" IS '버블에서 베스트 멤버로 뽑힌 수';

COMMENT ON COLUMN "record"."following_cnt" IS '해당 회원이 팔로잉 한 회원 수';

COMMENT ON COLUMN "record"."follower_cnt" IS '해당 회원을 팔로워한 회원 수';

COMMENT ON COLUMN "record"."record_update_dttm" IS '해당 회원의 기록 업데이트 일시';

CREATE TABLE "todo" (
	"todo_number"	BIGSERIAL		NOT NULL,
	"bubble_number"	BIGSERIAL		NOT NULL,
	"todo_context"	VARCHAR(50)		NOT NULL,
	"is_todo_done"	BOOLEAN	DEFAULT FALSE	NOT NULL,
	"create_dttm"	TIMESTAMP	DEFAULT now()	NOT NULL
);

COMMENT ON COLUMN "todo"."todo_number" IS '할 일 인덱스';

COMMENT ON COLUMN "todo"."bubble_number" IS '버블 인덱스';

COMMENT ON COLUMN "todo"."todo_context" IS '할 일 내용';

COMMENT ON COLUMN "todo"."is_todo_done" IS '할 일 완료 상태 여부';

COMMENT ON COLUMN "todo"."create_dttm" IS '할 일 생성 일시';

CREATE TABLE "best_member" (
	"bubble_number"	BIGSERIAL		NOT NULL,
	"member_number"	BIGSERIAL		NOT NULL,
	"best_member_number"	BIGSERIAL		NOT NULL,
	"create_dttm"	TIMESTAMP	DEFAULT now()	NOT NULL
);

COMMENT ON COLUMN "best_member"."bubble_number" IS '버블 인덱스 번호';

COMMENT ON COLUMN "best_member"."member_number" IS '베스트 멤버를 선택한 회원의 번호';

COMMENT ON COLUMN "best_member"."best_member_number" IS '베스트 멤버로 선택 된 회원의 번호';

COMMENT ON COLUMN "best_member"."create_dttm" IS '베스트 멤버 선택 일시';

CREATE TABLE "two_way_question" (
	"two_way_question_number"	BIGSERIAL		NOT NULL,
	"bubble_number"	BIGSERIAL		NOT NULL,
	"left_context"	VARCHAR(50)		NOT NULL,
	"right_context"	VARCHAR(50)		NOT NULL,
	"create_dttm"	TIMESTAMP	DEFAULT now()	NOT NULL
);

COMMENT ON COLUMN "two_way_question"."two_way_question_number" IS '양자택일 인덱스';

COMMENT ON COLUMN "two_way_question"."bubble_number" IS '버블 인덱스';

COMMENT ON COLUMN "two_way_question"."left_context" IS '양자택일 왼쪽 질문';

COMMENT ON COLUMN "two_way_question"."right_context" IS '양자택일 오른쪽 질문';

COMMENT ON COLUMN "two_way_question"."create_dttm" IS '양자택일 질문 생성 일시';

CREATE TABLE "two_way_answer" (
	"two_way_question_number"	BIGSERIAL		NOT NULL,
	"member_number"	BIGSERIAL		NOT NULL,
	"bubble_number"	BIGSERIAL		NOT NULL,
	"is_pick_right"	BOOLEAN	DEFAULT TRUE	NOT NULL,
	"create_dttm"	TIMESTAMP	DEFAULT now()	NOT NULL
);

COMMENT ON COLUMN "two_way_answer"."two_way_question_number" IS '양자택일 인덱스';

COMMENT ON COLUMN "two_way_answer"."member_number" IS '회원번호 인덱스 : 자동증가';

COMMENT ON COLUMN "two_way_answer"."bubble_number" IS '버블 인덱스';

COMMENT ON COLUMN "two_way_answer"."is_pick_right" IS 'TRUE : 오른쪽 / FALSE : 왼쪽 뽑음';

COMMENT ON COLUMN "two_way_answer"."create_dttm" IS '양자택일 답변 일시';

CREATE TABLE "vote_question" (
	"vote_question_number"	BIGSERIAL		NOT NULL,
	"bubble_number"	BIGSERIAL		NOT NULL,
	"vote_question_context"	VARCHAR(50)		NOT NULL,
	"is_active"	BOOLEAN	DEFAULT TRUE	NOT NULL,
	"create_dttm"	TIMESTAMP	DEFAULT now()	NOT NULL
);

COMMENT ON COLUMN "vote_question"."vote_question_number" IS '투표 질문 인덱스';

COMMENT ON COLUMN "vote_question"."bubble_number" IS '버블 인덱스';

COMMENT ON COLUMN "vote_question"."vote_question_context" IS '투표 질문 내용 : 글자 제한 50';

COMMENT ON COLUMN "vote_question"."is_active" IS 'TRUE : 투표 중인 질문 / FALSE : 삭제 된 질문';

COMMENT ON COLUMN "vote_question"."create_dttm" IS '투표 질문 생성 일시';

CREATE TABLE "vote_select" (
	"vote_question_number"	BIGSERIAL		NOT NULL,
	"member_number"	BIGSERIAL		NOT NULL
);

COMMENT ON COLUMN "vote_select"."vote_question_number" IS '투표 질문 인덱스';

COMMENT ON COLUMN "vote_select"."member_number" IS '회원번호 인덱스 : 자동증가';

ALTER TABLE "member" ADD CONSTRAINT "PK_MEMBER" PRIMARY KEY (
	"member_number"
);

ALTER TABLE "feed" ADD CONSTRAINT "PK_FEED" PRIMARY KEY (
	"feed_number"
);

ALTER TABLE "bubble" ADD CONSTRAINT "PK_bubble" PRIMARY KEY (
	"bubble_number"
);

ALTER TABLE "category" ADD CONSTRAINT "PK_CATEGORY" PRIMARY KEY (
	"category_number"
);

ALTER TABLE "bubble_list" ADD CONSTRAINT "PK_bubble_LIST" PRIMARY KEY (
	"bubble_number",
	"member_number"
);

ALTER TABLE "hashtag" ADD CONSTRAINT "PK_HASHTAG" PRIMARY KEY (
	"hashtag_number"
);

ALTER TABLE "likes" ADD CONSTRAINT "PK_LIKE" PRIMARY KEY (
	"like_feed_number",
	"like_member_number"
);

ALTER TABLE "tagging" ADD CONSTRAINT "PK_TAGGING" PRIMARY KEY (
	"bubble_number",
	"hashtag_number"
);

ALTER TABLE "follow" ADD CONSTRAINT "PK_FOLLOW" PRIMARY KEY (
	"follow_number"
);

ALTER TABLE "symbol" ADD CONSTRAINT "PK_SYMBOL" PRIMARY KEY (
	"symbol_number"
);

ALTER TABLE "report" ADD CONSTRAINT "PK_REPORT" PRIMARY KEY (
	"report_number"
);

ALTER TABLE "alarm" ADD CONSTRAINT "PK_ALARM" PRIMARY KEY (
	"alarm_number"
);

ALTER TABLE "symbol_grant" ADD CONSTRAINT "PK_GRANT" PRIMARY KEY (
	"granted_member_number",
	"symbol_number"
);

ALTER TABLE "record" ADD CONSTRAINT "PK_RECORD" PRIMARY KEY (
	"member_number"
);

ALTER TABLE "todo" ADD CONSTRAINT "PK_TODO" PRIMARY KEY (
	"todo_number"
);

ALTER TABLE "best_member" ADD CONSTRAINT "PK_BEST_MEMBER" PRIMARY KEY (
	"bubble_number",
	"member_number"
);

ALTER TABLE "two_way_question" ADD CONSTRAINT "PK_TWO_WAY_QUESTION" PRIMARY KEY (
	"two_way_question_number"
);

ALTER TABLE "two_way_answer" ADD CONSTRAINT "PK_TWO_WAY_ANSWER" PRIMARY KEY (
	"two_way_question_number",
	"member_number"
);

ALTER TABLE "vote_question" ADD CONSTRAINT "PK_VOTE_QUESTION" PRIMARY KEY (
	"vote_question_number"
);

ALTER TABLE "vote_select" ADD CONSTRAINT "PK_VOTE_SELECT" PRIMARY KEY (
	"vote_question_number",
	"member_number"
);

ALTER TABLE "feed" ADD CONSTRAINT "FK_member_TO_feed_1" FOREIGN KEY (
	"feed_creator_number"
)
REFERENCES "member" (
	"member_number"
);

ALTER TABLE "bubble" ADD CONSTRAINT "FK_member_TO_bubble_1" FOREIGN KEY (
	"member_number"
)
REFERENCES "member" (
	"member_number"
);

ALTER TABLE "bubble" ADD CONSTRAINT "FK_category_TO_bubble_1" FOREIGN KEY (
	"category_number"
)
REFERENCES "category" (
	"category_number"
);

ALTER TABLE "bubble_list" ADD CONSTRAINT "FK_bubble_TO_bubble_list_1" FOREIGN KEY (
	"bubble_number"
)
REFERENCES "bubble" (
	"bubble_number"
);

ALTER TABLE "bubble_list" ADD CONSTRAINT "FK_member_TO_bubble_list_1" FOREIGN KEY (
	"member_number"
)
REFERENCES "member" (
	"member_number"
);

ALTER TABLE "likes" ADD CONSTRAINT "FK_feed_TO_like_1" FOREIGN KEY (
	"like_feed_number"
)
REFERENCES "feed" (
	"feed_number"
);

ALTER TABLE "likes" ADD CONSTRAINT "FK_member_TO_like_1" FOREIGN KEY (
	"like_member_number"
)
REFERENCES "member" (
	"member_number"
);

ALTER TABLE "tagging" ADD CONSTRAINT "FK_bubble_TO_tagging_1" FOREIGN KEY (
	"bubble_number"
)
REFERENCES "bubble" (
	"bubble_number"
);

ALTER TABLE "tagging" ADD CONSTRAINT "FK_hashtag_TO_tagging_1" FOREIGN KEY (
	"hashtag_number"
)
REFERENCES "hashtag" (
	"hashtag_number"
);

ALTER TABLE "follow" ADD CONSTRAINT "FK_member_TO_follow_1" FOREIGN KEY (
	"follower_number"
)
REFERENCES "member" (
	"member_number"
);

ALTER TABLE "follow" ADD CONSTRAINT "FK_member_TO_follow_2" FOREIGN KEY (
	"following_number"
)
REFERENCES "member" (
	"member_number"
);

ALTER TABLE "report" ADD CONSTRAINT "FK_member_TO_report_1" FOREIGN KEY (
	"reporter"
)
REFERENCES "member" (
	"member_number"
);

ALTER TABLE "report" ADD CONSTRAINT "FK_member_TO_report_2" FOREIGN KEY (
	"suspect"
)
REFERENCES "member" (
	"member_number"
);

ALTER TABLE "alarm" ADD CONSTRAINT "FK_member_TO_alarm_1" FOREIGN KEY (
	"member_number"
)
REFERENCES "member" (
	"member_number"
);

ALTER TABLE "symbol_grant" ADD CONSTRAINT "FK_record_TO_grant_1" FOREIGN KEY (
	"granted_member_number"
)
REFERENCES "record" (
	"member_number"
);

ALTER TABLE "symbol_grant" ADD CONSTRAINT "FK_symbol_TO_grant_1" FOREIGN KEY (
	"symbol_number"
)
REFERENCES "symbol" (
	"symbol_number"
);

ALTER TABLE "record" ADD CONSTRAINT "FK_member_TO_record_1" FOREIGN KEY (
	"member_number"
)
REFERENCES "member" (
	"member_number"
);

ALTER TABLE "todo" ADD CONSTRAINT "FK_bubble_TO_todo_1" FOREIGN KEY (
	"bubble_number"
)
REFERENCES "bubble" (
	"bubble_number"
);

ALTER TABLE "best_member" ADD CONSTRAINT "FK_bubble_TO_best_member_1" FOREIGN KEY (
	"bubble_number"
)
REFERENCES "bubble" (
	"bubble_number"
);

ALTER TABLE "best_member" ADD CONSTRAINT "FK_member_TO_best_member_1" FOREIGN KEY (
	"member_number"
)
REFERENCES "member" (
	"member_number"
);

ALTER TABLE "best_member" ADD CONSTRAINT "FK_member_TO_best_member_2" FOREIGN KEY (
	"best_member_number"
)
REFERENCES "member" (
	"member_number"
);

ALTER TABLE "two_way_question" ADD CONSTRAINT "FK_bubble_TO_two_way_question_1" FOREIGN KEY (
	"bubble_number"
)
REFERENCES "bubble" (
	"bubble_number"
);

ALTER TABLE "two_way_answer" ADD CONSTRAINT "FK_two_way_question_TO_two_way_answer_1" FOREIGN KEY (
	"two_way_question_number"
)
REFERENCES "two_way_question" (
	"two_way_question_number"
);

ALTER TABLE "two_way_answer" ADD CONSTRAINT "FK_member_TO_two_way_answer_1" FOREIGN KEY (
	"member_number"
)
REFERENCES "member" (
	"member_number"
);

ALTER TABLE "two_way_answer" ADD CONSTRAINT "FK_bubble_TO_two_way_answer_1" FOREIGN KEY (
	"bubble_number"
)
REFERENCES "bubble" (
	"bubble_number"
);

ALTER TABLE "vote_question" ADD CONSTRAINT "FK_bubble_TO_vote_question_1" FOREIGN KEY (
	"bubble_number"
)
REFERENCES "bubble" (
	"bubble_number"
);

ALTER TABLE "vote_select" ADD CONSTRAINT "FK_vote_question_TO_vote_select_1" FOREIGN KEY (
	"vote_question_number"
)
REFERENCES "vote_question" (
	"vote_question_number"
);

ALTER TABLE "vote_select" ADD CONSTRAINT "FK_member_TO_vote_select_1" FOREIGN KEY (
	"member_number"
)
REFERENCES "member" (
	"member_number"
);



INSERT INTO "symbol" ("symbol_name", "symbol_img_name", "symbol_code", "symbol_condition_cnt") VALUES
	('1레벨 : AQuh 에 오신걸 환영합니다!', 'lv1.png', 'exp_cnt', 0),
	('2레벨 : 뉴비를 벗어나다', 'lv2.png', 'exp_cnt', 1000),
	('3레벨 : AQuh를 즐기는', 'lv3.png', 'exp_cnt', 2500),
	('4레벨 : AQuh 주민', 'lv4.png', 'exp_cnt', 4500),
	('5레벨 : AQuh 마스터', 'lv5.png', 'exp_cnt', 7000),
	('6레벨 : AQuh 고인물', 'lv6.png', 'exp_cnt', 10000),
	('버블 입문자', 'bb1.png', 'bubble_join_cnt', 1),
	('버블 이용자', 'bb2.png', 'bubble_join_cnt', 5),
	('버블 중견 이용자', 'bb3.png', 'bubble_join_cnt', 10),
	('버블을 즐기는', 'bb4.png', 'bubble_join_cnt', 20),
	('버블 마스터', 'bb5.png', 'bubble_join_cnt', 50),
	('버블 고인물', 'bb6.png', 'bubble_join_cnt', 100),
	('첫 베스트 멤버', 'Man_Standing.png', 'best_cnt', 1),
	('베스트 멤버에 익숙해진', 'Man_walking.png', 'best_cnt', 10),
	('베스트 멤버 마스터', 'Man_Running.png', 'best_cnt', 50),
	('AQua 베스트 멤버', 'Man_Dancing.png', 'best_cnt', 100),
	('피드를 시작한', 'p1.png', 'feed_cnt', 1),
	('피드를 즐겨 쓰는', 'p2.png', 'feed_cnt', 10),
	('피드에 할 말이 많은', 'p3.png', 'feed_cnt', 50),
	('피드 마스터', 'p4.png', 'feed_cnt', 200),
	('짝사랑', 'l1.png', 'like_give_cnt', 1),
	('좋아요를 누르기 시작한', 'l2.png', 'like_give_cnt', 10),
	('좋은 것들이 많아서', 'l3.png', 'like_give_cnt', 50),
	('적극적인 좋아요 표현', 'l4.png', 'like_give_cnt', 100),
	('AQuh를 좋아하는', 'l5.png', 'like_give_cnt', 200),
	('첫사랑', 'll1.png', 'like_receive_cnt', 1),
	('좋아요를 받기 시작한', 'll2.png', 'like_receive_cnt', 10),
	('좋아요를 많이 받은', 'll3.png', 'like_receive_cnt', 50),
	('수 많은 좋아요', 'll4.png', 'like_receive_cnt', 100),
	('AQuh 아이돌', 'll5.png', 'like_receive_cnt', 200),
	('팔로잉 시작', 'fg1.png', 'following_cnt', 1),
	('팔로잉을 즐기는', 'fg2.png', 'following_cnt', 10),
	('수 많은 팔로잉', 'fg3.png', 'following_cnt', 50),
	('팔로잉 마스터', 'fg4.png', 'following_cnt', 100),
	('AQuh를 팔로잉', 'fg5.png', 'following_cnt', 200),
	('팔로워 시작', 'fr1.png', 'follower_cnt', 1),
	('팔로워를 거느린', 'fr2.png', 'follower_cnt', 10),
	('수 많은 팔로워들', 'fr3.png', 'follower_cnt', 50),
	('팔로워 마스터', 'fr4.png', 'follower_cnt', 100),
	('AQuh 인플루언서', 'fr5.png', 'follower_cnt', 200),
	('스포츠-운동', '스포츠-운동.png', 'category', 10),
	('수공예-드로잉', '수공예-드로잉.png', 'category', 10),
	('요리-베이킹', '요리-베이킹.png', 'category', 10),
	('문화-예술', '문화-예술.png', 'category', 10),
	('미용-뷰티', '미용-뷰티.png', 'category', 10),
	('홈-리빙', '홈-리빙.png', 'category', 10),
	('자기개발', '자기개발.png', 'category', 10),
	('기타', '기타.png', 'category', 10);
	

INSERT INTO category (category_number, category_name) VALUES
	('1', '스포츠-운동'),
	('2', '수공예-드로잉'),
	('3', '요리-베이킹'),
	('4', '문화-예술'),
	('5', '미용-뷰티'),
	('6', '홈-리빙'),
	('7', '자기개발'),
	('8', '기타');
	
insert into member (member_id, member_email, member_password, member_nickname, member_name, member_intro, member_type, member_state, is_email_authentication, is_email_receive) 
values('testtesttest','test@test.com', 'test', '테스트계정', '테스트계정', '테스트계정입니다.', 'CO', 1, true,true);

insert into bubble (member_number,category_number,bubble_title,bubble_content,bubble_thumbnail,bubble_state, plan_open_dttm,plan_close_dttm)
values(1, 1, '테스트버블제목', '테스트버블내용', '테스트버블섬네일', true,'2023-08-18 09:00:00', '2023-08-18 12:00:00');