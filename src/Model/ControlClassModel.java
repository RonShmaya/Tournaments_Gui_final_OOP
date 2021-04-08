package Model;

import java.util.List;

import Exceptions.GameSizeNotOkException;
import Exceptions.InputGameOutFromLimits;
import Exceptions.NameException;
import Exceptions.TieException;
import Listeners.ListenersOfModel;
import Model.Championship.eMsgForUser;

public class ControlClassModel {
	public static enum eChmpStatus {
		firstRound, secondRound, finalRound
	};

	private Championship chmp;
	private ListenersOfModel listener;
	boolean isTheWinner;

	public ControlClassModel() {
		chmp = new Championship();
	}

	public void addListener(ListenersOfModel listener) {
		this.listener = listener;
	}

	public void addParticipate(String nameParticipate) {
		try {
			Person participate = new Person(nameParticipate);
			eMsgForUser msg = chmp.addParticipate(participate);
			if (msg == eMsgForUser.Succeded) {
				listener.modelUpdateViewNewParticipate(participate);
			}
			listener.modelUpdateViewMsg(msg);
		} catch (NameException e) {
			listener.modelUpdateViewException(e.getMessage());
		}
	}

	public void startChmp(String KindChmp) {
		eMsgForUser msg = chmp.startChamp(KindChmp);
		listener.modelUpdateViewMsg(msg);
		if (msg == eMsgForUser.Succeded) {
			listener.modelUpdateViewKindChmp(KindChmp);
		}
	}

	public void checkIfCanDoBattle(int numOfBattel, eChmpStatus chmpStat) {
		listener.modelUpdateViewMsg(chmp.checkGameSituation(numOfBattel, chmpStat));
	}

	public void Battle(int numOfBattel, eChmpStatus round, List<Integer> firstPersScores,
			List<Integer> secondPersScores) {
		try {
			eMsgForUser msg;
			if (round == eChmpStatus.firstRound) {
				msg = chmp.firstRoundBattle(numOfBattel, firstPersScores, secondPersScores);
			} else if (round == eChmpStatus.secondRound) {
				msg = chmp.secondRoundBattle(numOfBattel, firstPersScores, secondPersScores);
			} else {
				msg = chmp.finalBattle(firstPersScores, secondPersScores);
				isTheWinner = true;
			}
			if (msg == eMsgForUser.SuccededDoBattle) {
				listener.modelUpdateViewResult(chmp.getGame(numOfBattel, round).getGameResult());
				listener.modelUpdateViewGameUpdateParticipate(chmp.getGame(numOfBattel, round));
				if (isTheWinner) {
					listener.modelUpdateViewWeHaveAWinner();
				}
			}
			if (msg == eMsgForUser.Tie) {
				listener.modelUpdateViewResult(chmp.getGame(numOfBattel, round).getGameResult());
			}
			listener.modelUpdateViewGameSituation(msg);
		} catch (InputGameOutFromLimits e) {
			listener.modelUpdateViewExceptionInGame(e.getMessage());
		} catch (GameSizeNotOkException e) {
			listener.modelUpdateViewExceptionInGame(e.getMessage());
		}
	}

	public void tie(int numOfBattel, eChmpStatus round, List<Integer> firstPersScores, List<Integer> secondPersScores) {
		try {
			eMsgForUser msg;
			if (round == eChmpStatus.firstRound) {
				msg = chmp.firstRoundTie(numOfBattel, firstPersScores, secondPersScores);
			} else if (round == eChmpStatus.secondRound) {
				msg = chmp.secondRoundTie(numOfBattel, firstPersScores, secondPersScores);
			} else {
				msg = chmp.finalTie(firstPersScores, secondPersScores);
				isTheWinner = true;
			}
			if (msg == eMsgForUser.SuccededDoTieSituation) {
				listener.modelUpdateViewResult(chmp.getGame(numOfBattel, round).getGameResult());
				listener.modelUpdateViewGameUpdateParticipate(chmp.getGame(numOfBattel, round));
				if (isTheWinner) {
					listener.modelUpdateViewWeHaveAWinner();
				}
			}
			if (msg == eMsgForUser.Tie) {
				listener.modelUpdateViewResult(chmp.getGame(numOfBattel, round).getGameResult());
			}
			listener.modelUpdateViewGameSituation(msg);
		} catch (InputGameOutFromLimits e) {
			listener.modelUpdateViewExceptionInGame(e.getMessage());
		} catch (GameSizeNotOkException e) {
			listener.modelUpdateViewExceptionInGame(e.getMessage());
		} catch (TieException e) {
			listener.modelUpdateViewExceptionInGame(e.getMessage());
		}
	}

	public Game getGame(int numOfBattle, eChmpStatus round) {
		return chmp.getGame(numOfBattle, round);
	}

	public void clearGame(eChmpStatus roundChmp, int numOfGame) {
		chmp.clearGame(numOfGame, roundChmp);

	}

}
