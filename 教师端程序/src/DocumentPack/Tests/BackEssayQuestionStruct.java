package DocumentPack.Tests;

import DocumentPack.Members.Student;

import java.io.Serializable;

public class BackEssayQuestionStruct implements Serializable
{
	private String questionName;
	private Student formStudent;
	private String answer;
	private int point;
	private int getPoint;

	public BackEssayQuestionStruct(){}

	public Student getFormStudent(){return formStudent;}
	public String getQuestionName(){return questionName;}
	public String getAnswer(){return answer;}
	public int getPoint(){return point;}
	public int getGetPoint(){return getPoint;}

	public void setQuestionName(String questionName) {this.questionName = questionName;}
	public void setFormStudent(Student formStudent){this.formStudent = formStudent;}
	public void setAnswer(String answer){this.answer = answer;}
	public void setPoint(int point){this.point = point;}

	public boolean givePoint(int thePoint)
	{
		if(thePoint >0&&thePoint<=point)
		{
			this.getPoint=thePoint;
			return true;
		}
		return false;
	}
}
