import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class UtilGeral {

	private static final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	public static String getCurrentTimeStamp() {

		java.util.Date today = new java.util.Date();
		return dateFormat.format(today.getTime());
	}
}
