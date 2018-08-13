package com.marin.cityfilter.prefixtrie;

import com.marin.cityfilter.model.City;

/**
 * @author Marin Kacaj
 */
public interface PrefixTrieVisitor<Result> {

    void visit(String key, City city);

    Result getResult();
}
