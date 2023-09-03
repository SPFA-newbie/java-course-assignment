package MiddlePack;

import DataOperationPack.DataBaseManager;
import DataOperationPack.InternetManager;
import DocumentPack.Members.Student;
import DocumentPack.Questions.BasicQuestion;
import DocumentPack.Questions.ChoiceQuestion;
import DocumentPack.Questions.EssayQuestion;
import DocumentPack.Questions.QuestionType;
import DocumentPack.Tests.BackTestStruct;

import java.util.ArrayList;

public class BasicGet
{
	private BasicSend gsPair;
	private DataBaseManager dbManager;
	private InternetManager itManager;

	public BasicGet() {}

	public void linkPair(BasicSend pair){gsPair=pair;}
	public void linkDataBase(DataBaseManager theDbManager){dbManager=theDbManager;}
	public void linkInternet(InternetManager theItManager){itManager=theItManager;}

//About DataBase
	public String[] getQuestionList(QuestionType type)
	{
		String[] ret;
		String[] essayRet=dbManager.getEssay();
		String[] choiceRet=dbManager.getChoice();
		if(type==QuestionType.All)
		{
			ret=new String[essayRet.length+choiceRet.length];
			for(int i=0;i<essayRet.length;i++)
				ret[i]=essayRet[i];
			for(int i=essayRet.length;i<ret.length;i++)
				ret[i]=choiceRet[i-essayRet.length];
		}else if(type==QuestionType.Essay)
		{
			ret=essayRet;
		}else if(choiceRet.length==1&&choiceRet[0].equals(""))
		{
			ret=new String[1];
			ret[0]="null";
			return ret;
		}else
		{
			int num=0;
			for(int i=0;i<choiceRet.length;i++)
				if(dbManager.searchChoice(choiceRet[i])[1].equals(type.toString()))
					num++;
			ret=new String[num];
			num=0;
			for(int i=0;i<choiceRet.length;i++)
				if(dbManager.searchChoice(choiceRet[i])[1].equals(type.toString()))
				{
					ret[num]=choiceRet[i];
					num++;
				}
		}
		return ret;
	}
	public BasicQuestion getQuestion(String name)
	{
		BasicQuestion ret;
		if(dbManager.hasChoice(name))
		{
			ret=new ChoiceQuestion();
			String[] question=dbManager.searchChoice(name);
			ret.setName(question[0]);
			ret.setType(QuestionType.valueOf(question[1]));
			ret.setQuestionText(question[2]);

			if(question[3].equals(""))question[3]="0";
			if(question[4].equals(""))question[4]="1";

			((ChoiceQuestion)ret).setOptionNumber(Integer.parseInt(question[3]));
			((ChoiceQuestion)ret).setRightAnswer(Integer.parseInt(question[4]));
			for(int i=0;i<((ChoiceQuestion)ret).getOptionNumber();i++)
				((ChoiceQuestion)ret).addOptions(question[5+i]);
		}else if(dbManager.hasEssay(name))
		{
			ret=new EssayQuestion();
			String[] question=dbManager.searchEssay(name);
			ret.setName(question[0]);
			ret.setType(QuestionType.Essay);
			ret.setQuestionText(question[1]);
			((EssayQuestion)ret).setRightAnswer(question[2]);
		}else ret=null;
		return ret;
	}

	public String[] getStudentList()
	{
		String[] ret=dbManager.getStudent();
		if(ret.length==1&&ret[0].equals(""))return new String[0];
		return ret;
	}
	public Student getStudent(String id)
	{
		if(!dbManager.hasStudent(id))return new Student("","","");
		String[] message=dbManager.searchStudent(id);
		return new Student(id,message[0],message[1]);
	}

	public Student[] getAllStudent()
	{
		String[] list=this.getStudentList();
		if(list.length==0)return new Student[0];
		Student[] ret=new Student[list.length];
		for(int i=0;i<list.length;i++)
		{
			ret[i]=this.getStudent(list[i]);
		}
		return ret;
	}

//About Internet
	public ArrayList<BackTestStruct> getTestArray()
	{
		ArrayList<BackTestStruct> ret=new ArrayList<>();
		BackTestStruct[] array= itManager.getBackPaper();
		for(int i=0;i<array.length;i++)
			ret.add(array[i]);
		return ret;
	}
}
