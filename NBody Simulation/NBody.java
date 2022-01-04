public class NBody {
	/** Reads radius of string passed in */
	public static double readRadius(String s){
		In file = new In(s);
		file.readInt(); 
		return file.readDouble();
	}
	/** Creates an array of bodies of data file t passed in */
	public static Body[] readBodies(String t) {
		In txt = new In(t);
		int numofplan = txt.readInt();
		txt.readDouble();
		Body[] bodys =  new Body[numofplan];
		for (int i=0; i<numofplan; i++) {
			double cb_xpos= txt.readDouble();
			double cb_ypos= txt.readDouble();
			double cb_xvel= txt.readDouble();
			double cb_yvel= txt.readDouble();
			double cb_m = txt.readDouble();
			String cb_img= txt.readString();
			bodys[i]= new Body(cb_xpos, cb_ypos, cb_xvel, cb_yvel, cb_m, cb_img);
		}
		return bodys;
	}
	/** Main method to draw the bodies and update them */
	public static void main (String[] args) {
		double T = Double.parseDouble(args[0]);
		double dt = Double.parseDouble(args[1]);
		String filename = args[2];
		double r = readRadius(filename);
		Body[] btd  = readBodies(filename);
		StdDraw.setScale(-r, r);
		StdDraw.picture(0, 0, "images/starfield.jpg");
		for (Body b: btd) {
			b.draw();
		}
		StdDraw.enableDoubleBuffering();
		for (double itert = 0.0; itert <= T; itert += dt) {
		double[] xForces = new double[5];
		double[] yForces = new double[5];
		int xcount = 0;
		int ycount = 0;
		int updatecount = 0;
		for (Body b:btd) {
			xForces[xcount] = b.calcNetForceExertedByX(btd);
			xcount +=1;
		}
		for (Body c:btd) {
			yForces[ycount] = c.calcNetForceExertedByY(btd);
			ycount +=1;
		}
		for (Body d:btd) {
			d.update(dt,xForces[updatecount], yForces[updatecount]);
			updatecount+=1;
		}
		StdDraw.picture(0, 0, "images/starfield.jpg");
		for (Body e: btd) {
			e.draw();
		}
		StdDraw.show();
		StdDraw.pause(10);
		}
		StdOut.printf("%d\n", btd.length);
		StdOut.printf("%.2e\n", r);
		for (int i = 0; i < btd.length; i++) {
    	StdOut.printf("%11.4e %11.4e %11.4e %11.4e %11.4e %12s\n",
                  btd[i].xxPos, btd[i].yyPos, btd[i].xxVel,
                  btd[i].yyVel, btd[i].mass, btd[i].imgFileName);   
}
		}
	}
