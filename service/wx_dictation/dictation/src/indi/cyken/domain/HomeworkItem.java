package indi.cyken.domain;

/**
 * 作业项实体
 * @author Yong
 *
 */
public class HomeworkItem {
	
	private Homework homework;	//属于哪个作业
	private Word	  word;		//包含哪个单词
	public Homework getHomework() {
		return homework;
	}
	public void setHomework(Homework homework) {
		this.homework = homework;
	}
	public Word getWord() {
		return word;
	}
	public void setWord(Word word) {
		this.word = word;
	}
	
	

}
