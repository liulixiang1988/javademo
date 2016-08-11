package lx.spring.core.entities;

import org.springframework.stereotype.Component;

/**
 * Created by liulixiang on 16/8/9.
 */
@Component
public class Cubs implements Team {
    @Override
    public String getName() {
        return "Chicago Cubs";
    }
}
