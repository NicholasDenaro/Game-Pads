package denaro.nick.controllertest;

import java.awt.event.InputEvent;

public class XBoxButtonEvent
{
	public XBoxButtonEvent(int eventType, int buttonCode, float pollData)
	{
		this.buttonCode=buttonCode;
		this.eventType=eventType;
		this.pollData=pollData;
		this.deadZone=false;
	}
	
	public XBoxButtonEvent(int eventType, int buttonCode, float pollData, boolean deadZone)
	{
		this.buttonCode=buttonCode;
		this.eventType=eventType;
		this.pollData=pollData;
		this.deadZone=deadZone;
	}
	
	public int getButtonCode()
	{
		return(buttonCode);
	}
	
	public int getEventType()
	{
		return(eventType);
	}
	
	public float pollData()
	{
		return(pollData);
	}
	
	public boolean deadZone()
	{
		return(deadZone);
	}
	
	private int buttonCode;
	
	private int eventType;
	
	private float pollData;
	
	private boolean deadZone;
	
	public static final int BUTTON_PRESSED=0;
	public static final int BUTTON_RELEASED=1;
	public static final int ANALOG_MOVED=2;
}
