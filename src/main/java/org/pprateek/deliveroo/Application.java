package org.pprateek.deliveroo;

import org.pprateek.deliveroo.exceptions.InvalidCronExpressionException;
import org.pprateek.deliveroo.models.CronExpression;
import java.util.Arrays;

public class Application {
    public static void main(String args[]) throws Exception {
        if (args.length != 1) {
            System.err.println("Expected [minute] [hour] [day-of-month] [month] [day-of-week] [command] but got :" + Arrays.toString(args));
            return;
        }

        try {
            CronExpression expr = new CronExpression(args[0]);
            System.out.println(expr);

        } catch (InvalidCronExpressionException invalidCronExpression) {
            System.err.println(invalidCronExpression.getMessage());
        }
    }
}
