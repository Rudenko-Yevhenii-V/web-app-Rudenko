package ry.rudenko.englishlessonswebapp.auth.bean;


import lombok.Data;

@Data
public class CreateTestRequest {
    private  String testName;
    private  Integer questionOrder;
    private  String questionText;
    private  Integer answerOrder;
    private  String answerText;
    private  String falseAnswerText1;
    private  String falseAnswerText2;
    private  String falseAnswerText3;
}
