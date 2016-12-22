package com.github.jamies1211.specialdrops;

/**
 * Created by Jamie on 07-May-16.
 */

import com.github.jamies1211.specialdrops.Data.SpecialData.CustomData;
import com.github.jamies1211.specialdrops.Data.SpecialData.CustomDataBuilder;
import com.github.jamies1211.specialdrops.Data.SpecialData.ImmutableCustomData;
import com.google.inject.Inject;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.entity.EntitySnapshot;
import org.spongepowered.api.entity.Item;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.cause.entity.spawn.EntitySpawnCause;
import org.spongepowered.api.event.filter.cause.Root;
import org.spongepowered.api.event.game.state.GameInitializationEvent;
import org.spongepowered.api.event.item.inventory.ChangeInventoryEvent;
import org.spongepowered.api.event.item.inventory.DropItemEvent;
import org.spongepowered.api.plugin.Plugin;
import org.spongepowered.api.text.serializer.TextSerializers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.logging.Logger;

import static com.github.jamies1211.specialdrops.Data.Messages.startup;
import static com.github.jamies1211.specialdrops.Data.SpecialData.CustomData.ITEM_PERMISSION_DATA;

@Plugin(id = "specialdrops", name = "SpecialDrops", version = "1.0.0-1.10.2",
		description = "Restricts dropping and picking up rights",
		authors = {"JamieS1211"},
		url = "http://pixelmonweb.officialtjp.com")

public class SpecialDrops {

	@Inject
	private Logger logger;
	public static SpecialDrops plugin;

	public Logger getLogger() {
		return this.logger;
	}

	public static SpecialDrops getPlugin() {
		return plugin;
	}

	@Listener
	public void onServerStart (GameInitializationEvent event) {
		plugin = this;
		getLogger().info(startup);

		// Register your Data, ImmutableData and DataBuilder in GameInitializationEvent
		Sponge.getDataManager().register(CustomData.class, ImmutableCustomData.class, new CustomDataBuilder());
	}

	//TODO make function correctly
	@Listener
	public void onDropItem (DropItemEvent.Dispense event) {
		Object rootCause = event.getCause().root();

		if (rootCause instanceof EntitySpawnCause) {
			EntitySpawnCause entity = (EntitySpawnCause) rootCause;
			EntitySnapshot spawningEntity = entity.getEntity().createSnapshot();
			String spawningEntityID = spawningEntity.getType().getId();

			if (spawningEntityID.equalsIgnoreCase("minecraft:player")) {
				UUID spawnerUUID = spawningEntity.getUniqueId().get();
				Player player = Sponge.getServer().getPlayer(spawnerUUID).get();

				for (Entity droppedEntity : event.getEntities()) {
					if (droppedEntity instanceof Item) {
						Item item = (Item) droppedEntity;
						item.offer(new CustomData(player.getName()));
					}
				}
			}
		}
	}

	@Listener
	public void onItemPickup (ChangeInventoryEvent.Pickup event, @Root Player player) {
		Item item = event.getTargetEntity();

		if  (item.get(CustomData.class).isPresent()) {
			Optional<String> itemPermissionData = item.get(CustomData.class).get().toContainer().getString(ITEM_PERMISSION_DATA.getQuery());
			String permission = itemPermissionData.get();

			if (!player.hasPermission(permission)) {
				event.setCancelled(true);
				player.sendMessage(TextSerializers.FORMATTING_CODE.deserialize("You do not have permission to pick up this item."));
			}
		}
	}
}