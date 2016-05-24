package sinitsyn;

import java.util.Date;

/*
    Сущностный класс, хранящий первичные данные по вызываемым методам в хеш-таблице
 */
public class CallLogRecord {
    private String methodName;

    public String getMethodName()
    {
        return  methodName;
    }
    public void setMethodName(String methodName)
    {
        this.methodName = methodName;
    }

    private Date callTime;

    public Date getCallTime() {
        return callTime;
    }
    public void setCallTime(Date callTime) {
        this.callTime = callTime;
    }

    public CallLogRecord(String methodName, Date callTime)
    {
        this.methodName = methodName;
        this.callTime = callTime;
    }
}
