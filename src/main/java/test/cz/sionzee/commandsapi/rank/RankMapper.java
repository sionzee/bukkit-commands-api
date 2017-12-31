package test.cz.sionzee.commandsapi.rank;

import cz.sionzee.commandsapi.mappers.IArgumentConverter;
import cz.sionzee.commandsapi.mappers.IArgumentMapper;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.IdentityHashMap;
import java.util.Map;

public class RankMapper implements IArgumentMapper {
    private IdentityHashMap<Class, IArgumentConverter> m_map;

    @NotNull
    @Override
    public Map<Class<?>, IArgumentConverter<?>> getMapper() {
        return Collections.unmodifiableMap(m_map);
    }

    @Override
    public void init() {
        m_map = new IdentityHashMap<Class, IArgumentConverter>();
        m_map.put(Rank.class, Rank::from);
    }

    @Override
    public <V> V convertTo(@NotNull Class<V> key, @NotNull String arg) {
        return (V) m_map.get(key).from(arg);
    }
}
