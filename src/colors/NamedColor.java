package colors;

import java.awt.Color;

public class NamedColor
{
  private String myName = null;
  private Color myColor = null;
  
  public NamedColor(String name, Color color)
  {
    myName = name;
    myColor = color;
  }
  
  public String getName()
  {
    return myName;
  }
  
  public final Color getColor()
  {
    return myColor;
  }
  
  @Override
  public String toString()
  {
    return myName + " (" + myColor + ")";
  }
}
