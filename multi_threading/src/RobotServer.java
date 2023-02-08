


public class RobotServer implements Runnable{

	//private final int TIME_SLEEP = 10;
	private RobotDesigner designer;
	private BufferCircular buffer;
	private DesignerGui gui;
	private RobotLegoEV3 robot;

	// lança o robot designer como uma thread
	public RobotServer(BufferCircular buffer, RobotLegoEV3 robot) {
		this.buffer = buffer;
		gui = new DesignerGui("Servidor Robot");
		designer = new RobotDesigner();
		this.robot = robot;
	
		gui.setVisible();
	}

	// método bloqueante como semaforo que so acaba quando tem mensagens
	public void run() {
		for (;;) {
			Message m;
			if ((m = buffer.read()) != null)
				converteMessage(m);
		}
	}

	// função que interpreta a informação do buffer e age conforme a informação
	private void converteMessage(Message m) {
		int id = m.getId();


		if (id == Line.LINE) {
			robot.Reta(m.getDistance());
			gui.setTextToTextArea(gui.getTextFromTextArea() + "Reta: " + m.getDistance()+"\n");
			designer.setTextToWindow("Reta: " + m.getDistance());
			
		}
		
		else if (id == LeftCurve.CURVE) {
			//robot.CurvarEsquerda(m.getRay(), m.getAngle());
			robot.CurvarDireita(m.getRay(), m.getAngle());
			gui.setTextToTextArea(gui.getTextFromTextArea() + "Curvar: " + m.getRay() + ", angulo:" + m.getAngle()+ "º\n");
			designer.setTextToWindow("Curvar: " + m.getRay() + ", angulo: "+ m.getAngle());
		}

		else if (id == Stop.STOP) {
			robot.Parar(false);
			gui.setTextToTextArea(gui.getTextFromTextArea() + "Parar\n\n");
			designer.setTextToWindow("Parar");
			//System.out.println(buffer.toString());
		}
		

	}

}
