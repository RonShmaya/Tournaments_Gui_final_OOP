package Listeners;

import Model.Championship.eMsgForUser;
import Model.Game;
import Model.Person;

public interface ListenersOfModel {

	void modelUpdateViewNewParticipate(Person participate);

	void modelUpdateViewMsg(eMsgForUser msg);

	void modelUpdateViewException(String message);

	void modelUpdateViewGameUpdateParticipate(Game game);

	void modelUpdateViewKindChmp(String kindChmp);

	void modelUpdateViewExceptionInGame(String message);

	void modelUpdateViewGameSituation(eMsgForUser msg);

	void modelUpdateViewResult(String gameResult);

	void modelUpdateViewWeHaveAWinner();





}
