package nks.abc.domain.dto.converter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import nks.abc.domain.exception.ConversionException;

public class ConvertersHolder implements Cloneable{

	private List<Converter<?,?>> converters;

	public ConvertersHolder() {
		this(new ArrayList<Converter<?,?>>());
	}

	public ConvertersHolder(List<Converter<?,?>> converters) {
		super();
		this.converters = converters;
	}

	public ConvertersHolder(Converter<?,?>... converters) {
		this(new ArrayList<Converter<?,?>>(Arrays.asList(converters)));
	}

	public Converter findConverter(Class<? extends Converter<?,?>> clazz) {
		for (Converter converter : converters) {
			if (converter.getClass().equals(clazz)) {
				return converter;
			}
		}
		return null;

	}

	public Converter pullConverter(Class<? extends Converter<?,?>> clazz) {
		Converter converter = findConverter(clazz);
		if (converter != null) {
			converters.remove(converter);
			return converter;
		}

		return new FakeConverter();
	}

	public boolean shouldBeConverted(Class<? extends Converter<?,?>> clazz) {
		for (Converter<?,?> converter : converters) {
			if (converter.getClass().equals(clazz)) {
				return true;
			}
		}
		return false;
	}
	@Override
	public ConvertersHolder clone(){
		ConvertersHolder clone = null;
		try {
			clone = (ConvertersHolder) super.clone();
		}
		catch (CloneNotSupportedException e) {
			throw new ConversionException("Converters holder can't be cloned!", e);
		}
		clone.converters = new ArrayList<Converter<?,?>>(this.converters);
		return clone;
	}

}
