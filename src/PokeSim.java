
//PokeSim V1!

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JComponent;
import javax.swing.JFrame;

//Classes
/*	
 * Type
 * Pokemon
 * Viewer
 * World
 * PokeSim
 */

class Type{
	static String[] allTypes = {"normal", "fire", "water", "electric", "grass",
								"ice","fighting", "poison", "ground", "flying",
								"psychic", "bug", "rock", "ghost","dragon",
								"dark", "steel","fairy"};

	//Return random type
	public static int randomType(){
		return (int)(Math.random()*allTypes.length);
	}
	
	//Print name of a type associated with that int
	public static String toString(int t){
		try{
			return allTypes[t];
		}catch (NullPointerException e){
			return "Whoops";
		}
	}
	
	//Determines the multiplier of type1 attack on type2 defense
	//Oh god is there a nicer way to do this
	public static double compareTypes(int type1, int type2){
		switch(type1){
		case 0:
			switch(type2){
			case 12:				//not very effective
			case 16:
				return .5;
			case 13:				//no effect
				return 0;
			}
		break;
		case 1:
			switch(type2){			//not very effective
			case 2:
			case 12:
			case 14:
				return .5;
			case 4:					//super effective
			case 5:
			case 11:
			case 16:
				return 2;
			}
		break;
		case 2:
			switch(type2){
			case 2:					//not very effective
			case 4:
			case 14:
				return .5;
			case 1:					//super effective
			case 8:
			case 12:
				return 2;
			}
		break;
		case 3:
			switch(type2){
			case 8:					//no effect
				return 0;
			case 3:					//not very effective
			case 4:
			case 14:
				return .5;
			case 2:
			case 9:					//super effective
				return 2;
			}
		break;
		case 4:
			switch(type2){
			case 1:					//not very effective
			case 4:
			case 7:
			case 9:
			case 11:
			case 14:
			case 16:			
				return .5;
			case 2:		
			case 8:					//super effective
			case 12:
				return 2;
			}
		break;
		case 5:
			switch(type2){
			case 1:
			case 2:
			case 5:
			case 16:			
				return .5;
			case 4:				
			case 8:
			case 9:
			case 14:
				return 2;
			}
		break;
		case 6:
			switch(type2){
			case 13:					
				return 0;
			case 7:
			case 9:
			case 10:
			case 11:
			case 17:
				return .5;
			case 0:
			case 5:
			case 12:
			case 15:
			case 16:
				return 2;
			}
		break;
		case 7:
			switch(type2){
			case 16:					
				return 0;
			case 7:
			case 8:
			case 12:
			case 13:
				return .5;
			case 4:
			case 17:
				return 2;
			}
		break;
		case 8:
			switch(type2){
			case 9:					
				return 0;
			case 4:
			case 11:
				return .5;
			case 1:
			case 3:
			case 7:
			case 12:
			case 16:
				return 2;
			}
		break;
		case 9:
			switch(type2){
			case 3:
			case 12:
			case 16:
				return .5;
			case 4:
			case 6:
			case 11:
				return 2;
			}
		break;
		case 10:
			switch(type2){
			case 15:					
				return 0;
			case 10:
			case 16:
				return .5;
			case 6:				
			case 7:
				return 2;
			}
		break;
		case 11:
			switch(type2){
			case 1:
			case 6:
			case 7:
			case 9:
			case 13:
			case 16: 
			case 17:									
				return .5;
			case 4:
			case 10: 
			case 15:
				return 2;
			}
		break;
		case 12:
			switch(type2){
			case 6:
			case 8:
			case 16:			
				return .5;
			case 1:
			case 5:
			case 9:
			case 11:
				return 2;
			}
		break;
		case 13:
			switch(type2){
			case 0:					
				return 0;
			case 15:					
				return .5;
			case 13:	
			case 10:
			case 2:
				return 2;
			}
		break;
		case 14:
			switch(type2){
			case 17:					
				return 0;
			case 16:					
				return .5;
			case 14:				
				return 2;
			}
		break;
		case 15:
			switch(type2){
			case 6:
			case 15:
			case 17:
				return .5;
			case 13:	
			case 10:				
				return 2;
			}
		break;
		case 16:
			switch(type2){
			case 1:
			case 2:
			case 3:
			case 16:
				return .5;
			case 5:
			case 12:
			case 17:
				return 2;
			}
		break;
		case 17:
			switch(type2){
			case 1:
			case 7:
			case 16:
				return .5;
			case 6:
			case 15:
			case 14:
				return 2;
			}
		break;
		default:
			return 1;
		}
		return 1;
	}
}

