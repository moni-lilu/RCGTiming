package ui;

import org.checkerframework.checker.units.qual.C;

import javax.mail.*;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ReadEmail {
    static Controller controller = new Controller();
    //public static final String USERNAME = "juli@rcgtiming.com";
    //public static final String PASSWORD = "ttexablzanghuxnc";
    //String USERNAME;
   // String PASSWORD;
    public static String activateHref = "";

    public static String getActivateHref(String email) throws MessagingException, IOException {

        // 1. Setup properties for the mail session.
        Properties props = new Properties();
        props.put("mail.pop3.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.pop3.socketFactory.fallback", "false");
        props.put("mail.pop3.socketFactory.port", "995");
        props.put("mail.pop3.ssl.protocols", "TLSv1.2");

        // 2. Creates a javax.mail.Authenticator object.
        Authenticator auth = new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(ReadEmail.controller.getTestEmailForActivation(), ReadEmail.controller.getSecretPasswordForEmail());
            }
        };

        // 3. Creating mail session.
        Session session = Session.getDefaultInstance(props, auth);

        // 4. Get the POP3 store provider and connect to the store.
        Store store = session.getStore("pop3");
        store.connect("pop.gmail.com", 995, controller.getTestEmailForActivation(), controller.getSecretPasswordForEmail());

        // 5. Get folder and open the INBOX folder in the store.
        Folder inbox = store.getFolder("INBOX");
        inbox.open(Folder.READ_ONLY);

        // 6. Retrieve the messages from the folder.
        Message[] messages = inbox.getMessages();
        for (Message message : messages) {
            //message.writeTo(System.out);

            if (!message.getSubject().equals("RCGTiming and Scoring - Email Verification")) {
                continue;
            }
            if (!message.getRecipients(Message.RecipientType.TO)[0].toString().equals(email)) {
                continue;
            }

            OutputStream output = new OutputStream() {
                private final StringBuilder string = new StringBuilder();

                @Override
                public void write(int b) throws IOException {
                    this.string.append((char) b);
                }

                //Netbeans IDE automatically overrides this toString()
                public String toString() {
                    return this.string.toString();
                }
            };

            message.writeTo(output);

            String s = String.valueOf(output);

            Pattern hrefPattern = Pattern.compile("https:\\/\\/[^\\/]+\\/Login\\?action=Activate\\&code=.{50}");
            Matcher matcher = hrefPattern.matcher(s);

            if (matcher.find()) {
                int start = matcher.start();
                int end = matcher.end();
                activateHref = s.substring(start, end);
            }

            inbox.close(false);
            store.close();
        }
        return activateHref;
    }
}
