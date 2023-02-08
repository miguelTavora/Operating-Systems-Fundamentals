

public class Line implements Message{
	
	public static final int LINE = 2;
	//distancia que vai ser percorrida
	private int distance;
	
	public Line(int distance) {
		this.distance = distance;
	}

	@Override
	public int getId() {
		return LINE;
	}

	@Override
	public int getDistance() {
		return distance;
	}

	@Override
	public int getRay() {
		return 0;
	}

	@Override
	public int getAngle() {
		return 0;
	}
	
	@Override
	public String toString() {
		return "Reta "+distance;
	}

}
