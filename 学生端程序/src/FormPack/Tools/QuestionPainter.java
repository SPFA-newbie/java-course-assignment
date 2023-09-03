package FormPack.Tools;

import DocumentPack.Questions.ChoiceQuestion;
import DocumentPack.Questions.QuestionType;
import DocumentPack.Tests.ChoiceQuestionStruct;
import DocumentPack.Tests.EssayQuestionStruct;
import MiddlePack.BasicGet;
import StringPack.LabelStrings;

import javax.swing.*;
import java.awt.*;

public class QuestionPainter
{
	private static final int columns =90;
	private static final int rows =16;

	public static FatherChildStruct paint(EssayQuestionStruct question, BasicGet getter)
	{
		return paintEssay(question,getter);
	}
	public static FatherChildStruct paint(ChoiceQuestionStruct question, BasicGet getter)
	{
		if(question.getQuestionType()==QuestionType.Multiple)return paintMultiple(question,getter);
		 else return paintSingle(question,getter);
	}

	private static FatherChildStruct paintEssay(EssayQuestionStruct question, BasicGet getter)
	{
		JPanel mainPanel =new JPanel(new GridLayout(2,1));
//		GridBagLayout layout=new GridBagLayout();
//		GridBagConstraints GBC=new GridBagConstraints();
//		mainPanel.setLayout(layout);
//		GBC.fill=GridBagConstraints.BOTH;
//		GBC.weightx=GBC.weighty=100;

		JPanel upArea=new JPanel(new BorderLayout());
		JPanel downArea=new JPanel(new FlowLayout(FlowLayout.LEFT));
		JPanel subUpArea=new JPanel(new FlowLayout(FlowLayout.LEFT));
		JPanel subDownArea=new JPanel(new FlowLayout(FlowLayout.LEFT));

		JLabel nameLabel=new JLabel(LabelStrings.QuestionName);
		JLabel textLabel=new JLabel(LabelStrings.QuestionText);
		JLabel answerLabel=new JLabel(LabelStrings.QuestionAnswer);

		JTextField nameArea=new JTextField(question.getQuestionName(), columns);
		JTextArea theTextArea=new JTextArea(question.getTheQuestion(), rows, columns);
		JTextArea theAnswerArea=new JTextArea(getter.getEssayAnswer(question.getQuestionName()), rows, columns);
		JScrollPane textArea=new JScrollPane(theTextArea);
		JScrollPane answerArea=new JScrollPane(theAnswerArea);
		nameArea.setEditable(false);
		theTextArea.setEditable(false);
		theTextArea.setLineWrap(true);
		theAnswerArea.setLineWrap(true);

		subUpArea.add(nameLabel);
		subUpArea.add(nameArea);
		subDownArea.add(textLabel);
		subDownArea.add(textArea);
		upArea.add(subUpArea,BorderLayout.NORTH);
		upArea.add(subDownArea,BorderLayout.CENTER);
		downArea.add(answerLabel);
		downArea.add(answerArea);
		mainPanel.add(upArea);
		mainPanel.add(downArea);

		return new FatherChildStruct(mainPanel,theTextArea,theAnswerArea);
/*
		GBC.gridx=GBC.gridy=0;
		GBC.gridwidth=GBC.gridheight=1;
		layout.setConstraints(nameLabel,GBC);
		mainPanel.add(nameLabel);
		GBC.gridx=1;
		GBC.gridwidth=9;
		layout.setConstraints(nameArea,GBC);
		mainPanel.add(nameArea);
		GBC.gridy=1;
		GBC.gridwidth=1;
		layout.setConstraints(textLabel,GBC);
		mainPanel.add(textLabel);
		GBC.gridy=2;
		GBC.gridwidth=10;GBC.gridheight=9;
		layout.setConstraints(textArea,GBC);
		mainPanel.add(textArea);
		GBC.gridy=11;
		GBC.gridwidth=GBC.gridheight=1;
		layout.setConstraints(answerLabel,GBC);
		mainPanel.add(answerLabel);
		GBC.gridy=12;
		GBC.gridwidth=10;GBC.gridheight=9;
		layout.setConstraints(answerArea,GBC);
		mainPanel.add(answerArea);
*/
	}

