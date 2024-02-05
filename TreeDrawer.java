import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import javax.swing.JFrame;

public class TreeDrawer extends JFrame {
	static int nodeHeight = 30;
	static int nodeWidth = 75;
	static int edgeLength = 80;
	static int edgeWidth = nodeWidth + 50;
	SymExTree tree; 
	public TreeDrawer(SymExTree tree) {
        super("Symbolic Execution Tree");
 
        getContentPane().setBackground(Color.WHITE);
        setSize(2*(edgeWidth)*(1+tree.treeHeight(tree.root)) + 100, (nodeHeight + edgeLength)*(1+tree.treeHeight(tree.root)) + 100);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        this.tree = tree;
        
    }
	
	private class Point{
		int x;
		int y;
		public Point(int x, int y) {
			this.x = x;
			this.y = y;
		}
	}
	
	void drawTree(Graphics g) {
		drawTreeRec(g, tree.root, new Point(50+edgeWidth*(1+tree.treeHeight(tree.root)), 50), new Point(50+edgeWidth*(1+tree.treeHeight(tree.root)), 50 + edgeLength), 1);
	}
	
	void drawTreeRec(Graphics g, Edge edge, Point start, Point end, int level) {
        Graphics2D g2d = (Graphics2D) g;
        
        //Draw edge
        g2d.drawLine(start.x, start.y, end.x, end.y);
        g2d.drawString(String.join(", ", edge.getDecs()), (start.x + end.x)/2, (start.y + end.y)/2);
        
        //Draw node
        Node target = edge.getTarget(tree.nodes);
        g2d.draw(new Rectangle(end.x-nodeWidth/2, end.y, nodeWidth, nodeHeight));
        g2d.drawString(target.getComp(), end.x - target.getComp().length()*3, end.y + nodeHeight/2+5);
        
        //Draw next edges recursively
        if (!target.getComp().contains("return")) {
        	drawTreeRec(g, target.getHigh(), new Point(end.x, end.y+nodeHeight), new Point(end.x - edgeWidth/level, end.y+nodeHeight+edgeLength), level + 1);
        	drawTreeRec(g, target.getLow(), new Point(end.x, end.y+nodeHeight), new Point(end.x + edgeWidth/level, end.y+nodeHeight+edgeLength), level + 1);
        }
    }
 
	public void paint(Graphics g) {
        super.paint(g);
        drawTree(g);
    }
}
