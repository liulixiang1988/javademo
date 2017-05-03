package liulx.guice;

import com.google.inject.AbstractModule;
import liulx.Api;
import liulx.iservice.IPostService;
import liulx.iservice.IUserService;
import liulx.service.PostService;
import liulx.service.UserService;

/**
 * Created by Liu Lixiang on 2017/5/3.
 */
public class ServiceModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(IUserService.class).to(UserService.class);
        bind(IPostService.class).to(PostService.class);

        requestStaticInjection(Api.class);
    }
}
