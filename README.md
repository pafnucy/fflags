# Feature Flag Service

## Description
Provides access tokens to given functionalities to users, which can be used against other backends. 

Sets of permissions are managed by admin users.


## Approach

while feature flags are usually property-managed per service or in a static way for whole application, this per-user and per-feature approach made me think of some permission application. It is stateful (stored permissions), but allows for using permissions without calling Feature Flag Service every time a feature is used on different backend. 

Writing code took around 3.5 hours minutes (not counting breaks). Concessions toward managing time budget are mainly in form of
- limited defensive programming
- skipping on validations
- allowing oneself to use deprecated classes, not their newer replacements, also no Spring Boot 3
- symbolic approach to tokens, desired goal would be using JWT
- lack of API for managing users
- partial management of feature flags: can be added, not deleted or updated


## Developer mode

Running application `dev` profile uses different database name, clears all permission and user data, adds few records. 

