package com.elham.bankproject.searcher;

import java.util.List;

public interface Searcher {
    List<CustomerTransaction> search(String keyword);
}
