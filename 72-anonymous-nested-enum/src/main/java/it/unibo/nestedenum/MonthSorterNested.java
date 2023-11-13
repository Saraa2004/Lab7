package it.unibo.nestedenum;

import java.util.Comparator;
import java.util.Objects;

/**
 * Implementation of {@link MonthSorter}.
 */
public final class MonthSorterNested implements MonthSorter {
    private static final Comparator<String> BY_DAYS = new SortByDays();
    private static final Comparator<String> BY_ORDER = new SortByMonthOrder();

    private enum Month {
        JANUARY(31), 
        FEBRUARY(28), 
        MARCH(31), 
        APRIL(30), 
        MAY(31), 
        JUNE(30), 
        JULY(31), 
        AUGUST(31), 
        SEPTEMBER(30), 
        OCTOBER(31), 
        NOVEMBER(30), 
        DECEMBER(31);

        private final int days;

        private Month(final int days) {
            this.days = days;
        }

        public static Month fromString(final String month) {
            Objects.requireNonNull(month);
            try {
                return valueOf(month);
            } catch(IllegalArgumentException e) {
                Month match = null;
                for(final Month m : values()) {
                    if(m.toString().toLowerCase().startsWith(month.toLowerCase())) {
                        if(match != null) {
                            throw new IllegalArgumentException(month + "is ambiguous: both " + m + "and " + match + "match this name");
                        }
                        match = m;
                    }
                }
                if(match == null) {
                    throw new IllegalArgumentException("No matches found for " + month);
                }
                return match;
            }
        }
    }

    public Comparator<String> sortByDays() {
        return BY_DAYS;
    }

    public Comparator<String> sortByOrder() {
        return BY_ORDER;
    }

    private static class SortByMonthOrder implements Comparator<String> {
        public int compare(final String m1, final String m2) {
            return Month.fromString(m1).compareTo(Month.fromString(m2));         
        }
    }

    private static class SortByDays implements Comparator<String> {
        public int compare(String m1, String m2) {
            return Integer.compare(Month.fromString(m1).days, Month.fromString(m2).days);           
        }
    }
}
