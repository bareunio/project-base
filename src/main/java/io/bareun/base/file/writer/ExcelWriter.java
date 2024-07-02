package io.bareun.base.file.writer;

import io.bareun.base.file.annotation.ExcelHeader;
import io.bareun.base.file.util.ExcelFileUtils;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Workbook;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static io.bareun.base.common.util.ObjectMapperUtils.convert;
import static java.util.Collections.addAll;
import static java.util.Comparator.comparingInt;
import static java.util.Objects.nonNull;
import static java.util.stream.Collectors.toCollection;
import static java.util.stream.Collectors.toList;

/**
 * ExcelWriter 인터페이스는 Excel 파일 작성을 위한 기능을 정의합니다.
 * <p>
 * 구현체는 Excel 파일의 헤더 스타일, 헤더 이름 및 값을 작성하기 위한 메서드를 포함합니다.
 * </p>
 *
 * @param <T> Excel 파일에 작성할 객체의 타입
 */
public interface ExcelWriter<T> {

    /**
     * Excel 파일에 작성할 데이터 리스트를 반환합니다.
     *
     * @return Excel 파일에 작성할 데이터 리스트
     */
    List<?> getList();

    /**
     * Excel 파일에 작성할 객체의 클래스 타입을 반환합니다.
     *
     * @return Excel 파일에 작성할 객체의 클래스 타입
     */
    Class<T> getType();

    /**
     * 지정된 객체의 특정 인덱스에 해당하는 필드 값을 반환합니다.
     *
     * @param column 객체
     * @param index  필드 인덱스
     * @return 필드 값
     */
    default Object getFieldValue(T column, int index) {
        Field field = getExcelFields().get(index);
        field.setAccessible(true);

        try {
            return field.get(column);
        } catch (IllegalAccessException e) {
            throw new IllegalStateException("Cannot access field " + field.getName(), e);
        }
    }

    /**
     * Excel 파일에 작성할 객체들의 리스트를 반환합니다.
     *
     * @return Excel 파일에 작성할 객체들의 리스트
     */
    default List<T> getExcelColumns() {
        return getList().stream()
                .map(v -> convert(v, getType()))
                .collect(toList());
    }

    /**
     * Excel 헤더의 스타일을 반환합니다.
     *
     * @param workbook Excel 워크북
     * @return 헤더의 스타일
     */
    default CellStyle getHeaderStyle(Workbook workbook) {
        return ExcelFileUtils.getDefaultHeaderStyle(workbook);
    }

    /**
     * 주어진 인덱스에 해당하는 헤더의 이름을 반환합니다.
     *
     * @param index 헤더 인덱스
     * @return 헤더의 이름
     */
    default String getHeaderName(int index) {
        return getHeaderNames().get(index);
    }

    /**
     * 헤더의 총 개수를 반환합니다.
     *
     * @return 헤더의 총 개수
     */
    default int getHeaderSize() {
        return getHeaderNames().size();
    }

    /**
     * Excel 파일의 헤더 이름들을 포함하는 연결 리스트를 반환합니다.
     *
     * @return Excel 파일의 헤더 이름들을 포함하는 연결 리스트
     */
    default LinkedList<String> getHeaderNames() {
        return getExcelFields().stream()
                .map(this::getName)
                .collect(toCollection(LinkedList::new));
    }

    /**
     * Excel 파일에 작성할 필드들을 포함하는 연결 리스트를 반환합니다.
     *
     * @return Excel 파일에 작성할 필드들을 포함하는 연결 리스트
     */
    default LinkedList<Field> getExcelFields() {
		return getFields().stream()
                .filter(this::hasAnnotation)
                .sorted(comparingInt(this::getOrder))
                .collect(toCollection(LinkedList::new));
	}

    /**
     * Excel 파일에 작성할 필드들을 반환합니다.
     *
     * @return Excel 파일에 작성할 필드들
     */
    default List<Field> getFields() {
        List<Field> fields = new ArrayList<>();

        if (nonNull(getType().getSuperclass())) {
            addAll(fields, getType().getSuperclass().getDeclaredFields());
        }

        addAll(fields, getType().getDeclaredFields());

        return fields;
    }

    /**
     * 주어진 필드가 Excel 헤더 어노테이션을 가지고 있는지 여부를 반환합니다.
     *
     * @param field 필드
     * @return Excel 헤더 어노테이션을 가지고 있는지 여부
     */
    default boolean hasAnnotation(Field field) {
        return field.isAnnotationPresent(ExcelHeader.class);
    }

    /**
     * 주어진 필드의 정렬 순서를 반환합니다.
     *
     * @param field 필드
     * @return 정렬 순서
     */
    default int getOrder(Field field) {
        return field.getAnnotation(ExcelHeader.class).order();
    }

    /**
     * 주어진 필드의 이름을 반환합니다.
     *
     * @param field 필드
     * @return 필드의 이름
     */
    default String getName(Field field) {
        return field.getAnnotation(ExcelHeader.class).value();
    }
}
