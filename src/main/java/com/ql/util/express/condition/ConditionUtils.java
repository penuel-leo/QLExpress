package com.ql.util.express.condition;

import com.ql.util.express.InstructionSet;
import com.ql.util.express.InstructionSetContext;
import com.ql.util.express.instruction.detail.Instruction;
import com.ql.util.express.instruction.detail.InstructionConstData;
import com.ql.util.express.instruction.detail.InstructionLoadAttr;
import com.ql.util.express.instruction.detail.InstructionOperator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @author: penuel
 * @date: 2023/2/22 11:03
 * @desc: 解析条件，目前只支持两层判断
 */
public class ConditionUtils {

    /*
    A类违规天数90天内 == false && (虚假交易扣分 < 48 || 假冒扣分 < 12 || 待整改卖家 == false) && 宝贝相符DSR > 4.6
     */
    public static ICondition parse(InstructionSet instructionSet, InstructionSetContext instructionSetContext) throws Exception {
        AtomicReference<ICondition> conditionRef = new AtomicReference<>();

        List<ICondition> conditions = new ArrayList<>();

        for (Instruction instruction : instructionSet.getInstructionList()) {
            ICondition.Operator operator = next(conditionRef, instruction, instructionSetContext);
            if (null != operator) {
                if (operator.isLogical()) {
                    CompositeCondition compositeCondition;
                    ICondition conditionLast = conditions.remove(conditions.size() - 1);
                    ICondition conditionFirst = conditions.remove(conditions.size() - 1);
                    if (conditionFirst.getType() == ICondition.Type.Composite && conditionLast.getType() == ICondition.Type.Single) {
                        ((CompositeCondition) conditionFirst).getConditions().add(conditionLast);
                        compositeCondition = (CompositeCondition) conditionFirst;
                    } else {
                        compositeCondition = new CompositeCondition();
                        List<ICondition> tempConditions = new ArrayList<>();
                        tempConditions.add(conditionFirst);
                        tempConditions.add(conditionLast);
                        compositeCondition.setConditions(tempConditions);
                        compositeCondition.setOperator(operator);
                    }
                    conditions.add(compositeCondition);
                } else {
                    conditionRef.get().setOperator(operator);
                    conditions.add(conditionRef.get());
                }

            }
        }
        return conditions.get(0);
    }

    /*
    A类违规天数90天内 == false && (虚假交易扣分 < 48 || 假冒扣分 < 12 || 待整改卖家 == false) && 宝贝相符DSR > 4.6
     */
    public static ICondition.Operator next(AtomicReference<ICondition> conditionRef, Instruction instruction, InstructionSetContext instructionSetContext) throws Exception {
        if (instruction instanceof InstructionOperator) {
            ICondition.Operator operator = ICondition.Operator.fromName(((InstructionOperator) instruction).getOperator().getName());
            return operator;
        }

        if (instruction instanceof InstructionLoadAttr) {
            conditionRef.set(new SingleCondition());
            ((SingleCondition) conditionRef.get()).setField(((InstructionLoadAttr) instruction).getAttrName());
        }

        if (instruction instanceof InstructionConstData) {
            ((SingleCondition) conditionRef.get()).setValue(((InstructionConstData) instruction).getOperateData().getObject(instructionSetContext));
            ((SingleCondition) conditionRef.get()).setValueType(((InstructionConstData) instruction).getOperateData().getOriginalType());
        }
        return null;
    }


}
