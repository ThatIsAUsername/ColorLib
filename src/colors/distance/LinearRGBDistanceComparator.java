package colors.distance;

import java.awt.Color;

import colors.NamedColor;

/**
 * Sorts colors based on the linear distance in RGB space from the basis color.
 */
public class LinearRGBDistanceComparator implements ColorComparator
{
  private Color basisColor = null;
  
  public LinearRGBDistanceComparator(Color basis)
  {
    basisColor = basis;
  }
  
  public void setBasisColor(Color basis)
  {
    basisColor = basis;
  }

  @Override
  public int compare(NamedColor color0, NamedColor color1)
  {
    return (int)calculateDifferenceSquared(color0.getColor(), color1.getColor());
  }

  @Override
  public double calculateDistance(Color color)
  {
    return calculateDistance(color, basisColor);
  }

  @Override
  public double calculateDistance(Color color0, Color color1)
  {
    return Math.sqrt(Math.abs(calculateDifferenceSquared(color0, color1)));
  }

  private double calculateDifferenceSquared(Color color0, Color color1)
  {
    int rd0 = color0.getRed() - basisColor.getRed();
    int gd0 = color0.getGreen() - basisColor.getGreen();
    int bd0 = color0.getBlue() - basisColor.getBlue();
    int c0DistSqd = rd0*rd0 + gd0*gd0 + bd0*bd0;
    
    int rd1 = color1.getRed() - basisColor.getRed();
    int gd1 = color1.getGreen() - basisColor.getGreen();
    int bd1 = color1.getBlue() - basisColor.getBlue();
    int c1DistSqd = rd1*rd1 + gd1*gd1 + bd1*bd1;
    
    return c0DistSqd - c1DistSqd;
  }
}
