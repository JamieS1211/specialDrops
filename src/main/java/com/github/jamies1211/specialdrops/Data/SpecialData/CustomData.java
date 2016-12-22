package com.github.jamies1211.specialdrops.Data.SpecialData;

import com.google.common.reflect.TypeToken;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.data.DataContainer;
import org.spongepowered.api.data.DataHolder;
import org.spongepowered.api.data.key.Key;
import org.spongepowered.api.data.manipulator.mutable.common.AbstractData;
import org.spongepowered.api.data.merge.MergeFunction;
import org.spongepowered.api.data.value.mutable.Value;

import java.util.Optional;

import static org.spongepowered.api.data.DataQuery.of;
import static org.spongepowered.api.data.key.KeyFactory.makeSingleKey;

/**
 * Created by Jamie on 29/10/2016.
 */
public class CustomData extends AbstractData<CustomData, ImmutableCustomData> {

	// TypeTokens needed for creating Keys (can be created inline too)
	private static TypeToken<String> TT_String = new TypeToken<String>() {};
	private static TypeToken<Value<String>> TTV_String= new TypeToken<Value<String>>() {};

	// Keys for this custom Data
	public static Key<Value<String>> ITEM_PERMISSION_DATA = makeSingleKey(TT_String, TTV_String, of("String"), "specialdrops:itemPermissionData:string", "String");

	// Live Data in this instance
	private String itemPermissionData;

	// For DataBuilder and personal use
	public CustomData() {
	}

	public CustomData(String eventType) {
		this.itemPermissionData = eventType;
		registerGettersAndSetters();
	}

	@Override
	protected void registerGettersAndSetters() {
		// Getter, Setter and ValueGetter for ITEM_PERMISSION_DATA
		registerFieldGetter(ITEM_PERMISSION_DATA, CustomData.this::getItemPermissionData);
		registerFieldSetter(ITEM_PERMISSION_DATA, CustomData.this::setItemPermissionData);
		registerKeyValue(ITEM_PERMISSION_DATA, CustomData.this::eventType);
	}

	// Create immutable version of this
	@Override
	public ImmutableCustomData asImmutable() {
		return new ImmutableCustomData(this.itemPermissionData);
	}

	// Fill Data using DataHolder and MergeFunction
	@Override
	public Optional<CustomData> fill(DataHolder dataHolder, MergeFunction overlap) {
		Optional<String> eventType = dataHolder.get(ITEM_PERMISSION_DATA);
		// Only apply if the custom Data is present
		if (eventType.isPresent()) {
			CustomData data = this.copy();
			data.itemPermissionData = eventType.get();

			// merge Data
			data = overlap.merge(this, data);
			if (data != this) {
				this.itemPermissionData = data.itemPermissionData;
			}

			return Optional.of(this);
		}
		return Optional.empty();
	}

	// Fill Data using DataContainer
	@Override
	public Optional<CustomData> from(DataContainer container) {
		Optional<String> eventType = container.getString(ITEM_PERMISSION_DATA.getQuery());
		// Only apply if the custom Data is present
		if (eventType.isPresent()) {
			this.itemPermissionData = eventType.get();
			return Optional.of(this);
		}
		return Optional.empty();
	}

	// Create copy of this
	@Override
	public CustomData copy() {
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
		return super.toContainer().set(ITEM_PERMISSION_DATA, getItemPermissionData());
	}

	// Getters

	private String getItemPermissionData() {
		return this.itemPermissionData;
	}

	// Setters

	private void setItemPermissionData(String itemPermissionData) {
		this.itemPermissionData = itemPermissionData;
	}

	// ValueGetters

	private Value<String> eventType() {
		return Sponge.getRegistry().getValueFactory().createValue(ITEM_PERMISSION_DATA, getItemPermissionData());
	}
}
