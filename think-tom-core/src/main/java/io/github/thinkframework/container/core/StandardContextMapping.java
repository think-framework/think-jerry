package io.github.thinkframework.container.core;

import javax.servlet.http.HttpServletMapping;
import javax.servlet.http.MappingMatch;

public class StandardContextMapping {

    private HttpServletMapping httpServletMapping;

    class HttpServletMappingImpl implements HttpServletMapping {

        @Override
        public String getMatchValue() {
            return null;
        }

        @Override
        public String getPattern() {
            return null;
        }

        @Override
        public String getServletName() {
            return null;
        }

        @Override
        public MappingMatch getMappingMatch() {
            return null;
        }
    }
}
