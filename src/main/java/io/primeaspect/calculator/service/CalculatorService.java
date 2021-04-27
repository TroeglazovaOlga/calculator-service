package io.primeaspect.calculator.service;

import io.primeaspect.calculator.exception.BadRequestException;
import io.primeaspect.calculator.model.Number;
import io.primeaspect.calculator.service.enums.Delimiter;
import io.primeaspect.calculator.service.enums.Operation;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CalculatorService {
    public CalculatorService() {
    }

    public double calculate(String expression) throws BadRequestException {
        try {
            ExpressionProcessor processor = new ExpressionProcessor(expression);
            List<String> reversePolishNotation = processor.getReversePolishNotation();
            return calculateByReversePolishNotation(reversePolishNotation);
        } catch (Exception e) {
            throw new BadRequestException();
        }
    }

    private double calculateByReversePolishNotation(List<String> expression) {
        Stack<Double> stack = new Stack<>();

        while (!expression.isEmpty()) {
            String token = expression.remove(0);
            if (Operation.isOperation(token)) {
                Operation operation = Operation.getOperation(token);
                double first = stack.pop();
                double second = stack.pop();
                stack.add(operation.action(second, first));
            } else {
                stack.add(Double.parseDouble(token));
            }
        }

        return stack.pop();
    }

    public List<Number> getNumbers(String expression) {
        ExpressionProcessor processor = new ExpressionProcessor(expression);
        return processor.getNumbers();
    }
}

class ExpressionProcessor {
    private final String expression;

    ExpressionProcessor(String expression) {
        this.expression = expression;
    }

    List<String> getReversePolishNotation() {
        Stack<String> stack = new Stack<>();
        List<String> outputList = new LinkedList<>();
        String processedExpression = preProcessExpression(expression);
        StringTokenizer tokenizer = new StringTokenizer(processedExpression,
                Delimiter.getDelimiters() + Operation.getOperations(),
                true);

        while (tokenizer.hasMoreTokens()) {
            String token = tokenizer.nextToken();

            if (stack.isEmpty() && (Operation.isOperation(token) || Delimiter.isDelimiter(token))) {
                stack.add(token);
            } else if (Operation.isOperation(token)) {
                Operation curOperation = Operation.getOperation(token);
                String prevToken = stack.peek();
                int prevTokenPriority = Operation.isOperation(prevToken) ? Operation.getOperation(prevToken).getPriority() :
                        Delimiter.getDelimiter(prevToken).getPriority();

                if (curOperation.getPriority() <= prevTokenPriority) {
                    outputList.add(stack.pop());
                }
                stack.add(token);
            } else if (Delimiter.isDelimiter(token)) {
                Delimiter delimiter = Delimiter.getDelimiter(token);

                if (delimiter.equals(Delimiter.RIGHT)) {
                    while (!stack.peek().equals(Delimiter.LEFT.getValue())) {
                        outputList.add(stack.pop());
                    }
                    stack.pop();
                } else {
                    stack.add(token);
                }
            } else {
                outputList.add(token);
            }
        }

        while (!stack.isEmpty()) {
            outputList.add(stack.pop());
        }
        return outputList;
    }

    List<Number> getNumbers() {
        List<Number> numbers = new ArrayList<>();
        StringTokenizer tokenizer = new StringTokenizer(expression,
                Delimiter.getDelimiters() + Operation.getOperations());
        Map<Double, Integer> map = new HashMap<>();

        while (tokenizer.hasMoreTokens()) {
            double token = Double.parseDouble(tokenizer.nextToken());
            int count = map.getOrDefault(token, 0) + 1;
            map.put(token, count);
        }
        map.forEach((key, value) -> {
            numbers.add(new Number(key, value));
        });

        return numbers;
    }

    private String preProcessExpression(String expression) {
        String result = expression.replaceAll("\\s", "");
        if (result.startsWith("-")) {
            result = "0" + result;
        }
        return result.replace("(-", "(0-");
    }
}