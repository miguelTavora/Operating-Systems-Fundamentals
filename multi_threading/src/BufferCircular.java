

import java.util.concurrent.Semaphore;



public class BufferCircular {

	private final int MAX_MENSAGENS = 16;
	private Message[] mensagens;
	private Semaphore semRead;
	private Semaphore readWrite;
	private int pointWrite = 0;
	private int pointRead = 0;
	private int numMessagesToRead = 0;
	private int numConsumersToRead = 0;

	public BufferCircular() {
		mensagens = new Message[MAX_MENSAGENS];
		readWrite = new Semaphore(1);
		semRead = new Semaphore(0);

	}

	//com a palavra synchronized não precisa de semaforo para as funções de read e write
	//contudo não funciona com semaforos e monitores
	public void write(Message m) {
		try {
			readWrite.acquire();
			mensagens[pointWrite] = m;
			pointWrite = ++pointWrite % MAX_MENSAGENS;
			increaseNumMessagesToRead(numMessagesToRead);

			if(numConsumersToRead > 0) {
				numConsumersToRead--;
				semRead.release();
			}
			readWrite.release();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
	}

	//lê a mensagem currente do buffer
	public Message read() {
		Message m = null;
		try {
			readWrite.acquire();
			if (numMessagesToRead == 0) {
				numConsumersToRead++;
				readWrite.release();
				semRead.acquire();
			}
			else
				readWrite.release();

			readWrite.acquire();
			m = mensagens[pointRead];
			pointRead = ++pointRead % MAX_MENSAGENS;
			numMessagesToRead--;
			readWrite.release();
		} 
		catch (InterruptedException e1) {
			e1.printStackTrace();
		}

		return m;

	}

	
	//função para ler mensagens antigas, por isso não utiliza o semaforo de read
	//por vezes tem de se ler o indice -1 outras vezes -2
	public Message readLastMessage(int minusCurrentIndex) {
		try {
			readWrite.acquire();
			if (mensagens[convertIndexIntoCircular(minusCurrentIndex)] != null) {
				Message m = mensagens[convertIndexIntoCircular(minusCurrentIndex)];
				return m;
			}

		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally{
			readWrite.release();
		}
		return null;
	}
	
	//mensagens todas do buffer em string
	public String toString() {
		String message = "";
		try {
			readWrite.acquire();
			for (int i = 0; i < mensagens.length; i++) {
				if (mensagens[i] != null)
					message+= "Mensagem "+(i+1)+": "+mensagens[i].toString()+"\n";
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally{
			readWrite.release();
		}
		
		
		return message;

	}
	
	//TODO
	//quando o número mensagens é maior que 16 fica 16
	private void increaseNumMessagesToRead(int numMessages) {
		if(numMessagesToRead != 16)
			numMessagesToRead++;
	}
	
	private int convertIndexIntoCircular(int indexReducer) {
		if(pointWrite == 0) 
			return MAX_MENSAGENS-indexReducer;
		
		else if(pointWrite == 1) 
			return MAX_MENSAGENS-indexReducer+1;
		
		else if(pointWrite==2 && indexReducer==3) 
			return MAX_MENSAGENS-indexReducer;
		
		return (pointWrite-indexReducer)%MAX_MENSAGENS;
	}

}
