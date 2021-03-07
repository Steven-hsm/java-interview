package com.hsm.stack;

import lombok.Getter;

/**
 * 符号优先级
 */
@Getter
public enum SymbolPriorityEnum {
    NON("empty",4),
    LEFT("(",3),
    ADD("+",1),
    SUB("-",1),
    MUL("*",2),
    DIV("/",2)
    ;
    private String symbol;
    private Integer priority;

    SymbolPriorityEnum(String symbol, Integer priority) {
        this.symbol = symbol;
        this.priority = priority;
    }

    /**
     * 根据符号获取优先级
     * @param symbol
     * @return
     */
    public static Integer getPriorityFromSymbol(String symbol){
        SymbolPriorityEnum[] values = SymbolPriorityEnum.values();
        for (SymbolPriorityEnum value : values) {
            if(value.symbol.equals(symbol)){
                return value.priority;
            }
        }
        return -1;
    }
}
