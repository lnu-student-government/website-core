package org.sglnu.userservice.common;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Faculty {
    BIOLOGICAL_SCIENCES("Біологічний факультет"),
    GEOGRAPHY("Географічний факультет"),
    GEOLOGY("Геологічний факультет"),
    ECONOMICS("Економічний факультет"),
    ELECTRONICS_AND_COMPUTER_TECHNOLOGIES("Факультет електроніки та компʼютерних технологій"),
    JOURNALISM("Факультет журналістики"),
    FOREIGN_LANGUAGES("Факультет іноземних мов"),
    HISTORY("Історичний факультет"),
    CULTURE_AND_ARTS("Факультет культури і мистецтв"),
    MECHANICS_AND_MATHEMATICS("Механіко-математичний факультет"),
    INTERNATIONAL_RELATIONS("Факультет міжнародних відносин"),
    TEACHER_EDUCATION("Факультет педагогічної освіти"),
    APPLIED_MATHEMATICS_AND_COMPUTER_SCIENCE("Факультет прикладної математики та інформатики"),
    FINANCE_AND_BUSINESS_MANAGEMENT("Факультет управління фінансами та бізнесу"),
    PHYSICS("Фізичний факультет"),
    PHILOLOGY("Філологічний факультет"),
    PHILOSOPHY("Філософський факультет"),
    CHEMISTRY("Хімічний факультет"),
    LAW("Юридичний факультет"),
    TEACHER_TRAINING_COLLEGE("Педагогічний фаховий коледж");

    private final String name;

}

