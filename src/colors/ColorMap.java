package colors;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import colors.distance.ColorComparator;
import colors.distance.LinearRGBDistanceComparator;

public class ColorMap
{
  private ArrayList<NamedColor> myColors = null;
  private ColorComparator myComparator;

  public ColorMap()
  {
    myColors = new ArrayList<NamedColor>();
    myComparator = new LinearRGBDistanceComparator(Color.BLACK);
  }
  
  public boolean initialize(String colorList)
  {
    BufferedReader reader = null;
    try
    {
      reader = new BufferedReader(new FileReader(colorList));
    }
    catch( FileNotFoundException fnfe )
    {
      System.out.println("Failed to find " + colorList);
      return false;
    }

    System.out.println("Parsing color list");
    try
    {
      String line = reader.readLine();

      for(; null != line; line = reader.readLine())
      {
        String[] tokens = line.split("#");
        if( tokens.length != 2 || tokens[0].length() == 0)
        {
          // We should have one '#' in each line, separating the color name and hex value.
          // The first line has a leading '#', but it's just a comment.
          continue;
        }

        String name = tokens[0];
        int nameEnd = name.length()-1;
        while( name.charAt(nameEnd) == ' ') nameEnd--;
        name = name.substring(0, nameEnd);
        String hex = '#' + tokens[1].trim();
        Color decodedColor = null;
        try{
          decodedColor = Color.decode(hex);
        }catch(NumberFormatException nfe) {
          System.out.println("Failed to decode color '" + hex + "'");
          continue;
        }

        colors.NamedColor newColor = new NamedColor(name, decodedColor);

        System.out.println("Read in color " + newColor );
        myColors.add(newColor);
      }

      System.out.println("Finished parsing color list");
      reader.close();
    }
    catch( IOException ioe )
    {
      System.out.println("Error while reading file. Details:");
      System.out.println(ioe.toString());
    }

    return myColors.size() > 0;
  }

  public void add(NamedColor color)
  {
    myColors.add(color);
  }
  
  public int size()
  {
    return myColors.size();
  }
  
  public void setComparator(ColorComparator cmp)
  {
    myComparator = cmp;
  }
  
  public NamedColor[] getSortedList(Color color)
  {
    myComparator.setBasisColor(color);
    myColors.sort(myComparator);
    NamedColor[] colors = new NamedColor[myColors.size()];
    return myColors.toArray(colors);
  }

  public double getDistance(Color color0, Color color1)
  {
    return myComparator.calculateDistance(color0, color1);
  }
}
