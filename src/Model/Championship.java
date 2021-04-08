package Model;

import java.util.ArrayList;
import java.util.List;

import Exceptions.GameSizeNotOkException;
import Exceptions.InputGameOutFromLimits;
import Exceptions.TieException;
import Model.ControlClassModel.eChmpStatus;

public class Championship {
	public static enum eMsgForUser {
		GameFinishedCannotDoBattleAgain, GameInTieSituationCannotDoBattleAgain, SuccededDoBattle,
		SuccededDoTieSituation, GameNotInTieSituation, GameFinishedCannotDoTieSituation, theParticipateAlreadyInList,
		numberOfParticipateFull, Succeded, numberOfParticipateMustBe8, numOfBattleNotExsist, battleFinished,
		theGameStartAlready, theChmapIsNotInThisStage, Tie, NeedToAddParticipates, ChmpStartAlready, kindChampNotExsist
	};

	public static final int NUM_OF_PARTICIPATE = 8;
	public static final int NUM_OF_SECOND_GAMES = 2;
	public static final int NUM_OF_FINAL_GAMES = 1;
	public static final int NUM_OF_FIRST_ROUND_GAMES = 4;

	private List<Person> participates;
	private List<Game> firstRound;
	private List<Game> secondRound;
	private Game finalRound;
	private Person theWinnerChmp;
	private boolean isStartChmp;

	public Championship() {
		participates = new ArrayList<Person>();
		firstRound = new ArrayList<Game>();
		secondRound = new ArrayList<Game>();
		theWinnerChmp = new Person();
	}

	public eMsgForUser addParticipate(Person participate) {
		if (participates.size() >= NUM_OF_PARTICIPATE) {
			return eMsgForUser.numberOfParticipateFull;
		}
		if (participates.contains(participate)) {
			return eMsgForUser.theParticipateAlreadyInList;
		}
		participates.add(participate);
		return eMsgForUser.Succeded;
	}

	public eMsgForUser startChamp(String kindChmp) {
		if (isStartChmp) {
			return eMsgForUser.ChmpStartAlready;
		} else {
			if (participates.size() != NUM_OF_PARTICIPATE) {
				return eMsgForUser.numberOfParticipateMustBe8;
			}
			if (!kindChmp.equals(Tennis.class.getSimpleName()) && !kindChmp.equals(Soccer.class.getSimpleName())
					&& !kindChmp.equals(Basketball.class.getSimpleName())) {
				return eMsgForUser.kindChampNotExsist;
			}
			ChampKind(kindChmp);
			isStartChmp = true;
			return eMsgForUser.Succeded;
		}
	}

	private void ChampKind(String kindChmp) {
		int countNumOfGame = 0;
		if (kindChmp.equals(Tennis.class.getSimpleName())) {
			for (int i = 0; i < NUM_OF_PARTICIPATE; i = i + 2) {
				firstRound.add(new Tennis(participates.get(i), participates.get(1 + i), eChmpStatus.firstRound,
						countNumOfGame));
				countNumOfGame++;
			}
			for (int i = 0; i < NUM_OF_SECOND_GAMES; i++) {
				secondRound.add(new Tennis(eChmpStatus.secondRound, i));
			}
			finalRound = new Tennis(eChmpStatus.finalRound, 0);
		} else if (kindChmp.equals(Soccer.class.getSimpleName())) {
			for (int i = 0; i < NUM_OF_PARTICIPATE; i = i + 2) {
				firstRound.add(new Soccer(participates.get(i), participates.get(1 + i), eChmpStatus.firstRound,
						countNumOfGame));
				countNumOfGame++;
			}
			for (int i = 0; i < NUM_OF_SECOND_GAMES; i++) {
				secondRound.add(new Soccer(eChmpStatus.secondRound, i));
			}
			finalRound = new Soccer(eChmpStatus.finalRound, 0);
		} else {
			for (int i = 0; i < NUM_OF_PARTICIPATE; i = i + 2) {
				firstRound.add(new Basketball(participates.get(i), participates.get(1 + i), eChmpStatus.firstRound,
						countNumOfGame));
				countNumOfGame++;
			}
			for (int i = 0; i < NUM_OF_SECOND_GAMES; i++) {
				secondRound.add(new Basketball(eChmpStatus.secondRound, i));
			}
			finalRound = new Basketball(eChmpStatus.finalRound, 0);
		}
	}

	public eMsgForUser checkGameSituation(int numOfBattle, eChmpStatus round) {
		if (round == eChmpStatus.firstRound) {
			return checkIfCanBattle(numOfBattle, NUM_OF_FIRST_ROUND_GAMES, firstRound.get(numOfBattle));
		} else if (round == eChmpStatus.secondRound) {
			return checkIfCanBattle(numOfBattle, NUM_OF_SECOND_GAMES, secondRound.get(numOfBattle));
		} else {
			return checkIfCanBattle(numOfBattle, NUM_OF_FINAL_GAMES, finalRound);
		}
	}

