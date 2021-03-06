package playerinterface;

import model.Constants;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.World;
import org.mt4j.util.math.Vector3D;

import physic.shape.util.PhysicsHelper;



public class PlayerBulletLoader {
	Vec2 goalCenter;
	float angle;
	PlayerInterface myPI;
	int bulletsNumber;
	int reloadDelay_ms;
	
	public PlayerBulletLoader(PlayerInterface pI){
		myPI = pI;
		bulletsNumber = Constants.loaderBulletsNumber;
		reloadDelay_ms = Constants.loaderReloadDelay_ms;
		reload();
	}
	
	public void reload(){
		float interangle = (float) Math.toRadians(90/(float)bulletsNumber);
		//System.out.println("interangle: "+interangle);
		for(int i=0;i<bulletsNumber;i++){
			Vector3D pos = new Vector3D();
			System.out.println("angle bulletL: "+myPI.myAngle);
			pos.x=(float) (
					(myPI.getMyPG().getBody().getPosition().x)*myPI.getMyGCS().getScale()+
					Math.cos(myPI.myAngle-Math.PI-(((bulletsNumber/2)-i-0.5)*interangle))*Constants.loaderBulletDistance
					);
			pos.y=(float) (
					(myPI.getMyPG().getBody().getPosition().y)*myPI.getMyGCS().getScale()+
					Math.sin(myPI.myAngle-Math.PI-(((bulletsNumber/2)-i-0.5)*interangle))*Constants.loaderBulletDistance
					);
			PlayerBullet b = new PlayerBullet(myPI.myGCS.getMTApplication(), pos, 10,
					myPI.getMyGCS().getWorld(), 1.0f, 0, 1,
					myPI.getMyGCS().getScale(), myPI);
			myPI.getMyGCS().getPhysicsContainer().addChild(b);
			PhysicsHelper.addDragJoint(myPI.getMyGCS().getWorld(), b, b.getBody().isDynamic(), myPI.getMyGCS().getScale());
			//System.out.println("bullet added in "+pos.toString());
		}
	}
}
