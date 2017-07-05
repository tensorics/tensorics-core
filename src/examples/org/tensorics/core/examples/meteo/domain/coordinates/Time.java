package org.tensorics.core.examples.meteo.domain.coordinates;

import java.util.Date;

public class Time implements MeteoCoordinate {
    private long time;

    public Time(long time) {
        super();
        this.time = time;
    }

    public Time() {
        this(new Date().getTime());
    }

    public long getTime() {
        return time;
    }

    @Override
    public String toString() {
        return "[time=" + time + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (int) (time ^ (time >>> 32));
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Time other = (Time) obj;
        if (time != other.time)
            return false;
        return true;
    }

}
