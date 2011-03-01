package questions;

public class Question {
	
	private String question;
	private String[] choices;
	private int answer;
	private int points;
	
	public Question(String question, String[] choices, int answer, int points) {
		this.question = question;
		this.choices = choices;
		this.answer = answer;
		this.points = points;
	}
	
	public int getPoints() {
		return this.points;
	}
	
	public String[] getChoices() {
		return this.choices;
	}
	
	public String getQuestion() {
		return this.question;
	}
	
	public boolean isOk(int indice) {
		return choices[indice].equals(answer);
	}

}
