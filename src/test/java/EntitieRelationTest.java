import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Before;
import org.junit.Test;
import com.Perk;
import com.User;
import com.Subscription;
/**
 * Test the relationship between the User, Subscription and perk.
 *
 */

public class EntitieRelationTest {
    private User user;
    private Perk perk;
    private Subscription sub;

    private String username = "Test";
    private String pass = "pass";
    private String namePerk = "half off test";
    private String description = "This is a test";
    private String nameSub = "testVisa";
    private String fee = "once a month";

    @Before
    public void init()
    {
        user = new User(username,pass);
        perk = new Perk(namePerk, description);
        sub = new Subscription(nameSub,fee);
    }

    @Test
    public void testEntities()
    {
        sub.addPerk(perk);
        user.addSubscription(sub);

        assertThat(user.getSubscriptions().get(0).equals(sub));
        assertThat(user.getSubscriptions().get(0).getPerks().get(0).equals(perk));

        sub.removePerk(perk);
        user.removeSubscription(sub);

        assertThat(sub.getPerks().size() == 0);
        assertThat(user.getSubscriptions().size() == 0);
    }

}
