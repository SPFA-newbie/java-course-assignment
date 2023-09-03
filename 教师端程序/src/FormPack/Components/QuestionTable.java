package FormPack.Components;

import MiddlePack.BasicGet;
import MiddlePack.BasicSend;
import DocumentPack.Questions.BasicQuestion;
import DocumentPack.Questions.ChoiceQuestion;
import DocumentPack.Questions.EssayQuestion;
import DocumentPack.Questions.QuestionType;
import FormPack.Forms.QuestionInputForm;
import FormPack.Tools.FatherChildStruct;
import FormPack.Tools.QuestionPainter;
import StringPack.ButtonNames;
import StringPack.LabelStrings;
import StringPack.TypeNames;

import javax.swing.*;
import java.awt.*;

public class QuestionTable extends JPanel
{
	//Communication Resources
	private BasicGet Getter;
	private BasicSend Sender;
	//Question Data
	private QuestionType theType;
	//Link to TestTable
	private TestTable test;
	//Components of Main Part
	private BasicQuestion theQuestion;
	private FatherChildStruct mainAreaPack;
	//Components of left Area
	private JPanel leftArea;
	private JComboBox<String> questionType;
	private DefaultListModel<String> questions;
	private JList<String> questionList;
	private JScrollPane questionArea;
	//Components of down Area
	private JPanel downArea;
	private JButton addQuestion;
	private JButton deleteQuestion;
	private JButton saveQuestion;
	private JButton toTest;

	public QuestionTable()//Setting basic property
	{
		super();
		//Choose Container
		this.setLayout(new BorderLayout(5,5));
		//Visible of Components
		this.setVisible(true);
		//Make a Null Test table
		test=null;
	}
	public void link(BasicGet getter, BasicSend sender,TestTable table)//Link resources
	{
		Getter=getter;
		Sender=sender;
		test=table;
	}
	public void build()
	{
		mainAreaPack=new FatherChildStruct(new JPanel(),new JTextArea(),new JTextArea());

		//Build Type Filter and List
		leftArea=new JPanel();
		leftArea.setLayout(new BorderLayout());
		questionType=new JComboBox<>();
		questions=new DefaultListModel<>();
		questionList=new JList<>(questions);
		questionArea=new JScrollPane(questionList);

		questionType.addItem(TypeNames.AllQuestion);
		questionType.addItem(TypeNames.EssayQuestion);
		questionType.addItem(TypeNames.SingleChoice);
		questionType.addItem(TypeNames.MultipleChoice);
		questionType.setEditable(false);
		makeList(QuestionType.All);
		questionType.addActionListener(event->
		{
			String theType=(String)questionType.getSelectedItem();
			if(theType==TypeNames.AllQuestion)makeList(QuestionType.All);else
			if(theType==TypeNames.EssayQuestion)makeList(QuestionType.Essay);else
			if(theType==TypeNames.MultipleChoice)makeList(QuestionType.Multiple);else
			if(theType==TypeNames.SingleChoice)makeList(QuestionType.Single);
		});
		questionList.setVisibleRowCount(32);
		questionList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		questionList.addListSelectionListener(event->
		{
			if(questionList.getSelectedValue()!=null)
			{
				makeMainArea(questionList.getSelectedValue());
			}
		});

		//Build Button Class
		downArea =new JPanel();
		downArea.setLayout(new FlowLayout());
		addQuestion=new JButton(ButtonNames.AddQuestion);
		deleteQuestion=new JButton(ButtonNames.DeleteQuestion);
		saveQuestion=new JButton(ButtonNames.SaveQuestion);
		toTest=new JButton(ButtonNames.AddToTest);

		addQuestion.addActionListener(event->
		{
			QuestionInputForm inputForm=new QuestionInputForm(LabelStrings.InputNewQuestionName);
			inputForm.inputNewQuestion_Name(Sender,this);
		});
		deleteQuestion.addActionListener(event->
		{
			if(theQuestion!=null)
			{
				Sender.deleteQuestion(theQuestion);
				makeList(theType);
				theQuestion=null;
				flash();
			}
		});
		saveQuestion.addActionListener(event->
		{
			if(theQuestion!=null)
			{
				theQuestion.setQuestionText(mainAreaPack.textArea.getText());
				if(theQuestion.getType()==QuestionType.Essay)
				{
					((EssayQuestion)theQuestion).setRightAnswer(mainAreaPack.answerArea.getText());
				}else
				{
					((ChoiceQuestion)theQuestion).clearOptions();
					((ChoiceQuestion)theQuestion).clearRightAnswer();
					if(theQuestion.getType()==QuestionType.Single)
					{
						for(int i=0;i<mainAreaPack.options.length;i++)
						{
							((ChoiceQuestion)theQuestion).addOptions(mainAreaPack.options[i].getText());
							if(mainAreaPack.options[i].isSelected())
								((ChoiceQuestion)theQuestion).addRightAnswer(i);
						}
					}else
					{
						for(int i=0;i<mainAreaPack.mulOptions.length;i++)
						{
							((ChoiceQuestion)theQuestion).addOptions(mainAreaPack.mulOptions[i].getText());
							if(mainAreaPack.mulOptions[i].isSelected())
								((ChoiceQuestion)theQuestion).addRightAnswer(i);
						}
					}
				}
				Sender.sendQuestion(theQuestion);
			}
		});
		toTest.addActionListener(event->
		{
			if(catchQuestion()!=null&&test!=null&&test.getTest()!=null)
			{
				QuestionInputForm inputForm=new QuestionInputForm(LabelStrings.QuestionPoint);
				inputForm.inputPoint(test,catchQuestion());
			}
		});

		//Main Build
		flash();

	}

	public void makeList(QuestionType type)
	{
		theType=type;
		questions.removeAllElements();
		String[] names=Getter.getQuestionList(type);
		for(int i=0;i<names.length;i++)
			questions.addElement(names[i]);
	}
	private void makeMainArea(String name)
	{
		theQuestion=Getter.getQuestion(name);
		if(theQuestion!=null)
		{
			mainAreaPack=QuestionPainter.paint(theQuestion);
		}else
		{
			mainAreaPack=new FatherChildStruct(new JPanel(),new JTextArea(),new JTextArea());
		}
		flash();
	}
	public void flash()
	{
		this.removeAll();
		leftArea.removeAll();
		downArea.removeAll();

		leftArea.add(questionType,BorderLayout.NORTH);
		leftArea.add(questionArea,BorderLayout.CENTER);
		downArea.add(addQuestion);
		downArea.add(deleteQuestion);
		downArea.add(saveQuestion);
		downArea.add(toTest);
		leftArea.updateUI();
		downArea.updateUI();

		this.add(leftArea,BorderLayout.WEST);
		this.add(downArea,BorderLayout.SOUTH);
		this.add(mainAreaPack.mainPanel,BorderLayout.CENTER);
		this.updateUI();
	}

	public String catchQuestion()
	{
		if(questionList.getSelectedValue()!=null)
			this.makeMainArea(questionList.getSelectedValue());
		return questionList.getSelectedValue();
	}
}
