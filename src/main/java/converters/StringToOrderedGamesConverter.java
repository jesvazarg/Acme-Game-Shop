
package converters;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import repositories.OrderedGamesRepository;
import domain.OrderedGames;

@Component
@Transactional
public class StringToOrderedGamesConverter implements Converter<String, OrderedGames> {

	@Autowired
	OrderedGamesRepository	orderedGamesRepository;


	@Override
	public OrderedGames convert(final String text) {
		OrderedGames result;
		int id;

		try {
			if (StringUtils.isEmpty(text))
				result = null;
			else {
				id = Integer.valueOf(text);
				result = this.orderedGamesRepository.findOne(id);
			}
		} catch (final Throwable oops) {
			throw new IllegalArgumentException(oops);
		}

		return result;
	}

}
