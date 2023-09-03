package FormPack.Components;

import MiddlePack.BasicGet;
import MiddlePack.BasicSend;
import DocumentPack.Members.Student;
import FormPack.Forms.StudentInputForm;
import StringPack.ButtonNames;

import javax.swing.*;
import java.awt.*;

public class MemberTable extends JPanel
{
	//Communication Resources
	private BasicGet Getter;
	private BasicSend Sender;
	//Components of Form
	private DefaultListModel<String> studentInList;
	private JList<String> studentList;
	private JScrollPane studentArea;
	private JPanel downArea;
	private JButton addStudent;
	private JButton deleteStudent;
	//The Selected Student
	Student student;

	public MemberTable()//Setting basic property
	{
		super();
		//Choose Container
		this.setLayout(new BorderLayout(5,5));
		//Visible of Components;
		this.setVisible(true);
	}

	public void link(BasicGet getter,BasicSend send)
	{
		Getter=getter;
		Sender=send;
	}

	public void build()
	{
		student=null;
		//Build main Area
		studentInList=new DefaultListModel<>();
		studentList=new JList<>(studentInList);
		studentArea=new JScrollPane(studentList);

		studentList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		studentList.setVisibleRowCount(32);
		studentList.setLayoutOrientation(JList.VERTICAL_WRAP);

		this.makeList();

		this.add(studentArea,BorderLayout.CENTER);

		//Build button Area
		addStudent=new JButton(ButtonNames.AddStudent);
		deleteStudent=new JButton(ButtonNames.DeleteStudent);
		downArea=new JPanel(new FlowLayout());
		addStudent.addActionListener(event->
		{
			StudentInputForm inputForm=new StudentInputForm();
			inputForm.NewStudent(this,Getter,Sender);
		});
		deleteStudent.addActionListener(event->
		{
			if(student!=null)
			{
				Sender.deleteStudent(student.getId());
				this.makeList();
			}
		});

		downArea.add(addStudent);
		downArea.add(deleteStudent);
		this.add(downArea,BorderLayout.SOUTH);
	}

	public void makeList()
	{
		studentInList.removeAllElements();
		String[] theList=Getter.getStudentList();
		for(int i=0;i<theList.length;i++)
			studentInList.addElement(theList[i]);

		studentList.addListSelectionListener(event->
		{
			student=Getter.getStudent(studentList.getSelectedValue());
		});
	}
}
