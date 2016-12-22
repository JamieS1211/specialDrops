package com.github.jamies1211.specialdrops.Data.SpecialData;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.data.DataContainer;
import org.spongepowered.api.data.manipulator.immutable.common.AbstractImmutableData;
import org.spongepowered.api.data.value.immutable.ImmutableValue;

/**
 * Created by Jamie on 29/10/2016.
 */
public class ImmutableCustomData extends AbstractImmutableData<ImmutableCustomData, CustomData> {

	private String itemPermissionData;

	public ImmutableCustomData(String eventType) {
		this.itemPermissionData = eventType;
	}

	@Override
	protected void registerGetters() {
		// Getter and ValueGetter for ITEM_PERMISSION_DATA
		registerFieldGetter(CustomData.ITEM_PERMISSION_DATA, this::getItemPermissionData);
		registerKeyValue(CustomData.ITEM_PERMISSION_DATA, this::eventType);
	}

	// Create mutable version of this
	@Override
	public CustomData asMutable() {
		return new CustomData(this.itemPermissionData);
	}

	// Content Version (may be used for updating custom Data later)
	@Override
	public int getContentVersion() {
		return 1;
	}

	// !IMPORANT! Override toContainer and set your custom Data
	@Override
	public DataContainer toContainer() {
		return super.toContainer().set(CustomData.ITEM_PERMISSION_DATA, getItemPermissionData());
	}

	// Getters

	private String getItemPermissionData() {
		return this.itemPermissionData;
	}

	// Value Getters
	private ImmutableValue<String> eventType() {
		return Sponge.getRegistry().getValueFactory().createValue(CustomData.ITEM_PERMISSION_DATA, this.itemPermissionData).asImmutable();
	}
}