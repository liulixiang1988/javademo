import java.text.SimpleDateFormat;
import java.util.Date;

public class Main {

    public static void main(String[] args) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy��MM��dd��");
        String s = sdf.format(new Date());
        System.out.println(s);
    }
}
