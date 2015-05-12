package pl.edu.agh.student.mapper;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractMapper<T, TDto> {

    protected abstract T fromDtoIfNotNull(TDto dto);

    protected abstract TDto toDtoIfNotNull(T t);

    public T fromDto(TDto dto) {
        if (dto == null) {
            return null;
        }
        return fromDtoIfNotNull(dto);
    }

    public TDto toDto(T t) {
        if (t == null) {
            return null;
        }
        return toDtoIfNotNull(t);
    }

    public List<T> fromDto(List<TDto> dtos) {
        if (dtos == null) {
            return null;
        }

        List<T> dos = new ArrayList<>(dtos.size());
        for (TDto dto : dtos) {
            dos.add(fromDto(dto));
        }
        return dos;
    }

    public List<TDto> toDto(List<T> dos) {
        if (dos == null) {
            return null;
        }

        List<TDto> dtos = new ArrayList<>(dos.size());
        for (T t : dos) {
            dtos.add(toDto(t));
        }
        return dtos;
    }
}
