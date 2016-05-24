package sinitsyn;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Hashtable;

/*
 Основной "рабочий" класс для анализа лог-файла
 */
public class CallLogAnalyzer {

    private Hashtable<Integer, CallLogRecord> hashtableID = new Hashtable<>(); // Приватное поле класса - хеш-таблица конкретных ID
    private ResultStatistic resultStatistic = new ResultStatistic(); // Приватное поле - класс с результатами статистики

    private Date currentCallTime; // Переменная-поле для хранения времени работы с методом
    private String currentMethodName; // Переменная-поле для хранения имени метода
    private int currentCallId; // Переменная для хранения текущего ID вызова

    public ResultStatistic getStatistic()
    {
        return resultStatistic;
    }

    public Hashtable<Integer, CallLogRecord> getInfoFromhashtableID() {return  hashtableID;}


    public void readLog(String fileName) throws IOException // Основной метод класса
    {
        try (FileReader fr = new FileReader(fileName))
        {
            BufferedReader readerPerLine = new BufferedReader(fr);
            String line = null;
            while ((line = readerPerLine.readLine())!=null)
                parseLineToHashTable(line); //Построчное чтение данных из лог файла и анализ с помощью хеш-таблицы
        }
        catch (IOException е)
        {
            throw new IOException("Файл не найден");
        }
    }

    private void parseLineToHashTable(String logLine)
    {
        //Для начала разбиваем строку из лога по пробелам на логические подстроки
        String[] setofsubstrArray = logLine.split("\\s");

        // Из формата даты-времени находим время вызова метода
        extractCallTime(setofsubstrArray[0]);
        if (currentCallTime == null)
            return;

        // Логический флаг, показывающий вход это в метод или выход
        boolean isEntry = (setofsubstrArray[3].equals("entry")) ? true : false;

        // Находим имя метода и его ID
        extractMethodInfo(setofsubstrArray[5]);

        /* Если был произведен вызов метода, добавляем его для анализа в хеш-таблицу,
         где ключом служит ID, значением - класс-сущность CallLogRecord*/
        if (isEntry)
        {
            hashtableID.put(currentCallId, new CallLogRecord(currentMethodName, currentCallTime));
        }
        else
        /* Если же был произведен выход из метода, заканчиваем анализ по этому методу, добавляем необходимые данные
           в класс-сущность ResultStatistic  и удаляем строку из хеш таблицы hashtableID */
        {
            CallLogRecord entryInfo = hashtableID.get(currentCallId); // Получаем объект CallLogRecord по ключу ID в первичной таблице

            //Если этот объект существует, то всю инфу о вызове метода добавляем в хеш таблицу класса ResultStatistic
            if (entryInfo != null)
            {
                resultStatistic.addMethodCallInfo(currentMethodName, currentCallId,
                        currentCallTime.getTime() - entryInfo.getCallTime().getTime());
                hashtableID.remove(currentCallId);
            }
        }
    }

    private void extractCallTime(String dateTimeString)
    {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss,SSS");
        try
        {
            currentCallTime = dateFormat.parse(dateTimeString);
        }
        catch (ParseException e)
        {
            currentCallTime = null;
        }
    }

    private void extractMethodInfo(String sourceStr)
    {
        String[] methodWithCallId = sourceStr.substring(1, sourceStr.length() - 1).split(":"); // "Убрали" скобки
        currentMethodName = methodWithCallId[0];// Имя метода - первая часть до двоеточния
        currentCallId = Integer.parseInt(methodWithCallId[1]); // ID - вторая часть после двоеточия
    }
}
