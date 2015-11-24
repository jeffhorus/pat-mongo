# pat-mongo
Simple Twitter-Like Terminal Application using Java and MongoDB.

## Created By:
- Christ Angga Saputra - 13512019
- Jeffrey Lingga Binangkit - 13512059

## Petunjuk instalasi/building
1. Ada 2 cara untuk instalasi program ini, yaitu:
  - Clone dari `https://github.com/jeffhorus/pat-mongo`
  - Ekstrak source code zip

2. Selanjutnya `Import Project` dengan menggunakan IntelliJ IDEA

## Cara menjalankan program
1. Buka folder `bin/`
2. Jalankan `pat-mongo.jar` (melalui terminal : `java -jar pat-mongo.jar`, atau double click langsung pada `pat-mongo.jar` pada explorer)

## Perintah/query database untuk mengimplementasikan fungsionalitas

### Mendaftar user baru
1. Daftarkan username dan password ke dalam database dengan perintah `dataStore.save(new User(<username>, <password>))`.
2. Apabila telah ada username dengan nama tersebut, maka akan terjadi `DuplicateKeyException`.

## Login
1. Cek apakah username dan password sesuai dengan database dengan perintah `dataStore.createQuery(User.class).field("username").equal(<username>).field("password").equal(<password>).get()`
2. Jika ada, maka user berhasil login

### Follow a friend
1. Cek apakah username teman sama dengan username sendiri
2. Jika berbeda, cek apakah terdapat username teman tersebut dengan perintah `dataStore.createQuery(User.class).field("username").equal(<username>).get()`
3. Jika terdapat teman dengan username tersebut, maka masukkan username teman ke dalam database dengan perintah `dataStore.save(new Follower(<username_friend>, <username_self>, <tweet_time>));`

### Tweet
1. Masukkan tweet ke dalam tweets dengan perintah `dataStore.save(new Tweet(<username>, <tweet_body>, <tweet_time>))`
2. Masukkan tweet ke dalam userline agar mudah dalam menampilkan tweet per user dengan perintah `dataStore.save(new UserLine(<username>, <tweet_body>, <tweet_time>))`
3. Masukkan tweet ke dalam timeline seluruh followers agar mudah dalam menampilkan timeline per user dengan perintah `dataStore.save(new TimeLine(<username_follower>, <tweet_body>, <tweet_time>))`

### Menampilkan tweet per user
1. Ambil semua userline dengan perintah `dataStore.createQuery(UserLine.class).field("username").equal(<username>).order("-time").asList()`
2. Kemudian untuk setiap userline, ambil tweet tersebut dengan perintah `userline.getUsername()`, `userline.getTime()`, dan `userline.getBody()`

### Menampilkan timeline per user
1. Ambil semua timeline dengan perintah `dataStore.createQuery(TimeLine.class).field("username").equal(<username>).order("-time").asList()`
2. Kemudian untuk setiap timeline, ambil tweet tersebut dengan perintah `timeline.getUsername()`, `timeline.getTime()`, dan `timeline.getBody()`
