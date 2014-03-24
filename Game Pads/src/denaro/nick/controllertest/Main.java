package denaro.nick.controllertest;

import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JFrame;

import net.java.games.input.Component;
import net.java.games.input.Controller;
import net.java.games.input.ControllerEnvironment;
import net.java.games.input.Controller.Type;

public class Main
{
	public static void main(String[] args)
	{
		XBoxController controller=new XBoxController();
		
		if(!controller.isAlive())
		{
			System.out.println("No controller was found. Exiting...");
			System.exit(0);
		}
		
		JFrame frame=new JFrame("Controller testing!");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		final ControllerCanvas canvas=new ControllerCanvas(controller);
		controller.addXBoxControllerListener(canvas);
		frame.add(canvas);
		frame.setVisible(true);
		frame.pack();
		
		Timer timer=new Timer();
		
		timer.schedule(new TimerTask(){

			@Override
			public void run()
			{
				canvas.repaint();
			}
			
		},16,16);
	}
	
	public static Controller connectToController()
	{
		Controller controller=null;
		//System.out.println("Controllers: "+arrayToString(ControllerEnvironment.getDefaultEnvironment().getControllers()));
		Controller[] controllers=ControllerEnvironment.getDefaultEnvironment().getControllers();
		for(int i=0;i<controllers.length;i++)
		{
			if(controllers[i].getName().contains("360"))
			{
				controller=controllers[i];
				break;
			}
		}
		if(controller!=null)
			System.out.println("Controller: "+controller.getName()+" - "+controller.getType());
		else
			System.out.println("NO specified controller");
		
		return(controller);
	}
	
	public static String arrayToString(Object[] array)
	{
		String s="[";
		int i=0;
		for(;i<array.length-1;i++)
		{
			s+=array[i]+", ";
		}
		s+=array[i];
		s+="]";
		return(s);
	}
}
