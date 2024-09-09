package org.pprateek.deliveroo.models;

import org.pprateek.deliveroo.exceptions.InvalidCronExpressionException;
import org.pprateek.deliveroo.utils.IntegerUtils;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public abstract class CronField {
    protected CronFieldType type;
    protected String incomingText;
    protected Set<String> values;
    protected Map<String, String> numericToExtensionMapping; // mapping of numeric value in cron field to extension value
    protected Map<String, String> extensionToNumericMapping; // mapping of extension value in cron field to its numeric value

    public CronField(CronFieldType type, String incomingText) {
        this.type = type;
        this.incomingText = incomingText;
        this.values = new LinkedHashSet<>();
        this.parseIncomingText();
    }

    public CronField(CronFieldType type, String incomingText, Map<String, String> numericToExtensionMapping, Map<String, String> extensionToNumericMapping) {
        this.type = type;
        this.incomingText = incomingText;
        this.values = new LinkedHashSet<>();
        this.numericToExtensionMapping = numericToExtensionMapping;
        this.extensionToNumericMapping = extensionToNumericMapping;
    }

    public void parseIncomingText() {
        // Can contain * or / or - or ,
        if(this.incomingText.contains(",")) {
            this.parseCommaValues();
        }
        else if(this.incomingText.contains("/")) {
            this.parseStepValues();
        }
        else if(this.incomingText.contains("*")) {
            this.parseStarValues();
        }
        else if(this.incomingText.contains("-")) {
            this.parseRangeValues();
        }
        else {
            // Single value
            this.parseFixedValues();
        }
    }

    public void parseCommaValues() {
        String tokens[] = this.incomingText.split(",");

        if(tokens.length == 1)
            throw new InvalidCronExpressionException(String.format("%s comma separated values are expected", this.type.getCronFieldSign()));

        for(String token: tokens) {
            if(IntegerUtils.checkIfStringIsNumeric(token)) {
                Integer value = Integer.parseInt(token);

                if(value < this.type.getMin() || value > this.type.getMax())
                    throw new InvalidCronExpressionException(
                            String.format("%s must be between %d and %d", type.getCronFieldSign(), type.getMin(), type.getMax())
                    );

                this.values.add(token);
            }
            else if(this.extensionToNumericMapping != null) {
                if(this.extensionToNumericMapping.containsKey(token))
                    this.values.add(token);
                else
                    throw new InvalidCronExpressionException(String.format("%s comma separated values extensions should be valid extension values out of %s", this.extensionToNumericMapping.keySet().toString()));
            }
            else if(this.extensionToNumericMapping == null)
                throw new InvalidCronExpressionException(String.format("%s comma separated values should be numeric.", this.type.getCronFieldSign()));
        }
    }
    public void parseStepValues() {
        String tokens[] = this.incomingText.split("/");

        if(tokens.length != 2)
            throw new InvalidCronExpressionException(String.format("%s expected start and a step value", type.getCronFieldSign()));

        Integer start, step;
        boolean storeExtensions = false;
        if(IntegerUtils.checkIfStringIsNumeric(tokens[0])) {
            start = Integer.parseInt(tokens[0]);

            if(start < type.getMin() || start > type.getMax())
                throw new InvalidCronExpressionException(
                        String.format("%s start value must be between %d and %d", type.getCronFieldSign(), type.getMin(), type.getMax())
                );
        }
        else if(tokens[0].equals("*"))
            start = type.getMin();
        else if(this.extensionToNumericMapping != null) {
            if(this.extensionToNumericMapping.containsKey(tokens[0])) {
                start = Integer.parseInt(this.extensionToNumericMapping.get(tokens[0]));
                storeExtensions = true;
            }
            else
                throw new InvalidCronExpressionException(String.format("%s start must be a valid extension value in step values out of %s", type.getCronFieldSign(), this.extensionToNumericMapping.keySet().toString()));
        }
        else
            throw new InvalidCronExpressionException(String.format("%s start must be valid in step values.", type.getCronFieldSign()));

        if(!IntegerUtils.checkIfStringIsNumeric(tokens[1]))
            throw new InvalidCronExpressionException(String.format("%s step value must be an integer in step values.", type.getCronFieldSign()));

        step = Integer.parseInt(tokens[1]);

        for(int i = start; i<=type.getMax(); i += step) {
            if(storeExtensions)
                this.values.add(this.numericToExtensionMapping.get(String.valueOf(i)));
            else
                this.values.add(String.valueOf(i));
        }
    }
    public void parseStarValues() {
        if(this.incomingText.length() > 2)
            throw new InvalidCronExpressionException(String.format("%s single star value is allowed", type.getCronFieldSign()));

        for(int i = type.getMin();i<= type.getMax(); i++)
            this.values.add(String.valueOf(i));
    }

    public void parseRangeValues() {
        String tokens[] = this.incomingText.split("-");

        if(tokens.length > 2)
            throw new InvalidCronExpressionException(String.format("%s range values must only contain start and end ranges", type.getCronFieldSign()));

        Integer start, end;
        boolean storeExtensions = false;
        if(IntegerUtils.checkIfStringIsNumeric(tokens[0]) && IntegerUtils.checkIfStringIsNumeric(tokens[1])) {
            start = Integer.parseInt(tokens[0]);
            end = Integer.parseInt(tokens[1]);
        }
        else if(this.extensionToNumericMapping != null){
            if(this.extensionToNumericMapping.containsKey(tokens[0]) && this.extensionToNumericMapping.containsKey(tokens[1])) {
                start = Integer.parseInt(this.extensionToNumericMapping.get(tokens[0]));
                end = Integer.parseInt(this.extensionToNumericMapping.get(tokens[1]));
                storeExtensions = true;
            }
            else throw new InvalidCronExpressionException(String.format("%s range values must contain valid extensions from %s", type.getCronFieldSign(), this.extensionToNumericMapping.keySet().toString()));
        }
        else throw new InvalidCronExpressionException(String.format("%s range values must be numeric", type.getCronFieldSign()));

        if(start > end)
            throw new InvalidCronExpressionException(String.format("%s start must be less than or equal to end in range", type.getCronFieldSign()));

        if(start < type.getMin() || start > type.getMax() || end < type.getMin() || end > type.getMax())
            throw new InvalidCronExpressionException(
                    String.format("%s start and end ranges must be within %d and %d",
                            type.getCronFieldSign(), type.getMin(), type.getMax())
            );

        for(int i = start; i<= end; i++) {
            if(storeExtensions)
                this.values.add(this.numericToExtensionMapping.get(String.valueOf(i)));
            else this.values.add(String.valueOf(i));
        }
    }
    public void parseFixedValues() {
        Integer value;
        boolean storeExtension = false;
        if(IntegerUtils.checkIfStringIsNumeric(this.incomingText)) {
            value = Integer.parseInt(this.incomingText);
        }
        else if(this.extensionToNumericMapping != null) {
            if(this.extensionToNumericMapping.containsKey(this.incomingText)) {
                value = Integer.parseInt(this.extensionToNumericMapping.get(this.incomingText));
                storeExtension = true;
            }
            else throw new InvalidCronExpressionException(String.format("%s must be a valid extension value from %s", type.getCronFieldSign(), this.extensionToNumericMapping.keySet().toString()));
        }
        else throw new InvalidCronExpressionException(String.format("%s must be a numeric value.", type.getCronFieldSign()));


        if(value < type.getMin() || value > type.getMax())
            throw new InvalidCronExpressionException(
                    String.format("%s must be between %d and %d", type.getCronFieldSign(), type.getMin(), type.getMax())
            );

        if(storeExtension)
            this.values.add(this.numericToExtensionMapping.get(String.valueOf(value)));
        else
            this.values.add(String.valueOf(value));
    }

    public String toString() {
        return values.stream().map(Object::toString).collect(Collectors.joining(" "));
    }
}
