package FormPack.Forms;

import MiddlePack.BasicGet;
import MiddlePack.BasicSend;
import StringPack.ButtonNames;
import StringPack.LabelStrings;
import StringPack.MessageStrings;
import StringPack.TitleStrings;

import javax.swing.*;
import java.awt.*;

public class Register extends JFrame
{
	//Communication Resources
	BasicGet Getter;
	BasicSend Sender;
	//Components of Form
	private JLabel userNameLabel;
	private JLabel passWordLabel;
	private JLabel rePassWordLabel;
	private JTextField userName;
	private JPasswordField password;
	private JPasswordField rePassword;
	private JButton registerButton;
	private JButton gotoLogin;
	private JPanel textSet;
	private JPanel passwordSet;
	private JPanel rePasswordSet;
	private JPanel buttonSet;

	private boolean ComparePassword()//Compared "password" and "re-password"
	{
		char[] passwd=password.getPassword();
		char[] rePasswd=rePassword.getPassword();
		if(rePasswd.length!=passwd.length)
			return false;
		for(int i=0;i<passwd.length;i++)
			if(passwd[i]!=rePasswd[i])
				return false;
		return true;
	}

	public Register()//Setting some property of form
	{
		super();
		//Position of Form
		Dimension scan =Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(scan.width/4, scan.height/4);
		//Size of Form
		this.setSize(scan.width/4,scan.height/4);
		//Title of Form
		this.setTitle(TitleStrings.Register);
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
		passWordLabel =new JLabel(LabelStrings.PassWord);
		rePassWordLabel=new JLabel(LabelStrings.RePassWord);
		//Build the username&password field
		userName=new JFormattedTextField();
		password=new JPasswordField();
		rePassword=new JPasswordField();
		userName.setColumns(25);
		password.setColumns(25);
		rePassword.setColumns(25);
		userName.setText("");
		password.setText("");
		rePassword.setText("");
		//Build the Register button
		registerButton=new JButton(ButtonNames.Register);
		registerButton.addActionListener(event->
		{
			if(!userName.getText().equals(""))
			{
				if(this.ComparePassword())
				{
					if(Sender.sendRegisterMessage(userName.getText(),password.getPassword()))
					{
						JOptionPane.showMessageDialog
								(null,MessageStrings.RegisterSucceed,
								 TitleStrings.RegisterSucceed,JOptionPane.INFORMATION_MESSAGE);
					}else
					{
						JOptionPane.showMessageDialog
								(null,MessageStrings.RegisterError,
								 TitleStrings.RegisterFailed,JOptionPane.ERROR_MESSAGE);
					}
					userName.setText("");
				}else
				{
					JOptionPane.showMessageDialog
							(null, MessageStrings.RePassWordError,
							 TitleStrings.RegisterFailed,JOptionPane.ERROR_MESSAGE);
				}
			}else
			{
				JOptionPane.showMessageDialog
						(null,MessageStrings.EmptyNameError,
						 TitleStrings.RegisterFailed,JOptionPane.ERROR_MESSAGE);
			}
			password.setText("");
			rePassword.setText("");
		});
		//Build the gotoLogin button
		gotoLogin=new JButton(ButtonNames.Login);
		gotoLogin.addActionListener(event->
		{
			EventQueue.invokeLater(()->
			{
				this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
				Login login=new Login();
				login.link(Getter,Sender);
				login.build();
				this.dispose();
			});
		});
		//Build the set
		textSet=new JPanel();
		passwordSet=new JPanel();
		rePasswordSet=new JPanel();
		buttonSet=new JPanel();
		textSet.setLayout(new FlowLayout(FlowLayout.CENTER));
		passwordSet.setLayout(new FlowLayout(FlowLayout.CENTER));
		rePasswordSet.setLayout(new FlowLayout(FlowLayout.CENTER));
		buttonSet.setLayout(new FlowLayout(FlowLayout.CENTER));
		//Add components to the set
		textSet.add(userNameLabel);
		textSet.add(userName);
		passwordSet.add(passWordLabel);
		passwordSet.add(password);
		rePasswordSet.add(rePassWordLabel);
		rePasswordSet.add(rePassword);
		buttonSet.add(registerButton);
		buttonSet.add(gotoLogin);
		//Add set to form
		this.add(textSet);
		this.add(passwordSet);
		this.add(rePasswordSet);
		this.add(buttonSet);
	}
}
