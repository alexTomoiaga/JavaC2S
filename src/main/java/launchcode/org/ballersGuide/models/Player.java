package launchcode.org.ballersGuide.models;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
public class Player {

    @Id
    @GeneratedValue
    private int id;

    @NotNull
    @Size(min=3, max=15)
    private String name;

    @NotNull
    private int ageInLeague;

    @NotNull
    @Size(min=1, max=15)
    private String size;

    @ManyToOne
    private Position position;

    @ManyToMany(mappedBy = "players")
    private List<Team> teams;

    @NotNull
    private int number;

    public Player(String name, int ageInLeague, String size, int number) {
        this.name = name;
        this.ageInLeague = ageInLeague;
        this.size = size;
        this.number = number;
    }

    public Player() { }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAgeInLeague() {
        return ageInLeague;
    }

    public void setAgeInLeague(int ageInLeague) {
        this.ageInLeague = ageInLeague;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }
}
