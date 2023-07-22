
# Social Network

This project was made for the final session of the sophomore course "Introduction to Algorithms" at Suleyman Demirel University.


## Acknowledgements

- [Features](https://github.com/Goofeat/Social-Network#features)
- [Commands](https://github.com/Goofeat/Social-Network#commands)
    - [My Profile](https://github.com/Goofeat/Social-Network#my-profile)
    - [User's Profile](https://github.com/Goofeat/Social-Network#users-profile)


## Features

- Registering a profile with properties such as name, surname, age, email, gender, phone number
- Profile login
- Follow / unfollow another user
- Blocking / Unblocking another user
- Making posts on your profile
- Liking posts
- Commenting posts of another user

## Commands

- `/help` or `/h` — view available commands.
- `/authorize` or `/auth` or `/login` — log in to your account.
- `/register` or `/reg` — create a new account.
- `/my_profile` or `/my` or `/me` — view your profile.
- `/profile <username>` – view the profile of another user.
- `/wall` or `/w` — view your wall.
- `/my_posts` — view your posts.
- `/posts <username>` — view the posts of another user.
- `/comments <postID>` or `/coms` — view all comments on a specific post.
- `/new_post` or `/new` — publish a new post.
- `/like <postID>` — like a specific post.
- `/dislike <postID>` or `/dis` — dislike a specific post.
- `/comment <postID>` or `/com` — comment on a specific post.
- `/follow <username>` or `/subscribe` or `/sub` — subscribe to another user.
- `/unfollow <username>` or `/unsubscribe` or `/unsub` — unsubscribe another user.
- `/block <username>` — block another user.
- `/unblock <username>` — unblock another user.
- `/delete <type>` or `/del` — delete a specific type (subscriber, post, comment).
- `/change` — change your current password.
- `/settings` — privacy settings.
- `/logout` — log out of your account.
- `/quit` or `/exit` — exit the program.

### View available commands and their explanations

`> /help`

Helps users understand the available functionalities in the application.

### Log in to your account

`> /authorize` or `> /auth` or `> /login`

Allows users to log in with their credentials to access their personalized content and perform actions on the social network.

```
——————————————————————————————————————————————————
Tip: If you forgot your username or password, type /forgot or /register
Tip: You can return by typing /back or /cancel
——————————————————————————————————————————————————
Your username: goofeat
Your password: qwerty
Incorrect password!
Your password: asdfghjk
——————————————————————————————————————————————————
You have successfully authorized!
——————————————————————————————————————————————————
```

### Create a new account

`> /register` or `> /reg`

Enables new users to register and create a profile on the social network.

```
——————————————————————————————————————————————————
Tip: You can return by typing /back or /cancel
——————————————————————————————————————————————————
Your username: ex
Username may have any word characters and digits. 
Length of username from 4 to 30
Your username: example
Your first name: J
The minimum length is 2, the maximum is 25.
Your first name: John
Your last name: Doe
Your age: 10
Incorrect age! (From 15 to 65)
Your age: 15
Your gender (M or F): A
Incorrect gender!
Your gender (M or F): M
Your email: qwerty
Incorrect email address!
Your email: qwerty@gmail.com
Your phone number (+77xxxxxxxxx or 87xxxxxxxxx): 87771112233
Your password: qwerty
——————————————————————————————————————————————————
You have successfully registered!
——————————————————————————————————————————————————
```

_But if you try to register after you have logged into your account, the program will not allow you to do so._

```
> /reg
You have already authorized
```

### Reset account password

Provides a way for users who have forgotten their passwords to reset them.

### View your profile

`> /my_profile`

You will get this:
```
——————————————————————————————————————————————————
Your profile:
| Your username: @goofeat
| Your full name: Raiymbek Azharbayev (19 years old)
| Your gender: Male
| Your email: azharbayev@gmail.com (others cannot see)
| Your phone number: +7 (777) 751 21 21 (others cannot see)
——————————————————————————————————————————————————
| Posts: 2
| Following: 0
| Followers: 0
——————————————————————————————————————————————————
```

### User's Profile

`> /profile test`

You will get this:
```
——————————————————————————————————————————————————
@test's profile:
| Full name: Ivan Ivanov (24 years old)
| Gender: Male
| Email: [hidden]
| Phone number: [hidden]
——————————————————————————————————————————————————
| Posts: 3
| Following: 0
| Followers: 0
——————————————————————————————————————————————————
```
