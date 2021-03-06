package nks.abc.domain.dto.converter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public abstract class Converter<B,T> {

	private ConvertersHolder relativeСonvertersPattern = new ConvertersHolder();

	public abstract T toDTO(B bo);
	public abstract B toDomain(T dto);

	protected ConvertersHolder getRelativeConverters() {
		return relativeСonvertersPattern.clone();
	}

	public void setRelativeConvertersPattern(ConvertersHolder pattern) {
		this.relativeСonvertersPattern = pattern;
	}
	public void setRelativeConvertersPattern(Converter<?,?>... converters) {
		setRelativeConvertersPattern(new ConvertersHolder(converters));
	}
	public void setRelativeConvertersPattern(List<Converter<?,?>> converters) {
		setRelativeConvertersPattern(new ConvertersHolder(converters));
	}

	public List<T> toDTO(Collection<B> bos) {
		List<T> dtos = new ArrayList<T>();
		for (B bo : bos) {
			dtos.add(toDTO(bo));
		}
		return dtos;
	}
	public List<B> toDomain(Collection<T> dtos) {
		List<B> bos = new ArrayList<B>();
		for (T dto : dtos) {
			bos.add(toDomain(dto));
		}
		return bos;
	}
}