	public eMsgForUser firstRoundBattle(int numOfBattel, List<Integer> firstPersScores, List<Integer> secondPersScores)
			throws InputGameOutFromLimits, GameSizeNotOkException {
		return roundBattle(numOfBattel, firstPersScores, secondPersScores, firstRound, NUM_OF_FIRST_ROUND_GAMES);
	}

	public eMsgForUser firstRoundTie(int numOfBattel, List<Integer> firstScore, List<Integer> secondScore)
			throws GameSizeNotOkException, InputGameOutFromLimits, TieException {
		return tieBattle(numOfBattel, firstScore, secondScore, firstRound, NUM_OF_FIRST_ROUND_GAMES);
	}

	public eMsgForUser secondRoundBattle(int numOfBattel, List<Integer> firstPersScores, List<Integer> secondPersScores)
			throws InputGameOutFromLimits, GameSizeNotOkException {
		return roundBattle(numOfBattel, firstPersScores, secondPersScores, secondRound, NUM_OF_SECOND_GAMES);
	}

	public eMsgForUser secondRoundTie(int numOfBattel, List<Integer> firstScore, List<Integer> secondScore)
			throws GameSizeNotOkException, InputGameOutFromLimits, TieException {
		return tieBattle(numOfBattel, firstScore, secondScore, secondRound, NUM_OF_SECOND_GAMES);
	}

	private <T extends Game> eMsgForUser roundBattle(int numOfBattel, List<Integer> firstPersScores,
			List<Integer> secondPersScores, List<T> gamesRound, int numOfGamesInRound)
			throws InputGameOutFromLimits, GameSizeNotOkException {
		eMsgForUser msg = checkIfCanBattle(numOfBattel, numOfGamesInRound, gamesRound.get(numOfBattel));
		if (msg != eMsgForUser.Succeded) {
			return msg;
		}
		gamesRound.get(numOfBattel).setScores(firstPersScores, secondPersScores);
		msg = gamesRound.get(numOfBattel).battle();
		if (msg == eMsgForUser.SuccededDoBattle) {
			checkWichGameToAdd(gamesRound.get(numOfBattel).getWinner(), numOfBattel, numOfGamesInRound);
		}
		return msg;
	}

	private void checkWichGameToAdd(Person winner, int indexGame, int sizeGamesRound) {
		if (sizeGamesRound == NUM_OF_FIRST_ROUND_GAMES) {
			if (indexGame < sizeGamesRound / 2) {
				if (indexGame % 2 == 0) {
					addToNextGame(winner, 0, true);
				} else {
					addToNextGame(winner, 0, false);
				}
			} else {
				if (indexGame % 2 == 0) {
					addToNextGame(winner, 1, true);
				} else {
					addToNextGame(winner, 1, false);
				}
			}
		} else {
			if (indexGame % 2 == 0) {
				addToFinal(winner, true);
			} else {
				addToFinal(winner, false);
			}
		}
	}

	private void addToFinal(Person winner, boolean isFirstInNextGame) {
		if (isFirstInNextGame) {
			finalRound.setFirstParticipat(winner);
		} else {
			finalRound.setSecondParticipat(winner);
		}

	}

	private void addToNextGame(Person winner, int indexNextGame, boolean isFirstInNextGame) {
		if (isFirstInNextGame) {
			secondRound.get(indexNextGame).setFirstParticipat(winner);

		} else {
			secondRound.get(indexNextGame).setSecondParticipat(winner);
		}
	}

	private <T extends Game> eMsgForUser tieBattle(int numOfBattel, List<Integer> firstScore, List<Integer> secondScore,
			List<T> gamesRound, int numOfGamesInRound)
			throws GameSizeNotOkException, InputGameOutFromLimits, TieException {
		eMsgForUser msg = checkIfCanDoTie(numOfBattel, numOfGamesInRound, gamesRound.get(numOfBattel));
		if (msg != eMsgForUser.Succeded) {
			return msg;
		}
		if (finalRound instanceof Soccer) {
			if (gamesRound.get(numOfBattel).getCounterTie() == 1) {
				msg = gamesRound.get(numOfBattel).tie(firstScore.get(0), secondScore.get(0));
			} else {
				msg = ((Soccer) gamesRound.get(numOfBattel)).kicks(firstScore.get(0), secondScore.get(0));
			}
		} else if (finalRound instanceof Basketball) {
			msg = gamesRound.get(numOfBattel).tie(firstScore.get(0), secondScore.get(0));
		} else {
			msg = ((Tennis) gamesRound.get(numOfBattel)).tie(firstScore, secondScore);
		}
		if (msg == eMsgForUser.SuccededDoTieSituation) {
			checkWichGameToAdd(gamesRound.get(numOfBattel).getWinner(), numOfBattel, numOfGamesInRound);
		}
		return msg;

	}

	private eMsgForUser checkIfCanBattle(int numOfBattel, int limit, Game game) {
		eMsgForUser msg = checkIfCanDo(numOfBattel, limit, game);
		if (msg != eMsgForUser.Succeded) {
			return msg;
		}
		if (game.isGameInProccess()) {
			return eMsgForUser.theGameStartAlready;
		}
		if (game.getNumOfParticipate() != 2) {
			return eMsgForUser.NeedToAddParticipates;
		}
		return eMsgForUser.Succeded;
	}

