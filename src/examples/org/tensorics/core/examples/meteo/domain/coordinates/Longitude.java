package org.tensorics.core.examples.meteo.domain.coordinates;

public class Longitude implements MeteoCoordinate{
	@Override
	public String toString() {
		return "[longitude=" + longitude + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(longitude);
		result = prime * result + (int) (temp ^ (temp >>> 32));
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
		Longitude other = (Longitude) obj;
		if (Double.doubleToLongBits(longitude) != Double
				.doubleToLongBits(other.longitude))
			return false;
		return true;
	}

	private double longitude;

	public double getLongitude() {
		return longitude;
	}

	public Longitude(double longitude) {
		super();
		this.longitude = longitude;
	}

}
