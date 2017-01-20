package me.minidigger.voxelgameslib.website;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.cache.GuavaTemplateCache;
import com.github.jknack.handlebars.io.TemplateLoader;
import com.github.jknack.handlebars.io.TemplateSource;

import org.eclipse.jetty.io.RuntimeIOException;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import spark.ModelAndView;
import spark.template.handlebars.HandlebarsTemplateEngine;

/**
 * Created by Martin on 20.01.2017.
 */
public class MaTemplateEngine extends HandlebarsTemplateEngine {

    private Handlebars handlebars;

    public MaTemplateEngine(TemplateLoader loader) {
        loader.setSuffix(null);

        handlebars = new Handlebars(loader);

        // Set Guava cache.
        Cache<TemplateSource, Template> cache = CacheBuilder.newBuilder().expireAfterWrite(10, TimeUnit.MINUTES)
                .maximumSize(1000).build();

        handlebars = handlebars.with(new GuavaTemplateCache(cache));
    }

    @Override
    public String render(ModelAndView modelAndView) {
        String viewName = modelAndView.getViewName();
        try {
            Template template = handlebars.compile(viewName);
            return template.apply(modelAndView.getModel());
        } catch (IOException e) {
            throw new RuntimeIOException(e);
        }
    }
}
