package MiddlePack;

import DataOperationPack.InternetManager;
import DocumentPack.Members.Student;
import DocumentPack.Questions.QuestionType;
import DocumentPack.Tests.*;
import StringPack.MessageStrings;
import StringPack.TitleStrings;

import javax.swing.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class BasicSend
{
	private BasicGet gsPair;
	private InternetManager itManager;

	private BackChoiceQuestionStruct[] choiceQuestions;
	private BackEssayQuestionStruct[] essayQuestions;

	public BasicSend(){}

	public void linkPair(BasicGet getter){gsPair=getter;}
	public void linkInternet(InternetManager theItManager){itManager=theItManager;}

	public boolean sendLoginMessage(String id,String name, char[] password)
	{
		String passwd="";
		for(int i=0;i<password.length;i++)
			passwd=passwd+password[i];

		Student me=new Student(id,name,passwd);
		if(itManager.giveStudent(me))
		{
			gsPair.setUser(me);
			return true;
		}
		return false;
	}

	public void setPaper(TestPaper thePaper)
	{
		choiceQuestions=new BackChoiceQuestionStruct[thePaper.getChoiceQuestionNumber()];
		essayQuestions=new BackEssayQuestionStruct[thePaper.getEssayQuestionNumber()];
		for(int i=0;i< thePaper.getEssayQuestionNumber();i++)
		{
			BackEssayQuestionStruct question=new BackEssayQuestionStruct();
			question.setQuestionName(thePaper.getEssayQuestion(i).getQuestionName());
			question.setFormStudent(gsPair.whoAmI());
			question.setPoint(thePaper.getEssayQuestion(i).getQuestionPoint());
			question.setAnswer("");
			essayQuestions[i]=question;
		}
		for(int i=0;i<thePaper.getChoiceQuestionNumber();i++)
		{
			BackChoiceQuestionStruct question=new BackChoiceQuestionStruct();
			question.setQuestionName(thePaper.getChoiceQuestion(i).getQuestionName());
			question.setFormStudent(gsPair.whoAmI());
			question.setPoint(thePaper.getChoiceQuestion(i).getQuestionPoint());
			question.setAnswer(1);
			choiceQuestions[i]=question;
		}
	}

	public BackChoiceQuestionStruct getChoiceQuestion(String name)
	{
		for(int i=0;i<choiceQuestions.length;i++)
			if(choiceQuestions[i].getQuestionName().equals(name))
				return choiceQuestions[i];
		return null;
	}

	public BackEssayQuestionStruct getEssayQuestion(String name)
	{
		for(int i=0;i<essayQuestions.length;i++)
			if(essayQuestions[i].getQuestionName().equals(name))
				return essayQuestions[i];
		return null;
	}

	public boolean updatePaper(BackChoiceQuestionStruct question)
	{
		for(int i=0;i<choiceQuestions.length;i++)
			if(choiceQuestions[i].getQuestionName().equals(question.getQuestionName()))
				choiceQuestions[i]=question;
		return true;
	}

	public boolean updatePaper(BackEssayQuestionStruct question)
	{
		for(int i=0;i<essayQuestions.length;i++)
			if(essayQuestions[i].getQuestionName().equals(question.getQuestionName()))
				essayQuestions[i]=question;
		return true;
	}

	public boolean ready()
	{
		BackTestStruct paper=new BackTestStruct(gsPair.whoAmI(),essayQuestions.length,choiceQuestions.length);
		for(int i=0;i<essayQuestions.length;i++)
			paper.addEssayQuestion(essayQuestions[i]);

		for(int i=0;i<choiceQuestions.length;i++)
			paper.addChoiceQuestion(choiceQuestions[i]);

		itManager.sendPaper(paper);
		return true;
	}
}
