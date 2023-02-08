

public class RobotClient {

	// private final int TIME_SLEEP = 100;
	public final int STATE_DRAW_RECT = 1;
	public final int STATE_DRAW_CIRCLE = 2;
	
	private final int COMPLETE_ROTATION = 360;
	private final int INCREASE_SPACE = 10;//incremento espaço entre cada forma
	private final int DEFAULT_SPACE = 0;//espaçamento por omissão (quando nãoe xiste nenhuma forma anterior)

	private BufferCircular buffer;

	// cria os diferentes clientes que desenham formas e lançamos como threads
	public RobotClient(BufferCircular buffer) {
		this.buffer = buffer;

	}

	public void sendMessage(Message e) {
		buffer.write(e);
	}

	/*
	 * public void drawSpaceForms(int typeForm, int radius) {
	 * spaceForms.setTypeForm(typeForm); spaceForms.setCircleRadius(radius); }
	 * 
	 * public void drawSquare(int distance) { square.setDistance(distance);
	 * canDraw.set(true); state = STATE_DRAW_RECT; }
	 * 
	 * public void drawCircle(int radius) { circle.setDistance(radius);
	 * canDraw.set(true); state = STATE_DRAW_CIRCLE; }
	 */

	// buscar o tamanho das formas anteriores para incrementar
	public int spaceFormsCircle(int radius) {
		Message m = buffer.readLastMessage(2);
		Message m2 = buffer.readLastMessage(3);

		if (m != null) {
			if (m.getId() == LeftCurve.CURVE && m.getAngle() == COMPLETE_ROTATION)
				return m.getRay() + INCREASE_SPACE + radius;

		}

		if (m2 != null) {
			if (m2.getId() == Line.LINE)
				return m2.getDistance() + INCREASE_SPACE + radius;

		}
		return DEFAULT_SPACE;
	}

	// buscar o tamanho das formas anteriores para incrementar
	public int spaceFormsSquare() {
		Message m = buffer.readLastMessage(2);
		Message m2 = buffer.readLastMessage(3);

		if (m != null) {
			if (m.getId() == LeftCurve.CURVE && m.getAngle() == COMPLETE_ROTATION)
				return m.getRay() + INCREASE_SPACE;// corresponde ao raio mais mais distância segurança

		}

		if (m2 != null) {
			if (m2.getId() == Line.LINE)
				return m2.getDistance() + INCREASE_SPACE;// tamanho do lado do quadrado mais distância segurança

		}

		return DEFAULT_SPACE;
	}

}
