import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
public class Runtime {
    public static String ACC = "Protected";
    public static String TOKEN  = "Protected";
    public static final String ACCOUNT_SID = ACC;//System.getenv(ACC);
    public static final String AUTH_TOKEN = TOKEN;//System.getenv(TOKEN);

    public static final String P_Num = "+18663434694";
    public static void main(String[] args) {
        System.out.println(Scraper.Get_Items("#dinner",1));
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
        Message message = Message.creator(
                        new com.twilio.type.PhoneNumber("+13128331809"),
                        new com.twilio.type.PhoneNumber(P_Num),
                        "Test")
                .create();
        System.out.println(message.getSid());

    }
}
