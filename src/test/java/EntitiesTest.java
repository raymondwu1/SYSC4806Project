import static org.assertj.core.api.Assertions.assertThat;
import org.junit.Test;
import com.Perk;
import com.User;
import com.Subscription;
/**
 * Test the User, Subscription and perk classes.
 *
 */

public class EntitiesTest {
    private User user;
    private Perk perk;
    private Subscription sub;

    private String email = "Test@Test.com";
    private String pass = "pass";
    private String namePerk = "half off test";
    private String description = "This is a test";
    private String nameSub = "testVisa";
    private String fee = "once a month";

    public void init()
    {
        user = new User(email,pass);
        perk = new Perk(namePerk, description);
        sub = new Subscription(nameSub,fee);
    }

    @Test
    public void testEntities()
    {
        init();

        sub.addPerk(perk);
        user.addSubscription(sub);

        assertThat(user.getSubscriptions().get(0).equals(sub));
        assertThat(user.getSubscriptions().get(0).getPerks().get(0).equals(perk));

        sub.removePerk(perk);
        user.removeSubscription(sub);

        assertThat(user.getSubscriptions().size() == 0);
        assertThat(user.getSubscriptions().size() == 0);
    }

    @Test
    public void testUser()
    {
        init();

        assertThat(user.getEmail().equals(email));
        assertThat(user.getPassword().equals(pass));
    }

    @Test
    public void testPerk()
    {
        init();

        assertThat(perk.getName().equals(namePerk));
        assertThat(perk.getDescription().equals(description));
        assertThat(perk.getExpiryDate() == null);
    }

    @Test
    public void testSubscription()
    {
        init();

        assertThat(sub.getName().equals(nameSub));
        assertThat(sub.getFee().equals(fee));

        assertThat(new Subscription(nameSub).getFee().equals("FREE"));
    }
}
