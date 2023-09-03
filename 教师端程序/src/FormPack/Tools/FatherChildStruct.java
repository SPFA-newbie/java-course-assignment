package FormPack.Tools;

import DocumentPack.Questions.QuestionType;

import javax.swing.*;

//This is a pack about QuestionPainter
public class FatherChildStruct
{
	private final QuestionType type;

	public  JPanel mainPanel;
	public  JTextArea textArea;
	public  JTextArea answerArea;
	public  JRadioButton[] options;
	public  JCheckBox[] mulOptions;

	public FatherChildStruct(JPanel theMainPanel,JTextArea theTextArea,QuestionType theType)
	{
		mainPanel=theMainPanel;
		textArea=theTextArea;
		type=theType;
		answerArea=null;
		options=null;
		mulOptions=null;
	}

	public FatherChildStruct(JPanel theMainPanel,JTextArea theTextArea,JTextArea theAnswerArea)
	{
		mainPanel=theMainPanel;
		textArea=theTextArea;
		answerArea=theAnswerArea;
		options=null;
		mulOptions=null;
		type=QuestionType.Essay;
	}
	public FatherChildStruct(JPanel theMainPanel,JTextArea theTextArea,JRadioButton[] buttons)
	{
		mainPanel=theMainPanel;
		textArea=theTextArea;
		answerArea=null;
		options=buttons;
		mulOptions=null;
		type=QuestionType.Single;
	}
	public FatherChildStruct(JPanel theMainPanel,JTextArea theTextArea,JCheckBox[] buttons)
	{
		mainPanel=theMainPanel;
		textArea=theTextArea;
		answerArea=null;
		options=null;
		mulOptions=buttons;
		type=QuestionType.Multiple;
	}

	public void setAnswerArea(JTextArea answerArea)
	{
		this.answerArea = answerArea;
	}

	public void setMainPanel(JPanel mainPanel)
	{
		this.mainPanel = mainPanel;
	}

	public void setMulOptions(JCheckBox[] mulOptions)
	{
		this.mulOptions = mulOptions;
	}

	public void setOptions(JRadioButton[] options)
	{
		this.options = options;
	}

	public void setTextArea(JTextArea textArea)
	{
		this.textArea = textArea;
	}
}
