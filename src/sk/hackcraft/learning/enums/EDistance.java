package sk.hackcraft.learning.enums;

public enum EDistance {
	IN_RANGE("inRange"),
	IN_SIGHT("inSight"),
	OUT_OF_SIGHT("outOfSight");
	
	private final String value;
	
	EDistance(final String value) {
		this.value = value;
	}
	
	public String getValue() {
		return value;
	}
	
	@Override
    public String toString() {
        return this.getValue();
    }
}
