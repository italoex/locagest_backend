package progweb.locagest.util;

import jakarta.validation.constraints.*;
import java.lang.reflect.Field;
import java.util.*;

/**
 * Validador genérico usando reflexão.
 * Inspeciona os campos de uma classe e valida com base nas anotações de
 * validação.
 */
public class ReflectionValidator {

    /**
     * Valida um objeto analisando seus campos através de reflexão.
     * Retorna uma lista de mensagens de erro (vazia se válido).
     */
    public static List<String> validate(Object obj) {
        List<String> errors = new ArrayList<>();

        if (obj == null) {
            errors.add("Objeto não pode ser nulo");
            return errors;
        }

        Class<?> clazz = obj.getClass();
        Field[] fields = clazz.getDeclaredFields();

        for (Field field : fields) {
            // Permitir acesso a campos privados
            field.setAccessible(true);

            try {
                Object value = field.get(obj);
                String fieldName = field.getName();

                // Validar @NotNull
                if (field.isAnnotationPresent(NotNull.class) && value == null) {
                    errors.add(String.format("%s não pode ser nulo", fieldName));
                }

                // Validar @NotBlank (para String)
                if (field.isAnnotationPresent(NotBlank.class)) {
                    if (value == null || (value instanceof String && ((String) value).isBlank())) {
                        errors.add(String.format("%s não pode estar em branco", fieldName));
                    }
                }

                // Validar @NotEmpty (para Collection)
                if (field.isAnnotationPresent(NotEmpty.class)) {
                    if (value == null || (value instanceof Collection && ((Collection<?>) value).isEmpty())) {
                        errors.add(String.format("%s não pode estar vazio", fieldName));
                    }
                }

                // Validar @Min
                if (field.isAnnotationPresent(Min.class) && value instanceof Number) {
                    Min min = field.getAnnotation(Min.class);
                    long minValue = min.value();
                    long numValue = ((Number) value).longValue();
                    if (numValue < minValue) {
                        errors.add(String.format("%s deve ser >= %d", fieldName, minValue));
                    }
                }

                // Validar @Max
                if (field.isAnnotationPresent(Max.class) && value instanceof Number) {
                    Max max = field.getAnnotation(Max.class);
                    long maxValue = max.value();
                    long numValue = ((Number) value).longValue();
                    if (numValue > maxValue) {
                        errors.add(String.format("%s deve ser <= %d", fieldName, maxValue));
                    }
                }

                // Validar @Pattern
                if (field.isAnnotationPresent(Pattern.class) && value instanceof String) {
                    Pattern pattern = field.getAnnotation(Pattern.class);
                    String regex = pattern.regexp();
                    if (!((String) value).matches(regex)) {
                        errors.add(String.format("%s não atende ao padrão: %s", fieldName, regex));
                    }
                }

                // Validar @Size
                if (field.isAnnotationPresent(Size.class)) {
                    Size size = field.getAnnotation(Size.class);
                    int min = size.min();
                    int max = size.max();

                    if (value instanceof String) {
                        int len = ((String) value).length();
                        if (len < min || len > max) {
                            errors.add(String.format("%s deve ter entre %d e %d caracteres", fieldName, min, max));
                        }
                    } else if (value instanceof Collection) {
                        int len = ((Collection<?>) value).size();
                        if (len < min || len > max) {
                            errors.add(String.format("%s deve ter entre %d e %d elementos", fieldName, min, max));
                        }
                    }
                }

                // Validar @Email
                if (field.isAnnotationPresent(Email.class) && value instanceof String) {
                    String email = (String) value;
                    if (!email.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
                        errors.add(String.format("%s deve ser um email válido", fieldName));
                    }
                }

                // Validar @Positive
                if (field.isAnnotationPresent(Positive.class) && value instanceof Number) {
                    double numValue = ((Number) value).doubleValue();
                    if (numValue <= 0) {
                        errors.add(String.format("%s deve ser positivo", fieldName));
                    }
                }

                // Validar @PositiveOrZero
                if (field.isAnnotationPresent(PositiveOrZero.class) && value instanceof Number) {
                    double numValue = ((Number) value).doubleValue();
                    if (numValue < 0) {
                        errors.add(String.format("%s deve ser >= 0", fieldName));
                    }
                }

            } catch (IllegalAccessException e) {
                errors.add(String.format("Erro ao validar %s: %s", field.getName(), e.getMessage()));
            }
        }

        return errors;
    }

    /**
     * Retorna informações sobre todos os campos de uma classe usando reflexão.
     * Útil para debug e logging.
     */
    public static String getClassInfo(Object obj) {
        StringBuilder info = new StringBuilder();
        info.append("Classe: ").append(obj.getClass().getName()).append("\n");
        info.append("Campos:\n");

        Field[] fields = obj.getClass().getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            try {
                Object value = field.get(obj);
                info.append("  - ").append(field.getName())
                        .append(" (").append(field.getType().getSimpleName()).append(")")
                        .append(" = ").append(value).append("\n");
            } catch (IllegalAccessException e) {
                info.append("  - ").append(field.getName()).append(" [erro ao acessar]\n");
            }
        }

        return info.toString();
    }
}
