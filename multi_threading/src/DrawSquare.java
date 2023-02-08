import java.util.concurrent.Semaphore;

public class DrawSquare extends Draw implements Runnable{
	
	private final int ROTATION = 90;
	private final int TIME_TO_CURVE = 2*3*10*(90/360);
	private final int TIME_SLEEP_STOP = 60;
	private int distance;
	private DesignerGui gui;
	private RobotClient client;
	private Semaphore orderBehavior;
	

	public DrawSquare(Semaphore sendMessage, Semaphore orderBehavior, RobotClient client) {
		super(sendMessage);
		this.client = client;
		gui = new DesignerGui("Desenhar quadrado");
		gui.setVisible();
		this.orderBehavior = orderBehavior;
	}
	
	
	// Não é preciso a instrução parar, porque ao fim da ordem ele para
	@Override
	public void draw() {
		try {
			Message reta = new Line(distance);
			Message stop = new Stop();// Para rodar sem mover do sitio o primeiro argumento deve ser 0
			Message curve = new LeftCurve(10, ROTATION);// Para rodar sem mover do sitio o primeiro argumento deve ser 0
			
			orderBehavior.acquire();
			for (int i = 0; i < 4; i++) {
				client.sendMessage(reta);
				gui.setTextToTextArea(gui.getTextFromTextArea() + "Reta: " + distance + "\n");
				Thread.sleep(sleepTime());
				if(i != 3) {
					client.sendMessage(stop);
					gui.setTextToTextArea(gui.getTextFromTextArea() + "Parar\n");
					Thread.sleep(TIME_SLEEP_STOP);
				}
				client.sendMessage(curve);
				gui.setTextToTextArea(gui.getTextFromTextArea() + "Curva: 90º\n");
				Thread.sleep(TIME_TO_CURVE);
				
				
			}
			
			client.sendMessage(stop);
			gui.setTextToTextArea(gui.getTextFromTextArea() + "Parar\n\n");
			Thread.sleep(TIME_SLEEP_STOP);
			orderBehavior.release();

		} catch (InterruptedException e) {
			e.printStackTrace();
			orderBehavior.release();
		}
		
	}
	
	//fazer set da distancia da reta
	public void setDistance(int distance) {
		this.distance = distance;
	}
	
	private int sleepTime() {
		return (distance*1000)/30;		
	}
	
}
