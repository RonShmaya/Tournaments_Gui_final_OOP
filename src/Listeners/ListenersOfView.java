package Listeners;

import java.util.List;

import Model.ControlClassModel.eChmpStatus;
import Model.Game;

public interface ListenersOfView {

	void viewUpdateModelNewParticipate(String nameOfParticipate);

	void viewUpdateModelStartChmp(String kindChmp);

	void viewCheckFromModelIfCanDoBattle(int numOfBattle, eChmpStatus round);

	Game viewGetGameFromModel(int numOfBattle, eChmpStatus round);

	void viewUpdateModelScoresGame(eChmpStatus roundChmp, int numOfGame, List<Integer> scoresF, List<Integer> scoresS);

	void viewUpdateModelScoresTie(eChmpStatus roundChmp, int numOfGame, List<Integer> scoresF, List<Integer> scoresS);

	void viewClearGameInModel(eChmpStatus roundChmp, int numOfGame);


}
