import Dao.SteamPriceRes;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.apache.log4j.Logger;
import org.springframework.web.util.UriTemplate;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.Map;

public class SteamMarketAPI {

    private static Logger logger = Logger.getLogger(SteamMarketAPI.class);

    public SteamPriceRes fetchPrice(String itemName) {
        SteamPriceRes steamPriceRes = new SteamPriceRes();
        String response;
        JsonObject responseMap = new JsonObject();

        try {
            response = HttpConnection.makeConnection(getUrl(itemName));
            responseMap = getMap(response);
            steamPriceRes.setStatus(responseMap.get(SteamConstants.RESPONE_PARAMETER_SUCCESS).getAsString());
            steamPriceRes.setPrice(responseMap.get(SteamConstants.RESPONSE_PARAMETER_LOWEST_PRICE).getAsString());
            steamPriceRes.setCount(responseMap.get(SteamConstants.RESPONSE_PARAMETER_VOLUME).getAsString());
            steamPriceRes.setAveragePrice(responseMap.get(SteamConstants.RESPONSE_PARAMETER_AVERAGE_PRICE).getAsString());
        } catch (IOException e) {
            logger.error("Unable to prepare the requested URL exception: " + e);
        }
        return steamPriceRes;
    }

    private static JsonObject getMap(String response) {
        Gson responseGson = new Gson();
         return responseGson.fromJson(response, JsonObject.class);
    }

    private String getUrl(String itemName) throws UnsupportedEncodingException, MalformedURLException {
        StringBuilder url =new StringBuilder();
        Map<String, String> vars = new HashMap<String, String>();
        vars.put(SteamConstants.Currency_Key, SteamConstants.CURRENCY_VALUE);
        vars.put(SteamConstants.AppId_Key, SteamConstants.APPID_VALUE);
        vars.put("itemName", itemName);

        url.append(SteamConstants.STEAM_MARKET_URL);
        url.append(SteamConstants.Currency_Key);
        url.append(SteamConstants.EQUAL);
        url.append("{" + SteamConstants.Currency_Key + "}");
        url.append(SteamConstants.Amersand);
        url.append(SteamConstants.AppId_Key);
        url.append(SteamConstants.EQUAL);
        url.append("{" + SteamConstants.AppId_Key + "}");
        url.append(SteamConstants.Amersand);
        url.append(SteamConstants.MarketHashName);
        url.append(SteamConstants.EQUAL);
        url.append("{itemName}");
        UriTemplate template = new UriTemplate(url.toString());

        return template.expand(vars).toURL().toString();
    }

    public static void main(String args[]) throws UnsupportedEncodingException, MalformedURLException {
        SteamMarketAPI steamMarketAPI = new SteamMarketAPI();
        steamMarketAPI.fetchPrice("Dragonclaw Hook");
    }
}
