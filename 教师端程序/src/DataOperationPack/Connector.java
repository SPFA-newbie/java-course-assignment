package DataOperationPack;

import DocumentPack.Members.Student;
import DocumentPack.Tests.BackTestStruct;
import StringPack.MessageStrings;

import javax.swing.*;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Connector implements Runnable
{
	//Use their Class name
	private static final Student studentTag=new Student("","","");
	private static final Boolean booleanTag=true;
	private static final BackTestStruct testTag=new BackTestStruct(studentTag,0,0);
	//-------------------
	private Socket pipe;

	private ObjectInputStream input;
	private ObjectOutputStream output;

	private boolean linked;
	private boolean living;
	private Student whoLinked;
	private InternetManager manager;

	public Connector(Socket socket,InternetManager theManager)
	{
		linked=false;
		living=false;
		whoLinked=null;

		whoLinked=new Student(MessageStrings.UnknownStudent,"","");

		pipe=socket;
		manager=theManager;
	}

	public void run()
	{
		try
		{
			input=new ObjectInputStream(pipe.getInputStream());
			input.readObject();
			output=new ObjectOutputStream(pipe.getOutputStream());
			output.writeObject("linked");
			while(true)
			{
				Object object=input.readObject();
				if(object.getClass().getName().equals(studentTag.getClass().getName()))
				{
					if(manager.giveStudent(((Student)object).getId()).equals((Student)object))
					{
						output.writeObject(true);
						whoLinked=(Student)object;
						linked=true;
					}else
						output.writeObject(false);
				}else if(object.getClass().getName().equals(booleanTag.getClass().getName()))
				{
					if(linked)
					{
						if((Boolean)object)
						{
							while(manager.givePaper()==null)
								Thread.sleep(100);
							living=true;
							output.writeObject(manager.givePaper());
						}else
						{
							output.writeObject(manager.givePoint(whoLinked));
						}
					}else
					{
						input.close();
						output.close();
						return;
					}
				}else if(object.getClass().getName().equals(testTag.getClass().getName()))
				{
					if(linked&&living)
					{
						manager.catchBackTest((BackTestStruct)object);
						living=false;
					}else
					{
						input.close();
						output.close();
						return;
					}
				}else
				{
					input.close();
					output.close();
					return;
				}
			}
		}catch (Exception e)
		{
			JOptionPane.showMessageDialog
					(null, whoLinked.getId()+MessageStrings.ConnectionBroken,
					 "",JOptionPane.INFORMATION_MESSAGE);
		}finally
		{
			this.killMe();
		}
	}

	public void killMe()
	{
		manager.killConnector(this);
	}
}
