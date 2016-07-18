package nks.abc.bl.domain.user;

public enum Level {
	
	BEGINER("A1", "Beginer"),
	ELEMENTARY("A2", "Elementary"),
	PRE_INTERMEDIATE("B1", "Pre Intermediate"),
	INTERMEDIATE("B1", "Intermediate"),
	UPPER_INTERMEDIATE("B2", "Upper Intermediate"),
	ADVANCED("C1", "Advanced"),
	PROFICIENCY("C2", "Proficiency");
	
	private String ef;
	private String label;
	
	Level(String europeanFramework, String label){
		ef = europeanFramework;
		this.label = label;
	}
	
	public String getEF(){
		return ef;
	}
	
	public String getLabel(){
		return label + " (" + ef + ")";
	}
}
