
package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.OrderedGames;

@Component
@Transactional
public class OrderedGamesToStringConverter implements Converter<OrderedGames, String> {

	@Override
	public String convert(final OrderedGames orderedGames) {
		String result;

		if (orderedGames == null)
			result = null;
		else
			result = String.valueOf(orderedGames.getId());
		return result;
	}

}
