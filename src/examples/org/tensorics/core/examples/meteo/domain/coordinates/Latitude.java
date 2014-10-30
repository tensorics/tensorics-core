package org.tensorics.core.examples.meteo.domain.coordinates;

public class Latitude implements MeteoCoordinate{
	private double lattitude;

	public Latitude(double lattitude) {
		super();
		this.lattitude = lattitude;
	}

	public double getLattitude() {
		return lattitude;
	}

	@Override
	public String toString() {
		return "[lattitude=" + lattitude + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(lattitude);
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
		Latitude other = (Latitude) obj;
		if (Double.doubleToLongBits(lattitude) != Double
				.doubleToLongBits(other.lattitude))
			return false;
		return true;
	}

}