class Pokemon{
	
	boolean active = false; //When newly replicated, active=false so can't attack and immune till next round
	
	int exp = 0;
	int id;
	String name;
	int level;
	
	int[] baseStats = new int[6]; //Order: HP, ATK, DEF, SPATK, SPDEF, SPD
	int[] ivs = new int[6];  //Order: HP, ATK, DEF, SPATK, SPDEF, SPD
	int[] stats = new int[6];  //Order: HP, ATK, DEF, SPATK, SPDEF, SPD
	int atkStr;
	
	int type;
//	String[] types = new String[2];
	
	public Pokemon(int _id, int _level){;
		level = _level;
		id = _id;
		initMon(id);
	}
	
	void initMon(int id){
		type = Type.randomType();
		atkStr = (int)(Math.random()*50)+50;
		for(int i = 0; i< 6; i++){
			ivs[i] = (int)(Math.random()*31) +1;
			baseStats[i] = (int)(Math.random()*400)+50;
			calcStats();
		}
	}
	
	//updates pokemon's stats based on base stats, ivs, and current level
	void calcStats(){
		for(int i = 0; i < 6; i++){
			stats[i] = (int)(((float)level/50)*((float)baseStats[i]) + ((float)level/100)*((float)ivs[i]));
		}
	}
	
	public void gainExp(){
		exp++;
		while(exp>=level){
			levelUp();
			exp -= level;
		}
	}
	
	public void levelUp(){
		level ++;
		calcStats();
	}
	
	//Returns a color based on the pokemon's type
	public Color getColor(){
//		{"normal", "fire", "water", "electric", "grass",
//			"ice","fighting", "poison", "ground", "flying",
//			"psychic", "bug", "rock", "ghost","dragon",
//			"dark", "steel","fairy"};
		Color color = Color.decode("#FFFFFF");
		switch (type){
		case 0:
			color = Color.decode("#afaf7b");
			break;
		case 1:
			color = Color.decode("#FB5C20");
			break;
		case 2:
			color = Color.decode("#5d88Ff");
			break;
		case 3:
			color = Color.decode("#f3e41f");
			break;
		case 4:
			color = Color.decode("#6fde47");
			break;
		case 5:
			color = Color.decode("#87d2d2");
			break;
		case 6:
			color = Color.decode("#af2c25");
			break;
		case 7:
			color = Color.decode("#903a90");
			break;
		case 8:
			color = Color.decode("#d9b349");
			break;
		case 9:
			color = Color.decode("#a18be3");
			break;
		case 10:
			color = Color.decode("#f73972");
			break;
		case 11:
			color = Color.decode("#9dab1e");
			break;
		case 12:
			color = Color.decode("#aa9334");
			break;
		case 13:
			color = Color.decode("#69568c");
			break;
		case 14:
			color = Color.decode("#5919f2");
			break;
		case 15:
			color = Color.decode("#49413b");
			break;
		case 16:
			color = Color.decode("#b3b3c8");
			break;
		case 17:
			color = Color.decode("#e489e4");
			break;
		}
		return color;
	}
	
	//Static methods
	public static Pokemon randomPokemon(){
		return new Pokemon((int)(Math.random()*802), 1);
	}
}

class Viewer extends JComponent{
	World world;
	int stretch;
	
	public Viewer(World _world, int _stretch){
		world = _world;
		stretch = _stretch;
	}
	
