package com.marin.cityfilter.prefixtrie;

import com.marin.cityfilter.model.City;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

/**
 * @author Marin Kacaj
 */
public class CityPrefixTrie implements Map<String, City> {

    private static final String KEY_CANNOT_BE_NULL = "key cannot be null";
    private static final String PREFIX_CANNOT_BE_NULL = "prefix cannot be null";
    private static final String KEYS_MUST_BE_STRING = "keys must be String instances";
    private static final String OP_NOT_NECESSARY = "Operation not necessary for use case";

    private PrefixTrieNode root;

    public CityPrefixTrie() {
        this.root = new PrefixTrieNode("");
    }

    private static int largestPrefixLength(CharSequence a, CharSequence b) {
        int len = 0;
        for (int i = 0; i < Math.min(a.length(), b.length()); ++i) {
            if (a.charAt(i) != b.charAt(i)) {
                break;
            }
            ++len;
        }
        return len;
    }

    private void visit(PrefixTrieVisitor<?> visitor) {
        visit(root, "", "", visitor);
    }

    private void visit(PrefixTrieVisitor<?> visitor, String prefix) {
        visit(root, prefix, "", visitor);
    }

    private void visit(PrefixTrieNode node, String searchedPrefix, String currentPrefix, PrefixTrieVisitor<?> visitor) {
        if (node.hasCity() && currentPrefix.startsWith(searchedPrefix)) {
            visitor.visit(currentPrefix, node.getCity());
        }

        for (PrefixTrieNode child : node) {
            int prefixLen = currentPrefix.length();
            String newPrefix = currentPrefix + child.getPrefix();
            if (searchedPrefix.length() <= prefixLen
                    || newPrefix.length() <= prefixLen
                    || newPrefix.charAt(prefixLen) == searchedPrefix.charAt(prefixLen)) {
                visit(child, searchedPrefix, newPrefix, visitor);
            }
        }
    }

    @Override
    public int size() {
        PrefixTrieVisitor<Integer> visitor = new PrefixTrieVisitor<Integer>() {
            int count = 0;

            @Override
            public void visit(String key, City value) {
                ++count;
            }

            @Override
            public Integer getResult() {
                return count;
            }
        };
        visit(visitor);
        return visitor.getResult();
    }

    @Override
    public boolean isEmpty() {
        return root.getChildren().isEmpty();
    }

    @Override
    public boolean containsKey(final Object candidateKey) {
        throw new UnsupportedOperationException(OP_NOT_NECESSARY);
    }

    @Override
    public boolean containsValue(final Object candidateValue) {
        throw new UnsupportedOperationException(OP_NOT_NECESSARY);
    }

    @Override
    public City get(final Object keyToCheck) {
        Preconditions.checkNotNull(keyToCheck, KEY_CANNOT_BE_NULL); // per Map#get docs
        Preconditions.checkIsString(keyToCheck, KEYS_MUST_BE_STRING); // per Map#get docs

        PrefixTrieVisitor<City> visitor = new PrefixTrieVisitor<City>() {
            City result = null;

            @Override
            public void visit(String key, City value) {
                if (key.equals(keyToCheck)) {
                    result = value;
                }
            }

            @Override
            public City getResult() {
                return result;
            }
        };
        visit(visitor, (String) keyToCheck);
        return visitor.getResult();
    }

    @Override
    public City put(String key, City city) {
        Preconditions.checkNotNull(key, KEY_CANNOT_BE_NULL);
        return put(key, city, root);
    }

    @Override
    public City remove(Object o) {
        return null;
    }

    @Override
    public void putAll(Map<? extends String, ? extends City> map) {
        throw new UnsupportedOperationException(OP_NOT_NECESSARY);
    }

    @Override
    public void clear() {
        throw new UnsupportedOperationException(OP_NOT_NECESSARY);
    }

    @Override
    public Set<String> keySet() {
        PrefixTrieVisitor<Set<String>> visitor = new PrefixTrieVisitor<Set<String>>() {
            Set<String> result = new TreeSet<>();

            @Override
            public void visit(String key, City value) {
                result.add(key);
            }

            @Override
            public Set<String> getResult() {
                return result;
            }
        };
        visit(visitor);
        return visitor.getResult();
    }

    @Override
    public Collection<City> values() {
        throw new UnsupportedOperationException(OP_NOT_NECESSARY);
    }

    @Override
    public Set<Entry<String, City>> entrySet() {
        PrefixTrieVisitor<Set<Entry<String, City>>> visitor =
                new PrefixTrieVisitor<Set<Entry<String, City>>>() {
                    Set<Map.Entry<String, City>> result = new HashSet<>();

                    @Override
                    public void visit(String key, City value) {
                        result.add(new AbstractMap.SimpleEntry<>(key, value));
                    }

                    @Override
                    public Set<Map.Entry<String, City>> getResult() {
                        return result;
                    }
                };
        visit(visitor);
        return visitor.getResult();
    }

    public List<City> getValuesWithPrefix(String prefix) {
        Preconditions.checkNotNull(prefix, PREFIX_CANNOT_BE_NULL);

        PrefixTrieVisitor<List<City>> visitor = new PrefixTrieVisitor<List<City>>() {
            List<City> result = new ArrayList<>();

            @Override
            public void visit(String key, City value) {
                result.add(value);
            }

            @Override
            public List<City> getResult() {
                return result;
            }
        };
        visit(visitor, prefix);
        return visitor.getResult();
    }

    private City put(String key, City city, PrefixTrieNode node) {
        City ret = null;

        final int largestPrefix = largestPrefixLength(key, node.getPrefix());
        if (largestPrefix == node.getPrefix().length() && largestPrefix == key.length()) {
            ret = node.getCity();
            node.setCity(city);
            node.setHasCity(true);
        } else if (largestPrefix == 0
                || (largestPrefix < key.length() && largestPrefix >= node.getPrefix().length())) {
            final String leftoverKey = key.substring(largestPrefix);

            boolean found = false;
            for (PrefixTrieNode child : node) {
                if (child.getPrefix().charAt(0) == leftoverKey.charAt(0)) {
                    found = true;
                    ret = put(leftoverKey, city, child);
                    break;
                }
            }

            if (!found) {
                PrefixTrieNode n = new PrefixTrieNode(leftoverKey, city);
                node.getChildren().add(n);
            }
        } else if (largestPrefix < node.getPrefix().length()) {
            final String leftoverPrefix = node.getPrefix().substring(largestPrefix);
            final PrefixTrieNode n = new PrefixTrieNode(leftoverPrefix, node.getCity());
            n.setHasCity(node.hasCity());
            n.getChildren().addAll(node.getChildren());

            node.setPrefix(node.getPrefix().substring(0, largestPrefix));
            node.getChildren().clear();
            node.getChildren().add(n);

            if (largestPrefix == key.length()) {
                ret = node.getCity();
                node.setCity(city);
                node.setHasCity(true);
            } else {
                final String leftoverKey = key.substring(largestPrefix);
                final PrefixTrieNode keyNode = new PrefixTrieNode(leftoverKey, city);
                node.getChildren().add(keyNode);
                node.setHasCity(false);
            }
        } else {
            final String leftoverKey = key.substring(largestPrefix);
            final PrefixTrieNode n = new PrefixTrieNode(leftoverKey, city);
            node.getChildren().add(n);
        }

        return ret;
    }
}
