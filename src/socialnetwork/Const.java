package socialnetwork;

// Constants like name of tables (users, posts, comments), of columns, etc.
class Const {

    protected static final String USERS_TABLE            = "users";
    protected static final String USERS_USER_ID          = "user_id";
    protected static final String USERS_PASSWORD         = "password";
    protected static final String USERS_FIRSTNAME        = "first_name";
    protected static final String USERS_LASTNAME         = "last_name";
    protected static final String USERS_AGE              = "age";
    protected static final String USERS_GENDER           = "gender";
    protected static final String USERS_EMAIL            = "email";
    protected static final String USERS_PHONENUMBER      = "phone_number";
    protected static final String USERS_FOLLOWS_TO       = "follows_to";
    protected static final String USERS_SUBSCRIBERS      = "subscribers";
    protected static final String USERS_BLOCKED_USERS    = "blocked_users";
    protected static final String USERS_BLOCKED_BY       = "blocked_by";
    protected static final String USERS_IS_EMAIL_HIDDEN  = "is_email_hidden";
    protected static final String USERS_IS_NUMBER_HIDDEN = "is_number_hidden";
    protected static final String USER_PROPERTIES        =
                    USERS_USER_ID + "," +
                    USERS_PASSWORD + "," +
                    USERS_FIRSTNAME + "," +
                    USERS_LASTNAME + "," +
                    USERS_AGE + "," +
                    USERS_GENDER + "," +
                    USERS_EMAIL + "," +
                    USERS_PHONENUMBER + "," +
                    USERS_IS_EMAIL_HIDDEN + "," +
                    USERS_IS_NUMBER_HIDDEN;

    protected static final String POSTS_TABLE        = "posts";
    protected static final String POSTS_POST_ID      = "post_id";
    protected static final String POSTS_USER_ID      = "user_id";
    protected static final String POSTS_MESSAGE      = "message";
    protected static final String POSTS_PUBLISH_DATE = "publish_date";
    protected static final String POSTS_COMMENTS_ID  = "comments_id";
    protected static final String POSTS_LIKED_USERS  = "liked_users";
    protected static final String POST_PROPERTIES    =
                    POSTS_POST_ID + "," +
                    POSTS_USER_ID + "," +
                    POSTS_MESSAGE + "," +
                    POSTS_PUBLISH_DATE;

    protected static final String COMMENTS_TABLE        = "comments";
    protected static final String COMMENTS_COMMENT_ID   = "comment_id";
    protected static final String COMMENTS_USER_ID      = "user_id";
    protected static final String COMMENTS_POST_ID      = "post_id";
    protected static final String COMMENTS_MESSAGE      = "message";
    protected static final String COMMENTS_PUBLISH_DATE = "publish_date";
    protected static final String COMMENT_PROPERTIES    =
                    COMMENTS_COMMENT_ID + "," +
                    COMMENTS_USER_ID + "," +
                    COMMENTS_POST_ID + "," +
                    COMMENTS_MESSAGE + "," +
                    COMMENTS_PUBLISH_DATE;
}
