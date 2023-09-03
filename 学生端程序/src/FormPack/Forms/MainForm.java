package FormPack.Forms;

import MiddlePack.BasicGet;
import MiddlePack.BasicSend;
import FormPack.Components.*;
import StringPack.ButtonNames;
import StringPack.CardNames;
import StringPack.MessageStrings;
import StringPack.TitleStrings;

import javax.swing.*;
import java.awt.*;

public class MainForm extends JFrame
{
	//Communication Resources
	BasicGet Getter;
	BasicSend Sender;
	//Components of Form
	private JPanel buttonSet;
	private JPanel mainSet;
	private JLabel welcome;
	private ExamTable exam;
	//Components of ButtonSet
	private JButton preparedExam;
	private JButton checkPoint;

	public MainForm()//Setting some property of form
	{
		super();
		//Position of Form
		Dimension scan =Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(scan.width/10, scan.height/10);
		//Size of Form
		this.setSize(scan.width/10*8,scan.height/10*8);
		//Title of Form
		this.setTitle(TitleStrings.MainForm);
		//Choose Container
		this.setLayout(new BorderLayout());
		//Other Property list of Form
		this.setResizable(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
	}

	public void link(BasicGet getter, BasicSend sender)//Link resources the form use
	{
		Getter=getter;
		Sender=sender;
		//About user
	}

	public void build()
	{
		//Build preparedExam Button
		preparedExam=new JButton(ButtonNames.PreparedExam);
		preparedExam.addActionListener(event->
		{
			exam.link(Getter,Sender);
			Getter.setTest();
			exam.build();
			preparedExam.setEnabled(false);
			((CardLayout)mainSet.getLayout()).show(mainSet,CardNames.ExamArea);
		});
		//Build checkPoint Button
		checkPoint=new JButton(ButtonNames.CheckPoint);
		checkPoint.addActionListener(event->
		{
			PointForm myPoint=new PointForm();
			myPoint.link(Getter);
			myPoint.build();
		});

		//Build Button Set
		buttonSet =new JPanel();
		buttonSet.setLayout(new GridLayout(1,5,10,10));
		buttonSet.add(preparedExam);
		buttonSet.add(checkPoint);
		//Build Welcome Label
		welcome=new JLabel(MessageStrings.Welcome);
		welcome.setFont(new Font(welcome.getFont().getName(),Font.PLAIN,30));
		//Take the Exam Set
		exam=new ExamTable();
		//Build Main Set
		mainSet=new JPanel();
		mainSet.setLayout(new CardLayout());
		mainSet.add(welcome);
		mainSet.add(exam, CardNames.ExamArea);
		//Add Components to Form
		this.add(buttonSet,BorderLayout.NORTH);
		this.add(mainSet,BorderLayout.CENTER);
	}
}
