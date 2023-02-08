
import java.util.concurrent.Semaphore;

public abstract class Draw implements Runnable{
	
	//private int TIME_SLEEP = 100;
	private Semaphore semSend;
	//private AtomicBoolean canDraw; 
	
	public Draw(Semaphore semSend) {
		this.semSend = semSend;
		/*canDraw = new AtomicBoolean();
		canDraw.set(false);*/
	}


	public abstract void draw();
	
	//método que fica a correr até se clicar numa forma geometrica
	@Override
	public void run() {
		for (;;) {
			try {
				semSend.acquire();
				draw();
				//canDraw.set(false);
				/*Thread.sleep(TIME_SLEEP);
				if(canDraw.get()) {*/
					
				//}
				
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

	}
	
	//metodo set para meter mensagens no buffer
	/*public void setDraw(boolean value) {
		canDraw.set(value);
	}*/

}
