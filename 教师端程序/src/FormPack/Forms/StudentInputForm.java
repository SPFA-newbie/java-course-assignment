package FormPack.Forms;

import MiddlePack.BasicGet;
import MiddlePack.BasicSend;
import FormPack.Components.MemberTable;
import StringPack.ButtonNames;
import StringPack.LabelStrings;
import StringPack.TitleStrings;

import javax.swing.*;
import java.awt.*;

public class StudentInputForm extends JFrame
{
	private JLabel nameLabel;
	private JLabel idLabel;
	private JLabel ipLabel;
	private JTextField name;
	private JTextField id;
	private JTextField ip;
	private JButton addButton;
	private JPanel nameSet;
	private JPanel idSet;
	private JPanel ipSet;
	private JPanel buttonSet;

	public StudentInputForm()//Setting some property of form
	{
		super();
		//Position of Form
		Dimension scan =Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(scan.width/4, scan.height/4);
		//Size of Form
		this.setSize(scan.width/4,scan.height/4);
		//Title of Form
		this.setTitle(TitleStrings.InputForm);
		//Choose Container
		this.setLayout(new GridLayout(4,1,5,5));
		//Other Property list of Form
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);//If user do noting before exit it
		this.setVisible(true);
	}

	public void NewStudent(MemberTable theTable,BasicGet getter,BasicSend sender)
	{
		nameLabel=new JLabel(LabelStrings.InputStudentName);
		idLabel=new JLabel(LabelStrings.InputStudentId);
		ipLabel=new JLabel(LabelStrings.InputStudentPassword);
		name=new JTextField(25);
		id=new JTextField(25);
		ip=new JTextField(25);

		addButton=new JButton(ButtonNames.Accept);
		addButton.addActionListener(event->
		{
			sender.sendNewStudent(name.getText(),id.getText(),ip.getText());
			theTable.makeList();
			this.dispose();
		});

		nameSet=new JPanel(new FlowLayout());
		idSet=new JPanel(new FlowLayout());
		ipSet=new JPanel(new FlowLayout());
		buttonSet=new JPanel(new FlowLayout());

		nameSet.add(nameLabel);
		nameSet.add(name);
		idSet.add(idLabel);
		idSet.add(id);
		ipSet.add(ipLabel);
		ipSet.add(ip);
		buttonSet.add(addButton);

		this.add(nameSet);
		this.add(idSet);
		this.add(ipSet);
		this.add(buttonSet);
	}
}
