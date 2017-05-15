
package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.Developer;

@Component
@Transactional
public class DeveloperToStringConverter implements Converter<Developer, String> {

	@Override
	public String convert(final Developer developer) {
		String result;

		if (developer == null)
			result = null;
		else
			result = String.valueOf(developer.getId());
		return result;
	}

}
