
# Social Network

This project was made for the final session of the sophomore course "Introduction to Algorithms" at Suleyman Demirel University.


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
- `/forgot` — recover account password.
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

### Profile

`> /my_profile`

You will get this:
```
——————————————————————————————————————————————————
Your profile:
| Your username: @goofeat
| Your full name: Raiymbek Azharbayev (18 years old)
| Your gender: Male
| Your email: azharbayev@gmail.com (others cannot see)
| Your phone number: +7 (777) 751 21 21 (others cannot see)
——————————————————————————————————————————————————
| Posts: 2
| Following: 0
| Followers: 0
——————————————————————————————————————————————————
```

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