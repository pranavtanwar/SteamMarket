import Dao.SteamPriceRes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class Controller {

    @Autowired SteamMarketAPI steamMarketAPI;

    @RequestMapping(value = "/fetcPrice/{itemName}", method = RequestMethod.GET)
    public SteamPriceRes fetchPrice(@PathVariable("itemName") String itemName){
        return steamMarketAPI.fetchPrice(itemName);
    }

}
