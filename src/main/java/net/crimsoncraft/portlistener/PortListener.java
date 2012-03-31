/*
 * This file is part of PortListener.
 *
 * PortListener is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * PortListener is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with PortListener.  If not, see <http://www.gnu.org/licenses/>.
 */
package net.crimsoncraft.portlistener;

import java.util.logging.Level;
import net.crimsoncraft.portlistener.net.PortServer;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * PortListener main class.
 */
public class PortListener extends JavaPlugin {
    private PortServer server;

    @Override
    public void onDisable() {
        getLogger().log(Level.INFO, "Plugin disabled.");
    }

    @Override
    public void onEnable() {
        int port = getConfig().getInt("server.port", 10099);
        
        server = new PortServer(this, "0.0.0.0", port);
        server.start();
        getLogger().log(Level.INFO, "Plugin enabled.");
    }

    /**
     * Gets the Request Key of a referer.
     *
     * @param referer The referer.
     * @return The request key of the referer.
     */
    public String getRequestKey(String referer) {
        return getConfig().getString("referers." + referer, "");
    }

}
