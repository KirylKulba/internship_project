package pl.internship.antologic.common.utils;

import pl.internship.antologic.record.entity.Record;

import java.math.BigDecimal;

public class RecordUtils {

    public static void evaluateWorkCost(final Record record) {
        final double hours = DateUtils.getDiff(record.getBeginTimestamp(), record.getEndTimestamp());
        final BigDecimal userWorkCost = record.getUser().getCost().multiply(BigDecimal.valueOf(hours));
        record.setWorkCost(userWorkCost);
    }

}
