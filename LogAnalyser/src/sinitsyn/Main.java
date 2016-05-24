package sinitsyn;

import java.io.*;
import java.lang.*;

public class Main {

    public static void main(String[] args)
    {
        if (args.length == 0)
        {
            System.out.println("Не указан входной параметр: имя лог-файла");
            return;
        }
        CallLogAnalyzer callLogAnalyzer = new CallLogAnalyzer();
        try
        {
            callLogAnalyzer.readLog(args[0]);
        }
        catch (IOException e)
        {
            System.out.println(e);
            return;
        }
        ResultStatistic stat = callLogAnalyzer.getStatistic();
        for(MethodCallStat methodStat : stat)
        {
            System.out.println(String.format("OperationsImpl: %s min %d, max %d, avg %.0f, max id %d, count %d",
                    methodStat.getMethodName(), methodStat.getMinTimeCall(),
                    methodStat.getMaxTimeCall(), methodStat.getAvg(),
                    methodStat.getMaxId(), methodStat.getCount()));
        }
    }
}
