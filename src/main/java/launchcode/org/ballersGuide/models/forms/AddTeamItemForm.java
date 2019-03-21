package launchcode.org.ballersGuide.models.forms;

import launchcode.org.ballersGuide.models.Player;
import launchcode.org.ballersGuide.models.Team;

public class AddTeamItemForm {

    private Team team;
    private Iterable<Player> players;
    private int teamId;
    private int playerId;

    public AddTeamItemForm(){
    }

    public AddTeamItemForm(Team team, Iterable<Player> players) {
        this.team = team;
        this.players = players;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public Iterable<Player> getPlayers() {
        return players;
    }

    public void setPlayers(Iterable<Player> players) {
        this.players = players;
    }

    public int getTeamId() {
        return teamId;
    }

    public void setTeamId(int teamId) {
        this.teamId = teamId;
    }

    public int getPlayerId() {
        return playerId;
    }

    public void setPlayerId(int playerId) {
        this.playerId = playerId;
    }
}
