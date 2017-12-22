package com.marvin.bundle.framework.mvc.view;

import com.marvin.component.resolver.PathResolver;
import com.marvin.component.util.ClassUtils;
import java.lang.reflect.Constructor;

public class ViewClassResolver extends PathResolver<ViewInterface> implements ViewResolverInterface {

    private final ViewNameParser nameParser;

    public ViewClassResolver(ViewNameParser nameParser) {
        this.nameParser = nameParser;
    }

    @Override
    public ViewInterface resolve(String name) throws Exception {
        name = this.nameParser.parse(name);
        return doResolve(name);
    }

    @Override
    public ViewInterface doResolve(String name) throws Exception {
        name = ClassUtils.convertResourcePathToClassName(name);
        Class clazz = ClassUtils.forName(name, getClass().getClassLoader());
        Constructor<ViewInterface> constructor = ClassUtils.getConstructorIfAvailable(clazz, new Class[]{String.class});
        ViewInterface result = constructor.newInstance(new Object[]{name});
        return result;
    }
}
