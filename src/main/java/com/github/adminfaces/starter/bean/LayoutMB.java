package com.github.adminfaces.starter.bean;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import java.io.Serializable;

/**
 * Created by rmpestano on 07/05/17.
 */
@Named
@SessionScoped
public class LayoutMB implements Serializable {

    private String layout;
    private String publicLayout;

    @PostConstruct
    public void init() {
        setDefaultLayout();
    }

    public String getLayout() {
        return layout;
    }

    public void setHorizontalLayout() {
        layout = "/WEB-INF/templates/template-horizontal.xhtml";
    }

    public void setDefaultLayout() {
        layout = "/WEB-INF/templates/template.xhtml";
    }

    /**
     * @return the publicLayout
     */
    public String getPublicLayout() {
          publicLayout = "/WEB-INF/templates/template2.xhtml";
        return publicLayout;
    }

    /**
     * @param publicLayout the publicLayout to set
     */
    public void setPublicLayout(String publicLayout) {
        this.publicLayout = publicLayout;
    }
}
