/*
* An individual perk held by a subscription, a ubscription can have many perks
*
* */

import java.util.Date;
import javax.persistence.*;

@Entity
public class Perk {
    /* Perk fields */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String name;
    private String Description;
    private java.util.Date expiryDate;

    /* Constructors */
    public Perk(String name, String Description, java.util.Date expiryDate)
    {
        this.name = name;
        this.Description = Description;
        this.expiryDate = expiryDate;
    }

    /* We need at least  a name and description to make a perk, default expiry is never. */
    public Perk(String name, String description)
    {
        this(name, description, null);
    }

    public Perk()
    {
    }

    /* Getters & Setters */

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public Date getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(Date expiryDate) {
        this.expiryDate = expiryDate;
    }
}
