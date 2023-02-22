package com.ql.util.express.condition;

import java.util.List;

/**
 * @author: penuel
 * @date: 2023/2/22 10:33
 * @desc: 组合条件
 */
public class CompositeCondition implements ICondition{

    private List<ICondition> conditions;
    private ICondition.Operator operator;

    @Override
    public Operator getOperator() {
        return operator;
    }

    @Override
    public void setOperator(Operator operator) {
        this.operator = operator;
    }

    @Override
    public Type getType() {
        return Type.Composite;
    }

    public List<ICondition> getConditions() {
        return conditions;
    }

    public void setConditions(List<ICondition> conditions) {
        this.conditions = conditions;
    }


}
