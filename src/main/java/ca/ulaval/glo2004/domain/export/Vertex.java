package ca.ulaval.glo2004.domain.export;

public class Vertex {
    public double x, y, z;

    public Vertex(double x, double y, double z){
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public double getX(){ return x; }
    public double getY(){ return y; }
    public double getZ(){ return z; }

    public void addX (double adding){   setX( this.x + adding);}
    public void addY (double adding){   setY(this.y + adding);}
    public void addZ (double adding){   setZ(this.z + adding);}

    public void setX(double x){
        this.x = x;
    }
    public void setY(double y){
        this.y = y;
    }
    public void setZ(double z){
        this.z = z;
    }

}
