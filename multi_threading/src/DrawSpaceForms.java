import java.util.concurrent.Semaphore;

public class DrawSpaceForms extends Draw implements Runnable {

	public final int SQUARE = 1;
	public final int CIRCLE = 2;
	private final int TIME_SLEEP_STOP = 60;
	private DesignerGui gui;
	private int radius = 0;
	private int flag = 0;
	private RobotClient client;
	private Semaphore orderBehavior;

	public DrawSpaceForms(Semaphore sendMessage, Semaphore orderBehavior, RobotClient client) {
		super(sendMessage);
		this.client = client;
		gui = new DesignerGui("Espaçar formas");
		gui.setVisible();
		this.orderBehavior = orderBehavior;
	}

	// Por omissão executa sempre o quadrado para não haver excepções
	@Override
	public void draw() {
		Message spaceForms;

		try {
			if (flag == SQUARE) {
				orderBehavior.acquire();
				int space = client.spaceFormsSquare();
				spaceForms = new Line(space);
				client.sendMessage(spaceForms);
				gui.setTextToTextArea(gui.getTextFromTextArea() + "Reta: " + space + "\n");
				Thread.sleep(sleepTime(space));
			}

			else if (flag == CIRCLE) {
				orderBehavior.acquire();
				int space = client.spaceFormsCircle(radius);
				spaceForms = new Line(space);
				client.sendMessage(spaceForms);
				gui.setTextToTextArea(gui.getTextFromTextArea() + "Reta: " + space + "\n");
				Thread.sleep(sleepTime(space));

			}
			Message stop = new Stop();
			client.sendMessage(stop);
			gui.setTextToTextArea(gui.getTextFromTextArea() + "Parar\n\n");
			Thread.sleep(sleepTime(TIME_SLEEP_STOP));
			orderBehavior.release();
		} catch (InterruptedException e) {
			e.printStackTrace();
			orderBehavior.release();
		}
	}

	public void setTypeForm(int typeForm) {
		this.flag = typeForm;
	}

	public void setCircleRadius(int radius) {
		this.radius = radius;
	}

	private int sleepTime(int distance) {
		return (distance * 1000) / 30;
	}

}
