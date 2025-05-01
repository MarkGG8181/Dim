package dim.event.impl;

import dim.event.Event;
import net.minecraft.network.Packet;

public class ReceivePacketEvent extends Event {
    public final Packet packet;

    public ReceivePacketEvent(Packet packet) {
        this.packet = packet;
    }
}
