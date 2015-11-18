import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.*;

import java.util.Date;

/**
 * Created by jelink on 18/11/15.
 */
@Entity("userlines")
@Indexes(
        @Index(fields = {@Field("tweet.time")})
)
public class UserLine {
    @Id
    private ObjectId id;
    private String username;

    @Reference
    private Tweet tweet;
    private Date time;

    public UserLine() {}

    public UserLine(String _username, Tweet _tweet, Date _time) {
        username = _username;
        tweet = _tweet;
        time = _time;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String _username) {
        username = _username;
    }

    public Tweet getTweet() {
        return tweet;
    }

    public void setTweet(Tweet _tweet) {
        tweet = _tweet;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date _time) {
        time = _time;
    }
}
