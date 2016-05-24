package sinitsyn;

import java.util.Hashtable;
import java.util.Iterator;

/*
    Класс-оболочка над хеш-таблицей и хранящимися в ней данными
*/

public class ResultStatistic implements Iterable<MethodCallStat> {

    private Hashtable<String, MethodCallStat> data = new Hashtable<>();

    public MethodCallStat getMethodInfo(String methodName)
    {
        return data.get(methodName);
    }

    public void addMethodCallInfo(String methodName, int callId, long duration)
    {
        // Запрашиваем объект в хеш-таблице data
        MethodCallStat methodStat = data.get(methodName);

        // Если такого объекта в таблице нет, то создаем его и кладем в таблицу
        if (methodStat == null)
        {
            methodStat = new MethodCallStat(methodName);
            methodStat.setMinTimeCall(duration);
            methodStat.setMaxTimeCall(duration);
            methodStat.setMaxId(callId);
            methodStat.setCount(1);
            methodStat.setAvg(duration);
            data.put(methodName, methodStat);
        }
        else // Если такой объект есть, меняем поля класса
        {
            if (duration > methodStat.getMaxTimeCall())
            {
                methodStat.setMaxTimeCall(duration); // Запоминаем максимальное время пользования методом
                methodStat.setMaxId(callId); // устанавливаем ID нового максимального времени пользования
            }
            if (duration < methodStat.getMinTimeCall())
                methodStat.setMinTimeCall(duration); // Запоминаем минимальное время пользвания методом

            methodStat.setCount(methodStat.getCount() + 1);
            methodStat.setAvg(((methodStat.getCount() - 1)* methodStat.getAvg() + duration)/methodStat.getCount());
        }
    }

    /* Класс предоставляет "свой" итератор для обхода его коллекции.
    * В нашем случе коллекцию возвращает хеш-таблица (итератор соответственно её же) */
    @Override
    public Iterator iterator() {
        return data.values().iterator();
    }

}
