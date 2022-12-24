package socialnetwork;

import java.text.SimpleDateFormat;
import java.util.Date;

class Comment extends Post {

    // Comment properties
    final String commentID;
    final String userID;
    final String postID;
    final String message;
    final String publishDate;

    // Constructor with all data
    public Comment(int commentID, String userID, String postID, String message, Date publishDate) {
        this.commentID   = String.valueOf(commentID);
        this.userID      = userID;
        this.postID      = postID;
        this.message     = message;
        this.publishDate = new SimpleDateFormat("dd-M-yyyy hh:mm:ss").format(publishDate);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + commentID.hashCode();
        result = 31 * result + userID.hashCode();
        result = 31 * result + postID.hashCode();
        result = 31 * result + message.hashCode();
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Comment comment = (Comment) o;

        if (!commentID.equals(comment.commentID)) return false;
        if (!userID.equals(comment.userID)) return false;
        if (!postID.equals(comment.postID)) return false;
        return message.equals(comment.message);
    }

    @Override
    public String toString() {
        return commentID;
    }

    @Override
    public String getUserID() {
        return userID;
    }

    @Override
    public String getPostID() {
        return postID;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public String getPublishDate() {
        return publishDate;
    }

    public String getCommentID() {
        return commentID;
    }

}
