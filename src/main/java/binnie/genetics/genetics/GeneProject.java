package binnie.genetics.genetics;

import com.mojang.authlib.GameProfile;

import java.util.ArrayList;
import java.util.List;

public class GeneProject {
    int id = 0;
    String name;
    GameProfile leader = null;
    List members = new ArrayList();

    public GeneProject(int id, String name, GameProfile leader) {
        super();
        this.setID(id);
        this.setName(name);
        this.setLeader(leader);
    }

    public int getID() {
        return this.id;
    }

    public void setID(int id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public GameProfile getLeader() {
        return this.leader;
    }

    public void setLeader(GameProfile leader) {
        this.leader = leader;
        this.addPlayer(leader);
    }

    public boolean isEmpty() {
        return this.members.isEmpty();
    }

    public void addPlayer(GameProfile player) {
        if (!this.members.contains(player)) {
            this.members.add(player);
        }

        if (this.leader == null) {
            this.leader = player;
        }

    }

    public void removePlayer(GameProfile player) {
        if (player == this.leader) {
            throw new RuntimeException("Can\'t remove leader of a Gene Project");
        } else {
            this.members.remove(player);
        }
    }
}
