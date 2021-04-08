package Program;

import Controler.Controler;
import Model.ControlClassModel;
import View.MainView;
import javafx.application.Application;
import javafx.stage.Stage;

public class Program extends Application {

	public static void main(String[] args) {
		launch(args);

	}

	@Override
	public void start(Stage arg0) throws Exception {
		MainView view = new MainView(arg0);
		ControlClassModel model = new ControlClassModel();
		Controler controler = new Controler(view, model);

	}

}
