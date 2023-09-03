package DocumentPack.Tests;


import DocumentPack.Questions.BasicQuestion;
import DocumentPack.Questions.ChoiceQuestion;
import DocumentPack.Questions.EssayQuestion;
import DocumentPack.Questions.QuestionType;
import StringPack.OperationStrings;

import java.io.Serializable;
import java.util.ArrayList;

public class TestPaper implements Serializable
{
	private int testTime;
	private int essayQuestionNumber;
	private int choiceQuestionNumber;
	private ArrayList<ChoiceQuestionStruct> choiceQuestions;
	private ArrayList<EssayQuestionStruct> essayQuestions;

	public TestPaper()
	{
		testTime=Integer.parseInt(OperationStrings.TestTime);
		essayQuestionNumber =0;
		choiceQuestionNumber =0;
		choiceQuestions=new ArrayList<>();
		essayQuestions=new ArrayList<>();
	}

	public void setTestTime(int min){testTime= min;}

	public void addQuestion(BasicQuestion question,int point)
	{
		if(question.getType()== QuestionType.Essay)this.addEssayQuestion((EssayQuestion) question,point);
		 else this.addChoiceQuestion((ChoiceQuestion)question,point);
	}

	private void addEssayQuestion(EssayQuestion question,int point)
	{
		EssayQuestionStruct newQuestion=new EssayQuestionStruct
				(question.getName(),QuestionType.Essay,question.getQuestionText(),point);
		essayQuestionNumber++;
		essayQuestions.add(newQuestion);
	}

	private void addChoiceQuestion(ChoiceQuestion question,int point)
	{
		ChoiceQuestionStruct newQuestion=new ChoiceQuestionStruct
				(question.getName(),question.getType(),question.getQuestionText(),
				 question.getOptionNumber(),question.getChoice(),point);

		choiceQuestionNumber++;
		choiceQuestions.add(newQuestion);
	}

	public void deleteQuestion(String name)
	{
		for(int i=0;i<choiceQuestionNumber;i++)
		 if(choiceQuestions.get(i).getQuestionName().equals(name))
		 {
		 	choiceQuestions.remove(i);
		 	choiceQuestionNumber--;
		 	return;
		 }
		for(int i=0;i<essayQuestionNumber;i++)
		 if(essayQuestions.get(i).getQuestionName().equals(name))
		 {
		 	essayQuestions.remove(i);
		 	essayQuestionNumber--;
		 	return;
		 }
	}

	public int getTestTime(){return testTime;}

	public int getChoiceQuestionNumber(){return choiceQuestionNumber;}
	public int getEssayQuestionNumber(){return essayQuestionNumber;}

	public ChoiceQuestionStruct[] getChoiceQuestions()
	{
		ChoiceQuestionStruct[] retStruct=new ChoiceQuestionStruct[choiceQuestionNumber];
		for(int i=0;i<choiceQuestionNumber;i++)
			retStruct[i]=choiceQuestions.get(i);
		return retStruct;
	}
	public EssayQuestionStruct[] getEssayQuestions()
	{
		EssayQuestionStruct[] retStruct=new EssayQuestionStruct[essayQuestionNumber];
		for(int i=0;i<essayQuestionNumber;i++)
			retStruct[i]=essayQuestions.get(i);
		return retStruct;
	}

	public ChoiceQuestionStruct getChoiceQuestion(int index){return choiceQuestions.get(index);}
	public EssayQuestionStruct getEssayQuestion(int index){return essayQuestions.get(index);}
}
