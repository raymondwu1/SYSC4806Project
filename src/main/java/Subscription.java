/*
* This class holds the actual subscription of users
* that holds available perks
*
* */

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Subscription {
    /* Subscription fields */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @OneToMany(fetch = FetchType.EAGER)
    private List<Perk> perks;
    private String name;
    private String fee;
    private static final String FREE = "FREE";

    /* Constructors */
    public Subscription(String name, String fee)
    {
        this.name = name;
        this.fee = FREE;
        this.perks = new ArrayList<Perk>();
    }

    /* We need atleast  name to make a membership, default membership fee is free. */
    public Subscription(String name)
    {
        this(name, FREE);
    }

    public Subscription()
    {

    }

    /* Add/Remove Perk */
    public void addPerk(Perk p)
    {
        this.perks.add(p);
    }


    public void removePerk(Perk p)
    {
        this.perks.remove(p);
    }

    /* Getters & Setters */
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Perk> getPerks() {
        return perks;
    }

    public void setPerks(ArrayList<Perk> perks) {
        this.perks = perks;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFee() {
        return fee;
    }

    public void setFee(String fee) {
        this.fee = fee;
    }


}
