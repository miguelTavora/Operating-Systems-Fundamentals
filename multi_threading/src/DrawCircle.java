import java.util.concurrent.Semaphore;

public class DrawCircle extends Draw implements Runnable{
	
	private final int COMPLETE_ROTATION = 360;
	private final int TIME_SLEEP_STOP = 60;
	private int radius;
	private DesignerGui gui;
	private RobotClient client;
	private Semaphore orderBehavior;
	
	public DrawCircle(Semaphore sendMessage, Semaphore orderBehavior, RobotClient client) {
		super(sendMessage);
		this.client = client;
		gui = new DesignerGui("Desenhar circulo");
		gui.setVisible();
		this.orderBehavior = orderBehavior;
	}
	
	
	@Override
	public void draw() {
		Message curve = new LeftCurve(radius, COMPLETE_ROTATION);
		Message stop = new Stop();

		try {
			orderBehavior.acquire();
			client.sendMessage(curve);
			gui.setTextToTextArea(gui.getTextFromTextArea()+"Circulo: "+radius+"\n");
			Thread.sleep(sleepTime());
			client.sendMessage(stop);
			gui.setTextToTextArea(gui.getTextFromTextArea()+"Parar\n\n");
			Thread.sleep(TIME_SLEEP_STOP);
			orderBehavior.release();
		} catch (InterruptedException e) {
			e.printStackTrace();
			orderBehavior.release();
		}
		
		
	}
	
	//dar a distância que a forma geometrica deve ter
	public void setDistance(int distance) {
		this.radius = distance;
	}
	
	private int sleepTime() {
		return (2*radius*(int)Math.PI)/30*1000+600;//o 600 é para margem de erro		
	}
	
}
