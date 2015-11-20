/**
 * Created by jelink on 18/11/15.
 */

import com.mongodb.DuplicateKeyException;
import com.mongodb.MongoClient;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;

import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class MainClass {
    private String keyspace = "lingga";
    private String server_ip = "167.205.35.19";
    private User user;
    private Datastore store;
    private MongoClient client;
    private Morphia morphia;

    public MainClass() {
        client = new MongoClient(server_ip);
        morphia = new Morphia();
        morphia.map(User.class);
        morphia.map(Follower.class);
        morphia.map(Tweet.class);
        morphia.map(UserLine.class);
        morphia.map(TimeLine.class);
        store = morphia.createDatastore(client, keyspace);
        store.ensureIndexes();
    }

    public static void main (String[] args) {
        MainClass client = new MainClass();
        String command = "";
        while (!command.equals("exit")) {
            client.showInstruction();
            if (client.user != null) {
                System.out.print(client.user.getUsername());
            }
            System.out.print(">> ");
            Scanner scn = new Scanner(System.in);

            command = scn.next();

            if (command.toLowerCase().startsWith("reg")) {
                String username = scn.next();
                String password = scn.next();

                int register_code = client.register(username, password);
                if (register_code == 0) {
                    System.out.println("Registrasi berhasil");
                } else {
                    System.out.println("Registrasi gagal");
                }
                scn.nextLine();
            } else if (command.toLowerCase().startsWith("login")) {
                String username = scn.next();
                String password = scn.next();

                client.user = client.login(username, password);
                if (client.user != null) {
                    System.out.println("Login berhasil");
                } else {
                    System.out.println("Login gagal");
                }
                scn.nextLine();
            }
              else if (command.toLowerCase().startsWith("fol")) {
                String username = scn.next();

                int follow_code = client.follow(username);
                if (follow_code == -1) {
                    System.out.println("follow gagal");
                } else {
                    System.out.println("follow berhasil");
                }
                scn.nextLine();
            }
            else if (command.toLowerCase().startsWith("tweet")) {
                String tweet = scn.nextLine();
                tweet = tweet.substring(1);

                int tweet_code = client.tweet(tweet);
                if (tweet_code == -1) {
                    System.out.println("tweet gagal");
                } else {
                    System.out.println("tweet berhasil");
                }
            } else if (command.toLowerCase().startsWith("userl")) {
                String username = scn.next();

                int code = client.userline(username);
                if (code == -1) {
                    System.out.println("error");
                }
                scn.nextLine();
            }
            else if (command.toLowerCase().startsWith("timel")) {
                String username = scn.next();

                int code = client.timeline(username);
                if (code == -1) {
                    System.out.println("error");
                }
                scn.nextLine();
            } else if (command.toLowerCase().startsWith("logout")) {
                client.logout();
                scn.nextLine();
            }
            System.out.println("(enter to continue)");
            scn.nextLine();
        }
        System.out.println("exiting program");
    }

    private void showInstruction() {
        System.out.println("=========================== SimpleTweet ===========================");
        System.out.println("Pilih perintah-perintah di bawah ini, ikuti formatnya dengan benar");
        if (user != null) {
            System.out.println("follow <username>");
            System.out.println("tweet <tweet_content>");
            System.out.println("userline <username>");
            System.out.println("timeline <username>");
            System.out.println("logout");
        } else {
            System.out.println("register <username> <password>");
            System.out.println("login <username> <password>");
        }
        System.out.println("exit");
    }

    private int register (String username, String password) {
        User reg_user = new User(username, password);
        try {
            store.save(reg_user);
        } catch (DuplicateKeyException e) {
            return -1;
        }
        return 0;
    }

    private User login (String username, String password) {
        if (user != null) {
            return null;
        }

        return store.createQuery(User.class)
                .field("username").equal(username)
                .field("password").equal(password)
                .get();
    }

    private int follow (String username) {
        // prekondisi
        if (user == null) return -1;
        // body
        Follower follower = new Follower(username, user.getUsername(), new Date());

        try {
            store.save(follower);
            return 0;
        } catch (DuplicateKeyException e) {
            return -1;
        }
    }

    private int tweet (String tweet_str) {
        // prekondisi
        if (user == null) return -1;

        // body
        Date tweet_date = new Date();

        // simpan tweet
        Tweet tweet = new Tweet(user.getUsername(), tweet_str, tweet_date);
        store.save(tweet);

        // simpan di userline sendiri
        UserLine line = new UserLine(user.getUsername(), tweet, tweet_date);
        store.save(line);

        // simpan di timeline seluruh follower
        List<Follower> followers = store.createQuery(Follower.class).field("username").equal(user.getUsername()).asList();
        for (Follower follower : followers) {
            TimeLine t_line = new TimeLine(follower.getFollower(), tweet, tweet_date);
            store.save(t_line);
        }

        try {
            return 0;
        } catch (DuplicateKeyException e) {
            return -1;
        }
    }

    private int userline (String username) {
        // prekondisi
        if (user == null) return -1;

        // body
        for (UserLine line : store.createQuery(UserLine.class)
                .field("username").equal(username).order("-time").asList()) {
            System.out.println(line.getTweet().getUsername() + " [" + line.getTweet().getTime() + "] : " + line.getTweet().getBody());
        }

        return 0;
    }

    private int timeline (String username) {
        // prekondisi
        if (user == null) return -1;

        // body
        for (TimeLine line : store.createQuery(TimeLine.class)
                .field("username").equal(username).order("-time").asList()) {
            System.out.println(line.getTweet().getUsername() + " [" + line.getTweet().getTime() + "] : " + line.getTweet().getBody());
        }

        return 0;
    }

    private void logout () {
        user = null;
    }
}

