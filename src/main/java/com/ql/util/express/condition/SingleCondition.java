package com.ql.util.express.condition;

/**
 * @author: penuel
 * @date: 2023/2/22 10:35
 * @desc: 单个条件
 */
public class SingleCondition implements ICondition{

    private String field;
    private Object value;
    private Class valueType;
    private Operator operator;

    @Override
    public Type getType() {
        return Type.Single;
    }

    @Override
    public Operator getOperator() {
        return operator;
    }

    @Override
    public void setOperator(Operator operator) {
        this.operator = operator;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public Class getValueType() {
        return valueType;
    }

    public void setValueType(Class valueType) {
        this.valueType = valueType;
    }
}
