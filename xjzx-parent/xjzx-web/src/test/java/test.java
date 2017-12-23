import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class test {
	public static void main(String[] args) throws UnsupportedEncodingException {
		System.out.println(new String("æ¹å".getBytes("ISO-8859-1"), "UTF-8"));
	}
}
