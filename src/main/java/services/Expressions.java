package services;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Expressions {
    SUM("+");
    private String symbol;
}
