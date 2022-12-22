package socialnetwork;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import static socialnetwork.Command.execute;
import static socialnetwork.DatabaseHandler.*;
import static socialnetwork.Printer.printTip;

class Main {

    public static final    Scanner              in                  = new Scanner(System.in);
    public static final    String               SPLITTER            = "—".repeat(50);
    public static final    String               PROMPT              = "> ";
    public static final    String               SOCIAL_NETWORK_NAME = "TestName";
    protected static final Map<String, User>    USERS               = new HashMap<>();
    protected static final Map<String, Post>    POSTS               = new HashMap<>();
    protected static final Map<String, Comment> COMMENTS            = new HashMap<>();
    protected static       User                 currentUser;
    protected static       boolean              isAuthorized;

    public static void main(String[] args) throws SQLException {
        readUsers();

        readFollows();
        readSubscribers();
        readBlockedUsers();
        readBlockedBy();

        readPosts();
        readComments();
        readLikes();

        welcome();
        console();
    }

    public static void welcome() {
        System.out.println("Welcome to Social Network \"" + SOCIAL_NETWORK_NAME + "\"!");
        System.out.println(SPLITTER);
        System.out.println("There are " + USERS.size() + " users in our social network!\n");
        System.out.println("Type /help (/h) to see all available commands.");
        System.out.println(SPLITTER);
    }

    public static void console() {
        System.out.print(PROMPT);
        String userInput = in.nextLine().toLowerCase();

        if (!userInput.matches("^/[\\w( )]+")) {
            printTip();
            console();
        }

        execute(userInput.replace("/", ""));
    }

    public static boolean isBlock(User user) {
        if (currentUser.getBlockedUsers().contains(user)) {
            System.out.println("You blocked this user.");
            return true;
        }

        if (currentUser.getBlockedBy().contains(user)) {
            System.out.println("This user blocked you.");
            return true;
        }

        return false;
    }

    public static boolean isUserExists(User user) {
        if (user == null) {
            System.out.println("There is no user with this username!");
            return false;
        } else {
            return true;
        }
    }

    public static boolean isPostExists(Post post) {
        if (post == null) {
            System.out.println("There is no post with this ID!");
            return false;
        } else {
            return true;
        }
    }

    public static boolean isMe(User user) {
        return user.equals(currentUser);
    }

}