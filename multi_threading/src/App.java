import java.util.concurrent.Semaphore;

;

public class App extends Thread{
	
	public final int STATE_CIRCLE = 1;
	public final int STATE_SQUARE = 2;
	
	private int TIME_OPEN_ROBOT = 500;
	private int TIME_CLOSE_ROBOT = 100;
	private Gui gui;
	private RobotLegoEV3 robot;
	private RobotServer server;
	private RobotClient client;
	private DrawCircle circle;
	private DrawSquare square;
	private DrawSpaceForms spaceForms;
	private BufferCircular buffer;
	private Thread serverThread;
	private Thread circleThread;
	private Thread squareThread;
	private Thread spaceThread;
	private Semaphore[] sendMessages;
	private Semaphore semBehavior;
	
	
	
	//variável que dita se o canal de comunicação foi aberto com sucesso ou não
	private boolean comunicationOpened = false;
	
	public App() {
		gui = new Gui(this);
		buffer = new BufferCircular();
		robot = new RobotLegoEV3();
		server = new RobotServer(buffer,robot);
		client = new RobotClient(buffer);
		
		sendMessages = new Semaphore[3];
		for(int i = 0; i < 3;i++) {
			Semaphore s = new Semaphore(0);
			sendMessages[i] = s;
		}
		semBehavior = new Semaphore(1);
		
		spaceForms = new DrawSpaceForms(sendMessages[0],semBehavior,client);
		square = new DrawSquare(sendMessages[1],semBehavior,client);
		circle = new DrawCircle(sendMessages[2],semBehavior,client);
		
		circleThread = new Thread(circle);
		squareThread = new Thread(square);
		spaceThread = new Thread(spaceForms);
		
		
		serverThread = new Thread(server);
		
		circleThread.start();
		squareThread.start();
		spaceThread.start();
		
		serverThread.start();
		
		
	}
	
	public static void main(String[] args) {
		App app = new App();
		app.start();
	}
	
	public void run() {
		gui.setVisible();
		server.run();
	}
	
	
	public void stateMachine(int state) {
		switch (state) {
		case STATE_CIRCLE:
			// espaçar formas
			gui.setButtonsActive(false);
			int radius = Integer.parseInt(gui.getRadius());
			
			spaceForms.setCircleRadius(radius);
			spaceForms.setTypeForm(spaceForms.CIRCLE);
			sendMessages[0].release();

			// desenahr quadrado
			circle.setDistance(radius);
			sendMessages[2].release();
			gui.setButtonsActive(true);
			break;
				
		case STATE_SQUARE:
			// espaçar formas
			gui.setButtonsActive(false);
			int distance = Integer.parseInt(gui.getDistance());
			spaceForms.setCircleRadius(distance);
			spaceForms.setTypeForm(spaceForms.SQUARE);
			sendMessages[0].release();

			// desenahr quadrado
			square.setDistance(distance);
			sendMessages[1].release();
			gui.setButtonsActive(true);
			break;

		}
	}
	
	//função para abrir comunicação com o robô
	public void openCommunication() {
		try {
			gui.setTextToOutput("Abrir: "+gui.getRobotName());
			boolean open = robot.OpenEV3(gui.getRobotName());
			Thread.sleep(TIME_OPEN_ROBOT);
			gui.setOpen(open);//não fica selecionado quando não consegue abrir
			comunicationOpened = open;
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	//função chamada quando o robô está conectado e se desconecta
	public void closeCommunication() {
		try {
			if (comunicationOpened) {
				comunicationOpened = false;
				gui.setTextToOutput("Fechar");
				robot.CloseEV3();
				Thread.sleep(TIME_CLOSE_ROBOT);
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	//função chamada quando se termina a aplicação
	public void exitApp() {
		try {
			if (comunicationOpened) {
				robot.Parar(true);
				robot.CloseEV3();
				Thread.sleep(TIME_CLOSE_ROBOT);
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
