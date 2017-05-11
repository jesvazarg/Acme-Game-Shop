
package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.Discount;

@Component
@Transactional
public class DiscountToStringConverter implements Converter<Discount, String> {

	@Override
	public String convert(final Discount discount) {
		String result;

		if (discount == null)
			result = null;
		else
			result = String.valueOf(discount.getId());
		return result;
	}

}
