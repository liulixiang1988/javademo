package liulixiang1988;

/**
 * Created by liulixiang on 16/5/23.
 */
public class RegexDemo {
    public static void main(String[] args) {
        //String test = "\"ab\"cdefg\"hij\"";
        //String test = "ab\"cdefg\"hij";
        //String test = "\"ab\"cdefg\"hij";
        //String test = "ab\"cdefg\"hij\"";
        String test = "\"\"";
        if(test == null || test.isEmpty()) return;
        System.out.println(test.replaceAll("^\"(.*)\"$", "$1"));
    }
}
