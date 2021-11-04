package cal.ctrl;
import java.io.IOException;
import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class hihi {

/* A Java program to evaluate a
   given expression where tokens
   are separated by space.
*/
        public static Double evaluate(String expression)
        {
            //char[] tokens = expression.toCharArray();//Mang tokens

            //Chuỗi expression phải có dạng: "số"+"dấu cách"+"toán tử"+"số". VD: "5 + 6",... tương tự mấy phép tính khác.

            expression = expression.trim();

            String arr[] = expression.split(" ");//Tách thành mảng các phần tử từ chuỗi expression

                    //Ngan xep cho cac so 'values'
            Stack<Double> values = new Stack<>();

            //Ngan xep cho cac phep tinh 'ops'
            Stack<String> ops = new Stack<>();

            for (int i = 0; i < arr.length; i++)//vong for chay tu dau den cuoi mang tokens
            {
                //Phan tu cua Mang la 1 so thi push no vao ngan xep 'values'
                Matcher matcher = Pattern.compile(".*\\d.*").matcher(arr[i]);
                if (matcher.matches() == true)// Nếu nó match với cái pattern ở trên thì nó là số VD: 1.2, 2000, 0.3,...
                {
                   values.push(Double.parseDouble(arr[i]));
                }

                // Neu gap dau (
                // push no vao ops
                else if (arr[i].equals("("))
                    ops.push(arr[i]);

                    // Closing brace encountered,
                    // solve entire brace
                else if (arr[i].equals(")"))//neu gap ')'
                {
                    while (ops.peek().equals("(") == false)//Kiem tra neu dau ngan xep k co dau '('
                        values.push(applyOp(ops.peek(), values.pop(), values.pop())); // ops.pop() ở đây là lấy nó ra rồi, đổi thành peek() thì mới pop() thêm ở dưới
                    ops.pop();//lay no ra khoi ngan xep
                }

                // neu gap toan tu +-*/
                else // tất cả trường hợp còn lại là đều là toán tử
                {
                    // While top of 'ops' has same
                    // or greater precedence to current
                    // token, which is an operator.
                    // Apply operator on top of 'ops'
                    // to top two elements in values stack
                    while (!ops.empty() && hasPrecedence(arr[i], ops.peek()))// kiem tra ngan xep ops cos rong k va kiem tra uu tien '()'
                        values.push(applyOp(ops.pop(), values.pop(), values.pop()));
                    // Push current token to 'ops'.
                    ops.push(arr[i]);
                }
            }

            // Entire expression has been
            // parsed at this point, apply remaining
            // ops to remaining values
            while (!ops.empty())//neu ngan xep toan tu khac  rong
                values.push(applyOp(ops.pop(),
                        values.pop(),
                        values.pop()));

            // Top of 'values' contains
            // result, return it
            return values.pop();//tra ve ket qua
        }

        // Returns true if 'op2' has higher
        // or same precedence as 'op1',
        // otherwise returns false.
        public static boolean hasPrecedence(
                String op1, String op2)
        {
            if (op2.equals("(") || op2.equals(")"))
                return false;
            if ((op1.equals("*") || op1.equals("/") || op1.equals("^")) || op1.equals("log") &&
                    (op2.equals('+') || op2.equals("-")))
                return false;
            else
                return true;
        }

        // A utility method to apply an
        // operator 'op' on operands 'a'
        // and 'b'. Return the result.
        public static double applyOp(String op, Double b, Double a)
        {
            switch (op)
            {
                case "+":
                    return a + b;
                case "-":
                    return a - b;
                case "*":
                    return a * b;
                case "/":
                    if (b == 0)
                   /* throw new
                            UnsupportedOperationException("Cannot divide by zero");*/
                        return a/b;
                case "%":
                    return a % b;
                case "^":
                    return Math.pow(a, b);
                    /*
                    * Trường hợp logxy thì trong cái chuỗi expression thì ghi kiểu "y log x"
                    * Còn cái chuỗi để hiển thị thì ghi "y log base x" */
                case "log":
                    return logxy(b, a);
            }
            return 0;
        }


        //Hàm tính logxy
        private static double logxy(double x, double y){
            return Math.log10(y)/Math.log10(x);
        }

        // Driver method to test above methods
   /* public static void main(String[] args)
    {
        System.out.println(Basic.
                evaluate("10 + 2 * 6"));
        System.out.println(Basic.
                evaluate("100 * 2 + 12"));
        System.out.println(Basic.
                evaluate("100 * ( 2 + 12 )"));
        System.out.println(Basic.
                evaluate("100 * ( 2 + 12 ) / 14"));
    }*/
}

