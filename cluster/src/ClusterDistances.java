public class ClusterDistances {
    public Cluster cluster;
    public double distance;
    public Object object;

    public ClusterDistances(Cluster cluster, Object object, double distance){
        this.distance = distance;
        this.cluster = cluster;
        this.object = object;
    }

    public double getDistance() {
        return distance;
    }


}
