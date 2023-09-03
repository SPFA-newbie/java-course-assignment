package DocumentPack.Questions;

public class EssayQuestion extends BasicQuestion
{
	private String rightAnswer;

	public EssayQuestion(){}
	public EssayQuestion(String theName)
	{
		super(theName,QuestionType.Essay);
		rightAnswer=" ";
	}

	public void setRightAnswer(String theRight){rightAnswer = theRight;}
	public String getRightAnswer(){return rightAnswer;}
}
