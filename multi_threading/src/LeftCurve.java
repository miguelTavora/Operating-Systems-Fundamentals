

public class LeftCurve implements Message{
	
	public static final int CURVE = 3;
	//raio porque o ângulo é sempre 360º
	private int raio;
	private int angulo;
	
	public LeftCurve(int raio, int angulo) {
		this.raio = raio;
		this.angulo = angulo;
	}

	@Override
	public int getId() {
		return CURVE;
	}

	@Override
	public int getDistance() {
		return 0;
	}

	@Override
	public int getRay() {
		return raio;
	}

	@Override
	public int getAngle() {
		return angulo;
	}
	
	@Override
	public String toString() {
		return "Curvar "+raio+", "+angulo+"º";
	}

}
