package DocumentPack.Tests;

import DocumentPack.Questions.QuestionType;

import java.io.Serializable;

public class EssayQuestionStruct implements Serializable
{
	private String questionName;
	private QuestionType questionType;
	private String theQuestion;
	private int questionPoint;

	public EssayQuestionStruct(String name, QuestionType type, String question, int point)
	{
		this.questionName=name;
		this.questionType=type;
		this.theQuestion=question;
		this.questionPoint=point;
	}

	public String getQuestionName(){return questionName;}
	public QuestionType getQuestionType(){return questionType;}
	public String getTheQuestion(){return theQuestion;}
	public int getQuestionPoint(){return questionPoint;}
}
