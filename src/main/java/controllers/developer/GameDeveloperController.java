
package controllers.developer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import services.ActorService;
import services.GameService;

@Controller
@RequestMapping("/game/developer")
public class GameDeveloperController {

	// Services ---------------------------------------------------------------

	@Autowired
	private GameService		gameService;

	@Autowired
	private ActorService	actorService;


	// Constructors -----------------------------------------------------------

	public GameDeveloperController() {
		super();
	}

}
