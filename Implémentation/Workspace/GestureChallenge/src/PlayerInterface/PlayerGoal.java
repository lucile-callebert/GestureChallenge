package playerinterface;

import model.Constants;

import org.jbox2d.dynamics.World;
import org.mt4j.components.TransformSpace;
import org.mt4j.components.visibleComponents.widgets.MTTextField;
import org.mt4j.util.MTColor;
import org.mt4j.util.font.FontManager;
import org.mt4j.util.math.ToolsMath;
import org.mt4j.util.math.Vector3D;


import physic.shape.PhysicsCircle;
import processing.core.PApplet;

public class PlayerGoal extends PhysicsCircle {

	
	public PlayerGoal(PApplet applet, Vector3D centerPoint,
			World world,
			float worldScale, MTColor color, int number, float angle) {
		super(applet, centerPoint, Constants.radiusGoals, world, 0f, 0f, 0f,
				worldScale);
		//MTColor col1 = new MTColor(ToolsMath.getRandom(60, 255),ToolsMath.getRandom(60, 255),ToolsMath.getRandom(60, 255));
		
		this.setName("playerGoal");
		this.setFillColor(color);
		//color = MTColor.WHITE;
		MTColor darker = new MTColor(color.getR()/2f, color.getG()/2f, color.getB()/2f);

		
		//color.setColor(color.getR()-50, color.getG()-30, color.getB()-30);
		this.setStrokeColor(darker);
		this.setStrokeWeight(4);
		
		MTTextField jdisp = new MTTextField(applet, 0,0, 12f, 10f, FontManager.getInstance().createFont(applet, "SansSerif", 10));
		jdisp.scale(0.1f, 0.1f, 0.1f, jdisp.getCenterPointLocal());
		jdisp.setInnerPadding(0);
		this.addChild(jdisp);
		jdisp.setPositionGlobal(new Vector3D(this.getCenterPointGlobal().x,this.getCenterPointGlobal().y));
		System.out.println("disp = "+jdisp.getPosition(TransformSpace.GLOBAL));
		jdisp.setFontColor(MTColor.BLACK);
		jdisp.setNoFill(true);
		jdisp.setNoStroke(true);
		jdisp.setText("P"+number);
		jdisp.setPickable(false);
		jdisp.rotateZ(jdisp.getCenterPointGlobal(), (float) Math.toDegrees(angle)-90);
		//jdisp.rotateZGlobal(jdisp.getCenterPointGlobal(), angle);
		jdisp.removeAllGestureEventListeners();
	}

}
