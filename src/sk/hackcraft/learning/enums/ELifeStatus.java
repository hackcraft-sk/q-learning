package sk.hackcraft.learning.enums;

public enum ELifeStatus {
	DEAD("dead"),
	LOW("low"),
	MEDIUM("medium"),
	HIGH("high");
	
	private final String value;
	
	ELifeStatus(final String value) {
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
