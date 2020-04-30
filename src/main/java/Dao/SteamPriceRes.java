package Dao;

import lombok.*;

@Getter
@Setter
public class SteamPriceRes {
    private String status;
    private String price;
    private String count;
    private String averagePrice;
}
