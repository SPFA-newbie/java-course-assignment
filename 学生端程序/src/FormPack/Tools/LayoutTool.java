package FormPack.Tools;

import java.awt.*;

public class LayoutTool extends GridBagConstraints
{
	public LayoutTool()
	{
		super();
		weightx=1.0;
		weighty=1.0;
	}

	public LayoutTool setFill(int theFill)
	{
		this.fill=theFill;
		return this;
	}
	public LayoutTool setSize(int x,int y)
	{
		this.gridwidth=x;
		this.gridheight=y;
		return this;
	}
	public LayoutTool setPosition(int x,int y)
	{
		this.gridx=x;
		this.gridy=y;
		return this;
	}
	public LayoutTool endLine()
	{
		this.gridwidth=GridBagConstraints.REMAINDER;
		return this;
	}
	public LayoutTool endColumn()
	{
		this.gridheight=GridBagConstraints.REMAINDER;
		return this;
	}
	public LayoutTool fill(int theFill)
	{
		this.fill= theFill;
		return this;
	}

}
