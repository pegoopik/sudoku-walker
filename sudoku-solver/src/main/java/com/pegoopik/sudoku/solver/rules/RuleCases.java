package com.pegoopik.sudoku.solver.rules;

import lombok.Getter;

import java.util.Arrays;
import java.util.OptionalInt;

@Getter
public enum RuleCases {

    REMOVE_AVAILABLE_VALUES(1, Rules::removeAvailableValues),
    AVAILABLE_COUNT_EQUALS_ONE(2, Rules::availableCountEqualsOne),
    ONE_CELL_CANDIDATE_IN_GROUP(3, Rules::setValueIfOneCandidateInGroup),
    ENIGMA(4, Rules::enigmaRule),
    ;

    public static final int MAX_PRIORITY =
            Arrays.stream(RuleCases.values()).mapToInt(RuleCases::getPriority).max().getAsInt();

    private final int priority;
    private final RuleFunction function;

    RuleCases(int priority, RuleFunction function) {
        this.priority = priority;
        this.function = function;
    }
}
