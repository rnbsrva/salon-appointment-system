package com.akerke.salonservice.common.constants;

import org.springframework.context.annotation.DependsOn;

public enum WeekDay {
    @Deprecated
    EMPTY, // to shift ordinal value for each week day
    MONDAY,
    TUESDAY,
    WEDNESDAY,
    THURSDAY,
    FRIDAY,
    SATURDAY,
    SUNDAY
}
