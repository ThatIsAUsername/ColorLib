package test;

import java.awt.Color;

import colors.ColorMap;
import colors.NamedColor;

public class TestDriver
{
  public static void main(String[] args)
  {
    ColorMap colorMap = new ColorMap();
    boolean ok = colorMap.initialize("rgb.txt");
    if( !ok )
    {
      System.out.println("Failed to initialize color map; exiting");
      System.exit(1);
    }
    
    final Color PURPLE = new Color(231, 123, 255 );
    printClosestColors(colorMap, PURPLE, 10);
    printClosestColors(colorMap, Color.PINK, 10);
    printClosestColors(colorMap, Color.CYAN, 10);
    printClosestColors(colorMap, Color.ORANGE, 10);
    
    // Create a new color map with my known colors
    ColorMap myColors = new ColorMap();
    myColors.add(new NamedColor("PURPLE", PURPLE));
    myColors.add(new NamedColor("PINK", Color.PINK));
    myColors.add(new NamedColor("CYAN", Color.CYAN));
    myColors.add(new NamedColor("ORANGE", Color.ORANGE));

    // Make up a new color randomly and find a decent name for it by
    // leveraging the larger colorMap. I suppose we could also just
    // use the color from the color map. Hm.
    int misses = 0;
    while(misses < 5)
    {
      int newR = (int)(Math.random()*256);
      int newG = (int)(Math.random()*256);
      int newB = (int)(Math.random()*256);
      Color newColor = new Color(newR, newG, newB);
      NamedColor[] sortedColors = colorMap.getSortedList(newColor);
      NamedColor[] mySortedColors = myColors.getSortedList(newColor);
      double dist = myColors.getDistance(newColor, mySortedColors[0].getColor());
      NamedColor newNamedColor = new NamedColor(sortedColors[0].getName(), newColor);
      System.out.println("New color '" + newNamedColor.getName() + "' (" + newColor + ") is " + dist + " away from any previous entry in myColor");
      if( dist > 150 )
      {
        System.out.println("  Adding new named color: " + newNamedColor);
        myColors.add(newNamedColor);
      }
      else
      {
        //System.out.println("  Too close to existing colors. Skipping.");
        misses++;
      }
    }

    System.exit(0);
  }
  
  private static void printClosestColors(ColorMap cm, Color c, int n)
  {
    NamedColor sortedColors[] = cm.getSortedList(c);
    StringBuilder sb = new StringBuilder();
    sb.append("The " + n + " colors nearest to " + c + ":");
    for(int i = 0; i < n; ++i)
    {
      sb.append("\n  " + sortedColors[i]);
    }
    System.out.println(sb.toString());
  }
}
