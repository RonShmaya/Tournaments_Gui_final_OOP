package View;

import java.util.Random;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class ShowTheWinner {
	public static final int SCENE_H = 1400;
	public static final int SCENE_V = 800;
	public static final int LOOP_JUMPS = 151;
	public static final int BALON_JUMP = 50;
	public static final int BALON_H_START = 100;
	public static final int BALON_V_START = 150;
	public static final int BALON_RADIOUS_X = 50;
	public static final int BALON_RADIOUS_V = 75;
	public static final int LINE_LENGHT = 140;
	public static final int COLOR_LIMIT = 255;
	public static final int FACE_RADIOUS_X_AND_Y = 40;
	public static final int EYE_RADIUS_X_AND_Y = 10;
	public static final int ISHON_RADIOUS_X_AND_Y = 3;
	public static final int GOLBERT_START_V = 720;
	public static final int GOLBERT_UP_START_V = 350;
	private Pane mainPane;
	private Pane firstPersonOption;
	private Pane secondPersonOption;
	private int balonJumpsV;
	private Label winnerNameOnCup;
	private Label winnerName;
	private Button raise;
	private int middlePersonH;
	private int legsEndV;
	private int handStartV;
	private int faceStartV;
	private boolean isUp;

	public ShowTheWinner(String winnerName) {
		firstPersonOption = new Pane();
		;
		secondPersonOption = new Pane();
		;
		mainPane = new Pane();
		raise = new Button("Raise The Golbert");
		raise.setLayoutX(SCENE_H / 2 + 200);
		raise.setLayoutY(SCENE_V / 2);
		mainPane.getChildren().addAll(firstPersonOption, secondPersonOption, raise);
		mainPane.setPadding(new Insets(10));
		balons();
		nameOfTheWinnerUses(winnerName);
		legs();
		body();
		head();
		down();
		event();
		Stage stage = new Stage();
		stage.setScene(new Scene(mainPane, SCENE_H, SCENE_V));
		stage.show();
	}

	private void balons() {
		balonJumpsV = BALON_V_START;
		for (int i = BALON_H_START; i < SCENE_H; i = i + LOOP_JUMPS) {
			Ellipse ellipse = new Ellipse(i, balonJumpsV, BALON_RADIOUS_X, BALON_RADIOUS_V);
			Random rnd = new Random();
			ellipse.setFill(Color.rgb(rnd.nextInt(COLOR_LIMIT), rnd.nextInt(COLOR_LIMIT), rnd.nextInt(COLOR_LIMIT)));
			double[] points = { i - 10, balonJumpsV + BALON_RADIOUS_V + 10, i + 10, balonJumpsV + BALON_RADIOUS_V + 10,
					i, balonJumpsV + BALON_RADIOUS_V - 5 };
			Polygon triangle = new Polygon(points);
			Line line = new Line(i, balonJumpsV + BALON_RADIOUS_V + 10, i, balonJumpsV + LINE_LENGHT);
			triangle.setFill(ellipse.getFill());
			if (i % 2 == 0) {
				balonJumpsV = balonJumpsV - BALON_JUMP;
			} else {
				balonJumpsV = balonJumpsV + BALON_JUMP;
			}
			mainPane.getChildren().addAll(ellipse, triangle, line);
		}
	}

	private void nameOfTheWinnerUses(String winnerName) {
		this.winnerNameOnCup = new Label("Winner->" + winnerName);
		this.winnerName = new Label("Congratulations for " + winnerName);
		this.winnerName.setFont(new Font(30));
		this.winnerName.setLayoutX(10);
		this.winnerName.setLayoutY(400);
		mainPane.getChildren().add(this.winnerName);
	}

	private void up() {
		handsUp();
		golbetUp();
		firstPersonOption.getChildren().clear();
		isUp = true;

	}

	private void down() {
		hands();
		golbet();
		secondPersonOption.getChildren().clear();
		isUp = false;
	}

	private void legs() {
		middlePersonH = SCENE_H / 2;
		Line footRight = new Line(middlePersonH + 100, SCENE_V - 50, middlePersonH + 150, SCENE_V - 50);
		Line footLeft = new Line(middlePersonH - 100, SCENE_V - 50, middlePersonH - 150, SCENE_V - 50);
		Line kneeRight = new Line(middlePersonH + 100, SCENE_V - 50, middlePersonH + 100, SCENE_V - 150);
		Line kneeLeft = new Line(middlePersonH - 100, SCENE_V - 50, middlePersonH - 100, SCENE_V - 150);
		Line thighRight = new Line(middlePersonH + 100, SCENE_V - 150, middlePersonH, SCENE_V - 200);
		Line thighLeft = new Line(middlePersonH - 100, SCENE_V - 150, middlePersonH, SCENE_V - 200);
		mainPane.getChildren().addAll(footRight, footLeft, kneeRight, kneeLeft, thighRight, thighLeft);
		legsEndV = SCENE_V - 200;
	}

	private void body() {
		Line body = new Line(middlePersonH, legsEndV, middlePersonH, legsEndV - 150);
		mainPane.getChildren().add(body);
		handStartV = legsEndV - 100;
		faceStartV = legsEndV - 150;
	}

	private void head() {
		Ellipse face = new Ellipse(middlePersonH, faceStartV, FACE_RADIOUS_X_AND_Y, FACE_RADIOUS_X_AND_Y);
		face.setFill(Color.BEIGE);
		face.setStroke(Color.BLACK);
		Ellipse eye1 = new Ellipse(middlePersonH - 20, faceStartV - 5, EYE_RADIUS_X_AND_Y, EYE_RADIUS_X_AND_Y);
		Ellipse eye2 = new Ellipse(middlePersonH + 20, faceStartV - 5, EYE_RADIUS_X_AND_Y, EYE_RADIUS_X_AND_Y);
		eye1.setFill(Color.WHITE);
		eye2.setFill(Color.WHITE);
		eye1.setStroke(Color.BLACK);
		eye2.setStroke(Color.BLACK);
		Ellipse ishon1 = new Ellipse(middlePersonH - 20, faceStartV - 5, ISHON_RADIOUS_X_AND_Y, ISHON_RADIOUS_X_AND_Y);
		Ellipse ishon2 = new Ellipse(middlePersonH + 20, faceStartV - 5, ISHON_RADIOUS_X_AND_Y, ISHON_RADIOUS_X_AND_Y);
		Ellipse mouse = new Ellipse(middlePersonH, faceStartV + 30, 10, 3);
		mouse.setStroke(Color.BLACK);
		Ellipse mouseHelp = new Ellipse(SCENE_H / 2, faceStartV + 27, 10, 3);
		mouseHelp.setFill(face.getFill());
		mainPane.getChildren().addAll(face, eye1, eye2, ishon1, ishon2, mouse, mouseHelp);
	}

	private void hands() {
		Line handRight = new Line(middlePersonH, handStartV, middlePersonH + 100, handStartV + 100);
		Line handLeft = new Line(middlePersonH, handStartV, middlePersonH - 100, handStartV + 100);
		Line handRightSecond = new Line(middlePersonH + 100, handStartV + 100, middlePersonH + 50, handStartV + 200);
		Line handLeftSecond = new Line(middlePersonH - 100, handStartV + 100, middlePersonH - 50, handStartV + 200);
		firstPersonOption.getChildren().addAll(handRight, handLeft, handRightSecond, handLeftSecond);
	}

	private void golbet() {
		int jumper = 50;
		for (int i = 100; i > 0; i = i - 20) {
			Ellipse base = new Ellipse(middlePersonH, GOLBERT_START_V + jumper, i - jumper, 10);
			base.setFill(Color.BLACK);
			jumper = jumper - 5;
			firstPersonOption.getChildren().add(base);
		}
		for (int i = 0; i < 20; i++) {
			Ellipse body = new Ellipse(middlePersonH, GOLBERT_START_V + jumper, 10, 10);
			jumper = jumper - 2;
			body.setFill(Color.PALEGOLDENROD);
			body.setStroke(Color.BLACK);
			firstPersonOption.getChildren().add(body);
		}
		for (int i = 0; i < 100; i++) {
			Ellipse top = new Ellipse(middlePersonH, GOLBERT_START_V - i, i, 5);
			top.setFill(Color.BLACK);
			top.setStroke(Color.PALEGOLDENROD);
			firstPersonOption.getChildren().add(top);
		}
		this.winnerNameOnCup.setLayoutX(middlePersonH - 75);
		this.winnerNameOnCup.setLayoutY(GOLBERT_START_V - 100);
		this.winnerNameOnCup.setFont(new Font(17));
		firstPersonOption.getChildren().add(this.winnerNameOnCup);
	}

	private void handsUp() {
		Line handRight = new Line(middlePersonH, handStartV, middlePersonH + 100, handStartV - 50);
		Line handLeft = new Line(middlePersonH, handStartV, middlePersonH - 100, handStartV - 50);
		Line handRightSecond = new Line(middlePersonH + 100, handStartV - 50, middlePersonH + 50, handStartV - 100);
		Line handLeftSecond = new Line(middlePersonH - 100, handStartV - 50, middlePersonH - 50, handStartV - 100);
		secondPersonOption.getChildren().addAll(handRight, handLeft, handRightSecond, handLeftSecond);

	}

	private void golbetUp() {
		int jumper = 50;
		for (int i = 100; i > 0; i = i - 20) {
			Ellipse base = new Ellipse(middlePersonH, GOLBERT_UP_START_V + jumper, i - jumper, 10);
			base.setFill(Color.BLACK);
			jumper = jumper - 5;
			secondPersonOption.getChildren().add(base);
		}
		for (int i = 0; i < 20; i++) {
			Ellipse body = new Ellipse(middlePersonH, GOLBERT_UP_START_V + jumper, 10, 10);
			jumper = jumper - 2;
			body.setFill(Color.PALEGOLDENROD);
			body.setStroke(Color.BLACK);
			secondPersonOption.getChildren().add(body);
		}
		for (int i = 0; i < 100; i++) {
			Ellipse top = new Ellipse(middlePersonH, GOLBERT_UP_START_V - i, i, 5);
			top.setFill(Color.BLACK);
			top.setStroke(Color.PALEGOLDENROD);
			secondPersonOption.getChildren().add(top);
		}
		this.winnerNameOnCup.setLayoutX(middlePersonH - 75);
		this.winnerNameOnCup.setLayoutY(GOLBERT_UP_START_V - 100);
		this.winnerNameOnCup.setFont(new Font(17));
		secondPersonOption.getChildren().add(this.winnerNameOnCup);

	}

	private void event() {
		EventHandler<Event> raiseTheGolbert = new EventHandler<Event>() {

			@Override
			public void handle(Event event) {
				if (isUp) {
					down();
				} else {
					up();
				}
			}
		};
		raise.setOnMouseClicked(raiseTheGolbert);

	}

}
