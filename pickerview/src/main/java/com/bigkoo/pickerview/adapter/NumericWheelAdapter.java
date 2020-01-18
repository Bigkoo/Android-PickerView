package com.bigkoo.pickerview.adapter;


import com.contrarywind.adapter.WheelAdapter;

/**
 * Numeric Wheel adapter.
 */
public class NumericWheelAdapter implements WheelAdapter {
	
	private int minValue;
	private int maxValue;
	private int interval=1;

	public void setInterval(int interval) {
		this.interval = interval;
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

	@Override
	public Object getItem(int index) {
		if(interval==1) {
			if (index >= 0 && index < getItemsCount()) {
				int value = minValue + index;
				return value;
			}
		}else {
			if (index >= 0 && index < getItemsCount()) {
				int value = minValue + index*interval;
				return value;
			}
		}
		return 0;
	}

	@Override
	public int getItemsCount() {
		if(interval==1) {
			return maxValue - minValue + 1;
		}else {
			return (maxValue-minValue+1)/interval;
		}
	}
	
	@Override
	public int indexOf(Object o){
		try {
			if(interval==1) {
				return (int) o - minValue;
			}else {
				return (int)o/interval-minValue;
			}
		} catch (Exception e) {
			return -1;
		}

	}
}
