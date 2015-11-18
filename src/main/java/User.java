import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.*;

/**
 * Created by jelink on 18/11/15.
 */
@Entity("users")
@Indexes(
        @Index(fields = {@Field("username")},options = @IndexOptions(unique=true))
)
public class User {
    @Id
    private ObjectId id;
    private String username;
    private String password;

    public User () {}

    public User (String _username, String _password) {
        username = _username;
        password = _password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String _username) {
        username = _username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String _password) {
        password = _password;
    }
}
