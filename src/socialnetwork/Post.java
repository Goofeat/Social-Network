package socialnetwork;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

class Post extends User {

    // Post properties
    private String      postID;
    private String      userID;
    private String      message;
    private String      publishDate;
    // Set commentsID is where the current post's comments are stored
    private Set<String> commentsID;
    // Set likedUsers is where the current post's likes are stored
    private Set<String> likedUsers;

    // Empty constructor
    public Post() {
    }

    // Constructor with all data except commentsID, likedUsers
    public Post(int postID, String userID, String message, Date publishDate) {
        this.postID      = String.valueOf(postID);
        this.userID      = userID;
        this.message     = message;
        this.publishDate = new SimpleDateFormat("dd-M-yyyy hh:mm:ss").format(publishDate);
        commentsID       = new HashSet<>();
        likedUsers       = new HashSet<>();
    }

    public Set<String> getLikedUsers() {
        return likedUsers;
    }

    public Set<String> getCommentsID() {
        return commentsID;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (postID != null ? postID.hashCode() : 0);
        result = 31 * result + (userID != null ? userID.hashCode() : 0);
        result = 31 * result + (message != null ? message.hashCode() : 0);
        result = 31 * result + (commentsID != null ? commentsID.hashCode() : 0);
        result = 31 * result + (likedUsers != null ? likedUsers.hashCode() : 0);
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Post post = (Post) o;

        if (!postID.equals(post.postID)) return false;
        if (!userID.equals(post.userID)) return false;
        if (!message.equals(post.message)) return false;
        if (!Objects.equals(commentsID, post.commentsID)) return false;
        return Objects.equals(likedUsers, post.likedUsers);
    }

    @Override
    public String toString() {
        return postID;
    }

    @Override
    public String getUserID() {
        return userID;
    }

    public String getPostID() {
        return postID;
    }

    public String getMessage() {
        return message;
    }

    public String getPublishDate() {
        return publishDate;
    }

}
