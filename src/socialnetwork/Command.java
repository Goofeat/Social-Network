package socialnetwork;

import java.util.Date;

import static socialnetwork.DatabaseHandler.*;
import static socialnetwork.Logging.*;
import static socialnetwork.Main.*;
import static socialnetwork.Printer.*;

class Command {

    static void execute(String command) {
        switch (command) {
            case "help", "h" -> {
                printHelp();
                console();
            }
            case "quit", "exit" -> {
                quit();
                console();
            }
        }

        if (isAuthorized) {
            String option = null;

            if (command.matches("^\\w{1,12}( )\\w+$")) {
                String[] tmp = command.split(" ");
                command = tmp[0];
                option  = tmp[1];
            }

            switch (command) {
                // @formatter:off
                case "authorize", "auth", "register", "reg", "login" ->
                        System.out.println("You have already authorized");
                case "new", "new_post" -> newPost();
                case "wall", "w" -> printWall();
                case "my_posts" -> showMyPosts();
                case "posts" -> showPostsOf(option);
                case "comments", "coms" -> showCommentsOf(option);
                case "like" -> like(option);
                case "dislike", "dis" -> dislike(option);
                case "comment", "com" -> commentTo(option);
                case "follow", "subscribe", "sub" -> follow(option);
                case "unfollow", "unsubscribe", "unsub" -> unfollow(option);
                case "block" -> block(option);
                case "unblock" -> unblock(option);
                case "delete", "del" -> delete(option);
                case "my_profile", "my", "me" -> printProfile();
                case "profile" -> printProfile(option);
                case "change" -> forgot();
                case "settings" -> settings();
                case "sort" -> sort();
                case "logout" -> logOut();
                default -> printTip();
            }
        } else {
            switch (command) {
                case "forgot" -> forgot();
                case "authorize", "auth", "login" -> authorize();
                case "register", "reg" -> register();
                default -> printTip();
            }
        }

        console();
    }

    static void sort() {
//        System.out.println("Choose which type of sorting do you prefer (id, like, date, comment):");
        System.out.println(SPLITTER);
        System.out.println("Coming soon...");
        System.out.println(SPLITTER);
    }

    static void delete(String type) {
        if (type == null) {
            System.out.println("What type do you want to remove? (subscriber, post, comment)");
            type = in.next().toLowerCase();
        }

        switch (type) {
            case "subscriber" -> {
                System.out.println("Who do you want to delete from subscribers? (username)");
                String userID = in.next().toLowerCase();

                User user = USERS.get(userID);

                if (!isUserExists(user)) return;

                if (isMe(user)) {
                    System.out.println("You cannot delete yourself!");
                    return;
                }

                System.out.println(SPLITTER);
                if (!currentUser.getSubscribers().contains(user)) {
                    System.out.println("@" + userID + " does not follow you!");
                } else {
                    deleteSubscriber(user);
                    System.out.println("@" + userID + " is no longer subscribed to you!");
                }
                System.out.println(SPLITTER);
            }
            case "post", "comment" -> System.out.println("Coming soon...");
            default -> {
                System.out.println("Choose one of three options!");
                delete(null);
            }
        }
    }

