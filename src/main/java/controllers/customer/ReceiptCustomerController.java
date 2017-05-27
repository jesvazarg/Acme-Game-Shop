
package controllers.customer;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.CustomerService;
import services.ReceiptService;
import controllers.AbstractController;
import domain.OrderedGames;
import domain.Receipt;

@Controller
@RequestMapping("/receipt/customer")
public class ReceiptCustomerController extends AbstractController {

	// Service ---------------------------------------------------------------
	@Autowired
	private CustomerService	customerService;

	@Autowired
	private ReceiptService	receiptService;


	// Constructors -----------------------------------------------------------
	public ReceiptCustomerController() {
		super();
	}

	// List -------------------------------------------------------------------
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		Collection<Receipt> receipts;

		receipts = this.customerService.findByPrincipal().getReceipts();

		result = new ModelAndView("receipt/list");
		result.addObject("receipts", receipts);
		result.addObject("requestURI", "receipt/customer/list.do");

		return result;
	}

	// Display -------------------------------------------------------------------
	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(final int receiptId) {
		ModelAndView result;
		Receipt receipt;
		Collection<OrderedGames> orderedGames;
		Double[] desglose;

		receipt = this.receiptService.findOne(receiptId);
		orderedGames = receipt.getOrderedGames();

		desglose = this.receiptService.desglosePrecios(orderedGames);

		result = new ModelAndView("receipt/display");
		result.addObject("receipt", receipt);
		result.addObject("orderedGames", orderedGames);
		result.addObject("desglose", desglose);
		result.addObject("requestURI", "receipt/customer/display.do");

		return result;
	}

}
