# Feature Flag Service

Provides access tokens to given functionalities to users, which can be used against other backends. 

Sets of permissions are managed here by admin users.

Motivation: while feature flags are usually property-managed per service or in a static way for whole application, this per-user and per-feature approach made me think of some permission application. It is stateful (stored permissions), but allows for using permissions without calling Feature Flag Service every time a feature is used on different backend. 
