package Model;

import Exceptions.GameSizeNotOkException;
import Exceptions.InputGameOutFromLimits;
import Exceptions.TieException;
import Model.Championship.eMsgForUser;
import Model.ControlClassModel.eChmpStatus;

public class Soccer extends Game {
	public static final int LIMIT_FOR_HALF = 15;
	public static final int LIMIT_IS_2_HALF = 2;
	public static final int LIMIT_TIE_TIMES = 2;
	public static final int LIMIT_KICKS = 5;
	private boolean isKickSituation;

	public Soccer(Person firstPerson, Person secondPerson, eChmpStatus chmpRound, int numOfGame) {
		super(firstPerson, secondPerson, chmpRound, LIMIT_FOR_HALF, LIMIT_IS_2_HALF, LIMIT_TIE_TIMES, numOfGame);
	}

	public Soccer(eChmpStatus chmpRound, int numOfGame) {
		super(LIMIT_FOR_HALF, LIMIT_IS_2_HALF, LIMIT_TIE_TIMES, chmpRound, numOfGame);
	}

	public eMsgForUser battle() throws GameSizeNotOkException, InputGameOutFromLimits {
		return super.battle();
	}

	public eMsgForUser tie(int firstPersScores, int secondPersScores) throws InputGameOutFromLimits, TieException {
		return super.tie(firstPersScores, secondPersScores);
	}

	public eMsgForUser kicks(int firstPersScores, int secondPersScores) throws InputGameOutFromLimits, TieException {
		if (!gameFinished && tie) {
			if ((firstPersScores == LIMIT_KICKS && secondPersScores < LIMIT_KICKS)
					|| (secondPersScores == LIMIT_KICKS && firstPersScores < LIMIT_KICKS)
					|| (firstPersScores > LIMIT_KICKS && secondPersScores + 1 == firstPersScores)
					|| (secondPersScores > LIMIT_KICKS && firstPersScores + 1 == secondPersScores)) {
				if (firstPersScores > secondPersScores) {
					finishGame(firstPerson, secondPerson);
				} else {
					finishGame(secondPerson, firstPerson);
				}
				isKickSituation = true;
				setGameResult(firstPersScores, secondPersScores);
			} else {
				throw new InputGameOutFromLimits(Soccer.class.getSimpleName(), firstPersScores, secondPersScores,
						"Pendalty");
			}
			return eMsgForUser.SuccededDoTieSituation;
		} else {
			if (gameFinished) {
				return eMsgForUser.GameFinishedCannotDoTieSituation;
			}
			return eMsgForUser.GameNotInTieSituation;
		}
	}

	@Override
	protected void setGameResult(int firstScore, int secondScore) {
		if (isKickSituation) {
			gameResult = gameResult + "(" + firstScore + ":" + secondScore + ")";
		} else {
			super.setGameResult(firstScore, secondScore);
		}
	}

}
