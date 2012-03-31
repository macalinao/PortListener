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
package net.crimsoncraft.portlistener.api;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 * Called when the port receives something.
 */
public class PortReceiveEvent extends Event {
    private static HandlerList _handlers = new HandlerList();

    private final String referer;

    private final String key;

    private final String content;

    public PortReceiveEvent(String referer, String key, String content) {
        this.referer = referer;
        this.key = key;
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public String getKey() {
        return key;
    }

    public String getReferer() {
        return referer;
    }

    @Override
    public HandlerList getHandlers() {
        return _handlers;
    }

    public static HandlerList getHandlerList() {
        return _handlers;
    }

}
