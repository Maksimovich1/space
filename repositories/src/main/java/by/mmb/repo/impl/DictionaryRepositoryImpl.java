package by.mmb.repo.impl;

import by.mmb.model.analytic.AnalyticType;
import by.mmb.model.analytic.AnalyticValue;
import by.mmb.repo.DictionaryRepository;
import by.mmb.repo.rowMapperCar.dictionary.DictionaryRowMapper;
import by.mmb.util.Pair;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.*;
import java.util.function.Supplier;

/**
 * @author andrew.maksimovich
 */
@Slf4j
@Repository
public class DictionaryRepositoryImpl implements DictionaryRepository {

    private final JdbcTemplate jdbcTemplate;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    public DictionaryRepositoryImpl(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    public Optional<AnalyticType> getAnalTypeByIdType(int idType) {
        log.trace("Поиск аналитического типа по ид типа " + idType);

        Supplier<String> sql = () -> "select id_type, code_analytic_type, description " +
                "from space.analytic_type " +
                "where code_analytic_type = ?";

        AnalyticType resQuery;
        try {
            resQuery = jdbcTemplate.queryForObject(
                    sql.get(),
                    new DictionaryRowMapper(),
                    idType
            );
        } catch (EmptyResultDataAccessException e) {
            log.error("не найдена аналитика по idType=" + idType);
            return Optional.empty();
        }

        return Optional.ofNullable(resQuery);
    }

    @Override
    public List<AnalyticValue> getAnalValuesByIdType(int idType) {
        log.trace("Поиск аналитических значений по типу " + idType);

        Supplier<String> sql = () -> "select id_value, code_analytic_type, code_analytic_value, description, relation1_t, relation1_v, relation2_t, relation2_v " +
                "from analytic_value " +
                "where code_analytic_type = ?";

        List<AnalyticValue> resQuery;
        try {
            resQuery = jdbcTemplate.query(
                    sql.get(),
                    (resultSet, rowNum) -> AnalyticValue.builder()
                            .id(resultSet.getInt(1))
                            .codeTypeId(resultSet.getInt(2))
                            .codeValueId(resultSet.getInt(3))
                            .description(resultSet.getString(4))
                            .relationPair(new ArrayList<>()) // TODO !!!!!!!!!!!!!
                            .build(),
                    idType
            );

            return resQuery;
        } catch (EmptyResultDataAccessException ex) {
            log.error("не найдена аналитика по idType=" + idType);
            return Collections.emptyList();
        }
    }

    @Override
    public boolean createAnalValue(@NonNull AnalyticValue analyticValue) {
        log.trace("create analyticValue count type = " + analyticValue.getCodeTypeId());
        Supplier<String> sql = () -> "insert into space.analytic_value (code_analytic_type, code_analytic_value, description, relation1_t, relation1_v, relation2_t, relation2_v) " +
                "values (?, ?, ?, ?, ?, ?, ?);";

        List<Pair<Integer, Integer>> relationPair = analyticValue.getRelationPair();

        jdbcTemplate.update(
                sql.get(),
                analyticValue.getCodeTypeId(),
                analyticValue.getCodeValueId(),
                analyticValue.getDescription(),
                getRelationIfPresent(relationPair, 0, 1),
                getRelationIfPresent(relationPair, 0, 2),
                getRelationIfPresent(relationPair, 1, 1),
                getRelationIfPresent(relationPair, 1, 2)
        );

        return true;
    }

    @Override
    public boolean createAnalValue(@NonNull List<AnalyticValue> analyticValue) {
        int codeTypeId = analyticValue.get(0).getCodeTypeId();
        log.trace("create analyticValue count " + analyticValue.size() + " type = " + codeTypeId);

        Supplier<String> sql = () -> "insert into analytic_value (code_analytic_type, code_analytic_value, description, relation1_t, relation1_v, relation2_t, relation2_v) " +
                "values (?, ?, ?, ?, ?, ?, ?)";

        int[] ints = jdbcTemplate.batchUpdate(
                sql.get(),
                new BatchPreparedStatementSetter() {
                    @Override
                    public void setValues(PreparedStatement ps, int i) throws SQLException {
                        AnalyticValue analyticValue1 = analyticValue.get(i);
                        ps.setInt(1, analyticValue1.getCodeTypeId());
                        ps.setInt(2, analyticValue1.getCodeValueId());
                        ps.setString(3, analyticValue1.getDescription());
                        ps.setObject(4, getRelationIfPresent(analyticValue1.getRelationPair(), 0, 1));
                        ps.setObject(5, getRelationIfPresent(analyticValue1.getRelationPair(), 0, 2));
                        ps.setObject(6, getRelationIfPresent(analyticValue1.getRelationPair(), 1, 1));
                        ps.setObject(7, getRelationIfPresent(analyticValue1.getRelationPair(), 1, 2));
                    }

                    @Override
                    public int getBatchSize() {
                        return analyticValue.size();
                    }
                }
        );
        log.info("insert batch size " + Arrays.toString(ints));
        return true;
    }

    @Override
    public boolean createAnalType(@NonNull AnalyticType analyticType) {
        log.trace("create AnalyticType type = " + analyticType.getCodeType());
        Supplier<String> sql = () -> "insert into analytic_type (code_analytic_type, description) VALUES (?, ?)";

        jdbcTemplate.update(
                sql.get(),
                analyticType.getCodeType(),
                analyticType.getDescription()
        );

        return true;
    }

    @Override
    public boolean updateAnalValue(@NonNull AnalyticValue analyticValue) {
        return false;
    }

    @Override
    public boolean updateAnalType(@NonNull AnalyticType analyticType) {
        return false;
    }

    @Override
    public boolean deleteAnalValue(@NonNull final List<Integer> idValue, int idTypeCode) {
        log.trace("delete AnalyticType type = " + idTypeCode + " for ids = " + Arrays.toString(idValue.toArray()));

        Supplier<String> sql = () -> "delete from analytic_value where code_analytic_type in (:ids)";

        int countDelete = namedParameterJdbcTemplate.update(
                sql.get(),
                Collections.singletonMap("ids", idValue)
        );
        log.debug("has been deleted " + countDelete + " row(s)");
        return true;
    }

    @Override
    public boolean deleteAnalType(int idType) {
        log.trace("delete analyticType by idType " + idType);

        Supplier<String> sql = () -> "delete from analytic_type where code_analytic_type = ?";

        int update = jdbcTemplate.update(
                sql.get(),
                idType
        );
        log.info("delete __ " + update + " row(s)");
        return true;
    }

    /**
     * Возвращает нужную связь из списка
     *
     * @param pairList    лист со саязями
     * @param numberPair  номер пары
     * @param typeOrValue 1 is type and 2 is value
     * @return значение связи
     */
    private Integer getRelationIfPresent(List<Pair<Integer, Integer>> pairList, int numberPair, int typeOrValue) {
        if (
                pairList == null || pairList.isEmpty() ||
                        (typeOrValue != 1 && typeOrValue != 2) ||
                        pairList.size() <= numberPair
        ) {
            return null;
        }

        Pair<Integer, Integer> integerIntegerPair;
        try {
            integerIntegerPair = pairList.get(numberPair);
            return typeOrValue == 1 ? integerIntegerPair.getKey() : integerIntegerPair.getValue();
        } catch (Exception e) {
            return null;
        }
    }
}
