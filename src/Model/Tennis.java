package Model;

import java.util.List;
import Exceptions.GameSizeNotOkException;
import Exceptions.InputGameOutFromLimits;
import Model.Championship.eMsgForUser;
import Model.ControlClassModel.eChmpStatus;

public class Tennis extends Game {
	public static final int LIMIT_FOR_GAME_SCORE = 7;
	public static final int GAME_SCORE_STANDATRT = 6;
	public static final int LIMIT_IS_3_GAMES = 3;
	public static final int NUMBER_OF_TIE_ROUNDS = 2;

	public Tennis(Person firstPerson, Person secondPerson, eChmpStatus chmpRound, int numOfGame) {
		super(firstPerson, secondPerson, chmpRound, LIMIT_FOR_GAME_SCORE, LIMIT_IS_3_GAMES, 1, numOfGame);
	}

	public Tennis(eChmpStatus chmpRound, int numOfGame) {
		super(LIMIT_FOR_GAME_SCORE, LIMIT_IS_3_GAMES, 1, chmpRound, numOfGame);
	}

	public eMsgForUser battle() throws InputGameOutFromLimits, GameSizeNotOkException {
		if (!gameFinished && !tie && numOfParticipate == 2) {
			changeFromScoresToNumOfVictoryBattle(firstPerson.getListScores(), secondPerson.getListScores());
			return theWinner();
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

	public void changeFromScoresToNumOfVictoryBattle(List<Integer> firstPersScores, List<Integer> secondPersScores)
			throws InputGameOutFromLimits, GameSizeNotOkException {
		checkExceptionGameRoundsNotOk(firstPersScores.size(), LIMIT_IS_3_GAMES);
		checkExceptionGameRoundsNotOk(secondPersScores.size(), LIMIT_IS_3_GAMES);
		for (int i = 0; i < LIMIT_IS_3_GAMES; i++) {
			calculationOfOneRound(firstPersScores.get(i), secondPersScores.get(i));
		}
	}

	private void calculationOfOneRound(int firstPersScores, int secondPersScores) throws InputGameOutFromLimits {
		if (secondPersScores < 0 || firstPersScores < 0) {
			exceptionInputGameNotOk(firstPersScores, secondPersScores);
		}
		if (firstPersScores == GAME_SCORE_STANDATRT && firstPersScores > (secondPersScores + 1)) {
			addVictory(firstPerson);
		} else if (secondPersScores == GAME_SCORE_STANDATRT && secondPersScores > (firstPersScores + 1)) {
			addVictory(secondPerson);
		} else if (firstPersScores == LIMIT_FOR_GAME_SCORE
				&& (secondPersScores == GAME_SCORE_STANDATRT || secondPersScores == (GAME_SCORE_STANDATRT - 1))) {
			addVictory(firstPerson);
		} else if (secondPersScores == LIMIT_FOR_GAME_SCORE
				&& (firstPersScores == GAME_SCORE_STANDATRT || firstPersScores == (GAME_SCORE_STANDATRT - 1))) {
			addVictory(secondPerson);
		} else {
			firstPerson.clearScore();
			secondPerson.clearScore();
			exceptionInputGameNotOk(firstPersScores, secondPersScores);
		}

	}

	private void checkExceptionGameRoundsNotOk(int size, int limitIs3Games) throws GameSizeNotOkException {
		if (size != limitIs3Games) {
			throw new GameSizeNotOkException(limitIs3Games, size, this.getClass().getSimpleName());
		}
	}

	private void exceptionInputGameNotOk(int firstPersScores, int secondPersScores) throws InputGameOutFromLimits {
		firstPerson.clearScore();
		secondPerson.clearScore();
		throw new InputGameOutFromLimits(this.getClass().getSimpleName(), firstPersScores, secondPersScores);
	}

	private void addVictory(Person winnerRound) {
		winnerRound.updateScore(1);
	}

	protected eMsgForUser theWinner() {
		if ((firstPerson.getScore() == LIMIT_IS_3_GAMES && secondPerson.getScore() == 0)
				|| (secondPerson.getScore() == LIMIT_IS_3_GAMES && firstPerson.getScore() == 0)) {
			if (firstPerson.getScore() == LIMIT_IS_3_GAMES && secondPerson.getScore() == 0) {
				finishGame(firstPerson, secondPerson);
			} else {
				finishGame(secondPerson, firstPerson);
			}
			return eMsgForUser.SuccededDoBattle;
		} else if (tie) {
			if (firstPerson.getScore() > secondPerson.getScore()) {
				finishGame(firstPerson, secondPerson);
			} else {
				finishGame(secondPerson, firstPerson);
			}
			return eMsgForUser.SuccededDoTieSituation;
		} else {
			setGameResult(firstPerson.getScore(), secondPerson.getScore());
			this.tie = true;
			this.gameFinished = false;
			return eMsgForUser.Tie;
		}
	}

	public eMsgForUser tie(List<Integer> firstPersScores, List<Integer> secondPersScores)
			throws GameSizeNotOkException, InputGameOutFromLimits {
		if (!gameFinished && tie) {
			checkExceptionGameRoundsNotOk(firstPersScores.size(), NUMBER_OF_TIE_ROUNDS);
			checkExceptionGameRoundsNotOk(secondPersScores.size(), NUMBER_OF_TIE_ROUNDS);
			for (int i = 0; i < NUMBER_OF_TIE_ROUNDS; i++) {
				calculationOfOneRound(firstPersScores.get(i), secondPersScores.get(i));
			}
			theWinner();
			return eMsgForUser.SuccededDoTieSituation;
		} else {
			if (gameFinished) {
				return eMsgForUser.GameFinishedCannotDoTieSituation;
			}
			return eMsgForUser.GameNotInTieSituation;
		}
	}

}
