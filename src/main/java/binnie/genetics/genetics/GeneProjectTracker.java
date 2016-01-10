package binnie.genetics.genetics;

import com.mojang.authlib.GameProfile;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraft.world.WorldSavedData;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class GeneProjectTracker extends WorldSavedData {
    Map projects = new HashMap();
    Map TeamInvites = new HashMap();

    public GeneProjectTracker(String s) {
        super(s);
    }

    public static GeneProjectTracker getTracker(World world) {
        String filename = "GeneProjectTracker.common";
        GeneProjectTracker tracker = (GeneProjectTracker) world.loadItemData(GeneProjectTracker.class, filename);
        if (tracker == null) {
            tracker = new GeneProjectTracker(filename);
            world.setItemData(filename, tracker);
        }

        return tracker;
    }

    public void readFromNBT(NBTTagCompound nbt) {
    }

    public void writeToNBT(NBTTagCompound nbt) {
    }

    public int createProject(String name, GameProfile leader) {
        int i;
        for (i = 1; this.projects.keySet().contains(Integer.valueOf(i)); ++i) {
            ;
        }

        GeneProject project = new GeneProject(i, name, leader);
        this.projects.put(Integer.valueOf(i), project);
        this.markDirty();
        return i;
    }

    public void removeProject(int id) {
        this.projects.remove(Integer.valueOf(id));

        for (Entry<GameProfile, Set<Integer>> entry : this.TeamInvites.entrySet()) {
            ((Set) entry.getValue()).remove(Integer.valueOf(id));
        }

        this.markDirty();
    }

    public void leaveProject(int id, GameProfile player) {
        GeneProject project = (GeneProject) this.projects.get(Integer.valueOf(id));
        if (project != null) {
            project.removePlayer(player);
            if (project.isEmpty()) {
                this.removeProject(id);
            }

            this.markDirty();
        }
    }

    public void joinProject(int id, GameProfile player) {
        GeneProject project = (GeneProject) this.projects.get(Integer.valueOf(id));
        if (project != null) {
            project.addPlayer(player);
            this.markDirty();
        }
    }

    public void reassignPlayer(int id, int id2, GameProfile player) {
        GeneProject project = (GeneProject) this.projects.get(Integer.valueOf(id));
        if (project != null) {
            GeneProject project2 = (GeneProject) this.projects.get(Integer.valueOf(id2));
            if (project2 != null) {
                this.leaveProject(id, player);
                this.joinProject(id2, player);
            }
        }
    }

    public void renameProject(int id, String newName) {
        GeneProject project = (GeneProject) this.projects.get(Integer.valueOf(id));
        if (project != null) {
            project.setName(newName);
        }

        this.markDirty();
    }

    public void invitePlayer(int id, GameProfile player) {
        if (!this.TeamInvites.containsKey(player)) {
            this.TeamInvites.put(player, new LinkedHashSet());
        }

        ((Set) this.TeamInvites.get(player)).add(Integer.valueOf(id));
        this.markDirty();
    }

    public void revokeInvite(int id, GameProfile player) {
        if (!this.TeamInvites.containsKey(player)) {
            this.TeamInvites.put(player, new LinkedHashSet());
        }

        ((Set) this.TeamInvites.get(player)).add(Integer.valueOf(id));
        this.markDirty();
    }
}
