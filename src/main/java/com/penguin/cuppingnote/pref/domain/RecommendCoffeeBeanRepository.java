package com.penguin.cuppingnote.pref.domain;

import com.penguin.cuppingnote.coffeebean.domain.CoffeeBean;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecommendCoffeeBeanRepository extends JpaRepository<CoffeeBean, Long> {

    @Query(value = "SELECT *, ABS(aroma - :aroma) + ABS(acidity - :acidity) + ABS(sweetness - :sweetness) + ABS(bitterness - :bitterness) + ABS(body - :body) AS distance "
            + "FROM coffee_bean "
            + "WHERE acidity BETWEEN :acidity - 2 AND :acidity + 2 AND aroma BETWEEN :aroma - 2 AND :aroma + 2 "
            + "ORDER BY distance ASC", nativeQuery = true)
    List<CoffeeBean> findSimilarCoffeeBeansWithDistance(@Param("aroma") Integer aroma, @Param("acidity") Integer acidity,
                                                      @Param("sweetness") Integer sweetness, @Param("bitterness") Integer bitterness,
                                                      @Param("body") Integer body);

    @Query(value = "SELECT * FROM coffee_bean ORDER BY RAND() LIMIT 2", nativeQuery = true)
    List<CoffeeBean> findTwoRandomCoffeeBeans();

}