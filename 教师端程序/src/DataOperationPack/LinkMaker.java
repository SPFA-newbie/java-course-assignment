package DataOperationPack;

import StringPack.MessageStrings;
import StringPack.TitleStrings;

import javax.swing.*;
import java.io.IOException;
import java.net.ServerSocket;

public class LinkMaker implements Runnable
{
	private InternetManager manager;

	public LinkMaker(InternetManager theManager)
	{
		manager=theManager;
	}

	public void run()
	{
		try
		{
			ServerSocket listener=new ServerSocket(InternetManager.port);
			while(manager.isListening())
			{
				Connector newConnector=new Connector(listener.accept(),manager);
				manager.catchConnection(newConnector);
				Thread connectorTread=new Thread(newConnector);
				connectorTread.start();
			}
		}catch (IOException e)
		{
			JOptionPane.showMessageDialog
					(null, MessageStrings.InternetError,
					TitleStrings.InternetError,JOptionPane.ERROR_MESSAGE);
		}
	}
}
