package painter;
import java.awt.Color;
import java.io.Serializable;


@SuppressWarnings("serial")
class Point implements Serializable 
{
 int x,y;
 Color color;
 int tool;

 Point(int x, int y, int tool, Color color)
 {
  this.x = x;
  this.y = y;
  this.color = color;
  this.tool = tool;
  }
}


