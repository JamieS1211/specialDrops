package com.github.jamies1211.specialdrops.Data.SpecialData;

import org.spongepowered.api.data.DataHolder;
import org.spongepowered.api.data.DataView;
import org.spongepowered.api.data.manipulator.DataManipulatorBuilder;
import org.spongepowered.api.data.persistence.AbstractDataBuilder;
import org.spongepowered.api.data.persistence.InvalidDataException;

import java.util.Optional;

/**
 * Created by Jamie on 29/10/2016.
 */
public class CustomDataBuilder extends AbstractDataBuilder<CustomData> implements DataManipulatorBuilder<CustomData, ImmutableCustomData> {

	public CustomDataBuilder() {
		super(CustomData.class, 1);
	}

	@Override
	public CustomData create() {
		return new CustomData();
	}

	@Override
	public Optional<CustomData> createFrom(DataHolder dataHolder) {
		return this.create().fill(dataHolder);
	}

	@Override
	protected Optional<CustomData> buildContent(DataView dataView) throws InvalidDataException {
		return this.create().from(dataView.copy());
	}
}