	private eMsgForUser checkIfCanDoTie(int numOfBattel, int limit, Game game) {
		eMsgForUser msg = checkIfCanDo(numOfBattel, limit, game);
		if (msg != eMsgForUser.Succeded) {
			return msg;
		}
		if (!game.isGameInProccess()) {
			return eMsgForUser.GameNotInTieSituation;
		}
		return eMsgForUser.Succeded;
	}

	private eMsgForUser checkIfCanDo(int numOfBattel, int limit, Game game) {
		if (!isStartChmp) {
			return eMsgForUser.theChmapIsNotInThisStage;
		}
		if (numOfBattel >= limit) {
			return eMsgForUser.numOfBattleNotExsist;
		}
		if (game.isGameFinished()) {
			return eMsgForUser.battleFinished;
		}
		return eMsgForUser.Succeded;
	}

	public eMsgForUser finalBattle(List<Integer> firstScore, List<Integer> secondScore)
			throws InputGameOutFromLimits, GameSizeNotOkException {
		eMsgForUser msg = checkIfCanBattle(0, NUM_OF_FINAL_GAMES, finalRound);
		if (msg != eMsgForUser.Succeded) {
			return msg;
		} else {
			finalRound.setScores(firstScore, secondScore);
			msg = finalRound.battle();
			if (msg == eMsgForUser.SuccededDoBattle) {
				setWinnerChamp(finalRound.getWinner());
			}
			return msg;
		}
	}

	public eMsgForUser finalTie(List<Integer> firstScore, List<Integer> secondScore)
			throws GameSizeNotOkException, InputGameOutFromLimits, TieException {
		eMsgForUser msg = checkIfCanDoTie(0, NUM_OF_FINAL_GAMES, finalRound);
		if (msg != eMsgForUser.Succeded) {
			return msg;
		}
		if (finalRound instanceof Soccer) {
			if (finalRound.getCounterTie() == 1) {
				msg = finalRound.tie(firstScore.get(0), secondScore.get(0));
			} else {
				msg = ((Soccer) finalRound).kicks(firstScore.get(0), secondScore.get(0));
			}
		} else {
			msg = finalRound.tie(firstScore.get(0), secondScore.get(0));
		}
		if (msg == eMsgForUser.SuccededDoTieSituation) {
			setWinnerChamp(finalRound.getWinner());
		}
		return msg;
	}

	private void setWinnerChamp(Person winner) {
		theWinnerChmp = winner;
	}

	public Person getWinnerChmp() {
		return theWinnerChmp;
	}

	public Game getGame(int battleNum, eChmpStatus round) {
		if (round == eChmpStatus.firstRound) {
			if (battleNum < firstRound.size()) {
				return firstRound.get(battleNum);
			}
		} else if (round == eChmpStatus.secondRound) {
			if (battleNum < secondRound.size()) {
				return secondRound.get(battleNum);
			}
		} else {
			return finalRound;
		}
		return null;
	}

	public void clearGame(int numOfGame, eChmpStatus roundChmp) {
		if (roundChmp == eChmpStatus.firstRound) {
			helpClearGame(firstRound, roundChmp, numOfGame);
		} else if (roundChmp == eChmpStatus.secondRound) {
			helpClearGame(secondRound, roundChmp, numOfGame);
		} else {
			helpClearFinalGame(roundChmp);
		}
	}

	private void helpClearGame(List<Game> game, eChmpStatus roundChmp, int numOfGame) {
		game.get(numOfGame).getFirstParticipat().clearScore();
		game.get(numOfGame).getSecondParticipat().clearScore();
		if (finalRound instanceof Basketball) {
			game.set(numOfGame, new Basketball(game.get(numOfGame).getFirstParticipat(),
					game.get(numOfGame).getSecondParticipat(), roundChmp, numOfGame));
		} else if (finalRound instanceof Soccer) {
			game.set(numOfGame, new Soccer(game.get(numOfGame).getFirstParticipat(),
					game.get(numOfGame).getSecondParticipat(), roundChmp, numOfGame));
		} else {
			game.set(numOfGame, new Tennis(game.get(numOfGame).getFirstParticipat(),
					game.get(numOfGame).getSecondParticipat(), roundChmp, numOfGame));
		}
	}

	private void helpClearFinalGame(eChmpStatus roundChmp) {
		finalRound.getFirstParticipat().clearScore();
		finalRound.getSecondParticipat().clearScore();
		if (finalRound instanceof Basketball) {
			finalRound = new Basketball(finalRound.getFirstParticipat(), finalRound.getSecondParticipat(), roundChmp,
					0);
		} else if (finalRound instanceof Soccer) {
			finalRound = new Soccer(finalRound.getFirstParticipat(), finalRound.getSecondParticipat(), roundChmp, 0);
		} else {
			finalRound = new Tennis(finalRound.getFirstParticipat(), finalRound.getSecondParticipat(), roundChmp, 0);
		}

	}

}
