package DebuggerPack;

import DocumentPack.Members.Student;
import DocumentPack.Tests.BackChoiceQuestionStruct;
import DocumentPack.Tests.BackEssayQuestionStruct;
import DocumentPack.Tests.BackTestStruct;
import DocumentPack.Tests.TestPaper;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;

public class InternetDebugger
{
	public static void main(String[] args)throws Exception
	{
		Socket link=new Socket("localhost",3456);
		ObjectOutputStream output=new ObjectOutputStream(link.getOutputStream());
		output.writeObject("Can");
		ObjectInputStream input=new ObjectInputStream(link.getInputStream());
		Scanner in=new Scanner(System.in);

		Student student=new Student("100200300","张骏祺","100200300");
		TestPaper paper=new TestPaper();
		BackTestStruct back;

		System.out.println(input.readObject());
		while(true)
		{
			String operator=in.next();
			System.out.println("in");
			if(operator.equals("student"))
			{
				output.writeObject(student);
				System.out.println(input.readObject());
				System.out.println();
			}else if(operator.equals("paper"))
			{
				output.writeObject(true);
				System.out.println("take it");
				paper=(TestPaper)input.readObject();
				System.out.println(paper);
				System.out.println();
			}else if(operator.equals("point"))
			{
				output.writeObject(false);
				System.out.println((String)input.readObject());
			}else if(operator.equals("give"))
			{
				back=new BackTestStruct(student,paper.getEssayQuestionNumber(), paper.getChoiceQuestionNumber());
				for(int i=0;i< paper.getEssayQuestionNumber();i++)
				{
					BackEssayQuestionStruct essay=new BackEssayQuestionStruct();
					essay.setQuestionName(paper.getEssayQuestion(i).getQuestionName());
					essay.setPoint(paper.getEssayQuestion(i).getQuestionPoint());
					essay.setFormStudent(student);
					essay.setAnswer("123456789");
					back.addEssayQuestion(essay);
				}
				for(int i=0;i< paper.getChoiceQuestionNumber();i++)
				{
					BackChoiceQuestionStruct choice=new BackChoiceQuestionStruct();
					choice.setPoint(paper.getChoiceQuestion(i).getQuestionPoint());
					choice.setFormStudent(student);
					choice.setQuestionName(paper.getChoiceQuestion(i).getQuestionName());
					choice.setAnswer(2);
					back.addChoiceQuestion(choice);
				}
				output.writeObject(back);
			}
		}
	}
}
