import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.*;

import java.util.Date;

/**
 * Created by jelink on 18/11/15.
 */
@Entity("followers")
@Indexes(
        @Index(fields = {@Field("username"),@Field("follower")}, options = @IndexOptions(unique=true))
)
public class Follower {
    @Id
    private ObjectId id;
    private String username;
    private String follower;
    private Date since;

    public Follower() {}

    public Follower(String _username, String _follower, Date _date) {
        username = _username;
        follower = _follower;
        since = _date;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String _username) {
        username = _username;
    }

    public String getFollower() {
        return follower;
    }

    public void setFollower(String _follower) {
        follower = _follower;
    }

    public Date getDate() {
        return since;
    }

    public void setDate(Date _date) {
        since = _date;
    }
}