    static void settings() {
        System.out.println(SPLITTER);
        System.out.println("Privacy settings:");
        System.out.println(SPLITTER);

        if (currentUser.isEmailHidden()) {
            System.out.println("Your email address is hidden (others cannot see) — to show, type \"/show email\"");
        } else {
            System.out.println("Your email address is not hidden (others can see) — to hide, type \"/hide email\"");
        }

        if (currentUser.isNumberHidden()) {
            System.out.println("Your phone number is hidden (others cannot see) — to show, type \"/show number\"");
        } else {
            System.out.println("Your phone number is not hidden (others can see) — to hide, type \"/hide number\"");
        }

        System.out.println("\nYou can show or hide both by writing \"/show both\" and \"/hide both\" respectively.");
        System.out.println("\nTip: You can return by typing /back or /cancel");
        System.out.println(SPLITTER);

        System.out.print(PROMPT);
        String input = in.nextLine().toLowerCase();

        if (input.startsWith("/hide")) {
            switch (input.substring(6)) {
                case "email" -> {
                    if (currentUser.isEmailHidden()) {
                        System.out.println("You have already hidden your email address.");
                    } else {
                        hideEmail();
                        System.out.println("Successful!");
                    }
                }
                case "number" -> {
                    if (currentUser.isNumberHidden()) {
                        System.out.println("You have already hidden your phone number.");
                    } else {
                        hideNumber();
                        System.out.println("Successful!");
                    }
                }
                case "both" -> {
                    if (currentUser.isEmailHidden() && currentUser.isNumberHidden()) {
                        System.out.println("You have already hidden your phone number and email address.");
                    } else {
                        hideEmail();
                        hideNumber();
                        System.out.println("Successful!");
                    }
                }
            }

            System.out.println("Successful!");
        } else if (input.startsWith("/show")) {
            switch (input.substring(6)) {
                case "email" -> {
                    if (!currentUser.isEmailHidden()) {
                        System.out.println("You have already shown your email address.");
                    } else {
                        showEmail();
                        System.out.println("Successful!");
                    }
                }
                case "number" -> {
                    if (!currentUser.isNumberHidden()) {
                        System.out.println("You have already shown your phone number.");
                    } else {
                        showNumber();
                        System.out.println("Successful!");
                    }
                }
                case "both" -> {
                    if (!currentUser.isEmailHidden() && !currentUser.isNumberHidden()) {
                        System.out.println("You have already shown your phone number and email address.");
                    } else {
                        showEmail();
                        showNumber();
                        System.out.println("Successful!");
                    }
                }
            }
        } else if (input.equals("/back") || input.equals("cancel")) {
            System.out.println("Going back...");
            System.out.println(SPLITTER);
            console();
        } else {
            settings();
        }
    }

    static void showMyPosts() {
        int postCount = currentUser.getPosts().size();

        if (postCount == 0) {
            System.out.println("You do not have any posts!");
            return;
        } else if (postCount == 1) System.out.println("You have 1 post:");
        else System.out.println("You have " + postCount + " posts:");

        printPosts(currentUser);
        System.out.println(SPLITTER);
    }

    static void showPostsOf(String userID) {
        if (userID == null) {
            System.out.println("Whose posts do you want to see? (username)");
            userID = in.next().toLowerCase();
        }

        User user = USERS.get(userID);

        if (!isUserExists(user)) return;

        if (isMe(user)) {
            System.out.println("You can view your posts by /my_posts");
            return;
        }

        if (isBlock(user)) return;

        System.out.println(SPLITTER);
        int postCount = user.getPosts().size();

        if (postCount == 0) {
            System.out.println("@" + user.getUserID() + " does not have any posts!");
            return;
        } else if (postCount == 1) System.out.println("@" + user.getUserID() + " has 1 post:");
        else System.out.println("@" + user.getUserID() + " has " + postCount + " posts:");

        printPosts(user);
        System.out.println(SPLITTER);
        in.nextLine();
    }

    static void showCommentsOf(String postID) {
        if (postID == null) {
            System.out.println("Which post would you like to see comments on? (post ID)");
            postID = in.next().toLowerCase();
        }

        Post post = POSTS.get(postID);
        User user = USERS.get(post.getUserID());

        if (isBlock(user)) return;

        int commentCount = post.getCommentsID().size();

        if (commentCount == 0) {
            System.out.println("This post does not have any comments!");
            return;
        } else if (commentCount == 1) System.out.println("This post have 1 comment:");
        else System.out.println("This post have " + commentCount + " comments:");

        printComments(post);
    }

    static void newPost() {
        System.out.println("Your message:");
        System.out.print(PROMPT);
        String message = in.nextLine();

        Post post = new Post(POSTS.size() + 100,
                             currentUser.getUserID(),
                             message, new Date());

        publishPost(post);
    }

    static void commentTo(String postID) {
        if (postID == null) {
            System.out.println("Which post would you like to comment on? (post ID)");
            postID = in.next().toLowerCase();
        }

        Post post = POSTS.get(postID);

        System.out.println(SPLITTER);
        if (isPostExists(post)) {
            System.out.println("Your message:");
            System.out.print(PROMPT);
            String message = in.nextLine();

            Comment comment = new Comment(COMMENTS.size() + 10000,
                                          currentUser.getUserID(),
                                          post.getPostID(),
                                          message, new Date());

            User user = USERS.get(post.getUserID());

            if (isBlock(user)) return;

            publishComment(comment);

            commentPost(comment, post);
            System.out.println(SPLITTER);
        }
    }

