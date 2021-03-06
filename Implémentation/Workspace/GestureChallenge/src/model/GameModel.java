package model;

import java.beans.PropertyChangeSupport;
import java.util.Arrays;
import java.util.Timer;
import java.util.TimerTask;

import org.mt4j.util.MTColor;
import org.mt4j.util.math.ToolsMath;

import playerinterface.PlayerInterface;
import scene.GestureChallengeScene;


public class GameModel {
	PropertyChangeSupport support=new PropertyChangeSupport(this);
	int playerNumber;
	int levelNumber;
	MTColor[] playerColors;
	GestureChallengeScene myGCS;
	PlayerInterface[] myPI;
	int[] ranking;
	Timer myTimer;
	TimerTask endGame;
	TimerTask infoTimeleft;
	
	public GameModel(GestureChallengeScene gCS){
		
		playerColors= new MTColor[4];
		playerColors[0]=new MTColor(188f,140f,255f); //Purple
		playerColors[1]=new MTColor(255f,190f,0f);
		playerColors[2]=new MTColor(0f,217f,255f);
		playerColors[3]=new MTColor(136f,255f,89f);
		myGCS = gCS;
	}
	
	public void createInterfaces(){
		myPI = new PlayerInterface[playerNumber];
		ranking = new int[playerNumber];
		
		for(int i=0;i<playerNumber;i++){
			float angle =((float) ((i+1)*2*Math.PI/(playerNumber)+Math.PI/2f));		
			if(i<4){
				myPI[i]=new PlayerInterface(playerColors[i],i,angle,myGCS,playerNumber);
			}else{
				MTColor col = new MTColor(ToolsMath.getRandom(60, 255),ToolsMath.getRandom(60, 255),ToolsMath.getRandom(60, 255));
				myPI[i]=new PlayerInterface(col,i,angle,myGCS,playerNumber);
			}
			ranking[i]=playerNumber;
		}
	}
	
	public int getPlayerNumber() {
		return playerNumber;
	}
	public void setPlayerNumber(int playerNumber) {
		this.playerNumber = playerNumber;
	}
	public int getLevelNumber() {
		return levelNumber;
	}
	public void setLevelNumber(int levelNumber) {
		this.levelNumber = levelNumber;
	}
	
	public void updateRanking(){
		int[] scores = new int[playerNumber];
		int[] sortedscores = new int[playerNumber];
		for(int i=0;i<playerNumber;i++){
			scores[i]=myPI[i].getMyScore();
			sortedscores[i]=myPI[i].getMyScore();
		}
		
		Arrays.sort(sortedscores);
		int rank=playerNumber+1;
		int lastworse=-1;
		int equals=1;
		boolean[] assigned = new boolean[playerNumber];
		for(boolean i:assigned){
			i=false;
		}
		
		System.out.println("scores are: "+Arrays.toString(scores));	
		System.out.println("orderedScores are: "+Arrays.toString(sortedscores));
		
		for(int i=0;i<playerNumber;i++){
			for(int j=0;j<playerNumber;j++){
				if(!assigned[j]){
					if(sortedscores[i]==scores[j]){
						System.out.println("score "+sortedscores[i]+" found for P"+j);
						if(scores[j]>lastworse){
							lastworse=scores[j];
							rank-=equals;
							equals=1;
						}else{
							equals++;
						}
						System.out.println("assigning rank "+rank+" to P"+j);
						ranking[j]=rank;
						assigned[j]=true;
					}
				}
				
			}
			
		}

		System.out.println("ranks are: "+Arrays.toString(ranking));
		fireRanks();
	}

	public PropertyChangeSupport getSupport() {
		return support;
	}

public void fireRanks(){
	for(int i=0;i<playerNumber;i++){
		System.out.println("firing : ranking"+i);
		support.firePropertyChange("ranking"+i,null,String.valueOf(ranking[i]));
	}
}
	
	public void subscribeInterfaces() {
		for(PlayerInterface i:myPI){
			support.addPropertyChangeListener(i);
		}
		
	}
	
	public void initGame(){
		fireRanks();
		myTimer = new Timer();
		endGame=new TimerTask(){

			@Override
			public void run() {
				System.err.println("TIME'UP");
				
			}
			
		};
		
		infoTimeleft=new TimerTask(){

			@Override
			public void run() {
				int left = (int) (System.currentTimeMillis()-endGame.scheduledExecutionTime());
				int min = ((left/1000)/60)*-1;

				float sec = (left+min*60*1000)*-1;
				sec/=1000f;
				sec=Math.round(sec);
				int secI = (int) sec;
				if(secI==60){
					min++;
					secI=0;
				}
				if(secI/10==0){
					support.firePropertyChange("time", null, String.valueOf(min)+"'"+"0"+String.valueOf(secI)+"''");
					System.out.println(String.valueOf(min)+"'"+"0"+String.valueOf(secI)+"''");

				}else{
					support.firePropertyChange("time", null, String.valueOf(min)+"'"+String.valueOf(secI)+"''");
					System.out.println(String.valueOf(min)+"'"+String.valueOf(secI)+"''");

				}
				
			}
			
		};
		
		
		myTimer.schedule(endGame,(Constants.gameTime)*1000);
		myTimer.schedule(infoTimeleft,0, 1000);
	}
	
}