	private static FatherChildStruct paintSingle(ChoiceQuestionStruct question, BasicGet getter)
	{
		JPanel mainPanel =new JPanel(new GridLayout(2,1));
//		GridBagLayout layout=new GridBagLayout();
//		GridBagConstraints GBC=new GridBagConstraints();
//		mainPanel.setLayout(layout);
//		GBC.fill=GridBagConstraints.BOTH;
//		GBC.weightx=GBC.weighty=100;

		JPanel upArea=new JPanel(new BorderLayout());
		JPanel downArea=new JPanel(new FlowLayout(FlowLayout.LEFT));
		JPanel subUpArea=new JPanel(new FlowLayout(FlowLayout.LEFT));
		JPanel subDownArea=new JPanel(new FlowLayout(FlowLayout.LEFT));

		JLabel nameLabel=new JLabel(LabelStrings.QuestionName);
		JLabel textLabel=new JLabel(LabelStrings.QuestionText);
		JLabel choiceLabel=new JLabel(LabelStrings.QuestionChoice);

		JTextField nameArea=new JTextField(question.getQuestionName(), columns);
		JTextArea theTextArea=new JTextArea(question.getTheQuestion(), rows, columns);
		JScrollPane textArea=new JScrollPane(theTextArea);
		JPanel choiceArea=new JPanel(new GridLayout(question.getOptionNumber(),1));
		ButtonGroup choiceGroup=new ButtonGroup();


		nameArea.setEditable(false);
		theTextArea.setEditable(false);
		theTextArea.setLineWrap(true);

		FatherChildStruct retStruct=new FatherChildStruct(mainPanel,theTextArea,QuestionType.Single);

		//Build RadioButton
		int optionNumber= question.getOptionNumber();
		JRadioButton[] options=new JRadioButton[optionNumber];
		int nowAnswer=getter.getChoiceAnswer(question.getQuestionName());
		for(int i=0;i<optionNumber;i++)
		{
			boolean chooseIt=false;
			if(nowAnswer%ChoiceQuestion.answerTag[i]==0)chooseIt=true;
			options[i]=new JRadioButton(question.getTheOptions()[i],chooseIt);
			choiceGroup.add(options[i]);
			choiceArea.add(options[i]);
		}
		retStruct.setOptions(options);

		//Build Panel Area
		subUpArea.add(nameLabel);
		subUpArea.add(nameArea);
		subDownArea.add(textLabel);
		subDownArea.add(textArea);
		upArea.add(subUpArea,BorderLayout.NORTH);
		upArea.add(subDownArea,BorderLayout.CENTER);
		downArea.add(choiceLabel);
		downArea.add(choiceArea);
		mainPanel.add(upArea);
		mainPanel.add(downArea);

		return retStruct;

	}

	private static FatherChildStruct paintMultiple(ChoiceQuestionStruct question, BasicGet getter)
	{
		JPanel mainPanel =new JPanel(new GridLayout(2,1));
//		GridBagLayout layout=new GridBagLayout();
//		GridBagConstraints GBC=new GridBagConstraints();
//		mainPanel.setLayout(layout);
//		GBC.fill=GridBagConstraints.BOTH;
//		GBC.weightx=GBC.weighty=100;

		JPanel upArea=new JPanel(new BorderLayout());
		JPanel downArea=new JPanel(new FlowLayout(FlowLayout.LEFT));
		JPanel subUpArea=new JPanel(new FlowLayout(FlowLayout.LEFT));
		JPanel subDownArea=new JPanel(new FlowLayout(FlowLayout.LEFT));

		JLabel nameLabel=new JLabel(LabelStrings.QuestionName);
		JLabel textLabel=new JLabel(LabelStrings.QuestionText);
		JLabel choiceLabel=new JLabel(LabelStrings.QuestionChoice);

		JTextField nameArea=new JTextField(question.getQuestionName(), columns);
		JTextArea theTextArea=new JTextArea(question.getTheQuestion(), rows, columns);
		JScrollPane textArea=new JScrollPane(theTextArea);
		JPanel choiceArea=new JPanel(new GridLayout(question.getOptionNumber(),1));


		nameArea.setEditable(false);
		theTextArea.setEditable(false);
		theTextArea.setLineWrap(true);

		FatherChildStruct retStruct=new FatherChildStruct(mainPanel,theTextArea,QuestionType.Multiple);

		//Build CheckBox
		int optionNumber= question.getOptionNumber();
		JCheckBox[] options=new JCheckBox[optionNumber];
		int nowAnswer=getter.getChoiceAnswer(question.getQuestionName());
		for(int i=0;i<optionNumber;i++)
		{
			boolean chooseIt=false;
			if(nowAnswer%ChoiceQuestion.answerTag[i]==0)chooseIt=true;
			options[i]=new JCheckBox(question.getTheOptions()[i],chooseIt);
			choiceArea.add(options[i]);
		}
		retStruct.setMulOptions(options);

		//Build Panel Area
		subUpArea.add(nameLabel);
		subUpArea.add(nameArea);
		subDownArea.add(textLabel);
		subDownArea.add(textArea);
		upArea.add(subUpArea,BorderLayout.NORTH);
		upArea.add(subDownArea,BorderLayout.CENTER);
		downArea.add(choiceLabel);
		downArea.add(choiceArea);
		mainPanel.add(upArea);
		mainPanel.add(downArea);

		return retStruct;
	}

}
