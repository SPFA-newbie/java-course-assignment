package FormPack.Components;

import MiddlePack.BasicGet;
import MiddlePack.BasicSend;
import DocumentPack.Questions.EssayQuestion;
import DocumentPack.Tests.BackTestStruct;
import StringPack.ButtonNames;
import StringPack.LabelStrings;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Comparator;

public class CorrectionTable extends JPanel
{
	//Communication Resources
	private BasicGet Getter;
	private BasicSend Sender;
	//Components of Main
	private JTextArea questionText;
	private JTextArea rightAnswer;
	private JTextArea studentAnswer;
	private JScrollPane textBar;
	private JScrollPane rightBar;
	private JScrollPane studentBar;
	private JPanel mainArea;
	//Components of Left
	private JLabel textLabel;
	private JLabel rightLabel;
	private JLabel studentLabel;
	private JPanel leftArea;
	//Components of Right
	private JTextField point;
	private JButton givePoint;
	private JButton buildList;
	private JButton stopListen;
	private JPanel rightArea;
	//Link of Test Table
	private TestTable table;
	//Paper List
	private ArrayList<BackTestStruct> papers;
	//Correction Position
	private int paperPosition;
	private int questionPosition;

	public CorrectionTable()
	{
		super();
		//Choose Container
		this.setLayout(new BorderLayout(5,5));
		//Visible of Components;
		this.setVisible(true);
		//Position
		paperPosition=0;
		questionPosition=0;
		//Data
		papers=new ArrayList<>();
	}

	public void link(BasicGet getter,BasicSend sender,TestTable theTable)
	{
		Getter=getter;
		Sender=sender;
		table=theTable;
	}

	public void build()
	{
		//Main Area
		questionText=new JTextArea();
		rightAnswer=new JTextArea();
		studentAnswer=new JTextArea();
		textBar=new JScrollPane(questionText);
		rightBar=new JScrollPane(rightAnswer);
		studentBar=new JScrollPane(studentAnswer);
		mainArea=new JPanel(new GridLayout(3,1,5,5));
		questionText.setEditable(false);
		rightAnswer.setEditable(false);
		studentAnswer.setEditable(false);
		questionText.setLineWrap(true);
		rightAnswer.setLineWrap(true);
		studentAnswer.setLineWrap(true);

		mainArea.add(textBar);
		mainArea.add(rightBar);
		mainArea.add(studentBar);
		this.add(mainArea,BorderLayout.CENTER);
		//Left Area
		textLabel=new JLabel(LabelStrings.QuestionText);
		rightLabel=new JLabel(LabelStrings.QuestionAnswer);
		studentLabel=new JLabel(LabelStrings.StudentAnswer);
		leftArea=new JPanel(new GridLayout(3,1,5,5));

		leftArea.add(textLabel);
		leftArea.add(rightLabel);
		leftArea.add(studentLabel);
		this.add(leftArea,BorderLayout.WEST);
		//Right Area
		point=new JTextField(10);
		givePoint=new JButton(ButtonNames.GivePointToStudent);
		buildList=new JButton(ButtonNames.MakePointList);
		stopListen=new JButton(ButtonNames.StopWaitPaper);
		rightArea=new JPanel(new GridLayout(4,1,5,5));

		givePoint.setEnabled(false);
		buildList.setEnabled(false);
		stopListen.setEnabled(false);

		this.backToHead();
		this.loadQuestion(true);
		givePoint.addActionListener(event->
		{
			if(point.getText()!=null&&!point.getText().equals(""))
			{
				this.catchPoint();
				if(this.nextCorrection())
				{
					this.loadQuestion(false);
				}else
				{
					this.loadQuestion(true);
					givePoint.setEnabled(false);
					buildList.setEnabled(true);
				}
			}
		});
		buildList.addActionListener(event->
		{
			for(int i=0;i<papers.size();i++)
			{
				papers.get(i).takeChoicePoint(Getter);
				papers.get(i).takeEssayPoint();
			}
			papers.sort((a,b)->b.getFinalPoint()-a.getFinalPoint());
			Sender.setExamResult(papers);
			Sender.print(papers);
			buildList.setEnabled(false);
		});
		stopListen.addActionListener(event->
		{
			stopListen.setEnabled(false);
			givePoint.setEnabled(true);
			papers=Getter.getTestArray();

			this.backToHead();
			this.loadQuestion(false);
			table.reFlash();
		});

		rightArea.add(point);
		rightArea.add(givePoint);
		rightArea.add(buildList);
		rightArea.add(stopListen);
		this.add(rightArea,BorderLayout.EAST);
	}

	public void reFlash()
	{
		this.backToHead();
		this.loadQuestion(true);
		givePoint.setEnabled(false);
		buildList.setEnabled(false);
		stopListen.setEnabled(true);
	}

	public void loadQuestion(boolean empty)
	{
		if(empty)
		{
			questionText.setText("");
			rightAnswer.setText("");
			studentAnswer.setText("");
		}else
		{
			EssayQuestion question=(EssayQuestion)Getter.getQuestion
					(papers.get(paperPosition).getEssayQuestions(questionPosition).getQuestionName());
			questionText.setText(question.getQuestionText());
			rightAnswer.setText(question.getRightAnswer());
			studentAnswer.setText(papers.get(paperPosition).getEssayQuestions(questionPosition).getAnswer());
		}
	}
	public void backToHead()
	{
		paperPosition=0;
		questionPosition=0;
	}
	public boolean nextCorrection()
	{
		if(paperPosition==papers.size()-1)
		{
			paperPosition=0;
			if(questionPosition==papers.get(0).getEssayQuestionNumber()-1)
			{
				return false;
			}else questionPosition++;
		}else paperPosition++;
		return true;
	}

	private void catchPoint()
	{
		papers.get(paperPosition).getEssayQuestions(questionPosition).givePoint(Integer.parseInt(point.getText()));
		point.setText("");
	}
}
