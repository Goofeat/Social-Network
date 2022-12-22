package socialnetwork;

import java.util.HashSet;
import java.util.Set;

class User {

    private String       userID;
    private String       password;
    private String       firstName;
    private String       lastName;
    private String       age;
    private String       gender;
    private String       email;
    private String       phoneNumber;
    private Set<User>    followsTo;
    private Set<User>    subscribers;
    private Set<User>    blockedUsers;
    private Set<User>    blockedBy;
    private boolean      isEmailHidden;
    private boolean      isNumberHidden;
    private Set<Post>    posts;
    private Set<Comment> comments;

    public User() {
    }

    public User(String userID, String password, String firstName, String surname,
                String age, String gender, String email, String phoneNumber) {
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
        this.isEmailHidden  = true;
        this.isNumberHidden = true;
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
