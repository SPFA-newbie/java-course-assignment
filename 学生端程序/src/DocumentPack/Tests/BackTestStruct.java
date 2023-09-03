package DocumentPack.Tests;

import MiddlePack.BasicGet;
import DocumentPack.Members.Student;

import java.io.Serializable;
import java.util.ArrayList;

public class BackTestStruct implements Serializable
{
	private Student fromStudent;

	private int essayQuestionNumber;
	private int choiceQuestionNumber;
	private ArrayList<BackChoiceQuestionStruct> choiceQuestions;
	private ArrayList<BackEssayQuestionStruct> essayQuestions;

	private int finalPoint;

	public BackTestStruct(Student student,int essayNumber,int choiceNumber)
	{
		fromStudent=student;
		essayQuestionNumber=essayNumber;
		choiceQuestionNumber=choiceNumber;
		essayQuestions=new ArrayList<>();
		choiceQuestions=new ArrayList<>();
		finalPoint=-1;
	}

	public void addChoiceQuestion(BackChoiceQuestionStruct struct){choiceQuestions.add(struct);}
	public void addEssayQuestion(BackEssayQuestionStruct struct){essayQuestions.add(struct);}

	public void takeChoicePoint(BasicGet getter)
	{
		if(finalPoint==-1)finalPoint=0;
		for(int i=0;i<choiceQuestionNumber;i++)
		{
			choiceQuestions.get(i).givePoint(getter);
			finalPoint+=choiceQuestions.get(i).getGetPoint();
		}
	}
	public void takeEssayPoint()
	{
		if(finalPoint==-1)finalPoint=0;
		for(int i=0;i<essayQuestionNumber;i++)
			finalPoint+=essayQuestions.get(i).getGetPoint();
	}

	public Student getFromStudent(){return fromStudent;}
	public int getEssayQuestionNumber(){return essayQuestionNumber;}
	public BackChoiceQuestionStruct getChoiceQuestions(int index){return choiceQuestions.get(index);}
	public BackEssayQuestionStruct getEssayQuestions(int index){return essayQuestions.get(index);}
	public BackEssayQuestionStruct getEssayQuestions(String name)
	{
		for(int i=0;i<essayQuestionNumber;i++)
			if(essayQuestions.get(i).getQuestionName().equals(name))
				return essayQuestions.get(i);
		return null;
	}
	public int getFinalPoint(){return finalPoint;}
}
