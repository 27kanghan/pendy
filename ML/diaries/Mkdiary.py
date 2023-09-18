
#랭체인
from langchain.llms import OpenAI

#GPT
from openaikey import apikey

#키 등록
import os
os.environ["OPENAI_API_KEY"] = apikey

# 데이터프레임
import pandas as pd

def mkdiary(req):
    ret = {"message": "오늘은 소비내역이 없습니다"}
    # tempurature : 0 ~ 1 로 높아질수록 랜덤한 답변 생성 / 창의력
    llm = OpenAI(temperature=0.9)

    # req 포맷 : json
    # {
    # 		"ConsumptionLimit": 30000,
    # 		"ConsumptionDetails": {
    # 					"순대국밥": [9000, 3],
    # 					"메가커피": [2000, 4],
    # 		}
    # }

    # 받아온 req로 input_txt 수정
        # 접근하기 쉽게 데이터프레임화
    # BaseModel로 col을 명시해서 col_name와도 문제가 없습니다
    req_cols = list(req.keys())
    req_limit_amount = pd.DataFrame([req[req_cols[0]]])#[]로 감싼 이유_error: scalar values사용시 인덱스를 써주거나 list로 래핑
    req_consume_list = pd.DataFrame(req[req_cols[1]])

    limit_amount = req_limit_amount.iloc[0,0] #목표 금액
    # limit_amount = req_limit_amount["금액"][0]

        # 각 소비내역 시간 분류대로 텍스트화
    #컬럼 뽑아서
    #하나씩 붙이기
    consume_list = []
    feeling = ["매우 불만족","불만족","보통","만족","매우 만족"]
    for i in req_consume_list.columns:
        consume_list += str(i)+":"
        for j in req_consume_list[i]:
            # 3이 보통, feeling 리스트 참조
            consume_list += feeling[j]+","
    consume_list += " "
    consume_txt = ''.join(a for a in consume_list)



    #소비 내역으로 input_txt 생성
    #고정적인 입력값
    # 프롬프트 수정 필요(영어로 role등 명시해서(기능은 잘 돌아감, 토큰이 많이듬))
    input_head_txt =(
       "아래의 정보들로 일기를 써주세요\n"
        "일기 작성자는 5살입니다\n"
        "한번에 확인할 수 있게 컴팩트하게 작성해주세요\n"

        "제목 : 내용을 요약한 제목을 재미있게 10자 내외로 작성해주세요\n"
        "본문 : 일기를 작성하고\n"
        "피드백 : 소비 목표액을 고려한 피드백을 20자 내외로 적어주세요\n"

        "위 양식을 지켜주세요\n"
        "피드백은 초등학교 선생님의 입장에서 반말로 목표금액을 고려하여 경제적인 관점에서 작성해주세요\n")

    input_limit_txt = "\n소비한도금액:"+limit_amount+"\n"


    # 최종적으로 GPT에 입력할 텍스트
    input_txt = input_head_txt+consume_txt+input_limit_txt
    result = llm(input_txt)

    ret["message"] = result
    return ret

    # food : 식비
    # traffic : 교통
    # online : 온라인 쇼핑
    # offline : 오프라인 쇼핑
    # cafe : 카페/간식
    # housing : 주거/통신
    # fashion : 패션/미용
    # culture : 문화/여가