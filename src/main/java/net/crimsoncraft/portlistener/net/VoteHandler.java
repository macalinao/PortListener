/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.crimsoncraft.portlistener.net;

import com.crimsonrpg.pvpranker.CrimsonPvP;
import java.util.HashMap;
import java.util.Map;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

/**
 *
 * @author simplyianm
 */
public class VoteHandler {
    private CrimsonPvP core;

    private Map<String, Long> voters;

    private VoteServer server;

    public VoteHandler(CrimsonPvP core) {
        this.core = core;
        server = new VoteServer("0.0.0.0", 10099);
        server.startListener();
        server.start();

        calcVoters();
    }

    public void terminate() {
        server.close();
    }

    public void calcVoters() {
        ConfigurationSection vcs = core.getConfig().getConfigurationSection("voters");
        if (vcs == null) {
            vcs = core.getConfig().createSection("voters");
        }
        voters = new HashMap<String, Long>();
        for (String voter : vcs.getKeys(false)) {
            long since = vcs.getLong(voter, 0L);
            if (since > 0) {
                voters.put(voter, since);
            }
        }
    }

    public void addVoter(String voter) {
        voter = voter.toLowerCase();
        long time = System.currentTimeMillis();
        core.getConfig().set("voters." + voter, time);
        voters.put(voter, System.currentTimeMillis());
        CrimsonPvP.log("Vote received from " + voter + " at " + time + ".");
        core.saveConfig();
    }

    public boolean hasVoted(Player player) {
        if (!CrimsonPvP.VOTE) {
            return true;
        }
        String pn = player.getName().toLowerCase();
        Long get = core.getConfig().getLong("voters." + pn, 0L);
        return !(get == null || get <= 0 || get + 86400000 * 3 //Now 3 days
                < System.currentTimeMillis());
    }

    public void everyoneVoted() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            addVoter(player.getName());
        }
    }

}
