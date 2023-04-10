package socialnetwork;

import javax.mail.MessagingException;
import java.util.Random;

import static socialnetwork.Const.USERS_EMAIL;
import static socialnetwork.Const.USERS_PHONENUMBER;
import static socialnetwork.DatabaseHandler.*;
import static socialnetwork.Main.*;

class Logging {

    static void quit() {
        System.out.println("Are you sure you want to exit from app? (Y / N)");
        char input;

        while (true) {
            System.out.print(PROMPT);
            input = in.next().toLowerCase().charAt(0);

            if (input == 'n') break;

            if (input == 'y') {
                System.exit(0);
            } else {
                System.out.println("Tip: Y for Yes, N for No.");
                in.nextLine();
            }
        }
    }

    static void authorize() {
        System.out.println(SPLITTER);
        System.out.println(
                "Tip: If you forgot your username or password, type /forgot or /register\n" +
                        "Tip: You can return by typing /back or /cancel");
        System.out.println(SPLITTER);
        String userIDTEMP;

        while (true) {
            System.out.print("Your username: ");
            userIDTEMP = in.next().toLowerCase();

            checkREG(userIDTEMP);

            if (!USERS.containsKey(userIDTEMP)) {
                System.out.println("No user with this username!");
            } else break;
        }

        while (true) {
            System.out.print("Your password: ");
            String passwordTEMP = in.next();

            checkREG(passwordTEMP);

            if (!USERS.get(userIDTEMP).getPassword().equals(passwordTEMP)) {
                System.out.println("Incorrect password!");
            } else break;
        }

        System.out.println(SPLITTER);
        System.out.println("You have successfully authorized!");
        System.out.println(SPLITTER);
        in.nextLine();

        currentUser  = USERS.get(userIDTEMP);
        isAuthorized = true;
    }

    static void forgot() {
        final String VERIFY_CODE = String.format("%05d", new Random().nextInt(99999));
        int          attempts    = 3;
        String email;

        if (isAuthorized) {
            System.out.println("We'll send you the verification code to your email address.");
            email = currentUser.getEmail();
        } else {
            System.out.print("Your email: ");
            email = in.next().toLowerCase();
        }

        System.out.println(SPLITTER);

        if (getProperties(USERS_EMAIL).contains(email)) {
            try {
                MailUtil.sendMail(email, VERIFY_CODE);
            } catch (MessagingException e) {
                System.out.println("We couldn't send you the verification code...");
                System.out.println("Going back...");
                console();
            }

            System.out.println(SPLITTER);
            System.out.println("Tip: You can return by typing /back or /cancel");
            System.out.println(SPLITTER);

            while (attempts-- > 0) {
                System.out.print("Enter your verification code: ");
                String input = in.next();

                checkREG(input);

                if (input.equals(VERIFY_CODE)) {
                    System.out.print("Great!\nNow, your new password: ");
                    String password = in.next();

                    if (password.length() < 5 ||
                            password.length() > 30 ||
                            password.contains(" ")) {
                        System.out.println("Incorrect password!");
                    } else {
                        changePassword(email, password);
                        System.out.println("You have successfully changed your password!\n" +
                                                   "Try not to forget it =)");
                        in.nextLine();
                        break;
                    }
                } else {
                    if (attempts == 0) System.out.println("You did not change your password.");
                    else System.out.println("Oops! You have " + attempts +
                                                    (attempts == 1 ? " attempt"
                                                                   : " attempts") + " left!");
                    in.nextLine();
                }
            }
        } else {
            System.out.println("No user with this email!");
            in.nextLine();
        }
    }

    static void register() {
        System.out.println(SPLITTER);
        System.out.println("Tip: You can return by typing /back or /cancel");
        System.out.println(SPLITTER);
        String userIDREG, firstNameREG, lastNameREG, ageREG,
                emailREG, genderREG, phoneNumberREG, passwordREG;

        while (true) {
            System.out.print("Your username: ");
            userIDREG = in.next().toLowerCase();

            checkREG(userIDREG);

            if (!userIDREG.matches("[a-z\\d]{4,30}")) {
                System.out.println(
                        "Username may have any word characters and digits. Length of username from 4 to 30");
            } else {
                if (USERS.containsKey(userIDREG)) {
                    System.out.println("This username is not available!");
                } else break;
            }
        }

        while (true) {
            System.out.print("Your first name: ");
            firstNameREG = in.next();

            checkREG(firstNameREG);

            if (!firstNameREG.matches("[A-Z][a-z]{3,25}")) {
                System.out.println("Incorrect first name!");
            } else break;
        }

        while (true) {
            System.out.print("Your last name: ");
            lastNameREG = in.next();

            checkREG(lastNameREG);

            if (!lastNameREG.matches("[A-Z][a-z]{3,25}")) {
                System.out.println("Incorrect last name!");
            } else break;
        }

        while (true) {
            System.out.print("Your age: ");
            ageREG = in.next();

            checkREG(ageREG);

            if (ageREG.matches("\\d") ||
                    ageREG.matches("1[01234]") ||
                    ageREG.matches("[6789][6789]")) {
                System.out.println("Incorrect age! (From 15 to 65)");
            } else break;
        }

        while (true) {
            System.out.print("Your gender (M or F): ");
            genderREG = in.next();

            checkREG(genderREG);

            if (!genderREG.matches("[MFmf]")) {
                System.out.println("Incorrect gender!");
            } else break;
        }

        while (true) {
            System.out.print("Your email: ");
            emailREG = in.next().toLowerCase();

            checkREG(emailREG);

            if (!emailREG.matches(
                    "^[A-Za-z\\d+_.-]+@(.+)$")) {
                System.out.println("Incorrect email address!");
            } else {
                if (getProperties(USERS_EMAIL).contains(emailREG)) {
                    System.out.println("This email is not available!");
                } else break;
            }
        }

        while (true) {
            System.out.print("Your phone number (+77xxxxxxxxx or 87xxxxxxxxx): ");
            phoneNumberREG = in.next();

            checkREG(phoneNumberREG);

            if (!phoneNumberREG.matches("(\\+77)\\d{9}")
                    && !phoneNumberREG.matches("(87)\\d{9}")) {
                System.out.println("Incorrect phone number!");
            } else {
                phoneNumberREG = phoneNumberREG.replace("+7", "8");
                if (getProperties(USERS_PHONENUMBER).contains(phoneNumberREG)) {
                    System.out.println("This phone number is not available!");
                } else break;
            }
        }

        while (true) {
            System.out.print("Your password: ");
            passwordREG = in.next();

            checkREG(passwordREG);

            if (passwordREG.length() < 5 ||
                    passwordREG.length() > 30 ||
                    passwordREG.contains(" ")) {
                System.out.println("Incorrect password!");
            } else break;
        }

        System.out.println(SPLITTER);
        System.out.println("You have successfully registered!");
        System.out.println(SPLITTER);
        in.nextLine();

        currentUser  = new User(userIDREG, passwordREG, firstNameREG, lastNameREG,
                                ageREG, genderREG, emailREG, phoneNumberREG, true, true);
        isAuthorized = true;

        signUpUser(currentUser);
    }

    static void checkREG(String input) {
        if (input.charAt(0) == '/') {
            switch (input.replace("/", "")) {
                case "back", "cancel" -> {
                    System.out.println("Going back...");
                    System.out.println(SPLITTER);
                    in.nextLine();
                    welcome();
                    console();
                }
                case "forgot" -> forgot();
            }
        }
    }

}
