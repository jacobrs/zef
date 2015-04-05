# zef
Gagnsta numba 1

#Setup
```
npm install
bower install
node server.js
```

## API Usage
| Request  | Path           |        Description        |        Data        | Result            |
| -------- |:--------------:|:-------------------------:|:------------------:|:-----------------:|
| POST     | /api/accounts/ | Login to account          | username, password | account/error obj |
| PUT      | /api/accounts/ | Create an account         | username, password | account/error obj |
| GET      | /api/pictures/ |List the account's pictures| token              | pictures/error obj|
| GET      | /api/pictures/:pic_id | Get specific picture| token, pic_id     | pictures/error obj|
| GET      | /api/pictures/public  |List shared pictures | token             | picture/error obj|
| GET      | /api/pictures/public/:pic_id | Get specific picture| token, pic_id     | picture/error obj|
| PUT      | /api/pictures/ |Add a picture under signed in account| token, picture    | picture/error obj|
| DELETE      | /api/pictures/:pic_id | Delete specific picture| token, pic_id     | success/error obj|
