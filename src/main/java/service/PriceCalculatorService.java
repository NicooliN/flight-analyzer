package service;

import lombok.experimental.UtilityClass;
import java.util.List;
import java.util.Collections;

@UtilityClass
public class PriceCalculatorService {

    public double calculateAverage(List<Integer> prices) {
        return prices.stream()
                .mapToInt(Integer::intValue)
                .average()
                .orElse(0.0);
    }

    public double calculateMedian(List<Integer> prices) {
        Collections.sort(prices);
        int size = prices.size();

        if (size % 2 == 0) {
            return (prices.get(size / 2 - 1) + prices.get(size / 2)) / 2.0;
        } else {
            return prices.get(size / 2);
        }
    }

    public double calculatePriceDifference(List<Integer> prices) {
        double average = calculateAverage(prices);
        double median = calculateMedian(prices);
        return average - median;
    }
}