	public void paintComponent(Graphics g){
        for(int i = 0; i < world.size; i ++){
        	for (int j = 0; j < world.size; j++){
        		g.setColor(world.population[i][j].getColor());
        		g.fillRect(i*stretch, j*stretch, stretch, stretch);
        	}
        }
        
        paintVariables(g);
        
//        addMouseListener(new MouseAdapter(){
//            public void mousePressed(MouseEvent me) { 
//                
//            }
//        });
      }
	
	public void paintVariables(Graphics g){
		 g.setColor(Color.black);
	     g.drawString(""+world.epoch, stretch*20, stretch*world.size + 20);
	     Point p = this.getMousePosition();
	     try{
	    	 if(p.x/stretch < world.size && p.y/stretch < world.size){
	    		 Pokemon mon = world.population[p.x/stretch][p.y/stretch];
	    	 	g.drawString(Type.toString(mon.type) + " " + mon.level + " " + mon.stats[0], stretch*20, stretch*world.size + 40);
	    	 }
	     }catch (NullPointerException e){
	    	 
	     }
	}
}

class World{
	int size;
	int stretch;
	Pokemon[][] population;
	int[] spdOrder;
	int spds[] = new int[1000];	//Part of the speed sorting
	
	int epoch = 0;
	
	JFrame frame;
	Viewer subviewer;
	
