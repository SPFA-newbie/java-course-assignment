package MiddlePack;

import DataOperationPack.DataBaseManager;
import DataOperationPack.InternetManager;
import DocumentPack.Members.Student;
import DocumentPack.Questions.BasicQuestion;
import DocumentPack.Questions.ChoiceQuestion;
import DocumentPack.Questions.EssayQuestion;
import DocumentPack.Questions.QuestionType;
import DocumentPack.Tests.BackTestStruct;
import DocumentPack.Tests.TestPaper;
import StringPack.FileStrings;
import StringPack.MessageStrings;
import StringPack.OperationStrings;
import StringPack.TitleStrings;

import javax.swing.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class BasicSend
{
	private BasicGet gsPair;
	private DataBaseManager dbManager;
	private InternetManager itManager;

	public BasicSend(){}

	public void linkPair(BasicGet getter){gsPair=getter;}
	public void linkDataBase(DataBaseManager theDbManager){dbManager=theDbManager;}
	public void linkInternet(InternetManager theItManager){itManager=theItManager;}

//About DataBase
	public boolean sendLoginMessage(String name, char[] password)
	{
		String passwd="";
		for(int i=0;i<password.length;i++)
			passwd=passwd+password[i];

		if(!dbManager.hasUser(name))return false;
		if(dbManager.searchUser(name).equals(passwd))return true;
		return false;
	}
	public boolean sendRegisterMessage(String name, char[] password)
	{
		String passwd="";
		for(int i=0;i<password.length;i++)
			passwd=passwd+password[i];

		if(dbManager.hasUser(name))return false;
		dbManager.insertUser(name,passwd);
		return true;
	}
	public boolean sendQuestion(BasicQuestion question)
	{
		String name=question.getName();
		String text=question.getQuestionText();
		if(!(dbManager.hasChoice(name)||dbManager.hasEssay(name)))return false;
		if(question.getType()== QuestionType.Essay)
		{
			String answer=((EssayQuestion)question).getRightAnswer();
			dbManager.updateEssay(name,text,answer);
		}else
		{
			String type=question.getType().toString();
			String number=Integer.toString(((ChoiceQuestion)question).getOptionNumber());
			String answer=Integer.toString(((ChoiceQuestion)question).getRightAnswer());
			String[] options=((ChoiceQuestion)question).getChoice();
			dbManager.updateChoice(name,text,number,answer,options);
		}
		return true;
	}
	public boolean sendNewQuestion(BasicQuestion question)
	{
		String name=question.getName();
		String type=question.getType().toString();
		if(dbManager.hasChoice(name)||dbManager.hasEssay(name))return false;
		if(question.getType()==QuestionType.Essay)dbManager.insertEssay(name);
		 else dbManager.insertChoice(name,type);

		return true;
	}

	public boolean deleteQuestion(BasicQuestion question)
	{
		String name=question.getName();
		if(!(dbManager.hasChoice(name)||dbManager.hasEssay(name)))return false;
		if(question.getType()==QuestionType.Essay)dbManager.deleteEssay(name);
		 else dbManager.deleteChoice(name);

		return true;
	}

	public boolean deleteStudent(String id)
	{
		if(!dbManager.hasStudent(id))return false;
		dbManager.deleteStudent(id);
		return true;
	}

	public boolean sendNewStudent(String name,String id,String ip)
	{
		if(dbManager.hasStudent(id))return false;
		dbManager.insertStudent(id,name,ip);
		return true;
	}

//About Internet
	public boolean sendPaper(TestPaper paper, Student[] students)
	{
		itManager.setPaper(paper);
		return true;
	}
	public boolean setExamResult(ArrayList<BackTestStruct> papers)
	{
		itManager.correctOver(papers);
		return true;
	}

//About File
	public void print(ArrayList<BackTestStruct> papers)
	{
		try
		{
			File file=new File(OperationStrings.ResultPath +File.separator+ OperationStrings.ResultFileName);
			if(file.exists())
			{
				file.delete();
			}
			file.createNewFile();

			//make html file
			FileWriter writer=new FileWriter(file);
			writer.write(FileStrings.HTML_head);
			for(int i=0;i<papers.size();i++)
			{
				BackTestStruct paper=papers.get(i);
				Student student=paper.getFromStudent();
				writer.write(FileStrings.HTML_newLine(Integer.toString(i+1),
													  student.getId(),student.getName(),
													  Integer.toString(paper.getFinalPoint())));
			}
			writer.write(FileStrings.HTML_tail);
			writer.close();
		}catch (IOException e)
		{
			JOptionPane.showMessageDialog
					(null, MessageStrings.FileOperateError,
					 TitleStrings.FileError,JOptionPane.ERROR_MESSAGE);
		}
	}

}
