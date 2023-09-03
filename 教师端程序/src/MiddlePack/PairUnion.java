package MiddlePack;

import DataOperationPack.DataBaseManager;
import DataOperationPack.InternetManager;

public class PairUnion //Ues to Synchronize Getter and Sender
{
	public static void synchronizePair(BasicSend sender, BasicGet getter)
	{
		sender.linkPair(getter);
		getter.linkPair(sender);

		DataBaseManager dbManager=new DataBaseManager();
		InternetManager itManager=new InternetManager(getter);
		sender.linkDataBase(dbManager);
		getter.linkDataBase(dbManager);
		sender.linkInternet(itManager);
		getter.linkInternet(itManager);

		itManager.linkStart();
	}
}
