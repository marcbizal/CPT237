/**
 * This class provides demos of some basic Math operations in java,
 * by manipulating it's two object members x and y.
 * @author Marcus Bizal
 */

package edu.trident.bizal.assignment0;

public class MathDemo {
	
	private double x;
	private  double y;
	
	public MathDemo(double x_, double y_)
	{
		x = x_;
		y = y_;
	}
	
	/**
	 * Returns the smaller of the two object members x, and y.
	 */
	
	public double min()
	{
		if (x > y) return y;
		else return x;
	}
	
	/**
	 * Returns the greater of the two object members x, and y.
	 */
	
	public double max()
	{
		if (x > y) return x;
		else return y;
	}
	
	/**
	 * Returns the absolute product of x and y, raised to the p power.
	 * @param p The power |x*y| should be raised to.
	 * @return |x*y|^p
	 */
	
	public double productToPower(double p)
	{
		return Math.pow(Math.abs(x * y), p);
	}
	
	/**
	 * Returns the mod of x to the y power.
	 * @param p The mod to be applied to x^y.
	 * @return (x^y)%p
	 */
	
	public double modOfPower(double p)
	{
		return Math.pow(x, y) % p;
	}
	
	/**
	 * Returns the product of x and y ignoring their signs and applying the sign of `p`.
	 * @param p An arbitrary number to copy the sign from.
	 * @return |x*y| with the sign of p.
	 */
	
	public double signProduct(double p)
	{
		return Math.copySign(Math.abs(x * y), p);
	}
}
