package pk.edu.budget_app.util;

import java.time.LocalDateTime;
import java.time.YearMonth;

public class DateUtils {

    public static LocalDateTime now(){
        return LocalDateTime.now();
    }

    public static LocalDateTime beginningOfMonth(YearMonth yearMonth){
        return yearMonth.atDay(1).atStartOfDay();
    }

    public static LocalDateTime endOfMonth(YearMonth yearMonth){
        return yearMonth.atEndOfMonth().atTime(23, 59, 59, 999_999_999);
    }
}
