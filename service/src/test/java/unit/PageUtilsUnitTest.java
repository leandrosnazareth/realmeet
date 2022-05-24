package unit;

import static br.com.sw2you.realmeet.domain.entity.Allocation.SORTABLE_FIELDS;
import static br.com.sw2you.realmeet.util.PageUtils.newPageable;
import static java.util.Collections.emptyList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import br.com.sw2you.realmeet.core.BaseUnitTest;
import br.com.sw2you.realmeet.exception.InvalidOrderByFieldException;
import org.springframework.data.domain.Sort;
import org.junit.jupiter.api.Test;

public class PageUtilsUnitTest extends BaseUnitTest {

    @Test
    void testNewPageableWhenPageIsNullAndLimitIsNullAndOrderByIsNull() {
        var pageable = newPageable(null, null, 10, null, SORTABLE_FIELDS);
        assertEquals(0, pageable.getPageNumber());
        assertEquals(10, pageable.getPageSize());
        assertEquals(Sort.unsorted(), pageable.getSort());
    }

    @Test
    void testNewPageableWhenPageIsNegative() {
        assertThrows(IllegalArgumentException.class, () -> newPageable(-1, null, 10, null, SORTABLE_FIELDS));
    }

    @Test
    void testNewPageableWhenLimitIsInvalid() {
        assertThrows(IllegalArgumentException.class, () -> newPageable(null, 0, 10, null, SORTABLE_FIELDS));
    }

    @Test
    void testNewPageableWhenLimitExceedsMaximum() {
        var pageable = newPageable(null, 30, 10, null, SORTABLE_FIELDS);
        assertEquals(10, pageable.getPageSize());
    }

    @Test
    void testNewPageableWhenOrderByIsNull() {
        assertThrows(IllegalArgumentException.class, () -> newPageable(null, 0, 10, null, null));
    }

    @Test
    void testNewPageableWhenOrderByIsEmpty() {
        assertThrows(IllegalArgumentException.class, () -> newPageable(null, 0, 10, null, emptyList()));
    }

    @Test
    void testNewPageableWhenOrderByAscIsValid() {
        var pageable = newPageable(null, null, 10, SORTABLE_FIELDS.get(0), SORTABLE_FIELDS);
        assertEquals(Sort.by(Sort.Order.asc(SORTABLE_FIELDS.get(0))), pageable.getSort());
    }

    @Test
    void testNewPageableWhenOrderByDescIsValid() {
        var pageable = newPageable(null, null, 10, "-" + SORTABLE_FIELDS.get(0), SORTABLE_FIELDS);
        assertEquals(Sort.by(Sort.Order.desc(SORTABLE_FIELDS.get(0))), pageable.getSort());
    }

    @Test
    void testNewPageableWhenOrderByFieldIsInvalid() {
        assertThrows(InvalidOrderByFieldException.class, () -> newPageable(null, null, 10, "invalid", SORTABLE_FIELDS));
    }
}
