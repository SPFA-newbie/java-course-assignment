package FormPack.Tools;

import DocumentPack.Questions.BasicQuestion;
import DocumentPack.Questions.ChoiceQuestion;
import DocumentPack.Questions.EssayQuestion;
import DocumentPack.Questions.QuestionType;
import FormPack.Forms.QuestionInputForm;
import StringPack.ButtonNames;
import StringPack.LabelStrings;

import javax.swing.*;
import java.awt.*;

public class QuestionPainter
{
	private static final int columns =86;
	private static final int rows =16;

	public static FatherChildStruct paint(BasicQuestion question)
	{
		if(question.getType() == QuestionType.Essay)return paintEssay((EssayQuestion) question);else
		if(question.getType() == QuestionType.Single)return paintSingle((ChoiceQuestion)question);else
		if(question.getType() == QuestionType.Multiple)return paintMultiple((ChoiceQuestion)question);
		return null;
	}

	private static FatherChildStruct paintEssay(EssayQuestion question)
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

		JTextField nameArea=new JTextField(question.getName(), columns);
		JTextArea theTextArea=new JTextArea(question.getQuestionText(), rows, columns);
		JTextArea theAnswerArea=new JTextArea(question.getRightAnswer(), rows, columns);
		JScrollPane textArea=new JScrollPane(theTextArea);
		JScrollPane answerArea=new JScrollPane(theAnswerArea);
		nameArea.setEditable(false);
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

	private static FatherChildStruct paintSingle(ChoiceQuestion question)
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

		JTextField nameArea=new JTextField(question.getName(), columns);
		JTextArea theTextArea=new JTextArea(question.getQuestionText(), rows, columns);
		JScrollPane textArea=new JScrollPane(theTextArea);
		JPanel choiceArea=new JPanel(new GridLayout(question.getOptionNumber()+1,1));
		ButtonGroup choiceGroup=new ButtonGroup();

		JPanel buttonArea=new JPanel(new FlowLayout());
		JButton addChoice=new JButton(ButtonNames.AddOption);
		JButton deleteChoice=new JButton(ButtonNames.DeleteOption);

		nameArea.setEditable(false);
		theTextArea.setLineWrap(true);

		FatherChildStruct retStruct=new FatherChildStruct(mainPanel,theTextArea,QuestionType.Single);

		//Build Choice Button
		addChoice.addActionListener(event->
		{
			QuestionInputForm inputForm=new QuestionInputForm(LabelStrings.InputOption);
			inputForm.inputNewOption(question,buttonArea,choiceArea,retStruct,choiceGroup);
		});
		deleteChoice.addActionListener(event->
		{
			question.removeOption();
			choiceArea.removeAll();
			choiceArea.setLayout(new GridLayout(question.getOptionNumber()+1, 1));
			//Same as "Build RadioButton"
			int optionNumber= question.getOptionNumber();
			JRadioButton[] options=new JRadioButton[optionNumber];
			for(int i=0;i<optionNumber;i++)
			{
				options[i]=new JRadioButton(question.getChoice(i),question.isRight(i));
				choiceGroup.add(options[i]);
				choiceArea.add(options[i]);
			}
			//
			retStruct.setOptions(options);
			choiceArea.add(buttonArea);
			choiceArea.updateUI();
		});
		buttonArea.add(addChoice);
		buttonArea.add(deleteChoice);

		//Build RadioButton
		int optionNumber= question.getOptionNumber();
		JRadioButton[] options=new JRadioButton[optionNumber];
		for(int i=0;i<optionNumber;i++)
		{
			options[i]=new JRadioButton(question.getChoice(i),question.isRight(i));
			choiceGroup.add(options[i]);
			choiceArea.add(options[i]);
		}
		retStruct.setOptions(options);
		//Add Button to ChoiceArea
		choiceArea.add(buttonArea);

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

	private static FatherChildStruct paintMultiple(ChoiceQuestion question)
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

		JTextField nameArea=new JTextField(question.getName(), columns);
		JTextArea theTextArea=new JTextArea(question.getQuestionText(), rows, columns);
		JScrollPane textArea=new JScrollPane(theTextArea);
		JPanel choiceArea=new JPanel(new GridLayout(question.getOptionNumber()+1,1));

		JPanel buttonArea=new JPanel(new FlowLayout());
		JButton addChoice=new JButton(ButtonNames.AddOption);
		JButton deleteChoice=new JButton(ButtonNames.DeleteOption);

		nameArea.setEditable(false);
		theTextArea.setLineWrap(true);

		FatherChildStruct retStruct=new FatherChildStruct(mainPanel,theTextArea,QuestionType.Multiple);

		//Build Choice Button
		addChoice.addActionListener(event->
		{
			QuestionInputForm inputForm=new QuestionInputForm(LabelStrings.InputOption);
			inputForm.inputNewOption(question,buttonArea,choiceArea,retStruct);
		});
		deleteChoice.addActionListener(event->
		{
			question.removeOption();
			choiceArea.removeAll();
			choiceArea.setLayout(new GridLayout(question.getOptionNumber()+1, 1));
			//Same as "Build CheckBox"
			int optionNumber= question.getOptionNumber();
			JCheckBox[] options=new JCheckBox[optionNumber];
			for(int i=0;i<optionNumber;i++)
			{
				options[i]=new JCheckBox(question.getChoice(i),question.isRight(i));
				choiceArea.add(options[i]);
			}
			//
			retStruct.setMulOptions(options);
			choiceArea.add(buttonArea);
			choiceArea.updateUI();
		});
		buttonArea.add(addChoice);
		buttonArea.add(deleteChoice);

		//Build CheckBox
		int optionNumber= question.getOptionNumber();
		JCheckBox[] options=new JCheckBox[optionNumber];
		for(int i=0;i<optionNumber;i++)
		{
			options[i]=new JCheckBox(question.getChoice(i),question.isRight(i));
			choiceArea.add(options[i]);
		}
		retStruct.setMulOptions(options);
		//Add Button to ChoiceArea
		choiceArea.add(buttonArea);

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
