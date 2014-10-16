package denaro.nick.controllertest;

import java.util.ArrayList;

import net.java.games.input.Component;
import net.java.games.input.Controller;

public class XBoxController extends Thread
{
	public XBoxController()
	{
		controller=Main.connectToController();
		
		values=new float[LAST_BUTTON+1];
		leftAnalogDeadZone=0.3f;
		rightAnalogDeadZone=0.3f;
		triggerDeadZone=0.1f;
		triggerFull=0.8f;
		
		if(controller!=null)
		{
			start();
		}
	}
	
	public boolean isConnected()
	{
		return(controller!=null);
	}
	
	public float leftAnalogDeadZone()
	{
		return(leftAnalogDeadZone);
	}
	
	public void leftAnalogDeadZone(float leftAnalogDeadZone)
	{
		this.leftAnalogDeadZone=leftAnalogDeadZone;
	}
	
	public float rightAnalogDeadZone()
	{
		return(rightAnalogDeadZone);
	}
	
	public float triggerDeadZone()
	{
		return(triggerDeadZone);
	}
	
	public float triggerFull()
	{
		return(this.triggerFull);
	}
	
	public void addXBoxControllerListener(XBoxControllerListener listener)
	{
		if(listeners==null)
			listeners=new ArrayList<XBoxControllerListener>();
		
		if(!listeners.contains(listener))
			listeners.add(listener);
	}
	
	public void removeXBoxControllerListener(XBoxControllerListener listener)
	{
		if(listeners==null)
			listeners=new ArrayList<XBoxControllerListener>();
		else
			listeners.remove(listener);
	}
	
	public void buttonPressed(XBoxButtonEvent event)
	{
		if(listeners!=null)
		for(XBoxControllerListener listener:listeners)
			listener.buttonPressed(event);
	}
	
	public void buttonReleased(XBoxButtonEvent event)
	{
		if(listeners!=null)
		for(XBoxControllerListener listener:listeners)
			listener.buttonReleased(event);
	}
	
	public void analogMoved(XBoxAnalogEvent event)
	{
		if(listeners!=null)
		for(XBoxControllerListener listener:listeners)
			listener.analogMoved(event);
	}
	
	private boolean isInDeadZone(int id, Component component)
	{
		if(id==XBoxController.LEFTSTICK_X||id==XBoxController.LEFTSTICK_Y)
		{
			if(Math.abs(component.getPollData())<leftAnalogDeadZone)
				return(true);
		}
		return(false);
	}
	
	public boolean isInDeadZone(int buttonId)
	{
		if(buttonId==XBoxController.LEFTSTICK)
		{
			double distance=Math.sqrt(values[XBoxController.LEFTSTICK_X]*values[XBoxController.LEFTSTICK_X]+values[XBoxController.LEFTSTICK_Y]*(values[XBoxController.LEFTSTICK_Y]));
			return(distance<leftAnalogDeadZone);
		}
		if(buttonId==XBoxController.RIGHTSTICK)
		{
			double distance=Math.sqrt(values[XBoxController.RIGHTSTICK_X]*values[XBoxController.RIGHTSTICK_X]+values[XBoxController.RIGHTSTICK_Y]*(values[XBoxController.RIGHTSTICK_Y]));
			return(distance<leftAnalogDeadZone);
		}
		if(buttonId==XBoxController.TRIGGERS)
		{
			return(values[XBoxController.TRIGGERS]<triggerDeadZone);
		}
		return(false);
	}
	
	public void run()
	{
		while(controller.poll())
		{
			Component[] components=controller.getComponents();
			
			values[XBoxController.LEFTSTICK_X]=components[XBoxController.LEFTSTICK_X].getPollData();
			values[XBoxController.LEFTSTICK_Y]=components[XBoxController.LEFTSTICK_Y].getPollData();
			
			values[XBoxController.RIGHTSTICK_X]=components[XBoxController.RIGHTSTICK_X].getPollData();
			values[XBoxController.RIGHTSTICK_Y]=components[XBoxController.RIGHTSTICK_Y].getPollData();
			
			analogMoved(new XBoxAnalogEvent(XBoxButtonEvent.ANALOG_MOVED,XBoxController.LEFTSTICK,components[XBoxController.LEFTSTICK_X].getPollData(),components[XBoxController.LEFTSTICK_Y].getPollData(),isInDeadZone(XBoxController.LEFTSTICK)));
			analogMoved(new XBoxAnalogEvent(XBoxButtonEvent.ANALOG_MOVED,XBoxController.RIGHTSTICK,components[XBoxController.RIGHTSTICK_X].getPollData(),components[XBoxController.RIGHTSTICK_Y].getPollData(),isInDeadZone(XBoxController.RIGHTSTICK)));

			//do the triggers!
			
			
			//start with the buttons
			for(int i=TRIGGERS;i<components.length;i++)
			{
				if(!components[i].isAnalog())
				{
					if(values[i]!=components[i].getPollData())
					{
						
							if(components[i].getPollData()!=0)
								buttonPressed(new XBoxButtonEvent(XBoxButtonEvent.BUTTON_PRESSED,i,components[i].getPollData()));
							else
								buttonReleased(new XBoxButtonEvent(XBoxButtonEvent.BUTTON_RELEASED,i,components[i].getPollData()));
						
						values[i]=components[i].getPollData();
					}
				}
				else
				{
					//System.out.println("Shouldn't get here!");
					analogMoved(new XBoxAnalogEvent(XBoxButtonEvent.ANALOG_MOVED,i,components[i].getPollData(),components[i].getPollData(),isInDeadZone(i,components[i])));
				}
			}
			
			//the controller is there! If it disconnects poll==false
			//TODO check if I should poll constantly, or much less...
			try
			{
				Thread.sleep(10);
			}
			catch(InterruptedException e)
			{
				e.printStackTrace();
			}
		}
	}
	
	private Controller controller;
	
	private float leftAnalogDeadZone;
	private float rightAnalogDeadZone;
	private float triggerDeadZone;
	
	private float triggerFull;
	
	private float[] values;
	
	private ArrayList<XBoxControllerListener> listeners;
	
	//statics
		
		
		public static final int LEFTSTICK_X=1;
		public static final int LEFTSTICK_Y=0;
		public static final int LEFTSTICK=0;
		
		public static final int RIGHTSTICK_X=3;
		public static final int RIGHTSTICK_Y=2;
		public static final int RIGHTSTICK=2;
		
		public static final int TRIGGERS=4;
		
		public static final int A=5;
		public static final int B=6;
		public static final int X=7;
		public static final int Y=8;
		
		public static final int BACK=11;
		public static final int START=12;
		
		public static final int LEFTBUMPER=9;
		public static final int RIGHTBUMPER=10;
		
		public static final int LEFTSTICK_DOWN=13;
		public static final int RIGHTSTICK_DOWN=14;
		
		public static final int DPAD=15;
		public static final float DPAD_UP=0.25f;
		public static final float DPAD_RIGHT=0.5f;
		public static final float DPAD_DOWN=0.75f;
		public static final float DPAD_LEFT=1f;
		
		public static final int LAST_BUTTON=15;
}
