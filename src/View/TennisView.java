package View;

import java.util.ArrayList;
import java.util.List;

import Model.Championship.eMsgForUser;
import Model.ControlClassModel.eChmpStatus;
import Model.Tennis;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class TennisView extends GameView {

	public TennisView(eChmpStatus roundChmp, int numOfGame, int gameStages, String nameFirstPers, String nameSecPers,
			MainView mainView) {
		super(roundChmp, numOfGame, gameStages, nameFirstPers, nameSecPers, Tennis.class.getSimpleName(), mainView);
		isTieDisable(true);
		msgPlace.add(msg, 0, 0);
		msgPlace.add(this.gameResult, 0, 1);
		eventDone();
	}

	@Override
	protected void eventDone() {
		EventHandler<ActionEvent> pressDone = new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				try {
					List<Integer> scoresF = new ArrayList<Integer>();
					List<Integer> scoresS = new ArrayList<Integer>();
					if (isTie) {
						for (int i = Tennis.LIMIT_IS_3_GAMES; i < (Tennis.LIMIT_IS_3_GAMES
								+ Tennis.NUMBER_OF_TIE_ROUNDS); i++) {
							scoresF.add(Integer.parseInt(firstPersScores.get(i).getText()));
							scoresS.add(Integer.parseInt(secondPersScores.get(i).getText()));
						}
						mainView.viewGameSendMainViewResultTie(roundChmp, numOfGame, scoresF, scoresS);
					} else {
						for (int i = 0; i < Tennis.LIMIT_IS_3_GAMES; i++) {
							scoresF.add(Integer.parseInt(firstPersScores.get(i).getText()));
							scoresS.add(Integer.parseInt(secondPersScores.get(i).getText()));
						}
						mainView.viewGameSendMainViewResultBattle(roundChmp, numOfGame, scoresF, scoresS);
						if (msg.getText().equals(eMsgForUser.Tie.toString())) {
							msg.setText("need 5 Round for decision");
							isTieDisable(false);
							isTie = true;
						}
					}
				} catch (NumberFormatException e) {
					msg.setText("Please Add Digit Only");
				}

			}
		};
		this.done.setOnAction(pressDone);
	}

	private void isTieDisable(boolean ans) {
		for (int i = 0; i < (Tennis.LIMIT_IS_3_GAMES + Tennis.NUMBER_OF_TIE_ROUNDS); i++) {
			if (i < Tennis.LIMIT_IS_3_GAMES) {
				this.firstPersScores.get(i).setDisable(!ans);
				this.secondPersScores.get(i).setDisable(!ans);
			} else {
				this.firstPersScores.get(i).setDisable(ans);
				this.secondPersScores.get(i).setDisable(ans);
			}
		}
	}

	@Override
	protected void refreshTextField() {
		super.refreshTextField();
		for (int i = Tennis.LIMIT_IS_3_GAMES; i < (Tennis.LIMIT_IS_3_GAMES + Tennis.NUMBER_OF_TIE_ROUNDS); i++) {
			firstPersScores.get(i).setDisable(true);
			secondPersScores.get(i).setDisable(true);
		}

	}

}
