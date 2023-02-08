

public class Stop implements Message{

	public static final int STOP = 4;

	@Override
	public int getId() {
		return STOP;
	}

	@Override
	public int getDistance() {
		return 0;
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
		return "Parar";
	}
	
}
