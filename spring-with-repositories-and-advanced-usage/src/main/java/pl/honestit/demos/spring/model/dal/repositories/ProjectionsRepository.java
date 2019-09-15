package pl.honestit.demos.spring.model.dal.repositories;

import pl.honestit.demos.spring.model.entities.projections.Projection;

import java.awt.print.Pageable;
import java.io.Serializable;
import java.util.List;

/**
 * Interfejs obsługujący podstawowe operacje na projekcjach.
 *
 * @param <ID> Na potrzeby projekcji do pojedynczej encji
 */
public interface ProjectionsRepository<ID extends Serializable> {

    /**
     * Pobieranie listy encji z wynikami ograniczonymi do danej projekcji
     * @param projectionClass interfejs projekcji
     * @param <P> typ reprezetujący projekcje
     * @return lista wyników ograniczona do danej projekcji
     */
    <P extends Projection> List<P> findAllBy(Class<P> projectionClass);

    /**
     * Pobieranie listy encji z wynikami ograniczonymi do danej projekcji
     * @param projectionClass interfejs projekcji
     * @param pageable konfiguracja stronicowania
     * @param <P> typ reprezetujący projekcje
     * @return lista wyników ograniczona do danej projekcji
     */
    <P extends Projection> List<P> findAllBy(Class<P> projectionClass, Pageable pageable);

    /**
     * Pobieranie pojedynczje encji z wynikami ograniczonymi do danej projekcji
     * @param id identyfikator encji
     * @param projectionClass interfejs projekcji
     * @param <P> typ reprezentujący projekcie
     * @return wynik ograniczony do danej projekcji
     */
    <P extends Projection> P findById(ID id, Class<P> projectionClass);
}
