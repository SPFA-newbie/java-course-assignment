package FormPack.Forms;

import MiddlePack.BasicSend;
import DocumentPack.Questions.BasicQuestion;
import DocumentPack.Questions.ChoiceQuestion;
import DocumentPack.Questions.QuestionType;
import FormPack.Components.QuestionTable;
import FormPack.Components.TestTable;
import FormPack.Tools.FatherChildStruct;
import StringPack.*;

import javax.swing.*;
import java.awt.*;

public class QuestionInputForm extends JFrame
{
	private JPanel inputSet;
	private JLabel theLabel;
	private JTextField inputArea;
	private JButton acceptButton;

	public QuestionInputForm(String label)
	{
		super();

		inputSet=new JPanel(new FlowLayout());
		theLabel=new JLabel(label);
		inputArea=new JTextField(null);
		acceptButton=new JButton(ButtonNames.Accept);

		//Position of Form
		Dimension scan =Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(scan.width/5, scan.height/5);
		//Size of Form
		this.setSize(scan.width/5,scan.height/5);
		//Title of Form
		this.setTitle(TitleStrings.InputForm);
		//Choose Container
		this.setLayout(new GridLayout(2,1,5,5));
		//Other Property list of Form
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setVisible(true);

		//TextField Size
		inputArea.setColumns(20);

		//Add Components
		inputSet.add(theLabel);
		inputSet.add(inputArea);
		this.add(inputSet);
		this.add(acceptButton);
	}

	public void inputNewQuestion_Name(BasicSend sender,QuestionTable table)
	{
		this.acceptButton.addActionListener(event->
		{
			QuestionInputForm subForm=new QuestionInputForm(LabelStrings.InputNewQuestionType);
			subForm.inputNewQuestion_Type(sender,table,inputArea.getText());
			this.dispose();
		});
	}

	public void inputNewQuestion_Type(BasicSend sender,QuestionTable table,String name)
	{
		inputSet.removeAll();
		inputSet.setLayout(new FlowLayout());
		inputSet.updateUI();
		this.getContentPane().removeAll();
		this.setLayout(new GridLayout(2,1,5,5));

		JComboBox<String> questionType=new JComboBox<>();
		questionType.addItem(TypeNames.EssayQuestion);
		questionType.addItem(TypeNames.SingleChoice);
		questionType.addItem(TypeNames.MultipleChoice);
		questionType.setEditable(false);

		inputSet.add(theLabel);
		inputSet.add(questionType);
		this.add(inputSet);
		this.add(acceptButton);

		this.acceptButton.addActionListener(event->
		{
			BasicQuestion question=new BasicQuestion();
			question.setName(name);
			String theType=(String)questionType.getSelectedItem();
			if(theType==TypeNames.EssayQuestion)question.setType(QuestionType.Essay);else
			if(theType==TypeNames.MultipleChoice)question.setType(QuestionType.Multiple);else
			if(theType==TypeNames.SingleChoice)question.setType(QuestionType.Single);
			if(!sender.sendNewQuestion(question))
			{
				JOptionPane.showMessageDialog
						(null, MessageStrings.AddQuestionFailed,
								TitleStrings.AddingFailed,JOptionPane.ERROR_MESSAGE);
			}
			table.makeList(question.getType());
			this.dispose();
		});
	}

	public void inputNewOption
	 (ChoiceQuestion question, JPanel buttonArea, JPanel optionArea, FatherChildStruct struct, ButtonGroup optionGroup)
	{
		acceptButton.addActionListener(event->
		{
			optionArea.removeAll();
			if(inputArea.getText()!=null)
			{
				question.addOptions(inputArea.getText());
				this.dispose();
			}
			optionArea.setLayout(new GridLayout(question.getOptionNumber()+1,1));
			//Same as "Build RadioButton"
			int optionNumber= question.getOptionNumber();
			JRadioButton[] options=new JRadioButton[optionNumber];
			for(int i=0;i<optionNumber;i++)
			{
				options[i]=new JRadioButton(question.getChoice(i),question.isRight(i));
				optionGroup.add(options[i]);
				optionArea.add(options[i]);
			}
			//
			struct.setOptions(options);
			optionArea.add(buttonArea);
			optionArea.updateUI();
		}
		);
	}

	public void inputNewOption
	 (ChoiceQuestion question,JPanel buttonArea,JPanel optionArea,FatherChildStruct struct)
	{
		acceptButton.addActionListener(event->
				{
					optionArea.removeAll();
					if(inputArea.getText()!=null)
					{
						question.addOptions(inputArea.getText());
						this.dispose();
					}
					optionArea.setLayout(new GridLayout(question.getOptionNumber()+1,1));
					//Same as "Build CheckBox"
					int optionNumber= question.getOptionNumber();
					JCheckBox[] options=new JCheckBox[optionNumber];
					for(int i=0;i<optionNumber;i++)
					{
						options[i]=new JCheckBox(question.getChoice(i),question.isRight(i));
						optionArea.add(options[i]);
					}
					//
					struct.setMulOptions(options);
					optionArea.add(buttonArea);
					optionArea.updateUI();
				}
		);
	}

	public void inputPoint(TestTable table,String question)
	{
		acceptButton.addActionListener(event->
		{
			if(inputArea.getText()!=null)
			{
				table.addQuestion(inputArea.getText(),question);
				this.dispose();
			}
		});
	}
}
