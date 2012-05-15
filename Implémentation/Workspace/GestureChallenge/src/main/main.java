package main;

import org.mt4j.MTApplication;
import org.mt4j.input.inputSources.MacTrackpadSource;
import org.mt4j.util.math.Vector3D;

import physicsShapes.PhysicsRectangle;

import GameModel.GameModel;
import GestureChallengeScene.GestureChallengeScene;




public class main extends MTApplication {
	private static final long serialVersionUID = 1L;

	public static void main(String[] args) {
		initialize();
	}
	
	@Override
	public void startUp() {
		getInputManager().registerInputSource(new MacTrackpadSource(this));
		GestureChallengeScene GCS = new GestureChallengeScene(this, "GestureChallenge");
		addScene(GCS);
		GameModel GM = new GameModel(GCS);
		GM.setPlayerNumber(3);
		GM.createInterfaces();
		//PhysicsRectangle r = new PhysicsRectangle(new Vector3D(this.width/2f+50,this.height/2f), 30,30, this, GCS.getWorld(), 0f, 0f, 0f, GCS.getScale());
		//GCS.getPhysicsContainer().addChild(r);
		//r.rotateZGlobal(r.getCenterPointGlobal(), (float) Math.toDegrees(Math.PI/3f));
		//r.setCenterRotation((float) Math.PI/3f);
	}

}

