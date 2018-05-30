package KNN;

public class KNNData implements Comparable<KNNData> {
	double x; // 坐标轴
	double y;
	double AP1;
	double AP2;
	double AP3;
	double AP4;
	double AP5;
	double distance;
	String type;

	public KNNData(double x, double y,double AP1,double AP2,double AP3,double AP4,double AP5) {
		// TODO 自动生成的构造函数存根
        this.x=x;
        this.y=y;
        this.AP1=AP1;
        this.AP2=AP2;
        this.AP3=AP3;
        this.AP4=AP4;
        this.AP5=AP5;
	}

	@Override
	public int compareTo(KNNData arg0) { // 复写Comparator接口使其按照distance排序
		return Double.valueOf(this.distance).compareTo(Double.valueOf(arg0.distance));
	}
}