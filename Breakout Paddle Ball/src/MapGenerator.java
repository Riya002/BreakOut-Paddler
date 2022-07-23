import java.awt.*;

public class MapGenerator {
    public int [][]mapArray;
    public int brickWidth;
    public int brickHeight;
    public MapGenerator(int row,int col)
    {
        mapArray=new int[row][col];
        for(int i=0;i<mapArray.length;i++)
        {
            for(int j=0;j<mapArray[0].length;j++) {
                mapArray[i][j] = 1; //brick is not broken initially
            }
        }
        brickWidth=540/col;
        brickHeight=150/row;
    }
    public void draw(Graphics2D g)
    {
        for(int i=0;i<mapArray.length;i++)
        {
            for(int j=0;j<mapArray[0].length;j++) {
               if(mapArray[i][j]>0)
               {
                   g.setColor(Color.white);
                   g.fillRect(j*brickWidth+80,i*brickHeight+50,brickWidth,brickHeight);

                   g.setStroke(new BasicStroke(3));
                   g.setColor(Color.black);
                   g.drawRect(j*brickWidth+80,i*brickHeight+50,brickWidth,brickHeight);
               }
            }
        }
    }
    public void setBrickValue(int value,int row,int col)
    {
        mapArray[row][col]=value;
    }
}
