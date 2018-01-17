package com.bigkoo.pickerview.adapter;


/**
 * Numeric Wheel adapter.
 */
public class NumericWheelAdapter implements WheelAdapter {

	/** The default min value */
	public static final int DEFAULT_MAX_VALUE = 9;

	/** The default max value */
	private static final int DEFAULT_MIN_VALUE = 0;

	/** The default step size */
	private static final int DEFAULT_STEP_SIZE = 1;

	// Values
	private int minValue;
	private int maxValue;
	private int stepSize = DEFAULT_STEP_SIZE;

	/**
	 * Default constructor
	 */
	public NumericWheelAdapter() {
		this(DEFAULT_MIN_VALUE, DEFAULT_MAX_VALUE);
	}

	/**
	 * Constructor
	 * @param minValue the wheel min value
	 * @param maxValue the wheel max value
	 */
	public NumericWheelAdapter(int minValue, int maxValue) {
		this.minValue = minValue;
		this.maxValue = maxValue;
	}

	/**
	 * Constructor
	 * @param minValue the wheel min value
	 * @param maxValue the wheel max value
	 */
	public NumericWheelAdapter(int minValue, int maxValue, int stepSize) {
		this.minValue = minValue;
		this.maxValue = maxValue;
		if(stepSize < 1){
			stepSize = DEFAULT_STEP_SIZE;
		}else if(stepSize > maxValue){
			stepSize = maxValue;
		}
		this.stepSize = stepSize;
	}

	@Override
	public Object getItem(int index) {
		if(stepSize > 1){
			return index * stepSize;
		}
		if (index >= 0 && index < getItemsCount()) {
			int value = minValue + index;
			return value;
		}
		return 0;
	}

	@Override
	public int getItemsCount() {
		return (int) Math.ceil((maxValue - minValue + 1) / (double) stepSize);
	}

	@Override
	public int indexOf(Object o){
		try {
			return (int)o - minValue;
		} catch (Exception e) {
			return -1;
		}
	}
}
