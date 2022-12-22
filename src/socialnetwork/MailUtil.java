package socialnetwork;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

import static socialnetwork.Main.SOCIAL_NETWORK_NAME;

class MailUtil {

    public static void sendMail(String recipient, String code) throws MessagingException {
        System.out.println("Preparing verification code...");
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        String myAccountEmail = "azharbayev@gmail.com";
        String password       = "pjfvrquflqjbsbdt";

        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(myAccountEmail, password);
            }
        });

        Message message = prepareMessage(session, myAccountEmail, recipient, code);

        assert message != null;
        Transport.send(message);
        System.out.println("Verification code sent successfully!");
    }

    private static Message prepareMessage(Session session, String account,
                                          String recipient, String code) {
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(account));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(recipient));
            message.setSubject("Verification code from \"" + SOCIAL_NETWORK_NAME + "\"");
            message.setContent("<div style=\"background:white;background-color:white;margin:0px auto;max-width:700px;align:center\"><div style=\"margin:0px auto;max-width:600px;align:center\"><div style=\"font-size:0px;text-align:left;direction:ltr;display:inline-block;vertical-align:top;width:100%\"><div style=\"font-family:PT Sans;font-size:16px;line-height:4;text-align:left;color:#000000\">Hey!</div><div style=\"font-family:PT Sans;font-size:16px;line-height:1.4;text-align:left;color:#000000\">You received this email because we received a password reset request with your email address.</div><div style=\"font-family:PT Sans;font-size:16px;line-height:0.1;text-align:left;color:#000000\"><pre> </pre></div></div></div><div style=\"margin:0px auto;max-width:400px;align:center\"><div style=\"font-size:0px;text-align:left;direction:ltr;display:inline-block;vertical-align:middle;width:100%\"><div style=\"font-family:PT Sans;font-size:16px;font-weight:700;line-height:1;text-align:center;color:#404040;height:25px;background-color:#cecece;border-radius:25px;vertical-align:middle;padding:10px 25px;padding-top:15px\">" + code + "</div></div></div><div style=\"margin:0px auto;max-width:600px;align:center\"><div style=\"font-size:0px;text-align:left;direction:ltr;display:inline-block;vertical-align:top;width:100%\"><div style=\"font-family:PT Sans;font-size:16px;line-height:0.1;text-align:left;color:#000000\"><pre> </pre></div><div style=\"font-family:PT Sans;font-size:16px;line-height:1.4;text-align:left;color:#000000\">If you didn't request this email, there's nothing to worry about — you can safely ignore it.</div></div></div></div>",
                               "text/html");
            return message;
        } catch (Exception ignored) {
        }
        return null;
    }

}
