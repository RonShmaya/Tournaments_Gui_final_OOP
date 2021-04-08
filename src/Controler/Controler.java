package Controler;

import java.util.List;

import Listeners.ListenersOfModel;
import Listeners.ListenersOfView;
import Model.Championship.eMsgForUser;
import Model.ControlClassModel;
import Model.ControlClassModel.eChmpStatus;
import Model.Game;
import Model.Person;
import View.MainView;

public class Controler implements ListenersOfView, ListenersOfModel {

	private MainView view;
	private ControlClassModel model;

	public Controler(MainView view, ControlClassModel model) {
		this.view = view;
		this.model = model;
		this.model.addListener(this);
		this.view.addListener(this);
	}

	// model update view
	@Override
	public void modelUpdateViewNewParticipate(Person participate) {
		view.updateParticipate(participate);

	}

	@Override
	public void modelUpdateViewMsg(eMsgForUser msg) {
		view.updateMsg(msg);

	}

	@Override
	public void modelUpdateViewException(String message) {
		view.updateExceptionMsg(message);

	}

	@Override
	public void modelUpdateViewKindChmp(String kindChmp) {
		view.addKindOfChmp(kindChmp);

	}

	@Override
	public void modelUpdateViewGameUpdateParticipate(Game game) {
		view.updateParticipateNextLevel(game);

	}

	@Override
	public void modelUpdateViewExceptionInGame(String message) {
		view.mainViewUpdateGameViewMsgException(message);

	}

	@Override
	public void modelUpdateViewGameSituation(eMsgForUser msg) {
		view.mainViewUpdateGameViewGameSituation(msg);

	}

	@Override
	public void modelUpdateViewResult(String gameResult) {
		view.updateGameViewResultGame(gameResult);

	}

	@Override
	public void modelUpdateViewWeHaveAWinner() {
		view.weHaveAWinner();
	}

	// view update model
	@Override
	public void viewUpdateModelNewParticipate(String nameOfParticipate) {
		model.addParticipate(nameOfParticipate);
	}

	@Override
	public void viewUpdateModelStartChmp(String kindChmp) {
		model.startChmp(kindChmp);

	}

	@Override
	public void viewCheckFromModelIfCanDoBattle(int numOfBattle, eChmpStatus round) {
		model.checkIfCanDoBattle(numOfBattle, round);

	}

	@Override
	public Game viewGetGameFromModel(int numOfBattle, eChmpStatus round) {
		return model.getGame(numOfBattle, round);
	}

	@Override
	public void viewUpdateModelScoresGame(eChmpStatus roundChmp, int numOfGame, List<Integer> scoresF,
			List<Integer> scoresS) {
		model.Battle(numOfGame, roundChmp, scoresF, scoresS);
	}

	@Override
	public void viewUpdateModelScoresTie(eChmpStatus roundChmp, int numOfGame, List<Integer> scoresF,
			List<Integer> scoresS) {
		model.tie(numOfGame, roundChmp, scoresF, scoresS);
	}

	@Override
	public void viewClearGameInModel(eChmpStatus roundChmp, int numOfGame) {
		model.clearGame(roundChmp, numOfGame);

	}

}
