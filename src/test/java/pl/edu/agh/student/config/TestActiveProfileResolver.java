package pl.edu.agh.student.config;

import org.springframework.test.context.ActiveProfilesResolver;

public class TestActiveProfileResolver implements ActiveProfilesResolver {

    @Override
    public String[] resolve(final Class<?> aClass) {
        return new String[] { "dev" };
    }
}
