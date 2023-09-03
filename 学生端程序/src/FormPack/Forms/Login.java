package FormPack.Forms;

import MiddlePack.BasicGet;
import MiddlePack.BasicSend;
import StringPack.ButtonNames;
import StringPack.LabelStrings;
import StringPack.MessageStrings;
import StringPack.TitleStrings;

import javax.swing.*;
import java.awt.*;

public class Login extends JFrame
{
	//Communication Resources
	BasicGet Getter;
	BasicSend Sender;
	//Components of Form
	private JLabel userNameLabel;
	private JLabel userIdLabel;
	private JLabel passWordLabel;
	private JTextField userName;
	private JTextField userId;
	private JPasswordField password;
	private JButton loginButton;
	private JPanel textSet;
	private JPanel idSet;
	private JPanel passwordSet;
	private JPanel buttonSet;

	public Login()//Setting some property of form
	{
		super();
		//Position of Form
		Dimension scan =Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(scan.width/4, scan.height/4);
		//Size of Form
		this.setSize(scan.width/4,scan.height/4);
		//Title of Form
		this.setTitle(TitleStrings.Login);
		//Choose Container
		this.setLayout(new GridLayout(4,1,5,5));
		//Other Property list of Form
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//If user do noting before exit it
		this.setVisible(true);
	}

	public void link(BasicGet getter, BasicSend sender)//Link resources the form use
	{
		Getter=getter;
		Sender=sender;
	}

	public void build()//Load all parts of it
	{
		//Build the label
		userNameLabel=new JLabel(LabelStrings.UserName);
		userIdLabel=new JLabel(LabelStrings.UserId);
		passWordLabel =new JLabel(LabelStrings.PassWord);
		//Build the username&password field
		userName=new JFormattedTextField();
		userId=new JTextField();
		password=new JPasswordField();
		userName.setColumns(25);
		userId.setColumns(25);
		password.setColumns(25);
		userName.setText("");
		userId.setText("");
		password.setText("");
		//Build the login button
		loginButton=new JButton(ButtonNames.Login);
		loginButton.addActionListener(event->
		{
			if(!userName.getText().equals("")&&!userId.getText().equals(""))
			{
				if(Sender.sendLoginMessage(this.userId.getText(),this.userName.getText(),this.password.getPassword()))
				{
					EventQueue.invokeLater(()->
					{
						this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
						MainForm mainForm=new MainForm();
						mainForm.link(Getter,Sender);
						mainForm.build();
						this.dispose();
					});
				}else
				{
					JOptionPane.showMessageDialog
							(null, MessageStrings.LoginError,
							 TitleStrings.LoginFailed,JOptionPane.ERROR_MESSAGE);
				}
			}else
			{
				JOptionPane.showMessageDialog
						(null,MessageStrings.EmptyNameError,
						 TitleStrings.LoginFailed,JOptionPane.ERROR_MESSAGE);
			}
			this.userName.setText("");
			this.userId.setText("");
			this.password.setText("");
		});
		//Build the set
		textSet=new JPanel();
		idSet=new JPanel();
		passwordSet=new JPanel();
		buttonSet=new JPanel();
		textSet.setLayout(new FlowLayout(FlowLayout.CENTER));
		idSet.setLayout(new FlowLayout(FlowLayout.CENTER));
		passwordSet.setLayout(new FlowLayout(FlowLayout.CENTER));
		buttonSet.setLayout(new FlowLayout(FlowLayout.CENTER));
		//Add components to the set
		textSet.add(userNameLabel);
		textSet.add(userName);
		idSet.add(userIdLabel);
		idSet.add(userId);
		passwordSet.add(passWordLabel);
		passwordSet.add(password);
		buttonSet.add(loginButton);
		//Add set to form
		this.add(textSet);
		this.add(idSet);
		this.add(passwordSet);
		this.add(buttonSet);
	}
}
