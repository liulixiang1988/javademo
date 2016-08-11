package lx.spring.core.entities;

import org.springframework.stereotype.Component;

/**
 * Created by liulixiang on 16/8/9.
 */
@Component
public class Royals implements Team {
    @Override
    public String getName() {
        return "Kansas City Royals";
    }
}
