package FormPack.Components;

import MiddlePack.BasicGet;
import MiddlePack.BasicSend;
import DocumentPack.Tests.TestPaper;
import StringPack.ButtonNames;
import StringPack.LabelStrings;
import StringPack.OperationStrings;

import javax.swing.*;
import java.awt.*;

public class TestTable extends JPanel
{
	//Communication Resources
	private BasicGet Getter;
	private BasicSend Sender;
	//The Test Paper
	private TestPaper paper;
	//The Selected Question
	private String questionName;
	//Components of Form
	private JList<String> questionList;
	private DefaultListModel<String> questionInList;
	private JScrollPane questionArea;
	private JPanel downArea;
	private JButton deleteQuestion;
	private JButton clearPaper;
	private JButton sendPaper;
	private JLabel timeLabel;
	private JTextField testTime;
	private JPanel timeArea;
	//Link to Correction
	private CorrectionTable table;

	public TestTable()
	{
		super();
		//Choose Container
		this.setLayout(new BorderLayout(5,5));
		//Visible of Components;
		this.setVisible(true);
	}

	public void link(BasicGet getter,BasicSend sender,CorrectionTable theTable)
	{
		Getter=getter;
		Sender=sender;
		table=theTable;
	}

	public void build()
	{
		paper=new TestPaper();
		questionName=null;
		//Build Main Area
		questionInList=new DefaultListModel<>();
		questionList=new JList<>(questionInList);
		questionArea=new JScrollPane(questionList);

		questionList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		questionList.setVisibleRowCount(32);
		questionList.setLayoutOrientation(JList.VERTICAL_WRAP);

		this.makeList();

		this.add(questionArea,BorderLayout.CENTER);

		//Field in Down Area
		timeLabel=new JLabel(LabelStrings.TestTime);
		testTime=new JTextField(OperationStrings.TestTime);
		testTime.setColumns(8);
		timeArea=new JPanel(new FlowLayout());
		timeArea.add(timeLabel);
		timeArea.add(testTime);

		//Build Button Area
		downArea=new JPanel(new FlowLayout());
		deleteQuestion=new JButton(ButtonNames.DeleteQuestion);
		clearPaper=new JButton(ButtonNames.clearPaper);
		sendPaper=new JButton(ButtonNames.SendPaperToStudent);

		deleteQuestion.addActionListener(event->
		{
			if(questionName!=null)
			{
				paper.deleteQuestion(questionName);
				makeList();
			}
		});
		clearPaper.addActionListener(event->
		{
			paper=new TestPaper();
			makeList();
		});
		sendPaper.addActionListener(event->
		{
			if(paper.getEssayQuestionNumber()+paper.getChoiceQuestionNumber()!=0)
			{
				paper.setTestTime(Integer.parseInt(testTime.getText()));
				Sender.sendPaper(paper,Getter.getAllStudent());
				sendPaper.setEnabled(false);
				table.reFlash();
			}
		});

		downArea.add(deleteQuestion);
		downArea.add(clearPaper);
		downArea.add(timeArea);
		downArea.add(sendPaper);

		this.add(downArea,BorderLayout.SOUTH);
	}

	public void makeList()
	{
		questionInList.removeAllElements();

		for(int i=0;i<paper.getChoiceQuestionNumber();i++)
			questionInList.addElement(paper.getChoiceQuestion(i).getQuestionName());
		for(int i=0;i<paper.getEssayQuestionNumber();i++)
			questionInList.addElement(paper.getEssayQuestion(i).getQuestionName());

		questionList.addListSelectionListener(event->
		{
			questionName=questionList.getSelectedValue();
		});
	}

	public void addQuestion(String point, String question)
	{
		paper.addQuestion(Getter.getQuestion(question),Integer.parseInt(point));
		makeList();
	}

	public TestPaper getTest(){return paper;}
	public void reFlash(){sendPaper.setEnabled(true);}
}
