package DocumentPack.Questions;

public class BasicQuestion
{

	private String name;//ID of Question
	private QuestionType type;//type of question
	private String questionText;

	public BasicQuestion(){}
	public BasicQuestion(String theName,QuestionType theType)
	{
		name=theName;
		type=theType;
	}

	public void setName(String theName){name=theName;}
	public void setType(QuestionType theType){type=theType;}
	public void setQuestionText(String theText){questionText=theText;}

	public String getName(){return name;}
	public QuestionType getType(){return type;}
	public String getQuestionText(){return questionText;}
}