    static void unblock(String userID) {
        if (userID == null) {
            System.out.println("Who do you want to unblock? (username)");
            userID = in.next().toLowerCase();
        }

        User user = USERS.get(userID);

        if (!isUserExists(user)) return;

        if (isMe(user)) {
            System.out.println("You cannot unblock yourself!");
            return;
        }

        System.out.println(SPLITTER);
        if (currentUser.getBlockedUsers().contains(user)) {
            System.out.println("You did not block this user!");
        } else {
            unblockUser(user);
            System.out.println("Now you have unblocked @" + user.getUserID() + "!");
        }
    }

    static void block(String userID) {
        if (userID == null) {
            System.out.println("Who do you want to block? (username)");
            userID = in.next().toLowerCase();
        }

        User user = USERS.get(userID);

        if (!isUserExists(user)) return;

        if (isMe(user)) {
            System.out.println("You cannot block yourself!");
            return;
        }

        System.out.println(SPLITTER);
        if (currentUser.getBlockedUsers().contains(user)) {
            System.out.println("You have already blocked this user!");
        } else {
            blockUser(user);
            System.out.println("Now you have blocked @" + user.getUserID() + "!");
        }
    }

    static void unfollow(String userID) {
        if (userID == null) {
            System.out.println("Who do you want to unfollow? (username)");
            userID = in.next().toLowerCase();
        }

        User user = USERS.get(userID);

        if (!isUserExists(user)) return;

        if (isMe(user)) {
            System.out.println("You cannot unsubscribe from yourself!");
            return;
        }

        System.out.println(SPLITTER);
        if (!currentUser.getFollowsTo().contains(user)) {
            System.out.println("You do not follow this user!");
        } else {
            unfollowFromUser(user);
            System.out.println("You are no longer subscribed to @" + user.getUserID() + "!");
        }
        System.out.println(SPLITTER);
    }

    static void follow(String userID) {
        if (userID == null) {
            System.out.println("Who do you want to follow? (username)");
            userID = in.next().toLowerCase();
        }

        User user = USERS.get(userID);

        if (!isUserExists(user)) return;

        if (isMe(user)) {
            System.out.println("You cannot subscribe to yourself!");
            return;
        }

        if (isBlock(user)) return;

        System.out.println(SPLITTER);
        if (currentUser.getFollowsTo().contains(user)) {
            System.out.println("You have already followed this user!");
        } else {
            followToUser(user);
            System.out.println("You are subscribed to @" + user.getUserID() + "!");
        }
        System.out.println(SPLITTER);
    }

    static void logOut() {
        System.out.println("Are you sure you want to log out of your account? (Y / N)");
        char input;

        while (true) {
            System.out.print(PROMPT);
            input = in.next().toLowerCase().charAt(0);

            if (input == 'n') break;

            if (input == 'y') {
                currentUser  = null;
                isAuthorized = false;
                welcome();
                in.nextLine();
                break;
            } else {
                System.out.println("Tip: Y for Yes, N for No.");
                in.nextLine();
            }
        }
    }

    static void dislike(String postID) {
        if (postID == null) {
            System.out.println("Which post would you like to dislike? (post ID)");
            postID = in.next().toLowerCase();
        }

        Post post = POSTS.get(postID);

        System.out.println(SPLITTER);
        if (isPostExists(post)) {
            User user = USERS.get(post.getUserID());
            if (isBlock(user)) return;

            if (!post.getLikedUsers().contains(currentUser.getUserID())) {
                System.out.println("You did not like this post!");
            } else {
                dislikePost(post);
                System.out.println("You disliked the post with ID: " + post.getPostID());
            }
            System.out.println(SPLITTER);
        }
    }

    static void like(String postID) {
        if (postID == null) {
            System.out.println("Which post would you like to like? (post ID)");
            postID = in.next().toLowerCase();
        }

        Post post = POSTS.get(postID);

        System.out.println(SPLITTER);
        if (isPostExists(post)) {
            User user = USERS.get(post.getUserID());
            if (isBlock(user)) return;

            if (post.getLikedUsers().contains(currentUser.getUserID())) {
                System.out.println("You have already liked this post!");
            } else {
                likePost(post);
                System.out.println("You liked the post with ID: " + post.getPostID());
            }
            System.out.println(SPLITTER);
        }
    }

}
