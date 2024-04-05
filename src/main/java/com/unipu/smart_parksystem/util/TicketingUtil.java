package com.unipu.smart_parksystem.util;

import com.unipu.smart_parksystem.constants.Constants;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

public class TicketingUtil
{
    public static long hoursToPay(Instant start, Instant end) {
        long minutesBetween = ChronoUnit.MINUTES.between(start, end);

        long hoursBetween = (minutesBetween / 60);
        if ((minutesBetween % 60) > 0) {
            return ++hoursBetween;
        }
        return hoursBetween;
    }


    public static BigDecimal getAmountToPay(long hoursToPay) {

        if (hoursToPay < 0) {
            throw new IllegalArgumentException("Can't pay before timeout time outs");
        }

        return BigDecimal.valueOf(Constants.PRICE_PER_HOUR * hoursToPay);
    }
}
