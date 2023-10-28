package com.akerke.authservice.reflection;

import com.akerke.authservice.common.reflection.MapUtils;
import com.akerke.authservice.common.utils.Pair;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class MapUtilsTest {

    private static class TestObject {
        private final String field1 = "value1";
        private final Integer field2 = 2;
    }

    @Test
    public void toMap_whenObjectAndNoPairs_thenReturnMapWithObjectFields() {
        var testObject = new TestObject();

        var result = MapUtils.toMap(testObject);

        assertThat(result).hasSize(2);
        assertThat(result).containsEntry("field1", "value1");
        assertThat(result).containsEntry("field2", 2);
    }

    @Test
    public void toMap_whenObjectAndOnePair_thenReturnMapWithObjectFieldsAndPair() {
        var testObject = new TestObject();
        var pair = new Pair("key", "value");

        var result = MapUtils.toMap(testObject, pair);

        assertThat(result).hasSize(3);
        assertThat(result).containsEntry("field1", "value1");
        assertThat(result).containsEntry("field2", 2);
        assertThat(result).containsEntry("key", "value");
    }

    @Test
    public void toMap_whenObjectAndMultiplePairs_thenReturnMapWithObjectFieldsAndPairs() {
        var testObject = new TestObject();
        var pair1 = new Pair("key1", "value1");
        var pair2 = new Pair("key2", "value2");

        var result = MapUtils.toMap(testObject, pair1, pair2);

        assertThat(result).hasSize(4);
        assertThat(result).containsEntry("field1", "value1");
        assertThat(result).containsEntry("field2", 2);
        assertThat(result).containsEntry("key1", "value1");
        assertThat(result).containsEntry("key2", "value2");
    }
}