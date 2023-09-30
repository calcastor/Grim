package ac.grim.grimac.checks.impl.multiactions;

import ac.grim.grimac.checks.Check;
import ac.grim.grimac.checks.CheckData;
import ac.grim.grimac.checks.type.PacketCheck;
import ac.grim.grimac.player.GrimPlayer;
import com.github.retrooper.packetevents.event.PacketReceiveEvent;
import com.github.retrooper.packetevents.protocol.packettype.PacketType;

import java.util.Collections;
import java.util.Set;

@CheckData(name = "MultiActionsC", description = "Clicked in inventory while sprinting", experimental = true)
public class MultiActionsC extends Check implements PacketCheck {
    public MultiActionsC(GrimPlayer player) {
        super(player);
    }

    @Override
    public void onPacketReceive(PacketReceiveEvent event) {
        if (event.getPacketType() == PacketType.Play.Client.CLICK_WINDOW) {
            if (player.isSprinting && !player.isSwimming && !player.serverOpenedInventoryThisTick && flagAndAlert() && shouldModifyPackets()) {
                event.setCancelled(true);
                player.onPacketCancel();
            }
        }
    }

    @Override
    public Set<PacketType.Play.Server> typesCheckedOnSend() {
        return Collections.singleton(PacketType.Play.Server.OPEN_WINDOW);
    }
}
