package com.github.cm360.pixadv.modules.builtin.commands;

import java.util.UUID;

import com.github.cm360.pixadv.ClientApplication;
import com.github.cm360.pixadv.commands.Command;
import com.github.cm360.pixadv.commands.Syntax;
import com.github.cm360.pixadv.environment.storage.Universe;
import com.github.cm360.pixadv.environment.storage.World;
import com.github.cm360.pixadv.environment.types.entity.Entity;
import com.github.cm360.pixadv.network.client.AbstractClient;

public class NoClipCommand extends Command {

	protected Universe universe;
	
	public NoClipCommand(Universe universe) {
		super();
		this.universe = universe;
		// Toggles noclip
		addSyntax(new Syntax(args -> {
			AbstractClient client = ClientApplication.getClient();
			World world = universe.getWorld(client.getCurrentWorldName());
			UUID playerId = client.getPlayerId();
			if (playerId != null) {
				Entity player = world.getEntity(playerId);
				if (player != null) {
					synchronized (player) {
						boolean noclip = !player.canNoClip();
						player.setNoClip(noclip);
						return "Toggled noclip %s.".formatted(noclip);
					}
				} else {
					return "The player could not be found!";
				}	
			} else {
				return "This command is only available when executed by the client!";
			}
		}));
		// Sets noclip to a certain value
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "noclip";
	}

}
