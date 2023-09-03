package DataOperationPack;

import DocumentPack.Members.Student;
import DocumentPack.Tests.BackTestStruct;
import DocumentPack.Tests.TestPaper;
import MiddlePack.BasicGet;
import StringPack.InternetString;
import StringPack.MessageStrings;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

public class InternetManager
{
	private Socket link;
	private ObjectInputStream input;
	private ObjectOutputStream output;

	public InternetManager()
	{
		boolean success=false;
		while(!success)
		{
			try
			{
				link=new Socket(InternetString.Address,Integer.parseInt(InternetString.Port));
				output=new ObjectOutputStream(link.getOutputStream());
				output.writeObject("Can");
				input=new ObjectInputStream(link.getInputStream());
				input.readObject();
				success=true;
			}catch (Exception e)
			{
				success=false;
			}
		}
	}

	public TestPaper getTest()
	{
		try
		{
			output.writeObject(true);
			return (TestPaper)input.readObject();
		}catch (Exception e)
		{
		}
		return null;
	}

	public boolean giveStudent(Student me)
	{
		try
		{
			output.writeObject(me);
			return (Boolean)input.readObject();
		}catch (Exception e)
		{
		}
		return false;
	}

	public void sendPaper(BackTestStruct paper)
	{
		try
		{
			output.writeObject(paper);
		}catch (Exception e)
		{
		}
	}

	public String getPoint()
	{
		try
		{
			output.writeObject(false);
			return (String)input.readObject();
		}catch (Exception e)
		{
		}
		return "";
	}
}
