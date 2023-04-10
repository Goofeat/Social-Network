package socialnetwork;

import java.util.HashSet;
import java.util.Set;

class User {

    // User properties
    private String       userID;
    private String       password;
    private String       firstName;
    private String       lastName;
    private String       age;
    private String       gender;
    private String       email;
    private String       phoneNumber;
    // Sets with all users, where the element is the user's data
    // Set followsTo is where the current user's subscriptions are stored
    private Set<User>    followsTo;
    // Set subscribers is where the current user's subscribers are stored
    private Set<User>    subscribers;
    // Set blockedUsers is where the blocked users of the current user are located
    private Set<User>    blockedUsers;
    // Set blockedBy is where the users who have blocked the current user are located
    private Set<User>    blockedBy;
    // If isEmailHidden is true, then other users cannot see the current user's email address
    // true by default
    private boolean      isEmailHidden;
    // If isNumberHidden is true, then other users cannot see the current user's phone number
    // true by default
    private boolean      isNumberHidden;
    // Set with all posts, where the element is the post's data
    private Set<Post>    posts;
    // Set with all posts, where the element is the comment's data
    private Set<Comment> comments;

    // Empty constructor
    public User() {
    }

    // Constructor with all data except followsTo, subscribers, blockedUsers,
    //                                  blockedBy, posts and comments
    public User(String userID, String password, String firstName, String surname,
                String age, String gender, String email, String phoneNumber,
                boolean isEmailHidden, boolean isNumberHidden) {
        this.userID         = userID;
        this.password       = password;
        this.firstName      = firstName;
        this.lastName       = surname;
        this.age            = age;
        this.gender         = gender;
        this.email          = email;
        this.phoneNumber    = phoneNumber;
        followsTo           = new HashSet<>();
        subscribers         = new HashSet<>();
        blockedUsers        = new HashSet<>();
        blockedBy           = new HashSet<>();
        this.isEmailHidden  = isEmailHidden;
        this.isNumberHidden = isNumberHidden;
        posts               = new HashSet<>();
        comments            = new HashSet<>();
    }

    public Set<User> getFollowsTo() {
        return followsTo;
    }

    public Set<User> getSubscribers() {
        return subscribers;
    }

    public Set<User> getBlockedUsers() {
        return blockedUsers;
    }

    public Set<User> getBlockedBy() {
        return blockedBy;
    }

    public Set<Post> getPosts() {
        return posts;
    }

    public Set<Comment> getComments() {
        return comments;
    }

    @Override
    public int hashCode() {
        int result = userID != null ? userID.hashCode() : 0;
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (firstName != null ? firstName.hashCode() : 0);
        result = 31 * result + (lastName != null ? lastName.hashCode() : 0);
        result = 31 * result + (age != null ? age.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (gender != null ? gender.hashCode() : 0);
        result = 31 * result + (phoneNumber != null ? phoneNumber.hashCode() : 0);
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        return userID != null ? userID.equals(user.userID) : user.userID != null;
    }

    @Override
    public String toString() {
        return userID;
    }

    public String getUserID() {
        return userID;
    }

    public String getPassword() {
        return password;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getAge() {
        return age;
    }

    public String getEmail() {
        return email;
    }

    public String getGender() {
        return gender;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getFullName() {
        return firstName + " " + lastName;
    }

    public boolean isEmailHidden() {
        return isEmailHidden;
    }

    public void setEmailHidden(boolean emailHidden) {
        isEmailHidden = emailHidden;
    }

    public boolean isNumberHidden() {
        return isNumberHidden;
    }

    public void setNumberHidden(boolean numberHidden) {
        isNumberHidden = numberHidden;
    }

}
