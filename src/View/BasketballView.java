package View;

import java.util.ArrayList;
import java.util.List;

import Model.Basketball;
import Model.Championship.eMsgForUser;
import Model.ControlClassModel.eChmpStatus;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.TextField;

public class BasketballView extends GameView {
	private List<TextField> firstPersTieScore;
	private List<TextField> secondPersTieScore;
	private int counterTie;

	public BasketballView(eChmpStatus roundChmp, int numOfGame, int gameStages, String nameFirstPers,
			String nameSecPers, MainView mainView) {
		super(roundChmp, numOfGame, gameStages, nameFirstPers, nameSecPers, Basketball.class.getSimpleName(), mainView);
		firstPersTieScore = new ArrayList<TextField>();
		secondPersTieScore = new ArrayList<TextField>();
		msgPlace.add(msg, 0, 0);
		msgPlace.add(this.gameResult, 0, 1);
		startShow();
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
						scoresF.add(Integer.parseInt(firstPersTieScore.get(counterTie).getText()));
						scoresS.add(Integer.parseInt(secondPersTieScore.get(counterTie).getText()));
						counterTie++;
						mainView.viewGameSendMainViewResultTie(roundChmp, numOfGame, scoresF, scoresS);
					} else {
						for (int i = 0; i < Basketball.LIMIT_IS_4_QUARTER; i++) {
							scoresF.add(Integer.parseInt(firstPersScores.get(i).getText()));
							scoresS.add(Integer.parseInt(secondPersScores.get(i).getText()));
						}
						mainView.viewGameSendMainViewResultBattle(roundChmp, numOfGame, scoresF, scoresS);
					}
					if (msg.getText().equals(eMsgForUser.Tie.toString())) {
						tieShow();
						isTie = true;
					} else if (msg.getText().equals(eMsgForUser.SuccededDoTieSituation.toString())) {
						counterTie--;
					}
				} catch (NumberFormatException e) {
					msg.setText("Please Add Digit Only");
				}

			}
		};
		this.done.setOnAction(pressDone);

	}

	private void startShow() {
		for (int i = 0; i < Basketball.LIMIT_TIE_TIMES; i++) {
			firstPersTieScore.add(new TextField());
			secondPersTieScore.add(new TextField());
			firstPersTieScore.get(i).setMaxSize(50, 50);
			secondPersTieScore.get(i).setMaxSize(50, 50);
			firstPersTieScore.get(i).setVisible(false);
			secondPersTieScore.get(i).setVisible(false);
			detailsOrder.add(firstPersTieScore.get(i), i + Basketball.LIMIT_IS_4_QUARTER + 1, 0);
			detailsOrder.add(secondPersTieScore.get(i), i + Basketball.LIMIT_IS_4_QUARTER + 1, 1);
		}
	}

	private void tieShow() {
		for (int i = 0; i < Basketball.LIMIT_IS_4_QUARTER; i++) {
			firstPersScores.get(i).setDisable(true);
			secondPersScores.get(i).setDisable(true);
		}
		if (counterTie != 0) {
			firstPersTieScore.get(0).setDisable(true);
			secondPersTieScore.get(0).setDisable(true);
		}
		firstPersTieScore.get(counterTie).setVisible(true);
		secondPersTieScore.get(counterTie).setVisible(true);

	}

	@Override
	protected void refreshTextField() {
		super.refreshTextField();
		for (int i = 0; i < Basketball.LIMIT_TIE_TIMES; i++) {
			firstPersTieScore.get(i).setText("");
			secondPersTieScore.get(i).setText("");
			firstPersTieScore.get(i).setVisible(false);
			secondPersTieScore.get(i).setVisible(false);
			firstPersTieScore.get(i).setDisable(false);
			secondPersTieScore.get(i).setDisable(false);
		}
		counterTie = 0;
	}
}
