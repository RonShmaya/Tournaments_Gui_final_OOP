package View;

import java.util.ArrayList;
import java.util.List;

import Model.Championship.eMsgForUser;
import Model.ControlClassModel.eChmpStatus;
import Model.Game;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public abstract class GameView {
	protected VBox mainOrder;
	protected GridPane detailsOrder;
	protected Label title;
	protected List<TextField> firstPersScores;
	protected List<TextField> secondPersScores;
	protected Label firstPersName;
	protected Label secondPersName;
	protected Button done;
	protected int numOfGame;
	protected int gameStage;
	protected eChmpStatus roundChmp;
	protected GridPane msgPlace;
	protected Label msg;
	protected Label gameResult;
	protected MainView mainView;
	protected boolean isTie;

	public GameView(eChmpStatus roundChmp, int numOfGame, int gameStages, String nameFirstPers, String nameSecPers,
			String title, MainView mainView) {
		this.mainView = mainView;
		msg = new Label();
		gameResult = new Label();
		mainOrder = new VBox();
		msgPlace = new GridPane();
		detailsOrder = new GridPane();
		detailsOrder.setHgap(10);
		detailsOrder.setVgap(10);
		detailsOrder.setAlignment(Pos.CENTER);
		this.title = new Label(title);
		this.title.setFont(new Font(45));
		firstPersScores = new ArrayList<TextField>();
		secondPersScores = new ArrayList<TextField>();
		firstPersName = new Label(nameFirstPers);
		secondPersName = new Label(nameSecPers);
		this.roundChmp = roundChmp;
		this.gameStage = gameStages;
		this.numOfGame = numOfGame;
		addDetails();
		done = new Button("done");
		mainOrder.getChildren().addAll(this.title, detailsOrder, msgPlace, done);
		mainOrder.setPadding(new Insets(10));
		mainOrder.setSpacing(50);
		mainOrder.setAlignment(Pos.TOP_CENTER);
		Stage stage = new Stage();
		outFromWindow(stage);
		stage.setScene(new Scene(mainOrder, 425, 400));
		stage.show();
	}

	private void addDetails() {
		detailsOrder.add(firstPersName, 0, 0);
		detailsOrder.add(secondPersName, 0, 1);
		for (int i = 0; i < gameStage; i++) {
			firstPersScores.add(new TextField());
			secondPersScores.add(new TextField());
			firstPersScores.get(i).setMaxSize(50, 50);
			secondPersScores.get(i).setMaxSize(50, 50);
			detailsOrder.add(firstPersScores.get(i), i + 1, 0);
			detailsOrder.add(secondPersScores.get(i), i + 1, 1);
		}
	}

	public void updateMsgInGame(eMsgForUser msg) {
		if (msg == eMsgForUser.Succeded || msg == eMsgForUser.SuccededDoBattle
				|| msg == eMsgForUser.SuccededDoTieSituation) {
			this.msg.setTextFill(Color.GREEN);
		} else if (msg == eMsgForUser.Tie) {
			this.msg.setTextFill(Color.ORANGE);
		} else {
			this.msg.setTextFill(Color.RED);
		}
		this.msg.setText(msg.toString());
	}

	public void setResultGame(String ans) {
		this.gameResult.setText(ans);
	}

	public void updateMsgInGameException(String message) {
		this.msg.setText(message);
		this.msg.setTextFill(Color.RED);
		mainView.clearGame(roundChmp, numOfGame);
		refreshTextField();
	}

	protected void refreshTextField() {
		for (int i = 0; i < gameStage; i++) {
			firstPersScores.get(i).setText("");
			secondPersScores.get(i).setText("");
			firstPersScores.get(i).setDisable(false);
			secondPersScores.get(i).setDisable(false);
		}
		this.gameResult.setText("");
		isTie = false;
	}

	private void outFromWindow(Stage stage) {
		EventHandler<WindowEvent> closeWindow = new EventHandler<WindowEvent>() {

			@Override
			public void handle(WindowEvent event) {
				Game game = mainView.viewSendGameViewGame(roundChmp, numOfGame);
				if (!game.isGameFinished()) {
					mainView.clearGame(roundChmp, numOfGame);
				}
			}
		};
		stage.setOnCloseRequest(closeWindow);
	}

	protected abstract void eventDone();

}
