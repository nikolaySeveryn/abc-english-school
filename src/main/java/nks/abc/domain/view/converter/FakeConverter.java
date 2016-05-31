package nks.abc.domain.view.converter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class FakeConverter extends Converter<Object,Object> {

	@Override
	public Object toView(Object bo) {
		return null;
	}

	@Override
	public Object toDomain(Object dto) {
		return null;
	}

	@Override
	public List<Object> toView(Collection<Object> bos) {
		return new ArrayList<Object>();
	}

	@Override
	public List<Object> toDomain(Collection<Object> dtos) {
		return new ArrayList<Object>();
	}

}
