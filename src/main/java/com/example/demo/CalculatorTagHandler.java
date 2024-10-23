package com.example.demo;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;

public class CalculatorTagHandler extends TagSupport {
    private double num1;
    private double num2;
    private String operator;

    public void setNum1(double num1) {
        this.num1 = num1;
    }

    public void setNum2(double num2) {
        this.num2 = num2;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    @Override
    public int doStartTag() throws JspException {
        return EVAL_BODY_INCLUDE;
    }

    public int doEndTag() throws JspException {
        double result = 0;

        switch (operator) {
            case "+":
                result = num1 + num2;
                break;
            case "-":
                result = num1 - num2;
                break;
            case "*":
                result = num1 * num2;
                break;
            case "/":
                if (num2 == 0) {
                    try {
                        pageContext.getOut().println("<br>   ");
                        pageContext.getOut().println("<br>Cannot divide by zero!");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    return SKIP_PAGE;
                }
                else {
                    result = num1 / num2;
                }
                break;
            default:
                try {
                    pageContext.getOut().println("<br>   ");
                    pageContext.getOut().println("<br>Invalid operator!");
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return SKIP_PAGE; // 终止页面处理
        }
        try {
            pageContext.getOut().println("<br>   ");
            pageContext.getOut().println("<br>"+num1 + " " + operator+ " " + num2 + " = " + result);
        }
        catch (IOException e) {
            throw new JspException(e);
        }

        return EVAL_PAGE;
    }
}