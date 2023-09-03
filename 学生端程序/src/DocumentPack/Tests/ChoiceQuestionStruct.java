package DocumentPack.Tests;

import DocumentPack.Questions.QuestionType;

import java.io.Serializable;

public class ChoiceQuestionStruct implements Serializable
{
	private String questionName;
	private QuestionType questionType;
	private String theQuestion;
	private int optionNumber;
	private String[] theOptions;
	private int questionPoint;

	public ChoiceQuestionStruct
		   (String name,QuestionType type,String question,int number,String[] option,int point)
	{
		this.questionName=name;
		this.questionType=type;
		this.theQuestion=question;
		this.optionNumber=number;
		this.theOptions=option;
		this.questionPoint=point;
	}

	public String getQuestionName(){return questionName;}
	public QuestionType getQuestionType(){return questionType;}
	public String getTheQuestion(){return theQuestion;}
	public int getOptionNumber(){return optionNumber;}
	public String[] getTheOptions(){return theOptions;}
	public int getQuestionPoint(){return questionPoint;}
}
