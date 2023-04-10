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
    // Map with all users, where the key is the user's ID and the value is the user's data
    protected static final Map<String, User>    USERS               = new HashMap<>();
    // Map with all posts, where the key is the post's ID and the value is the post's data
    protected static final Map<String, Post>    POSTS               = new HashMap<>();
    // Map with all users, where the key is the comment's ID and the value is the comment's data.
    protected static final Map<String, Comment> COMMENTS            = new HashMap<>();
    // If the user is logged in, then the value is the user's data
    protected static       User                 currentUser;
    // If the user is logged in, then the value is true
    protected static       boolean              isAuthorized;

    // Read all the data from database
    public static void main(String[] args) throws SQLException {
        // Read all the users
        readUsers();

        // Read who followed whom and who blocked whom
        readFollows();
        readSubscribers();
        readBlockedUsers();
        readBlockedBy();

        // Read posts, comments and likes
        readPosts();
        readComments();
        readLikes();

        // Print welcome and console
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

        // Check if user input is a command (begins with "/") or not
        if (!userInput.matches("^/[\\w( )]+")) {
            printTip();
            console();
        }

        // If yes, then try to execute command
        execute(userInput.replace("/", ""));
    }

    // Check if a user blocked a specific user or blocked by a specific user
    public static boolean isBlock(User user) {
        if (currentUser.getBlockedUsers().contains(user)) {
            System.out.println("You blocked this user.");
            in.nextLine();
            return true;
        }

        if (currentUser.getBlockedBy().contains(user)) {
            System.out.println("This user blocked you.");
            in.nextLine();
            return true;
        }

        return false;
    }

    // Check if a particular user exists or not
    public static boolean isUserExists(User user) {
        if (user == null) {
            System.out.println("There is no user with this username!");
            return false;
        } else {
            return true;
        }
    }

    // Check if a particular post exists or not
    public static boolean isPostExists(Post post) {
        if (post == null) {
            System.out.println("There is no post with this ID!");
            return false;
        } else {
            return true;
        }
    }

    // Check if a particular user is the current logged-in user or not
    public static boolean isMe(User user) {
        return user.equals(currentUser);
    }

}