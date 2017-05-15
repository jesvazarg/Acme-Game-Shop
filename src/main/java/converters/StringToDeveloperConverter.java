
package converters;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import repositories.DeveloperRepository;
import domain.Developer;

@Component
@Transactional
public class StringToDeveloperConverter implements Converter<String, Developer> {

	@Autowired
	DeveloperRepository	developerRepository;


	@Override
	public Developer convert(final String text) {
		Developer result;
		int id;

		try {
			if (StringUtils.isEmpty(text))
				result = null;
			else {
				id = Integer.valueOf(text);
				result = this.developerRepository.findOne(id);
			}
		} catch (final Throwable oops) {
			throw new IllegalArgumentException(oops);
		}

		return result;
	}

}