	public World(int _size, int _stretch){
		size = _size;
		stretch = _stretch;
		
		spdOrder = new int[size*size];
		population = initPop(size);	
		
		frame = new JFrame();
        frame.setTitle("PokeSim");
        frame.setSize(size*stretch, size*stretch+80);
        subviewer = new Viewer(this, stretch);
        frame.add(subviewer);
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public Pokemon[][] initPop(int size){
		Pokemon[][] pop = new Pokemon[size][size];
		for(int i = 0; i < size; i++){
			for(int j = 0; j < size; j++){
				pop[i][j] = Pokemon.randomPokemon();
				spdOrder[(i*size)+j] = (i*size)+j;
			}
		}
		return pop;
	}
	
	public void step(){
		 sortSpeed();
		 for(int i = 0; i < spdOrder.length; i ++){
			 int mon = spdOrder[i];
			 if(population[mon/size][mon%size].active && !(mon/size == 0 && mon%size == 0)){	//Upperleftmost mon is buggy- not letting him attack. :(
				 int wn = determineWN(mon);
				 if(wn>0) attack(mon, wn);
			 }
		 }	
		 activateAllMons();	//at the end of a round, activate all new mons
		 epoch ++;
	}
	
	public int determineWN(int mon){
		int[] potMons = findNeighbors(mon);
		Pokemon attacker = population[mon/size][mon%size];
		Pokemon defender;
		
		double bestRatio = -1;
		int bestDir = -1;
		
		for(int i = 0; i < 4; i++){
			defender = population[potMons[i]/size][potMons[i]%size];
			if(defender.id != attacker.id && defender.active){
				for(int j = 1; j < 4; j+=2){
					double ratio = (attacker.stats[j])/(defender.stats[j+1]) * Type.compareTypes(attacker.type, defender.type);
					if(ratio > bestRatio){
						bestRatio = ratio;
						bestDir = i;
					}
				}
			}
		}
		if(bestDir == -1) return -1;
		else return potMons[bestDir];
	}
	
	//Might have an issue- upperleftmost mon is always invincible
	int[] findNeighbors(int mon){
		int[] neighbors = new int[4];
		
		//right neighbor
		if(mon%size + 1 == size){
			neighbors[0] = mon-size+1;
		}else{
			neighbors[0] = mon+1;
		}
		
		//left neighbor
		if(mon%size-1<0){
			neighbors[1] = mon+size-1;
		}else{
			neighbors[1] = mon-1;
		}
		
		//up
		if(mon/size == 0){
			neighbors[2] = mon+(size*(size-1));
		}else{
			neighbors[2] = mon-size;
		}
		
		//down
		if(mon/size == size-1){
			neighbors[3]= mon-(size*(size-1));
		}else{
			neighbors[3] = mon+size;
		}
		
		return neighbors;
	}
	
	//deal damage to weakest neighbor and gain experience
	public void attack(int mon, int wn){
		Pokemon attacker = population[mon/size][mon%size];
		Pokemon defender = population[wn/size][wn%size];
		
		float typeEff = 1;
		int style = 0;
		
		float atkStr = attacker.atkStr;
		float lvl = attacker.level;
		
		float atk;
		float def;
		if(style == 0){
			atk = attacker.stats[1];
			def = defender.stats[2];
		}else{
			atk = attacker.stats[3];
			def = defender.stats[4];
		}
		
		defender.stats[0] -= Type.compareTypes(attacker.type, defender.type)*((((2*(lvl+10)/250)*(atk/def)*atkStr)+2)*typeEff);
		
		if(defender.stats[0] <= 0){
			replicate(mon, wn);
			if(attacker.level<100){		//Level cap at 100
				attacker.gainExp();
			}
		}
	}
	
	//creates a replica of mon in neighbor's position (replacing it)
	public void replicate(int mon, int neighbor){
		Pokemon attacker = population[mon/size][mon%size];
		population[neighbor/size][neighbor%size] = new Pokemon(attacker.id, 1);
		Pokemon defender = population[neighbor/size][neighbor%size];
		
		defender.type = attacker.type;
		defender.level = attacker.level;
//		defender.exp = attacker.exp;
		defender.id = attacker.id;
		for(int i = 0; i < 6; i++){
			defender.baseStats[i] = attacker.baseStats[i];
//			defender.ivs[i] = attacker.ivs[i];
		}
		defender.calcStats();
	}
	
	public void activateAllMons(){
		for(int i = 0; i < size; i++){
			for(int j = 0; j < size; j++){
				population[i][j].active = true;
			}
		}
	}
	
	//Almost certainly not the best way to sort Pokemon by speed
	//(count sort)
	public void sortSpeed(){
		int n = spdOrder.length;
		for(int i = 0; i < spds.length; i++){
			spds[i] = 0;
		}
		for (int i = 0; i < n; i++){
            spds[(population[i/size][i%size].stats[5])]++;
		}
		for (int i = 1; i < spds.length; i++){
            spds[i] += spds[i-1];
		}
		for (int i = n-1; i>=0; i--){
            int monSpd = (population[i/size][i%size].stats[5]);
            spds[monSpd]--;
            spdOrder[spds[monSpd]] = i;
		}
	}
	
	//I was lazy with the directories, so this will only work on unix right now I think
	public void saveImage(String location) throws IOException{
		BufferedImage image = new BufferedImage(subviewer.getWidth(), subviewer.getHeight(), BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics2D = image.createGraphics();
        subviewer.paint(graphics2D);
        
        ImageIO.write(image,"jpeg", new File(location+ "/foldy/pic" + epoch + ".jpeg"));
       
	}
	
	public void repaint(){
		frame.repaint();
	}
	
}




//Runs well in real time up to a size of about 300
//After that, you probably want to be saving the images to see the sim sped up
//Add the desired location as a command line argument ie /users/zack/desktop
//A folder named foldy will be created at the location, and the images stored there.
public class PokeSim {
	public static void main(String[] args) throws InterruptedException, IOException {
		int SIZE =200;
		int STRETCH = 2;
//		boolean saveToDesktop = false;
		boolean running = true;
		
		World world = new World(SIZE, STRETCH);
		
		if(args.length>0){
			File dir = new File(args[0]+"/foldy/");
			dir.mkdir();
		}
        
		while(running){
			world.step();
			world.repaint();
			//If a location was added as a command line argument, it'll assume its a directory and try to save there
			if(args.length>0 && world.epoch%10 == 0){
				Thread.sleep(2);	//Needs this? Otherwise image isn't fully loaded when it saves
				world.saveImage(args[0]);
			}
		}
	}
}
