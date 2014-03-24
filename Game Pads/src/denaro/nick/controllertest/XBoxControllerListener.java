package denaro.nick.controllertest;

public interface XBoxControllerListener
{
	public void buttonPressed(XBoxButtonEvent event);
	
	public void buttonReleased(XBoxButtonEvent event);
	
	public void analogMoved(XBoxAnalogEvent event);
}
