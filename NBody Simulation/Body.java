public class Body { 
	/** Instance Variables for Body Class */
	public double xxPos;
	public double yyPos;
	public double xxVel;
	public double yyVel;
	public double mass;
	public String imgFileName;
	private static double gc = 6.67e-11;

	/** First constructor of body */
	public Body(double xP, double yP, double xV, double yV, double m, String img) {
		xxPos = xP;
		yyPos = yP;
		xxVel = xV;
		yyVel = yV;
		mass = m;
		imgFileName = img;
	}

	/** Second constructor of body */
	public Body(Body b) {
		this.xxPos = b.xxPos;
		this.yyPos = b.yyPos;
		this.xxVel = b.xxVel;
		this.yyVel = b.yyVel;
		this.mass= b.mass;
		this.imgFileName = b.imgFileName;
	}

	/** Returns the euclidean distance between two bodys */
	public double calcDistance(Body a) {
		double xdif = this.xxPos - a.xxPos;
		double ydif = this.yyPos - a.yyPos;
		return Math.sqrt(xdif*xdif + ydif*ydif);
	 }

	/** Returns F (force exerted by) two bodies */
	public double calcForceExertedBy(Body c) {
		return (gc * this.mass * c.mass)/(this.calcDistance(c)*(this.calcDistance(c)));
		}

	/** Returns a force by x on body d */
	public double calcForceExertedByX(Body d) {
		return (this.calcForceExertedBy(d)*(d.xxPos-this.xxPos))/(this.calcDistance(d));
	}
	/** Returns a force by x on body e */
	public double calcForceExertedByY(Body e) {
		return (this.calcForceExertedBy(e)*(e.yyPos-this.yyPos))/(this.calcDistance(e));
	}
	/** Returns F net x on body called */
	public double calcNetForceExertedByX(Body[] bodys) {
		double nfx = 0.0;
		double ofx = 0.0;
		for (Body a:bodys) {
			if (this.equals(a)){
				continue;
			} else {
				ofx = this.calcForceExertedByX(a);
				nfx += ofx;
			}
		}
		return nfx;
	}
	/** Returns F net y on body called */
	public double calcNetForceExertedByY (Body[] bodys) {
		double nfy = 0.0;
		double ofy = 0.0;
		for (Body b:bodys) {
			if (this.equals(b)){
				continue;
			} else {
				ofy = this.calcForceExertedByY(b);
				nfy += ofy;
			}
		}
		return nfy;
	}
	/** Updates position and velocity of body instance */
	public void update(double dt, double fX, double fY) {
		double aX = fX/this.mass;
		double aY = fY/this.mass;
		this.xxVel = this.xxVel + dt*aX;
		this.yyVel = this.yyVel + dt*aY;
		this.xxPos = this.xxPos+dt*this.xxVel;
		this.yyPos = this.yyPos+dt*this.yyVel;
	}

	public void draw() {
		StdDraw.picture(xxPos, yyPos, "images/"+ imgFileName);
	}

}