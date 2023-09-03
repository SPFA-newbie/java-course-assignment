package FormPack.Components;

import DocumentPack.Questions.ChoiceQuestion;
import DocumentPack.Tests.BackChoiceQuestionStruct;
import DocumentPack.Tests.BackEssayQuestionStruct;
import DocumentPack.Tests.ChoiceQuestionStruct;
import DocumentPack.Tests.EssayQuestionStruct;
import MiddlePack.BasicGet;
import MiddlePack.BasicSend;
import DocumentPack.Questions.QuestionType;
import FormPack.Tools.FatherChildStruct;
import FormPack.Tools.QuestionPainter;
import StringPack.ButtonNames;
import StringPack.LabelStrings;

import javax.swing.*;
import java.awt.*;

public class ExamTable extends JPanel
{
	//Communication Resources
	private BasicGet Getter;
	private BasicSend Sender;
	//Question Data
	private ChoiceQuestionStruct choiceQuestion;
	private EssayQuestionStruct essayQuestion;
	//Clock
	Timer changeTime;
	int hasTime;//Second
	//Components of Main Part
	private FatherChildStruct mainAreaPack;
	//Components of left Area
	private JPanel leftArea;
	private DefaultListModel<String> questions;
	private JList<String> questionList;
	private JScrollPane questionArea;
	//Components of down Area
	private JPanel downArea;
	private JButton saveQuestion;
	private JButton stopExam;
	//Components of up Area
	private JLabel haveTime;
	private JPanel upArea;

	public ExamTable()//Setting basic property
	{
		super();
		//Choose Container
		this.setLayout(new BorderLayout(5,5));
		//Visible of Components
		this.setVisible(true);
		//Make a Null Question
		choiceQuestion=null;
		essayQuestion=null;
	}
	public void link(BasicGet getter, BasicSend sender)//Link resources
	{
		Getter=getter;
		Sender=sender;
	}
	public void build()
	{
		mainAreaPack=new FatherChildStruct(new JPanel(),new JTextArea(),new JTextArea());

		//Wait for Paper
		while(!Getter.hasPaper())
		{
			try
			{
				Thread.sleep(10);
			}catch (Exception e)
			{
				System.out.println("...");
			}
		}

		//Build List
		leftArea=new JPanel();
		leftArea.setLayout(new BorderLayout());
		questions=new DefaultListModel<>();
		questionList=new JList<>(questions);
		questionArea=new JScrollPane(questionList);

		makeList();
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
		saveQuestion=new JButton(ButtonNames.SaveQuestion);
		stopExam=new JButton(ButtonNames.StopExam);

		saveQuestion.addActionListener(event->
		{
			if(choiceQuestion!=null)
			{
				BackChoiceQuestionStruct ret=new BackChoiceQuestionStruct();
				ret.setQuestionName(choiceQuestion.getQuestionName());
				ret.setFormStudent(Getter.whoAmI());
				ret.setPoint(choiceQuestion.getQuestionPoint());

				int answer=1;
				if(choiceQuestion.getQuestionType()==QuestionType.Multiple)
				{
					for(int i=0;i<mainAreaPack.mulOptions.length;i++)
						if(mainAreaPack.mulOptions[i].isSelected())
							answer*=ChoiceQuestion.answerTag[i];
				}else
				{
					for(int i=0;i<mainAreaPack.options.length;i++)
						if(mainAreaPack.options[i].isSelected())
							answer= ChoiceQuestion.answerTag[i];
				}
				ret.setAnswer(answer);

				Sender.updatePaper(ret);
			}else if(essayQuestion!=null)
			{
				BackEssayQuestionStruct ret=new BackEssayQuestionStruct();
				ret.setQuestionName(essayQuestion.getQuestionName());
				ret.setFormStudent(Getter.whoAmI());
				ret.setPoint(essayQuestion.getQuestionPoint());
				ret.setAnswer(mainAreaPack.answerArea.getText());

				Sender.updatePaper(ret);
			}
		});
		stopExam.addActionListener(event->
		{
			Sender.ready();
			this.removeAll();
			this.updateUI();
		});

		//Build UpArea
		haveTime=new JLabel(LabelStrings.HaveTime);
		upArea=new JPanel(new FlowLayout(FlowLayout.CENTER));
		haveTime.setFont(new Font(haveTime.getFont().getName(),Font.PLAIN,20));
		this.setClock();

		//Main Build
		flash();
	}

	public void makeList()
	{
		questions.removeAllElements();
		String[] names=Getter.getQuestionList();
		for(int i=0;i<names.length;i++)
			questions.addElement(names[i]);
	}
	private void makeMainArea(String name)
	{
		choiceQuestion=Getter.getChoiceQuestion(name);
		essayQuestion=Getter.getEssayQuestion(name);
		if(choiceQuestion!=null)
		{
			mainAreaPack=QuestionPainter.paint(choiceQuestion,Getter);
		}else if(essayQuestion!=null)
		{
			mainAreaPack=QuestionPainter.paint(essayQuestion,Getter);
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
		upArea.removeAll();

		leftArea.add(questionArea,BorderLayout.CENTER);
		downArea.add(saveQuestion);
		downArea.add(stopExam);
		upArea.add(haveTime);
		leftArea.updateUI();
		downArea.updateUI();
		upArea.updateUI();

		this.add(leftArea,BorderLayout.WEST);
		this.add(downArea,BorderLayout.SOUTH);
		this.add(upArea,BorderLayout.NORTH);
		this.add(mainAreaPack.mainPanel,BorderLayout.CENTER);
		this.updateUI();
	}
	private void setClock()
	{
		hasTime=Integer.parseInt(Getter.getTime())*60;
		changeTime=new Timer(1000,event->
		{
			if(hasTime>0)
			{
				hasTime--;
				String minute=Integer.toString(hasTime/60);
				String second=Integer.toString(hasTime%60);
				haveTime.setText(LabelStrings.HaveTime+minute+" : "+second);
				changeTime.start();
			}else
			{
				Sender.ready();
				this.removeAll();
				this.updateUI();
			}
		});
		changeTime.start();
	}
}
