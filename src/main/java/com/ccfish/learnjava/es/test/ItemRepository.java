package com.ccfish.learnjava.es.test;

import com.ccfish.learnjava.es.model.Item;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Component;

/**
 * @Author: Ciaos
 * @Date: 2019/11/3 14:24
 */
@Component
public interface ItemRepository extends ElasticsearchRepository<Item, String> {

    Item queryItemById(String id);
}
