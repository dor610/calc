package cal;

import cal.ctrl.MainController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main extends Application {
    
    private MainController mc;
    
    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader fx = new FXMLLoader(getClass().getResource("fxml/main.fxml"));
        Parent root = fx.load();

        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("css/main.css").toExternalForm());
        scene.getStylesheets().add(getClass().getResource("css/basic.css").toExternalForm());

        primaryStage.setTitle("Calculator");

        primaryStage.initStyle(StageStyle.UNDECORATED);
        scene.setFill(Color.TRANSPARENT);

        mc = fx.getController();

        scene.addEventFilter(KeyEvent.KEY_RELEASED, keyEvent -> {
            //System.out.println(keyEvent.getCode().toString());

            if(keyEvent.getCode().toString().equals("BACK_SPACE"))
                mc.backSpaceBtnHandler();
            else if(keyEvent.getCode().toString().equals("DELETE"))
                mc.deleteBtnHandler();
            else if (keyEvent.getCode().toString().equals("ENTER"))
                mc.enterBtnHandler();
            else
                mc.keyPressHandler(keyEvent);
        });

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args){
        launch(args);
    }
}
