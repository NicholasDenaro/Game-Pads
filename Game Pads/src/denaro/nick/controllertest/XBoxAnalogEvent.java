package denaro.nick.controllertest;

public class XBoxAnalogEvent
{
	public XBoxAnalogEvent(int eventType, int analogCode, float pollDataX, float pollDataY)
	{
		this.analogCode=analogCode;
		this.eventType=eventType;
		this.pollDataX=pollDataX;
		this.pollDataY=pollDataY;
		this.inDeadZone=false;
	}
	
	public XBoxAnalogEvent(int eventType, int buttonCode, float pollDataX, float pollDataY, boolean inDeadZone)
	{
		this.analogCode=buttonCode;
		this.eventType=eventType;
		this.pollDataX=pollDataX;
		this.pollDataY=pollDataY;
		this.inDeadZone=inDeadZone;
	}
	
	public int getButtonCode()
	{
		return(analogCode);
	}
	
	public int getEventType()
	{
		return(eventType);
	}
	
	public float pollDataX()
	{
		return(pollDataX);
	}
	
	public float pollDataY()
	{
		return(pollDataY);
	}
	
	public boolean inDeadZone()
	{
		return(inDeadZone);
	}
	
	private int analogCode;
	
	private int eventType;
	
	private float pollDataX, pollDataY;
	
	private boolean inDeadZone;
}
