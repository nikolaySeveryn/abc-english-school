package nks.abc.domain.entity.user;

public enum Level {
	
	BEGINER("A1"),
	ELEMENTARY("A2"),
	PRE_INTERMEDIATE("B1"),
	INTERMEDIATE("B1"),
	UPPER_INTERMEDIATE("B2"),
	ADVANCED("C1"),
	PROFICIENCY("C2");
	
	private String ef;
	
	Level(String europeanFramework){
		ef = europeanFramework;
	}
	
	public String getEF(){
		return ef;
	}
}
