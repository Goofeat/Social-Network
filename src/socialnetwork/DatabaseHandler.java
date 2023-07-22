package socialnetwork;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import static socialnetwork.Const.*;
import static socialnetwork.Main.*;

class DatabaseHandler extends Configs {

    private static final Statement statement;

    static {
        try {
            statement = getDbConnection()
                    .createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                                     ResultSet.CONCUR_UPDATABLE);
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static Connection getDbConnection()
            throws ClassNotFoundException, SQLException {
        String URL = "jdbc:mysql://" + DB_HOST + ":" + DB_PORT + "/" + DB_NAME;

        Class.forName("com.mysql.cj.jdbc.Driver");

        return DriverManager.getConnection(URL, DB_USER, DB_PASS);
    }

    public static void readComments() throws SQLException {
        String SQL = "SELECT " + COMMENT_PROPERTIES + " FROM " + COMMENTS_TABLE;

        ResultSet executeSQL = statement.executeQuery(SQL);

        while (executeSQL.next()) {
            Comment comment = readComment(executeSQL);
            COMMENTS.put(comment.getCommentID(), comment);

            POSTS.get(comment.getPostID())
                 .getCommentsID()
                 .add(comment.getCommentID());

            USERS.get(comment.getUserID())
                 .getComments()
                 .add(comment);
        }
    }

    public static Comment readComment(ResultSet input) throws SQLException {
        String str  = input.getString(5);
        Date   date = null;
        try {
            date = new SimpleDateFormat("dd-M-yyyy hh:mm:ss").parse(str);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Comment(input.getInt(1),
                           input.getString(2),
                           input.getString(3),
                           input.getString(4),
                           date);
    }

    public static void readLikes() throws SQLException {
        String SQL = "SELECT " + POSTS_POST_ID + "," + POSTS_LIKED_USERS + " FROM " + POSTS_TABLE;

        ResultSet executeSQL = statement.executeQuery(SQL);

        while (executeSQL.next()) {
            String postID     = executeSQL.getString(1);
            String likedUsers = executeSQL.getString(2);

            Set<String> tmpSet = POSTS.get(postID).getLikedUsers();

            if (likedUsers == null) continue;

            tmpSet.addAll(Arrays.asList(likedUsers.split("\\|")));
        }
    }

    public static void publishPost(Post newPost) {
        POSTS.put(newPost.getPostID(), newPost);
        currentUser.getPosts().add(newPost);

        String insert =
                "INSERT INTO " + POSTS_TABLE + "(" + POST_PROPERTIES + ")" + "VALUES (?,?,?,?)";

        try (PreparedStatement prSt = getDbConnection().prepareStatement(insert)) {
            prSt.setString(1, newPost.getPostID());
            prSt.setString(2, newPost.getUserID());
            prSt.setString(3, newPost.getMessage());
            prSt.setString(4, newPost.getPublishDate());

            prSt.executeUpdate();

            System.out.println("Successful!\nNew post ID: " + newPost.getPostID());
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void readPosts() throws SQLException {
        String SQL = "SELECT " + POST_PROPERTIES + " FROM " + POSTS_TABLE;

        ResultSet executeSQL = statement.executeQuery(SQL);

        while (executeSQL.next()) {
            Post post = readPost(executeSQL);
            POSTS.put(post.getPostID(), post);
            USERS.get(post.getUserID()).getPosts().add(post);
        }
    }

    public static Post readPost(ResultSet input) throws SQLException {
        String str  = input.getString(4);
        Date   date = null;
        try {
            date = new SimpleDateFormat("dd-M-yyyy hh:mm:ss").parse(str);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Post(input.getInt(1),
                        input.getString(2),
                        input.getString(3),
                        date);
    }

    public static void readBlockedBy() throws SQLException {
        String SQL =
                "SELECT " + USERS_USER_ID + "," + USERS_BLOCKED_BY +
                        " FROM " + USERS_TABLE;
        ResultSet executeSQL = statement.executeQuery(SQL);

        while (executeSQL.next()) {
            String userID    = executeSQL.getString(1);
            String blockedBy = executeSQL.getString(2);

            Set<User> tmpSet = USERS.get(userID).getBlockedBy();

            if (blockedBy == null) continue;

            for (String str : blockedBy.split("\\|")) {
                tmpSet.add(USERS.get(str));
            }
        }
    }

    public static void readBlockedUsers() throws SQLException {
        String SQL =
                "SELECT " + USERS_USER_ID + "," + USERS_BLOCKED_USERS +
                        " FROM " + USERS_TABLE;
        ResultSet executeSQL = statement.executeQuery(SQL);

        while (executeSQL.next()) {
            String userID      = executeSQL.getString(1);
            String blockedList = executeSQL.getString(2);

            Set<User> tmpSet = USERS.get(userID).getBlockedUsers();

            if (blockedList == null) continue;

            for (String str : blockedList.split("\\|")) {
                tmpSet.add(USERS.get(str));
            }
        }
    }

    public static void readSubscribers() throws SQLException {
        String    SQL        =
                "SELECT " + USERS_USER_ID + "," + USERS_SUBSCRIBERS + " FROM " + USERS_TABLE;
        ResultSet executeSQL = statement.executeQuery(SQL);

        while (executeSQL.next()) {
            String userID      = executeSQL.getString(1);
            String subscribers = executeSQL.getString(2);

            Set<User> tmpSet = USERS.get(userID).getSubscribers();

            if (subscribers == null) continue;

            for (String str : subscribers.split("\\|"))
                tmpSet.add(USERS.get(str));
        }
    }

    public static void readFollows() throws SQLException {
        String SQL =
                "SELECT " + USERS_USER_ID + "," + USERS_FOLLOWS_TO + " FROM " + USERS_TABLE;
        ResultSet executeSQL = statement.executeQuery(SQL);

        while (executeSQL.next()) {
            String userID    = executeSQL.getString(1);
            String followsTo = executeSQL.getString(2);

            Set<User> tmpSet = USERS.get(userID).getFollowsTo();

            if (followsTo == null) continue;

            for (String str : followsTo.split("\\|")) {
                tmpSet.add(USERS.get(str));
            }
        }
    }

    public static void readUsers() throws SQLException {
        String SQL = "SELECT " + USER_PROPERTIES + " FROM " + USERS_TABLE;

        ResultSet executeSQL = statement.executeQuery(SQL);

        while (executeSQL.next()) {
            User user = readUser(executeSQL);
            USERS.put(user.getUserID(), user);
        }
    }

    public static User readUser(ResultSet input) throws SQLException {
        return new User(input.getString(1), input.getString(2),
                        input.getString(3), input.getString(4),
                        input.getString(5), input.getString(6),
                        input.getString(7), input.getString(8),
                        input.getBoolean(9), input.getBoolean(10));
    }

    public static void signUpUser(User newUser) {
        USERS.put(newUser.getUserID(), newUser);

        String insert =
                "INSERT INTO " + USERS_TABLE + "(" + USER_PROPERTIES + ")" + "VALUES (?,?,?,?,?,?,?,?,?,?)";

        try (PreparedStatement prSt = getDbConnection().prepareStatement(insert)) {
            prSt.setString(1, newUser.getUserID());
            prSt.setString(2, newUser.getPassword());
            prSt.setString(3, newUser.getFirstName());
            prSt.setString(4, newUser.getLastName());
            prSt.setString(5, newUser.getAge());
            prSt.setString(6, newUser.getGender());
            prSt.setString(7, newUser.getEmail());
            prSt.setString(8, newUser.getPhoneNumber());
            prSt.setBoolean(9, true);
            prSt.setBoolean(10, true);

            prSt.executeUpdate();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void unblockUser(User user) {
        currentUser.getBlockedUsers().remove(user);
        user.getBlockedBy().remove(currentUser);

        blockedListUpdate(user);
    }

    public static void blockUser(User user) {
        currentUser.getBlockedUsers().add(user);
        user.getBlockedBy().add(currentUser);

        unfollowFromUser(user);
        deleteSubscriber(user);

        blockedListUpdate(user);
    }

    public static void blockedListUpdate(User user) {
        try {
            PreparedStatement blockedUsersPS =
                    getDbConnection().prepareStatement("UPDATE " + USERS_TABLE +
                                                               " SET " + USERS_BLOCKED_USERS + "=?" +
                                                               " WHERE " + USERS_USER_ID + "=?");

            if (currentUser.getBlockedUsers().size() == 0)
                blockedUsersPS.setString(1, null);
            else blockedUsersPS.setString(1, currentUser.getBlockedUsers().toString()
                                                        .replace(", ", "|")
                                                        .replace("[", "")
                                                        .replace("]", ""));

            blockedUsersPS.setString(2, currentUser.getUserID());

            PreparedStatement blockedByPS =
                    getDbConnection().prepareStatement("UPDATE " + USERS_TABLE +
                                                               " SET " + USERS_BLOCKED_BY + "=?" +
                                                               " WHERE " + USERS_USER_ID + "=?");

            if (user.getBlockedBy().size() == 0)
                blockedByPS.setString(1, null);
            else blockedByPS.setString(1, user.getBlockedBy().toString()
                                              .replace(", ", "|")
                                              .replace("[", "")
                                              .replace("]", ""));

            blockedByPS.setString(2, user.getUserID());

            blockedUsersPS.executeUpdate();
            blockedByPS.executeUpdate();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void deleteSubscriber(User subscriber) {
        subscriber.getFollowsTo().remove(currentUser);
        currentUser.getSubscribers().remove(subscriber);

        followersUpdate(subscriber);
        followersUpdate(currentUser);
    }

    public static void unfollowFromUser(User from) {
        currentUser.getFollowsTo().remove(from);
        from.getSubscribers().remove(currentUser);

        followersUpdate(from);
        followersUpdate(currentUser);
    }

    public static void followToUser(User to) {
        currentUser.getFollowsTo().add(to);
        to.getSubscribers().add(currentUser);

        followersUpdate(to);
        followersUpdate(currentUser);
    }

    public static void followersUpdate(User user) {
        try {
            PreparedStatement followsToPS =
                    getDbConnection().prepareStatement("UPDATE " + USERS_TABLE +
                                                               " SET " + USERS_FOLLOWS_TO + "=?" +
                                                               " WHERE " + USERS_USER_ID + "=?");

            if (user.getFollowsTo().size() == 0)
                followsToPS.setString(1, null);
            else followsToPS.setString(1, user.getFollowsTo().toString()
                                              .replace(", ", "|")
                                              .replace("[", "")
                                              .replace("]", ""));

            followsToPS.setString(2, user.getUserID());

            PreparedStatement subscribersPS =
                    getDbConnection().prepareStatement("UPDATE " + USERS_TABLE +
                                                               " SET " + USERS_SUBSCRIBERS + "=?" +
                                                               " WHERE " + USERS_USER_ID + "=?");

            if (user.getSubscribers().size() == 0)
                subscribersPS.setString(1, null);
            else subscribersPS.setString(1, user.getSubscribers().toString()
                                                .replace(", ", "|")
                                                .replace("[", "")
                                                .replace("]", ""));

            subscribersPS.setString(2, user.getUserID());

            followsToPS.executeUpdate();
            subscribersPS.executeUpdate();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void publishComment(Comment newComment) {
        COMMENTS.put(newComment.getPostID(), newComment);
        currentUser.getComments().add(newComment);

        String insert =
                "INSERT INTO " + COMMENTS_TABLE + "(" + COMMENT_PROPERTIES + ")" + "VALUES (?,?,?,?,?)";

        try (PreparedStatement prSt = getDbConnection().prepareStatement(insert)) {
            prSt.setString(1, newComment.getCommentID());
            prSt.setString(2, newComment.getUserID());
            prSt.setString(3, newComment.getPostID());
            prSt.setString(4, newComment.getMessage());
            prSt.setString(5, newComment.getPublishDate());

            prSt.executeUpdate();

            System.out.println("Successful!\nNew comment ID: " + newComment.getCommentID());
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void commentPost(Comment comment, Post post) {
        post.getCommentsID().add(comment.getCommentID());

        commentsUpdate(post);
    }

    public static void commentsUpdate(Post post) {
        try {
            PreparedStatement commentsPS =
                    getDbConnection().prepareStatement("UPDATE " + POSTS_TABLE +
                                                               " SET " + POSTS_COMMENTS_ID + "=?" +
                                                               " WHERE " + POSTS_POST_ID + "=?");

            if (post.getCommentsID().size() == 0)
                commentsPS.setString(1, null);
            else commentsPS.setString(1, post.getCommentsID().toString()
                                             .replace(", ", "|")
                                             .replace("[", "")
                                             .replace("]", ""));

            commentsPS.setString(2, post.getPostID());

            commentsPS.executeUpdate();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void dislikePost(Post post) {
        post.getLikedUsers().remove(currentUser.getUserID());

        likesUpdate(post);
    }

    public static void likePost(Post post) {
        post.getLikedUsers().add(currentUser.getUserID());

        likesUpdate(post);
    }

    public static void likesUpdate(Post post) {
        try {
            PreparedStatement likedUsersPS =
                    getDbConnection().prepareStatement("UPDATE " + POSTS_TABLE +
                                                               " SET " + POSTS_LIKED_USERS + "=?" +
                                                               " WHERE " + POSTS_POST_ID + "=?");

            if (post.getLikedUsers().size() == 0)
                likedUsersPS.setString(1, null);
            else likedUsersPS.setString(1, post.getLikedUsers().toString()
                                               .replace(", ", "|")
                                               .replace("[", "")
                                               .replace("]", ""));

            likedUsersPS.setString(2, post.getPostID());

            likedUsersPS.executeUpdate();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void changePassword(String email, String newPassword) {
        try {
            PreparedStatement prSt =
                    getDbConnection().prepareStatement("UPDATE " + USERS_TABLE +
                                                               " SET " + USERS_PASSWORD + "=?" +
                                                               " WHERE " + USERS_EMAIL + "=?");

            prSt.setString(1, newPassword);
            prSt.setString(2, email);

            prSt.executeUpdate();

            readUsers();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    static Set<String> getProperties(String properties) {
        try {
            ResultSet resultSet =
                    statement.executeQuery("SELECT " + properties + " FROM " + USERS_TABLE);

            Set<String> tmpSet = new HashSet<>();

            while (resultSet.next()) {
                tmpSet.add(resultSet.getString(1));
            }

            return tmpSet;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    static void hideEmail() {
        currentUser.setEmailHidden(true);

        hide(USERS_IS_EMAIL_HIDDEN);
    }

    static void hideNumber() {
        currentUser.setNumberHidden(true);

        hide(USERS_IS_NUMBER_HIDDEN);
    }

    private static void hide(String toHide) {
        try {
            PreparedStatement prSt =
                    getDbConnection().prepareStatement("UPDATE " + USERS_TABLE +
                                                               " SET " + toHide + "=?" +
                                                               " WHERE " + USERS_USER_ID + "=?");

            prSt.setBoolean(1, true);
            prSt.setString(2, currentUser.getUserID());

            prSt.executeUpdate();

            readUsers();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    static void showEmail() {
        currentUser.setEmailHidden(false);

        show(USERS_IS_EMAIL_HIDDEN);
    }

    static void showNumber() {
        currentUser.setNumberHidden(false);

        show(USERS_IS_NUMBER_HIDDEN);
    }

    private static void show(String toShow) {
        try {
            PreparedStatement prSt =
                    getDbConnection().prepareStatement("UPDATE " + USERS_TABLE +
                                                               " SET " + toShow + "=?" +
                                                               " WHERE " + USERS_USER_ID + "=?");

            prSt.setBoolean(1, false);
            prSt.setString(2, currentUser.getUserID());

            prSt.executeUpdate();

            readUsers();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

}
