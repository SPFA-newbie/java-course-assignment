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
	private TestTable test;
	private MemberTable member;
	private SettingTable setting;
	private QuestionTable question;
	private CorrectionTable correction;
	//Components of ButtonSet
	private JButton toTest;
	private JButton toMember;
	private JButton toSetting;
	private JButton toQuestion;
	private JButton toCorrection;

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
		//Build toTest Button
		toTest=new JButton(ButtonNames.OpenTest);
		toTest.addActionListener(event->
		{
			((CardLayout)mainSet.getLayout()).show(mainSet,CardNames.TestArea);
		});
		//Build toMember Button
		toMember=new JButton(ButtonNames.OpenGroupDB);
		toMember.addActionListener(event->
		{
			((CardLayout)mainSet.getLayout()).show(mainSet,CardNames.MemberArea);
		});
		//Build toQuestion Button
		toQuestion=new JButton(ButtonNames.OpenQuestionDB);
		toQuestion.addActionListener(event->
		{
			((CardLayout)mainSet.getLayout()).show(mainSet,CardNames.QuestionArea);
		});
		//Build toCorrection Button
		toCorrection=new JButton(ButtonNames.OpenCorrection);
		toCorrection.addActionListener(event->
		{
			((CardLayout)mainSet.getLayout()).show(mainSet,CardNames.CorrectionArea);
		});
		//Build toSetting Button
		toSetting =new JButton(ButtonNames.OpenSetting);
		toSetting.addActionListener(event->
		{
			EventQueue.invokeLater(()->
			{
				((CardLayout)mainSet.getLayout()).show(mainSet,CardNames.SettingArea);
			});
		});
		//Build Button Set
		buttonSet =new JPanel();
		buttonSet.setLayout(new GridLayout(1,5,10,10));
		buttonSet.add(toTest);
		buttonSet.add(toMember);
		buttonSet.add(toQuestion);
		buttonSet.add(toCorrection);
//		buttonSet.add(toSetting);
		//Build Welcome Label
		welcome=new JLabel(MessageStrings.Welcome);
		welcome.setFont(new Font(welcome.getFont().getName(),Font.PLAIN,30));
		//Build Other Set In Main Set
		test=new TestTable();
		member=new MemberTable();
		setting=new SettingTable();
		question=new QuestionTable();
		correction=new CorrectionTable();

		question.link(Getter,Sender,test);
		question.build();
		member.link(Getter,Sender);
		member.build();
		test.link(Getter,Sender,correction);
		test.build();
		correction.link(Getter,Sender,test);
		correction.build();
		//Build Main Set
		mainSet=new JPanel();
		mainSet.setLayout(new CardLayout());
		mainSet.add(welcome);
		mainSet.add(test, CardNames.TestArea);
		mainSet.add(member,CardNames.MemberArea);
		mainSet.add(setting,CardNames.SettingArea);
		mainSet.add(question,CardNames.QuestionArea);
		mainSet.add(correction,CardNames.CorrectionArea);
		//Add Components to Form
		this.add(buttonSet,BorderLayout.NORTH);
		this.add(mainSet,BorderLayout.CENTER);
	}
}
