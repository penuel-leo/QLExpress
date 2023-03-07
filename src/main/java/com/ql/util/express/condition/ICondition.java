package com.ql.util.express.condition;

import java.util.Arrays;
import java.util.List;

/**
 * @author: penuel
 * @date: 2023/2/22 10:32
 * @desc: 条件组合
 */
public interface ICondition {

    Operator getOperator();

    void setOperator(Operator operator);

    Type getType();

    enum Type{
        Single,
        Composite;
    }

    enum Operator{

        IGNORE(),
        EQUAL("==","<>"),
        NOT_EQUAL("!="),
        GREATER_THAN(">"),
        GREATER_OR_EQUAL_THAN(">="),
        LESS_THAN("<"),
        LESS_OR_EQUAL_THAN("<="),
        AND(true,"&&"),
        OR(true,"||"),
        ;
        List<String> name;
        boolean logical;
        Operator(String... name){
            this.name = Arrays.asList(name);
        }
        Operator(boolean isLogical, String... name){
            this.logical = isLogical;
            this.name = Arrays.asList(name);
        }

        public boolean isLogical() {
            return logical;
        }

        public static Operator fromName(String name){
            for (Operator operator : Operator.values()) {
                if(operator.name.contains(name)){
                    return operator;
                }
            }
            return IGNORE;
        }
    }



}
