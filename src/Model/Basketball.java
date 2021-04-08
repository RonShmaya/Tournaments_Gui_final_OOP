package Model;

import Exceptions.GameSizeNotOkException;
import Exceptions.InputGameOutFromLimits;
import Exceptions.TieException;
import Model.Championship.eMsgForUser;
import Model.ControlClassModel.eChmpStatus;

public class Basketball extends Game {
	public static final int LIMIT_FOR_QUARTER = 80;
	public static final int LIMIT_IS_4_QUARTER = 4;
	public static final int LIMIT_TIE_TIMES = 2;

	public Basketball(Person firstPerson, Person secondPerson, eChmpStatus chmpRound, int numOfGame) {
		super(firstPerson, secondPerson, chmpRound, LIMIT_FOR_QUARTER, LIMIT_IS_4_QUARTER, LIMIT_TIE_TIMES, numOfGame);
	}

	public Basketball(eChmpStatus chmpRound, int numOfGame) {
		super(LIMIT_FOR_QUARTER, LIMIT_IS_4_QUARTER, LIMIT_TIE_TIMES, chmpRound, numOfGame);
	}

	public eMsgForUser battle() throws GameSizeNotOkException, InputGameOutFromLimits {
		return super.battle();
	}

	public eMsgForUser tie(int firstPersScores, int secondPersScores) throws InputGameOutFromLimits, TieException {
		return super.tie(firstPersScores, secondPersScores);
	}

}
