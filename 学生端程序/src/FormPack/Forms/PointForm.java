package FormPack.Forms;

import MiddlePack.BasicGet;
import MiddlePack.BasicSend;
import StringPack.LabelStrings;
import StringPack.TitleStrings;

import javax.swing.*;
import java.awt.*;

public class PointForm extends JFrame
{
	//Communication Resources
	BasicGet Getter;
	//Components
	JLabel point;

	public PointForm()//Setting some property of form
	{
		super();
		//Position of Form
		Dimension scan =Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(scan.width/4, scan.height/4);
		//Size of Form
		this.setSize(scan.width/4,scan.height/4);
		//Title of Form
		this.setTitle(TitleStrings.ExamResult);
		//Choose Container
		this.setLayout(new FlowLayout(FlowLayout.CENTER));
		//Other Property list of Form
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setVisible(true);
	}

	public void link(BasicGet getter)
	{
		Getter=getter;
	}

	public void build()
	{
		point=new JLabel(LabelStrings.YourPoint);
		point.setFont(new Font(point.getFont().getName(),Font.PLAIN,30));
		point.setText(LabelStrings.YourPoint+Getter.getPoint());
		this.add(point);
	}
}
