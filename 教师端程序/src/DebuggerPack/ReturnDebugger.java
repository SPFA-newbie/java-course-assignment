package DebuggerPack;

import DocumentPack.Members.Student;
import DocumentPack.Questions.BasicQuestion;
import DocumentPack.Questions.ChoiceQuestion;
import DocumentPack.Questions.EssayQuestion;
import DocumentPack.Questions.QuestionType;

public class ReturnDebugger
{
	public static String[] returnStringArray()
	{
		String[] ret=new String[2];
		ret[0]="123";
		ret[1]="abc";
		return ret;
	}
	public static BasicQuestion returnQuestion()
	{
		ChoiceQuestion ret=new ChoiceQuestion("1231231231231231231",QuestionType.Single,4);
		ret.setQuestionText("aaaaaaaaaa");
		ret.addOptions("bbbbbbbbbbbbbbbbb4313132132132132132bbb");
		ret.addOptions("cfasdadsasdsasdasdad64151313123132132132132a");
		ret.addOptions("ds45612564120315641203156486120356481203546812035648aads");
		ret.addOptions("zzxzxc31532031546896123468965126489561235468123546856xzczxc");
		ret.addRightAnswer(1);
		return ret;
	}

	public static Student returnStudent()
	{
		Student ret=new Student("1122334","Ni","192.111.111.111");
		return ret;
	}
}
