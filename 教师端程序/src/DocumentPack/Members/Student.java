package DocumentPack.Members;

import java.io.Serializable;

public class Student implements Serializable
{
	private String id;
	private String name;
	private String password;

	public Student(String theId,String theName,String thePassword)
	{
		id=theId;
		name=theName;
		password = thePassword;
	}

	public boolean equals(Student s)
	{
		if(
				this.id.equals(s.getId())&&
				this.name.equals(s.getName())&&
				this.password.equals(s.getPassword())
		  )return true;
		return false;
	}

	public String getId(){return id;}
	public String getName(){return name;}
	public String getPassword(){return password;}
}
