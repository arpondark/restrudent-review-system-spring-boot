package com.shazan.restrudent.repo;

import com.shazan.restrudent.domain.entity.Restrurant;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RestrurantRepo extends ElasticsearchRepository<Restrurant, String> {


}
