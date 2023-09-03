import MiddlePack.BasicGet;
import MiddlePack.BasicSend;
import MiddlePack.PairUnion;
import FormPack.Forms.Login;

import java.awt.*;

public class Start
{
	public static void main(String[] args)
	{
		//Communication Class build
		BasicGet getter=new BasicGet();
		BasicSend sender=new BasicSend();
		PairUnion.synchronizePair(sender,getter);
		//Form Class build
		EventQueue.invokeLater(()->
		{
			Login login=new Login();
			login.link(getter,sender);
			login.build();
		});
	}
}
