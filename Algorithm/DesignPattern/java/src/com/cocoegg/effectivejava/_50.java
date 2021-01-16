package com.cocoegg.effectivejava;

import java.time.Instant;
import java.util.Date;

public class _50 {
    public static void main(String[] args) {

        // Attack the internals of a Period instance

        Date start = new Date();

        Date end = new Date();

        Period p = new Period(start, end);

        end.setYear(78);  // Modifies internals of p!


    }
}


final class Period {

    private final Date start;

    private final Date end;



    /**

     * @param  start the beginning of the period

     * @param  end the end of the period; must not precede start

     * @throws IllegalArgumentException if start is after end

     * @throws NullPointerException if start or end is null

     */

    public Period(Date start, Date end) {

        if (start.compareTo(end) > 0)

            throw new IllegalArgumentException(

                    start + " after " + end);

        this.start = start;

        this.end   = end;

    }

    public Date start() {

        return start;

    }

    public Date end() {

        return end;

    }


}