package org.team3236;

public class Conversion {

	public enum Units {
		IN(1.0), FT(1/12), MM(1/25.4), CM(1/2.54), M(39.37);

		private double convVal;

		Units(double conversion) {
			this.convVal = conversion;
		}

		// Returns the conversion factor relative to 1 inch
		public double getConversionFactor() {
			return this.convVal;
		}
	}

	private double baseUnits;
	private Units unitType;

	/**
	 * Creates a new conversion factor based on number of ticks per unit
	 * @param base the number of ticks
	 * @param unit a unit conversion factor
	 */
	public Conversion(double base, Units unit) {
		baseUnits = base;
		unitType = unit;
	}

	/**
	 * Converts raw value into inches based on given units
	 * @param rawVal the raw value (i.e., ticks on an encoder)
	 * @return number of inches that is based on the units given on instantiation
	 */
	public double getInches(double rawVal) {
		return (rawVal/this.baseUnits) * unitType.getConversionFactor();
	}

	/** 
	* Returns raw value based on conversion's units
	* @param value the amount of units to convert
	* @return number of ticks per distance unit
	*/
	public double getRawValue(double value) {
		return value * baseUnits;
	}
}