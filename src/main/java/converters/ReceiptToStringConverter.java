
package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.Receipt;

@Component
@Transactional
public class ReceiptToStringConverter implements Converter<Receipt, String> {

	@Override
	public String convert(final Receipt receipt) {
		String result;

		if (receipt == null)
			result = null;
		else
			result = String.valueOf(receipt.getId());
		return result;
	}

}
