package DataOperationPack;

import StringPack.DataBaseStrings;
import StringPack.MessageStrings;
import StringPack.TitleStrings;

import javax.swing.*;
import java.sql.*;
import java.util.ArrayList;

public class DataBaseManager
{
	private Connection link;

	public DataBaseManager()
	{
		try
		{
			Class.forName(DataBaseStrings.DataBaseDrive);
			link=DriverManager.getConnection(DataBaseStrings.DataBaseUrl,
											 DataBaseStrings.DataBaseUser,
											 DataBaseStrings.DataBasePassword);
		}catch (Exception e)
		{
			this.dataBaseError();
		}

	}

//Insert Part
	public void insertUser(String name,String password)
	{
		try
		{
			Statement state=link.createStatement();
			state.executeUpdate(DataBaseStrings.InsertUser+"(\""+name+"\",\""+password+"\")");
		}catch (Exception e)
		{
			dataBaseError();
		}
	}
	public void insertStudent(String id,String name,String password)
	{
		try
		{
			Statement state=link.createStatement();
			state.executeUpdate(DataBaseStrings.InsertStudent+"(\""+id+"\",\""+name+"\",\""+ password +"\")");
		}catch (Exception e)
		{
			dataBaseError();
		}
	}
	public void insertEssay(String name)
	{
		try
		{
			Statement state=link.createStatement();
			state.executeUpdate(DataBaseStrings.InsertEssay+"(\""+name+"\","+this.getNullString(2)+")");
		}catch (Exception e)
		{
			dataBaseError();
		}
	}
	public void insertChoice(String name,String type)
	{
		try
		{
			Statement state=link.createStatement();
			state.executeUpdate(DataBaseStrings.InsertChoice+
								    "(\""+name+"\",\""+type+"\","+this.getNullString(11)+")");
		}catch (Exception e)
		{
			dataBaseError();
		}
	}

//Delete Part
	public void deleteStudent(String id)
	{
		try
		{
			PreparedStatement state=link.prepareStatement(DataBaseStrings.DeleteStudent);
			state.setString(1,id);
			state.executeUpdate();
		}catch (Exception e)
		{
			dataBaseError();
		}
	}
	public void deleteEssay(String name)
	{
		try
		{
			PreparedStatement state=link.prepareStatement(DataBaseStrings.DeleteEssay);
			state.setString(1,name);
			state.executeUpdate();
		}catch (Exception e)
		{
			dataBaseError();
		}
	}
	public void deleteChoice(String name)
	{
		try
		{
			PreparedStatement state=link.prepareStatement(DataBaseStrings.DeleteChoice);
			state.setString(1,name);
			state.executeUpdate();
		}catch (Exception e)
		{
			dataBaseError();
		}
	}

//Update Part
	public void updateEssay(String name,String text,String answer)
	{
		try
		{
			PreparedStatement state= link.prepareStatement(DataBaseStrings.UpdateEssay);
			state.setString(3,name);
			state.setString(1,text);
			state.setString(2,answer);
			state.executeUpdate();
		}catch (Exception e)
		{
			dataBaseError();
		}
	}
	public void updateChoice(String name,String text,String opNumber,String answer,String[] options)
	{
		try
		{
			PreparedStatement state1=link.prepareStatement(DataBaseStrings.UpdateChoice);
			PreparedStatement state2=link.prepareStatement(DataBaseStrings.UpdateOption);

			state1.setString(4,name);
			state1.setString(1,text);
			state1.setString(2,opNumber);
			state1.setString(3,answer);

			state2.setString(9,name);
			for(int i=1;i<=8;i++)
			 if(i<=options.length)state2.setString(i,options[i-1]);
			  else state2.setString(i,"");

			state1.executeUpdate();
			state2.executeUpdate();
		}catch (Exception e)
		{
			dataBaseError();
		}
	}

//Search Part
	public String searchUser(String name)
	{
		String ret=null;
		try
		{
			PreparedStatement state=link.prepareStatement(DataBaseStrings.SearchUser);
			state.setString(1,name);

			ResultSet result=state.executeQuery();
			if(result!=null&&result.next())
			{
				ret=result.getString(1);
			}
		}catch (Exception e)
		{
			dataBaseError();
		}
		return ret;
	}
	public String[] searchStudent(String id)
	{
		String[] ret=new String[2];
		try
		{
			PreparedStatement state=link.prepareStatement(DataBaseStrings.SearchStudent);
			state.setString(1,id);

			ResultSet result=state.executeQuery();
			if(result!=null&&result.next())
			{
				ret[0]=result.getString(1);
				ret[1]=result.getString(2);
			}
		}catch (Exception e)
		{
			dataBaseError();
		}
		return  ret;
	}
	public String[] searchEssay(String name)
	{
		String[] ret=new String[3];
		try
		{
			PreparedStatement state=link.prepareStatement(DataBaseStrings.SearchEssay);
			state.setString(1,name);

			ResultSet result=state.executeQuery();
			if(result!=null&&result.next())
			{
				ret[0]=result.getString(1);
				ret[1]=result.getString(2);
				ret[2]=result.getString(3);
			}
		}catch (Exception e)
		{
			dataBaseError();
		}
		return  ret;
	}
	public String[] searchChoice(String name)
	{
		String[] ret=new String[1];
		int number;
		try
		{
			PreparedStatement state1=link.prepareStatement(DataBaseStrings.SearchChoice);
			state1.setString(1,name);
			ResultSet result1=state1.executeQuery();

			if(result1!=null&&result1.next())
			{
				String numberString=result1.getString(4);
				if(!numberString.equals(""))
					number=Integer.parseInt(numberString);
				 else number=0;
				ret=new String[5+number];
				ret[0]=result1.getString(1);
				ret[1]=result1.getString(2);
				ret[2]=result1.getString(3);
				ret[3]=result1.getString(4);
				ret[4]=result1.getString(5);

				if(number!=0)
				{
					PreparedStatement state2=link.prepareStatement(DataBaseStrings.SearchOption[number]);
					state2.setString(1,name);
					ResultSet result2=state2.executeQuery();
					if(result2!=null&&result2.next())
					{
						for(int i=5;i<5+number;i++)
							ret[i]=result2.getString(i-4);

					}else throw (new Exception());
				}
			}else throw(new Exception());
		}catch (Exception e)
		{
			dataBaseError();
		}
		return  ret;
	}

//Sub Search Part
	public boolean hasUser(String name)
	{
		try
		{
			PreparedStatement state =link.prepareStatement(DataBaseStrings.SearchUser);
			state.setString(1,name);
			ResultSet result= state.executeQuery();
			return result.next();
		}catch (Exception e)
		{
			dataBaseError();
		}
		return false;
	}
	public boolean hasStudent(String id)
	{
		try
		{
			PreparedStatement state =link.prepareStatement(DataBaseStrings.SearchStudent);
			state.setString(1,id);
			ResultSet result= state.executeQuery();
			return result.next();
		}catch (Exception e)
		{
			dataBaseError();
		}
		return false;
	}
	public boolean hasEssay(String name)
	{
		try
		{
			PreparedStatement state =link.prepareStatement(DataBaseStrings.SearchEssay);
			state.setString(1,name);
			ResultSet result= state.executeQuery();
			return result.next();
		}catch (Exception e)
		{
			dataBaseError();
		}
		return false;
	}
	public boolean hasChoice(String name)
	{
		try
		{
			PreparedStatement state =link.prepareStatement(DataBaseStrings.SearchChoice);
			state.setString(1,name);
			ResultSet result= state.executeQuery();
			return result.next();
		}catch (Exception e)
		{
			dataBaseError();
		}
		return false;
	}

//Return List of Name
	public String[] getStudent()
	{
		ArrayList<String> stringBin =new ArrayList<>();
		try
		{
			Statement state=link.createStatement();
			ResultSet result=state.executeQuery(DataBaseStrings.StudentList);
			while(result!=null&&result.next())
			{
				stringBin.add(result.getString(1));
			}
			if(stringBin.size()==0)
			{
				String[] ret=new String[1];
				ret[0]="";
				return ret;
			}
		}catch (Exception e)
		{
			dataBaseError();
		}

		String[] ret=new String[stringBin.size()];
		for(int i = 0; i< stringBin.size(); i++)
			ret[i]= stringBin.get(i);
		return ret;
	}
	public String[] getEssay()
	{
		ArrayList<String> stringBin =new ArrayList<>();
		try
		{
			Statement state=link.createStatement();
			ResultSet result=state.executeQuery(DataBaseStrings.EssayQuestionList);
			while(result!=null&&result.next())
			{
				stringBin.add(result.getString(1));
			}
			if(stringBin.size()==0)
			{
				String[] ret=new String[1];
				ret[0]="";
				return ret;
			}
		}catch (Exception e)
		{
			dataBaseError();
		}

		String[] ret=new String[stringBin.size()];
		for(int i = 0; i< stringBin.size(); i++)
			ret[i]= stringBin.get(i);
		return ret;
	}
	public String[] getChoice()
	{
		ArrayList<String> stringBin =new ArrayList<>();
		try
		{
			Statement state=link.createStatement();
			ResultSet result=state.executeQuery(DataBaseStrings.ChoiceQuestionList);
			while(result!=null&&result.next())
			{
				stringBin.add(result.getString(1));
			}
			if(stringBin.size()==0)
			{
				String[] ret=new String[1];
				ret[0]="";
				return ret;
			}
		}catch (Exception e)
		{
			dataBaseError();
		}

		String[] ret=new String[stringBin.size()];
		for(int i = 0; i< stringBin.size(); i++)
			ret[i]= stringBin.get(i);
		return ret;
	}

	private void dataBaseError()
	{
		JOptionPane.showMessageDialog
				(null, MessageStrings.DataBaseFailed,
						TitleStrings.DataBaseError,JOptionPane.ERROR_MESSAGE);
	}

	private String getNullString(int re)
	{
		String Null=",\"\"";
		String ret="\"\"";
		for(int i=1;i<re;i++)
			ret=ret+Null;
		return ret;
	}
}
