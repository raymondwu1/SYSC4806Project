import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * User class, Holds a collection of memberships.
 *
 */
@Entity
public class User {
    /* User fields */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String email;
    private String password;
    @OneToMany
    private List<Subscription> subscriptions;

    /* Constructors */
    public User(String email, String password)
    {
        this.email = email;
        this.password = password;
        this.subscriptions = new ArrayList<Subscription>();
    }

    public User()
    {

    }

    /* Add/Remove Subscriptions */
    public void addSubscription(Subscription s)
    {
        this.subscriptions.add(s);
    }


    public void removeSubscription(Subscription s)
    {
        this.subscriptions.remove(s);
    }

    /* Getters & Setters */
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Subscription> getSubscriptions() {
        return subscriptions;
    }

    public void setSubscriptions(ArrayList<Subscription> subscriptions) {
        this.subscriptions = subscriptions;
    }
}
