package DocumentPack.Tests;

import MiddlePack.BasicGet;
import DocumentPack.Members.Student;
import DocumentPack.Questions.ChoiceQuestion;

import java.io.Serializable;

public class BackChoiceQuestionStruct implements Serializable
{
	private String questionName;
	private Student formStudent;
	private int answer;
	private int point;
	private int getPoint;

	public BackChoiceQuestionStruct(){}

	public Student getFormStudent(){return formStudent;}
	public String getQuestionName(){return questionName;}
	public int getAnswer(){return answer;}
	public int getPoint(){return point;}
	public int getGetPoint(){return getPoint;}

	public void setQuestionName(String questionName) {this.questionName = questionName;}
	public void setFormStudent(Student formStudent){this.formStudent = formStudent;}
	public void setAnswer(int answer){this.answer = answer;}
	public void setPoint(int point){this.point = point;}

	public void givePoint(BasicGet getter)
	{
		ChoiceQuestion question=(ChoiceQuestion)getter.getQuestion(questionName);
		if(question.getRightAnswer()!=answer)getPoint=0;
		 else getPoint=point;
	}
}
