package View;

import java.util.ArrayList;
import java.util.List;

import Listeners.ListenersOfView;
import Model.Basketball;
import Model.Championship;
import Model.Championship.eMsgForUser;
import Model.ControlClassModel.eChmpStatus;
import Model.Game;
import Model.Person;
import Model.Soccer;
import Model.Tennis;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class MainView {
	private Label title;
	private HBox mainOrder;
	private HBox datailsWithRadioBtn;
	private VBox secondOrderOfScene;
	private VBox secondOrderWithMsg;
	private VBox participantsPlace;
	private GridPane rightSide;
	private List<TextField> participants;
	private Label namesLabel;
	private TextField namesTextField;
	private Button btnAdd;
	private Button btnStartChampione;
	private GridPane details;
	private RadioButton basketball;
	private RadioButton soccer;
	private RadioButton tenis;
	private ToggleGroup champOption;
	private ListenersOfView listener;
	private int participateCounter;
	private Label msg;
	private List<Button> firstGamesRound;
	private List<TextField> secondRoundParticipate;
	private List<Button> secondGamesRound;
	private List<TextField> lastRoundParticipate;
	private List<Button> finalGame;
	private List<TextField> theWinner;
	private BorderPane newMainOrder;
	private String kindChmp;
	private GameView gameView;

	public MainView(Stage stage) {
		newMainOrder = new BorderPane();
		// title
		title = new Label("Championship");
		title.setFont(new Font(45));
		// left side
		participants = new ArrayList<TextField>();
		participantsPlace = new VBox();
		participantsPlace.setSpacing(15);
		participantsPlace.setAlignment(Pos.CENTER_LEFT);
		participantsPlace.setPadding(new Insets(10));
		openParticipantsPlace(Championship.NUM_OF_PARTICIPATE, participants, participantsPlace);
		participantsPlace.setSpacing(20);
		participantsPlace.setAlignment(Pos.CENTER_LEFT);
		participantsPlace.setPadding(new Insets(10));
		// middle
		msg = new Label();
		namesLabel = new Label("participant name:");
		namesTextField = new TextField();
		btnAdd = new Button("add participant");
		btnStartChampione = new Button("Start Championship");
		details = new GridPane();
		details.setHgap(40);
		details.setVgap(40);
		details.add(namesLabel, 0, 0);
		details.add(namesTextField, 1, 0);
		details.add(btnAdd, 0, 1);
		details.add(btnStartChampione, 1, 1);
		details.add(msg, 0, 2, 1, 15);
		// right side
		rightSide = new GridPane();
		rightSide.setPadding(new Insets(10));
		rightSide.setHgap(10);
		rightSide.setVgap(10);
		basketball = new RadioButton("Basketball");
		tenis = new RadioButton("Tennis");
		soccer = new RadioButton("Soccer");
		champOption = new ToggleGroup();
		basketball.setToggleGroup(champOption);
		tenis.setToggleGroup(champOption);
		soccer.setToggleGroup(champOption);
		rightSide.add(basketball, 0, 0);
		rightSide.add(soccer, 0, 1);
		rightSide.add(tenis, 0, 2);
		// main panes
		secondOrderOfScene = new VBox();
		datailsWithRadioBtn = new HBox();
		secondOrderOfScene.setSpacing(15);
		secondOrderOfScene.setAlignment(Pos.CENTER_LEFT);
		secondOrderOfScene.setPadding(new Insets(10));
		datailsWithRadioBtn.getChildren().addAll(details, rightSide);
		secondOrderOfScene.getChildren().addAll(title, datailsWithRadioBtn);
		details.setAlignment(Pos.CENTER);
		datailsWithRadioBtn.setSpacing(200);
		secondOrderOfScene.setAlignment(Pos.TOP_CENTER);
		secondOrderOfScene.setSpacing(200);
		secondOrderWithMsg = new VBox();
		secondOrderWithMsg.getChildren().addAll(secondOrderOfScene, msg);
		secondOrderWithMsg.setSpacing(20);
		mainOrder = new HBox();
		mainOrder.setSpacing(150);
		mainOrder.getChildren().addAll(participantsPlace, secondOrderWithMsg);
		event();
		newMainOrder.setCenter(mainOrder);
		stage.setScene(new Scene(newMainOrder, 1350, 800));
		stage.show();

	}

	public void addListener(ListenersOfView listener) {
		this.listener = listener;
	}

	private void event() {
		EventHandler<ActionEvent> addParticipate = new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				listener.viewUpdateModelNewParticipate(namesTextField.getText());
			}
		};
		EventHandler<ActionEvent> startChmp = new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				boolean succeded = true;
				msg.setText("");
				if (!basketball.isSelected() && !soccer.isSelected() && !tenis.isSelected()) {
					msg.setTextFill(Color.RED);
					msg.setText("please pick kind of championship");
					succeded = false;
				} else if (basketball.isSelected()) {
					listener.viewUpdateModelStartChmp(Basketball.class.getSimpleName());
				} else if (soccer.isSelected()) {
					listener.viewUpdateModelStartChmp(Soccer.class.getSimpleName());
				} else {
					listener.viewUpdateModelStartChmp(Tennis.class.getSimpleName());
				}
				if (succeded && msg.getText().equals(eMsgForUser.Succeded.toString())) {
					changeSceneOrder();
				}
			}

		};
		btnAdd.setOnAction(addParticipate);
		btnStartChampione.setOnAction(startChmp);
	}

	private void openParticipantsPlace(int numOfParticiptes, List<TextField> list, Pane addTo) {
		for (int i = 0; i < numOfParticiptes; i++) {
			list.add(new TextField());
			list.get(i).setBorder(
					new Border(new BorderStroke(Color.BLUE, BorderStrokeStyle.SOLID, null, new BorderWidths(2))));
			addTo.getChildren().add(list.get(i));
		}
	}

	public void updateParticipate(Person participate) {
		participants.get(participateCounter).setText(participate.getName());
		participants.get(participateCounter).setBorder(
				new Border(new BorderStroke(Color.LIMEGREEN, BorderStrokeStyle.SOLID, null, new BorderWidths(2))));
		participateCounter++;
	}

	private void changeSceneOrder() {
		firstGamesRound = new ArrayList<Button>();
		secondRoundParticipate = new ArrayList<TextField>();
		secondGamesRound = new ArrayList<Button>();
		lastRoundParticipate = new ArrayList<TextField>();
		finalGame = new ArrayList<Button>();
		theWinner = new ArrayList<TextField>();
		mainOrder.setSpacing(5);
		secondOrderWithMsg.getChildren().clear();
		addParticipateRounds(Championship.NUM_OF_FIRST_ROUND_GAMES, secondRoundParticipate);
		addBtnGame(Championship.NUM_OF_FIRST_ROUND_GAMES, firstGamesRound);
		addParticipateRounds(Championship.NUM_OF_SECOND_GAMES, lastRoundParticipate);
		addBtnGame(Championship.NUM_OF_SECOND_GAMES, secondGamesRound);
		addParticipateRounds(Championship.NUM_OF_FINAL_GAMES, theWinner);
		addBtnGame(Championship.NUM_OF_FINAL_GAMES, finalGame);
		orderOfPartOfNewShow(Championship.NUM_OF_FIRST_ROUND_GAMES, secondRoundParticipate, firstGamesRound, 25, 25,45);
		orderOfPartOfNewShow(Championship.NUM_OF_SECOND_GAMES, lastRoundParticipate, secondGamesRound, 115, 40, 45);
		orderOfPartOfNewShow(Championship.NUM_OF_FINAL_GAMES, theWinner, finalGame, 30, 100, 45);
		VBox forTitle = new VBox();
		forTitle.setAlignment(Pos.TOP_CENTER);
		msg.setText("");
		forTitle.getChildren().addAll(title, msg);
		forTitle.setSpacing(10);
		newMainOrder.setTop(forTitle);
		eventsNewOrder();
	}

	private void eventsNewOrder() {
		for (int i = 0; i < Championship.NUM_OF_FIRST_ROUND_GAMES; i++) {
			eventPressGame(firstGamesRound.get(i), i, eChmpStatus.firstRound);
		}
		for (int i = 0; i < Championship.NUM_OF_SECOND_GAMES; i++) {
			eventPressGame(secondGamesRound.get(i), i, eChmpStatus.secondRound);
		}
		eventPressGame(finalGame.get(0), 0, eChmpStatus.finalRound);
	}

	private void orderOfPartOfNewShow(int num, List<TextField> field, List<Button> btn, int vSpace, int linesVLenght,
			int lineHLenght) {
		GridPane btnWithTextOrder = new GridPane();
		List<VBox> orderlineAndBtn = new ArrayList<VBox>();
		List<VBox> firstHLine = new ArrayList<VBox>();
		List<VBox> secondHLine = new ArrayList<VBox>();
		for (int j = 0; j < num; j++) {
			orderlineAndBtn.add(new VBox());
			firstHLine.add(new VBox());
			firstHLine.get(j).setAlignment(Pos.CENTER_LEFT);
			secondHLine.add(new VBox());
			secondHLine.get(j).setAlignment(Pos.CENTER_LEFT);
			orderlineAndBtn.get(j).setAlignment(Pos.CENTER);
			firstHLine.get(j).getChildren().add(new Line(0, 0, lineHLenght, 0));
			secondHLine.get(j).getChildren().add(new Line(0, 0, lineHLenght, 0));
			orderlineAndBtn.get(j).getChildren().addAll(firstHLine.get(j), new Line(0, 0, 0, linesVLenght), btn.get(j),
					new Line(0, 0, 0, linesVLenght), secondHLine.get(j));
			btnWithTextOrder.add(orderlineAndBtn.get(j), 0, j);
			btnWithTextOrder.add(new Line(0, 0, lineHLenght, 0), 1, j);
			btnWithTextOrder.add(field.get(j), 2, j);
		}
		btnWithTextOrder.setVgap(vSpace);
		btnWithTextOrder.setAlignment(Pos.CENTER_LEFT);
		mainOrder.getChildren().add(btnWithTextOrder);
	}

	private void addBtnGame(int numOfGame, List<Button> list) {
		for (int i = 0; i < numOfGame; i++) {
			list.add(new Button("Play Game"));
		}
	}

	private void addParticipateRounds(int numOfGame, List<TextField> list) {
		for (int i = 0; i < numOfGame; i++) {
			list.add(new TextField());
			list.get(i).setBorder(
					new Border(new BorderStroke(Color.BLUE, BorderStrokeStyle.SOLID, null, new BorderWidths(2))));
		}
	}

	public void updateMsg(eMsgForUser msg) {
		if (msg == eMsgForUser.Succeded) {
			this.msg.setTextFill(Color.GREEN);
		} else {
			this.msg.setTextFill(Color.RED);
		}
		this.msg.setText(msg.toString());
	}

	private void eventPressGame(Button button, int numOfBattle, eChmpStatus round) {
		EventHandler<Event> pressGame = new EventHandler<Event>() {

			@Override
			public void handle(Event event) {
				listener.viewCheckFromModelIfCanDoBattle(numOfBattle, round);
				if (msg.getText().equals(eMsgForUser.Succeded.toString())) {
					newSceneGame(numOfBattle, round);
				}
			}
		};
		button.setOnMouseClicked(pressGame);
	}

	private void newSceneGame(int numOfBattle, eChmpStatus round) {
		Game game = listener.viewGetGameFromModel(numOfBattle, round);
		if (kindChmp.equals(Tennis.class.getSimpleName())) {
			gameView = new TennisView(round, numOfBattle, (Tennis.LIMIT_IS_3_GAMES + Tennis.NUMBER_OF_TIE_ROUNDS),
					game.getFirstParticipat().getName(), game.getSecondParticipat().getName(), this);
		} else if (kindChmp.equals(Soccer.class.getSimpleName())) {
			gameView = new SoccerView(round, numOfBattle, Soccer.LIMIT_IS_2_HALF, game.getFirstParticipat().getName(),
					game.getSecondParticipat().getName(), this);
		} else {
			gameView = new BasketballView(round, numOfBattle, Basketball.LIMIT_IS_4_QUARTER,
					game.getFirstParticipat().getName(), game.getSecondParticipat().getName(), this);
		}

	}

	public void updateExceptionMsg(String message) {
		this.msg.setTextFill(Color.RED);
		this.msg.setText(message.toString());
	}

	public void addKindOfChmp(String kindChmp) {
		this.kindChmp = kindChmp;
	}

	public void mainViewUpdateGameViewGameSituation(eMsgForUser msg) {
		gameView.updateMsgInGame(msg);

	}

	public void viewGameSendMainViewResultBattle(eChmpStatus roundChmp, int numOfGame, List<Integer> scoresF,
			List<Integer> scoresS) {
		listener.viewUpdateModelScoresGame(roundChmp, numOfGame, scoresF, scoresS);

	}

	public void updateGameViewResultGame(String gameResult) {
		gameView.setResultGame(gameResult);
	}

	public void mainViewUpdateGameViewMsgException(String message) {
		gameView.updateMsgInGameException(message);

	}

	public void viewGameSendMainViewResultTie(eChmpStatus roundChmp, int numOfGame, List<Integer> scoresF,
			List<Integer> scoresS) {
		listener.viewUpdateModelScoresTie(roundChmp, numOfGame, scoresF, scoresS);
	}

	public Game viewSendGameViewGame(eChmpStatus roundChmp, int numOfGame) {
		return listener.viewGetGameFromModel(numOfGame, roundChmp);
	}

	public void clearGame(eChmpStatus roundChmp, int numOfGame) {
		listener.viewClearGameInModel(roundChmp, numOfGame);
	}

	public void updateParticipateNextLevel(Game game) {
		if (game.getStatusRound() == eChmpStatus.firstRound) {
			secondRoundParticipate.get(game.getNumOfGame()).setText(game.getWinner().getName());
		} else if (game.getStatusRound() == eChmpStatus.secondRound) {
			lastRoundParticipate.get(game.getNumOfGame()).setText(game.getWinner().getName());
		} else {
			theWinner.get(0).setText(game.getWinner().getName());
		}
	}

	public void weHaveAWinner() {
		ShowTheWinner winnerShow = new ShowTheWinner(theWinner.get(0).getText());
	}
}
