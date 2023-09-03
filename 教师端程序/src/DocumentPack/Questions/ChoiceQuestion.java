package DocumentPack.Questions;

import java.util.ArrayList;
import java.util.Iterator;

public class ChoiceQuestion extends BasicQuestion
{
	public static final int[] answerTag={2,3,5,7,11,13,17,19};

	private Integer optionNumber;
	private ArrayList<String> options;
	private Integer rightAnswer;

	public ChoiceQuestion()
	{
		options=new ArrayList<>();
	}
	public ChoiceQuestion(String theName,QuestionType theType,int numberOfOption)
	{
		super(theName,theType);
		optionNumber=numberOfOption;
		options=new ArrayList<>();
		rightAnswer=1;
	}

	public void resetOptions(String option,int position){options.set(position,option);}
	public void setOptionNumber(int theNumber){optionNumber=theNumber;}
	public void setRightAnswer(int theRightAnswer){rightAnswer=theRightAnswer;}
	public void clearOptions(){options.clear();}
	public void clearRightAnswer(){rightAnswer=1;}
	public void addOptions(String option)
	{
		if(options.size()==optionNumber)optionNumber++;
		options.add(option);
	}
	public void addRightAnswer(int answerPosition)
	{
		if(this.getType()==QuestionType.Single)rightAnswer=1;
		if(rightAnswer%answerTag[answerPosition]!=0)
			rightAnswer*=answerTag[answerPosition];
	}
	public void removeOption()
	{
		if(optionNumber>0)
		{
			optionNumber--;
			if(rightAnswer%answerTag[optionNumber]==0)
				rightAnswer/=answerTag[optionNumber];
			options.remove((int)optionNumber);
		}
	}

	public int getOptionNumber() {return optionNumber;}
	public int getRightAnswer(){return rightAnswer;}
	public String getChoice(int position){return options.get(position);}
	public String[] getChoice()
	{
		String[] ret=new String[optionNumber];
		int p=0;
		for (Iterator<String> position=options.iterator();position.hasNext();)
		{
			ret[p]=position.next();
			p++;
		}
		return ret;
	}

	public boolean isRight(int position){return rightAnswer%answerTag[position]==0?true:false;}
}
