package DataOperationPack;

import DocumentPack.Members.Student;
import DocumentPack.Tests.BackTestStruct;
import DocumentPack.Tests.TestPaper;
import MiddlePack.BasicGet;
import StringPack.MessageStrings;

import java.util.ArrayList;

public class InternetManager
{
	public final static int port=3456;

	private BasicGet getter;

	private boolean listening;
	private LinkMaker linkMaker;
	private ArrayList<Connector> connectors;

	private TestPaper paper;
	private ArrayList<BackTestStruct> backPapers;

	public InternetManager(BasicGet Getter)
	{
		getter=Getter;

		listening=false;

		connectors=new ArrayList<>();
		backPapers=new ArrayList<>();
		paper=null;
	}

//Start and Stop Link with Student
	public void linkStart()
	{
		listening=true;
		linkMaker=new LinkMaker(this);
		Thread linkThread=new Thread(linkMaker);
		linkThread.start();
	}
	public void linkStop()
	{
		listening=false;
	}

//Used by Link Maker
	public boolean isListening()
	{
		return listening;
	}
	public void catchConnection(Connector newConnector)
	{
		connectors.add(newConnector);
	}

//Used by Connector
	public Student giveStudent(String id)
	{
		return getter.getStudent(id);
	}
	public TestPaper givePaper()
	{
		return paper;
	}
	public void catchBackTest(BackTestStruct object)
	{
		backPapers.add(object);
	}
	public String givePoint(Student want)
	{
		String ret="-1";
		for(int i=0;i< backPapers.size();i++)
			if(want.equals(backPapers.get(i).getFromStudent()))
				ret=Integer.toString(backPapers.get(i).getFinalPoint());
		if(ret.equals("-1"))return MessageStrings.NoInformation;
		return ret;
	}
	public void killConnector(Connector connector)
	{
		connectors.remove(connector);
	}

//Used by Sender&Getter
	public void setPaper(TestPaper thePaper)
	{
		paper=thePaper;
	}
	public BackTestStruct[] getBackPaper()
	{
		BackTestStruct[] ret=new BackTestStruct[0];
		if(backPapers.size()==0)return ret;

		ret=new BackTestStruct[backPapers.size()];
		for(int i=0;i<backPapers.size();i++)
			ret[i]=backPapers.get(i);
		return ret;
	}
	public void correctOver(ArrayList<BackTestStruct> result)
	{
		backPapers=result;
	}
}
