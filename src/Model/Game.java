package Model;

import java.util.List;

import Exceptions.GameSizeNotOkException;
import Exceptions.InputGameOutFromLimits;
import Exceptions.TieException;
import Model.Championship.eMsgForUser;
import Model.ControlClassModel.eChmpStatus;
import Model.Person.eStatus;

public abstract class Game {

	protected Person firstPerson;
	protected Person secondPerson;
	protected Person winner;
	protected eChmpStatus chmpRound;
	protected boolean tie;
	protected boolean gameFinished;
	protected String gameResult;
	protected int counterTie;
	protected int numOfParticipate;
	protected int limitNumOfStages;
	protected int limitScore;
	protected int tieLimit;
	protected int numOfGame;

	public Game(Person firstPerson, Person secondPerson, eChmpStatus chmpRound, int limitScore, int limitNumOfStages,
			int tieLimit, int numOfGame) {
		this.firstPerson = firstPerson;
		this.secondPerson = secondPerson;
		this.limitScore = limitScore;
		this.limitNumOfStages = limitNumOfStages;
		this.chmpRound = chmpRound;
		this.tieLimit = tieLimit;
		numOfParticipate = 2;
		winner = new Person();
		tie = false;
		gameFinished = false;
		this.numOfGame = numOfGame;
	}

	public Game(int limitScore, int limitNumOfStages, int tieLimit, eChmpStatus chmpRound, int numOfGame) {
		this(new Person(), new Person(), chmpRound, limitScore, limitNumOfStages, tieLimit, numOfGame);
		numOfParticipate = 0;
	}

	public void setFirstParticipat(Person first) {
		this.firstPerson = first;
		numOfParticipate++;
	}

	public void setSecondParticipat(Person sec) {
		this.secondPerson = sec;
		numOfParticipate++;
	}

	public int getNumOfParticipate() {
		return numOfParticipate;
	}

	public Person getFirstParticipat() {
		return this.firstPerson;
	}

	public Person getSecondParticipat() {
		return this.secondPerson;
	}

	public int getNumOfGame() {
		return this.numOfGame;
	}

	protected void setGameResult(int firstScore, int secondScore) {
		gameResult = "Result---> " + firstScore + ":" + secondScore;
	}

	public String getGameResult() {
		return gameResult;
	}

	public boolean isGameFinished() {
		return gameFinished;
	}

	public boolean isGameInProccess() {
		return tie;
	}

	public void setScores(List<Integer> firstPersScores, List<Integer> secondPersScores) {
		firstPerson.setListScores(firstPersScores);
		secondPerson.setListScores(secondPersScores);
	}

	protected eMsgForUser battle() throws GameSizeNotOkException, InputGameOutFromLimits {
		if (!gameFinished && !tie && numOfParticipate == 2) {
			if (firstPerson.getListScores().size() != limitNumOfStages) {
				throw new GameSizeNotOkException(limitNumOfStages, firstPerson.getListScores().size(),
						this.getClass().getSimpleName());
			}
			if (secondPerson.getListScores().size() != limitNumOfStages) {
				throw new GameSizeNotOkException(limitNumOfStages, secondPerson.getListScores().size(),
						this.getClass().getSimpleName());
			}
			participateCalculationScores(firstPerson, secondPerson);
			participateCalculationScores(secondPerson, firstPerson);
			if (firstPerson.getScore() != secondPerson.getScore()) {
				theWinner();
				return eMsgForUser.SuccededDoBattle;
			} else {
				setGameResult(firstPerson.getScore(), secondPerson.getScore());
				tie = true;
				this.counterTie++;
				gameFinished = false;
				return eMsgForUser.Tie;
			}

		} else {
			if (gameFinished) {
				return eMsgForUser.GameFinishedCannotDoBattleAgain;
			} else if (tie) {
				return eMsgForUser.GameInTieSituationCannotDoBattleAgain;
			} else {
				return eMsgForUser.NeedToAddParticipates;
			}
		}
	}

	protected void participateCalculationScores(Person persToCheck, Person secondPers) throws InputGameOutFromLimits {
		for (int i = 0; i < persToCheck.getListScores().size(); i++) {
			CalculationScores(persToCheck.getListScores().get(i), persToCheck, secondPers);
		}
	}

	protected void CalculationScores(int score, Person persToCheck, Person secondPers) throws InputGameOutFromLimits {
		if (score > limitScore || score < 0) {
			persToCheck.clearScore();
			secondPers.clearScore();
			throw new InputGameOutFromLimits(score, this.getClass().getSimpleName());
		} else {
			persToCheck.updateScore(score);
		}
	}

	private void theWinner() {
		if (firstPerson.getScore() > secondPerson.getScore()) {
			finishGame(firstPerson, secondPerson);
		} else {
			finishGame(secondPerson, firstPerson);
		}
	}

	protected void finishGame(Person winner, Person loser) {
		setGameResult(firstPerson.getScore(), secondPerson.getScore());
		this.winner = winner;
		this.winner.statusParticipate(eStatus.waitForBattle);
		loser.statusParticipate(eStatus.outFromChampionship);
		tie = false;
		gameFinished = true;

	}

	protected eMsgForUser tie(int firstPersScores, int secondPersScores) throws InputGameOutFromLimits, TieException {
		if (!gameFinished && tie) {
			CalculationScores(firstPersScores, firstPerson, secondPerson);
			CalculationScores(secondPersScores, secondPerson, firstPerson);
			if (firstPerson.getScore() != secondPerson.getScore()) {
				theWinner();
				return eMsgForUser.SuccededDoTieSituation;
			} else {
				if (counterTie >= tieLimit) {
					throw new TieException();
				}
				setGameResult(firstPerson.getScore(), secondPerson.getScore());
				counterTie++;
				tie = true;
				gameFinished = false;
				return eMsgForUser.Tie;
			}
		} else {
			if (gameFinished) {
				return eMsgForUser.GameFinishedCannotDoTieSituation;
			} else {
				return eMsgForUser.GameNotInTieSituation;
			}
		}

	}

	public Person getWinner() {
		if (gameFinished) {
			return this.winner;
		}
		return null;
	}

	public int getCounterTie() {
		return counterTie;
	}

	public eChmpStatus getStatusRound() {
		return chmpRound;
	}
}
