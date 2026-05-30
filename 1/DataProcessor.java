import java.lang.annotation.*;

// Наша пользовательская аннотация @DataProcessor
// RUNTIME - её можно читать во время выполнения программы
// METHOD - применяется только к методам
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface DataProcessor {
    String description() default "Обработчик данных";
}
