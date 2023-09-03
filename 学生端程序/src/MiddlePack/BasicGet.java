package MiddlePack;

import DataOperationPack.InternetManager;
import DocumentPack.Members.Student;
import DocumentPack.Questions.BasicQuestion;
import DocumentPack.Tests.*;

import javax.swing.*;

public class BasicGet
{
	private BasicSend gsPair;
	private InternetManager itManager;

	private Student user;
	private TestPaper test;

	public BasicGet() {}

	public void linkPair(BasicSend pair){gsPair=pair;}
	public void linkInternet(InternetManager theItManager){itManager=theItManager;}

	public boolean setUser(Student me)
	{
		user=me;
		return true;
	}

	public boolean setTest()
	{
		test=itManager.getTest();
		gsPair.setPaper(test);
		return true;
	}

	public String[] getQuestionList()
	{
		String[] ret=new String[test.getChoiceQuestionNumber()+ test.getEssayQuestionNumber()];
		for(int i=0;i<test.getChoiceQuestionNumber();i++)
		{
			ret[i]=test.getChoiceQuestion(i).getQuestionName();
		}
		for(int i=0;i< test.getEssayQuestionNumber();i++)
		{
			ret[test.getChoiceQuestionNumber()+i]=test.getEssayQuestion(i).getQuestionName();
		}
		return ret;
	}
	public EssayQuestionStruct getEssayQuestion(String name)
	{
		for(int i=0;i<test.getEssayQuestionNumber();i++)
			if(test.getEssayQuestion(i).getQuestionName().equals(name))
				return test.getEssayQuestion(i);
		return null;
	}
	public ChoiceQuestionStruct getChoiceQuestion(String name)
	{
		for(int i=0;i< test.getChoiceQuestionNumber();i++)
			if(test.getChoiceQuestion(i).getQuestionName().equals(name))
				return test.getChoiceQuestion(i);
		return null;
	}

	public Student whoAmI()
	{
		return user;
	}

	public boolean hasPaper()
	{
		if(test!=null)return true;
		return false;
	}

	public String getTime()
	{
		return Integer.toString(test.getTestTime());
	}


	public BasicQuestion getQuestion(String questionName)//Occupied Position
	{
		return null;
	}

	public String getEssayAnswer(String name)
	{
		BackEssayQuestionStruct want=gsPair.getEssayQuestion(name);
		return want.getAnswer();
	}

	public int getChoiceAnswer(String name)
	{
		BackChoiceQuestionStruct want= gsPair.getChoiceQuestion(name);
		return want.getAnswer();
	}

	public String getPoint()
	{
		return itManager.getPoint();
	}
}
