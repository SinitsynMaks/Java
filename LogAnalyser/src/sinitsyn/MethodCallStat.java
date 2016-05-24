package sinitsyn;

/*
   Сущностный класс, хранящийся в хеш-таблице и содержащий статистику по вызовам каждого метода
*/
public class MethodCallStat
{

    public MethodCallStat(String methodName) {
        this.methodName = methodName;
    }

    private String methodName; // Поле "Имя метода"

    public String getMethodName() {
        return methodName;
    }
    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    private long minTimeCall; // Поле "минимальное время работы с методом"

    public long getMinTimeCall() {
        return minTimeCall;
    }
    public void setMinTimeCall(long minTimeCall) {
        this.minTimeCall = minTimeCall;
    }

    private long maxTimeCall; // Поле "максимальное время работы с методом"

    public long getMaxTimeCall() {
        return maxTimeCall;
    }
    public void setMaxTimeCall(long maxTimeCall) {
        this.maxTimeCall = maxTimeCall;
    }

    private int maxId; // Поле "ID самого максимального вызова"

    public int getMaxId() {
        return maxId;
    }
    public void setMaxId(int maxId) {
        this.maxId = maxId;
    }

    private int count; // Поле "общее количество вызовов метода"

    public int getCount() {
        return count;
    }
    public void setCount(int count) {
        this.count = count;
    }

    private  double avg; // Поле "среднее время работы с методом"

    public double getAvg() {
        return avg;
    }
    public void setAvg(double avg) {
        this.avg = avg;
    }
}
