package org.sglnu.mediaservice.mapper;

import org.sglnu.mediaservice.dto.ErrorDetail;
import org.sglnu.mediaservice.exception.DetailedEntityNotFoundException;
import jakarta.validation.ConstraintViolation;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.beans.TypeMismatchException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@Mapper(componentModel = "spring")
public interface ErrorDetailMapper {

    @Mapping(target = "cause", expression = "java(ex.getPropertyPath().toString())")
    @Mapping(target = "message", expression = "java(ex.getMessage())")
    ErrorDetail from(ConstraintViolation<?> ex);

    @Mapping(target = "cause", expression = "java(ex.getName())")
    @Mapping(target = "message", expression = "java(\"Must be of type [%s]\".formatted(ex.getRequiredType().getSimpleName()))")
    ErrorDetail from(MethodArgumentTypeMismatchException ex);

    @Mapping(target = "cause", expression =  "java(ex.getParameterName())")
    @Mapping(target = "message", expression = "java(ex.getMessage())")
    ErrorDetail from(MissingServletRequestParameterException ex);

    @Mapping(target = "cause", expression = "java(\"id\")")
    @Mapping(target = "message", expression = "java(\"Entity with id=[%s] hasn't been found\".formatted(ex.getEntityId()))")
    ErrorDetail from(DetailedEntityNotFoundException ex);

    @Named("toErrorDetail")
    default ErrorDetail toErrorDetail(ObjectError objectError) {
        if (objectError.contains(ConstraintViolation.class)) {
            return from(objectError.unwrap(ConstraintViolation.class));
        }

        return new ErrorDetail(objectError.getObjectName(), objectError.getDefaultMessage());
    }

    @Named("toErrorDetail")
    default ErrorDetail toErrorDetail(TypeMismatchException typeMismatchException) {
        if (typeMismatchException.contains(MethodArgumentTypeMismatchException.class)) {
            return from((MethodArgumentTypeMismatchException) typeMismatchException);
        }

        return new ErrorDetail(typeMismatchException.getPropertyName(), typeMismatchException.getMessage());
    }
}
