import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

import java.util.Date;

/**
 * Created by jelink on 18/11/15.
 */
@Entity("tweets")
public class Tweet {
    @Id
    private ObjectId tweetId;
    private String username;
    private String body;
    private Date time;

    public Tweet () {}

    public Tweet (String _username, String _body, Date _time) {
        username = _username;
        body = _body;
        time = _time;
    }

    public ObjectId getTweetId() {
        return tweetId;
    }

    public void setTweetId(ObjectId _tweetId) {
        tweetId = _tweetId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String _username) {
        username = _username;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String _body) {
        body = _body;
    }

    public Date getTime() {
        return time;
    }

    public void setBody(Date _time) {
        time = _time;
    }
}
