package cal.ctrl;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class TitleBarController implements Initializable {

    @FXML
    private ImageView minimizeBtn, resizeBtn, closeBtn;
    @FXML
    private AnchorPane titleBar;

    private double oldX =0.0d, oldY = 0.0d, oldWidth = 0.0d, oldHeight = 0.0d, mouseX = 0.0d, mouseY = 0.0d;
    private boolean isMaximized = false;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setIcon();
        closeBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, e ->{
            closeWindow();
        });
      /*  resizeBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, e->{
            resizeWindow();
        });*/
        minimizeBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, e->{
            minimizeWindow();
        });
        /*titleBar.addEventHandler(MouseEvent.MOUSE_CLICKED, e ->{
            resizeWindow(e);
        });*/
        titleBar.addEventHandler(MouseEvent.MOUSE_PRESSED, e->{
            detectMousePress(e);
        });
        titleBar.addEventHandler(MouseEvent.MOUSE_DRAGGED, e->{
            detectMouseDrag(e);
        });
    }

    private void setIcon(){
        Image min = new Image(String.valueOf(getClass().getResource("../img/minimizeBtn.png")));
        Image close = new Image(String.valueOf(getClass().getResource("../img/closeBtn.png")));
        Image max = new Image(String.valueOf(getClass().getResource("../img/maximizeBtn.png")));
        //Image resize = new Image(String.valueOf(getClass().getResource("../img/resizeBtn.png")));

        minimizeBtn.setImage(min);
        closeBtn.setImage(close);
        /*resizeBtn.setImage(max);*/
    }

    public void closeWindow(){
        Stage stage = (Stage) closeBtn.getScene().getWindow();
        stage.close();
    }

   /* public void resizeWindow(){
        Stage stage = (Stage) resizeBtn.getScene().getWindow();

        if(isMaximized){
            isMaximized = false;
            stage.setX(oldX);
            stage.setY(oldY);
            stage.setWidth(oldWidth);
            stage.setHeight(oldHeight);
            resizeBtn.setImage(new Image(String.valueOf(getClass().getResource("../img/maximizeBtn.png"))));
        }else{
            isMaximized = true;
            oldHeight = stage.getHeight();
            oldWidth = stage.getWidth();
            oldX = stage.getX();
            oldY = stage.getY();
            resizeBtn.setImage(new Image(String.valueOf(getClass().getResource("../img/resizeBtn.png"))));
            Rectangle2D rec = Screen.getPrimary().getVisualBounds();
            stage.setX(rec.getMinX());
            stage.setY(rec.getMinY());
            stage.setWidth(rec.getWidth());
            stage.setHeight(rec.getHeight());
        }
    }*/

    public void minimizeWindow(){
        Stage stage = (Stage) minimizeBtn.getScene().getWindow();

        stage.setIconified(true);
    }

    /*private void resizeWindow(MouseEvent e){
        if(e.getClickCount() == 2)
            resizeWindow();
    }*/

    private void detectMousePress(MouseEvent e){
        Stage stage =(Stage) titleBar.getScene().getWindow();

        mouseX = stage.getX() - e.getScreenX();
        mouseY = stage.getY() - e.getScreenY();
    }

    private void detectMouseDrag(MouseEvent e){
        Stage stage = (Stage) titleBar.getScene().getWindow();
        stage.setX(e.getScreenX() + mouseX);
        stage.setY(e.getScreenY() + mouseY);

        if(isMaximized){
            isMaximized = false;
            stage.setHeight(stage.getHeight() - 20);
            stage.setWidth(stage.getWidth() - 20);
            resizeBtn.setImage(new Image(String.valueOf(getClass().getResource("../img/maximizeBtn.png"))));
        }
    }
}
