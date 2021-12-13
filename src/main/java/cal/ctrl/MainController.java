package cal.ctrl;

import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainController implements Initializable {

    @FXML
    private AnchorPane title, convertTab, normalTab;

    @FXML
    private Label result, expression;

    @FXML
    private JFXButton num0, num1, num2, num3, num4, num5, num6, num7, num8, num9;

    @FXML
    private JFXButton mulBtn, divBtn, subBtn, addBtn, dotBtn, eqBtn, delBtn, clearBtn, openPBtn, closePBtn, numberSign;

    @FXML
    private JFXButton srBtn, logxyBtn, lnBtn, x2Btn, xnBtn, factBtn, eulerNumberBtn, piBtn;

    @FXML
    private JFXButton sinBtn, cosBtn, tanBtn, cotBtn, angleUnitBtn, toConvertTabBtn, toNormalTabBtn;

    @FXML
    private JFXButton cNum0Btn, cNum1Btn, cNum2Btn, cNum3Btn, cNum4Btn, cNum5Btn, cNum6Btn, cNum7Btn, cNum8Btn, cNum9Btn;
    @FXML
    private JFXButton aBtn, bBtn, cBtn, dBtn, eBtn, fBtn, cSignBtn, cClearBtn, cDelBtn, hexBtn, binBtn, decBtn, octBtn;
    @FXML
    private Label hexNum, binNum, decNum, octNum;


    @FXML
    private ImageView x2Icon, xnIcon, srIcon, logxyIcon, delIcon, piIcon, cDelIcon;

    private double curNumber, lastResult;

    private String exp, lastOp, angleUnit, convertMode;

    boolean isLastOp, isCalculated, isDivideByZero, specialExp, invalidResult;
    int openParentheses;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        convertMode = "DEC";
        angleUnit = "deg";
        reset();
        setTitleBar();
        setIcon();
        setHandler();
    }

    private void setIcon(){
        Image x2 = new Image(String.valueOf(getClass().getResource("../img/x2.png")));
        x2Icon.setImage(x2);

        Image xn = new Image(String.valueOf(getClass().getResource("../img/xn.png")));
        xnIcon.setImage(xn);

        Image sr = new Image(String.valueOf(getClass().getResource("../img/sr.png")));
        srIcon.setImage(sr);

        Image logxy = new Image(String.valueOf(getClass().getResource("../img/logxy.png")));
        logxyIcon.setImage(logxy);

        Image del = new Image(String.valueOf(getClass().getResource("../img/remove.png")));
        delIcon.setImage(del);
        cDelIcon.setImage(del);

        Image pi = new Image(String.valueOf(getClass().getResource("../img/pi.png")));
        piIcon.setImage(pi);
    }

    private void setTitleBar(){
        try{
            title.getChildren().clear();
            FXMLLoader fx = new FXMLLoader(getClass().getResource("../fxml/titleBar.fxml"));
            AnchorPane pane = fx.load();
            title.getChildren().setAll(pane);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setHandler(){

        num0.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> numberBtnHandler(num0));
        num1.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> numberBtnHandler(num1));
        num2.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> numberBtnHandler(num2));
        num3.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> numberBtnHandler(num3));
        num4.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> numberBtnHandler(num4));
        num5.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> numberBtnHandler(num5));
        num6.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> numberBtnHandler(num6));
        num7.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> numberBtnHandler(num7));
        num8.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> numberBtnHandler(num8));
        num9.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> numberBtnHandler(num9));


        numberSign.addEventHandler(MouseEvent.MOUSE_CLICKED, e ->{
            String text = result.getText();
            if(Pattern.compile(".*\\d.*").matcher(text).matches()){
                if(!text.equals("0")){
                    if(text.contains("-")){
                        text = text.substring(1);
                    }else {
                        text = "-"+text;
                    }
                    result.setText(text);
                }
            }
        });

        addBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, e-> opBtnHandler("+"));
        mulBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, e-> opBtnHandler("*"));
        divBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, e-> opBtnHandler("/"));
        subBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, e-> opBtnHandler("-"));
        xnBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> opBtnHandler("^"));


        eqBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, e-> eqBtnHandler());

        piBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, e-> piBtnHandler());

        factBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, e-> factBtnHandler());

        eulerNumberBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, e-> eulerNumberBtnHandler());

        x2Btn.addEventHandler(MouseEvent.MOUSE_CLICKED, e-> square());

        srBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, e-> opBtnHandler("sqrt"));

        delBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> backSpaceBtnHandler());

        dotBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> dotBtnHandler());

        lnBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> opBtnHandler("ln"));

        sinBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, e-> opBtnHandler("sin"));

        cosBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, e-> opBtnHandler("cos"));

        tanBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, e-> opBtnHandler("tan"));

        cotBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, e-> opBtnHandler("cot"));

        angleUnitBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, e->{
            if(angleUnit.equals("deg")){
                angleUnit = "rad";
                angleUnitBtn.setText("rad");
            }else {
                angleUnit = "deg";
                angleUnitBtn.setText("deg");
            }
        });

        result.textProperty().addListener((observable, oldValue, newValue) ->{
                Matcher matcher = Pattern.compile(".*\\..*").matcher(newValue);
                factBtn.setDisable(matcher.matches());

                if(newValue.length()>23){
                    result.setStyle("-fx-font-size: 28;");
                }else if(newValue.length() > 15) {
                    result.setStyle("-fx-font-size: 44;");
                }
                else{
                    result.setStyle("-fx-font-size: 64;");
                }

            logxyBtn.setDisable(newValue.equals("0"));

            closePBtn.setDisable(openParentheses <= 0);

                /* if(exp.equals("") || isLastOp){
                    openPBtn.setDisable(false);
                }else openPBtn.setDisable(true);*/



        });


        clearBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, e-> clearBtnHandler());

        logxyBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, e-> logxyBtnHandler());

        openPBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> openPHandler());

        closePBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, e-> closePHandler());

        toConvertTabBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, e ->{
            reset();
            openConvertMode("BIN");
            convertTab.toFront();
        });


        //Convert tab event handler

        toNormalTabBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, e-> {
            normalTab.toFront();
            reset();
            decNum.setText("0");
            hexNum.setText("0");
            octNum.setText("0");
            binNum.setText("0");
        });

        cNum0Btn.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
            numberBtnHandler(cNum0Btn);
            convert();
        });
        cNum1Btn.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
            numberBtnHandler(cNum1Btn);
            convert();
        });
        cNum2Btn.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
            numberBtnHandler(cNum2Btn);
            convert();
        });
        cNum3Btn.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
            numberBtnHandler(cNum3Btn);
            convert();
        });
        cNum4Btn.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
            numberBtnHandler(cNum4Btn);
            convert();
        });
        cNum5Btn.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
            numberBtnHandler(cNum5Btn);
            convert();
        });
        cNum6Btn.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
            numberBtnHandler(cNum6Btn);
            convert();
        });
        cNum7Btn.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
            numberBtnHandler(cNum7Btn);
            convert();
        });
        cNum8Btn.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
            numberBtnHandler(cNum8Btn);
            convert();
        });
        cNum9Btn.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
            numberBtnHandler(cNum9Btn);
            convert();
        });

        aBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
            numberBtnHandler(aBtn);
            convert();
        });
        bBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
            numberBtnHandler(bBtn);
            convert();
        });
        cBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
            numberBtnHandler(cBtn);
            convert();
        });
        dBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
            numberBtnHandler(dBtn);
            convert();
        });
        eBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
            numberBtnHandler(eBtn);
            convert();
        });
        fBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
            numberBtnHandler(fBtn);
            convert();
        });

        cDelBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, e-> {
            backSpaceBtnHandler();
            convert();
        });
        cClearBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, e->{
            result.setText("0");
            convert();
        });

        cSignBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, e->{
            //đổi dấu
        });

        binBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, e-> openConvertMode("BIN"));
        hexBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, e-> openConvertMode("HEX"));
        octBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, e-> openConvertMode("OCT"));
        decBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, e-> openConvertMode("DEC"));

    }


    // Thiết lập lại các biến
    public void reset(){
        exp = "";
        curNumber = 0;
        lastResult = 0;
        Stack<Double> operandStack = new Stack<>();
        Stack<String> operatorStack = new Stack<>();
        isLastOp = false;
        isCalculated = false;
        isDivideByZero = false;
        specialExp = false;
        openParentheses = 0;
        expression.setText("");
        result.setText("0");
        invalidResult = false;
    }

    //Xử lý sự kiện nhấn nút =
    public void eqBtnHandler(){
       if(openParentheses > 0){
            if(!exp.endsWith(")")){
                exp += " "+ result.getText();
                expression.setText(expression.getText() + " " + result.getText());
            }
           while (openParentheses > 0){
               System.out.println(openParentheses);
               //closePHandler();
               exp += " )";
               expression.setText(expression.getText() + " )");
               System.out.println(exp);
               openParentheses--;
           }
       }
        if(exp.equals("")){
            exp = result.getText();
            expression.setText(result.getText());
        }else {
            if(specialExp){
                expression.setText(expression.getText());
            }else {
                if(!exp.endsWith(")")){
                    exp = exp + " " + result.getText();
                    expression.setText(expression.getText() + " "+ result.getText());
                }
            }
        }

        //System.out.println(exp);
        isCalculated = true;
        lastResult = shuntingYard(exp);

        if(isDivideByZero){
            isDivideByZero = false;
            result.setText("Cannot divide by zero");
            expression.setText("");
        }else if(invalidResult){
            invalidResult = false;
            result.setText("Invalid Input");
            expression.setText("");
        }else {
            invalidResult = false;
            isDivideByZero = false;
            result.setText(numberFormat(lastResult));
            expression.setText(expression.getText()+" =");
        }



        exp = "";
        clearBtn.setText("C");
    }

    //xử lý sự kiện nhấn phím số trên màn hình
    private void numberBtnHandler(JFXButton btn){
        if(invalidResult || isCalculated){  reset();}
        clearBtn.setText("CE");
        if(result.getText().equals("0") || isLastOp){
            System.out.println("hihi");
            result.setText("");
            isLastOp = false;
            isCalculated = false;
        }

        String num = btn.getText();
        result.setText(result.getText()+num);
    }
    //xử lý sự kiện nhấn phím số trên bàn phím
    public void numberBtnHandler(KeyEvent e){
        if(invalidResult || isCalculated){  reset();}
        clearBtn.setText("CE");
        if(result.getText().equals("0") || isLastOp){
            result.setText("");
            isLastOp = false;
        }
        result.setText(result.getText()+e.getText());

    }
    //xử lý sự kiện nhấn phím toán hạng trên màn hình
    private void opBtnHandler(String op){
        if(! Pattern.compile(".*\\d.*").matcher(result.getText()).matches()) result.setText("0");
        //truoc do vua nhan operator nao do
        //System.out.println(op +" ------------ "+ isLastOp+" -------------- "+lastOp);
        if(isCalculated){
            expression.setText("");
            exp = "";
            isLastOp = false;
            isCalculated = false;
        }
        if(isLastOp){
            if(!lastOp.equals(op)){
                switch (lastOp){
                    case ")":
                        switch (op) {
                            case "sin", "cos", "tan", "cot", "sqrt", "ln" -> {
                                exp = op + " (";
                                expression.setText(op + " (");
                            }
                            default -> {
                                exp += " " + op;
                                expression.setText(expression.getText() + " " + op);
                            }
                        }
                    break;
                    case "sin": case "cos": case "tan": case "cot": case "sqrt": case "ln":
                        switch (op) {
                            case "sin", "cos", "tan", "cot", "sqrt", "ln" -> {
                                exp = exp.substring(0, exp.length() - 2 - lastOp.length()) + op + " (";
                                String ex = expression.getText();
                                expression.setText(ex.substring(0, ex.length() - 2 - lastOp.length()) + op + " (");
                            }
                            default -> {
                                int length = lastOp.length() + 4;
                                if (exp.length() > length) {
                                    exp = exp.substring(0, exp.length() - length - 2) + op;
                                } else {
                                    exp = exp.substring(0, exp.length() - length) + op;
                                }
                                expression.setText(expression.getText().substring(0, expression.getText().length() - length) + op);
                                openParentheses--;
                                if (openParentheses == 0) closePBtn.setDisable(true);
                            }
                        }
                        break;
                    default:
                        switch (op) {
                            case "sin", "cos", "tan", "cot", "sqrt", "ln" -> {
                                exp += " 1 " + op + " (";
                                String expr = expression.getText();
                                expression.setText(expr + " " + op + " (");
                                openParentheses++;
                                closePBtn.setDisable(false);
                            }
                            default -> {
                                exp = exp.substring(0, exp.length() - 1) + op;
                                String ex = expression.getText();
                                expression.setText(ex.substring(0, ex.length() - 1) + op);
                            }
                        }
                }
            }

            lastOp = op;
            isLastOp = true;
        }else if(specialExp){
            specialExp = false;
            isLastOp = true;
            exp = exp + " " + op;
            lastOp = op;
            expression.setText(expression.getText() + " " + op);
        } else {
            switch (op) {
                case "sin", "cos", "tan", "cot", "sqrt", "ln" -> {
                    exp = "1 " + op + " (";
                    expression.setText(op + " (");
                    lastOp = op;
                    openParentheses++;
                    closePBtn.setDisable(false);
                    isLastOp = false;
                }
                default -> {
                    exp += " " + result.getText() + " " + op;
                    expression.setText(expression.getText() + " " + result.getText() + " " + op);
                    lastOp = op;
                    isLastOp = true;
                }
            }
        }

    }

    public void keyPressHandler(KeyEvent e){
        Matcher operand = Pattern.compile("\\d+").matcher(e.getText());
        if(operand.matches()){
            numberBtnHandler(e);
        }
    }

    public void backSpaceBtnHandler(){
        String text = result.getText();

        if(isCalculated){
            expression.setText("");
            exp = "";
            isCalculated = false;
        }else if(!text.equals("0")){
            result.setText(text.substring(0, text.length()-1));
            if(result.getText().equals("")){
                result.setText("0");
                clearBtn.setText("C");
            }
        }
    }

    public void deleteBtnHandler(){
        clearBtn.setText("C");
        result.setText("0");
    }

    public void enterBtnHandler(){
        eqBtnHandler();
    }

    public void dotBtnHandler(){
        if(invalidResult){  reset();}
        factBtn.setDisable(true);
        result.setText(result.getText()+".");
    }

    public void factBtnHandler(){
        if(invalidResult){  reset();}
        int num = Integer.parseInt(result.getText());
        //isLastOp = true;

        curNumber = fact(num);
        exp = exp + " " + curNumber;
        expression.setText(expression.getText() + " " + "fact("+num+")");
        result.setText(curNumber + "");
    }

    private void openPHandler(){
        if(invalidResult){  reset();}
        closePBtn.setDisable(false);
        String ex = expression.getText();
        exp += " (";
        expression.setText(ex + " (");
        openParentheses++;
    }
    private void closePHandler(){
        if(invalidResult){  reset();}
        String ex = expression.getText();
        if(isLastOp){
            exp = exp.substring(0, exp.lastIndexOf(" "));
            expression.setText(expression.getText().substring(0, expression.getText().lastIndexOf(" ")));
        }

        if(ex.endsWith(")")){
            exp +=" )";
            expression.setText(expression.getText() + " )");
        }else {
            exp += " "+Double.parseDouble(result.getText()) +" )";
            expression.setText(ex +" "+result.getText()+ " )");
        }
        isLastOp = true;
        lastOp = ")";
        openParentheses--;
        if(openParentheses == 0){
            closePBtn.setDisable(true);
        }
    }

    public int fact(int num){
        if(invalidResult){  reset();}
        specialExp = true;
        if(num > 0){
            return num*fact(num-1);
        }else return 1;
    }

    public void piBtnHandler(){
        if(invalidResult){  reset();}
        curNumber = Math.PI;

        result.setText(curNumber + "");
    }

    public void eulerNumberBtnHandler(){
        if(invalidResult){  reset();}
        curNumber = Math.E;

        result.setText(curNumber + "");
    }

    public double ln(double a){
        if(a == 0){
            invalidResult = true;
            return 0;
        }else {
            a = lnCalculate(a);
            return a;
        }
    }

    public double sin(double a){
        if(angleUnit.equals(("deg"))){
            return Math.sin(Math.toRadians(a));
        }else return Math.sin(a);
    }

    public double cos(double a){
        if(angleUnit.equals("deg")){
            return Math.cos(Math.toRadians(a));
        }else return Math.cos(a);
    }

    public double tan(double a){
        if(a != 0 && ((angleUnit.equals("deg") && a%90 == 0) || (angleUnit.equals("rad") && a%0.5 == 0))){
            invalidResult = true;
            return 0;
        } else {
            if(angleUnit.equals("deg")) a = Math.toRadians(a);
            a = Math.tan(a);
            return a;
        }
    }

    public double cot(double a){
        if((angleUnit.equals("deg") && a%180 == 0) || (angleUnit.equals("rad") && a%1 == 0)){
            invalidResult = true;
            return 0;
        } else {
            if(angleUnit.equals("deg")) a =  Math.toRadians(a);

            a = 1 / Math.tan(a);

           return a;
        }
    }

    public void logxyBtnHandler(){
        if(invalidResult){  reset();}
        exp += " " + result.getText().replaceAll(",", "") + " log";
        expression.setText(expression.getText() + " " + result.getText() + " logBase");
        isLastOp = true;
    }

    public void clearBtnHandler(){
        String name = clearBtn.getText();

        if(name.equals("C")){
            expression.setText("");
            exp = "";
            result.setText("0");
            reset();
        }else {
            result.setText("0");
            clearBtn.setText("C");
        }
    }

    public void square(){
        double num = Double.parseDouble(result.getText().replaceAll(",",""));

        curNumber = pow(num, 2);

        result.setText(curNumber + "");
    }

    public double squareRoot(double a){
        if(a> 0) return Math.sqrt(a);

        invalidResult = true;
        return 0;
    }


    public double shuntingYard(String exp){

        /*
        * Chuỗi a có dạng "a + b - c";
        * Chuyển thành mãng các chuỗi b
        * Tạo 2 stack chứa toán tử và toán hạng
        *  */
        exp = exp.trim();
       // System.out.println("chuoi a dua vao la: "+exp);
        String[] expArr = exp.split("\\s");
       // System.out.println(exp.length() - expArr.length);
        Stack<Double> operandStack = new Stack<>();
        Stack<String> operatorStack = new Stack<>();

        //Nếu trong mảng b chỉ có một phần tử và nó là số thì return số đó
        if(expArr.length == 1 && Pattern.compile(".*\\d.*").matcher(expArr[0]).matches()) return Double.parseDouble(expArr[0]);

        //Duyệt qua mảng b
       for(String element: expArr){

           //System.out.println("\n c la: "+element+"\n");
           //pattern kiểm tra có phải số
           Matcher matcher = Pattern.compile(".*\\d.*").matcher(element);
           //Nếu c là số
           if(matcher.matches()){
               double operand = Double.parseDouble(element);
               //push c vào trong ngăn xếp
               operandStack.push(operand);
           }
           //Nếu c là toán tử
           else
           //Nếu ngăn xếp trống thì push c vào
           if(operatorStack.isEmpty()){
                   operatorStack.push(element);
           }
           //Ngược lại ngăn xếp có toán tử
           else {
               //Lấy toán tử đang ở trong ngăn xếp
               String op = operatorStack.peek();
               //Xét toán tử c
               switch (element){
                   // Nếu là dấu "(" thì push vào stack
                   case "(":
                       operatorStack.push(element);
                       break;
                       //Nếu là dấu ")" thì thực hiện các phép toán bên trong
                   case ")":
                       //Nếu toán tử trong stack là "(" thì pop nó ra
                       if(op.equals("(")){
                           operatorStack.pop();
                       }else {
                           //Ngược lại thì tính toán các phép toán bên trong
                               /*
                               * Lặp cho đến khi phép toán trong stack là "("
                               * hock stack rỗng*/
                           while (op.equals("(") == false && operatorStack.isEmpty() == false){
                               double operand1 = operandStack.pop();
                               double operand2 = operandStack.pop();
                               //thực hiện tính toán
                               double result = calculate(operand1, operand2, op);
                               //puch kết quả vào stack chứa số
                               operandStack.push(result);
                               operatorStack.pop();
                               //lấy phép toán trong stack toán tử
                               op = operatorStack.peek();
                           }

                           //Nếu stack toán tử không trống thì pop nó ra
                           if(operatorStack.isEmpty() == false) operatorStack.pop();
                       }
                       break;
                   default:
                       //nếu phép toán hiện tại trong stack là "(" thì push thẳng phép toán đang xét vào stack
                       if (!op.equals("(")) {
                           //Ngược lại check độ ưu tiên của phép toán đang xét với phép toán hiện đang trong stack
                           while (priorityCheck(op, element)) {

                               //Như trên vòng while
                               /*System.out.println("------------------------------------------------------");
                               System.out.println(operandStack.toString());
                               System.out.println(operatorStack.toString());*/
                               double operand1 = operandStack.pop();
                               double operand2 = operandStack.pop();
                               double result = calculate(operand1, operand2, op);
                               operandStack.push(result);
                               operatorStack.pop();
                               if (!operatorStack.isEmpty()) {
                                   if (operatorStack.peek().equals("(")) break;
                                   op = operatorStack.peek();
                               } else break;
                           }
                           /* System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++");
                           System.out.println(operandStack.toString());
                           System.out.println(operatorStack.toString());*/
                       }
                       operatorStack.push(element);
                       break;
                   }
               }
        }

       /*
       *
       * */

       System.out.println("/////////////////////////////");
       System.out.println(exp);
       System.out.println("---------------------------------");
        System.out.println(operandStack.toString());
        System.out.println(operatorStack.toString());
       //Thực hiện vòng lặp cho đến khi stack toán tử có trống
        while (!operatorStack.isEmpty()){

            String op = operatorStack.pop();
            double operand1 = operandStack.pop();
            double operand2 = operandStack.pop();
            double result = calculate(operand1, operand2, op);
            operandStack.push(result);
        }

        //kết quả cuối cùng nằm trong stack số;
        // System.out.println("result: "+result);
        return operandStack.pop();
    }

    //kiểm tra độ ưu tiên
    private boolean priorityCheck(String op1, String op2){

        //op2 là toán tử đang xét, op1 là toán tử trong stack

        if(op1.equals("")) return false;

        int p1, p2;
        p1 = 1;
        p2 = 1;

        if(op1.equals("*") || op1.equals("/") || op1.equals("^")) p1 = 2;

        if(op2.equals("*") || op2.equals("/") || op2.equals("^")) p2 = 2;

        if(op2.equals("log") || op2.equals("sin") || op2.equals("cos") || op2.equals("tan") || op2.equals("cot")
                || op2.equals("fact") || op2.equals("sqrt") || op2.equals("ln")) p2 = 3;

        if(op1.equals("log") || op1.equals("sin") || op1.equals("cos") || op1.equals("tan") || op1.equals("cot")
                || op1.equals("fact") || op1.equals("sqrt") || op1.equals("ln")) p1 = 3;


        //Nếu toán tử đang op2 có độ ưu tiên thấp hơn op1 thì return false <=> không thực hiện phép tính
        //Ngược lại return true <=> thực hiện phép tính.
        return p1>=p2;
    }

    //Hàm tính toán
    private double calculate(double b, double a, String c){
        switch (c){
            case "+": return a + b;
            case "-": return a - b;
            case "*": return a * b;
            case "/":
                if(!(b == 0)) return a / b;

                    //Biến trạng thái: chia cho 0;
                else isDivideByZero = true;

                return 0;
            case "^":
                //Hàm tính luỹ thừa
                return pow(a, b);
            case "log":
                //hàm tính logxy
                return logxyCalculate(b, a);
            case "ln": return ln(b);
            case "sin": return sin(b);
            case "cos": return cos(b);
            case "tan": return tan(b);
            case "cot": return cot(b);
            case "sqrt": return  squareRoot(b);
            default: return 0;
        }
    }


    private double logCalculate(double x){
        return Math.log10(x);
    }

    private double lnCalculate(double x){
        return Math.log(x);
    }

    private double logxyCalculate(double x, double y){
        return logCalculate(y)/logCalculate(x);
    }

    private double pow(double x, double n){
        double result;
        if(n - Math.floor(n) > 0){
            result = Math.pow(x, n);
        }else {
            result = intPow(x,(int) n);
        }
        return result;
    }

    private double intPow(double x, int n){
        if(n == 0) return 1;
        else return x * pow(x, n-1);
    }

    private String numberFormat(double num){
        if((num+"").contains("E")){
            return num+"";
        }else {
            String fnum = String.format("%,f", num);
           // System.out.println(fnum);
            if (num % 1 != 0){
                while (true){
                    if(fnum.endsWith("0") || fnum.endsWith(".")){
                        fnum = fnum.substring(0, fnum.length() -1);
                        continue;
                    } break;
                }
            }else {
               // System.out.println("Integer");
                fnum = fnum.substring(0, fnum.indexOf("."));
            }
            return fnum;
        }
    }


    public String decToBin(long a){
        StringBuilder result = new StringBuilder();
        while (true){
            if(a == 1 || a == 0){
                result.insert(0, a);
                break;
            }else {
                result.insert(0, a % 2);
                a = a/2;
            }
        }
        return result.toString();
    }

    public String decToHex(long a) {
        StringBuilder result = new StringBuilder();
        while (true){
            if(a <= 15){
               result.insert(0, translateDecToHex(a + ""));
               break;
            }
            else {
                result.insert(0, translateDecToHex(a % 16 + ""));
                a = a/16;
            }
        }
        return result.toString();
    }

    private String translateDecToHex(String a){
        return switch (a) {
            case "10" -> "A";
            case "11" -> "B";
            case "12" -> "C";
            case "13" -> "D";
            case "14" -> "E";
            case "15" -> "F";
            default -> a;
        };
    }

    public String decToOct(long a) {
        StringBuilder result = new StringBuilder();
        while (true){
            if(a <=7){
                result.insert(0, a);
                break;
            }else {
                result.insert(0, a % 8);
                a = a/8;
            }
        }
        return result.toString();
    }

    public String binToDec(String a){
        if(a.equals("0")) return "0";
        char[] arr = a.toCharArray();
        long result = 0;
        int length = arr.length;
        for(int i = 0; i < length ; i++){
            result += Integer.parseInt(""+ arr[i])* (long) pow(2, length - (i+1));
        }
        return "" +result;
    }

    public String hexToDec(String a){
        if(a.equals("0")) return "0";
        char[] arr = a.toCharArray();
        long result = 0;
        int length = arr.length;
        for(int i = 0; i < length; i++){
            result += Integer.parseInt(translateHexToDec(arr[i]+""))* (long) pow(16, length - 1 - i );
        }
        return "" +result;
    }

    private String translateHexToDec(String a){
        return switch (a) {
            case "A" -> "10";
            case "B" -> "11";
            case "C" -> "12";
            case "D" -> "13";
            case "E" -> "14";
            case "F" -> "15";
            default -> a;
        };
    }

    public String octToDec(String a){
        if(a.equals("0")) return "0";
        char[] arr = a.toCharArray();
        long result = 0;
        int length = arr.length;
        for(int i = 0; i < length; i++){
            result += Integer.parseInt(translateHexToDec(arr[i]+""))* (long) pow(8, length - 1 - i );
        }
        return "" +result;
    }

    public void openConvertMode(String mode){
        convert(convertMode, mode);
        convertMode = mode;
        cNum9Btn.setDisable(true);
        cNum8Btn.setDisable(true);
        cNum7Btn.setDisable(true);
        cNum6Btn.setDisable(true);
        cNum5Btn.setDisable(true);
        cNum4Btn.setDisable(true);
        cNum3Btn.setDisable(true);
        cNum2Btn.setDisable(true);
        cNum1Btn.setDisable(true);
        cNum0Btn.setDisable(true);
        aBtn.setDisable(true);
        bBtn.setDisable(true);
        cBtn.setDisable(true);
        dBtn.setDisable(true);
        eBtn.setDisable(true);
        fBtn.setDisable(true);

        switch (mode) {
            case "HEX" -> {
                fBtn.setDisable(false);
                eBtn.setDisable(false);
                dBtn.setDisable(false);
                cBtn.setDisable(false);
                bBtn.setDisable(false);
                aBtn.setDisable(false);
                cNum9Btn.setDisable(false);
                cNum8Btn.setDisable(false);
                cNum7Btn.setDisable(false);
                cNum6Btn.setDisable(false);
                cNum5Btn.setDisable(false);
                cNum4Btn.setDisable(false);
                cNum3Btn.setDisable(false);
                cNum2Btn.setDisable(false);
                cNum1Btn.setDisable(false);
                cNum0Btn.setDisable(false);
            }
            case "DEC" -> {
                cNum9Btn.setDisable(false);
                cNum8Btn.setDisable(false);
                cNum7Btn.setDisable(false);
                cNum6Btn.setDisable(false);
                cNum5Btn.setDisable(false);
                cNum4Btn.setDisable(false);
                cNum3Btn.setDisable(false);
                cNum2Btn.setDisable(false);
                cNum1Btn.setDisable(false);
                cNum0Btn.setDisable(false);
            }
            case "OCT" -> {
                cNum7Btn.setDisable(false);
                cNum6Btn.setDisable(false);
                cNum5Btn.setDisable(false);
                cNum4Btn.setDisable(false);
                cNum3Btn.setDisable(false);
                cNum2Btn.setDisable(false);
                cNum1Btn.setDisable(false);
                cNum0Btn.setDisable(false);
            }
            case "BIN" -> {
                cNum1Btn.setDisable(false);
                cNum0Btn.setDisable(false);
            }
            default -> {
            }
        }

    }

    public void convert(){
        String text = result.getText().replaceAll("\\s", "");
        String decNumber = switch (convertMode) {
            case "BIN" -> binToDec(text);
            case "HEX" -> hexToDec(text);
            case "OCT" -> octToDec(text);
            default -> text;
        };
       // System.out.println(convertMode+"  "+decNumber);
        decNum.setText(decNumber);
        binNum.setText(binNumberFormat(decToBin(Long.parseLong(decNumber))));
        octNum.setText(decToOct(Long.parseLong(decNumber)));
        hexNum.setText(decToHex(Long.parseLong(decNumber)));
    }


    public void convert(String oldMode, String newMode){
        String text = result.getText().replaceAll("\\s", "");
        String decNumber;
        switch (oldMode) {
            case "BIN" -> {
                decNumber = binToDec(text);
                binBtn.getStyleClass().remove("active-btn");
            }
            case "HEX" -> {
                decNumber = hexToDec(text);
                hexBtn.getStyleClass().remove("active-btn");
            }
            case "OCT" -> {
                decNumber = octToDec(text);
                octBtn.getStyleClass().remove("active-btn");
            }
            default -> {
                decNumber = text;
                decBtn.getStyleClass().remove("active-btn");
            }
        }


        String newNumber;
        switch (newMode) {
            case "BIN" -> {
                newNumber = binNumberFormat(decToBin(Long.parseLong(decNumber)));
                binBtn.getStyleClass().add("active-btn");
            }
            case "HEX" -> {
                newNumber = decToHex(Long.parseLong(decNumber));
                hexBtn.getStyleClass().add("active-btn");
            }
            case "OCT" -> {
                newNumber = decToOct(Long.parseLong(decNumber));
                octBtn.getStyleClass().add("active-btn");
            }
            default -> {
                newNumber = decNumber;
                decBtn.getStyleClass().add("active-btn");
            }
        }

        System.out.println("hih: " + newNumber);

        result.setText(newNumber);
    }

    private String binNumberFormat(String num){
        if(num.equals("0")) return "0";
        int length = num.length();
        int i = 4 - length % 4;
        StringBuilder numBuilder = new StringBuilder(num);
        for (; i > 0; i--) {
            numBuilder.insert(0, "0");
        }
        num = numBuilder.toString();

        char[] arr = num.toCharArray();
        length = arr.length;
        int count = 1;
        StringBuilder result = new StringBuilder();
        for(i = 0; i < length; i++){
            if(count == 4){
                result.append(arr[i]).append(" ");
                count = 1;
            }else {
                count++;
                result.append(arr[i]);
            }
        }

        return result.toString().trim();
    }
}
