package sk.hackcraft.learning.enums;

public enum EAction {
	DO_NOTHING("doNothing"),
	RUN_AWAY("runAway"),
	ATTACK("attack");
	
	private final String value;
	
	EAction(final String value) {
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
