package colors.distance;

import java.awt.Color;
import java.util.Comparator;

import colors.NamedColor;

public interface ColorComparator extends Comparator<NamedColor>
{
  /**
   * Sets the color against which other colors are compared for sorting.
   * Colors closer to the basis will be "less than" colors farther away.
   * @param basis
   */
  public void setBasisColor(Color basis);

  /**
   * Calculates the distance between the provided color and the basis color.
   */
  public double calculateDistance(Color color);

  /**
   * Calculates the distance between two colors using this comparator's
   * distance metric. Doesn't use the basis color.
   */
  public double calculateDistance(Color color0, Color color1);
}
