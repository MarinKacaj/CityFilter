package com.marin.cityfilter.prefixtrie;

import com.marin.cityfilter.model.City;

import java.util.Collection;
import java.util.Iterator;
import java.util.TreeSet;

/**
 * @author Marin Kacaj
 */
class PrefixTrieNode implements Iterable<PrefixTrieNode>, Comparable<PrefixTrieNode> {

    private String prefix;

    private City city;

    private boolean hasCity;

    private Collection<PrefixTrieNode> children;

    PrefixTrieNode(String prefix) {
        this(prefix, null);
        this.hasCity = false;
    }

    PrefixTrieNode(String prefix, City city) {
        this.prefix = prefix;
        this.city = city;
        this.hasCity = true;
        this.children = new TreeSet<>();
    }

    City getCity() {
        return city;
    }

    void setCity(City city) {
        this.city = city;
    }

    String getPrefix() {
        return prefix;
    }

    void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    Collection<PrefixTrieNode> getChildren() {
        return children;
    }

    void setHasCity(boolean hasCity) {
        this.hasCity = hasCity;
        if (!hasCity) {
            this.city = null;
        }
    }

    public boolean hasCity() {
        return hasCity;
    }

    @Override
    public Iterator<PrefixTrieNode> iterator() {
        return children.iterator();
    }

    @Override
    public int compareTo(PrefixTrieNode prefixTrieNode) {
        return prefix.compareTo(prefixTrieNode.getPrefix());
    }
}
