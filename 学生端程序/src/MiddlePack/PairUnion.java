package MiddlePack;

import DataOperationPack.InternetManager;

public class PairUnion //Ues to Synchronize Getter and Sender
{
	public static void synchronizePair(BasicSend sender, BasicGet getter)
	{
		sender.linkPair(getter);
		getter.linkPair(sender);

		InternetManager itManager=new InternetManager();
		sender.linkInternet(itManager);
		getter.linkInternet(itManager);
	}
}
