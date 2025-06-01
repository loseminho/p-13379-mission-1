package com.back;

import java.util.ArrayList;
import java.util.List;

public class Calc {
    public static int run(String exp){
        //값으로 받은 문자열을 각각의 토큰 단위로 분리 해주는 함수
        List<String> tokens = makeTokens(exp);

        //토큰으로 변경된 값을 계산(현재 야매)
        int ans = makeAnswer(tokens);



        return ans;
    }

    //값으로 받은 문자열을 각각의 토큰 단위로 분리 해주는 함수
    private static List<String> makeTokens(String exp) {
        List<String> tokens = new ArrayList<>();
        int i = 0;

        while (i<exp.length()){
            char c = exp.charAt(i);

            //공백일땐 단순 i를 카운팅하며 넘김
            if(c == ' '){
                i++;
                continue;
            }

            //해당 위치가 -인경우 = 뺄셈 연산자인지, 음수를 나타내는지 먼저 체크
            if(c == '-'){
                //음수인 경우 = (수식 첫 시작이 -인경우 또는 바로 앞에 공백이 있는 경우)
                if(i == 0 || exp.charAt(i-1) == ' '){
                    String num = "-";
                    i++;

                    while (i < exp.length()){
                        char nc = exp.charAt(i);
                        if('0'<= nc && nc <= '9'){
                            num += nc;
                            i++;
                        }else{
                            break;
                        }
                    }
                    //"-숫자" 모양으로 num을 만들어준 뒤 tokens에 추가
                    tokens.add(num);
                }else{
                    //뺼셈 연산자인 경우
                    tokens.add("-");
                    i++;
                }
            // -가 아닌 나머지 연산자인 경우
            }else if(c == '+' || c == '*' || c == '/'){
                tokens.add(String.valueOf(c));
                i++;
            //숫자인 경우
            }else if('0' <= c && c <= '9'){
                String num = "";
                while (i < exp.length()){
                    char nc = exp.charAt(i);
                    if ('0' <= nc && nc <= '9') {
                        num += nc;
                        i++;
                    } else {
                        break;
                    }
                }
                tokens.add(num);
            }

        }

        return tokens;
    }

    //토큰으로 변경된 값을 계산(현재 야매 = 좌에서 우로 계산이라)
    private static int makeAnswer(List<String> tokens){
        if (tokens == null || tokens.isEmpty()) {
            return 0; // 빈 리스트인 경우 0 반환
        }

        int result = 0;
        String currentOperator = "+"; // 초기 연산자를 '+'로 설정? 추가하여 첫 숫자를 더하도록 함

        for (int i = 0; i < tokens.size(); i++) {
            String token = tokens.get(i);

            try {
                // 토큰이 숫자인 경우
                int number = Integer.parseInt(token);

                switch (currentOperator) {
                    case "+":
                        result += number;
                        break;
                    case "-":
                        result -= number;
                        break;
                    case "*":
                        // *와 /는 이전 결과에 바로 적용하면 안되고, 이전 피연산자와 다음 피연산을 가지고 계산해야 함
                        // 현재 구조는 좌측부터 순차 계산이므로, 이전 계산된 result에 바로 적용..(야매)
                        result *= number;
                        break;
                    case "/":
                        if (number == 0) {
                            System.err.println("오류");
                            return 0; // 또는 예외 처리
                        }
                        result /= number;
                        break;
                }
            } catch (NumberFormatException e) {
                // 토큰이 숫자가 아닌 경우
                // 연산자일 때만 currentOperator를 업데이트합니다.
                if (token.equals("+") || token.equals("-") || token.equals("*") || token.equals("/")) {
                    currentOperator = token;
                } else {
                    System.err.println("계산에서 제외합니다.");
                }
            }
        }
        return result;
    }
}

