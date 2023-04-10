package socialnetwork;

import java.util.Map;
import java.util.Objects;

import static socialnetwork.Command.showPostsOf;
import static socialnetwork.Main.*;

class Printer {

    static void printProfile() {
        System.out.println(SPLITTER);
        System.out.println("Your profile:");
        System.out.println(SPLITTER);
        System.out.printf("| Your username: @%s%n", currentUser.getUserID());

        System.out.printf("| Your full name: %s (%s years old)%n",
                          currentUser.getFullName(), currentUser.getAge());

        System.out.printf("| Your gender: %s%n",
                          Objects.equals(currentUser.getGender(), "M") ? "Male" : "Female");

        System.out.printf("| Your email: %s (%s)%n",
                          currentUser.getEmail(),
                          currentUser.isEmailHidden() ? "others cannot see" : "others can see");

        System.out.printf("| Your phone number: %s (%s)%n",
                          formatPhone(currentUser.getPhoneNumber()),
                          currentUser.isNumberHidden() ? "others cannot see" : "others can see");

        System.out.println(SPLITTER);
        System.out.printf("| Posts: %d%n", currentUser.getPosts().size());
        System.out.printf("| Following: %d%n", currentUser.getFollowsTo().size());
        System.out.printf("| Followers: %d%n", currentUser.getSubscribers().size());
        System.out.println(SPLITTER);
    }

    static void printProfile(String userID) {
        if (userID == null) {
            System.out.println("Whose profile do you want to see? (username)");
            userID = in.next().toLowerCase();
        }

        User user = USERS.get(userID);

        if (!isUserExists(user)) return;

        if (isMe(user)) {
            System.out.println("You can view your profile by /my_profile (/my, /me)");
            return;
        }

        if (isBlock(user)) return;

        System.out.println(SPLITTER);
        System.out.printf("@%s's profile:%n", user.getUserID());
        System.out.println(SPLITTER);
        System.out.printf("| Full name: %s (%s years old)%n",
                          user.getFullName(), user.getAge());

        System.out.printf("| Gender: %s%n",
                          Objects.equals(user.getGender(), "M") ? "Male" : "Female");

        if (user.isEmailHidden()) System.out.println("| Email: [hidden]");
        else System.out.printf("| Email: %s%n", user.getEmail());


        if (user.isNumberHidden()) System.out.println("| Phone number: [hidden]");
        else System.out.printf("| Phone number: %s%n", formatPhone(user.getPhoneNumber()));

        System.out.println(SPLITTER);
        System.out.printf("| Posts: %d%n", user.getPosts().size());
        System.out.printf("| Following: %d%n", user.getFollowsTo().size());
        System.out.printf("| Followers: %d%n", user.getSubscribers().size());
        System.out.println(SPLITTER);
    }

    static void printWall() {
        if (currentUser.getFollowsTo().size() == 0) {
            System.out.println(SPLITTER);
            System.out.println("You don't follow anyone");
        }

        for (User user : currentUser.getFollowsTo()) {
            showPostsOf(user.getUserID());
        }
        System.out.println(SPLITTER);
    }

    static void printComments(Post post) {
        for (Map.Entry<String, Comment> entry : COMMENTS.entrySet()) {
            if (Objects.equals(entry.getValue().getPostID(), post.getPostID())) {
                System.out.println(SPLITTER);
                System.out.print("Comment ID: ");
                System.out.println(entry.getValue().getCommentID());
                System.out.print("| Publish Date: ");
                System.out.println(entry.getValue().getPublishDate());
                System.out.print("| From: @");
                System.out.println(entry.getValue().getUserID());
                System.out.print("| Message: ");
                System.out.println(entry.getValue().getMessage());
            }
        }
    }

    static void printPosts(User user) {
        for (Map.Entry<String, Post> entry : POSTS.entrySet()) {
            if (Objects.equals(entry.getValue().getUserID(), user.getUserID())) {
                System.out.println(SPLITTER);
                System.out.print("Post ID: ");
                System.out.println(entry.getKey());
                System.out.print("| Publish Date: ");
                System.out.println(entry.getValue().getPublishDate());
                System.out.print("| Message: ");
                System.out.println(entry.getValue().getMessage());
                System.out.print("| Likes: ");
                System.out.println(entry.getValue().getLikedUsers().size());
                System.out.print("| Comments: ");
                System.out.println(entry.getValue().getCommentsID().size());
            }
        }
    }

    static void printTip() {
        System.out.println(SPLITTER);
        System.out.println("Type /help (/h) to see all available commands.");
        System.out.println(SPLITTER);
    }

    static void printHelp() {
        System.out.println(SPLITTER);
        System.out.println("Available commands for you:");
        System.out.println(SPLITTER);
        if (isAuthorized) {
            System.out.print(
                    """
                    /help (/h) — view available commands.
                    /my_profile (/my, /me) — view your profile.
                    /profile <username> – view the profile of another user.
                    /wall (/w) — view your wall.
                    /my_posts — view your posts.
                    /posts <username> — view the posts of another user.
                    /comments <postID> (/coms) — view all comments on a specific post.
                    /new_post (/new) — publish a new post.
                    /like <postID> — like a specific post.
                    /dislike <postID> (/dis) — dislike a specific post.
                    /comment <postID> (/com) — comment on a specific post.
                    /follow <username> (/subscribe, /sub) — subscribe to another user.
                    /unfollow <username> (/unsubscribe, /unsub) — unsubscribe another user.
                    /block <username> — block another user.
                    /unblock <username> — unblock another user.
                    /delete <type> (/del) — delete a specific type (subscriber, post, comment).
                    /change — change your current password.
                    /settings — privacy settings.
                    /logout — log out of your account.
                    /quit or /exit — exit the program.
                    """
            );
        } else {
            System.out.print(
                    """
                    /help (/h) — see available commands.
                    /authorize (/auth, /login) — log in to your account.
                    /register (/reg) — create a new account.
                    /forgot — recover account password.
                    /quit or /exit — exit the program.
                    """
            );
        }
        System.out.println(SPLITTER);
    }

    static String formatPhone(String phoneNumber) {
        return String.format(
                "+7 (7%c%c) %c%c%c %c%c %c%c",
                phoneNumber.charAt(2),
                phoneNumber.charAt(3),
                phoneNumber.charAt(4),
                phoneNumber.charAt(5),
                phoneNumber.charAt(6),
                phoneNumber.charAt(7),
                phoneNumber.charAt(8),
                phoneNumber.charAt(9),
                phoneNumber.charAt(10)
        );
    }

}
