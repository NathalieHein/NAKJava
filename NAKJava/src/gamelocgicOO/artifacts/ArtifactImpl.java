package gamelocgicOO.artifacts;

public abstract class ArtifactImpl implements Artifact {
	protected int value;

	public boolean isDown() {
		return value == 0;
	}

	@Override
	public boolean isGreaterThan(int operand) {
		return value > operand;
	}

	public boolean isGreaterThan(Artifact operand) {
		return !operand.isGreaterThan(value);
	}

	public boolean isGreaterThanAddition(Artifact operand, int addition) {
		return !operand.isGreaterThan(value + addition);
	}

	// rounds up
	public boolean isGreaterThanMultiplication(Artifact operand,
			float multiplicator) {
		return !operand.isGreaterThan((int) Math.ceil(value * multiplicator));
	}

	protected int getValue() {
		return value;
	}

	protected void setValue(int value) {
		this.value = value;
	}
}
