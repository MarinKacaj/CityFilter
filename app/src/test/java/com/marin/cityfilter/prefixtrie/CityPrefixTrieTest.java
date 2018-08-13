package com.marin.cityfilter.prefixtrie;

import com.marin.cityfilter.model.City;
import com.marin.cityfilter.model.GeoCoordinates;

import org.junit.Before;
import org.junit.Test;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * @author Marin Kacaj
 */
public class CityPrefixTrieTest {

    private static final String TIRANA_DUMMY_NAME = "Abc";
    private CityPrefixTrie trie;
    private City tirana;
    private City rome;

    @Before
    public void setUp() throws Exception {
        trie = new CityPrefixTrie();
        GeoCoordinates tiranaGeoC = new GeoCoordinates(41.3275, 19.8187);
        tirana = new City(1, "AL", TIRANA_DUMMY_NAME, tiranaGeoC);
        GeoCoordinates romeGeoC = new GeoCoordinates(41.9028, 12.4964);
        rome = new City(2, "IT", "Abd", romeGeoC);
    }

    @Test
    public void size() {
        trie.put(tirana.getName().toLowerCase(), tirana);
        trie.put(rome.getName().toLowerCase(), rome);
        assertEquals(2, trie.size());
    }

    @Test
    public void isEmpty() {
        assertTrue(trie.isEmpty());
    }

    @Test
    public void entrySet() {
        trie.put(tirana.getName().toLowerCase(), tirana);
        trie.put(rome.getName().toLowerCase(), rome);
        Set<Map.Entry<String, City>> entrySet = trie.entrySet();
        assertEquals(2, entrySet.size());
    }

    @Test
    public void get() {
        trie.put(tirana.getName().toLowerCase(), tirana);
        City maybeTirana = trie.get(TIRANA_DUMMY_NAME.toLowerCase());
        assertNotNull(maybeTirana);
        assertEquals(tirana.getName(), maybeTirana.getName());
        assertEquals(tirana.getFullName(), maybeTirana.getFullName());
        assertEquals(tirana.getCountry(), maybeTirana.getCountry());
        final double MAX_DELTA = 0.001;
        assertEquals(tirana.getCoord().getLat(), maybeTirana.getCoord().getLat(), MAX_DELTA);
        assertEquals(tirana.getCoord().getLon(), maybeTirana.getCoord().getLon(), MAX_DELTA);
    }

    @Test
    public void getValuesWithPrefix() {
        trie.put(tirana.getName().toLowerCase(), tirana);
        trie.put(rome.getName().toLowerCase(), rome);
        Collection<City> ok = trie.getValuesWithPrefix("A");
        assertEquals(2, ok.size());
        Collection<City> one = trie.getValuesWithPrefix("Abc");
        assertEquals(1, one.size());
        Collection<City> none = trie.getValuesWithPrefix("+");
        assertEquals(0, none.size());
    }
}