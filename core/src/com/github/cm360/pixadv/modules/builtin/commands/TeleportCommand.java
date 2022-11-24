package com.github.cm360.pixadv.modules.builtin.commands;

import com.github.cm360.pixadv.ClientApplication;
import com.github.cm360.pixadv.commands.Command;
import com.github.cm360.pixadv.commands.Syntax;
import com.github.cm360.pixadv.environment.storage.Universe;
import com.github.cm360.pixadv.environment.storage.World;
import com.github.cm360.pixadv.environment.types.entities.Entity;
import com.github.cm360.pixadv.network.client.AbstractClient;

public class TeleportCommand extends Command {

	protected Universe universe;
	
	public TeleportCommand(Universe universe) {
		super();
		this.universe = universe;
		// Teleports self to position
		addSyntax(new Syntax(args -> {
			AbstractClient client = ClientApplication.getClient();
			if (client != null) {
				Entity player = client.getPlayer();
				if (player != null) {
					synchronized (player) {
						double x = ((Number) args[0]).doubleValue();
						double y = ((Number) args[1]).doubleValue();
						player.setX(x);
						player.setY(y);
						return "Teleported %s to %f %f".formatted(player, x, y);
					}
				} else {
					return "The player could not be found!";
				}	
			} else {
				return "This command is only available when executed by the client!";
			}
		}, Number.class, Number.class));
	}
	
	public boolean teleport(Entity entity, World fromWorld, World toWorld, double x, double y) {
		return true;
	}
	
	@Override
	public String getName() {
		return "tp";
	}

}
