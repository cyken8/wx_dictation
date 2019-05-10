package indi.cyken.domain;

public class WordComeType {

	private String cometypeid;
	private String cometypename;
	public WordComeType() {
		
	}
	
	
	public WordComeType(String cometypeid) {
		super();
		this.cometypeid = cometypeid;
	}


	public String getCometypeid() {
		return cometypeid;
	}
	public void setCometypeid(String cometypeid) {
		this.cometypeid = cometypeid;
	}
	public String getCometypename() {
		return cometypename;
	}
	public void setCometypename(String cometypename) {
		this.cometypename = cometypename;
	}
	
	
}
