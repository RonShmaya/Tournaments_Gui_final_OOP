package Model;

import java.util.ArrayList;
import java.util.List;

import Exceptions.NameException;

public class Person implements Participate {
	public static enum eStatus {
		waitForBattle, outFromChampionship
	};

	private String name;
	private List<Integer> listScores;
	private int sumScore;
	private eStatus status;

	public Person(String name) throws NameException {
		setName(name);
		this.status = eStatus.waitForBattle;
		listScores = new ArrayList<Integer>();
		sumScore = 0;
	}

	public Person() {

	}

	private void setName(String name) throws NameException {
		if (name.isEmpty() || name == null) {
			throw new NameException();
		}
		boolean isWithoutDigit = isWithoutDigit(name);
		if (!isWithoutDigit) {
			throw new NameException(name);
		}
		this.name = name;
	}

	private boolean isWithoutDigit(String name) {
		for (int i = 0; i < name.length(); i++) {
			if (Character.isDigit(name.charAt(i))) {
				return false;
			}
		}
		return true;
	}

	public String getName() {
		return this.name;
	}

	@Override
	public void setListScores(List<Integer> scores) {
		listScores = scores;

	}

	@Override
	public List<Integer> getListScores() {
		return listScores;
	}

	@Override
	public void statusParticipate(eStatus ans) {
		if (ans == eStatus.waitForBattle) {
			sumScore = 0;
			listScores.clear();
		}
		this.status = ans;
	}

	@Override
	public eStatus getStatusParticipate() {
		return this.status;
	}

	@Override
	public String toString() {
		return this.name;
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Person)) {
			return false;
		}
		if (!(this.name.equals(((Person) obj).name))) {
			return false;
		}
		return true;
	}

	public void updateScore(int score) {
		this.sumScore = this.sumScore + score;
	}

	public void clearScore() {
		this.sumScore = 0;
	}

	public int getScore() {
		return this.sumScore;
	}
}
