package com.javarush.task.task22.task2212;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;
public class Solution
{
    public static void main(String[] args)
    {
        if (args.length != 2)
        {
            System.err.println("usage: java RegexDemo regex input");
            return;
        }
        // Преобразуем символьные последовательности начала новой строки (\n) в символы начала строки.
        args[1] = args[1].replaceAll("\\\\n", "\n");
        try
        {
            System.out.println("regex = " + args[0]);
            System.out.println("input = " + args[1]);
            Pattern p = Pattern.compile(args[0], Pattern.MULTILINE);
            Matcher m = p.matcher(args[1]);
            while (m.find())
                System.out.println("Found [" + m.group() + "] starting at "
                        + m.start() + " and ending at " + (m.end() - 1));
        }
        catch (PatternSyntaxException pse)
        {
            System.err.println("Неправильное регулярное выражение: " + pse.getMessage());
            System.err.println("Описание: " + pse.getDescription());
            System.err.println("Позиция: " + pse.getIndex());
            System.err.println("Неправильный шаблон: " + pse.getPattern());
        }
    }
}